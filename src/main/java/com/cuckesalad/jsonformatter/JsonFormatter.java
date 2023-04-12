package com.cuckesalad.jsonformatter;

import com.cuckesalad.jsonformatter.exceptions.JsonCreationException;
import com.cuckesalad.jsonformatter.models.JvmAttachment;
import com.cuckesalad.jsonformatter.models.JvmBackground;
import com.cuckesalad.jsonformatter.models.JvmFeature;
import com.cuckesalad.jsonformatter.models.JvmHook;
import com.cuckesalad.jsonformatter.models.JvmTestCase;
import com.cuckesalad.jsonformatter.models.JvmTestStep;
import com.cuckesalad.jsonformatter.models.TestExecution;
import com.cuckesalad.jsonformatter.models.TestSourcesModel;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.messages.types.Background;
import io.cucumber.messages.types.Feature;
import io.cucumber.messages.types.FeatureChild;
import io.cucumber.messages.types.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EmbedEvent;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.HookTestStep;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.WriteEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JsonFormatter implements ConcurrentEventListener {

  private TestExecution testExecution;
  private Map<UUID, UUID> lastPickleStepMap = new HashMap<>();
  private Map<UUID, UUID> lastHookStepMap = new HashMap<>();
  private Map<UUID, String> lastStepMap = new HashMap<>();
  private final TestSourcesModel testSources = new TestSourcesModel();

  public JsonFormatter(String str) {
    testExecution = new TestExecution();
  }

  @Override
  public void setEventPublisher(EventPublisher publisher) {
    publisher.registerHandlerFor(TestRunStarted.class, this::handleTestRunStarted);
    publisher.registerHandlerFor(TestSourceRead.class, this::handleTestSourceRead);
    publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
    publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
    publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    // publisher.registerHandlerFor(TestCaseFinished.class,
    // this::handleTestCaseFinished); --
    // unused in cucumber core
    publisher.registerHandlerFor(WriteEvent.class, this::handleWrite);
    publisher.registerHandlerFor(EmbedEvent.class, this::handleEmbed);
    publisher.registerHandlerFor(TestRunFinished.class, this::finishReport);
  }

  private void handleTestRunStarted(TestRunStarted event) {
    testExecution.setStartTime(new Date(event.getInstant().toEpochMilli()));
  }

  private void handleTestSourceRead(TestSourceRead event) {
    testSources.addTestSourceReadEvent(event.getUri(), event);
    Feature feature = testSources.getFeature(event.getUri());
    JvmFeature jvmFeature = new JvmFeature();
    jvmFeature.setName(feature.getName());
    jvmFeature.setDescription(feature.getDescription());
    jvmFeature.setKeyword(feature.getKeyword());
    jvmFeature.setSource(event.getSource());
    jvmFeature.setUri(event.getUri());
    jvmFeature.setFileName(new File(event.getUri().toString()).getName());
    jvmFeature.setTags(
        feature.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList()));
    testExecution.setFeature(jvmFeature);
  }

  private void handleTestCaseStarted(TestCaseStarted event) {
    TestCase testCase = event.getTestCase();
    JvmTestCase jvmTestCase = new JvmTestCase();

    jvmTestCase.setLine(testCase.getLocation().getLine());
    jvmTestCase.setTags(testCase.getTags());
    jvmTestCase.setName(testCase.getName());
    jvmTestCase.setId(testCase.getId());
    jvmTestCase.setKeyword(testCase.getKeyword());
    jvmTestCase.setUri(testCase.getUri());
    jvmTestCase.setStartTime(new Date(event.getInstant().toEpochMilli()));
    TestSourcesModel.AstNode astNode =
        testSources.getAstNode(testCase.getUri(), testCase.getLocation().getLine());
    if (astNode != null) {
      Scenario scenarioDefinition = TestSourcesModel.getScenarioDefinition(astNode);
      jvmTestCase.setDescription(scenarioDefinition.getDescription());
    }
    testExecution.getFeature(testCase.getUri()).ifPresent(f -> f.setTestCase(jvmTestCase));
  }

  // private void handleTestCaseFinished(TestCaseFinished event) {
  // System.out.println();
  // }

  private void handleTestStepStarted(TestStepStarted event) {
    TestStep testStep = event.getTestStep();

    if (testStep instanceof PickleStepTestStep) {
      createJvmStepFromPickle(event);
      if (isBackgroundStep((PickleStepTestStep) testStep)) {
        createBackgroundStep(event);
      }
      lastPickleStepMap.put(event.getTestCase().getId(), event.getTestStep().getId());
      lastStepMap.put(event.getTestCase().getId(), "TEST_STEP");
    }
    if (testStep instanceof HookTestStep) {
      createJvmStepFromHook(event);
      lastHookStepMap.put(event.getTestCase().getId(), event.getTestStep().getId());
    }
  }

  private void createBackgroundStep(TestStepStarted event) {
    Optional<JvmTestCase> jvmTestCase =
        testExecution
            .getFeature(event.getTestCase().getUri())
            .get()
            .getTestCase(event.getTestCase().getId());
    PickleStepTestStep pickleTestStep = (PickleStepTestStep) event.getTestStep();
    TestSourcesModel.AstNode astNode =
        testSources.getAstNode(event.getTestCase().getUri(), pickleTestStep.getStep().getLine());
    Background background = getBackgroundForTestCase(astNode).get();
    TestStep testStep = event.getTestStep();
    JvmTestStep jvmTestStep = null;
    JvmBackground jvmBackground = null;

    if (jvmTestCase.get().getBackground() == null) {
      jvmBackground = new JvmBackground();
    } else {
      jvmBackground = jvmTestCase.get().getBackground();
    }

    jvmBackground.setName(background.getName());
    jvmBackground.setDescription(background.getDescription());
    jvmBackground.setId(background.getId());
    jvmTestCase.get().setBackground(jvmBackground);

    jvmTestCase
        .get()
        .getBackground()
        .setTestStep(jvmTestCase.get().getTestStep(pickleTestStep.getId()).get());
    jvmTestCase.get().removeTestStep(pickleTestStep.getId());
  }

  private void createJvmStepFromHook(TestStepStarted event) {
    TestStep testStep = event.getTestStep();
    HookTestStep hookTestStep = (HookTestStep) event.getTestStep();
    JvmHook jvmHookStep = new JvmHook(hookTestStep.getHookType().toString());
    jvmHookStep.setStartTime(new Date(event.getInstant().toEpochMilli()));
    jvmHookStep.setId(testStep.getId());
    jvmHookStep.setCodeLocation(testStep.getCodeLocation());
    jvmHookStep.setKeyword(hookTestStep.getHookType().toString());
    jvmHookStep.setLine(event.getTestCase().getLocation().getLine());

    JvmTestCase jvmTestCase =
        testExecution
            .getFeature(event.getTestCase().getUri())
            .get()
            .getTestCase(event.getTestCase().getId())
            .get();

    switch (hookTestStep.getHookType()) {
      case BEFORE:
        jvmTestCase.setBefore(jvmHookStep);
        break;
      case AFTER:
        jvmTestCase.setAfter(jvmHookStep);
        break;
      case BEFORE_STEP:
        if (jvmTestCase.getTestStep(null).isEmpty()) {
          JvmTestStep jvmTestStep = new JvmTestStep();
          jvmTestStep.setBeforeStep(jvmHookStep);
          jvmTestCase.setTestStep(jvmTestStep);
        } else {
          jvmTestCase.getTestStep(null).get().setBeforeStep(jvmHookStep);
        }
        try {
          lastPickleStepMap.put(jvmTestCase.getId(), null);
        } catch (Exception e) {
          throw e;
        }

        break;
      case AFTER_STEP:
        UUID lastStepUUID = lastPickleStepMap.get(jvmTestCase.getId());
        if (jvmTestCase.getTestStep(lastStepUUID).isPresent()) {
          jvmTestCase.getTestStep(lastStepUUID).get().setAfterStep(jvmHookStep);
        } else if (jvmTestCase.getBackground().getTestStep(lastStepUUID).isPresent()) {
          jvmTestCase.getBackground().getTestStep(lastStepUUID).get().setAfterStep(jvmHookStep);
        }

        break;
      default:
        break;
    }

    lastStepMap.put(event.getTestCase().getId(), hookTestStep.getHookType().name());
  }

  private void createJvmStepFromPickle(TestStepStarted event) {
    Optional<JvmTestCase> jvmTestCase =
        testExecution
            .getFeature(event.getTestCase().getUri())
            .get()
            .getTestCase(event.getTestCase().getId());
    PickleStepTestStep pickleTestStep = (PickleStepTestStep) event.getTestStep();
    TestSourcesModel.AstNode astNode =
        testSources.getAstNode(event.getTestCase().getUri(), pickleTestStep.getStep().getLine());

    TestStep testStep = event.getTestStep();
    JvmTestStep jvmTestStep = null;

    if (jvmTestCase.get().getTestStep(null).isPresent()) {
      jvmTestStep = jvmTestCase.get().getTestStep(null).get();

    } else {
      jvmTestStep = new JvmTestStep();
      jvmTestCase.get().setTestStep(jvmTestStep);
    }

    jvmTestStep.setStartTime(new Date(event.getInstant().toEpochMilli()));
    jvmTestStep.setId(testStep.getId());
    jvmTestStep.setCodeLocation(testStep.getCodeLocation());
    jvmTestStep.setKeyword(pickleTestStep.getStep().getKeyword());
    jvmTestStep.setName(pickleTestStep.getStep().getText());
    jvmTestStep.setLine(pickleTestStep.getStep().getLine());
    jvmTestCase.get().setTestStep(jvmTestStep);
  }

  private boolean isBackgroundStep(PickleStepTestStep pickleTestStep) {
    TestSourcesModel.AstNode astNode =
        testSources.getAstNode(pickleTestStep.getUri(), pickleTestStep.getStep().getLine());
    if (astNode == null) {
      return false;
    }
    return TestSourcesModel.isBackgroundStep(astNode);
  }

  public Optional<Background> getBackgroundForTestCase(TestSourcesModel.AstNode astNode) {
    Feature feature = getFeatureForTestCase(astNode);
    return feature.getChildren().stream()
        .map(FeatureChild::getBackground)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst();
  }

  private Feature getFeatureForTestCase(TestSourcesModel.AstNode astNode) {
    while (astNode.getParent() != null) {
      astNode = astNode.getParent();
    }
    return (Feature) astNode.getNode();
  }

  private void handleTestStepFinished(TestStepFinished event) {
    TestStep testStep = event.getTestStep();
    UUID testCaseUuid = event.getTestCase().getId();
    URI uri = event.getTestCase().getUri();

    if (testStep instanceof PickleStepTestStep) {
      PickleStepTestStep pickleTestStep = (PickleStepTestStep) event.getTestStep();
      TestSourcesModel.AstNode astNode =
          testSources.getAstNode(event.getTestCase().getUri(), pickleTestStep.getStep().getLine());
      if (isBackgroundStep(pickleTestStep)) {
        testExecution
            .getFeature(uri)
            .get()
            .getTestCase(testCaseUuid)
            .get()
            .getBackground()
            .getTestSteps()
            .stream()
            .filter(ts -> ts.getId().equals(testStep.getId()))
            .findFirst()
            .get()
            .setEndTime(new Date(event.getInstant().toEpochMilli()));
      } else {
        testExecution
            .getFeature(uri)
            .get()
            .getTestCase(testCaseUuid)
            .get()
            .getTestStep(testStep.getId())
            .get()
            .setEndTime(new Date(event.getInstant().toEpochMilli()));
      }
    }
    if (testStep instanceof HookTestStep) {
      HookTestStep hookTestStep = (HookTestStep) event.getTestStep();
      JvmTestCase jvmTestCase =
          testExecution
              .getFeature(event.getTestCase().getUri())
              .get()
              .getTestCase(event.getTestCase().getId())
              .get();
      switch (hookTestStep.getHookType()) {
        case BEFORE:
          jvmTestCase.getBefore().stream()
              .filter(ts -> ts.getId().equals(hookTestStep.getId()))
              .findFirst()
              .get()
              .setEndTime(new Date(event.getInstant().toEpochMilli()));
          break;
        case AFTER:
          jvmTestCase.getAfter().stream()
              .filter(ts -> ts.getId().equals(hookTestStep.getId()))
              .findFirst()
              .get()
              .setEndTime(new Date(event.getInstant().toEpochMilli()));
          break;
        case BEFORE_STEP:
          jvmTestCase.getTestStep(null).get().getBeforeSteps().stream()
              .filter(step -> step.getId().equals(hookTestStep.getId()))
              .findFirst()
              .get()
              .setEndTime(new Date(event.getInstant().toEpochMilli()));
          break;
        case AFTER_STEP:
          UUID lastStepUUID = lastPickleStepMap.get(jvmTestCase.getId());
          if (jvmTestCase.getTestStep(lastStepUUID).isPresent()) {
            jvmTestCase.getTestStep(lastStepUUID).get().getAfterSteps().stream()
                .filter(afterStep -> afterStep.getId().equals(hookTestStep.getId()))
                .findFirst()
                .get()
                .setEndTime(new Date(event.getInstant().toEpochMilli()));
          } else if (jvmTestCase.getBackground().getTestStep(lastStepUUID).isPresent()) {
            jvmTestCase.getBackground().getTestStep(lastStepUUID).get().getAfterSteps().stream()
                .filter(afterStep -> afterStep.getId().equals(hookTestStep.getId()))
                .findFirst()
                .get()
                .setEndTime(new Date(event.getInstant().toEpochMilli()));
          }
          break;
        default:
          // throw exception
          break;
      }
    }
    testExecution
        .getFeature(uri)
        .get()
        .getTestCase(testCaseUuid)
        .get()
        .setEndtTime(new Date(event.getInstant().toEpochMilli()));
  }

  private void handleWrite(WriteEvent event) {
    System.out.println();
  }

  private void handleEmbed(EmbedEvent event) {
    JvmAttachment jvmAttachment = new JvmAttachment();
    jvmAttachment.setData(Base64.getEncoder().encodeToString(event.getData()));
    jvmAttachment.setName(event.getName());
    jvmAttachment.setMediaType(event.getMediaType());
    jvmAttachment.setStartTime(new Date(event.getInstant().toEpochMilli()));

    UUID lastStepUUID = lastPickleStepMap.get(event.getTestCase().getId());

    JvmTestCase jvmTestCase =
        testExecution
            .getFeature(event.getTestCase().getUri())
            .get()
            .getTestCase(event.getTestCase().getId())
            .get();
    JvmTestStep jvmTestStep = null;
    ;

    if (jvmTestCase.getTestStep(lastStepUUID).isPresent()) {
      jvmTestStep = jvmTestCase.getTestStep(lastStepUUID).get();
    } else if (jvmTestCase.getBackground().getTestStep(lastStepUUID).isPresent()) {
      jvmTestStep = jvmTestCase.getBackground().getTestStep(lastStepUUID).get();
    } else {
      return;
    }

    switch (lastStepMap.get(event.getTestCase().getId())) {
      case "BEFORE":
        jvmTestCase.getBefore().stream()
            .reduce((one, two) -> two)
            .get()
            .setAttachment(jvmAttachment);
        break;
      case "AFTER":
        jvmTestCase.getAfter().stream()
            .reduce((one, two) -> two)
            .get()
            .setAttachment(jvmAttachment);
        break;
      case "BEFORE_STEP":
        jvmTestStep.getBeforeSteps().stream()
            .reduce((one, two) -> two)
            .get()
            .setAttachment(jvmAttachment);
        break;
      case "AFTER_STEP":
        jvmTestStep.getAfterSteps().stream()
            .reduce((one, two) -> two)
            .get()
            .setAttachment(jvmAttachment);
        break;
      default:
        break;
    }
  }

  private void finishReport(TestRunFinished event) {
    try {
      ObjectMapper Obj = new ObjectMapper();
      byte[] json = Obj.writeValueAsBytes(testExecution);

      File file = new File(System.getProperty("user.dir") + "/target/vkd.json");
      FileOutputStream writer = new FileOutputStream(file);
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      throw new JsonCreationException(e.getMessage());
    }
  }
}

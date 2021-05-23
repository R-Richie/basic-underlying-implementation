package designpatterns;

import java.util.Optional;

/**
 * 步骤接口
 */
public interface Step {
    String getStepName();
    Optional<String> getNextStepByState(StepState resultState);
}

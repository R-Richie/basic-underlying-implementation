package designpatterns;

import java.util.Map;
import java.util.Optional;

/**
 * 单个流程
 */

public class Process {
    private String name;

    private Step first;

    private Map<String, Step> stepNameMapping;
    public Step getFirstStep(){
        return first;
    }
    public Step getCurrentStep(String currentStep){
         return stepNameMapping.get(currentStep);
    }

    public String getNextStep(String currentStep, StepState stepState){
        boolean containsKey = stepNameMapping.containsKey(currentStep);
        if(!containsKey){
            return null;
        }
        Step step = stepNameMapping.get(currentStep);
        Optional<String> nextStepByState = step.getNextStepByState(stepState);
        return nextStepByState.get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

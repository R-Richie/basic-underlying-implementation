package designpatterns;

import java.util.Map;
import java.util.Optional;

/**
 * 步骤实现
 */
public class StepImpl implements Step{
    private String stepName;
    private Map<StepState,String> stateMapping;

    @Override
    public String getStepName() {
        return stepName;
    }
    @Override
    public Optional<String> getNextStepByState(StepState resultState){
        boolean containsKey = stateMapping.containsKey(resultState);
        if(!containsKey){
            return Optional.empty();
        }
        String nextStepName = stateMapping.get(resultState);
        return Optional.of(nextStepName);
    }
}

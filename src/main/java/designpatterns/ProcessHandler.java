package designpatterns;

import java.util.Map;

/**
 * 流程处理器
 */
public class ProcessHandler {
    private Map<String, Process> processMapping;
    private Map<Step, StepHandler> stepMapping;

    public void addProcess(Process process){
       String name = process.getName();
       processMapping.put(name, process);
    }

    public void handler(String name, ParamIn in){
       Process process = processMapping.get(name);
       Step firstStep = process.getFirstStep();
       StepHandler stepHandler = stepMapping.get(firstStep);
       StepState stepState = stepHandler.handler(in);

       String nextStep = process.getNextStep(firstStep.getStepName(), stepState);

       while(nextStep != "end"){
           String nextTemp = nextStep;
           StepHandler nextStepHandler = stepMapping.get(nextTemp);
           StepState nextStepState = nextStepHandler.handler(in);
           nextStep = process.getNextStep(nextTemp, nextStepState);
       }

    }
    public void handler(String processName, String stepName, ParamIn in){
       Process process = processMapping.get(processName);
       String nextStep = stepName;

       while(nextStep != "end"){
           String nextTemp = nextStep;
           StepHandler nextStepHandler = stepMapping.get(nextTemp);
           StepState nextStepState = nextStepHandler.handler(in);
           nextStep = process.getNextStep(nextTemp, nextStepState);
       }
    }

    public void refreshMapping(){
        refreshProcessMapping();
        refreshStepMapping();
    }
    private void refreshProcessMapping(){
        //会动态更新，根据配置重新加载

    }
    private void refreshStepMapping(){
        //不会更新，根据代码加载节点
    }
}

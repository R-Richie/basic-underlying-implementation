package designpatterns;

public enum DefaultStepState implements StepState {
    /**
     * 默认成功
     */
    STEP_DEFAULT_SUCCESS("SUCCESS"),
    /**
     * 默认失败
     */
    STEP_DEFAULT_FAILURE("FAILURE");

    private final String state;

    DefaultStepState(String state) {
        this.state = state;
    }

    @Override
    public String getCurrentState() {
        return state;
    }
}

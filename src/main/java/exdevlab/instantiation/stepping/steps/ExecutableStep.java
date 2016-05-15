package exdevlab.instantiation.stepping.steps;

import exdevlab.instantiation.stepping.StepState;

public abstract class ExecutableStep {
    protected StepState State;

    private Boolean IsFakeStep(){
        return (FakeStepAttribute.class.getAnnotation(FakeStepAttribute.class)) != null;
    }

    protected ExecutableStep()
    {
        State = StepState.BEFOREEXECUTE;
    }
}

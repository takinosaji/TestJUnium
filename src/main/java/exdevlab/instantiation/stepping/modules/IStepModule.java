package exdevlab.instantiation.stepping.modules;

import exdevlab.instantiation.stepping.StepExecutionResult;
import exdevlab.instantiation.stepping.steps.IStep;

public interface IStepModule {
    void BeforeExecution(IStep step);
    void AfterExecution(IStep step, StepExecutionResult result);
}

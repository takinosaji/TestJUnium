package exdevlab.instantiation.stepping;

import exdevlab.common.Action;
import exdevlab.common.Func;
import exdevlab.instantiation.stepping.modules.IStepModule;
import exdevlab.instantiation.stepping.steps.IExecutableStep;
import exdevlab.instantiation.stepping.steps.IExecutableStepOfTResult;
import exdevlab.instantiation.stepping.steps.IStep;

public interface IStepRunner {
    void Run(IExecutableStep step);
    void Run(Action operations);
    <TResult> TResult RunWithReturnValue(IExecutableStepOfTResult<TResult> step);
    <TResult> TResult RunWithReturnValue(Func<TResult> operationsWithReturnValue);
    void BeforeExecution(IStep step);
    void AfterExecution(IStep step, StepExecutionResult result);
    void RegisterModules(IStepModule... modules);
    void UnregisterModules(IStepModule... modules);
}

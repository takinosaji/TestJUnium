package exdevlab.instantiation.stepping.steps;

public interface IExecutableStepOfTResult<TResult> extends IStep {
    TResult Execute();
}

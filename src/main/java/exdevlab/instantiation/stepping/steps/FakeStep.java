package exdevlab.instantiation.stepping.steps;

import exdevlab.common.Action;

public class FakeStep extends ExecutableStep implements IExecutableStep {
    public Action Operations;

    public FakeStep(Action operations)
    {
        Operations = operations;
    }
    public FakeStep() {}

    @Override
    public void Execute() {
        Operations.Invoke();
    }
}

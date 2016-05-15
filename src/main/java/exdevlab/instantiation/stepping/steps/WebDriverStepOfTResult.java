package exdevlab.instantiation.stepping.steps;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverStepOfTResult<TResult> extends WebDriverBaseStep
        implements IExecutableStepOfTResult {

    protected WebDriverStepOfTResult(WebDriver driver) {
        super(driver);
    }

    public abstract TResult Execute();;
}

package exdevlab.instantiation.stepping.steps;


import org.openqa.selenium.WebDriver;

public abstract class WebDriverStep extends WebDriverBaseStep implements IExecutableStep{
    protected WebDriverStep(WebDriver driver) {
        super(driver);
    }
    public abstract void Execute();
}


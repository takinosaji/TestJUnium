package exdevlab.instantiation.stepping.steps;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverBaseStep extends ExecutableStep {
    public WebDriver Driver;

    protected WebDriverBaseStep(WebDriver driver)
    {
        Driver = driver;
    }
}

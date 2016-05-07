package exdevlab.instantiation.stepping.modules.logging;

public class ConsoleLogger implements ILogger {
    @Override
    public void Info(String message) {
        System.out.println("INFO: " + message);
    }

    @Override
    public void Info(String template, Object[]... params) {
        System.out.println(String.format("INFO: " + template, params));
    }

    @Override
    public void Debug(String message) {
        System.out.println("DEBUG: " + message);
    }

    @Override
    public void Debug(String template, Object[]... params) {
        System.out.println(String.format("DEBUG: " + template, params));
    }

    @Override
    public void Exception(String message) {
        System.out.println("EXCEPTION: " + message);
    }

    @Override
    public void Exception(String template, Object[]... params) {
        System.out.println(String.format("EXCEPTION: " + template, params));
    }

    @Override
    public void Exception(Exception exception) {
        System.out.println(String.format("EXCEPTION: " + exception.getMessage()));
    }
}

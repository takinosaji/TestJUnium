package exdevlab.instantiation.stepping.modules.logging;

public interface ILogger {
    void Info(String message);
    void Info(String template, Object[]... params );
    void Debug(String message);
    void Debug(String template, Object[]... params);
    void Exception(String message);
    void Exception(String template, Object[]... params);
    void Exception(Exception exception);
}

package logging;

public interface ICreateLogger {

    public Logging.Logger.Component createStandaloneLogger(String name);
}

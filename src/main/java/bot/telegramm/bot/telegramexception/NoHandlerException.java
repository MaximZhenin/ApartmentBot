package bot.telegramm.bot.telegramexception;

public class NoHandlerException extends Exception {

    public NoHandlerException() {
    }

    public NoHandlerException(String message) {
        super(message);
    }

    public NoHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoHandlerException(Throwable cause) {
        super(cause);
    }

}
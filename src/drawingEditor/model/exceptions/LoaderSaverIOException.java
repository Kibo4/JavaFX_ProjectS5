package drawingEditor.model.exceptions;

/**
 * thrown when there is a problem with IO access
 */
public class LoaderSaverIOException extends Exception{
    public LoaderSaverIOException(String message) {
        super(message);
    }
}

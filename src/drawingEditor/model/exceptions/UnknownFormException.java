package drawingEditor.model.exceptions;

/**
 * thrown when the user try to create a form that doesn't exist
 */
public class UnknownFormException extends Exception{
    public UnknownFormException(String message) {
        super(message);
    }
}

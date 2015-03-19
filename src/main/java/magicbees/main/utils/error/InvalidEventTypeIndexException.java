package magicbees.main.utils.error;

public class InvalidEventTypeIndexException extends RuntimeException {
	private static final long serialVersionUID = 8920019214439088440L;

	public InvalidEventTypeIndexException(String message) {
		this(message, null);
	}
	
	public InvalidEventTypeIndexException(String message, Throwable cause) {
		super(message, cause);
	}
}

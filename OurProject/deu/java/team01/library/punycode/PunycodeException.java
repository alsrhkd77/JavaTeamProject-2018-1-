package deu.java.team01.library.punycode;

public class PunycodeException extends Exception {
	static final String OVERFLOW = "Overflow.";
	static final String BAD_INPUT = "Bad input.";

	/**
	 * Creates a new PunycodeException.
	 *
	 * @param m
	 *            message.
	 */
	public PunycodeException(String m) {
		super(m);
	}
}
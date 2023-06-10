/**
 * MException.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.util;

/**
 * <b>MException</b><br>
 * 
 * @author omatei01 (2018)
 */
public class MException extends Exception {

	private static final long serialVersionUID = -2009321799458568704L;

	public MException() {
		super();
	}

	public MException(String message) {
		super(message);
	}

	public MException(String message, Throwable cause) {
		super(message, cause);
	}

	public MException(String message, Object... args) {
		super(String.format(message, args));
	}

	public MException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}

	public MException(Throwable cause) {
		super(cause);
	}
}

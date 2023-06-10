package ro.anzisoft.common.db;

import ro.anzisoft.common.util.MException;

public class DbException extends MException {
	private static final long serialVersionUID = -8746410386831920290L;

	public DbException() {
		super();
	}

	public DbException(String message) {
		super(message);
	}

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbException(String message, Object... args) {
		super(String.format(message, args));
	}

	public DbException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}

	public DbException(Throwable cause) {
		super(cause);
	}
}
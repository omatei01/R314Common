/**
 * ConfigPropertiesException.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.config;

/**
 * Exception used when an setting is not found.<br>
 * Runtime ca s anu fiu nevoit sa interceptez explicit.<br>
 * 
 * @see ConfigException
 */
public class ConfigPropertiesException extends RuntimeException {

	private static final long serialVersionUID = -2538888915000382217L;

	public ConfigPropertiesException() {
		super();
	}

	public ConfigPropertiesException(String message) {
		super(message);
	}

	public ConfigPropertiesException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigPropertiesException(Throwable cause) {
		super(cause);
	}
}

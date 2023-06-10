/**
 * ConfigException.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.config;

/**
 * 
 * <b>ConfigException</b>
 * erori de tip, valori etc<br>
 * daca lipseste atunci {@link ConfigPropertiesException}<br>
 * 
 * @author omatei01 (9 mai 2018)
 * @version 1.0
 */
public class ConfigException extends RuntimeException {
	private static final long serialVersionUID = -1665812582652029177L;

	public ConfigException() {
		super();
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}
}

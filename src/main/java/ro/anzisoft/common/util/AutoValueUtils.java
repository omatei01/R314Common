package ro.anzisoft.common.util;

/**
 * Utility methods related to Google AutoValue
 */
public final class AutoValueUtils {
	private AutoValueUtils() {}

	/**
	 * Get the canonical class name of the provided {@link Class} with special handling of Google AutoValue classes.
	 *
	 * @param aClass a class
	 * @return the canonical class name of {@code aClass} or its super class in case of an auto-generated class by
	 *         Google AutoValue (com.google.auto.value.AutoValue)
	 * @see Class#getCanonicalName()
	 */
	public static String getCanonicalName(final Class<?> aClass) {
		Class<?> cls = aClass;
		while (cls.getSimpleName().matches("^\\$*AutoValue_.*")) {
			cls = cls.getSuperclass();
		}

		return cls.getCanonicalName();
	}
}

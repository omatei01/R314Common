package ro.anzisoft.common.util;

import java.nio.ByteBuffer;

/**
 * <b>ByteBufferUtils</b><br>
 * 
 * @author omatei01 (2018)
 */
public abstract class ByteBufferUtils {
	/**
	 * Read the given byte buffer into a byte array
	 * 
	 * @param buffer b
	 * @return b
	 */
	public static byte[] readBytes(ByteBuffer buffer) {
		return readBytes(buffer, 0, buffer.limit());
	}

	/**
	 * Read a byte array from the given offset and size in the buffer
	 * 
	 * @param buffer b
	 * @param offset b
	 * @param size b
	 * @return b
	 */
	public static byte[] readBytes(ByteBuffer buffer, int offset, int size) {
		final byte[] dest = new byte[size];
		if (buffer.hasArray()) {
			System.arraycopy(buffer.array(), buffer.arrayOffset() + offset, dest, 0, size);
		} else {
			buffer.mark();
			buffer.get(dest);
			buffer.reset();
		}

		return dest;
	}
}

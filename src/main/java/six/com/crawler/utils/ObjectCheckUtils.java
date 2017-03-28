package six.com.crawler.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author six
 * @date 2016年6月24日 上午10:47:06
 */

public class ObjectCheckUtils {

	private ObjectCheckUtils() {
	}

	public static <T> T checkNotNull(T arg, String text) {
		if (arg == null) {
			throw new NullPointerException(text);
		}
		return arg;
	}

	public static <T> T checkNotNull(T arg) {
		if (arg == null) {
			throw new NullPointerException();
		}
		return arg;
	}

	public static int checkPositive(int i, String name) {
		if (i <= 0) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
		}
		return i;
	}

	public static long checkPositive(long i, String name) {
		if (i <= 0) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
		}
		return i;
	}

	public static int checkPositiveOrZero(int i, String name) {
		if (i < 0) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
		}
		return i;
	}

	public static <T> T[] checkNonEmpty(T[] array, String name) {
		checkNotNull(array, name);
		checkPositive(array.length, name + ".length");
		return array;
	}
	
	public static String checkStrBlank(String str, String name) {
		if(StringUtils.isBlank(str)){
			throw new IllegalArgumentException(name + " must be not blank");
		}
		return str;
	}

	public static <T> T[] checkNonEmpty(T[] array) {
		checkNotNull(array);
		checkPositive(array.length, "length");
		return array;
	}
	
	public static byte[] checkNonEmpty(byte[] array) {
		checkNotNull(array);
		checkPositive(array.length, "length");
		return array;
	}

	public static int intValue(Integer wrapper, int defaultValue) {
		return wrapper != null ? wrapper.intValue() : defaultValue;
	}

	public static long longValue(Long wrapper, long defaultValue) {
		return wrapper != null ? wrapper.longValue() : defaultValue;
	}

}

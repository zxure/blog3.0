package zx.blog.util;

public class StringUtils {
	/**
	 * 将字符串第一个字母变为大写
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}
	
	/**
	 * 将字符串第一个字母转换为小写
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}
	
	
	/**
	 * 转换字符串的第一个字母
	 * @param str
	 * @param capitalize true：转换为大写， false：转换为小写
	 * @return
	 */
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}
}

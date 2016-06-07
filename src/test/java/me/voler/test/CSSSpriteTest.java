package me.voler.test;

import java.util.HashMap;

public class CSSSpriteTest {

	public static void main(String[] args) {
		int[] nextEmojis = { 18, 37 };
		for (int i = 0; i < nextEmojis.length; i++) {
			int px = nextEmojis[i] % 16 * 33;
			int py = nextEmojis[i] / 16 * 33;
			System.out.println(".nj" + nextEmojis[i] + " {");
			System.out.println("\tbackground-position: " + (-px) + "px " + (-py) + "px;");
			System.out.println("}");
		}
		String emojiString = "😀中文😊😉😍😙😜😳😁English😂😭😪😥😅😓😫😱😡😤😆😋😷😎😴😵";
		// Pattern emojiPattern = Pattern.compile(
		// "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
		// Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		
		System.err.println(convert(emojiString));

	}

	public static String convert(String content) {
		StringBuilder afterConvert = new StringBuilder();
		for (int i = 0; i < content.length(); i++) {
			String preByte = Integer.toHexString(content.charAt(i));
			if (preByte.equals("d83d")) {
				preByte += Integer.toHexString(content.charAt(++i));
				afterConvert.append(EMOJI2CLASS.get(preByte));
			} else {
				afterConvert.append(content.charAt(i));
			}
		}
		return afterConvert.toString();
	}

	private static final HashMap<String, String> EMOJI2CLASS = new HashMap<String, String>(24);

	static {
		// 😀😊😉😍😙😜😳😁
		EMOJI2CLASS.put("d83dde00", "<span class=\"eq mp0 nj49\"></span>");
		EMOJI2CLASS.put("d83dde0a", "<span class=\"eq mp0 nj4\"></span>");
		EMOJI2CLASS.put("d83dde09", "<span class=\"eq mp0 nj15\"></span>");
		EMOJI2CLASS.put("d83dde0d", "<span class=\"eq mp0 nj0\"></span>");
		EMOJI2CLASS.put("d83dde19", "<span class=\"eq mp0 nj58\"></span>");
		EMOJI2CLASS.put("d83dde1c", "<span class=\"eq mp0 nj14\"></span>");
		EMOJI2CLASS.put("d83dde33", "<span class=\"eq mp0 nj6\"></span>");
		EMOJI2CLASS.put("d83dde01", "<span class=\"eq mp0 nj111\"></span>");
		// 😂😭😪😥😅😓😫😱
		EMOJI2CLASS.put("d83dde02", "<span class=\"eq mp0 nj104\"></span>");
		EMOJI2CLASS.put("d83dde2d", "<span class=\"eq mp0 nj105\"></span>");
		EMOJI2CLASS.put("d83dde2a", "<span class=\"eq mp0 nj135\"></span>");
		EMOJI2CLASS.put("d83dde25", "<span class=\"eq mp0 nj156\"></span>");
		EMOJI2CLASS.put("d83dde05", "<span class=\"eq mp0 nj120\"></span>");
		EMOJI2CLASS.put("d83dde13", "<span class=\"eq mp0 nj147\"></span>");
		EMOJI2CLASS.put("d83dde2b", "<span class=\"eq mp0 nj123\"></span>");
		EMOJI2CLASS.put("d83dde31", "<span class=\"eq mp0 nj21\"></span>");
		// 😡😤😆😋😷😎😴😵
		EMOJI2CLASS.put("d83dde21", "<span class=\"eq mp0 nj22\"></span>");
		EMOJI2CLASS.put("d83dde24", "<span class=\"eq mp0 nj23\"></span>");
		EMOJI2CLASS.put("d83dde06", "<span class=\"eq mp0 nj36\"></span>");
		EMOJI2CLASS.put("d83dde0b", "<span class=\"eq mp0 nj12\"></span>");
		EMOJI2CLASS.put("d83dde37", "<span class=\"eq mp0 nj29\"></span>");
		EMOJI2CLASS.put("d83dde0e", "<span class=\"eq mp0 nj10\"></span>");
		EMOJI2CLASS.put("d83dde34", "<span class=\"eq mp0 nj9\"></span>");
		EMOJI2CLASS.put("d83dde35", "<span class=\"eq mp0 nj60\"></span>");
	}

}

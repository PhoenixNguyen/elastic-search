package vn.onepay.utils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DinhNN
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class VnUtils {
	public static final String[] utfhoa = new String[68];
	public static final String[] utfsthuong = new String[68];
	public static final String[] utfthuong = new String[68];
	static {
		utfhoa[1] = "Á";
		utfsthuong[1] = "as";
		utfhoa[2] = "À";
		utfsthuong[2] = "af";
		utfhoa[3] = "Ả";
		utfsthuong[3] = "ar";
		utfhoa[4] = "Ã";
		utfsthuong[4] = "ax";
		utfhoa[5] = "Ạ";
		utfsthuong[5] = "aj";

		utfhoa[6] = "Ấ";
		utfsthuong[6] = "aas";
		utfhoa[7] = "Ầ";
		utfsthuong[7] = "aaf";
		utfhoa[8] = "Ẩ";
		utfsthuong[8] = "aar";
		utfhoa[9] = "Ẫ";
		utfsthuong[9] = "aax";
		utfhoa[10] = "Ậ";
		utfsthuong[10] = "aaj";

		utfhoa[11] = "Ắ";
		utfsthuong[11] = "aws";
		utfhoa[12] = "Ằ";
		utfsthuong[12] = "awf";
		utfhoa[13] = "Ẳ";
		utfsthuong[13] = "awr";
		utfhoa[14] = "Ẵ";
		utfsthuong[14] = "awx";
		utfhoa[15] = "Ặ";
		utfsthuong[15] = "awj";

		utfhoa[16] = "É";
		utfsthuong[16] = "es";
		utfhoa[17] = "È";
		utfsthuong[17] = "ef";
		utfhoa[18] = "Ẻ";
		utfsthuong[18] = "er";
		utfhoa[19] = "Ẽ";
		utfsthuong[19] = "ex";
		utfhoa[20] = "Ẹ";
		utfsthuong[20] = "ej";

		utfhoa[21] = "Ế";
		utfsthuong[21] = "ees";
		utfhoa[22] = "Ề";
		utfsthuong[22] = "eef";
		utfhoa[23] = "Ể";
		utfsthuong[23] = "eer";
		utfhoa[24] = "Ễ";
		utfsthuong[24] = "eex";
		utfhoa[25] = "Ệ";
		utfsthuong[25] = "eej";

		utfhoa[26] = "Í";
		utfsthuong[26] = "is";
		utfhoa[27] = "Ì";
		utfsthuong[27] = "if";
		utfhoa[28] = "Ỉ";
		utfsthuong[28] = "ir";
		utfhoa[29] = "Ĩ";
		utfsthuong[29] = "ix";
		utfhoa[30] = "Ị";
		utfsthuong[30] = "ij";

		utfhoa[31] = "Ó";
		utfsthuong[31] = "os";
		utfhoa[32] = "Ò";
		utfsthuong[32] = "of";
		utfhoa[33] = "Ỏ";
		utfsthuong[33] = "or";
		utfhoa[34] = "Õ";
		utfsthuong[34] = "ox";
		utfhoa[35] = "Ọ";
		utfsthuong[35] = "oj";

		utfhoa[36] = "Ố";
		utfsthuong[36] = "oos";
		utfhoa[37] = "Ồ";
		utfsthuong[37] = "oof";
		utfhoa[38] = "Ổ";
		utfsthuong[38] = "oor";
		utfhoa[39] = "Ỗ";
		utfsthuong[39] = "oox";
		utfhoa[40] = "Ộ";
		utfsthuong[40] = "ooj";

		utfhoa[41] = "Ớ";
		utfsthuong[41] = "ows";
		utfhoa[42] = "Ờ";
		utfsthuong[42] = "owf";
		utfhoa[43] = "Ở";
		utfsthuong[43] = "owr";
		utfhoa[44] = "Ỡ";
		utfsthuong[44] = "owx";
		utfhoa[45] = "Ợ";
		utfsthuong[45] = "owj";

		utfhoa[46] = "Ú";
		utfsthuong[46] = "us";
		utfhoa[47] = "Ù";
		utfsthuong[47] = "uf";
		utfhoa[48] = "Ủ";
		utfsthuong[48] = "ur";
		utfhoa[49] = "Ũ";
		utfsthuong[49] = "ux";
		utfhoa[50] = "Ụ";
		utfsthuong[50] = "uj";

		utfhoa[51] = "Ứ";
		utfsthuong[51] = "uws";
		utfhoa[52] = "Ừ";
		utfsthuong[52] = "uwf";
		utfhoa[53] = "Ử";
		utfsthuong[53] = "uwr";
		utfhoa[54] = "Ữ";
		utfsthuong[54] = "uwx";
		utfhoa[55] = "Ự";
		utfsthuong[55] = "uwj";

		utfhoa[56] = "Ý";
		utfsthuong[56] = "ys";
		utfhoa[57] = "Ỳ";
		utfsthuong[57] = "yf";
		utfhoa[58] = "Ỷ";
		utfsthuong[58] = "yr";
		utfhoa[59] = "Ỹ";
		utfsthuong[59] = "yx";
		utfhoa[60] = "Ỵ";
		utfsthuong[60] = "yj";

		utfhoa[61] = "Â";
		utfsthuong[61] = "aa";
		utfhoa[62] = "Ă";
		utfsthuong[62] = "aw";
		utfhoa[63] = "Ê";
		utfsthuong[63] = "ee";
		utfhoa[64] = "Đ";
		utfsthuong[64] = "dd";
		utfhoa[65] = "Ô";
		utfsthuong[65] = "oo";
		utfhoa[66] = "Ơ";
		utfsthuong[66] = "ow";
		utfhoa[67] = "Ư";
		utfsthuong[67] = "uw";

		utfthuong[1] = "á";
		utfthuong[2] = "à";
		utfthuong[3] = "ả";
		utfthuong[4] = "ã";
		utfthuong[5] = "ạ";

		utfthuong[6] = "ấ";
		utfthuong[7] = "ầ";
		utfthuong[8] = "ẩ";
		utfthuong[9] = "ẫ";
		utfthuong[10] = "ậ";

		utfthuong[11] = "ắ";
		utfthuong[12] = "ằ";
		utfthuong[13] = "ẳ";
		utfthuong[14] = "ẵ";
		utfthuong[15] = "ặ";

		utfthuong[16] = "é";
		utfthuong[17] = "è";
		utfthuong[18] = "ẻ";
		utfthuong[19] = "ẽ";
		utfthuong[20] = "ẹ";

		utfthuong[21] = "ế";
		utfthuong[22] = "ề";
		utfthuong[23] = "ể";
		utfthuong[24] = "ễ";
		utfthuong[25] = "ệ";

		utfthuong[26] = "í";
		utfthuong[27] = "ì";
		utfthuong[28] = "ỉ";
		utfthuong[29] = "ĩ";
		utfthuong[30] = "ị";

		utfthuong[31] = "ó";
		utfthuong[32] = "ò";
		utfthuong[33] = "ỏ";
		utfthuong[34] = "õ";
		utfthuong[35] = "ọ";

		utfthuong[36] = "ố";
		utfthuong[37] = "ồ";
		utfthuong[38] = "ổ";
		utfthuong[39] = "ỗ";
		utfthuong[40] = "ộ";

		utfthuong[41] = "ớ";
		utfthuong[42] = "ờ";
		utfthuong[43] = "ở";
		utfthuong[44] = "ỡ";
		utfthuong[45] = "ợ";

		utfthuong[46] = "ú";
		utfthuong[47] = "ù";
		utfthuong[48] = "ủ";
		utfthuong[49] = "ũ";
		utfthuong[50] = "ụ";

		utfthuong[51] = "ứ";
		utfthuong[52] = "ừ";
		utfthuong[53] = "ử";
		utfthuong[54] = "ữ";
		utfthuong[55] = "ự";

		utfthuong[56] = "ý";
		utfthuong[57] = "ỳ";
		utfthuong[58] = "ỷ";
		utfthuong[59] = "ỹ";
		utfthuong[60] = "ỵ";

		utfthuong[61] = "â";
		utfthuong[62] = "ă";
		utfthuong[63] = "ê";
		utfthuong[64] = "đ";
		utfthuong[65] = "ô";
		utfthuong[66] = "ơ";
		utfthuong[67] = "ư";
	}
	
	public static String Utf8toUniSearch(String strInput) {
		String retVal = strInput;
		for (int i = 1; i < 68; i++) {
			retVal = retVal.replace(utfhoa[i], utfsthuong[i]);
			retVal = retVal.replace(utfthuong[i], utfsthuong[i]);
		}
		return retVal;
	}

	final static HashMap replaceMap = new HashMap();

	final static Pattern p = Pattern.compile(
			"(?:[óòỏõọ][ea])|(?:[úùủũụ][êy])|(?:(?<=q)[úùủũụ]a)",
			Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	private static char[] noneToneMapping = new char[7930];
	static {
		init();
	}

	private static void init() {
		String matcher = repeat("óòỏõọ,ea,úùủũụ,êy,úùủũụ,a");
		String replacement = repeat("o,éáèàẻảẽãẹạ,u,ếýềỳểỷễỹệỵ,u,áàảãạ");
		for (int i = 0; i < matcher.length();) {
			int j = i;
			i += 2;
			replaceMap
					.put(matcher.substring(j, i), replacement.substring(j, i));
		}

		char[] find = "ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặẸẹẺẻẼẽẾếỀềỂểỄễỆệỈỉỊịỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợỤụỦủỨứỪừỬửỮữỰựỲỳỴỵỶỷỸỹ"
				.toCharArray();
		char[] nonetones = "AAAAEEEIIOOOOUUYaaaaeeeiioooouuyAaDdIiUuOoUuAaAaAaAaAaAaAaAaAaAaAaAaEeEeEeEeEeEeEeEeIiIiOoOoOoOoOoOoOoOoOoOoOoOoUuUuUuUuUuUuUuYyYyYyYy"
				.toCharArray();
		for (int i = 0; i < noneToneMapping.length; i++) {
			noneToneMapping[i] = (char) i;
		}
		for (int i = 0; i < find.length; i++) {
			noneToneMapping[find[i]] = nonetones[i];
		}

	}

	private static String repeat(String seq) {
		String[] parts = seq.split(",");
		String rs = "";

		for (int i = 0; i < parts.length;) {
			String first = parts[i++];
			String second = parts[i++];
			for (int j = 0; j < first.length(); j++) {
				for (int k = 0; k < second.length();) {
					rs += first.charAt(j);
					rs += second.charAt(k);
					rs += Character.toUpperCase(first.charAt(j));
					rs += second.charAt(k);
					rs += first.charAt(j);
					rs += Character.toUpperCase(second.charAt(k));
					rs += Character.toUpperCase(first.charAt(j));
					rs += Character.toUpperCase(second.charAt(k++));
				}
			}
		}
		return rs;
	}

	public static String normalize(String text) {
		text = Normalizer.normalize(text, Normalizer.Form.NFC);

		Matcher m = p.matcher(text);
		String out = "";
		int start = 0;
		while (m.find()) {
			out += text.substring(start, m.start());
			out += replaceMap.get(m.group());
			start = m.end();
		}
		out += text.substring(start);
		return out;
	}

	public static boolean isVietnamese(String text) {
		for (int i = text.length(); --i >= 0;) {
			if (text.charAt(i) > 127)
				return true;
		}
		return false;
	}

	public static String removeTone(String text) {
		char[] chars = text.toCharArray();
		for (int i = chars.length; --i >= 0;) {
			char c = chars[i];
			if (c < noneToneMapping.length)
				chars[i] = noneToneMapping[c];
		}
		return new String(chars);
	}

	public static String comparable(String str) {
		char[] rs = str.toCharArray();
		for (int i = 0; i < rs.length; i++) {
			char c = rs[i];
			switch (c) {
			case 'A':
				c = 'A';
				break;
			case 'a':
				c = 'A' + 1;
				break;
			case 'À':
				c = 'A' + 2;
				break;
			case 'à':
				c = 'A' + 3;
				break;
			case 'Á':
				c = 'A' + 4;
				break;
			case 'á':
				c = 'A' + 5;
				break;
			case 'Ả':
				c = 'A' + 6;
				break;
			case 'ả':
				c = 'A' + 7;
				break;
			case 'Ã':
				c = 'A' + 8;
				break;
			case 'ã':
				c = 'A' + 9;
				break;
			case 'Ạ':
				c = 'A' + 10;
				break;
			case 'ạ':
				c = 'A' + 11;
				break;
			case 'Ă':
				c = 'A' + 12;
				break;
			case 'ă':
				c = 'A' + 13;
				break;
			case 'Ằ':
				c = 'A' + 14;
				break;
			case 'ằ':
				c = 'A' + 15;
				break;
			case 'Ắ':
				c = 'A' + 16;
				break;
			case 'ắ':
				c = 'A' + 17;
				break;
			case 'Ẳ':
				c = 'A' + 18;
				break;
			case 'ẳ':
				c = 'A' + 19;
				break;
			case 'Ẵ':
				c = 'A' + 20;
				break;
			case 'ẵ':
				c = 'A' + 21;
				break;
			case 'Ặ':
				c = 'A' + 22;
				break;
			case 'ặ':
				c = 'A' + 23;
				break;
			case 'Â':
				c = 'A' + 24;
				break;
			case 'â':
				c = 'A' + 25;
				break;
			case 'Ầ':
				c = 'A' + 26;
				break;
			case 'ầ':
				c = 'A' + 27;
				break;
			case 'Ấ':
				c = 'A' + 28;
				break;
			case 'ấ':
				c = 'A' + 29;
				break;
			case 'Ẩ':
				c = 'A' + 30;
				break;
			case 'ẩ':
				c = 'A' + 31;
				break;
			case 'Ẫ':
				c = 'A' + 32;
				break;
			case 'ẫ':
				c = 'A' + 33;
				break;
			case 'Ậ':
				c = 'A' + 34;
				break;
			case 'ậ':
				c = 'A' + 35;
				break;
			case 'B':
				c = 'A' + 36;
				break;
			case 'b':
				c = 'A' + 37;
				break;
			case 'C':
				c = 'A' + 38;
				break;
			case 'c':
				c = 'A' + 39;
				break;
			case 'D':
				c = 'A' + 40;
				break;
			case 'd':
				c = 'A' + 41;
				break;
			case 'Đ':
				c = 'A' + 42;
				break;
			case 'đ':
				c = 'A' + 43;
				break;
			case 'E':
				c = 'A' + 44;
				break;
			case 'e':
				c = 'A' + 45;
				break;
			case 'È':
				c = 'A' + 46;
				break;
			case 'è':
				c = 'A' + 47;
				break;
			case 'É':
				c = 'A' + 48;
				break;
			case 'é':
				c = 'A' + 49;
				break;
			case 'Ẻ':
				c = 'A' + 50;
				break;
			case 'ẻ':
				c = 'A' + 51;
				break;
			case 'Ẽ':
				c = 'A' + 52;
				break;
			case 'ẽ':
				c = 'A' + 53;
				break;
			case 'Ẹ':
				c = 'A' + 54;
				break;
			case 'ẹ':
				c = 'A' + 55;
				break;
			case 'Ê':
				c = 'A' + 56;
				break;
			case 'ê':
				c = 'A' + 57;
				break;
			case 'Ề':
				c = 'A' + 58;
				break;
			case 'ề':
				c = 'A' + 59;
				break;
			case 'Ế':
				c = 'A' + 60;
				break;
			case 'ế':
				c = 'A' + 61;
				break;
			case 'Ể':
				c = 'A' + 62;
				break;
			case 'ể':
				c = 'A' + 63;
				break;
			case 'Ễ':
				c = 'A' + 64;
				break;
			case 'ễ':
				c = 'A' + 65;
				break;
			case 'Ệ':
				c = 'A' + 66;
				break;
			case 'ệ':
				c = 'A' + 67;
				break;
			case 'F':
				c = 'A' + 68;
				break;
			case 'f':
				c = 'A' + 69;
				break;
			case 'G':
				c = 'A' + 70;
				break;
			case 'g':
				c = 'A' + 71;
				break;
			case 'H':
				c = 'A' + 72;
				break;
			case 'h':
				c = 'A' + 73;
				break;
			case 'I':
				c = 'A' + 74;
				break;
			case 'i':
				c = 'A' + 75;
				break;
			case 'Ì':
				c = 'A' + 76;
				break;
			case 'ì':
				c = 'A' + 77;
				break;
			case 'Í':
				c = 'A' + 78;
				break;
			case 'í':
				c = 'A' + 79;
				break;
			case 'Ỉ':
				c = 'A' + 80;
				break;
			case 'ỉ':
				c = 'A' + 81;
				break;
			case 'Ĩ':
				c = 'A' + 82;
				break;
			case 'ĩ':
				c = 'A' + 83;
				break;
			case 'Ị':
				c = 'A' + 84;
				break;
			case 'ị':
				c = 'A' + 85;
				break;
			case 'J':
				c = 'A' + 86;
				break;
			case 'j':
				c = 'A' + 87;
				break;
			case 'K':
				c = 'A' + 88;
				break;
			case 'k':
				c = 'A' + 89;
				break;
			case 'L':
				c = 'A' + 90;
				break;
			case 'l':
				c = 'A' + 91;
				break;
			case 'M':
				c = 'A' + 92;
				break;
			case 'm':
				c = 'A' + 93;
				break;
			case 'N':
				c = 'A' + 94;
				break;
			case 'n':
				c = 'A' + 95;
				break;
			case 'O':
				c = 'A' + 96;
				break;
			case 'o':
				c = 'A' + 97;
				break;
			case 'Ò':
				c = 'A' + 98;
				break;
			case 'ò':
				c = 'A' + 99;
				break;
			case 'Ó':
				c = 'A' + 100;
				break;
			case 'ó':
				c = 'A' + 101;
				break;
			case 'Ỏ':
				c = 'A' + 102;
				break;
			case 'ỏ':
				c = 'A' + 103;
				break;
			case 'Õ':
				c = 'A' + 104;
				break;
			case 'õ':
				c = 'A' + 105;
				break;
			case 'Ọ':
				c = 'A' + 106;
				break;
			case 'ọ':
				c = 'A' + 107;
				break;
			case 'Ô':
				c = 'A' + 108;
				break;
			case 'ô':
				c = 'A' + 109;
				break;
			case 'Ồ':
				c = 'A' + 110;
				break;
			case 'ồ':
				c = 'A' + 111;
				break;
			case 'Ố':
				c = 'A' + 112;
				break;
			case 'ố':
				c = 'A' + 113;
				break;
			case 'Ổ':
				c = 'A' + 114;
				break;
			case 'ổ':
				c = 'A' + 115;
				break;
			case 'Ỗ':
				c = 'A' + 116;
				break;
			case 'ỗ':
				c = 'A' + 117;
				break;
			case 'Ộ':
				c = 'A' + 118;
				break;
			case 'ộ':
				c = 'A' + 119;
				break;
			case 'Ơ':
				c = 'A' + 120;
				break;
			case 'ơ':
				c = 'A' + 121;
				break;
			case 'Ờ':
				c = 'A' + 122;
				break;
			case 'ờ':
				c = 'A' + 123;
				break;
			case 'Ớ':
				c = 'A' + 124;
				break;
			case 'ớ':
				c = 'A' + 125;
				break;
			case 'Ở':
				c = 'A' + 126;
				break;
			case 'ở':
				c = 'A' + 127;
				break;
			case 'Ỡ':
				c = 'A' + 128;
				break;
			case 'ỡ':
				c = 'A' + 129;
				break;
			case 'Ợ':
				c = 'A' + 130;
				break;
			case 'ợ':
				c = 'A' + 131;
				break;
			case 'P':
				c = 'A' + 132;
				break;
			case 'p':
				c = 'A' + 133;
				break;
			case 'Q':
				c = 'A' + 134;
				break;
			case 'q':
				c = 'A' + 135;
				break;
			case 'R':
				c = 'A' + 136;
				break;
			case 'r':
				c = 'A' + 137;
				break;
			case 'S':
				c = 'A' + 138;
				break;
			case 's':
				c = 'A' + 139;
				break;
			case 'T':
				c = 'A' + 140;
				break;
			case 't':
				c = 'A' + 141;
				break;
			case 'U':
				c = 'A' + 142;
				break;
			case 'u':
				c = 'A' + 143;
				break;
			case 'Ù':
				c = 'A' + 144;
				break;
			case 'ù':
				c = 'A' + 145;
				break;
			case 'Ú':
				c = 'A' + 146;
				break;
			case 'ú':
				c = 'A' + 147;
				break;
			case 'Ủ':
				c = 'A' + 148;
				break;
			case 'ủ':
				c = 'A' + 149;
				break;
			case 'Ũ':
				c = 'A' + 150;
				break;
			case 'ũ':
				c = 'A' + 151;
				break;
			case 'Ụ':
				c = 'A' + 152;
				break;
			case 'ụ':
				c = 'A' + 153;
				break;
			case 'Ư':
				c = 'A' + 154;
				break;
			case 'ư':
				c = 'A' + 155;
				break;
			case 'Ừ':
				c = 'A' + 156;
				break;
			case 'ừ':
				c = 'A' + 157;
				break;
			case 'Ứ':
				c = 'A' + 158;
				break;
			case 'ứ':
				c = 'A' + 159;
				break;
			case 'Ử':
				c = 'A' + 160;
				break;
			case 'ử':
				c = 'A' + 161;
				break;
			case 'Ữ':
				c = 'A' + 162;
				break;
			case 'ữ':
				c = 'A' + 163;
				break;
			case 'Ự':
				c = 'A' + 164;
				break;
			case 'ự':
				c = 'A' + 165;
				break;
			case 'V':
				c = 'A' + 166;
				break;
			case 'v':
				c = 'A' + 167;
				break;
			case 'W':
				c = 'A' + 168;
				break;
			case 'w':
				c = 'A' + 169;
				break;
			case 'X':
				c = 'A' + 170;
				break;
			case 'x':
				c = 'A' + 171;
				break;
			case 'Y':
				c = 'A' + 172;
				break;
			case 'y':
				c = 'A' + 173;
				break;
			case 'Ỳ':
				c = 'A' + 174;
				break;
			case 'ỳ':
				c = 'A' + 175;
				break;
			case 'Ý':
				c = 'A' + 176;
				break;
			case 'ý':
				c = 'A' + 177;
				break;
			case 'Ỷ':
				c = 'A' + 178;
				break;
			case 'ỷ':
				c = 'A' + 179;
				break;
			case 'Ỹ':
				c = 'A' + 180;
				break;
			case 'ỹ':
				c = 'A' + 181;
				break;
			case 'Ỵ':
				c = 'A' + 182;
				break;
			case 'ỵ':
				c = 'A' + 183;
				break;
			case 'Z':
				c = 'A' + 184;
				break;
			case 'z':
				c = 'A' + 185;
				break;
			default:
				if (c > 'A')
					c += 'A' + 185;
			}
			rs[i] = c;
		}
		return new String(rs);
	}
}
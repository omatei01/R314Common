/**
 * StringUtils.java
 * 
 * 
 * @author omatei01 (5 sept. 2017)
 * 
 */
package ro.anzisoft.common.util;

import static java.lang.Thread.sleep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import javax.xml.bind.DatatypeConverter;

import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.tinylog.Logger;
import org.tinylog.Supplier;

import com.google.common.hash.Hashing;

/**
 * MUtils<br>
 * 
 * @author omatei01 (5 sept. 2017 15:57:50)
 */
public class MUtils {
	// private static final Logger LOG = LoggerFactory.getLogger(MUtils.class);
	static {
		// Logger.info("MUtils version 10 " );
		// Logger.info("Architecture: {}-{}",System.getProperty("os.arch_full"),System.getProperty("os.arch"));

	}
	private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	/**
	 * Returneaza calea curenta a app<br>
	 * Implicit level=2, adica returneaza 2 steps back: ../; level1: ./; level 0 = chiar dir <br>
	 * <p>
	 * 
	 * @param parent   clasa fata de care se determina path
	 * @param level    nivel in arbore:0, 1 sau 2
	 * @param sufixDir folderele dorita in cale
	 * @return calea completa
	 */
	public static String getLocalPath2(Class<?> parent, int level, String... sufixDir) {

		String rootPath = "";
		try {
			Class<?> cls = Class.forName(parent.getName());
			if (level == 3)
				rootPath = Paths.get(cls.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().getParent().getParent().toString();
			if (level == 2)
				rootPath = Paths.get(cls.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().getParent().toString();
			else if (level == 1)
				rootPath = Paths.get(cls.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString();
			else if (level == 0)
				rootPath = Paths.get(cls.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
		} catch (URISyntaxException | ClassNotFoundException e) {
			throw new RuntimeException("Apel incorect getLocalPath", e);
		}
		// Logger.info("Local path detected is:" + rootPath);

		if (isNullOrEmpty(rootPath))
			throw new RuntimeException("Apel incorect getLocalPath");
		StringBuilder result = new StringBuilder(rootPath);
		for (int i = 0; i < sufixDir.length; i++) {
			result.append(File.separator).append(sufixDir[i]);
		}
		// Logger.trace("getLocalPath: " + result.toString());
		return result.toString();
	}

	public static String getLocalPath(String... sufixDir) {
		// return getLocalPath(parent, 2, sufixDir);
		// System.out.println("getLocalPath:" + Paths.get("/home/anzisoft/anzi", sufixDir).toString());
		return Paths.get("/home/anzisoft/anzi", sufixDir).toString();
	}

	/**
	 * Add spații la stînga<br>
	 * [abc,5] ="__abc"<br>
	 * 
	 * @param text   string
	 * @param length lungimea finala a stringului
	 * @return string cu spatii
	 */
	public static String leftpad(String text, int length) {
		if (text.length() > length)
			return text.substring(0, length);

		String result = "";
		try {
			result = String.format("%" + length + "." + length + "s", text).substring(0, length);
		} catch (Exception e) {
			result = text.substring(0, length);
		}
		return result;
	}

	/**
	 * Add 0 la stînga <br>
	 * [abc,5] ="00abc"<br>
	 * 
	 * @param number
	 * @param length lungimea finala a stringului
	 * @return string cu spatii
	 */
	public static String leftpad0(int number, int length) {
		return String.format("%0" + length + "d", number);
	}

	/**
	 * 
	 * @param text
	 * @param length
	 * @param c
	 * @return
	 */
	public static String leftpadChar(String text, int length, char chr) {
		String result = "";
		String c = String.valueOf(chr);
		for (int i = 0; i < length; i++) {
			result = result.concat(c);
		}
		result = result.concat(text);
		return result.substring(result.length() - length, result.length());
	}

	/**
	 * Add spații la dreapta<br>
	 * abc,5 ="abc "<br>
	 * 
	 * @param s    string
	 * @param size lungimea finala a stringului
	 * @return string cu spatii
	 */
	public static String rightpad(String s, int size) {
		if (s.length() > size)
			return s.substring(0, s.length());

		String r = String.format("%-" + size + "." + size + "s", s);
		return r.substring(r.length() - size, r.length());
	}

	public static String centerpad(String s, int size) {

		if (s.length() > size)
			return s.substring(0, size);

		final StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < ((size - s.length()) / 2); i++) {
			sb.append(' ');
		}
		sb.append(s);

		while (sb.length() < size) {
			sb.append(' ');
		}
		return sb.toString();
	}

	public static String left(String s, int len) {
		if (s.length() < len)
			s = rightpad(s, len);
		return s.substring(0, len);
	}

	/**
	 * substr la dreapta <br>
	 * <p>
	 * 
	 * @param s   ex: 1234567890
	 * @param len daca e 1 - 234567890
	 * @return un substring de lungime len extras din s
	 */
	public static String right(String s, int len) {
		if (s.length() < len)
			s = leftpad(s, len);
		return s.substring(s.length() - len, s.length());
	}

	public static String repeat(char ch, int repeat) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < repeat; i++) {
			s.append(ch);
		}
		return s.toString();
	}

	public static String repeat(String rep, int repeat) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < repeat; i++) {
			s.append(rep);
		}
		return s.toString();
	}

	/**
	 * Add spații intre texte.<br>
	 * [abc,cba,12] = "abc______cba".<br>
	 * Folosit la print, mai ales.<br>
	 * 
	 * @param textLeft  textul din stinga
	 * @param textRight textul din dreapta
	 * @param length    lungimea finala a string-ului
	 * @return string cu spatii
	 */
	public static String padTwoText(String textLeft, String textRight, int length) {
		String s;

		// daca e mai mult decit se poate
		if ((textLeft.length() + textRight.length()) < length)
			s = left(textLeft.concat(" ").concat(textRight), length);
		// daca text stg e mai mare decit 1/2
		if (textLeft.length() > length / 2)
			s = textLeft.concat(leftpad(textRight, (length > textLeft.length() ? length - textLeft.length() : length / 2))); // 2020.10
		// daca text dreapta e mai mare decit 1/2
		else if (textRight.length() > length / 2)
			s = rightpad(textLeft, (length > textLeft.length() ? length - textRight.length() : length / 2)).concat(textRight);  // 2020.10

		// caz normal
		else
			s = rightpad(textLeft, length / 2).concat(leftpad(textRight, length / 2));

		return s;
	}

	public static boolean isNullOrEmpty(String str) {
		return ((str == null) || (str.trim().equals("")));
	}

	/**
	 * Forteaza un string sa fie de o anumita lungime<br>
	 * 
	 * @param par    string
	 * @param length lung dorita
	 * @return un string de lungimea ceruta
	 * 
	 */
	public static String toStringFixedLength(String par, int length) {
		if (par.length() > length)
			return par.substring(0, length);
		return rightpad(par, length);
	}

	/*	*//**
			 * Determina calea/path-ul in care se gaseste jar-ul si de aici poti determina mai departe caile necesare app<br>
			 * 
			 * @return calea de install
			 * @throws RuntimeException err grava
			 */
	/*
	 * public static String getLocalBasePath() throws RuntimeException {
	 * try {
	 * Logger.trace(
	 * "getLocalBasePath: " + Paths
	 * .get(PiPrinterService.class.getProtectionDomain().getCodeSource().getLocation().toURI())
	 * .getParent()
	 * .toString());
	 * 
	 * return Paths
	 * .get(PiPrinterService.class.getProtectionDomain().getCodeSource().getLocation().toURI())
	 * .getParent()
	 * .toString();
	 * } catch (URISyntaxException ex) {
	 * throw new RuntimeException("Unable to resolve application path");
	 * }
	 * }
	 * 
	 *//**
		 * Returneaza o cale catre dir de resurse al driver-ului!!<br>
		 * In dir de resurse al app poate sa existe pt fiecare tip de driver un dir propriu <br>
		 * 
		 * @param name numele fisierului dorit din resurse
		 * @return
		 * 
		 *//*
			 * public static String getPathResource(String nameFile, String folderName) {
			 * String rootPath = getLocalBasePath();// Thread.currentThread().getContextClassLoader().getResource( "" ).getPath();
			 * String imageTestPath = Paths.get(rootPath, folderName, nameFile).toString(); // rootPath + "/" + name;
			 * 
			 * if (!new File(imageTestPath).exists()) {
			 * Logger.getLogger(Util.class.getName()).log(Level.INFO, "nu exista fisierul:{0}", imageTestPath);
			 * return "";
			 * }
			 * 
			 * return imageTestPath;
			 * }
			 * 
			 * public static String getPathResource(String nameFile) {
			 * return getPathResource(nameFile, "");
			 * }
			 */

	/**
	 * Citeste dintr-un fiser binar, tot ce e
	 * <br>
	 * 
	 * @param f fisierul
	 * @return intreg continutul convertit in String
	 */
	public static String readFromBinaryFile(File f) {
		String result = "";
		try {
			// result = new String( Files.readAllBytes( Paths.get( f.getName() ) ) );
			try (RandomAccessFile fb = new RandomAccessFile(f, "r")) {
				byte[] b = new byte[(int) f.length()];
				fb.seek(0);
				fb.read(b);
				result = new String(b);
			}
		} catch (IOException ex) {
			// throw new PiException("Inconsistență fișiere(read bin) JE", ex);
			Logger.error(ex, "Inconsistență fișiere(read bin)");
		}

		return result;
	}

	/**
	 * Salveaza string-ul intr-un fisier binar, cu stergere in prealabil!!! <br>
	 * <p>
	 * 
	 * @param f fiserul
	 * @param s stringul care se pune in fisier
	 * 
	 */
	public static void overwriteToBinaryFile(File f, String s) {
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();

			try (RandomAccessFile fw = new RandomAccessFile(f, "rw")) {
				fw.seek(0);
				byte[] b = s.getBytes();
				fw.write(b);
			}

		} catch (IOException ex) {
			// throw new PiException("Nu pot salva serial(writeBin)", ex);
			Logger.error(ex, "Inconsistență fișiere(read bin)");
		}
	}

	public static void overwriteToBinaryFile(File f, byte b[]) {
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();

			try (RandomAccessFile fw = new RandomAccessFile(f, "rw")) {
				fw.seek(0);
				fw.write(b);
			}

		} catch (IOException ex) {
			// throw new PiException("Nu pot salva serial(writeBin)", ex);
			Logger.error(ex, "Inconsistență fișiere(read bin)");
		}
	}

	/**
	 * Citeste tot fisierul text intr-un string
	 * <br>
	 * <p>
	 * 
	 * @param f fisierul care se citeste
	 * @return continut fisier
	 * @throws IOException daca sunt pb la acces fisier
	 */
	// result = new String( Files.readAllBytes( Paths.get( f.getName() ) ) );
	public static String readFromTxtFile(File f) throws IOException {
		if (!f.exists())
			return "";
		String s = new String(Files.readAllBytes(Paths.get(f.getPath())));
		String crlf = System.getProperty("line.separator");

		// daca are entr la sf il scot!!
		s = nz(s);
		if (!isNullOrEmpty(s))
			if (s.substring(s.length() - crlf.length()).equals(crlf))
				s = s.substring(0, s.length() - crlf.length());
		return s;
	}

	/**
	 * Scrie untr-un fisier text<br>
	 * E cu suprascriere!!<br>
	 * <p>
	 * 
	 * @param f fisierul
	 * @param s continutul
	 * @throws IOException e
	 * 
	 */
	public static void overwriteToTxtFile(File f, String s) throws IOException {
		if (f.exists())
			f.delete();

		f.createNewFile();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			bw.write(s);
			bw.newLine();
		}
	}

	public static void overwriteToTxtFile(File f, String s, boolean enter) throws IOException {
		if (f.exists())
			f.delete();

		f.createNewFile();
		int i = 0;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			if (i > 0)
				bw.newLine();
			bw.write(s);
			i++;
		}
	}

	/**
	 * Size-ul unui folder<br>
	 * <p>
	 * 
	 * @param folder folder
	 * @return long cu size
	 */
	public static long getFolderSize(File folder) {
		if (!folder.exists()) {
			return 0;
		}
		long length = 0;
		File[] files = folder.listFiles();

		int count = files.length;

		for (int i = 0; i < count; i++) {
			if (files[i].isFile()) {
				length += files[i].length();
			} else {
				length += getFolderSize(files[i]);
			}
		}
		return length;
	}

	/**
	 * Sterge un folder cu tot ce e in el
	 * 
	 * @param folderToBeDeleted folderul
	 * @param inclusive         da sau nu
	 * @return da sau nu
	 */
	public static boolean deleteFolder(File folderToBeDeleted, boolean inclusive) {
		boolean result = false;
		File[] allContents = folderToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteFolder(file, true);
			}
		}
		if (inclusive)
			result = folderToBeDeleted.delete();
		else
			result = true;
		return result;
	}

	public static byte[] bytesFromInt(int[] a) {
		byte[] r = new byte[a.length];
		for (int i = 0; i < a.length; i++) {
			r[i] = (byte) (a[i] & 0xff);
		}
		return r;
	}

	public static byte[] intToByteArray(int value) {
		return new byte[] {
				(byte) (value >>> 24),
				(byte) (value >>> 16),
				(byte) (value >>> 8),
				(byte) value };
	}

	public static int intFromBytes(byte b1, byte b2) {
		byte[] arr = { b1, b2 };
		ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
		short num = wrapped.getShort(); // 1
		return num;
	}

	public static int intFromBytes(byte b1, byte b2, byte b3, byte b4) {
		byte[] arr = { b1, b2, b3, b4 };
		ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
		int num = wrapped.getInt(); // 1
		return num;
	}

	/**
	 * Get Least significant "byte" from int/short
	 * 
	 * @param value
	 * @return
	 */
	public static byte byteLSBFromInt(int value) {
		byte lsb = (byte) (value & 0xFF);           //
		return lsb;
	}

	/**
	 * Get Most significant "byte" from short
	 * 
	 * @param value
	 * @return
	 */
	public static byte byteMSBFromInt(int value) {
		byte msb = (byte) ((value & 0xFF00) >> 8);  // Most significant "byte"
		return msb;
	}

	/**
	 * get int from lsd and msb
	 * 
	 * @param lsb Least significant "byte"
	 * @param msb Most significant "byte"
	 * @return
	 */
	public static int intFromBytes2(byte lsb, byte msb) {
		// return ((msb << 8) + lsb) ;
		int i = 0;
		i |= lsb & 0xFF;
		i <<= 8;
		i |= msb & 0xFF;
		return i;
	}

	/**
	 * daca e setat un anumit bit dintr-un byte
	 *
	 * @param b   byte
	 * @param bit index bit in byte
	 *
	 * @return true daca e 1
	 */
	public static Boolean isBitSet(byte b, int bit) {
		return (b & (1 << bit)) != 0;
	}

	/**
	 * Functia normala de sleep dara fara err
	 * <br>
	 * 
	 * @param ms milisec
	 */
	public static void sleep0(long ms) {
		try {
			sleep(ms);
		} catch (InterruptedException ex) {
			Logger.error(ex, "intrerupere thread");
		}
	}

	/**
	 * Functie pentru copiere rapida fisiere<br>
	 * <p>
	 * Daca se dau mai multe fisiere source, acestea se concateneaza in dest!!!<br>
	 * 
	 * @param dest    fisierul final
	 * @param sources colectie de fisiere
	 * 
	 * @throws IOException error
	 */
	public static void copyFile(String dest, String... sources) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(dest);
			for (String sourcePath : sources) {
				is = new FileInputStream(sourcePath);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				os.flush();
				MUtils.sync();
			}
		} finally {
			is.close();
			os.close();
		}
	}

	public static void copyFileToDir(String destFolder, String sourceFilePath) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(Paths.get(destFolder, Paths.get(sourceFilePath).toFile().getName()).toFile().getAbsolutePath());
			is = new FileInputStream(sourceFilePath);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
			MUtils.sync();
		} finally {
			is.close();
			os.close();
		}
	}

	/**
	 * Muta un fisier intr-un dir <br>
	 * 
	 * @param destFolder
	 * @param sourceFilePath
	 * @throws IOException
	 */
	public static void moveFile(String destFolder, String sourceFilePath) throws IOException {
		// if (!stickMounted) throw new JposException(MfaConst.MFA_ERR_ILLEGAL, "Usb stick nu este pregatit");
		File sourceFile = Paths.get(sourceFilePath).toFile();
		// if (!sourceFile.exists()) throw new JposException(MfaConst.MFA_ERR_ILLEGAL, "Nu exista fisierul de copiat");

		String fileName = Paths.get(sourceFilePath).getFileName().toString();
		File destFile = Paths.get(destFolder, fileName).toFile();

		Files.deleteIfExists(Paths.get(destFolder, fileName));
		com.google.common.io.Files.move(sourceFile, destFile);
		MUtils.sleep0(10);

	}

	/**
	 * Rules for version: <br>
	 * Major - The “millions” place; Minor - The “thousands” place;Build - The “units” place.<br>
	 * Ex: 1002038 = 1.2.38<br>
	 * <br>
	 * <p>
	 * 
	 * @param version
	 * @return striong formatat
	 */
	public static String parseVersion(int version) {

		return String.format("%d.%d.%d", version / 1000000, (version % 1000000) / 1000, version % 1000);
	}

	public static String toHexString3(byte[] array) {
		return DatatypeConverter.printHexBinary(array).toLowerCase();
	}

	public static String toHexString2(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			hexString.append(" 0x" + ((hex.length() == 1) ? "0" : "").concat(hex));
		}

		return hexString.toString();
	}

	public static String toHexString(byte[] hex) {
		String result = "";
		for (int i = 0; i < hex.length; i++) {
			result = result.concat(" " + String.format("%02x", hex[i]));
		}
		return result;
	}

	/**
	 * se foloseste la scriere in log din driver printer
	 * 
	 * @param hex
	 * @return
	 */
	public static String debugPrinterAsciiString(byte[] hex) {
		if (Paths.get("/home/anzisoft/anzi/config/printer.debug.text").toFile().exists())
			return hexToAscii(toHexString3(hex));
		else
			return "";

	}

	private static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}

		return output.toString();
	}

	public static String toHexStringForJE(byte[] hex) {
		String result = "";
		for (int i = 0; i < hex.length; i++) {
			result = result.concat(String.format("%02x", hex[i]));
		}
		return result;
	}

	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}

	public static byte[] longToBytes(long l) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(l);
		return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(bytes);
		buffer.flip();// need flip
		return buffer.getLong();
	}

	public static byte[] shortToBytes(short s) {
		ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
		buffer.putShort(s);
		return buffer.array();
	}

	public static short bytesToShort(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
		buffer.put(bytes);
		buffer.flip();// need flip
		return buffer.getShort();
	}

	public static void sync() {
		Process p;
		try {
			p = Runtime.getRuntime().exec("sync");
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			Logger.error(e, "Eroare la sync(linux)");
		}

	}

	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}

	public static String getFilePrefix(String fileName, int len) {

		String prefix = "";
		try {
			prefix = fileName.substring(0, len);
		} catch (Exception e) {
		}
		return prefix;
	}

	public static boolean validateCif(String cif) {
		// Daca este string, elimina atributul fiscal si spatiile
		if (!isNumeric(cif)) {
			cif = cif.toUpperCase();
			if (cif.substring(0, 2).equals("RO")) {
				cif = cif.substring(2);
			}
		}

		// daca are mai mult de 10 cifre sau mai putin de 2, nu-i valid
		if (cif.length() > 10 || cif.length() < 2) {
			return false;
		}
		long cifN = Long.parseLong(cif);

		// numarul de control
		int v = 753217532;

		// extrage cifra de control
		long c1 = cifN % 10;

		cifN = (cifN / 10); // cast la int

		// executa operatiile pe cifre
		int t = 0;
		while (cifN > 0) {
			t += (cifN % 10) * (v % 10);
			cifN = (cifN / 10);
			v = (v / 10);
		}

		// aplica inmultirea cu 10 si afla modulo 11
		int c2 = t * 10 % 11;

		// daca modulo 11 este 10, atunci cifra de control este 0
		if (c2 == 10) {
			c2 = 0;
		}
		return (c1 == c2);
	}

	/**
	 * Verificare NUI
	 * 
	 * @param nui
	 * @return daca e ok sau nu
	 */
	public static boolean validateNui(String nui) {
		// algoritm cifra control NUI. Intoarce:
		// -1 - cifra control eronata
		// 0 - cifra control corecta
		int[] _ponderiCifraControl = { 7, 8, 6, 2, 1, 3, 4, 5, 9 };

		if (nui.length() != 10) {
			return false;
		}
		long val = 0;
		try {
			val = Long.parseLong(nui);
		} catch (Throwable ex) {
			return false;
		}
		long rest = 0;
		long cifraControl = 0;
		for (int i = 0; i < 9; i++) {
			rest = val % 10;
			val /= 10;
			if (rest >= 5) {
				val++;
			}
			cifraControl += rest * _ponderiCifraControl[i];
		}
		if (rest >= 5) {
			val--;
		}
		if (val != (char) (1 + (cifraControl % 9))) {
			return false;
		}
		return true;
	}

	public static boolean isNumeric(String strNum) {
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	public static int getLen(long num) {
		return Long.toString(num).length();
	}

	public static boolean executeCommand(String commandDescr, String command0, String path, boolean wait) {
		boolean result = false;
		try {
			if (isWindows) {
				// conventie
				Path fileBatPath = Paths.get(path, "exec.bat");
				Files.deleteIfExists(fileBatPath);
				overwriteToTxtFile(fileBatPath.toFile(), command0, false);
				result = shellWait(commandDescr, new String[] { fileBatPath.toString() }, Paths.get(path), wait);
			} else {
				Path fileShellPath = Paths.get(path, "exec");
				Files.deleteIfExists(fileShellPath);
				overwriteToTxtFile(fileShellPath.toFile(), "#!/usr/bin/env sh\n" + command0, false);

				if (!setFilePermission(fileShellPath))
					return false;
				result = shellWait(commandDescr, new String[] { "sh", fileShellPath.toString() }, Paths.get(path), wait);// splitCommand(command0)
			}

		} catch (IOException e) {
			Logger.error(e, "Erori la executie comenzi \n{}\n{}", commandDescr, command0);
			return false;
		}
		return result;
	}

	public static boolean executeCommand(String commandDescr, String command0, String path) {
		return executeCommand(commandDescr, command0, path, true);
	}

	public static boolean setFilePermission(Path pathToFile) {
		Set<PosixFilePermission> ownerWritable = PosixFilePermissions.fromString("rwxr-xr-x");
		// FileAttribute<?> permissions = PosixFilePermissions.asFileAttribute(ownerWritable);
		try {
			Files.setPosixFilePermissions(pathToFile, ownerWritable);
			return true;
		} catch (IOException e) {
			Logger.error(e, "Erori la setare permisiuni fisier {}", pathToFile);
			return false;
		}
	}

	public static boolean shellWait(String descrCommand, final String[] cmd, Path path, boolean wait) {

		boolean runExec = ((cmd.length == 1) && (cmd[0].contains("\"")));
		int exitCode = 1;
		if (runExec) {
			Process p;

			try {
				Logger.trace("shellWait: Runtime.getRuntime().exec {}: {} ", descrCommand, cmd[0]);
				// String[] cmd = { "sh", scriptFile };
				p = Runtime.getRuntime().exec(cmd, null, path.toFile());
				if (wait)
					exitCode = p.waitFor();
				else
					exitCode = 0;
				Logger.trace(">> shellWait: Runtime.getRuntime().exec {}: {} ", descrCommand, exitCode);
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					Logger.trace(">> shellWait: Runtime.getRuntime().exec {}: {} ", descrCommand, line);
				}
			} catch (IOException | InterruptedException e) {
				Logger.error(e, "shellWait: ProcessBuilder.start error {}", descrCommand);
				// throw new JposException(MfaConst.MFA_ERR_FAILURE, "Erori la executie script [" + descrCommand + "]\n", e);
				return false;
			}
		}

		// rulare cu ProcessBuilder
		if (!runExec) {
			try {
				Logger.trace("shellWait: ProcessBuilder.start {}: {} ", descrCommand, cmd[0]);
				ProcessBuilder pb = new ProcessBuilder(cmd);
				if (path == null) {
					// path = Paths.get(getLocalPath(MUtils.class, 1, ""));
					path = Paths.get(getLocalPath("tmp"));
				}
				Logger.trace("shellWait: ProcessBuilder.start directory {}", path);
				pb.directory(path.toFile());
				pb.inheritIO();
				Process process = pb.start();
				if (wait)
					exitCode = process.waitFor();
				else
					exitCode = 0;
				return (exitCode == 0);

			} catch (Exception e) {
				Logger.error("shellWait: ProcessBuilder.start error {}", descrCommand);
				Logger.error(e);
				return false;
			}
		}
		return (exitCode == 0);

	}

	/**
	 * formateaza un string ca in Logger; parametrii in acolade
	 * vezi si helper functions
	 * 
	 * @param message
	 * @param arguments
	 * @return
	 */
	public static String format(final String message, final Object... arguments) {
		if (arguments == null || arguments.length == 0) {
			return message;
		} else {
			StringBuilder builder = new StringBuilder(256);

			int argumentIndex = 0;
			int start = 0;
			int openBraces = 0;

			for (int index = 0; index < message.length(); ++index) {
				char character = message.charAt(index);
				if (character == '{') {
					if (openBraces++ == 0 && start < index) {
						builder.append(message, start, index);
						start = index;
					}
				} else if (character == '}' && openBraces > 0) {
					if (--openBraces == 0) {
						if (argumentIndex < arguments.length) {
							Object argument = arguments[argumentIndex++];
							if (argument instanceof Supplier) {
								argument = ((Supplier<?>) argument).get();
							}

							if (index == start + 1) {
								builder.append(argument);
							} else {
								builder.append(format0(message.substring(start + 1, index), argument));
							}
						} else {
							builder.append(message, start, index + 1);
						}

						start = index + 1;
					}
				}
			}

			if (start < message.length()) {
				builder.append(message, start, message.length());
			}

			return builder.toString();
		}
	}

	private static String format0(final String pattern, final Object argument) {
		try {
			return getFormatter(pattern, argument).format(argument);
		} catch (IllegalArgumentException ex) {
			return String.valueOf(argument);
		}
	}

	private static final DecimalFormatSymbols FORMATTER_SYMBOLS = new DecimalFormatSymbols(Locale.ENGLISH);

	private static Format getFormatter(final String pattern, final Object argument) {
		if (pattern.indexOf('|') != -1) {
			int start = pattern.indexOf('{');
			if (start >= 0 && start < pattern.lastIndexOf('}')) {
				return new ChoiceFormat(format0(pattern, new Object[] { argument }));
			} else {
				return new ChoiceFormat(pattern);
			}
		} else {
			return new DecimalFormat(pattern, FORMATTER_SYMBOLS);
		}
	}

	/**
	 * Diferenta intre doua date la nivel de perioada
	 * Atentie pt durata tr alta functie!
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param unit     doar days, month, year
	 * @return
	 */
	public static long diffPeriodToTimestamp(java.sql.Timestamp fromDate, java.sql.Timestamp toDate, ChronoUnit unit) {

		DateTime start = new DateTime(fromDate);
		DateTime end = new DateTime(toDate);

		return Days.daysBetween(start.withTimeAtStartOfDay(), end.withTimeAtStartOfDay()).getDays();

	}

	/**
	 * returneaza o err intr-un format mai friendly
	 * trebuie setat package-ul principal al app
	 * 
	 * @param throwable
	 * @return
	 */
	public static String parseStacktrace(final Throwable throwable) {
		String result = "";

		// List<String> lines = generate_$(throwable);
		List<String> lines = new LinkedList<String>();
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer));
		lines.add(writer.getBuffer().toString());

		for (String trace : lines) {
			Pattern headLinePattern = Pattern.compile("([\\w\\.]+)(:.*)?");
			Matcher headLineMatcher = headLinePattern.matcher(trace);
			if (headLineMatcher.find()) {
				result = result + headLineMatcher.group(1);
				// System.out.println("parseStacktrace Headline: " + headLineMatcher.group(1));

				if (headLineMatcher.group(2) != null) {
					String aux = headLineMatcher.group(2);
					int n = aux.lastIndexOf("Exception: ");
					if (n > 0) {
						result = result + aux.substring(0, n + 11 - 1);
						result = result + "\n" + "!\t\t" + aux.substring(n + 11).replace(". ", ". \n!\t\t");
					} else {
						result = result + "\n" + "!\t\t" + aux;
					}
					// System.out.println("parseStacktrace Optional message " + headLineMatcher.group(2));
					result = result + "\n";// + headLineMatcher.group(2) +
				}
			}

			// "at package.class.method(source.java:123)"
			Pattern tracePattern = Pattern
					.compile("\\s*at\\s+([\\w\\.$_]+)\\.([\\w$_]+)\\((.*java)?:(\\d+)\\)(\\n|\\r\\n)");
			Matcher traceMatcher = tracePattern.matcher(trace);
			List<StackTraceElement> stackTrace = new ArrayList<StackTraceElement>();
			boolean addResult = false;
			while (traceMatcher.find()) {
				addResult = false;
				String className = traceMatcher.group(1);
				String methodName = traceMatcher.group(2);
				String sourceFile = traceMatcher.group(3);
				int lineNum = Integer.parseInt(traceMatcher.group(4));
				if (className.contains("ro.synergizer.")) {
					addResult = true;
					// className = className.replace("ro.synergizer.", "..");
				}
				StackTraceElement elem = new StackTraceElement(className, methodName, sourceFile, lineNum);
				stackTrace.add(elem);
				if (addResult) {
					result = result + "!\t\t\t" + elem.toString() + "\n";
				}
			}
			// System.out.println("parseStacktrace Stack: " + result);
			// result = result + "\n" + "Stack: " + stackTrace;

		}
		return result;

	}

	/**
	 * Converts a hexadecimal string to a byte array, for challenges
	 *
	 * @param
	 * hex        A hexadecimal string to be converted
	 * @return
	 *         binary array representation of the hexadecimal String
	 */
	private byte[] fromHexString(String hex) {
		if (hex.length() % 2 != 0) {
			System.out.println("uneven length");
		}

		byte[] result = new byte[hex.length() / 2];

		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}

		return result;
	}

	/**
	 * 
	 * @param f
	 * @param s
	 */
	public static boolean searchNullEndOfFile(File f) {
		boolean result = false;

		try {
			if (f.length() < 4)
				return false;
			// result = new String( Files.readAllBytes( Paths.get( f.getName() ) ) );
			try (RandomAccessFile fb = new RandomAccessFile(f, "r")) {
				byte[] b = new byte[4];
				fb.seek((int) f.length() - 4);
				fb.read(b);
				result = isNullOrEmpty(new String(b)); // daca sunt NULL la sfirsit
			}
		} catch (IOException ex) {
			// throw new PiException("Inconsistență fișiere(read bin) JE", ex);
			Logger.error(ex, "Inconsistență fișiere(read bin)");
		}

		return result;
	}

	public static String nz(String str, String value) {
		if ((str == null) || (str.trim().equals(""))) {
			return value;
		} else {
			return str;
		}
	}

	public static String nz(String str) {
		return nz(str, "");
	}

	public static BigDecimal nz(BigDecimal n) {
		if (n == null) {
			return BigDecimal.ZERO;
		} else {
			return n;
		}
	}

	/**
	 * 
	 * org.apache.commons.codec.digest.DigestUtils.sha256Hex
	 * 
	 * @param key
	 * @return
	 */
	public static String getHash(String key) {
		return Hashing.sha256().hashString(key, StandardCharsets.UTF_8).toString();
	}

	/**
	 * formateaza frumos un size in BYTES
	 * 
	 * @param size
	 * @return
	 */
	public static String formatReadableSize(long size) {

		if (size == 0) {
			return "0.00 B";
		}
		String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		int unitIndex = (int) (Math.log10(size) / 3);
		double unitValue = 1 << (unitIndex * 10);

		String readableSize = new DecimalFormat("#,##0.#")
				.format(size / unitValue) + " " + units[unitIndex];
		// assertEquals( "12.3 KB", readableSize );
		return readableSize;
	}

	/**
	 * Apache ArrayUtils
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static byte[] arrayAddAll(final byte[] array1, final byte... array2) {
		if (array1 == null) {
			return arrayClone(array2);
		} else if (array2 == null) {
			return arrayClone(array1);
		}
		final byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * Apache ArrayUtils
	 * 
	 * @param array
	 * @return
	 */
	public static byte[] arrayClone(final byte[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}
}

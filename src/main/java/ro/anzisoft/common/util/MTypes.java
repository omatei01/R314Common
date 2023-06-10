/**
 * Types.java
 * 
 * 
 * @author omatei01 (20 iul. 2017)
 * 
 */
package ro.anzisoft.common.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.tinylog.Logger;

import com.google.common.base.Strings;

/**
 * Types
 * <p>
 * <b>Conversii de date </b>
 * <p>
 * In db tin toate sumele ca long iar data+time ca string<br>
 * Functii folosite pentru conversii in in/din sqlite:<br>
 * - money- long cu 2 zecimale<br>
 * - double for financial caclulation - long cu 5 zecimale<br>
 * - percent - long cu 4 zecimale<br>
 * - datetime - string de forma yyyy-MM-dd hh:mm<br>
 * 
 * @author omatei01 (20 iul. 2017 12:05:17)
 *
 * @version 1.0
 */
public class MTypes {
    // private static final Logger LOG = LoggerFactory.getLogger(MTypes.class);

    public static final Locale DEFAULT_LOCALE = new Locale("ro", "RO"); // new Locale("en", "EN");
    public static final String DEFAULT_UNSELECTED = "unspecified";

    public static final int SCALE_FOR_PERCENT = 4;
    public static final int PERCENT_MIN_VALUE = 0;   // 15% => 0,15 => 1500; 100% => 1,00 => 10000
    public static final int PERCENT_MAX_VALUE = 99900; // se imparte la 10000; daca se doreste max 999%

    public static final int COUNTER4_MIN_VALUE = 0; // int 16
    public static final int COUNTER4_MAX_VALUE = 9999; // pentru afisare pe 4

    public static final int COUNTER6_MIN_VALUE = 0; // int 32
    public static final int COUNTER6_MAX_VALUE = 999999; // pt afisare pe 6/poate fi si pe 8

    public static final int SCALE_FOR_MONEY = 2;

    public static final int PRICE_MIN = 0;
    public static final int PRICE_MAX = (int) (9999999.99 * 100); // currency9, int32, 9.999.999,99

    public static final long VALUE_MIN = 0;
    public static final long VALUE_MAX = (long) (999999999.99 * 100); // currency14, int64, 999.999.999,99

    public static final long TOTAL_MIN = 0; // IMPORTANT: asa se face validare la tot negativ
    public static final long TOTAL_MAX = (long) (999999999999.99 * 100);// currency14, int64, 999,999,999,999.99

    public static final int SCALE_FOR_FIN_CALCULATIONS = 5;
    public static final int SCALE_FOR_DOUBLE_STORAGE = 5;

    public static final int SCALE_FOR_QUANTITY = 3;
    public static final int QUANTITY_MIN = 0; // at la valid exclusiv
    public static final int QUANTITY_MAX = (int) (999999.999 * 1000); // int 32, max=999.999,999

    public static final String PATTERN_DATE_VIEW = "dd-MM-yyyy"; // format scurt/doar data pt afisare
    public static final String PATTERN_DATETIME_VIEW = "dd-MM-yyyy HH:mm"; // format lung/si time pt afisare
    public static final String PATTERN_DATE_DB = "yyyyMMdd"; // format scurt pt stocare/API
    public static final String PATTERN_DATETIME_DB = "yyyyMMddHHmm"; // format lung pt stocare/API
    public static final String PATTERN_DATETIME_XML = "yyyyMMddHHmmss";// format lung pt API XML Anaf
    public static final String PATTERN_DATE_DISPLAY_XML = "dd.MM.yyyy";// format lung pt API XML Anaf
    public static final String PATTERN_DATETIME_DISPLAY_XML = "dd.MM.yyyy HH:mm:ss";// format lung pt API XML Anaf
    // public static final String PATTERN_DATETIME_JPOS = "ddMMyyyyhhmm";
    public static final String PATTERN_DATE_ONLY_MONTH = "MMM-yyyy"; //
    public static final String PATTERN_DATE_ONLY_TIME = "HH:mm";

    public static final String PATTERN_DOUBLE_VIEW = "###########0.00";
    public static final String PATTERN_QUANTITY_VIEW = "###0.0##";
    public static final String PATTERN_AMOUNT_VIEW = "######0.00";
    public static final String PATTERN_AMOUNT_SEP_VIEW = "#,###,##0.00";
    public static final String PATTERN_TOTAL_VIEW = "###########0.00";
    public static final String PATTERN_TOTAL_SEP_VIEW = "###,###,###,##0.00";

    public static final String PATTERN_PERCENT_VIEW = "#0.00";

    /**
     * Convertesc suma de tip money
     * <p>
     * Ex: 1500 din db inseamna 15.00
     * 
     * @param amount
     *        e un intreg de forma 1500
     * @return un bigDecimal de forma 15.00
     */
    public static BigDecimal toMoney(long amount) {
        return toBigDecimal(amount, SCALE_FOR_MONEY);
    }

    /**
     * Convertesc suma de tip money
     * <p>
     * Ex: 15.00 inseamna 1500 in db
     * 
     * @param amount suma in bigdec
     * @return un long money dintr-un BigDecimal
     */
    public static long toLongMoney(BigDecimal amount) {
        return toLong(amount, SCALE_FOR_MONEY);
    }

    public static boolean validPrice(long val) {
        return (val >= PRICE_MIN) && (val <= PRICE_MAX);
    }

    public static boolean validPrice(BigDecimal valD) {
        long val = toLongMoney(valD);
        return (val >= PRICE_MIN) && (val <= PRICE_MAX);
    }

    public static boolean validAmount(long val) {
        return (val >= VALUE_MIN) && (val <= VALUE_MAX);
    }

    public static boolean validAmount(BigDecimal valD) {
        long val = toLongMoney(valD);
        return (val >= VALUE_MIN) && (val <= VALUE_MAX);
    }

    public static boolean validTotal(long val) {
        return (val >= TOTAL_MIN) && (val <= TOTAL_MAX);
    }

    public static boolean validTotal(BigDecimal valD) {
        long val = toLongMoney(valD);
        return (val >= TOTAL_MIN) && (val <= TOTAL_MAX);
    }

    public static String toStringMoney(long d) {
        final DecimalFormat df = new DecimalFormat(PATTERN_TOTAL_VIEW);
        return df.format(d / 100d);
    }

    public static String toStringMoney(BigDecimal d) {
        final DecimalFormat df = new DecimalFormat(PATTERN_TOTAL_VIEW);
        return df.format(d);
    }

    public static String toStringMoney(BigDecimal d, boolean sep) {
        if (sep) {
            return new DecimalFormat(PATTERN_TOTAL_SEP_VIEW).format(d);
        } else {
            return new DecimalFormat(PATTERN_TOTAL_VIEW).format(d);
        }
    }

    public static boolean validCounter4(int val) {
        return (val >= COUNTER4_MIN_VALUE) && (val <= COUNTER4_MAX_VALUE);
    }

    public static boolean validCounter6(int val) {
        return (val >= COUNTER6_MIN_VALUE) && (val <= COUNTER6_MAX_VALUE);
    }

    public static String toStringCounter4(int nr) {
        return String.format("%04d", nr);
    }

    public static String toStringCounter6(int number) {
        return String.format("%06d", number);
    }

    /**
     * Convertesc numar de tip procent
     * <p>
     * Ex: 1500 in db inseamna 0.15 adica 15%
     * 
     * @param amount suma ca 0.15
     * @return un BigDecimal dintr-un int
     */
    public static BigDecimal toPercent(int amount) {
        return toBigDecimal(amount, SCALE_FOR_PERCENT);
    }

    /**
     * `
     * Convertesc numar de tip procent
     * <p>
     * Ex: 15% adica 0.15, in db e 1500
     * 
     * @param amount procentul de ex 0.15
     * @return un int care tr impartit la 10.000; de ex 1500
     */
    public static int toIntPercent(BigDecimal amount) {
        return (int) toLong(amount, SCALE_FOR_PERCENT);
    }

    public static boolean validPercent(int val) {
        return (val >= PERCENT_MIN_VALUE) && (val <= PERCENT_MAX_VALUE);
    }

    /**
     * Returneaza un text cu procent<br>
     * de ex 9.00% <br>
     * 
     * @param d un string cu 15%
     * @return un string formatat
     */
    public static String toStringPercent(int d) {
        final DecimalFormat df = new DecimalFormat(PATTERN_PERCENT_VIEW);
        return df.format(d / 100d) + "%";
    }

    public static String toStringPercent(BigDecimal d) {
        final DecimalFormat df = new DecimalFormat(PATTERN_PERCENT_VIEW);
        return MUtils.right(df.format(d.multiply(new BigDecimal(100))) + "%", 7);
    }

    /**
     * Convertesc numar de tip cantitate
     * <p>
     * Ex: 1000 in db inseamna 1.000
     * 
     * @param amount un int * 1000
     * @return un BigDecimal dintr-un int cant
     */
    public static BigDecimal toQuantity(int amount) {
        return toBigDecimal(amount, SCALE_FOR_QUANTITY);
    }

    /**
     * Convertesc numar de tip cantitate
     * <p>
     * Ex: 1.000, in db e 1000
     * 
     * @param amount un big dec cu 3 zec
     * @return un long money dintr-un BigDecimal
     */
    public static int toLongQuantity(BigDecimal amount) {
        return (int) toLong(amount, SCALE_FOR_QUANTITY);
    }

    /**
     * Atentie: capetele sun exclusiv!! <br>
     * 
     * @param val
     * @return da sau nu
     */
    public static boolean validQuantity(int val) {
        return (val > QUANTITY_MIN) && (val < QUANTITY_MAX);
    }

    /**
     * String cu cantitatea pt afisare in app
     * <p>
     * 
     * @param d un int cu cant
     * @return un string cu cantitatea formatat pentru afisare
     */
    public static String toStringQuantity(int d) {
        final DecimalFormat df = new DecimalFormat(PATTERN_QUANTITY_VIEW);
        return df.format(d / 1000d);
    }

    public static String toStringQuantity(BigDecimal d) {
        final DecimalFormat df = new DecimalFormat(PATTERN_QUANTITY_VIEW);
        return df.format(d);
    }

    /**
     * Convertesc numar de tip double necesar pentru calcule pe 4 zecimale
     * <p>
     * Ex: in db e 152356 inseamna 15.2356
     * 
     * @param amount un long care /10000
     * @return un bigdecimal cu 4 zecimale
     */
    public static BigDecimal toDouble(long amount) {
        return toBigDecimal(amount, SCALE_FOR_DOUBLE_STORAGE);
    }

    /**
     * Convertesc numar de tip double necesar pentru calcule financiare
     * <p>
     * Ex: 15.2356 in db 152356
     * 
     * @param amount un long care /10000
     * @return un long double dintr-un BigDecimal
     */
    public static long toLongDouble(BigDecimal amount) {
        return toLong(amount, SCALE_FOR_DOUBLE_STORAGE);
    }

    public static BigDecimal roundMoney(BigDecimal aNumber) {
        return round(aNumber, SCALE_FOR_MONEY);
    }

    public static BigDecimal roundForPrecision(BigDecimal aNumber) {
        return round(aNumber, SCALE_FOR_FIN_CALCULATIONS);
    }

    public static BigDecimal roundPercent(BigDecimal aNumber) {
        return round(aNumber, SCALE_FOR_PERCENT);
    }

    public static boolean toBoolean(long i) {
        return (i > 0 ? true : false);
    }

    public static int parseBool(boolean b) {
        return (b ? 1 : 0);
    }

    /**
     * Returneaza o data java.utilDate din format intern<br>
     * Doar data fara timp<br>
     * Atentie: data trebuie tinuta intern asa:<br>
     * yyyyMMddHHmm<br>
     * <p>
     * 
     * @param dateString ddMMyyyy
     * @param pattern vezi const penrtu pattern PATTERN...
     * @return java.util.Date
     */
    public static java.util.Date toDate(String dateString, String pattern) {
        if (Strings.isNullOrEmpty(dateString))
            return null;
        final DateFormat df = new SimpleDateFormat(pattern);
        java.util.Date d = null;
        try {
            d = df.parse(dateString);
            // Date d = new Date(TimeUnit.SECONDS.toMillis(1220227200L));
        } catch (final ParseException e) {
            Logger.error("Format data gresit", e);
            // throw new JposException(JposConst.JPOS_E_ILLEGAL, "Format data greșit");
        }
        return d;
    }

    /**
     * Returneaza un date-string dintr-un util-date pe baza de pattern <br>
     * <p>
     * 
     * @param date data care se convert
     * @param pattern vezi const pentru pattern PATTERN...
     * @return sting cu data in format dorit
     */
    public static String toStringFromDate(java.util.Date date, String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Transform dintr-un string intr-un timestamp sql <br>
     * <p>
     * 
     * @param timestampString din db
     * @param pattern un pattern ; vezi const
     * @return un timestamp dintr-un string cu pattern
     */
    public static java.sql.Timestamp toTimestamp(String timestampString, String pattern) {
        if (Strings.isNullOrEmpty(timestampString))
            return null;
        final DateFormat df = new SimpleDateFormat(pattern);
        java.sql.Timestamp d = null;
        try {
            d = MTypes.toTimestampSql(df.parse(timestampString));
        } catch (final ParseException e) {
            Logger.error("Format data gresit", e);
        }
        // ystem.out.println("toTimestamp:" + timestampString);
        return d;
    }

    public static String toStringFromTimestamp(java.sql.Timestamp time, String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(time);
    }

    /**
     * Valideaza formatul de data in fc interne mfa
     * <p>
     * The date and time is passed as a string in the format “ddmmyyyyhhmm”, where:<br>
     * dd=day of the month (1 - 31)<br>
     * mm=month (1 - 12)<br>
     * yyyy=year (1997-)<br>
     * hh=hour (0-23)<br>
     * mm=minutes (0-59)<br>
     * 
     * @param date data in formatul de mai sus
     * @param pattern vezi const
     * @return da sau nu
     */
    public static boolean validDateFormat(String date, String pattern) {
        boolean r = false;

        try {
            Date dateTmp = new SimpleDateFormat(pattern).parse(date);
            r = true;
        } catch (ParseException e) {

        }
        return r;
    }

    public static String toStringCurrentDate(String pattern) {
        String r = new SimpleDateFormat(pattern).format(System.currentTimeMillis());
        return r;
    }
    // --------------------------------------------------------------------------
    //
    // HELPER FUNCTIONS
    //
    // --------------------------------------------------------------------------

    public static BigDecimal toBigDecimal(long amount, int scale) {
        final BigDecimal amountDecimal = new BigDecimal(amount);
        final BigDecimal factor = new BigDecimal(Math.pow(10, scale));

        amountDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return round(amountDecimal.divide(factor), scale);
    }

    private static long toLong(BigDecimal amount, int scale) {
        final BigDecimal factor = new BigDecimal(Math.pow(10, scale));
        return round(amount, scale).multiply(factor).longValue();
    }

    private static BigDecimal round(BigDecimal amount, int scale) {
        return amount.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    // --------------------------------------------------------------------------
    //
    // DATE & TIME - conversie intre diferitele tipuri de date: util, sql, joda, calendar etc
    //
    // --------------------------------------------------------------------------

    // --------------
    // util.Date
    // --------------
    public static java.util.Date toDateUtil(java.sql.Date sqlDate) {
        final java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return utilDate;
    }

    public static java.util.Date toDateUtil(DateTime jodaDate) {
        final java.util.Date utilDate = jodaDate.toDate();
        // Logger.debug("Common.getDateUtil(form joda):" + getDateFormat(utilDate));
        return utilDate;
    }

    public static java.util.Date toDateUtil(java.util.Calendar cal) {
        return cal.getTime();
    }

    public static java.util.Date toDateUtil(java.sql.Timestamp timeStampValue) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampValue.getTime());
        final java.util.Date utilDate = cal.getTime();

        // Logger.debug("Common.getDateUtil(form sql):" + getDateFormat(utilDate));
        return utilDate;
    }

    // --------------
    // sql.Date
    // --------------
    public static java.sql.Date toDateSql(java.util.Date utilDate) {
        final java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        // Logger.debug("Common.getDateSql(util):" + sqlDate);
        return sqlDate;
    }

    public static java.sql.Date toDateSql(DateTime jodaDate) {
        final java.sql.Date sqlDate = new java.sql.Date(jodaDate.getMillis());
        // Logger.debug("Common.getDateSql(joda):" + sqlDate + "; millis:" + jodaDate.getMillis());
        return sqlDate;
    }

    public static java.sql.Date toDateSql(java.util.Calendar cal) {
        final java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
        // Logger.debug("Common.getDateSql(util):" + sqlDate);
        return sqlDate;
    }

    public static java.sql.Date toDateSql(java.sql.Timestamp timeStampValue) {
        final java.sql.Date sqlDate = new java.sql.Date(toDateUtil(timeStampValue).getTime());
        // Logger.debug("Common.getDateSql(util):" + sqlDate);
        return sqlDate;
    }

    // --------------
    // joda.DateTime
    // --------------
    public static org.joda.time.DateTime toDateJoda(java.util.Date utilDate) {
        final DateTime dt = new DateTime(utilDate);
        // Logger.debug("Types.getDateJoda(util):" + dt);
        return dt;
    }

    public static org.joda.time.DateTime toDateJoda(java.sql.Date sqlDate) {
        // ReadableInstant rs;
        final DateTime dt = new DateTime(toDateUtil(sqlDate)); // new DateTime(LocalDate.fromDateFields(getDateUtil(sqlDate)));
        // Logger.debug("Types.getDateJoda(sql):" + dt);
        return dt;
    }

    // --------------
    // util.Calendar
    // --------------

    public static java.util.Calendar toCalendar(java.util.Date utilDate) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        return cal;
    }

    public static java.util.Calendar toCalendar(java.sql.Date sqlDate) {
        final java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return toCalendar(utilDate);
    }

    public static java.util.Calendar toCalendarUtil(java.sql.Date sqlDate, boolean resetTime) {
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        Calendar result = toCalendar(utilDate);
        if (resetTime) {
            result.set(Calendar.HOUR_OF_DAY, 0);
            result.set(Calendar.MINUTE, 0);
            result.set(Calendar.SECOND, 0);
            result.set(Calendar.MILLISECOND, 0);
        }
        return result;
    }

    public static java.util.Calendar toCalendar(DateTime jodaDate) {
        final java.util.Date utilDate = jodaDate.toDate();
        // Logger.debug("Types.getDateUtil(form joda):" + getDateFormat(utilDate));
        return toCalendar(utilDate);
    }

    // --------------
    // sql.Timestamp
    // --------------

    public static java.sql.Timestamp toTimestampSql(java.util.Calendar cal) {
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        final java.sql.Timestamp sqlDate = new java.sql.Timestamp(cal.getTime().getTime());
        return sqlDate;
    }

    public static java.sql.Timestamp toTimestampSql(java.util.Date utilDate) {
        // java.sql.Timestamp sqlDate = new java.sql.Timestamp(cal.getTime().getTime());//utilDate.getTime());
        final java.sql.Timestamp sqlDate = toTimestampSql(toCalendar(utilDate));
        return sqlDate;
    }

    public static java.sql.Timestamp toTimestampSql(DateTime jodaDate) {
        // java.sql.Timestamp sqlDate = new java.sql.Timestamp(jodaDate.getMillis());
        final java.sql.Timestamp sqlDate = toTimestampSql(toCalendar(jodaDate));
        return sqlDate;
    }

    // ----------------------------
    // Helper Methods Date
    // ----------------------------
    public static boolean isOverlapping(java.util.Date start1, java.util.Date end1, java.util.Date start2, java.util.Date end2) {
        return start1.before(end2) && start2.before(end1);
    }

    public static int getYear(java.util.Date date) {
        return MTypes.toCalendar(date).get(Calendar.YEAR);
    }

    public static int getYear(java.sql.Date date) {
        return MTypes.toCalendar(date).get(Calendar.YEAR);
    }

    public static int getCurrentYear() {
        final Date now = java.util.Calendar.getInstance().getTime();
        return MTypes.toCalendar(now).get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        final Date now = java.util.Calendar.getInstance().getTime();
        return MTypes.toCalendar(now).get(Calendar.MONTH); // atentie porneste de la 0-ianuarie
    }

    public static int getMonth(java.util.Date date) {
        return MTypes.toCalendar(date).get(Calendar.MONTH);
    }

    public static int getHour(java.util.Date date) {
        return MTypes.toCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    public static int getDay(java.util.Date date) {
        return MTypes.toCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static java.util.Date getFirstDayOfMonth(final Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date.getTime();
    }

    public static java.util.Date getFirstDayOfMonth(final java.util.Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getFirstDayOfMonth(cal);
    }

    public static java.util.Date getLastDayOfMonth(final Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, getLastDayOfMonthNumber(date));
        return date.getTime();
    }

    public static java.util.Date getLastDayOfMonth(final java.util.Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getLastDayOfMonth(cal);
    }

    public static int getLastDayOfMonthNumber(Calendar date) {
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getLastDayOfMonthNumber(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getLastDayOfMonthNumber(cal);
    }

    public static String getDayOfWeek(Calendar dateTM) {
        final String s = new SimpleDateFormat("EEE", DEFAULT_LOCALE).format(dateTM.getTime());
        return s;
    }

    public static String getDayOfWeek(Date dateTM) {
        final String s = new SimpleDateFormat("EEE", DEFAULT_LOCALE).format(dateTM.getTime());
        return s;
    }

    public static String getDayOfWeek2(Date dateTM) {
        final String s = new SimpleDateFormat("EEEE, dd/MM/yyyy", DEFAULT_LOCALE).format(dateTM.getTime()).toUpperCase();
        return s;
    }

    public static int getDateForCompareMonth(Date d) {
        final DateFormat df = new SimpleDateFormat("yyyyMM", DEFAULT_LOCALE);
        return Integer.parseInt(df.format(d));
    }

    public static String toStringFromDouble(double d, String pattern) {
        final DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    public static String toStringFromBigDecimal(BigDecimal d, String pattern) {
        final DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    /**
     * Formeaza o data din parametrii
     * 
     * @param year an
     * @param month luna
     * @param day ziua
     * @param hour ora
     * @param minute min
     * @return un date util din componente
     */
    public static java.util.Date toDateUtil(int year, int month, int day, int hour, int minute) {
        final DateTime d = new DateTime(year, month, day, hour, minute);
        return toDateUtil(d);
    }

    /**
     * Formeaza o data din parametrii
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return un date sql din componente
     */
    public static java.sql.Date toDateSql(int year, int month, int day, int hour, int minute) {
        final DateTime d = new DateTime(year, month, day, hour, minute);
        return toDateSql(d);
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());// new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public static java.util.Date getCurrentDate() {
        return new Date(System.currentTimeMillis());// Calendar.getInstance().getTime();
    }

    /**
     * Calculeaza un maxim de tip Timestamp pentru stocare in db<br>
     * De ex se fol la init unor cimpuri gen dataExpirare sau dataTerminare<br>
     * 
     * @return o data maxima
     */
    public static Timestamp getMaxTimestamp() {
        final java.util.Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2055);
        cal.set(Calendar.MONTH, 0);// jan
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    // 175301010000
    // new :20000101
    public static java.sql.Timestamp getMinTimestamp() {
        final java.util.Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 0);// jan
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        final java.sql.Timestamp sqlDate = toTimestampSql(cal);
        return sqlDate;
    }

    public static Date getDateMin() {
        return toDateUtil(getMinTimestamp());
    }

    public static Date getDateMax() {
        return toDateUtil(getMaxTimestamp());
    }

    /**
     * Returneaza un timestamp modificat in fc de parametrii<br>
     * <p>
     * 
     * @param field const gen alendar.YEAR
     * @param amount numar care se add
     * @return un timestamp dintr-un part of date
     */
    public static Timestamp getCalculateTimestamp(int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(field, amount);// Calendar.YEAR
        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    // --------------------------
    // pentru NAV
    // --------------------------

    /**
     * Returneaza o data pentru SQL cu info de timp resetate
     * Este conventia pentru Navision pentru cimpurile de tip DATE
     * 
     * @param utilDate
     * @return un timestamp pentru nav sal
     */
    public static java.sql.Timestamp NAV_getDate(java.sql.Timestamp utilDate) {
        final java.util.Calendar cal = toCalendar(utilDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // System.out.printlnprintln("hei");
        final java.sql.Timestamp sqlDate = toTimestampSql(cal);
        return sqlDate;
    }

    /**
     * Returneaza o data pentru SQL cu info de data(an, luna, zi) resetate la o data mnima
     * Este conventia pentru Navision pentru cimpurile de tip TIME
     * 
     * @param utilDate
     * @return un time pt sql nav
     */
    public static java.sql.Timestamp NAV_getTime(java.sql.Timestamp utilDate) {
        final java.util.Calendar cal = toCalendar(utilDate);
        cal.set(Calendar.YEAR, 1753);
        cal.set(Calendar.MONTH, 0); // ATENTIE: jan=0
        cal.set(Calendar.DATE, 1);

        final java.sql.Timestamp sqlDate = toTimestampSql(cal);
        return sqlDate;
    }

    /**
     * Returneaza o data NULL pentru Navision
     * E initilizata la o data minima
     * 
     * @return un null date pt nav
     */
    public static java.sql.Timestamp NAV_getNullDate() {
        final java.util.Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1753);
        cal.set(Calendar.MONTH, 0);// jan
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        final java.sql.Timestamp sqlDate = toTimestampSql(cal);
        return sqlDate;
    }

    public static java.sql.Date NAV_getMinDate() {
        final java.util.Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1753);
        cal.set(Calendar.MONTH, 0);// jan
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        final java.sql.Date sqlDate = toDateSql(cal);
        return sqlDate;
    }

    public static java.sql.Date NAV_getMaxDate() {
        final java.util.Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2100);
        cal.set(Calendar.MONTH, 0);// jan
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        final java.sql.Date sqlDate = toDateSql(cal);
        return sqlDate;
    }

    public static String NAV_getDateFormat_woTime(java.sql.Date d) {
        if (d == null)
            return "(fara)";

        if ((getYear(d) < 1800) || (getYear(d) > 2024)) {
            return "(fara)";
        }
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(d);

    }

    // ------------------------------------------------------------
    // XML ANAF
    // ------------------------------------------------------------
    /**
     * C24
     * Returneaza un IdM dupa regula:<br>
     * - NNNNNNNNNN (10 digits)<br>
     * - AAAALLDDHHMMSS <br>
     * <p>
     * 
     * @param amefFiscalId fiscal amef id - NUI
     * @return id in format anaf
     */
    public static String getAnaf_IdM(String amefFiscalId) {
        return amefFiscalId.concat(MTypes.toStringCurrentDate(PATTERN_DATETIME_XML));
    }

    public static String getAnaf_IdMZ(String amefFiscalId, int Z) {
        return amefFiscalId.concat(MTypes.toStringCurrentDate(PATTERN_DATETIME_XML)).concat(MTypes.toStringCounter4(Z));
    }

    /**
     * C28
     * Returneaza un IdM dupa regula:<br>
     * - NNNNNNNNNN (10 digits)<br>
     * - AAAALLDDHHMMSS <br>
     * -RRRR <br>
     * <p>
     * 
     * @param amefFiscalId fiscal amef id - NUI
     * @param Z ZID
     * @param dateZ
     * @return in format anaf
     */
    public static String getAnaf_IdZ(String amefFiscalId, int Z, long dateZ) {
        return amefFiscalId.concat(MTypes.toStringFromDate(new Date(dateZ), PATTERN_DATETIME_XML)).concat(MTypes.toStringCounter4(Z));
    }

    // C32
    public static String getAnaf_IdB(String amefFiscalId, int Z, int nrBon, long dateB) {
        return amefFiscalId.concat(MTypes.toStringFromDate(new Date(dateB), PATTERN_DATETIME_XML)).concat(MTypes.toStringCounter4(Z)).concat(MTypes.toStringCounter4(nrBon));
    }

    /**
     * Returneaza o data in format zz.ll.yyyy <br>
     * <p>
     * 
     * @return string cu data in format anaf
     */
    public static String getAnaf_DateDisplay() {
        return MTypes.toStringCurrentDate(PATTERN_DATE_DISPLAY_XML);
    }

    public static String getAnaf_DateTimeDisplay() {
        return MTypes.toStringCurrentDate(PATTERN_DATETIME_DISPLAY_XML);
    }

    public static String getDateStringDisplay(long timestamp) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(timestamp));
    }
}

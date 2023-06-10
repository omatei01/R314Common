package ro.anzisoft.common.notif;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.tinylog.Logger;

import ro.anzisoft.common.util.MUtils;

/**
 * <b>NotifDb</b><br>
 * 
 * @author omatei01 (2019)
 */
public class NotifDb {

    public static String DATABASE_FOLDER = "/home/anzisoft/util/notif/";
    public static String DATABASE_PATH = DATABASE_FOLDER + "r314notif.db";

    // -------------------------------------------------------------------------
    // bill pugh singleton
    // -------------------------------------------------------------------------
    private static class SingletonHelper {
        private static final NotifDb INSTANCE = new NotifDb();
    }

    public static NotifDb getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // -------------------------------------------------------------------------
    private static Jdbi jdbi;
    private static Handle conn;

    private static boolean isConnOpen = false;

    private NotifDb() {
        init();
    }

    private void init() {

        if (isConnOpen)
            return;

        final String sDriverName = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:" + DATABASE_PATH;

        try {
            Class.forName(sDriverName);
            jdbi = Jdbi.create(url);
            conn = jdbi.open();
            isConnOpen = true;

        } catch (final ClassNotFoundException ex) {
            Logger.error(ex, "DB driver not found");
        }

    }

    public Handle getHandle() {
        if (isConnOpen == false)
            return null;
        return conn;
    }

    public Jdbi getJDBI() {
        if (isConnOpen == false)
            return null;
        return jdbi;
    }

    public void closeHandle() {
        // if (connectionPool != null) connectionPool.close();
        conn.close();
        isConnOpen = false;
    }

    public boolean insertNotif(NotifBean notif) {
        boolean ok = false;
        if (isConnOpen == false)
            return ok;

        // conventie: nu fac nimic dacă nu e nik in code
        if (notif == null)
            return ok;

        String addressTo = "";
        if (notif.getAddressTo().size() > 0) {
            for (String address : notif.getAddressTo()) {
                if (!MUtils.isNullOrEmpty(addressTo))
                    addressTo = addressTo.concat(",");
                addressTo = addressTo.concat(address);
            }
        }

        String files = "";
        if (notif.getFiles().size() > 0) {
            for (String file : notif.getFiles()) {
                if (!MUtils.isNullOrEmpty(files))
                    files = files.concat(",");
                files = files.concat(file);
            }
        }

        String sqlInsert = "insert into Notif(timestamp, origin, toAddress, ccAddress, bccAddress, subject, body, files, filesCompress,whenSendDate, whenSentDate, expiryDate) "
                + "values(:timestamp, :origin, :toAddress, :ccAddress, :bccAddress, :subject, :body, :files,:filesCompress, :whenSendDate, :whenSentDate, :expiryDate)";
        int rec = conn.createUpdate(sqlInsert).bind("timestamp", notif.getTimestamp()).bind("origin", notif.getOrigin()).bind("toAddress", addressTo).bind("ccAddress", "").bind(
                "bccAddress", "").bind("subject", notif.getSubject()).bind("body", notif.getBody()).bind("files", files).bind("filesCompress", notif.isFilesCompress()).bind(
                        "whenSendDate", notif.getWhenSendDate())
                .bind(
                        "whenSentDate", notif.getWhenSentDate())
                .bind("expiryDate", notif.getExpiryDate()).execute();
        ok = (rec == 1);
        return ok;
    }
}

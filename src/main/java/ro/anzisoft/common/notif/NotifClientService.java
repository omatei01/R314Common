package ro.anzisoft.common.notif;

import java.io.IOException;
import java.nio.file.Paths;

import org.tinylog.Logger;

import ro.anzisoft.common.util.MUtils;

/**
 * <b>NotifClientService</b><br>
 * Doar salveaza notificarile in db
 * 
 * @author omatei01 (2019)
 */
public class NotifClientService {

    public static String REPO_FOLDER = "/home/anzisoft/util/notif/files";

    private static NotifDb db = NotifDb.getInstance();

    public static boolean send(NotifBean notif) {
        return db.insertNotif(notif);
    }

    public static boolean sendNow(NotifBean notif) {
        return db.insertNotif(notif);
    }

    protected static boolean copyFileInRepo(String filePath) {
        if (!Paths.get(REPO_FOLDER).toFile().isDirectory()) {
            Paths.get(REPO_FOLDER).toFile().mkdir();
        }
        if (!Paths.get(filePath).toFile().exists()) {
            Logger.error("The file not exists {}", filePath);
            return false;
        }
        try {
            MUtils.copyFileToDir(REPO_FOLDER, filePath);
        } catch (IOException e) {
            Logger.error(e, "Cannot move file: {}", filePath);
            return false;
        }
        return true;
    }
}

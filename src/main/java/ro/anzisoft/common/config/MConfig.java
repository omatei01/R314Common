/**
 * MConfig.java
 * 
 * 
 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

import org.tinylog.Level;
import org.tinylog.Logger;
import org.tinylog.configuration.Configuration;

import ro.anzisoft.common.util.MUtils;

/**
 * Gestioneaza setarile app.<br>
 * <p>
 * E fisierul de proprietati all app <br>
 * Vezi si GlobalConst, din fiecare package <br>
 * Trebuie sa existe atit fisierul normal cu configurari, cit si cel cu valorile default. <br>
 * Daca o setare nu e in config at se ia val din default. Altfel err. <br>
 * Exista un helper: patchConfig
 * 
 * Trebuie sa fie in dir config al app (nu linga).<br>
 * <br>
 * Daca e cu inject foloseste ConfigProvider}.<br>
 * Daca e fara, foloseste mecanismul de singleton cu getInstance().<br>
 * <br>
 * In plus denumirea e configurabila in fc de param din ctor.
 * <p>
 * Update: MUtils are fc de detect path local; in GlobalConst se poate baga SETTINGS
 * 
 * @author omatei01
 *
 */
public final class MConfig extends Properties {
    private static final long serialVersionUID = 1521920663026738844L;

    // -------------------------------------------------------------------------
    // bill pugh singleton
    // -------------------------------------------------------------------------
    // private static class SingletonHelper {
    // private static final MConfig INSTANCE = new MConfig();
    //
    // }

    // public static MConfig getInstance() {
    // return SingletonHelper.INSTANCE;
    // }
    // -------------------------------------------------------------------------

    // private static final Logger LOG = LoggerFactory.getLogger(MConfig.class);

    // calea catre file properties!!
    private static String propConfigPath;
    private static String propConfigDefaultPath;

    /**
     * Incarca setarile app astfel:<br>
     * - verifica daca exista setari cu val template<br>
     * - verifica daca exista setarile specifice; daca nu exista creaza unul<br>
     * - incarca setarile specifice si cele default pentru cele nespecificate<br>
     * 
     * AppConst.FOLDER_APP_CONFIG
     * 
     * @param pathProp
     * @param pathPropDefaults
     * 
     * @throws ConfigException
     */
    public MConfig(String pathProp, String pathPropDefaults, String callerVersion) {
        super();
        // LoggerConfig();

        // String propName = filePropName;//filePrefix.concat(".properties");
        // String propDefaultName = filePropDefaults;//filePrefix.concat(".default.properties");
        //
        propConfigPath = pathProp;// pathResolver.getLocalPath(folderName, propName);
        propConfigDefaultPath = pathPropDefaults;// pathResolver.getLocalPath(folderName, propDefaultName);

        File f = new File(propConfigPath);
        if (!f.exists()) {
            Logger.error("Setări lipsă ({})", propConfigPath);
            throw new RuntimeException("Fișier setări lipsă " + propConfigPath, null);
        }
        f = new File(propConfigDefaultPath);
        if (!f.exists()) {
            Logger.error("Setari default lipsă ({})", propConfigDefaultPath);
            throw new RuntimeException("Setari default lipsă  " + propConfigDefaultPath, null);
        }

        Properties defaultProps = new Properties();
        try {

            try (FileInputStream inStream = new FileInputStream(propConfigDefaultPath)) {
                defaultProps.load(inStream);
                this.defaults = defaultProps;
                // deschid stream si il inchid!!
                try (FileInputStream inStream2 = new FileInputStream(propConfigPath)) {
                    this.load(inStream2);
                }
            }
        } catch (IOException e) {
            Logger.error("Probleme acces setari");
            throw new RuntimeException("Probleme acces setari", e);
        }

        patchConfig();
    }

    public String getString(String name) throws ConfigException {
        String value = this.getProperty(name);

        if (value == null) {
            value = defaults.getProperty(name);
        }
        if (value == null) {
            throw new ConfigException(String.format("Setare lipsa:{}", name));
        }
        return value;
    }

    public boolean getBool(String name) throws ConfigException {
        String value = this.getProperty(name);

        if (value == null) {
            value = defaults.getProperty(name);
        }
        if (value == null) {
            throw new ConfigException(MUtils.format("Setare lipsa:{}", name));
        }

        return (value.equals("0") ? false : true);
    }

    public int getInt(String name) throws ConfigException {
        String value = this.getProperty(name);

        if (value == null) {
            value = defaults.getProperty(name);
        }
        if (value == null) {
            throw new ConfigException(MUtils.format("Setare lipsa:{}", name));
        }

        int valueOf = 0;
        try {
            valueOf = Integer.valueOf(value).intValue();
        } catch (NumberFormatException e) {
            throw new ConfigException(MUtils.format("Format gresit(int) setare:{}", name), e);
        }

        return valueOf;
    }

    public long getLong(String name) throws ConfigException {
        String value = this.getProperty(name);

        if (value == null) {
            value = defaults.getProperty(name);
        }
        if (value == null) {
            throw new ConfigException(MUtils.format("Setare lipsa:{}", name));
        }

        long valueOf = 0l;
        try {
            valueOf = Long.valueOf(value).longValue();
        } catch (NumberFormatException e) {
            throw new ConfigException(MUtils.format("Format gresit(long) setare lipsa:{}", name), e);
        }

        return valueOf;
    }

    public float getFloat(String name) throws ConfigException {
        String value = this.getProperty(name);

        if (value == null) {
            value = defaults.getProperty(name);
        }
        if (value == null) {
            throw new ConfigException(MUtils.format("Setare lipsa:{}", name));
        }

        float valueOf = 0f;
        try {
            valueOf = Float.valueOf(value).floatValue();
        } catch (NumberFormatException e) {
            throw new ConfigException(MUtils.format("Format gresit(long) setare lipsa:{}", name), e);
        }

        return valueOf;
    }

    public void setString(String name, String value) {
        this.setProperty(name, value);
    }

    public void setBool(String name, boolean value) {
        this.setProperty(name, String.valueOf(value));
    }

    public void setInt(String name, int value) {
        this.setProperty(name, String.valueOf(value));
    }

    public void setLong(String name, long value) {
        this.setProperty(name, String.valueOf(value));
    }

    public void setFloat(String name, float value) {
        this.setProperty(name, String.valueOf(value));
    }

    /**
     * Pentru salvare in fisier <br>
     * <p>
     * 
     * @throws ConfigException
     */
    public void store() throws ConfigException {
        // deschid si inchid un output stream
        try (FileOutputStream outStream = new FileOutputStream(propConfigPath)) {
            this.store(outStream, MUtils.format("file updated:"));
        } catch (IOException e) {
            throw new ConfigException("Probleme salvare setari", e);
        }
    }

    // aici add config noi -> le scriu direct in default!
    private void patchConfig() {
        Logger.trace(">>>>Config: patch config..{}",propConfigPath);
        try {
            String s = getString("printer.driver");
        } catch (ConfigException e) {
            Logger.debug("update config defaults");
            setDefa("printer.driver", "2");
        }
        try {
            String s = getString("printer.baud.rate");
        } catch (ConfigException e) {
            Logger.debug("update config defaults");
            setDefa("printer.baud.rate", "0");
        }
        try {
            String s = getString("scale.type");
        } catch (ConfigException e) {
            Logger.debug("update config defaults");
            setDefa("scale.type", "1");
        }

    }

    private void setDefa(String k, String v) throws ConfigException {
        this.defaults.put(k, v);
        try (FileOutputStream outStream = new FileOutputStream(propConfigDefaultPath)) {
            this.defaults.store(outStream, MUtils.format("file updated:"));
        } catch (IOException e) {
            throw new ConfigException("Probleme salvare setari", e);
        }
    }

    /*
     * // semafoare pt python -> provizoriu
     * private static final  String receiptFileName = "receipt.prt";
     * private static final  String receiptOffFileName = "receipt.off";
     * 
     * //de sters
     * public static void saveToFile(ObservableMap<Integer, LineReceipt> lines) {
     * File receiptFile = new File(MLocalPathResolver.getLocalPath(settingsPath, receiptFileName)); // new
     * StringBuilder(appRoot).append(File.separator).append(settingsPath).append(File.separator).append(settingsDefaultFile).toString());
     * if (receiptFile.exists()) {
     * receiptFile.delete();
     * Logger.info("Am sters fisierul " + receiptFile.toString());
     * }
     * 
     * BufferedWriter bw = null;
     * FileWriter fw = null;
     * try {
     * fw = new FileWriter(receiptFile.getAbsolutePath());
     * bw = new BufferedWriter(fw);
     * for (LineReceipt line : lines.values()) {
     * bw.write(line.lineFormatForPrint() + "\n");
     * }
     * 
     * } catch (IOException e) {
     * e.printStackTrace();
     * } finally {
     * try {
     * if (bw != null) bw.close();
     * if (fw != null) fw.close();
     * } catch (IOException ex) {
     * ex.printStackTrace();
     * }
     * 
     * }
     * Logger.info("Am generat fisierul " + receiptFile.toString());
     * }
     * 
     * public static boolean isPrinterOff() {
     * File offFile = new File(MLocalPathResolver.getLocalPath(settingsPath, receiptOffFileName)); // new
     * StringBuilder(appRoot).append(File.separator).append(settingsPath).append(File.separator).append(settingsDefaultFile).toString());
     * return (offFile.exists());
     * }
     */

}

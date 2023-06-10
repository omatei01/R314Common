package ro.anzisoft.common.notif;

import java.nio.file.Paths;
import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <b>NotifBean</b><br>
 * Se foloseste asa:
 * NotifBean notif =new NotifBean();
 * notif.set..
 * 
 * @author omatei01 (2019)
 */
@NoArgsConstructor
public class NotifBean {
    private @Getter long timestamp = System.currentTimeMillis();
    private @Getter @Setter String origin;
    private @Getter ArrayList<String> addressTo = new ArrayList<>();
    // private @Getter ArrayList<String> addressCc = new ArrayList<>();
    // private ArrayList<String> bccAddress = new ArrayList<>();

    private @Getter @Setter String subject;
    private @Getter @Setter String body;
    private @Getter ArrayList<String> files = new ArrayList<>(); // cai catre fisierele care se vor muta in /notif/files
    private @Getter @Setter boolean filesCompress = true;

    private @Getter @Setter long whenSendDate; // cind trebuie sa fie trimis
    private @Getter long whenSentDate; // daca sent=true reprez cind a fost trimis
    private @Getter @Setter long expiryDate; // daca sent=false at se compara currentDate cu expiryDate

    // ctor pt email
    public NotifBean(String toAddress, String subject, String body) {
        this.subject = subject;
        this.body = body;
        this.addressTo.add(toAddress);
    }

    public NotifBean(String pathToFile) {
        addAttachment(pathToFile);
    }

    public void addAddressTo(String to) {
        addressTo.add(to);
    }

    // public void addAddressCC(String to) {
    // addressCc.add(to);
    // }

    public boolean addAttachment(String pathToFile) {
        boolean ok = NotifClientService.copyFileInRepo(pathToFile);
        if (ok)
            files.add(Paths.get(pathToFile).toFile().getName());
        return ok;
    }

}

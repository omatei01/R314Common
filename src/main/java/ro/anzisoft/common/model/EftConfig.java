package ro.anzisoft.common.model;

import lombok.Data;

/**
 * E EftDb din AnziDrive
 * @author omatei01
 *
 */
@Data
public class EftConfig {
    /* amef serial*/
    String amefSerial = "";
    /* pentru legatura cu konc - valori predefinite in konc*/
    String docPlataId = "";
    /* tip eft pentru protocol */
    String eftType = "";
    /* port serial cu nume exact sau doar suffix */
    String serialPort = "";
    /* serial number al eft - optionalk*/
    String eftSerialNumber = "";
    /* daca se pot instala terminale multiple de acelasi tip */
    boolean multiple = false;
}

package ro.anzisoft.device.services;

import jpos.JposException;
import jpos.services.BaseService;

/**
 * Se determina portul cu checkSerialPort dupa care se face open!!
 * vezi si MDriverProvider cu lista de porturi ocupate
 * 
 * @author omatei01
 *
 */
public interface MEftDevice2 extends BaseService {

	/**
	 * cauta un port dupa port generic din EFT_TYPE
	 * 
	 * @return
	 * @throws JposException
	 */
	public boolean checkSerialPort() throws JposException;

	/**
	 * verifica daca un port serial raspunde la un request specific protocolului
	 * 
	 * @param serialPort
	 * @return
	 * @throws JposException
	 */
	public boolean checkSerialPort(String serialPort) throws JposException;

	/**
	 * verifica daca un port serial raspunde la un request specific protocolului inclusiv verificare de serial number
	 * 
	 * @param serialPort
	 * @param eftSerialNumber
	 * @return
	 * @throws JposException
	 */
	public boolean checkSerialPort(String serialPort, String eftSerialNumber) throws JposException;

	/**
	 * 
	 * @param portSerial
	 */
	public void open() throws JposException;

	/**
	 * face o plata pe device
	 * 
	 * @param amount
	 * @return daca e true at plata e acceptata altfel false;
	 * @throws JposException
	 *             orice eroare de protocol etc care inseamna ca plata e respinsa!!!
	 */
	public boolean makePayment(long amount) throws JposException;

	/**
	 * ID pt instanta de eft service
	 * 
	 * @return
	 */
	public String getHardwareId();

	public String getSoftwareId();

	public String getSerialPort();

	public int getLastResponseCode();

	public String getLastResponse();

	/** daca e null sau empty at e ok; altfel e inactiv si aici e motiv */
	public String getStatus();

	public boolean isOpen();

	/**
	 * Info ref la ultima vinzare
	 * e empty daca nu a fost nicio vinzare
	 * 
	 */
	public String getLastSaleResponse();

	/**
	 * executa settlement
	 * daca nu exista implementare at ret true!!
	 * 
	 * @return
	 * @throws JposException
	 */
	public boolean settlement() throws JposException;

	/**
	 * daca are implementata settlement
	 * 
	 * @return
	 * @throws JposException
	 */
	public boolean hasSettlement();
}

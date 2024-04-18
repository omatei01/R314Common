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
public interface MEftDevice3 extends MEftDevice2 {

	/**
	 * 
	 * @param amount
	 * @param transactionId optional
	 * @return
	 * @throws JposException
	 */
	public boolean preauthorization(long amount, int transactionId) throws JposException;

	/**
	 * 
	 * @param amount
	 * @param transactionId
	 * @return
	 * @throws JposException
	 */
	public boolean preauthorizationVoid() throws JposException;
	/**
	 * 
	 * @param amount
	 * @param rrn
	 * @param authCode
	 * @return
	 * @throws JposException
	 */	
	boolean preauthorizationComplete(long amount) throws JposException;

}

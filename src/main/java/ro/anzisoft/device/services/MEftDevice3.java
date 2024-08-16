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
	 * returneaza un id care se cva folosi la completion sau void
	 * daca nu reuseste da err
	 * 
	 * @param amount
	 * 
	 * @return
	 * @throws JposException
	 */
	String preauthorization(long amount) throws JposException;

	/**
	 * 
	 * @param id tranzact care se anuleaza
	 * @return
	 * @throws JposException
	 */
	void preauthorizationVoid(String id, long amount) throws JposException;

	/**
	 * 
	 * @param amount
	 * @param rrn
	 * @param authCode
	 * @return
	 * @throws JposException
	 */
	void preauthorizationComplete(String id, long amount) throws JposException;

	
	public boolean posReportInitialization() throws JposException;
	public boolean posGetPosReportRecord() throws JposException;
	
	
}

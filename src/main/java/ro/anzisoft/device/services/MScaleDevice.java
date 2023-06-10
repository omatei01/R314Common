package ro.anzisoft.device.services;

import jpos.JposException;
import jpos.services.BaseService;

/**
 * 
 * @author omatei01
 *
 */
public interface MScaleDevice extends BaseService {
	
	public int readWeight() throws JposException;

	public int readWeight(int timeout_waiting_seconds) throws JposException;

	public  void test() throws JposException;

	//nu e corect asa dar asta e 
	public void open(int port) throws JposException;

}

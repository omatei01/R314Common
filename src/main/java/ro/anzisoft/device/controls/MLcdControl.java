/**
 * 
 */
package ro.anzisoft.device.controls;

import org.tinylog.Logger;

import jpos.JposConst;
import jpos.JposException;
import jpos.services.BaseService;
import jpos.services.EventCallbacks;
import ro.anzisoft.device.services.MLcdDevice;

/**
 * Ăsta e un control care nu respectă specificațiile JavaPos.<br>
 * Interfața specifica e definite in R314Common, MDisplayInterface. <br>
 * Serviciul din R314Pi e PiLineDisplayService <br>
 * <p>
 * 
 * @author omatei01
 *
 */
public class MLcdControl extends MBaseControl implements JposConst {

	protected MLcdDevice service; // serviciu nativ -> vezi R314MFA

	public MLcdControl() {
		this.deviceControlDescription = "Anzi MDisplay Device Control";
		this.deviceControlVersion = 1000000;
	}

	// -------------------------------------------------------------------------
	// Framework Methods
	// -------------------------------------------------------------------------
	@Override
	protected EventCallbacks createEventCallbacks() {
		return null;
	}

	@Override
	protected void setDeviceService(BaseService service, int nServiceVersion) throws JposException {
		try {
			this.service = (MLcdDevice) service;
		} catch (final Exception e) {
			throw new JposException(
					JPOS_E_NOSERVICE,
					"Service does not fully implement PiLineDisplayService interface",
					e);
		}
	}

	// -------------------------------------------------------------------------
	// Specific Methods from MLineDisplayInterface
	// -------------------------------------------------------------------------
	/**
	 * @throws JposException
	 */
	public void clear() throws JposException {
		if (!this.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}
		// Perform the operation
		try {
			this.service.clear();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	public void display(String arg0, String arg1) throws JposException {
		// Make sure control is opened
		if (!this.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}
		Logger.debug("display : " + arg0);
		// Perform the operation
		try {
			service.display(arg0, arg1);
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	public int getColumnCount() {
		return service.getColumnCount();
	}

	public int getRowCount() {
		return service.getRowCount();
	}

	/**
	 * <br>
	 * <p>
	 * 
	 * @param statisticsBuffer
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void resetStatistics(String statisticsBuffer) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * <br>
	 * <p>
	 * 
	 * @param statisticsBuffer
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void retrieveStatistics(String[] statisticsBuffer) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * <br>
	 * <p>
	 * 
	 * @param statisticsBuffer
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void updateStatistics(String statisticsBuffer) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * <br>
	 * <p>
	 * 
	 * @param firmwareFileName
	 * @param result
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void compareFirmwareVersion(String firmwareFileName, int[] result) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * <br>
	 * <p>
	 * 
	 * @param firmwareFileName
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void updateFirmware(String firmwareFileName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

}

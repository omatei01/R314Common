/**
 * PiBeepControl.java
 * 
 * 
 * @author omatei01 (16 iun. 2017)
 * 
 */
package ro.anzisoft.device.controls;

import org.tinylog.Logger;

import jpos.JposConst;
import jpos.JposException;
import jpos.services.BaseService;
import jpos.services.EventCallbacks;
import ro.anzisoft.device.services.MBeepDevice;

/**
 * <b>PiBeepControl</b>
 * <p>
 * Face beep
 * <p>
 * 
 * 
 * @author omatei01 (16 iun. 2017 16:36:00)
 *
 * @version 1.0
 */
public class MBeepControl extends MBaseControl implements JposConst {

	protected MBeepDevice service; // serviciu specific

	/**
	 * CTOR
	 * 
	 * @since 1.0
	 */
	public MBeepControl() {
		this.deviceControlDescription = "Anzi Beep Device Control";
		this.deviceControlVersion = 1000000;
	}

	// -------------------------------------------------------------------------
	// Methods - common
	// -------------------------------------------------------------------------

	@Override
	protected EventCallbacks createEventCallbacks() {
		return null;
	}

	@Override
	protected void setDeviceService(BaseService service, int nServiceVersion) throws JposException {
		try {
			this.service = (MBeepDevice) service;
			Logger.debug("open successfully: " + service.getPhysicalDeviceName());
		} catch (final Exception e) {
			throw new JposException(JPOS_E_NOSERVICE, "Service does not fully implement MLineDisplayService interface", e);
		}
	}

	@Override
	public void resetStatistics(String statisticsBuffer) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void retrieveStatistics(String[] statisticsBuffer) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void updateStatistics(String statisticsBuffer) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void compareFirmwareVersion(String firmwareFileName, int[] result) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void updateFirmware(String firmwareFileName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	// -------------------------------------------------------------------------
	// Methods: Specific from MBeepInterface
	// -------------------------------------------------------------------------

	/**
	 * 
	 * @param range
	 * @param clock
	 * @param delay
	 * @param cycles
	 * @param delay_cycles
	 * @throws JposException
	 */
	public void beep(int range, int clock, int delay, int cycles, int delay_cycles) throws JposException {
		// Make sure control is opened
		if (!this.controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			this.service.beep(range, clock, delay, cycles, delay_cycles);

		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	public void beepAlert() throws JposException {
		// Make sure control is opened
		if (!this.controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			this.service.beepAlert();

		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}

	}

	public void beepInfo() throws JposException {
		// Make sure control is opened
		if (!this.controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			this.service.beepInfo();

		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	public void beepWarning() throws JposException {
		// Make sure control is opened
		if (!this.controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			this.service.beepWarning();

		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}

	}

}

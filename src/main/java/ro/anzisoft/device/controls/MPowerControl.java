package ro.anzisoft.device.controls;

import java.util.Vector;

import org.tinylog.Logger;

import jpos.BaseControl;
import jpos.JposConst;
import jpos.JposException;
import jpos.POSPowerControl114;
import jpos.events.DataEvent;
import jpos.events.DirectIOEvent;
import jpos.events.DirectIOListener;
import jpos.events.ErrorEvent;
import jpos.events.OutputCompleteEvent;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;
import jpos.services.BaseService;
import jpos.services.EventCallbacks;
import ro.anzisoft.device.services.MPowerDevice;

/**
 * Implementare POSPower compatibila cu JavaPos
 * <p>
 * <b>Functionalitati din JavaPos:</b><br>
 * <p>
 * The POSPower device class has the following capabilities:<br>
 * - Supports a command to “shut down” the system.<br>
 * - Supports a command to restart the system.<br>
 * - Supports a command to “suspend” the system.<br>
 * - Supports a command to have the system go to standby.<br>
 * - Supports accessing a power handling mechanism of the underlying operating system and hardware.<br>
 * - Informs the application if a power fail situation has occurred.<br>
 * - Informs the application about battery level.<br>
 * - Informs the application if the UPS charge state has changed.<br>
 * - Informs the application about high CPU temperature.<br>
 * - Informs the application about stopped CPU fan.<br>
 * - Informs the application if an operating system dependent enforced shutdown mechanism is processed.<br>
 * - Allows the application after saving application data locally or transferring application data to a server to shut down the POS terminal.<br>
 * - Informs the application about an initiated shutdown.
 * <p>
 * 
 * <b>Model:</b><br>
 * <p>
 * UnifiedPOS architecture segments device power into 3 states:<br>
 * •ONLINE. The device is powered on and ready for use. This is the “operational” state.<br>
 * •OFF. The device is powered off or detached from the terminal. This is a “non-operational” state.<br>
 * •OFFLINE. The device is powered on but is either not ready or not able to respond to requests. It may need to be placed online by pressing a button, or it may not be responding to terminal requests.
 * This is a “non-operational” state. In addition, one combination state is defined:<br>
 * •OFF_OFFLINE. The device is either off or offline, and the Service cannot distinguish these states. Power reporting only occurs while the device is open, claimed (if the device is exclusive-use), and enabled.<br>
 * Power reporting only occurs while the device is open, enabled and power notification is switched on.<br>
 * In a powerfail situation - that means the POSPower is in the state OFF - the POS terminal will be shut down automatically after the last application has closed the POSPower device or the time
 * specified by the EnforcedShutdownDelayTime property has been elapsed.<br>
 * A call to the shutdownPOS method will always shut down the POS terminal independent of the system power state.<br>
 * <p>
 * <b>Power Properties </b><br>
 * <p>
 * 
 * The UnifiedPOS device power reporting model adds the following common elements across all device classes.<br>
 * <p>
 * 
 * <b><i>CapPowerReporting property</i></b><br>
 * <p>
 * Identifies the reporting capabilities of the device. The UML pattern for the property is:PR_xxx : int32 { frozen }<br>
 * <p>
 * This property may be one of:<br>
 * <p>
 * •PR_NONE. The Service cannot determine the state of the device. Therefore, no power reporting is possible.<br>
 * •PR_STANDARD. The Service can determine and report two of the power states - OFF_OFFLINE (that is, off or offline) and ONLINE.
 * •PR_ADVANCED. The Service can determine and report all three power states - ONLINE, OFFLINE, and OFF.<br>
 * <p>
 * <b><i>PowerState property</i></b><br>
 * <p>
 * Maintained by the Service at the current power condition, if it can be determined. The UML pattern for the property is:PS_xxx : int32 { frozen } This property may be one of:<br>
 * <p>
 * •PS_UNKNOWN<br>
 * •PS_ONLINE<br>
 * •PS_OFF<br>
 * •PS_OFFLINE<br>
 * •PS_OFF_OFFLINE<br>
 * <p>
 * <b><i>PowerNotify property</i></b><br>
 * <p>
 * The application may set this property to enable power reporting via StatusUpdateEvents and the PowerState property.<br>
 * This property may only be changed while the device is disabled (that is, before DeviceEnabled is set to true). This restriction allows simpler implementation of power notification with no adverse effects on the application.
 * The application is either prepared to receive notifications or doesn't want them, and has no need to switch between these cases.<br>
 * <p>
 * The UML pattern for the property is: PN_xxx : int32 { frozen } This property may be one of:<br>
 * •PN_DISABLED<br>
 * •PN_ENABLED<br>
 * <p>
 * <b>Power Reporting Requirements for DeviceEnabled</b><br>
 * <p>
 * The following semantics are added to DeviceEnabled when: CapPowerReporting != PR_NONE + PowerNotify = PN_ENABLED:<br>
 * <p>
 * <b>A.</b>When the Control changes from DeviceEnabled false to true, then begin monitoring the power state:<br>
 * <p>
 * 1.If the Physical Device is ONLINE, then: PowerState = PS_ONLINE; A StatusUpdateEvent is enqueued with its Status = SUE_POWER_ONLINE.<br>
 * 2.If the Physical Device’s power state is OFF, OFFLINE, or OFF_OFFLINE, then the Service may choose to fail the enable by notifying the application with error code E_NOHARDWARE or E_OFFLINE. However,
 * if there are no other conditions that cause the enable to fail, and the Service chooses to return success for the enable, then: PowerState is set to PS_OFF, PS_OFFLINE, or PS_OFF_OFFLINE.<br>
 * <p>
 * A StatusUpdateEvent is enqueued with its Status property=SUE_POWER_OFF, SUE_POWER_OFFLINE, or SUE_POWER_OFF_OFFLINE.<br>
 * <p>
 * 
 * <b>B.</b>When the Device changes from DeviceEnabled true to false, UnifiedPOS assumes that the Device is no longer monitoring the power state and sets the value of PowerState to PS_UNKNOWN <br>
 * 
 * @author omatei01
 *
 */
public class MPowerControl extends MBaseControl implements POSPowerControl114, JposConst {
	//private static final Logger Logger = LoggerFactory.getLogger(MPowerControl.class);

	protected MPowerDevice nativeService; // serviciul nativ specific-> vezi init in
	protected Vector<DirectIOListener> directIOListeners;
	protected Vector<StatusUpdateListener> statusUpdateListeners;

	public MPowerControl() {
		// Initialize base class instance data
		deviceControlDescription = "Anzi PiPower Device Control";
		deviceControlVersion = 1;

		directIOListeners = new Vector<>();
		statusUpdateListeners = new Vector<>();
	}

	// -------------------------------------------------------------------------
	//
	// METHODS: Commons from PiBaseControl
	//
	// -------------------------------------------------------------------------
	@Override
	protected void setDeviceService(BaseService service, int nServiceVersion) throws JposException {
		if (service == null) {
			Logger.error("Apel incorect -> <service> e deja null");
			throw new JposException(JPOS_E_NOSERVICE, "(setDeviceService) APEL INCORECT -> service e null");
		}
		try {
			this.nativeService = (MPowerDevice) service;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_NOSERVICE, "Service does not fully implement POSPowerService interface", e);
		}
	}

	@Override
	protected EventCallbacks createEventCallbacks() {
		return new POSPowerCallbacks();
	}

	// --------------------------------------------------------------------------
	// Properties
	// ----------------------------------------------------------------------------

	/**
	 * Contains the type of power notification selection made by the Application.<br>
	 * It has one of the following values:<br>
	 * PN_DISABLED = The UPOS Service will not provide any power notifications to the application. No power notification StatusUpdateEvents will be fired, and PowerState may not be set.<br>
	 * PN_ENABLED = The UPOS Service will fire power notification StatusUpdateEvents and update PowerState, beginning when DeviceEnabled is set to true. The level of functionality depends upon CapPowerReporting.<br>
	 * PowerNotify may only be set while the device is disabled; that is, while DeviceEnabled is false.<br>
	 * This property is initialized to PN_DISABLED by the open method. This value provides compatibility with earlier releases.<br>
	 * 
	 * @return vezi const
	 * @throws JposException
	 *         Some possible values of the exception’s ErrorCode property are:<br>
	 *         E_ILLEGAL=One of the following occurred:<br>
	 *         1) the device is already enabled.<br>
	 *         2)PowerNotify = PN_ENABLED but CapPowerReporting = PR_NONE.<br>
	 * @since 1.0
	 */
	@Override
	public int getPowerNotify() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getPowerNotify();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void setPowerNotify(int powerNotify) throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		if (nativeService == null)
			Logger.error("(setPowerNotify)WHAT'S UP DAC???");

		// Perform the operation
		try {
			nativeService.setPowerNotify(powerNotify);
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * Identifies the current power condition of the device, if it can be determined. It has one of the following values:<br>
	 * <b><i>PS_UNKNOWN</i></b> = Cannot determine the device’s power state for one of the following reasons:<br>
	 * CapPowerReporting = PR_NONE ( the device does not support power reporting) + PowerNotify = PN_DISABLED (power notifications are disabled) + DeviceEnabled = false (Power state monitoring does not occur until the device is enabled)<br>
	 * <b><i>PS_ONLINE</i></b> = The device is powered on and ready for use. Can be returned if CapPowerReporting = PR_STANDARD or PR_ADVANCED.<br>
	 * <b><i>PS_OFF</i></b> = The device is powered off or detached from the POS terminal. Can only be returned if CapPowerReporting = PR_ADVANCED.<br>
	 * <b><i>PS_OFFLINE</i></b> = The device is powered on but is either not ready or not able to respond to requests. Can only be returned if CapPowerReporting = PR_ADVANCED.<br>
	 * <b><i> PS_OFF_OFFLINE</i></b> = The device is either off or off-line. Can only be returned if CapPowerReporting = PR_STANDARD.
	 * <p>
	 * This property is initialized to PS_UNKNOWN by the open method.<br>
	 * <p>
	 * When PowerNotify is set to enabled and DeviceEnabled is true, then this property is updated as the UnifiedPOS Service detects power condition changes.<br>
	 * 
	 * @return  vezi const
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getPowerState() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getPowerState();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * A value of 0 to 100 represents percent of battery capacity remaining. This property is initialized by the open method.<br>
	 * 
	 * @return val
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getBatteryCapacityRemaining() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getBatteryCapacityRemaining();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * If not zero, this property holds the threshold at which a PWR_SUE_BAT_CRITICAL Status Update Event is generated. The values 1 through 99 represent the percentage of the capacity remaining.<br>
	 * The value 0 indicates that Battery Critically Low reporting is not supported or is disabled.<br>
	 * This property is initialized by the open method.<br>
	 * 
	 * @return val
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getBatteryCriticallyLowThreshold() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getBatteryCriticallyLowThreshold();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void setBatteryCriticallyLowThreshold(int threshold) throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.setBatteryCriticallyLowThreshold(threshold);
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * If not zero, this property holds the threshold at which a PWR_SUE_BAT_LOW Status Update Event is generated.<br>
	 * The value 1 to 99 represents the percent capacity remaining.<br>
	 * The value 0 indicates that battery low reporting is not supported or is disabled. If variable battery low threshold is supported, setting a value between 1 and 99 sets the threshold to that value. Setting a value of zero disables battery low
	 * reporting.<br>
	 * This property is initialized by the open method Description <br>
	 * 
	 * @return val
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getBatteryLowThreshold() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getBatteryLowThreshold();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void setBatteryLowThreshold(int threshold) throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.setBatteryLowThreshold(threshold);
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * If not equal to zero the system has a built-in mechanism to shut down the POS terminal after a determined time in a power fail situation. This property contains the time in milliseconds when the system will shut down
	 * automatically after a power failure.<br>
	 * A power failure is the situation when the POS terminal is powered off or detached from the power supplying net and runs on UPS.<br>
	 * If zero no automatic shutdown is performed and the application has to call itself the shutdownPOS method.<br>
	 * Applications will be informed about an initiated automatic shutdown. This property is initialized by the open method.<br>
	 * 
	 * @return val
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getEnforcedShutdownDelayTime() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getEnforcedShutdownDelayTime();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void setEnforcedShutdownDelayTime(int delay) throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.setEnforcedShutdownDelayTime(delay);
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * This property contains the time in milliseconds for power fail intervals which will not create a power fail situation. In some countries the power has sometimes short intervals where the power supply is interrupted. Those short intervals are in the
	 * range of milliseconds up to a few seconds and are handled by batteries or other electric equipment and should not cause a power fail situation.<br>
	 * The power fail interval starts when the POS terminal is powered off or detached from the power supplying net and runs on UPS. The power fail interval ends when the POS terminal is again powered on or attached to the power supplying net. However, if
	 * the power fail interval is longer than the time specified in the PowerFailDelayTime property a power fail situation is created.<br>
	 * Usually this parameter is a configuration parameter of the underlying power management. So, the application can only read this property.<br>
	 * This property is initialized by the open method.<br>
	 * 
	 * @return val
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getPowerFailDelayTime() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getPowerFailDelayTime();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * This property holds the current power source if power source reporting is available.<br>
	 * A StatusUpdateEvent is generated each time this property is updated.<br>
	 * - PWR_SOURCE_NA = Power source reporting is not available.<br>
	 * - PWR_SOURCE_AC = The current power source is the AC line.<br>
	 * - PWR_SOURCE_BATTERY = The current power source is a system battery. This value is only presented for systems that operate normally on battery.<br>
	 * - PWR_SOURCE_BACKUP = The current power source is a backup source such as an UPS or backup battery.<br>
	 * This property is initialized by the open method.<br>
	 * 
	 * @return val
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public int getPowerSource() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getPowerSource();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	// --------------------------------------------------------------------------
	//
	// SPECIFIC METHODS
	//
	// --------------------------------------------------------------------------
	/**
	 * Call to restart the POS terminal.<br>
	 * This method will always restart the system independent of the system power state.<br>
	 * If the POSPower is claimed, only the application which claimed the device is able to restart the POS terminal.<br>
	 * Applications will be informed about an initiated restart.<br>
	 * 
	 * @throws JposException
	 *         Some possible values of the exception’s ErrorCode property are:<br>
	 *         E_ILLEGAL = This method is not supported (see the CapRestartPOS property)<br>
	 * @since 1.0
	 */
	@Override
	public void restartPOS() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.restartPOS();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * Call to shut down the POS terminal.<br>
	 * This method will always shut down the system independent of the system power state.<br>
	 * If the POSPower is claimed, only the application which claimed the device is able to shut down the POS terminal.
	 * Applications will be informed about an initiated shutdown.<br>
	 * It is recommended that in a power fail situation an application has to call this method after saving all data and
	 * setting the application to a defined state.<br>
	 * If the EnforcedShutdownDelayTime property specifies a time greater than zero and the application did not call the
	 * shutdownPOS method within the time specified in EnforcedShutdownDelayTime, the system will be shut down
	 * automatically.<br>
	 * This mechanism may be provided by an underlying operating system to prevent the battery from being emptied before
	 * the system is shut down.<br>
	 * This method is only supported if CapShutdownPOS is true.<br>
	 * 
	 * @throws JposException
	 *         E_ILLEGAL = This method is not supported (see the CapShutdownPOS property)<br>
	 * @since 1.0
	 */
	@Override
	public void shutdownPOS() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.shutdownPOS();
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * CapSuspendPOS=false
	 * 
	 * @param reason
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void standbyPOS(int reason) throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.standbyPOS(reason);
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * CapSuspendPOS
	 * <p>
	 * 
	 * @param reason
	 * @throws JposException
	 * @since 1.0
	 */
	@Override
	public void suspendPOS(int reason) throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			nativeService.suspendPOS(reason);
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	// --------------------------------------------------------------------------
	//
	// Event Listener Methods
	//
	// --------------------------------------------------------------------------

	@Override
	public void addDirectIOListener(DirectIOListener l) {
		synchronized (directIOListeners) {
			directIOListeners.addElement(l);
		}
	}

	@Override
	public void removeDirectIOListener(DirectIOListener l) {
		synchronized (directIOListeners) {
			directIOListeners.removeElement(l);
		}
	}

	@Override
	public void addStatusUpdateListener(StatusUpdateListener l) {
		synchronized (statusUpdateListeners) {
			statusUpdateListeners.addElement(l);
		}
	}

	@Override
	public void removeStatusUpdateListener(StatusUpdateListener l) {
		synchronized (statusUpdateListeners) {
			statusUpdateListeners.removeElement(l);
		}
	}

	// --------------------------------------------------------------------------
	//
	// EventCallbacks
	//
	// --------------------------------------------------------------------------

	/**
	 * 
	 * POSPowerCallbacks<br>
	 * <p>
	 * Implementeaza model evenimente JavaPos cu EventCallbacks.<br>
	 * PosPower are 2 tipuri de events: StatusUpdate si DirectIO (care nu ma intereseaza)<br>
	 * <p>
	 * <b>StatusUpdateEvent</b><br>
	 * Notifies the application when a device has detected an operation status change. This event contains the following attribute:<br>
	 * Status(int32) = category-specific status, describing the type of status change.<br>
	 * <p>
	 * Status values are:<br>
	 * PWR_SUE_PWR_SOURCE = The PowerSource property has been updated.<br>
	 * PWR_SUE_SHUTDOWN = The system will shutdown immediately.<br>
	 * PWR_SUE_RESTART = The system will restart immediately.<br>
	 * PWR_SUE_STANDBY = The system is requesting a transition to the Standby state<br>
	 * PWR_SUE_USER_STANDBY = The system is requesting a transition to the Standby state as a result of user input.<br>
	 * PWR_SUE_SUSPEND = The system is requesting a transition to the Suspend state.<br>
	 * PWR_SUE_USER_SUSPEND = The system is requesting a transition to the Suspend state as a result of user input.<br>
	 * 
	 * PWR_SUE_BAT_LOW = The system remaining battery capacity is at or below the low battery threshold and the system is operating from the battery.<br>
	 * PWR_SUE_BAT_CRITICAL = The system remaining battery capacity is at or below the critically low battery threshold and the system is operating from the battery.<br>
	 * PWR_SUE_BAT_CAPACITY_REMAINING = The BatteryCapacityRemaining property has been updated<br>
	 * 
	 * PWR_SUE_FAN_STOPPED = The CPU fan is stopped. Can be returned if CapFanAlarm is true.<br>
	 * PWR_SUE_FAN_RUNNING = The CPU fan is running. Can be returned if CapFanAlarm is true.<br>
	 * 
	 * PWR_SUE_TEMPERATURE_HIGH = The CPU is running on high temperature. Can be returned if CapHeatAlarm is true.<br>
	 * PWR_SUE_TEMPERATURE_OK = The CPU is running on normal temperature. Can be returned if CapHeatAlarm is true.<br>
	 * <br>
	 * 
	 * @author omatei01 (13 iun. 2017 12:43:14)
	 *
	 * @version 1.0
	 */
	protected class POSPowerCallbacks implements EventCallbacks {
		@Override
		public BaseControl getEventSource() {
			return MPowerControl.this;
		}

		@Override
		public void fireDataEvent(DataEvent e) {}

		@Override
		public void fireDirectIOEvent(DirectIOEvent e) {
			synchronized (MPowerControl.this.directIOListeners) {
				// deliver the event to all registered listeners
				for (int x = 0; x < MPowerControl.this.directIOListeners.size(); x++) {
					MPowerControl.this.directIOListeners.elementAt(x).directIOOccurred(e);
				}
			}
		}

		@Override
		public void fireErrorEvent(ErrorEvent e) {}

		@Override
		public void fireOutputCompleteEvent(OutputCompleteEvent e) {}

		@Override
		public void fireStatusUpdateEvent(StatusUpdateEvent e) {
			synchronized (MPowerControl.this.statusUpdateListeners) {
				// deliver the event to all registered listeners
				for (int x = 0; x < MPowerControl.this.statusUpdateListeners.size(); x++) {
					MPowerControl.this.statusUpdateListeners.elementAt(x).statusUpdateOccurred(e);
				}
			}
		}
	}

	// --------------------------------------------------------------------------
	// Capabilities
	// --------------------------------------------------------------------------

	@Override
	public boolean getCapFanAlarm() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapFanAlarm();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapHeatAlarm() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapHeatAlarm();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * Identifies the reporting capabilities of the Device.
	 * <p>
	 * It has one of the following values:
	 * <p>
	 * PR_NONE = The UnifiedPOS Service cannot determine the state of the device. Therefore, no power reporting is
	 * possible.
	 * <p>
	 * PR_STANDARD = The UnifiedPOS Service can determine and report two of the power states - OFF_OFFLINE (that is, off
	 * or offline) and ONLINE.
	 * <p>
	 * PR_ADVANCED = The UnifiedPOS Service can determine and report all three power states - OFF, OFFLINE, and ONLINE
	 * 
	 * @see jpos.POSPowerControl15#getCapPowerReporting()
	 * @see jpos.POSPowerControl15#getPowerState()
	 */
	@Override
	public int getCapPowerReporting() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapPowerReporting();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapQuickCharge() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapQuickCharge();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapShutdownPOS() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapShutdownPOS();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public int getCapUPSChargeState() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapUPSChargeState();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapStatisticsReporting() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapStatisticsReporting();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapUpdateStatistics() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapUpdateStatistics();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapBatteryCapacityRemaining() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapBatteryCapacityRemaining();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapCompareFirmwareVersion() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapCompareFirmwareVersion();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapRestartPOS() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapRestartPOS();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapStandbyPOS() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapStandbyPOS();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapSuspendPOS() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapSuspendPOS();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapUpdateFirmware() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapUpdateFirmware();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapVariableBatteryCriticallyLowThreshold() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapVariableBatteryCriticallyLowThreshold();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public boolean getCapVariableBatteryLowThreshold() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getCapVariableBatteryLowThreshold();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * (must be non-Javadoc) doar completari/chestii specifice
	 * 
	 * @see jpos.services.POSPowerService19#compareFirmwareVersion(java.lang.String, int[])
	 */
	@Override
	public void compareFirmwareVersion(String arg0, int[] arg1) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");

	}

	/**
	 * (must be non-Javadoc) doar completari/chestii specifice
	 * 
	 * @see jpos.services.POSPowerService19#updateFirmware(java.lang.String)
	 */
	@Override
	public void updateFirmware(String arg0) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");

	}

	/**
	 * (must be non-Javadoc) doar completari/chestii specifice
	 * 
	 * @see jpos.services.POSPowerService18#resetStatistics(java.lang.String)
	 */
	@Override
	public void resetStatistics(String arg0) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");

	}

	/**
	 * (must be non-Javadoc) doar completari/chestii specifice
	 * 
	 * @see jpos.services.POSPowerService18#retrieveStatistics(java.lang.String[])
	 */
	@Override
	public void retrieveStatistics(String[] arg0) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");

	}

	/**
	 * (must be non-Javadoc) doar completari/chestii specifice
	 * 
	 * @see jpos.services.POSPowerService18#updateStatistics(java.lang.String)
	 */
	@Override
	public void updateStatistics(String arg0) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");

	}

	@Override
	public boolean getQuickChargeMode() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getQuickChargeMode();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public int getQuickChargeTime() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getQuickChargeTime();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public int getUPSChargeState() throws JposException {
		// Make sure control is opened
		if (!super.controlOpen) {
			throw new JposException(JPOS_E_CLOSED, "Control not opened");
		}

		// Perform the operation
		try {
			return nativeService.getUPSChargeState();
		}
		catch (final JposException je) {
			throw je;
		}
		catch (final Exception e) {
			throw new JposException(JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

}

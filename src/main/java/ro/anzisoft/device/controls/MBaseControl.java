package ro.anzisoft.device.controls;

import jpos.BaseControl;
import jpos.JposConst;
import jpos.JposException;
import jpos.services.BaseService;
import jpos.services.EventCallbacks;

/**
 * BaseControl pentru toate device-urile<br>
 * <p>
 * Procedura de integrare a unui device in app:<br>
 * - se creaza Control cu extends PiBaseControl (in open se face legatura cu serviciul PI care a fost dezvoltat in prealabil)<br>
 * - fiecare control implementeaza propriile handlere-e pentru events, errors, power<br>
 * - fiecare control foloseste un serviciu (nativ/dependent de platforma) care implementeaza interfata specifica; acestea sunt in R314Common sau/si Jpos
 * - daca sunt implementate chestii specifice (deci in afara jpos) at ele vor fi cuprinse in interfete specifie; de ex pt BeepControl am interfata MBeep
 * 
 * Asa se foloseste un control de catre app:<br>
 * 
 * 1. Obtain a Control reference. Prepare for events if necessary.<br>
 * 2. Call the open method to instantiate a Service and link it to the Control.<br>
 * 3. Call the claim method to gain exclusive access to the Physical Device. Required for exclusive-use Devices; optional for some shareable Devices. (See “Device Sharing Model” on page 18 for more information).<br>
 * 4. Set the DeviceEnabled property to true to make the Physical Device operational. (For sharable Devices, the Device may be enabled without first claiming it.)<br>
 * <br>
 * --  Use the device.<br>
 * 4'. Set the DeviceEnabled property to false to disable the Physical Device.<br>
 * 3'. Call the release method to release exclusive access to the Physical Device.<br>
 * 2'. Call the close method to unlink the Service from the Control.<br>
 * 1'. Release events receipt if necessary. Remove the reference to the Control<br>
 * 
 * @author omatei01
 *
 */
public abstract class MBaseControl implements BaseControl {

	// --------------------------------------------------------------------------
	// Constants
	// --------------------------------------------------------------------------

	protected static final int deviceVersion = 1000000; // major(3).minor(2).build(2)

	// --------------------------------------------------------------------------
	// Variables
	// --------------------------------------------------------------------------

	protected BaseService baseService; // se init in abstract setDeviceService din implementarea specifica cind controlOpen=true

	// Instance Data Set in Derived Class
	protected String deviceControlDescription;
	protected int deviceControlVersion;

	// Instance Data Set in Base Class
	protected boolean controlOpen;
	protected int serviceVersion;

	//--------------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------------
	public MBaseControl() {
		// Initialize instance data. Initializations are commented out for efficiency if the Java
		// default is correct.
		// serviceVersion = 0;
		// bOpen = false;
	}

	private MServiceProviderInterface provider = null;

	public void setServiceProvider(MServiceProviderInterface pProvider) {
		this.provider = pProvider;
	}
	// --------------------------------------------------------------------------
	//
	// Framework Methods
	//
	// --------------------------------------------------------------------------

	/**
	 * Create an EventCallbacks interface implementation object for this Control<br>
	 * <p>
	 * Doar pentru cele cu evenimente gen PiPowerControl!!<br>
	 * <p>
	 * 
	 * @return ec
	 * @since 1.0
	 */
	abstract protected EventCallbacks createEventCallbacks();

	/**
	 * Asa se face legatura intre control si serviciul nativ<br>
	 * <p>
	 * Store the reference to the Device Service f.f.imp<br>
	 * <p>
	 * 
	 * @param service s
	 * @param nServiceVersion n 
	 * @throws JposException e
	 * @since 1.0
	 */
	abstract protected void setDeviceService(BaseService service, int nServiceVersion) throws JposException;

	/**
	 * Opens a device for subsequent I/O.
	 * <p>
	 * The device name specifies which of one or more devices supported by this UnifiedPOS Control should be used.<br>
	 * The logicalDeviceName must exist in the operating system’s reference locater system (such as the JavaPOS Configurator/Loader (JCL) or the Window’s Registry) for this device category so that its relationship to the physical device can be
	 * determined.<br>
	 * <p>
	 * Entries in the reference locator’s system are created by a setup or configuration utility<br>
	 * <p>
	 * When this method is successful, it initializes the properties Claimed, DeviceEnabled, DataEventEnabled, and FreezeEvents, as well as descriptions and version numbers of the UnifiedPOS software layers. Additional
	 * category-specific properties may also be initialized.<br>
	 * <p>
	 * 
	 * @param logicalDeviceName specifies the device name to open
	 * @throws JposException Some possible values of the exception’s ErrorCode property are:<br>
	 *         E_ILLEGAL = The UnifiedPOS Control is already open.<br>
	 *         E_NOEXIST = The specified logicalDeviceName was not found.<br>
	 *         E_NOSERVICE = Could not establish a connection to the corresponding UnifiedPOS Service.<br>
	 * 
	 * @since 1.0
	 */
	@Override
	public void open(String logicalDeviceName) throws JposException {
		// Make sure the control is not already open
		if (controlOpen) { throw new JposException(JposConst.JPOS_E_ILLEGAL, "Device Control already open"); }

		// Get an instance of the BaseService interface
		try {
			switch (logicalDeviceName) {
				case "PiPosPower":
					baseService = provider.getPowerService();//new PowerPiService();
					break;
				case "PiLineDisplay":
					baseService = provider.getLcdService();// new LcdPiService();
					break;
				case "PiBeepDevice":
					baseService = provider.getBeepService();//new BeepPiService();
					break;
				case "PiFiscalDevice":
					baseService = provider.getFiscalService();//new BeepPiService();
					break;

				default:
					throw new JposException(JposConst.JPOS_E_NOSERVICE, "Could not get service instance");
			}

		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_NOSERVICE, "Could not get service instance", e);
		}
		// boolean bRealOpenSucceeded = false;
		// Create callback subclass and attach it to the device service
		final EventCallbacks callbacks = createEventCallbacks();
		baseService.open(logicalDeviceName, callbacks);

		// If we got this far, the real open call succeeded
		// bRealOpenSucceeded = true;

		// If the open succeeds, remember the service instance and determine the actual service version
		serviceVersion = baseService.getDeviceServiceVersion();
		setDeviceService(baseService, serviceVersion);

		// If everything worked to this point, the open has succeeded.
		controlOpen = true;

		// If the overall open failed, clean up then throw the specified exception
		if (!controlOpen) {
			// If the Device Service open call succeeded, close it
			baseService.close();
			serviceVersion = 0;
			// Now that we've cleaned up, throw the exception that caused all this
			throw new JposException(JposConst.JPOS_E_ILLEGAL, "Problem at open control");
		}
	}

	//--------------------------------------------------------------------------
	// Methods
	//--------------------------------------------------------------------------
	/**
	 * Tests the state of a device.
	 * <p>
	 * A text description of the results of this method is placed in the CheckHealthText property.
	 * <p>
	 * The health of many devices can only be determined by a visual inspection of these test results.
	 * <p>
	 * This method is always synchronous.
	 * <p>
	 * 
	 * @param level indicates the type of health check to be performed on the device<br>
	 *        CH_INTERNAL = Perform a health check that does not physically change the device. The device is tested by internal tests to the extent possible.<br>
	 *        CH_EXTERNAL = Perform a more thorough test that may change the device. For example, a pattern may be printed on the printer.<br>
	 *        CH_INTERACTIVE = Perform an interactive test of the device. The supporting UnifiedPOS Service will typically display a modal dialog box to present test options and results.<br>
	 * 
	 * 
	 * @throws JposException E_ILLEGAL = The specified health check level is not supported by the UnifiedPOS Service.
	 * @since 1.0
	 */
	@Override
	public void checkHealth(int level) throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			baseService.checkHealth(level);
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void claim(int timeout) throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			baseService.claim(timeout);
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * Releases the device and its resources.
	 * <p>
	 * If the DeviceEnabled property is true, then the device is disabled.
	 * <p>
	 * If the Claimed property is true, then exclusive access to the device is released
	 * 
	 * @see jpos.BaseControl#close()
	 */
	@Override
	public synchronized void close() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }
		// Perform the operation
		try {
			baseService.close();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		} finally {
			setDeviceService(null, 0);
			baseService = null;
			serviceVersion = 0;
			controlOpen = false;
		}
	}

	/**
	 * Communicates directly with the UPOS Service.
	 * <p>
	 * This method provides a means for a UnifiedPOS Service to provide functionality to the application that is not otherwise supported by the standard UnifiedPOS Control for its device category
	 * <p>
	 * Depending upon the UnifiedPOS Service’s definition of the command, this method may be asynchronous or synchronous.
	 * <p>
	 * Use of this method will make an application non-portable.
	 * <p>
	 * The application may, however, maintain portability by performing directIO calls within conditional code. This
	 * code may be based upon the value of the DeviceServiceDescription, PhysicalDeviceDescription, or PhysicalDeviceName property.
	 * <p>
	 * 
	 * @see jpos.BaseControl#directIO(int, int[], java.lang.Object)
	 */
	@Override
	public void directIO(int command, int[] data, Object object) throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			baseService.directIO(command, data, object);
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * Releases exclusive access to the device.<br>
	 * If the DeviceEnabled=true, and the device is an exclusive-use device, then the device is also disabled (this method does not change the device enabled state of sharable devices).<br>
	 * <br>
	 * <p>
	 * 
	 * @throws JposException E_ILLEGAL = The application does not have exclusive access to the device.
	 * @since 1.0
	 * @see jpos.BaseControl#release()
	 */
	@Override
	public void release() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		// Perform the operation
		try {
			baseService.release();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	//--------------------------------------------------------------------------
	// Properties
	//--------------------------------------------------------------------------
	/**
	 * Holds the current state of the Device.<br>
	 * It has one of the following values:<br>
	 * <p>
	 * S_CLOSED = The Device is closed.<br>
	 * S_IDLE = The Device is in a good state and is not busy.<br>
	 * S_BUSY = The Device is in a good state and is busy performing output.<br>
	 * S_ERROR = An error has been reported, and the application must recover the Device to a good state before normal<br>
	 * I/O can resume.<br>
	 * <p>
	 * This property is always readable.<br>
	 */
	@Override
	public int getState() {
		// Preset result to JposConst.JPOS_S_CLOSED
		int nState = JposConst.JPOS_S_CLOSED;

		// If control is opened, get state from Device Service
		if (controlOpen) {
			try {
				nState = baseService.getState();
			} catch (final Exception e) {}
		}

		return nState;
	}

	/**
	 * If true, the device is claimed for exclusive access. If false, the device is released for sharing with other applications
	 *
	 * @see jpos.BaseControl#getClaimed()
	 */
	@Override
	public boolean getClaimed() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getClaimed();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * If true, the device is in an operational state.
	 * <p>
	 * If changed to true, then the device is brought to an operational state.
	 * <p>
	 * If false, the device has been disabled.
	 * <p>
	 * If changed to false, then the device is physically disabled when possible, any subsequent input will be
	 * discarded, and output operations are disallowed.
	 * <p>
	 * Changing this property usually does not physically affect output devices. For consistency, however, the
	 * application must set this property to true before using output devices.
	 * <p>
	 * The Device’s power state may be reported while DeviceEnabled is true; See “Device Power Reporting Model" on page
	 * Intro-26 for details.
	 * <p>
	 * This property is initialized to false by the open method.
	 * <p>
	 * Note that an exclusive use device must be claimed before the device may be enabled.
	 * <p>
	 * 
	 * @see jpos.BaseControl#getDeviceEnabled()
	 */
	@Override
	public boolean getDeviceEnabled() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getDeviceEnabled();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void setDeviceEnabled(boolean deviceEnabled) throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			baseService.setDeviceEnabled(deviceEnabled);
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * If true, the UnifiedPOS Control will not deliver events. Events will be enqueued until this property is set to false.
	 * <p>
	 * If false, the application allows events to be delivered.
	 * <p>
	 * If some events have been held while events were frozen and all other conditions are correct for delivering the events, then changing this property to false will allow these events to be delivered. An application may choose
	 * to freeze events for a specific sequence of code where interruption by an event is not desirable. Unless specified otherwise, properties that convey device state information (e.g., JrnEmpty and DrawerOpened) are kept
	 * current while the device is enabled, regardless of the setting of this property.
	 * <p>
	 * This property is initialized to false by the open method.
	 * 
	 * @see jpos.BaseControl#getFreezeEvents()
	 */
	@Override
	public boolean getFreezeEvents() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getFreezeEvents();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	@Override
	public void setFreezeEvents(boolean freezeEvents) throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			baseService.setFreezeEvents(freezeEvents);
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * Holds the results of the most recent call to the checkHealth method.<br>
	 * - Internal HCheck: Successful<br>
	 * - External HCheck: Not Responding<br>
	 * - Interactive HCheck: Complete<br>
	 * <p>
	 * This property is empty (“”) before the first call to the checkHealth method.<br>
	 * 
	 * @see jpos.BaseControl#getCheckHealthText()
	 */
	@Override
	public String getCheckHealthText() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getCheckHealthText();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * 
	 * A sample version number is:1002038
	 * <p>
	 * This value may be displayed as version “1.2.38”, and interpreted as
	 * <p>
	 * major version 1,
	 * <p>
	 * minor version 2,
	 * <p>
	 * build 38.
	 * <p>
	 * This property is always readable.
	 * 
	 * @see jpos.BaseControl#getDeviceControlVersion()
	 */
	@Override
	public int getDeviceControlVersion() {
		return deviceControlVersion;
	}

	/**
	 * 
	 * Holds an identifier for the UnifiedPOS Control and the company that produced it.
	 * <p>
	 * A sample returned string is:
	 * <p>
	 * “POS Printer UnifiedPOS Compatible Control, (C) 1998 Epson”
	 * <p>
	 * This property is always readable.
	 * 
	 * @see jpos.BaseControl#getDeviceControlDescription()
	 */
	@Override
	public String getDeviceControlDescription() {
		return deviceControlDescription;
	}

	/**
	 * Holds an identifier for the UnifiedPOS Service and the company that produced it.
	 * <p>
	 * A sample returned string is:
	 * <p>
	 * “TM-U950 Printer UnifiedPOS Compatible Service Driver, (C) 1998 Epson”
	 * <p>
	 * This property is initialized by the open method.
	 * 
	 * @see jpos.BaseControl#getDeviceServiceDescription()
	 */
	@Override
	public String getDeviceServiceDescription() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getDeviceServiceDescription();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * A sample version number is: 1002038
	 * 
	 * @see jpos.BaseControl#getDeviceServiceVersion()
	 */
	@Override
	public int getDeviceServiceVersion() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getDeviceServiceVersion();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * Holds an identifier for the physical device.
	 * <p>
	 * A sample returned string is:
	 * <p>
	 * “NCR 7192-0184 Printer, Japanese Version” This property is initialized by the open method.
	 * 
	 * @see jpos.BaseControl#getPhysicalDeviceName()
	 */
	@Override
	public String getPhysicalDeviceDescription() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getPhysicalDeviceDescription();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	/**
	 * Holds a short name identifying the physical device
	 * <p>
	 * This is a short version of PhysicalDeviceDescription and should be limited to 30 characters. This property will
	 * typically be used to identify the device in an application message box, where the full description is too
	 * verbose.
	 * <p>
	 * A sample returned string is:
	 * <p>
	 * “IBM Model II Printer, Japanese”
	 * <p>
	 * This property is initialized by the open method.
	 * 
	 * @see jpos.BaseControl#getPhysicalDeviceName()
	 */
	@Override
	public String getPhysicalDeviceName() throws JposException {
		// Make sure control is opened
		if (!controlOpen) { throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened"); }

		try {
			return baseService.getPhysicalDeviceName();
		} catch (final JposException je) {
			throw je;
		} catch (final Exception e) {
			throw new JposException(JposConst.JPOS_E_FAILURE, "Unhandled exception from Device Service", e);
		}
	}

	//---------------------
	//
	//----------------------
	/**
	 * <br>
	 * <p>
	 * 
	 * @param statisticsBuffer
	 * @throws JposException
	 * @since 1.0
	 */

	public abstract void resetStatistics(String statisticsBuffer) throws JposException;

	/**
	 * <br>
	 * <p>
	 * 
	 * @param statisticsBuffer
	 * @throws JposException
	 * @since 1.0
	 */
	public abstract void retrieveStatistics(String[] statisticsBuffer) throws JposException;

	/**
	 * <br>
	 * <p>
	 * 
	 * @param statisticsBuffer
	 * @throws JposException
	 * @since 1.0
	 */
	public abstract void updateStatistics(String statisticsBuffer) throws JposException;

	/**
	 * <br>
	 * <p>
	 * 
	 * @param firmwareFileName
	 * @param result
	 * @throws JposException
	 * @since 1.0
	 */
	public abstract void compareFirmwareVersion(String firmwareFileName, int[] result) throws JposException;

	/**
	 * <br>
	 * <p>
	 * 
	 * @param firmwareFileName
	 * @throws JposException
	 * @since 1.0
	 */
	public abstract void updateFirmware(String firmwareFileName) throws JposException;
}

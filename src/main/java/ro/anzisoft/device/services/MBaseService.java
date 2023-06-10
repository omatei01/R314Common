package ro.anzisoft.device.services;

import java.util.logging.Logger;

import jpos.JposConst;
import jpos.JposException;
import jpos.services.BaseService;
import jpos.services.EventCallbacks;

/**
 * Service + Control<br>
 * MBaseService este varianta customizata de mine a lui {@link jpos.services.BaseService}
 * <p>
 * Pe baza lui îmi definesc serviciile de periferice.<br>
 * Orice device Pi (din R314Pi) implementeaza MBaseService<br>
 * <p>
 * De ex, dacă am de implementat perifericDisplay pentru Pi, PiDisplayService derivat din MBaseService,
 * va mai trebui sa implementeze și MDisplayServiceInterface(fc mele) sau LineDisplayService(pt compatib. Jpos)
 * Dacă se doreste si pentru Windows at tr facut WindowsDisplayService la fel.
 * <p>
 * 
 * @author omatei01
 *
 */
public abstract class MBaseService implements BaseService {

    private static final Logger LOG = Logger.getLogger(MBaseService.class.getName());

    // controlul(care implementeaza eventbus + publica addListener pentru aplicatie) face open si incarca eventbus
    // serviciul nativ creaza evenimentul pe care il declanseaza pe eventBus si astfel ajunge in control
    protected EventCallbacks eventsBus; // serviciul e un publisher; controlul e un subscriber iar app un listener

    //
    protected int stateService = JposConst.JPOS_S_CLOSED;
    protected boolean bOpen = false;
    protected boolean bClaimed = true;

    protected boolean bEnabled = false;
    protected boolean bFreezeEvents = false;

    // -------------------------------------------------------------------------
    // Methods specific
    // -------------------------------------------------------------------------
    protected abstract void openSpecific() throws JposException;

    protected abstract void enabledSpecific(boolean e) throws JposException;// se apeleaza in setEnabled

    protected abstract void closeSpecific() throws JposException;

    public abstract void reset();

    /*
    public void setDeviceEnabled(boolean enabled) throws JposException {
    	checkIfOpen();
    	checkIfClaimed();
    	this.enabled = enabled;
    
    	if (this instanceof AnziPOSPowerService) {
    		invoke(new Runnable() {
    			public void run() {
    				this.getNativeService();
    			}
    		});
    		SimulatedMSRPanel.getInstance().setCallbacks(this._callbacks);
    		SimulatedMSRPanel.getInstance().setDeviceCallback((SimulatedMSRService) this);
    
    	} else if (this instanceof SimulatedPOSPrinterService) {
    		invoke(new Runnable() {
    			public void run() {
    				if (!SimulatedPOSPrinterPanel.getInstance().isInitialized()) {
    					SimulatedPOSPrinterPanel.getInstance().init();
    				}
    				SimulatedDeviceWindow.getInstance().getTabbedPane().setSelectedComponent(SimulatedPOSPrinterPanel
    				        .getInstance());
    			}
    		});
    
    	} 
    }*/

    /**
     * Functia open care se apeleaza din app, la init controlului
     *
     * @param logicalName nume
     * @throws JposException ex
     */
    @Override
    public void open(String logicalName, EventCallbacks eventBusParam) throws JposException {
        // LOG.info("MBaseService (" + logicalName + ") : open (callbacks=" + (eventBusParam == null ? "false" : "true") + ")");

        if (bOpen) {
            throw new JposException(JposConst.JPOS_E_ILLEGAL, "Service is already open.");
        }
        this.eventsBus = eventBusParam;

        openSpecific();

        bOpen = true;
        stateService = JposConst.JPOS_S_IDLE;

        // LOG.info("MBaseService: open end");
    }

    @Override
    public void close() throws JposException {
        if (!bOpen)
            throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened");

        bOpen = false;
        stateService = JposConst.JPOS_S_CLOSED;

        closeSpecific();
        // Also need to reset all the member variables
        // reset();

        eventsBus = null;
        bEnabled = false;
        bFreezeEvents = false;
        bClaimed = false;

    }

    @Override
    public void claim(int arg0) throws JposException {
        if (!bOpen)
            throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened");
        bClaimed = true;
    }

    @Override
    public void release() throws JposException {
        if (!bOpen)
            throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened");
        if (!bClaimed)
            throw new JposException(JposConst.JPOS_E_CLAIMED, "Control not claimed");

        this.bClaimed = false;
        this.bEnabled = false;

        this.stateService = JposConst.JPOS_S_IDLE;
    }

    @Override
    public void setDeviceEnabled(boolean e) throws JposException {
        if (!bOpen)
            throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened");
        if (!bClaimed)
            throw new JposException(JposConst.JPOS_E_CLAIMED, "Control not claimed");

        enabledSpecific(e);

        bEnabled = e;
    }

    @Override
    public boolean getDeviceEnabled() throws JposException {
        return bEnabled;
    }

    @Override
    public int getState() throws JposException {
        return this.stateService;
    }

    @Override
    public boolean getClaimed() throws JposException {
        return bClaimed;
    }

    @Override
    public void setFreezeEvents(boolean freezeEvents) throws JposException {
        if (!bOpen)
            throw new JposException(JposConst.JPOS_E_CLOSED, "Control not opened");
        this.bFreezeEvents = freezeEvents;
    }

    @Override
    public boolean getFreezeEvents() throws JposException {
        return bFreezeEvents;
    }

    @Override
    public void directIO(int i, int[] ints, Object o) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    public void deleteInstance() throws JposException {

    }

    // -------------------------------------------------------------------------
    // Unsupported methods always
    // -------------------------------------------------------------------------
    public boolean getCapCompareFirmwareVersion() throws JposException {
        return false;
    }

    public boolean getCapUpdateFirmware() throws JposException {
        return false;
    }

    public void updateFirmware(String string) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    public boolean getCapStatisticsReporting() throws JposException {
        return false;
    }

    public boolean getCapUpdateStatistics() throws JposException {
        return false;
    }

    public void resetStatistics(String string) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    public void retrieveStatistics(String[] strings) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    public void updateStatistics(String string) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    public void compareFirmwareVersion(String string, int[] ints) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
}

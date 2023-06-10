/**
 * MPowerInterface.java
 * 
 * 
 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.device.services;

import jpos.JposException;
import jpos.events.StatusUpdateListener;
import jpos.services.POSPowerService114;

/**
 * <b>MPowerInterface</b>
 * @author omatei01 (19 iul. 2018)
 * @version 1.0
 */
public interface MPowerDevice extends POSPowerService114 {
    // NU AM NIMIC DEOSEBIT
    void addStatusUpdateListener(StatusUpdateListener l);

    void removeStatusUpdateListener(StatusUpdateListener l);

    boolean isBatteryAbsent() throws JposException;

    boolean isACDetectFalse() throws JposException;

    String getCrateInfo() throws JposException;

    float getCrate() throws JposException;
    
    String getVCELL() throws JposException;
    
    public void setContextForPressButton(boolean val);
    
    
}

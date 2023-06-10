/**
 * MDeviceProviderInterface.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.device.controls;

import ro.anzisoft.device.services.MBeepDevice;
import ro.anzisoft.device.services.MLcdDevice;
import ro.anzisoft.device.services.MPowerDevice;
import ro.anzisoft.device.services.MfaDevice;

/**
 * <b>MDeviceProviderInterface</b><br>
 * <p>
 * Se foloseste pentru incarcarea serviciilor native
 * <p>
 * De ex pentru Pi exista MDeviceProviderPi in R314Pi
 * Pentru Windows se poate face MDeviceProviderW
 * <p>
 * Acesta se foloseste pentru a face leg intre controls si services in MBaseControls
 * <p>
 * 
 * @author omatei01 (2018)
 */
public interface MServiceProviderInterface {

	MBeepDevice getBeepService();

	MLcdDevice getLcdService();

	MPowerDevice getPowerService();

	MfaDevice getFiscalService();

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.anzisoft.device.services;

import jpos.JposException;
import jpos.services.BaseService;

/**
 *
 * @author omatei01
 */
public interface MBeepDevice extends BaseService {

    /**
     * PiBeepInterface
     * <p>
     * Functia generala pentru beep
     *
     * @param range        parm pt pwm - max 1024: pleaca de la 100 si creste practic tonalitatea/vol
     * @param clock        creste frecventa; pleaca de la 20 si face ca greierele la 300
     * @param delay        durata sunet; pleaca de la 100
     * @param cycles       nr de repetitii beep; default 1
     * @param delay_cycles delay intre repetitii; default 0
     * @throws jpos.JposException ex
     */
    public void beep(int range, int clock, int delay, int cycles, int delay_cycles) throws JposException;

    /**
     * PiBeepInterface pentru info
     * <p>
     * 1(0) x (100.30.100)
     * @throws jpos.JposException ex
     */
    public void beepInfo() throws JposException;

    /**
     * PiBeepInterface pentru warning
     * <p>
     * 3(0) x [300.20.50]
     * @throws jpos.JposException ex
     */
    public void beepWarning() throws JposException;

    /**
     * PiBeepInterface pentru alert
     * <p>
     * 5(50) x [200.100.200]
     * @throws jpos.JposException ex
     */
    public void beepAlert() throws JposException;
}

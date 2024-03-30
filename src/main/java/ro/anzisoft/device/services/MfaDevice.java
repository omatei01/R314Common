/**
 * MFiscalInterface.java
 * 
 * 
 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.device.services;

import java.util.List;

import jpos.JposException;
import jpos.services.BaseService;
import ro.anzisoft.common.MfaStockType;
import ro.anzisoft.device.events.DeviceEventListener;

/**
 * <b>MfaDevice</b><br>
 * 
 * @author omatei01 (2018)
 */
/**
 * <b>MfaDevice</b><br>
 * Serviciul pentru accesat MFA-ul (modulul fiscal)<br>
 * <p>
 * <b>Fiscal Printer Modes:</b>
 * <p>
 * fiscal<br>
 * training<br>
 * non-fiscal<br>
 * <p>
 * <b>Fiscal Printer States</b><br>
 * <p>
 * MFA_STATUS_MONITOR - așteaptă comenzi<br>
 * MFA_STATUS_FISCAL_RECEIPT - a fost inceput un bon<br>
 * MFA_STATUS_FISCAL_RECEIPT_TOTAL - s-a dat total in timpul bonului<br>
 * MFA_STATUS_FISCAL_RECEIPT_ENDING - se salveaza bonul<br>
 * MFA_STATUS_LOCKED - mfa este blocat;<br>
 * - fiscalMode - trebuie fiscalizare<br>
 * - readOnlyMode - doar citire<br>
 * - deviceError - probleme/lipsesc periferice: rtc,mem-fisc etc<br>
 * MFA_STATUS_NONFISCAL - a fost inceput un doc nefiscal<br>
 * MFA_STATUS_REPORT - a fost inceput un raport<br>
 * <br>
 * Starea mfa este complet descrisa de state in combinatie cu propr:<br>
 * - fiscalMode - daca nu , doar anumite operatii!!<br>
 * - readOnlyMode - only with MFA_STATUS_LOCKED<br>
 * - onlyZMode - - only with MFA_STATUS_LOCKED<br>
 * - trainingMode<br>
 * - dayOpened<br>
 * - deviceError - periferic obligatoriu cu probleme - vezi state<br>
 * <br>
 * 
 * <p>
 * <b>Status Update Model</b><br>
 * <p>
 * Daca Mfa este enabled at statusul perifericelor obligatorii si starii de lock sunt monitorizate iar schimbarile sunt raportate ca events:<br>
 * - MFA_EVENT_STATUS_PRINTER_OFF<br>
 * - MFA_EVENT_STATUS_PRINTER_ON<br>
 * - MFA_EVENT_STATUS_PRINTER_TIMEOUT<br>
 * - MFA_EVENT_STATUS_MEM_FISC_OFF<br>
 * - MFA_EVENT_STATUS_MEM_FISC_ON<br>
 * - MFA_EVENT_STATUS_BATTERY_ON<br>
 * - MFA_EVENT_STATUS_BATTERY_OFF<br>
 * - MFA_EVENT_STATUS_BATTERY_LOW<br>
 * - MFA_EVENT_STATUS_BATTERY_CRITICAL<br>
 * <p>
 * <b>Fiscal Printer States</b><br>
 * <p>
 * 
 * Setul de comenzi Mfa ce se pot executa la un moment dat depinde de statusul MFA<br>
 * Statusul curent al MFA se obtine cu getMfaState().<br>
 * <p>
 * 
 * 
 * @author omatei01 (2018)
 */
public interface MfaDevice extends BaseService { // FiscalPrinterService114

	// --------------------------------------------------------------------------
	// Methods - Programmming
	// --------------------------------------------------------------------------
	/**
	 * Fiscalizare <br>
	 * Programeaza antetul fiscal si fiscalId[NUI]<br>
	 * Trebuie sa fie in stare nefiscala<br>
	 * 
	 * @param companyName
	 *            numele companiei ex: LEONARDO ROMANIA SRL
	 * @param storeName
	 *            numele magazinului ex: MAGAZIN AFI COTROCENI
	 * @param address
	 *            adresa line 1: STR. DR. IACOB FELIX, NR.8-10
	 * @param address2
	 *            adresa line 2: SECTOR 1, BUCURESTI
	 * @param companyCif
	 *            cif companie/store: CIF RO6205722
	 * @param bnrCode
	 *            cod statistic BNR: XXX.XX.XXX.XX [doar pt exchange]
	 * @param amefFiscalId
	 *            logo fiscal: 9000028216
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: acest mfa NU e nefiscal<br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: tip gresit mfa<br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: Eroare la fiscalizare in mem.fiscala<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_DESCRIPTION: Parametrii incorecti (lungime gresita)<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_DESCRIPTION: Serie fiscala gresita<br>
	 */
	void fiscalizationExchange(String companyName, String storeName, String address, String address2, String companyCif, String bnrCode, String amefFiscalId) throws JposException;

	/**
	 * 
	 * @param companyName
	 * @param storeName
	 * @param address
	 * @param address2
	 * @param companyCif
	 * @param vendingSerial
	 * @param amefFiscalId
	 * @throws JposException
	 */
	void fiscalizationVending(String companyName, String storeName, String address, String address2, String companyCif, String vendingSerial, String amefFiscalId) throws JposException;

	/**
	 * Seteaza cota de tva pentru exchange <br>
	 * <p>
	 * 
	 * @param vatRate
	 *            format int pentru procent: 15 % - 0.15 *10000 = 1500
	 * @param pNeplatitorTva
	 *            daca e neplatitor sau nu ; daca e true at va ignora vatRate
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: erori grave la scriere in mem.fisc.<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_DAY_END_REQUIRED: operatia nu se poate realiza in timpul zilei fiscale
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: lungime array gresita<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_VAT: valoare incorecta procent tva
	 */
	void setVatExchange(int vatRate, boolean pNeplatitorTva) throws JposException;

	/**
	 * Cota de tva pentru exchange <br>
	 * <p>
	 * 
	 * @return un int pentru procent: 15 % - 0.15 *10000 = 1500
	 * @throws JposException
	 *             e
	 */
	int getVatExchange() throws JposException;

	/**
	 * Permite reprogramarea antetului fiscal dupa fiscalizare.<br>
	 * Parametrii trimisi cu empty string (“”) sunt ignorati.<br>
	 * <p>
	 * getDayOpened trbuie sa fie false.<br>
	 * <p>
	 * 
	 * 
	 * @param companyName
	 *            s
	 * @param storeName
	 *            s
	 * @param address
	 *            s
	 * @param address2
	 *            s
	 * @param companyCif
	 *            s
	 * @param bnrCod
	 *            s; la retail este optional
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: Acest MFA nu este de tip EXCHANGE<br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: Eroare la scriere in mem.fiscala<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_DESCRIPTION: Parametrii incorecti (lungime gresita)<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_DAY_END_REQUIRED: ziua inceputa;tr Z<br>
	 */
	void setHeaderFiscal(String companyName, String storeName, String address, String address2, String companyCif, String bnrCod) throws JposException;

	/**
	 * Liniile de antet fiscal care se printeaza pe bon<br>
	 * 
	 * @param inoutHeaderLines
	 *            output string array .
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 */
	void getHeaderFiscal(String[] inoutHeaderLines) throws JposException;

	/**
	 * Linii de antet suplimentare, altele decit cele fiscale<br>
	 * Se tiparesc imediat dupa antet fiscal.<br>
	 * Pot fi oricite. Se tiparesc exact cum sunt transmise<br>
	 * 
	 * Se pastreaza intre bonuri.<br>
	 * Empty string sunt ignorate; asa se si reseteaza
	 * <p>
	 * apiValidation(monitor:true, write:false, needFiscal: false, needDayClosed: true, needZ: true);
	 * 
	 * @param pHeaderLines
	 *            lines
	 * @param print
	 *            daca print nonfiscal receipt
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MfaConst.MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: lungime array gresit<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_DAY_END_REQUIRED: ziua inceputa;tr Z<br>
	 */
	void setHeader(String[] pHeaderLines, boolean print) throws JposException;

	@Deprecated
	void setHeader(String[] pHeaderLines) throws JposException;

	/**
	 * Liniile de header care se printeaza pe bon<br>
	 * vezi {@link #setHeaderFiscal(String, String, String, String, String, String)} <br>
	 * 
	 * @param inoutHeaderLines
	 *            lines
	 * @throws JposException
	 *             e
	 */
	void getHeader(String[] inoutHeaderLines) throws JposException;

	/**
	 * Linii de trailer<br>
	 * Se tiparesc inainte de subsol; pot fi oricite; sunt ignorate empty strings<br>
	 * Se pastreaza intre bonuri<br>
	 * 
	 * apiValidation(monitor:true, write:true, needFiscal: false, needDayClosed: false, needZ: true);
	 * 
	 * @param trailerLines
	 *            Texts to which to set the trailer line.
	 * @param print
	 *            daca print nonfiscal
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MfaConst.MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: Depasire max. linii trailer<br>
	 */
	void setTrailer(final String[] trailerLines, boolean print) throws JposException;

	@Deprecated
	void setTrailer(final String[] trailerLines) throws JposException;

	/**
	 * Citire linii trailer programat <br>
	 * 
	 * @param trailerLines
	 *            String array
	 * @throws JposException
	 *             e
	 */
	void getTrailer(String[] trailerLines) throws JposException;

	/**
	 * 
	 * Sets the Fiscal Printer’s date and time.<br>
	 * Momentan doar verifica data ca e ok.<br>
	 * <p>
	 * Nu se poate programa data dacă:<br>
	 * - mfa nu e in stare monitor!!??<br>
	 * - înaintea lui dateSafe.<br>
	 * - day opened ?<br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: false, needDayClosed: false, needZ: false);
	 * 
	 * @param date
	 *            datatimp in fomrmat yyyyMMddHHmm.
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: erori grave la scriere in mem.fisc.<br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: tip gresit mfa<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_DATE: format data gresit<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_CLOCK_ERROR: nu se poate programa inaintea lui dateSafe<br>
	 */
	void setDate(String date) throws JposException;

	/**
	 * <b>getDate</b>
	 * <p>
	 * Citeste datele din mfa in fc de parametrul transmis.<br>
	 * <p>
	 * Rezultatul e un string in formatul “yyyyMMddhhmm”:<br>
	 * dd = day of the month (1 - 31)<br>
	 * mm = month (1 - 12)<br>
	 * yyyy = year (1997-)<br>
	 * hh = hour (0-23)<br>
	 * mm = minutes (0-59)<br>
	 * <p>
	 * Tipurile de date:
	 * - FPTR_DT_RTC(4) Real time clock of the Fiscal Printer.<br>
	 * - FPTR_DT_SAFE(9) Safe date for MFA.<br>
	 * - FPTR_DT_LAST_RECEIPT(8) Date of the last fiscal doc.<br>
	 * - FPTR_DT_START(6) The date and time that the fiscal day started(for 24h test). Warning: if dayOpened=false then return will be empty<br>
	 * - FPTR_DT_EOD(2) Date of last end of day.<br>
	 * - FPTR_DT_CONF(1) Date of configuration/fiscalization.<br>
	 * - FPTR_DT_RESET(3) Date of last reset.<br>
	 * - FPTR_DT_VAT(5) Date of last VAT change.<br>
	 * - FPTR_DT_JE(7) Date of the last Je init.<br>
	 * <p>
	 * 
	 * @param dataType
	 *            vezi mai sus valorile
	 * @return data dorită ca string, in formatul de mai sus
	 * @throws JposException
	 *             e
	 */
	String getDate(int dataType) throws JposException;

	/**
	 * Sets the POS identifier.<br>
	 * These values will be printed when each fiscal receipt is closed.<br>
	 * This method can only be called while DayOpened is false.<br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: false, needDayClosed: true, needZ: false);
	 * 
	 * @param posId
	 *            Identifier for the POS system (max len 2)
	 * @param print
	 *            daca da sau nu
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: Casa id prea mare [0-99]<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_DAY_END_REQUIRED: trebuie Z<br>
	 */
	void setPosId(int posId, boolean print) throws JposException;

	/**
	 * @return casa id programat
	 * @throws JposException
	 *             e
	 */
	int getPosId() throws JposException;

	/**
	 * Programeaza nume si cod casier<br>
	 * Se poate programa si cod si nume sau niciunul! <br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: false, needDayClosed: false, needZ: false);
	 * 
	 * @param cashierId
	 *            0 sau un cod
	 * @param cashierName
	 *            "" sau un nume
	 * @param print
	 *            daca da sau nu bon
	 * 
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: erori grave la scriere in mem.fisc.<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: Cod casier prea mare [0-99]<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: Nume casier prea mare [max %d]", maxCharsLine / 2<br>
	 */
	void setCashier(int cashierId, String cashierName, boolean print) throws JposException;

	/**
	 * @return cod casier care e programat
	 * @throws JposException
	 *             e
	 */
	int getCashierId() throws JposException;

	/**
	 * @return numele casierului programat
	 * @throws JposException
	 *             e
	 */
	String getCashierName() throws JposException;

	/**
	 * Daca este vorba de user neplatitor de tva<br>
	 * Se poate seta oricind; <br>
	 * Se va tipari textul NEPLATITOR TVA in header pentru bonuri
	 * <p>
	 * 
	 * @param b
	 *            daca da sau nu
	 * @throws JposException
	 *             e
	 */
	// void setNeplatitorTva(boolean b) throws JposException;

	/**
	 * Inchide MFA
	 * 
	 * @throws JposException
	 *             e
	 */
	void shutdown() throws JposException;

	/**
	 * Seteaza AMEF sa faca restart dupa shutdown
	 * 
	 * @throws JposException
	 */
	void setRestartOn() throws JposException;

	/**
	 * Seteaza AMEF sa NU faca restart dupa shutdown
	 * 
	 * @throws JposException
	 */
	void setRestartOff() throws JposException;

	/**
	 * Citeste flag RESTART din AMEF
	 * 
	 * @throws JposException
	 */
	boolean checkRestartFlag() throws JposException;

	/**
	 * Afiseaza lines la display (mic)
	 * 
	 * @param line1
	 *            rind 1
	 * @param line2
	 *            rind 2
	 * @throws JposException
	 *             e
	 */
	void writeDisplay(String line1, String line2) throws JposException;

	/**
	 * Afiseaza la deisplay secundar (big)
	 * 
	 * @param line1
	 *            r1
	 * @param line2
	 *            r2
	 * @throws JposException
	 *             e
	 */
	void writeSecondaryDisplay(String line1, String line2) throws JposException;

	/**
	 * Programeaza inregistrarea de je <br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: true, needDayClosed: true, needZ: true);
	 * 
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: Eroare la initializare je<br>
	 */
	void initDMJE() throws JposException;

	// --------------------------------------------------------------------------
	// Methods - Specific - Exchange Fiscal Receipt
	// --------------------------------------------------------------------------
	/**
	 * Tipareste un bon fiscal cu o tranzactie de exchange: casa face vinzare de valuta
	 * <p>
	 * se incaseaza RON si se vinde VALUTA la curs de VINZARE<br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: true, needDayClosed: false, needZ: true);
	 * 
	 * @param customer
	 *            numele si prenumele clientului de ex VASILE ION
	 * @param country
	 *            țara fie cod, fie denumire. de ex RO
	 * @param identityID
	 *            serie si numar act de identitate. de ex CI RX 552345
	 * @param CNP
	 *            cnp nume 5551235464646
	 * @param rezident
	 *            da sau nu
	 * @param amount
	 *            suma neta -fara comision -cumparată/vinduta: 25678 (256.78 * 100)
	 * @param valuta
	 *            valuta pentru suma cumparata; altceva decit ron(text gen EUR, RON etc)
	 * @param curs
	 *            curs in ron si 4 zecimale; 47895 (4.7895 * 10000)
	 * @param unit
	 *            unitatea pentru curs: 100 leva = 1 ron
	 * @param comissionPercent
	 *            procent comision; 1000 (10% adică 0.10 * 10000)
	 * @param inoutAmountsMfa
	 *            array cu sumele calculate de mfa
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: Acest MFA nu este de tip EXCHANGE<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_VAT_REQUIRED:MFA nu are tva programat<br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: erori grave la scriere in mem.fisc.<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_REQUIRED_FISCALIZED: mfa tr sa fie fiscalizata<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_AMOUNT: Sumă greșită. Depașire limite<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_AMOUNT: Procent greșit<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_AMOUNT: Valoare greșită [unit]<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: Parametru sume mfa greșit [inoutAmountsMfa.size=2]<br>
	 * @see #getCheckHealthText()
	 */
	void printSaleExchange(String customer,
			String country,
			String identityID,
			String CNP,
			boolean rezident,
			long amount,
			String valuta,
			int curs,
			int unit,
			int comissionPercent,
			long[] inoutAmountsMfa) throws JposException;

	void simulateSaleExchange(String customer,
			String country,
			String identityID,
			String CNP,
			boolean rezident,
			long amount,
			String valuta,
			int curs,
			int unit,
			int comissionPercent,
			long[] inoutAmountsMfa) throws JposException;

	/**
	 * Tipareste un bon fiscal cu o tranzactie de exchange: casa face cumparare de valuta
	 * <p>
	 * se incaseaza RON si se vinde VALUTA la curs de VINZARE<br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: true, needDayClosed: false, needZ: true);
	 * 
	 * @param customer
	 *            numele si prenumele clientului de ex VASILE ION
	 * @param country
	 *            țara fie cod, fie denumire. de ex RO
	 * @param identityID
	 *            serie si numar act de identitate. de ex CI RX 552345
	 * @param CNP
	 *            cnp nume 5551235464646
	 * @param rezident
	 *            da sau nu
	 * @param amount
	 *            suma neta -fara comision -cumparată/vinduta: 25678 (256.78 * 100)
	 * @param valuta
	 *            valuta pentru suma cumparata; altceva decit ron(text gen EUR, RON etc)
	 * @param curs
	 *            curs in ron si 4 zecimale; 47895 (4.7895 * 10000); NU INCLUDE COMISION
	 * @param unit
	 *            denominator/ 100 leva = CURS
	 * @param comissionPercent
	 *            procent comision; 1000 (10% adică 0.10 * 10000)
	 * @param inoutAmountsMfa
	 *            array cu sumele calculate din mfa: comision, baza de calcul, suma finala
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: Acest MFA nu este de tip EXCHANGE<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_VAT_REQUIRED:MFA nu are tva programat<br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: erori grave la scriere in mem.fisc.<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_REQUIRED_FISCALIZED: mfa tr sa fie fiscalizata<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_AMOUNT: Sumă greșită. Depașire limite<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_AMOUNT: Procent greșit<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_ITEM_AMOUNT: Valoare greșită [unit]<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: Parametru sume mfa greșit [inoutAmountsMfa.size=2]<br>
	 * @see #getCheckTotal()
	 */
	void printBuyExchange(String customer,
			String country,
			String identityID,
			String CNP,
			boolean rezident,
			long amount,
			String valuta,
			int curs,
			int unit,
			int comissionPercent,
			long[] inoutAmountsMfa) throws JposException;

	void simulateBuyExchange(String customer,
			String country,
			String identityID,
			String CNP,
			boolean rezident,
			long amount,
			String valuta,
			int curs,
			int unit,
			int comissionPercent,
			long[] inoutAmountsMfa) throws JposException;

	/**
	 * Operatii de casa<br>
	 * <p>
	 * Prints a cash-in or cash-out receipt amount.<br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: true, needDayClosed: false, needZ: true);
	 * 
	 * @param type
	 *            int FPTR_RT_CASH_IN(1) or FPTR_RT_CASH_OUT(2) .
	 * @param amount
	 *            amount; always positive
	 * @param currency
	 *            text for currency
	 * @param description
	 *            EXPLICATII sau empty
	 * @param name
	 *            nume si prenume sau empty
	 * @param identityId
	 *            act identitate tip, serie, numar
	 * @param issuer
	 *            emitent (vezi alimentari) sau empty
	 * @param recipient
	 *            destinatar (vezi alimentari) sau empty
	 * @param operType
	 *            operatiune (de ex plata la deconturi) sau empty
	 * 
	 * @throws JposException
	 *             ErrorCode=<br>
	 */
	void printCashExchange(int type,
			long amount,
			String currency,
			String description,
			String name,
			String identityId,
			String issuer,
			String recipient,
			String operType) throws JposException;

	void printCashExchange(int type,
			long amount,
			String currency,
			String description,
			String name,
			String identityId,
			String issuer,
			String recipient,
			String operType,
			boolean duplicat) throws JposException;

	void printCashExchange(int type,
			long amount,
			String currency,
			String description,
			String name,
			String identityId,
			String issuer,
			String recipient,
			String operType,
			int cursReferinta) throws JposException;

	void printCashExchange(int type,
			long amount,
			String currency,
			String description,
			String name,
			String identityId,
			String issuer,
			String recipient,
			String operType,
			int cursReferinta,
			boolean duplicat) throws JposException;

	// --------------------------------------------------------------------------
	// Methods - Specific - Fiscal Reports
	// --------------------------------------------------------------------------
	/**
	 * Prints a report of all the daily fiscal activities on the receipt. No data will be written to the fiscal EPROM as a result of this method invocation.
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: true, needDayClosed: false, needZ: false);
	 * 
	 * @throws JposException
	 *             <br>
	 *             <b>ErrorCode</b> <br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita<br>
	 */
	void printXReport() throws JposException;

	/**
	 * Prints a report of all the daily fiscal activities on the receipt.<br>
	 * Data will be written to the fiscal EPROM as a result of this method invocation.<br>
	 * <p>
	 * Since running printZReport is implicitly a fiscal end of day function, the getDayOpened() property will be set to false.<br>
	 * <p>
	 * apiValidation(monitor:true, write:true, needFiscal: true, needDayClosed: false, needZ: false)
	 * 
	 * @throws JposException
	 *             vezi mai jos<br>
	 *             <i>errorCode</i>=MFA_ERR_ILLEGAL: Acest MFA nu este de tip EXCHANGE<br>
	 *             <i>errorCode</i>=MFA_ERR_TIMEOUT: Probleme la tiparire Z<br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: EROARE LA SCRIERE Z IN MEM FISC<br>
	 */
	void printZReport() throws JposException;

	/**
	 * Prints a report of the fiscal EPROM contents on the receipt that occurred between two end points. <br>
	 * <p>
	 * apiValidation(monitor:true, write:false, needFiscal: true, needDayClosed: false, needZ: false);
	 * <p>
	 * apiValidation2(needMfOk:true, needJeOk:false, needCryptoOk:false);
	 * 
	 * @param reportType
	 *            1=MFA_REP_TR_SUMAR or 2=MFA_REP_TR_DETAILED
	 * @param filterFlag
	 *            combinatii de
	 *            MFA_REP_TF_EOD_ORDINAL, MFA_REP_TF_EOD_ORDINAL, MFA_REP_TF_FLAG_INCLUDE_PROBA, MFA_REP_TF_FLAG_ONLY_PROBA
	 * @param start
	 *            ASCII string identifying the starting record in Fiscal Printer memory from which to begin printing
	 * @param stop
	 *            ASCII string identifying the final record in Fiscal Printer memory at which printing is to end.
	 *            See reportType table below to find out the exact meaning of this parameter.
	 * @param outputType
	 *            1=MFA_REP_OUTPUT_PRINT, 2=MFA_REP_OUTPUT_FILE, 3=MFA_REP_OUTPUT_EMAIL
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_NOHARDWARE: Lipseste stick usb<br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: Probleme la citire memorie<br>
	 */
	void reportMf(int reportType, int filterFlag, String start, String stop, int outputType) throws JposException;

	/**
	 * Raport JE <br>
	 * <p>
	 * apiValidation(monitor:true, write:false, needFiscal: true, needDayClosed: false, needZ: false);
	 * 
	 * @param filterType
	 *            combinatii de
	 *            MFA_REP_TF_EOD_ORDINAL, MFA_REP_TF_EOD_ORDINAL, MFA_REP_TF_FLAG_INCLUDE_PROBA, MFA_REP_TF_FLAG_ONLY_PROBA
	 * @param start
	 *            ASCII string with data/z number
	 * @param stop
	 *            ASCII string with data/z number
	 *            See reportType table below to find out the exact meaning of this parameter.
	 * @param outputType
	 *            1=MFA_REP_OUTPUT_PRINT, 2=MFA_REP_OUTPUT_FILE, 3=MFA_REP_OUTPUT_EMAIL
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_NOHARDWARE: Lipseste stick usb<br>
	 */
	void reportJe(int filterType, String start, String stop, int outputType) throws JposException;

	/**
	 * Raport cu initializari de DMJE <br>
	 * 
	 * @throws JposException
	 *             e
	 */
	void reportInitDMJE() throws JposException;

	void reportInitDMJE(int outputType) throws JposException;

	/**
	 * Raport cu schimbarile de antet <br>
	 * 
	 * @throws JposException
	 *             e
	 */
	void reportChangeHeader() throws JposException;

	void reportChangeHeader(int outputType) throws JposException;

	/**
	 * Raport caderi tensiune <br>
	 * Print intotdeauna
	 * 
	 * @throws JposException
	 *             e daca nu e fiscal, daca param sunt gresiti; daca stick lipseste etc
	 */
	void reportPowerLoss() throws JposException;

	// --------------------------------------------------------------------------
	// Methods - Specific - Non-Fiscal
	// --------------------------------------------------------------------------
	/**
	 * Initiates non-fiscal operations on the Fiscal Printer.
	 * <p>
	 * This method is only supported if CapNonFiscalMode is true. <br>
	 * Output in this mode is accomplished using the {@link #printNonFiscal(String)} method. <br>
	 * This method can be successfully called only if the current value of the {@link #getMfaStatus()} property is MFA_STATUS_MONITOR.<br>
	 * <p>
	 * If this method is successful, the MfaStatus property will be changed to MFA_STATUS_NONFISC<br>
	 * <p>
	 * apiValidation(monitor:true, write:false, needFiscal: false, needDayClosed: false, needZ: false); * @throws JposException <br>
	 * 
	 * @throws JposException
	 */
	void beginNonFiscal() throws JposException;

	void printNonFiscal(String data) throws JposException;

	/**
	 * Performs non-fiscal printing. Prints data on the Fiscal Printer station.
	 * <p>
	 * 
	 * @param data
	 *            The characters to be printed. May consist mostly of printable characters, escape sequences, carriage returns (13 decimal), and line feeds (10 decimal)
	 *            but in many cases these are not supported.
	 * @param format
	 *            vezi constante print tranzact
	 * @throws JposException
	 *             <br>
	 *             <b>ErrorCode</b> <br>
	 *             E_EXTENDED:<br>
	 *             ErrorCodeExtended = MFA_ERRX_WRONG_STATE: Stare greșită<br>
	 */
	void printNonFiscal(String data, String format) throws JposException;

	/**
	 * Terminates non-fiscal operations on one Fiscal Printer station.<br>
	 * <p>
	 * If this method is successful, the MfaStatus property will be changed to MFA_STATUS_MONITOR.<br>
	 * <p>
	 * Printeaza nr de tranzactie +1.
	 * 
	 * @throws JposException
	 *             <br>
	 *             E_EXTENDED:<br>
	 *             ErrorCodeExtended = EFPTR_WRONG_STATE: <br>
	 */
	void endNonFiscal() throws JposException;

	// --------------------------------------------------------------------------
	// Methods - Specific - Data Requests
	// --------------------------------------------------------------------------

	/**
	 * Retrieves data and counters from the printer’s fiscal module.<br>
	 * <p>
	 * The data is returned in a string because some of the fields, such as the grand total, might overflow a 4-byte integer.<br>
	 * <p>
	 * Rules for version: <br>
	 * Major - The “millions” place; Minor - The “thousands” place;Build - The “units” place.<br>
	 * Ex: 1002038 = 1.2.38<br>
	 * <p>
	 * The <b><i>dataItem</i></b> parameter has one of the following values:<br>
	 * <b>Identification data</b><br>
	 * FPTR_GD_FIRMWARE = Get the Fiscal Printer’s firmware release number.<br>
	 * 
	 * FPTR_GD_PRINTER_ID = Get the Fiscal Printer’s fiscal ID.<br>
	 * FPTR_GD_PRINTER_ID = Get the Fiscal Printer’s fiscal ID.<br>
	 * <p>
	 * <b>Totals</b><br>
	 * FPTR_GD_CURRENT_TOTAL = Get the current receipt total.<br>
	 * FPTR_GD_DAILY_TOTAL = Get the daily total.<br>
	 * FPTR_GD_GRAND_TOTAL = Get the Fiscal Printer’s grand total.<br>
	 * FPTR_GD_MID_VOID = Get the total number of voided receipts.<br>
	 * FPTR_GD_RECEIPT_NUMBER = Get the number of fiscal receipts printed.<br>
	 * <p>
	 * <b>Fiscal memory counts</b><br>
	 * FPTR_GD_NUMB_CONFIG_BLOCK= Get the grand number of configuration blocks.<br>
	 * FPTR_GD_NUMB_HDR_BLOCK = Get the grand number of header blocks.<br>
	 * FPTR_GD_NUMB_RESET_BLOCK = Get the grand number of reset blocks.<br>
	 * FPTR_GD_NUMB_VAT_BLOCK = Get the grand number of VAT blocks.<br>
	 * FPTR_GD_NUMB_JE_BLOCK = Get the grand number of VAT blocks.<br>
	 * <p>
	 * <b>Counter</b><br>
	 * FPTR_GD_FISCAL_REC = Get the number of daily fiscal sales receipts.<br>
	 * FPTR_GD_FISCAL_REC_VOID = Get the number of daily voided fiscal sales receipts.<br>
	 * FPTR_GD_NONFISCAL_DOC = Get the number of daily non fiscal documents<br>
	 * FPTR_GD_NONFISCAL_DOC_VOID = Get the number of daily voided non fiscal documents.<br>
	 * FPTR_GD_RESTART = Get the Fiscal Printer’s restart count<br>
	 * FPTR_GD_Z_REPORT = Get the Z report number.<br>
	 * 
	 * @param dataItem
	 *            The specific data item to retrieve.
	 * @param optArgs
	 *            For some dataItem this additional argument is needed.
	 * @param data
	 *            Character string to hold the data retrieved.
	 * @throws JposException
	 *             <i>ErrorCode</i> <br>
	 */
	void getData(int dataItem, int[] optArgs, String[] data) throws JposException;

	/**
	 * versiune simplificata fara subtip <br>
	 * 
	 * @param dataItem
	 *            di
	 * @param data
	 *            d
	 * @throws JposException
	 *             e
	 */
	void getData(int dataItem, String[] data) throws JposException;

	/**
	 * pt o sg val de tip string - tr sa fii sigur ca e string val <br>
	 * 
	 * @param dataItem
	 * @return o sg val de tip string
	 * @throws JposException
	 *             e
	 */
	String getData(int dataItem) throws JposException;

	/**
	 * pt o sg val de tip numeric - tr sa fii sigur ca e int val<br>
	 * 
	 * @param dataItem
	 * @return date de tip numeric
	 * @throws JposException
	 *             e
	 */
	long getDataN(int dataItem) throws JposException;

	/**
	 * Neimplementata
	 * Gets the totalizer specified by the optArgs argument Some of the totalizers such as item or VAT totalizers may be associated with the given vatID.<br>
	 * <p>
	 * * Specifies the type of totalizer to be requested when calling the getTotalizer method.<br>
	 * <p>
	 * For param <i>totalizerType</i> values are:<br>
	 * FPTR_TT_DOCUMENT = Document totalizer <br>
	 * FPTR_TT_DAY = Day totalizer<br>
	 * FPTR_TT_RECEIPT = Receipt totalizer<br>
	 * FPTR_TT_GRAND = Grand totalizer<br>
	 * <p>
	 * This property is initialized to FPTR_TT_DAY and kept current while the device is enabled, which is the functionality supported prior to Release 1.6.
	 * 
	 * The optArgs parameter has one of the following values:<br>
	 * <p>
	 * FPTR_GT_GROSS = Gross totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_NET = Net totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_DISCOUNT = Discount totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_DISCOUNT_VOID = Voided discount totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_ITEM = Item totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_ITEM_VOID = Voided item totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_NOT_PAID = Not paid totalizer specified by the TotalizerType param.<br>
	 * (no) FPTR_GT_REFUND = Refund totalizer specified by the TotalizerType param.<br>
	 * (no) FPTR_GT_REFUND_VOID = Voided refund totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_SUBTOTAL_DISCOUNT = Subtotal discount totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_SUBTOTAL_DISCOUNT_VOID = Voided discount totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_SUBTOTAL_SURCHARGES = Subtotal surcharges totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_SUBTOTAL_SURCHARGES_VOID = Voided surcharges totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_SURCHARGE = Surcharge totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_SURCHARGE_VOID = Voided surcharge totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_VAT = VAT totalizer specified by the TotalizerType param.<br>
	 * FPTR_GT_VAT_CATEGORY = VAT totalizer per VAT category specified by the TotalizerType property associated to the given vatID.<br>
	 * 
	 * @param totalizerType
	 *            totalizer type; see values permited
	 * @param vatID
	 *            VAT identifier of the required totalizer.
	 * @param optArgs
	 *            Specifies the required totalizer.
	 * @param data
	 *            Totalizer returned as a string.
	 * @throws JposException
	 *             e
	 */
	void getTotalizer(int totalizerType, int vatID, int optArgs, String[] data) throws JposException;

	// --------------------------------------------------------------------------
	// Methods - Specific - Retail Fiscal Receipt
	// --------------------------------------------------------------------------
	/**
	 * 
	 * <br>
	 * <p>
	 * 
	 * @param type
	 * @param amount
	 * @param text
	 * @throws JposException
	 */
	void printCashRetail(int type, long amount, String text) throws JposException;

	/**
	 * 
	 * <br>
	 * <p>
	 * 
	 * @param company
	 * @param store
	 * @param address1
	 * @param address2
	 * @param companyCif
	 * @param FiscalAmefSerial
	 * @throws JposException
	 */
	void fiscalizationRetail(String company, String store, String address1, String address2, String companyCif, String FiscalAmefSerial) throws JposException;

	/**
	 * Defiscalizare
	 * Trebuie sa fie online
	 */
	void fiscalOff() throws JposException;

	/**
	 * Se apeleaza intern la replaceDmje.. pentru teste am scos-o in interfata
	 * 
	 * @throws JposException
	 */
	void archiveDmje() throws JposException;

	// void setFiscalReceiptType(int fiscalReceiptType) throws JposException;

	// int getFiscalReceiptType() throws JposException;

	/**
	 * 
	 * Trece MFA in stare de bon fiscal: MFA_STATUS_FISCAL_RECEIPT<br>
	 * Se accepta comenzile cu printRec...<br>
	 * 
	 * @param printHeader
	 *            Indicates if the header lines are to be printed at this time; for normal use = true.
	 * @throws JposException
	 *             e
	 */
	void beginFiscalReceipt(boolean printHeader) throws JposException;

	/**
	 * 
	 * <br>
	 * <p>
	 * 
	 * @param printAllLines
	 *            daca tipareste toate liniile bonului
	 * @param printHeader
	 *            daca se tipareste header; daca e false se tipareste la sfirsit, vezi endFiscalReceipt
	 * @throws JposException
	 */
	void beginFiscalReceipt(boolean printAllLines, boolean printHeader) throws JposException;

	/**
	 * Termina bonul fiscal si trece mfa in stare MFA_STATUS_MONITOR<br>
	 * 
	 * @param printHeader
	 *            Indicates if the header lines of the following receipt are to be printed at this time.
	 * @throws JposException
	 *             e
	 */

	void endFiscalReceipt(boolean printHeader) throws JposException;

	/**
	 * Printeaza un articol pe bonul fiscal<br>
	 * Se poate combina cu preline/postline<br>
	 * 
	 * @param description
	 *            denumire articol
	 * @param quantity
	 *            cantitate
	 * @param unitName
	 *            unitatea de masura; max 3 chars
	 * @param unitPrice
	 *            pretul unitar cu tva
	 * @param vatInfo
	 *            cota de tva: A, B, C, D, E sau S, sau T
	 * @throws JposException
	 *             e
	 */
	void printRecItem(final String description, final int quantity, final String unitName, final long unitPrice, final char vatInfo) throws JposException;

	/**
	 * Anuleaza un articol de pe bon<br>
	 * Toate valorile sunt pozitive<br>
	 * In fc de setari se poate anula 1)doar ultimul articol 2)orice articol de pe bon 3) orice articol doar tot pe cota sa fie pozitiv<br>
	 * Daca ultimul articol are reducere at mai intai tr anulata reducerea si apoi articolul<br>
	 * Se poate combina cu preline/postline<br>
	 * 
	 * @param description
	 *            denumire articol
	 * @param quantity
	 *            cantitate
	 * @param vatInfo
	 *            cota de tva
	 * @param unitPrice
	 *            pret unitar
	 * @param unitName
	 *            unitatea de masura; max 3
	 * @throws JposException
	 *             e
	 */
	void printRecItemVoid(final String description, final int quantity, final String unitName, final long unitPrice, final char vatInfo) throws JposException;

	/**
	 * Anulare ultim articol<br>
	 * Varianta simplificata<br>
	 * 
	 * @throws JposException
	 */
	public void printRecLastItemVoid() throws JposException;

	/**
	 * Acordare de reducere/adaos la ULTIMUL articol
	 * Se poate combina cu preline/postline<br>
	 * Se valideaza suma (de ex nu se poate da reducere mai mare decit valoare pzitiei anterioare), cota, total tva etc<br>
	 * 
	 * @param adjustmentType
	 *            MFA_AT_AMOUNT_DISCOUNT = Fixed amount discount. The amount parameter contains a currency value.<br>
	 *            MFA_AT_AMOUNT_SURCHARGE = Fixed amount surcharge. The amount parameter contains a currency value.<br>
	 *            MFA_AT_PERCENTAGE_DISCOUNT = Percentage discount. The amount parameter contains a percentage value.<br>
	 *            MFA_AT_PERCENTAGE_SURCHARGE = Percentage surcharge. The amount parameter contains a percentage value.<br>
	 * @param description
	 *            text optional pentru descriere err
	 * @param amount
	 *            suma sau procent penrtu reducere/adaos
	 * @param vatInfo
	 *            cota de tva
	 * @throws JposException
	 *             e
	 */
	void printRecItemAdjustment(int adjustmentType, String description, long amount, char vatInfo) throws JposException;

	/**
	 * Anuleaza reducerea la articol<br>
	 * Daca nu e reducere, da err;
	 * Se valideaza suma, cota, total tva etc<br>
	 * Se poate combina cu preline/postline<br>
	 * 
	 * @param adjustmentType
	 *            Type of adjustment to be voided. {@link #printRecItemAdjustment(int, String, long, char)}
	 * @param description
	 *            Text describing the adjustment to be voided.
	 * @param amount
	 *            Amount of the adjustment to be voided.
	 * @param vatInfo
	 *            VAT rate identifier or amount.
	 * @throws JposException
	 *             e
	 * @see #beginFiscalReceipt(boolean)
	 * @see #endFiscalReceipt(boolean)
	 * @see #printRecItemAdjustment(int, String, long, char)
	 */
	void printRecItemAdjustmentVoid(int adjustmentType, String description, long amount, char vatInfo) throws JposException;

	void printRecItemFuel(String description, int quantity, String unitName, long unitPrice, char vatInfo, long specialTax, String specialTaxName) throws JposException;

	void printRecItemFuelVoid(String description, long price, char vatInfo, long specialTax) throws JposException;

	// void printRecItemRefund(String description, long amount, int quantity, int vatInfo, long unitAmount, String unitName) throws JposException;

	// void printRecItemRefundVoid(String description, long amount, int quantity, int vatInfo, long unitAmount, String unitName) throws JposException;

	/**
	 * Tipareste linii cu text in zona de final a bonului (intre bon si trailer)<br>
	 * 
	 * Mfa trebuie sa fie in stare MFA_FISCAL_RECEIPT_ENDING
	 * 
	 * @param message
	 *            Text message to print.
	 * @throws JposException
	 *             e
	 */
	void printRecMessage(String message) throws JposException;

	void printRecMessage(String[] message) throws JposException;

	/**
	 * Tipareste SUBTOTAL pe bon cu totalul curent al bonului<br>
	 * De asemenea verifica si totalul app dat ca parametru. Daca nu coincid at err si se anuleaza bonul <br>
	 * Util cind se da reducere la total<br>
	 * Se poate combina cu preline/postline<br>
	 * 
	 * @param amount
	 *            Amount of the subtotal.
	 * @throws JposException
	 *             e
	 */
	void printRecSubtotal(long amount) throws JposException;

	/**
	 * printRecSubtotalAdjustment
	 * <p>
	 * Reducere la total<br>
	 * 
	 * @param adjustmentType
	 *            Type of adjustment. See below for values. The adjustmentType parameter has one of the following values (Note: If currency value, four decimal places
	 *            are used):<br>
	 *            FPTR_AT_AMOUNT_DISCOUNT = Fixed amount discount. The amount parameter contains a currency value.<br>
	 *            FPTR_AT_AMOUNT_SURCHARGE = Fixed amount surcharge. The amount parameter contains a currency value.<br>
	 *            FPTR_AT_PERCENTAGE_DISCOUNT = Percentage discount. The amount parameter contains a percentage value.<br>
	 *            FPTR_AT_PERCENTAGE_SURCHARGE = Percentage surcharge. The amount parameter contains a percentage value.<br>
	 * @param description
	 *            text care descrie reducerea (optional)
	 * @param amount
	 *            suma pozitiva coresp reducerii sau adaosului (discount or surcharge).
	 * @throws JposException
	 *             e
	 */
	void printRecSubtotalAdjustment(int adjustmentType, String description, long amount) throws JposException;

	/**
	 * Anulare reducere/adao la total
	 * 
	 * @param adjustmentType
	 *            Type of adjustment. See below for values. The adjustmentType parameter has one of the following values (Note: If currency value, four decimal places
	 *            are used):<br>
	 *            FPTR_AT_AMOUNT_DISCOUNT = Fixed amount discount. The amount parameter contains a currency value.<br>
	 *            FPTR_AT_AMOUNT_SURCHARGE = Fixed amount surcharge. The amount parameter contains a currency value.<br>
	 *            FPTR_AT_PERCENTAGE_DISCOUNT = Percentage discount. The amount parameter contains a percentage value.<br>
	 *            FPTR_AT_PERCENTAGE_SURCHARGE = Percentage surcharge. The amount parameter contains a percentage value.<br>
	 * @param description
	 *            descriere
	 * @param amount
	 *            Amount of the adjustment (discount or surcharge).
	 * @throws JposException
	 *             e
	 */
	void printRecSubtotalAdjustmentVoid(int adjustmentType, String description, long amount) throws JposException;

	/**
	 * Pentru preluare CIF!!<br>
	 * <p>
	 * 
	 * @param taxID
	 *            Customer identification with identification characters and tax number.
	 * @throws JposException
	 *             e
	 */
	void printRecTaxID(String taxID) throws JposException;

	/**
	 * 
	 * TOTAL
	 * <p>
	 * Scrie total pe bon, verifica totalul cu cel al app si permite inregistrarea platilor.<br>
	 * Nu se poate anula; ori se finalizeaza bonul prin plati, fie se anuleaza<br>
	 * Daca plata este partiala sau lipseste(=0) mfa printeaza linia cu TOTAL si trece in starea FISCAL_RECEIPT_TOTAL<br>
	 * Se poate apela ori cite ori pina cind platile acopera total bon<br>
	 * Mfa ramine in aceasta stare pina cind totalul platilor depaseste totalul bonului<br>
	 * Daca suma cash e mai mare decit total atunci scrie linia cu REST<br>
	 * Daca suma platilor cu documente depaseste total atunci da ERR, caci nu permite rest cash la doc plata<br>
	 * Tipurile de plata sunt cele predefinite (de la 1 la 9)<br>
	 * Schimba starea MFA fie in FISCAL RECEIPT_TOTAL fieFISCAL RECEIPT_ENDING
	 * 
	 * @param total
	 *            totalul bonului din aplicatie
	 * @param payment
	 *            suma de plata
	 * @param paymentType
	 *            tip plata /index
	 * @param description
	 *            optional, o descriere a platii
	 * @throws JposException
	 *             e
	 */
	public void printRecTotal(long total, long payment, int paymentType, String description) throws JposException;

	/**
	 * Anuleaza bonul curent<br>
	 * 
	 * @param description
	 *            mesaj optional
	 * @throws JposException
	 */
	void printRecVoid(String description) throws JposException;

	/**
	 * Programeaza niste texte care se vor tipari in timpul bonului in fc de comenzile executate<br>
	 * De ex daca se seteaza si se apeleaza prinRecItem, dupa de tiparire denumire articol se va tipari preline<br>
	 * Dupa printare se reseteaza automat<br>
	 * 
	 * @param postLine
	 *            lines
	 * @throws JposException
	 *             e
	 */
	void setPostLine(final String[] postLine) throws JposException;

	/**
	 * Programeaza niste texte care se vor tipari in timpul bonului in fc de comenzile executate<br>
	 * De ex daca se seteaza si se apeleaza prinRecItem, inainte de tiparire denumire articol se va tipari postlines<br>
	 * Dupa printare se reseteaza automat<br>
	 * 
	 * @param preLine
	 *            array cu linii de print
	 * @throws JposException
	 *             e
	 */
	void setPreLine(final String[] preLine) throws JposException;

	// daca e bon cu cif; poate fi si empty
	// void setRecCif(String cif) throws JposException;

	// --------------------------------------------------------------------------
	// Methods - Specific - Error Corrections
	// --------------------------------------------------------------------------
	/**
	 * Forces the Fiscal Printer to return to Monitor state.<br>
	 * 
	 * @throws JposException
	 * 
	 */
	void resetPrinter() throws JposException;

	/**
	 * Trece in mod PROBA
	 * Doar la inceput de zi <br>
	 * 
	 * apiValidation(true, true, true, true, true);
	 * 
	 * @throws JposException
	 *             <i>ErrorCode</i> <br>
	 *             E_ILLEGAL = Stare incorecta (end training)<br>
	 */
	void beginTraining() throws JposException;

	/**
	 * Iese din mod PROBA
	 * Doar la inceput de zi <br>
	 * 
	 * apiValidation(true, true, true, true, true);
	 * 
	 * @throws JposException
	 *             <i>ErrorCode</i> <br>
	 *             E_ILLEGAL = Stare incorecta (end training)<br>
	 */

	void endTraining() throws JposException;

	/**
	 * <b>setVatTable</b>
	 * <p>
	 * Programeaza tabela de TVA in mem fiscala <br>
	 * 
	 * @param vatRate
	 *            int[5] vatRate array cu procentele de tva;
	 * @param neplatitorTva
	 *            daca e neplatitor de tva sau nu
	 * @throws JposException
	 *             <br>
	 *             <i>errorCode</i>=MFA_ERR_FAILURE: erori grave la scriere in mem.fisc.<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_WRONG_STATE: mfa in stare gresita
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_DAY_END_REQUIRED: operatia nu se poate realiza in timpul zilei fiscale
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_LENGTH: lungime array gresita, vezi {@link #getNumVatRates()}<br>
	 *             <i>errorCode</i>=MFA_ERR_EXTENDED + <i>codeExtended</i>=MFA_ERRX_BAD_VAT: valoare incorecta procent tva
	 */
	void setVatTable(final int[] vatRate, boolean neplatitorTva) throws JposException;

	/**
	 * Returneaza cotele de tva
	 * <br>
	 * <p>
	 * 
	 * @param vatRateInputOutput
	 *            predef size 5
	 * @throws JposException
	 */
	void getVatTable(int[] vatRateInputOutput) throws JposException;

	/**
	 * Returneaza statusul de neplatitorTva, programat in mfa <br>
	 * 
	 * @return da sau nu
	 * @throws JposException
	 */
	boolean getNeplatitorTva() throws JposException;

	// --------------------------------------------------------------------------
	// Properties
	// --------------------------------------------------------------------------
	boolean getCheckTotal() throws JposException;

	void setCheckTotal(boolean checkTotal) throws JposException;

	String[] getAdditionalHeader() throws JposException;

	void setAdditionalHeader(String[] additionalHeader) throws JposException;

	String[] getAdditionalTrailer() throws JposException;

	void setAdditionalTrailer(String[] additionalTrailer) throws JposException;

	/**
	 * getMfaStatus<br>
	 * 
	 * @return status ; vezi const
	 * @throws JposException
	 */
	int getMfaStatus() throws JposException;

	int getMfaType() throws JposException;

	boolean isVending() throws JposException;

	String getMfaDescription() throws JposException;

	void selfTest() throws JposException;

	/**
	 * Numar cote vat = 5
	 * 
	 * @return num
	 * @throws JposException
	 *             e
	 */
	int getNumVatRates() throws JposException;

	int getNumTrailerLines() throws JposException;

	int getNumHeaderLines() throws JposException;

	int getNumHeaderFiscalLines() throws JposException;

	/**
	 * Daca e deschisa sau nu ziua fiscala<br>
	 * 
	 * @return true/false
	 * @throws JposException
	 *             e
	 */
	boolean getDayOpened() throws JposException;

	boolean getCoverOpen() throws JposException;

	boolean getRecNearEnd() throws JposException;

	boolean getRecEmpty() throws JposException;

	int getRemainingFiscalMemory() throws JposException;

	int getQuantityLength() throws JposException;

	int getQuantityDecimalPlaces() throws JposException;

	int getAmountDecimalPlaces() throws JposException;

	boolean getTrainingModeActive() throws JposException;

	void setMessageType(int messageType) throws JposException;

	@SuppressWarnings("javadoc")
	int getMessageType() throws JposException;

	int getMessageLength() throws JposException;

	int getDescriptionLength() throws JposException;

	// --------------------------------------------------
	// Framework Methods - specific Power Model
	// --------------------------------------------------
	void setPowerNotify(int powerNotify) throws JposException;

	int getPowerNotify() throws JposException;

	// ps_online sau pe_offline
	int getPowerState() throws JposException;

	int getBatteryCapacityRemaining() throws JposException;

	int getPowerSource() throws JposException;

	int getBatteryLowThreshold() throws JposException;

	void setBatteryLowThreshold(int i) throws JposException;

	int getBatteryCriticallyLowThreshold() throws JposException;

	void setBatteryCriticallyLowThreshold(int i) throws JposException;

	int getEnforcedShutdownDelayTime() throws JposException;

	void setEnforcedShutdownDelayTime(int delay) throws JposException;

	// --------------------------------------------------
	// Framework Events
	// --------------------------------------------------

	void addDeviceEventListener(DeviceEventListener l);

	void removeDeviceEventListener(DeviceEventListener l);

	void eraseAllData() throws JposException;

	// --------------------------------------------------
	// USB Methods
	// --------------------------------------------------

	/**
	 * copiaza fisierul de pe pos in root usb stick
	 * 
	 * @param filePath
	 * @throws JposException
	 */
	void usbCopyFile(String filePath) throws JposException;

	boolean usbStickIsPresent();

	boolean usbStickIsMounted();

	void usbMount() throws JposException;

	void usbUnmount() throws JposException;

	/**
	 * 2024 - copiere fisier de pe stick pe pos
	 * 
	 * @param filePath
	 * @throws JposException
	 */
	void usbCopyFile(String usbFilePath, String posFilePath) throws JposException;

	// --------------------------------------------------
	// Anaf Methods
	// --------------------------------------------------
	/**
	 * Incarca certificat ANAF necesar pentr verificare la import prfile <br>
	 * 
	 * @param pathFile
	 *            trbuie sa existe acest file
	 * @throws JposException
	 */
	void anafImportCertificate(String pathFile) throws JposException;

	/**
	 * incearca sa importe fisier anaf.crt de pe stick
	 * 
	 * @throws JposException
	 */
	void anafImportCertificate() throws JposException;

	/**
	 * Incarca un fisier de tip Profile0 - reset to offline <br>
	 * 
	 * @param pathFile
	 *            trbuie sa existe acest file
	 * @throws JposException
	 */
	void anafImportProfileReset(String pathFile) throws JposException;

	/**
	 * incearca sa importe fisier mReset.p7b de pe stick
	 * 
	 * @throws JposException
	 */
	void anafImportProfileReset() throws JposException;

	/**
	 * Incarca un fisier de tip Profile1 - activate online sau offline<br>
	 * 
	 * @param pathFile
	 *            trbuie sa existe acest file
	 * @throws JposException
	 */
	void anafImportProfile1(String pathFile) throws JposException;

	/**
	 * Incarca un fisier de tip Profile1 - activate online sau offline<br>
	 * Fisierul trebuie sa existe asa: "/home/anzisoft/usb/Profile_%NUI%.p7b"<br>
	 * (deci tr montat usb)
	 * 
	 * @throws JposException
	 */
	void anafImportProfile1() throws JposException;

	/**
	 * Status descriere conexiune ANAF <b>
	 * 
	 * @return
	 * @throws JposException
	 */
	String anafGetStatusDescription() throws JposException;

	/**
	 * Status conexiune ANAF conform AnafStatusEnum <br>
	 * 
	 * @throws JposException
	 */
	int anafGetStatus() throws JposException;

	/**
	 * Exporta certificat AMEF pentru site ANAF<br>
	 * Il pune pe stick cu o denumire prestabilita amef_{NUI}.crt<br>
	 * Daca se intimpla ceva si nu poate copia da eroare
	 * 
	 * @throws JposException
	 */
	void anafExportAmefCertificate() throws JposException;

	/**
	 * Sterge cert anaf, profil.. reset complet la anaf engine
	 * 
	 * @return
	 * @throws JposException
	 */
	void anafReinit() throws JposException;

	/**
	 * Transmite un hello la serverul ANAF cu scopul de verifica parametrii comunicatiei
	 * 
	 * @return daca da a fost un succes; daca nu, atunci au fot erori iar cu anafLastError se pot obtine detalii
	 * @throws JposException
	 */
	boolean anafSendTestData() throws JposException;

	boolean anafRestoreFromMemFisc() throws JposException;

	/**
	 * 
	 * @return
	 * @throws JposException
	 */
	public String anafGetLastError() throws JposException;

	/**
	 * Nu se mai foloseste; vezi {@link #xmlExport(int, int, String, String, int)}
	 * <p>
	 * Genereaza fisierul XML pe stick <br>
	 * Obs:<br>
	 * - se comporta similar ca reportJe (dpv manip. de files + tratare params)
	 * 
	 * @param xmlType
	 *            0 - deocamdata nefolosit
	 * @param filterType
	 *            1 sau 2 - vezi mfaConst
	 * @param start
	 *            interval start
	 * @param stop
	 *            interval stop
	 * @throws JposException
	 *             ex
	 * @see #xmlExport(int, int, String, String, int)
	 */
	@Deprecated
	void xmlExport(int xmlType, int filterType, String start, String stop) throws JposException;

	/**
	 * Genereaza fisierul xml si returneaza calea locala (din temp) catre fisier<br>
	 * 
	 * @param filterType
	 *            MFA_REP_TF_EOD_ORDINAL = 1 (rap dupa nr) sau MFA_REP_TF_DATE = 2 (rap dupa data)
	 * @param start
	 *            in fc de filterType, nr Z start sau data in format yyyyMM, pt luna raport
	 * @param stop
	 *            in fc de filtertType, nr Z stop sau empty pentru data stop caci nu conteaza
	 * @throws JposException
	 *             ex
	 */
	String xmlExport(int filterType, String start, String stop) throws JposException;

	/**
	 * Genereaza setul de fisiere xml si fie il pune pe stick, fie il trimite ca zip pe email<br>
	 * <p>
	 * Obs:<br>
	 * Metoda adaugata cu ocazia implementarii email - 2020.01.14<br>
	 *
	 * @param filterType
	 *            MFA_REP_TF_EOD_ORDINAL = 1 (rap dupa nr) sau MFA_REP_TF_DATE = 2 (rap dupa data)
	 * @param start
	 *            in fc de filterType, nr Z start sau data in format yyyyMM, pt luna raport
	 * @param stop
	 *            in fc de filtertType, nr Z stop sau empty pentru data stop caci nu conteaza
	 * @param outputType
	 *            MFA_REP_OUTPUT_FILE=2 sau MFA_REP_OUTPUT_EMAIL = 3
	 * @throws JposException
	 *             ex
	 * 
	 */
	void xmlExport(int xmlType, int filterType, String start, String stop, int outputType) throws JposException;

	/**
	 * Genereaza setul de fisiere xml si fie il pune pe stick, fie il trimite ca zip pe email<br>
	 * 
	 * @param xmlType
	 *            0; nefolosit
	 * @param filterType
	 *            MFA_REP_TF_EOD_ORDINAL = 1 (rap dupa nr) sau MFA_REP_TF_DATE = 2 (rap dupa data)
	 * @param start
	 *            in fc de filterType, nr Z start sau data in format yyyyMM, pt luna raport
	 * @param stop
	 *            in fc de filtertType, nr Z stop sau empty pentru data stop caci nu conteaza
	 * @param outputType
	 *            MFA_REP_OUTPUT_FILE=2 sau MFA_REP_OUTPUT_EMAIL = 3
	 * @param emailBody
	 *            conteaza doar cu MFA_REP_OUTPUT_EMAIL, un text optional; altfel empty
	 * @param automat
	 *            conteaza doar cu MFA_REP_OUTPUT_EMAIL; altfel false
	 * @throws JposException
	 */
	void xmlExport(int xmlType, int filterType, String start, String stop, int outputType, String emailBody, boolean automat) throws JposException;

	String xmlExportTest(int mfaType, String sNuiTest, String sCifTest, String luna) throws JposException;

	/**
	 * Returneaza calea catre fisier rola martor<br>
	 * <p>
	 * 
	 * @param zNumber
	 * @return string sau empty
	 * @throws JposException
	 */
	String filePrinterRM(int zNumber) throws JposException;

	/**
	 * Holds the list of all possible words to be used as indexes of the predefined payment lines (for example, “a, b, c, d, z”).
	 * <p>
	 * Those indexes are used in the printRecTotal method for the description parameter.<br>
	 * If CapPredefinedPaymentLines is true, only predefined payment lines are allowed.<br>
	 * This property is initialized by the open method<br>
	 * 
	 * @throws JposException
	 */

	// public String getPredefinedPaymentLines() throws JposException;

	/**
	 * Salveaza un sir de bytes in flash<br>
	 * user data mini-backup<br>
	 * atentie: ar trebui utilizata 1-2 ori pe zi<br>
	 * 
	 * @param data
	 *            continut fisier/date de salvat
	 * @throws JposException
	 *             ex
	 */
	void userDataSave(final byte[] data) throws JposException;

	/**
	 * Face restore daca a fost salvat ceva in zona de date, getUserDataSize.gt0 <br>
	 * 
	 * @param inoutData
	 *            valoare returnata; size =getUserDataSize
	 * @throws JposException
	 *             ex
	 */
	void userDataRestore(byte[] inoutData) throws JposException;

	/**
	 * Dimensiune maxima ce poate fi salvata pentru userData <br>
	 * 
	 * @return int
	 * @throws JposException
	 *             ex
	 */
	int getUserBigDataSizeMax() throws JposException;

	/**
	 * Daca a fost salvat ceva user data at asta e size <br>
	 * 
	 * @return int
	 * @throws JposException
	 *             ex
	 */
	int getUserBigDataSize() throws JposException;

	/**
	 * Salveaza un sir de bytes in flash<br>
	 * user data mini-backup<br>
	 * atentie: ar trebui utilizata 1-2 ori pe zi<br>
	 * 
	 * @param data
	 *            continut fisier/date de salvat
	 * @throws JposException
	 *             ex
	 */
	void userBigDataSave(final byte[] data) throws JposException;

	/**
	 * Face restore daca a fost salvat ceva in zona de date, getUserDataSize.gt0 <br>
	 * 
	 * @param inoutData
	 *            valoare returnata; size =getUserDataSize
	 * @throws JposException
	 *             ex
	 */
	void userBigDataRestore(byte[] inoutData) throws JposException;

	/**
	 * Dimensiune maxima ce poate fi salvata pentru userData <br>
	 * 
	 * @return int
	 * @throws JposException
	 *             ex
	 */
	int getUserDataSizeMax() throws JposException;

	/**
	 * Daca a fost salvat ceva user data at asta e size <br>
	 * 
	 * @return int
	 * @throws JposException
	 *             ex
	 */
	int getUserDataSize() throws JposException;

	/**
	 * type=1 - info
	 * type=2 - warn
	 * type=3 - alert
	 * 
	 * @param type
	 *            1,2,3
	 */
	void beep(int type);

	/**
	 * Importa PK si cert public din folder dat ca param
	 * pk.pem si cert.pem
	 * 
	 * @param pathToFolder
	 *            aici tr sa se gaseasca files!!
	 * @return daca s-a importat sau nu
	 * @throws JposException
	 */
	boolean saveAmefCertInMf(String pathToFolder) throws JposException;

	/**
	 * 
	 */
	void openDrawer();

	/**
	 * Deschide comunicatie cu cintarul dse tip type, pe port default(ttySC2)<br>
	 * Parametrul vine din zsales:<br>
	 * 1-Digi DS980<br>
	 * 2-Dibal KS400<br>
	 * <p>
	 * 
	 * @throws JposException
	 */
	void scaleOpen(int type) throws JposException;

	/**
	 * Returneaza cantitatea de la cintar (cant * 1000)
	 * Foloseste timeout default 10 sec
	 * 
	 * @return cantitatea sau -1
	 * @throws JposException
	 */
	int scaleReadWeight() throws JposException;

	/**
	 * Returneaza cantitatea de la cintar (cant * 1000)
	 * Foloseste timeout din parametru; mai mare decit 0
	 * 
	 * @param timeoutSeconds
	 *            in secunde
	 * @return cantitatea sau -1
	 * @throws JposException
	 * 
	 */
	int scaleReadWeight(int timeoutSeconds) throws JposException;

	// boolean sendNotif(NotifBean notif);

	// boolean sendNotifNow(NotifBean notif);

	void setMfaProperties(String type, String value) throws JposException;

	void resetMfaProperties(String key) throws JposException;

	String getMfaProperties(String key) throws JposException;

	boolean isMfaFiscal();

	/**
	 * Deschide comunicatie cu EFT-POS pe port default 2<br>
	 * <p>
	 * 
	 * @throws JposException
	 */
	// void eftOpen(int type) throws JposException;

	/**
	 * Efectueaza plata sumei la EFT<br>
	 * Daca e ok returneaza true; daca nu, trebuie vazut response
	 * 
	 * @param amount
	 *            suma de plata in format numeric intreg - deci e suma* 100
	 * @return da sau nu
	 * @throws JposException
	 *             erori de seriala sau protocol
	 */
	@Deprecated
	public boolean eftPayment(long amount) throws JposException;

	/**
	 * Vers 2.0 de fc pt plata cu card
	 * 
	 * @param docPlataID
	 *            id palat din backoffice
	 * @param amount
	 * @return
	 * @throws JposException
	 */
	public boolean eftPayment(String docPlataID, long amount) throws JposException;

	/**
	 * Ultimul raspuns de la EFT<br>
	 * Daca plata a fost refuzata atunci EFT intoarce un motiv pe care il dau catre apps<br>
	 * 
	 * @return reason de refuz plata sau alt mesaj de le eft
	 * @throws JposException
	 */
	public String eftLastResponse() throws JposException;

	/**
	 * Returneaza info ref la ultiuma plata
	 * 
	 * @return
	 * @throws JposException
	 */
	public String eftLastSaleInfo() throws JposException;

	/**
	 * inchide ziua la efts
	 * 
	 * @return daca da sau nu iar rasp se citeste cu getLastResponse
	 * @throws JposException
	 */
	public boolean eftSettlement(String docPlataID) throws JposException;

	public boolean eftHasSettlement(String docPlataId) throws JposException;

	/**
	 * Returneaza lista cu instantele eft montate pe pos inclusiv status actual
	 * 
	 * @return
	 */
	public List<String> getEftList();

	/**
	 * reseteaza lista curenta de eft, aduce lista de eft din AnziDrive si o instaleaza<br>
	 * se poate apela oricind <br>
	 * 
	 * @return
	 */
	public void eftReinitList();

	/**
	 * daca eft s-au initializat corect
	 * 
	 * @return
	 */
	public boolean isEftLoadedOk();

	/**
	 * Verifica daca id este in lista de eft
	 * 
	 * @param id
	 *            eftId de validat
	 * @return da sau nu
	 * @throws JposException
	 *             daca lista de eft este in curs de incarcare
	 */
	boolean checkIfExistsPaymentId(String id) throws JposException;

	/**
	 * Verifica daca terminalul asociat cu un id este incarcat OK (dpv al initializarii cu seriala)
	 * 
	 * @param id
	 *            eftId de validat
	 * @return da sau nu
	 * @throws JposException
	 *             daca lista de eft este in curs de incarcare
	 */
	boolean checkIfPaymentIdIsOk(String id) throws JposException;

	// public void eftClose() throws JposException;

	public boolean isInternetAvailable() throws JposException;

	/**
	 * Daca mfa e offline; nu are conexiune la AnziDrive
	 * 
	 * @return
	 */
	public boolean isOffline();

	/**
	 * Cite zile mai sunt pina se blocheaza/se reseteaza functionalitatile online
	 * 
	 * @return
	 */
	public int getRemainingsOfflineDays();

	public void simulateEventsPrinterOff(boolean off);

	/**
	 * daca e defiscal sau nu<br>
	 * pentru zsales sa stie daca tr activat buton sau nu<br>
	 * 
	 * @return
	 */
	public boolean isDefisc();

	public void reset();

	//--functii stock

	/**
	 * daca este configurat modul de lucru cu stoc
	 * daca nu este config at toate apelurile de fc de stock vor da err
	 * 
	 * @return
	 */
	public boolean isWithStock();

	/**
	 * Get stock
	 * 
	 * @param itemCode
	 * @return
	 * @throws JposException
	 */
	public int getStock(String itemCode) throws JposException;

	/**
	 * add stock
	 * 
	 * @param itemCode
	 * @param quantity
	 * @return
	 * @throws JposException
	 */
	public boolean addStock(String itemCode, int quantity) throws JposException;

	/**
	 * 
	 * @param itemCode
	 * @param quantity
	 * @return
	 * @throws JposException
	 */
	public boolean removeStock(String itemCode, int quantity) throws JposException;

	/**
	 * vezi si MfaStockType
	 * 
	 * @param inventory
	 * @return
	 * @throws JposException
	 */
	public boolean saveInventory(MfaStockType[] inventory) throws JposException;

	/**
	 * 
	 * @param itemCode
	 * @param quantity
	 * @return
	 * @throws JposException
	 */
	public boolean resetStock(String itemCode, int quantity) throws JposException;

	// functii NIR
	/**
	 * 
	 * @return
	 */
	public String[] getNirInTransit() throws JposException;

	/**
	 * 
	 * @param nirDescription
	 * @return
	 */
	public boolean confirmNirInTransit(String nirNumber) throws JposException;

	public void setZnetHeartbeatInterval(int seconds);

	public long sendNotif(String category, String subject, String message);

	/**
	 * 0 - nu exista
	 * -1 - consumat
	 * -2 - expirat
	 * >0 - valabil si exprima suma voucherului
	 * 
	 * @param serial
	 * @param voucherTypeZSales 1 sau 2
	 * @param customerId orice inclusiv null
	 * @return
	 */
	//public int checkVoucherAD(String serial) throws JposException;

	public int checkVoucherAD(String serial, int voucherTypeZSales, String customerId) throws JposException;

	/**
	 * Returneaza o lista cu voucherele valide pentru un anumit client
	 * E o lista de strings de forma: barcodeVoucher;amount
	 *  
	 * @param customerId
	 * @return
	 * @throws JposException
	 */
	public List<String> getVoucherAD(String customerId) throws JposException;
	/**
	 * 
	 * 
	 * @param serial
	 *            serial voucher
	 * @return
	 */
	public void consumeVoucherADFromReceipt(String serial) throws JposException;

	public void consumeVoucherADFromReceipt(String serial, String customerId) throws JposException;

	/**
	 * 
	 * @param serial
	 */
	public void consumeVoucherADRefund(String serial) throws JposException;
	

	public void consumeVoucherADRefund(String serial, String customerID) throws JposException;

	/**
	 * 
	 * @param serial
	 */
	public void revertVoucherAD(String serial) throws JposException;

	/**
	 * Functie completa de aprobare cu parametrii
	 * 
	 * @param verificationCode
	 *            codul de verificat
	 * @param operType
	 *            tipul operatiei.. de ex plata
	 * @param params
	 *            optionali parametrii care folosesc de ex limita suma
	 * @return
	 */
	public boolean getApprovalOfflineOTP(int verificationCode, String operType, String... params)  throws JposException;

	/**
	 * Functie sipla care veririfca doar un cod de 6 cifre
	 * 
	 * @param verificationCode
	 * @return
	 */
	public boolean getApprovalOfflineOTP(int verificationCode) throws JposException;
	
	/**
	 * Creaza un voucher in AD de tip SGR cu suma transmisa ca parametru
	 * Returneaza un code pt voucherBarcode
	 * 
	 * @param amount 
	 * @return
	 * @throws JposException
	 */
	public String createVoucherSGR(int amount) throws JposException;
}

/**
 * PiMfaControl.java
 * 
 * 
 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.device.controls;

import java.util.Vector;

import jpos.BaseControl;
import jpos.FiscalPrinterConst;
import jpos.FiscalPrinterControl114;
import jpos.JposConst;
import jpos.JposException;
import jpos.events.DataEvent;
import jpos.events.DirectIOEvent;
import jpos.events.DirectIOListener;
import jpos.events.ErrorEvent;
import jpos.events.ErrorListener;
import jpos.events.OutputCompleteEvent;
import jpos.events.OutputCompleteListener;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;
import jpos.services.BaseService;
import jpos.services.EventCallbacks;
import ro.anzisoft.device.services.MfaDevice;

/**
 * @author omatei01 (13 iul. 2018)
 * @version 1.0
 */
//implements MFiscalInterface,
class MFiscalControl extends MBaseControl implements FiscalPrinterControl114, FiscalPrinterConst, JposConst {
	//private static final Logger LOG = LoggerFactory.getLogger(MFiscalControl.class);

	private MfaDevice service;

	protected Vector<DirectIOListener> directIOListeners;
	protected Vector<StatusUpdateListener> statusUpdateListeners;

	protected Vector<OutputCompleteListener> outputCompleteListeners;
	protected Vector<ErrorListener> errorListeners;

	public MFiscalControl() {
		// Initialize base class instance data
		deviceControlDescription = "Modul Fiscal Anzi Control";
		deviceControlVersion = 1;

		directIOListeners = new Vector<>();
		outputCompleteListeners = new Vector<>();
		errorListeners = new Vector<>();
		statusUpdateListeners = new Vector<>();
	}

	//--------------------------------------------------------------------------
	// Methods
	//--------------------------------------------------------------------------

	//--------------------------------------------------------------------------
	// Methods - Specific - Presetting Fiscal
	//--------------------------------------------------------------------------

	/**
	 * Sets the Fiscal Printer’s date and time.<br>
	 * The date and time is passed as a string in the format “ddmmyyyyhhmm”, where:<br>
	 * dd=day of the month (1 - 31)<br>
	 * mm=month (1 - 12)<br>
	 * yyyy=year (1997-)<br>
	 * hh=hour (0-23)<br>
	 * mm=minutes (0-59)<br>
	 * <p>
	 * This method can only be called while DayOpened is false.<br>
	 * <p>
	 * 
	 * @param date Date and time as a string.
	 * @throws JposException ErrorCode property are:<br>
	 *         E_ILLEGAL = The Fiscal Printer has already begun the fiscal day (see the DayOpened property).<br>
	 *         E_EXTENDED cu ErrorCodeExtended = EFPTR_BAD_DATE: One of the entries of the date parameters is invalid.<br>
	 * 
	 */
	@Override
	public void setDate(String date) throws JposException {
		if (!controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Sets one of the fiscal receipt header lines.<br>
	 * The text set by this method will be stored by the Fiscal Printer and retained across power losses.<br>
	 * <p>
	 * The lineNumber parameter must be between 1 and the value of the NumHeaderLines property. <br>
	 * <p>
	 * If text is an empty string (“”), then the header line is unset and will not be printed. <br>
	 * The doubleWidth characters will be printed if the Fiscal Printer supports them.<br>
	 * <p>
	 * See the CapDoubleWidth property to determine if they are supported. <br>
	 * <p>
	 * This method is only supported if CapSetHeader is true. <br>
	 * <p>
	 * This method can only be called while DayOpened is false.<br>
	 * <br>
	 * <p>
	 * 
	 * @param text Text to which to set the header line.
	 * @param doubleWidth Print this line in double wide characters.
	 * @throws JposException ErrorCode:
	 *         1)E_ILLEGAL = One of the following errors occurred:<br>
	 *         •The Fiscal Printer does not support setting header lines (see the CapSetHeader property), or<br>
	 *         •The Fiscal Printer has already begun the fiscal day (see the DayOpened property), or<br>
	 *         •the lineNumber parameter was invalid.<br>
	 *         2) E_EXTENDED cu ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION: The text parameter<br>
	 * 
	 * 
	 */
	@Override
	public void setHeaderLine(int lineNumber, String text, boolean doubleWidth) throws JposException {
		if (!controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Sets the POS and cashier identifiers.<br>
	 * These values will be printed when each fiscal receipt is closed.<br>
	 * This method is only supported if CapSetPOSID is true.<br>
	 * 
	 * This method can only be called while DayOpened is false.<br>
	 * 
	 * @param POSID Identifier for the POS system.
	 * @param cashierID Identifier of the current cashier.
	 * @throws JposException E_ILLEGAL daca:<br>
	 *         •The Fiscal Printer does not support setting the POS identifier (see the CapSetPOSID property), or<br>
	 *         •The printer has already begun the fiscal day (see the DayOpened property), or<br>
	 *         •Either the POSID or cashierID parameter is invalid.<br>
	 * 
	 */
	@Override
	public void setPOSID(String POSID, String cashierID) throws JposException {
		if (!controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Sets the store fiscal ID.<br>
	 * This value is retained by the Fiscal Printer even after power failures. <br>
	 * This ID is automatically printed by the Fiscal Printer after the fiscal receipt header lines.<br>
	 * This method is only supported if CapSetStoreFiscalID is true. This method can only be called while DayOpened is false.<br>
	 * <p>
	 * 
	 * @param ID Fiscal identifier
	 * @throws JposException E_ILLEGAL<br>
	 *         •The Fiscal Printer does not support setting the store fiscal identifier (see the CapSetStoreFiscalID property), or<br>
	 *         •The Fiscal Printer has already begun the fiscal day (see the DayOpened property), or<br>
	 *         •The ID parameter was invalid.<br>
	 * 
	 */
	@Override
	public void setStoreFiscalID(String ID) throws JposException {
		if (!controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Sets one of the fiscal receipt trailer lines.<br>
	 * The text set by this method will be stored by the Fiscal Printer and retained across power losses.<br>
	 * The lineNumber parameter must be between 1 and the value of the NumTrailerLines property. <br>
	 * If text is an empty string (“”), then the trailer line is unset and will not be printed. <br>
	 * The doubleWidth characters will be printed if the Fiscal Printer supports them. <br>
	 * 
	 * @param lineNumber Line number of the trailer line to set.
	 * @param text Text to which to set the trailer line.
	 * @param doubleWidth Print this line in double wide characters.
	 * @throws JposException
	 */
	@Override
	public void setTrailerLine(int lineNumber, String text, boolean doubleWidth) throws JposException {
		if (!controlOpen) { throw new JposException(JPOS_E_CLOSED, "Control not opened"); }
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * setVatTable
	 * <p>
	 * Sends the VAT table built inside the Service to the Fiscal Printer. <br>
	 * The VAT table is built one entry at a time using the setVatValue method. <br>
	 * This method is only supported if CapHasVatTable and CapSetVatTable are true. <br>
	 * This method can only be called while DayOpened is false <br>
	 * 
	 * <p>
	 * 
	 * @throws JposException E_ILLEGAL
	 *         One of the following errors occurred:
	 *         •The Fiscal Printer does not support VAT tables or their setting (see the CapHasVatTable or CapSetVatTable property)<br>
	 *         , or
	 *         •The Fiscal Printer has already begun the fiscal day (see the DayOpened property)<br>
	 */
	@Override
	public void setVatTable() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Fiscal Receipt
	//--------------------------------------------------------------------------

	/**
	 * Sets the value of a specific VAT class in the VAT table.<br>
	 * The VAT table is built one entry at a time in the Service using this method.<br>
	 * The entire table is then sent to the Fiscal Printer at one time using the setVatTable method.<br>
	 * This method is only supported if CapHasVatTable and CapSetVatTable<br>
	 * <p>
	 * 
	 * @param vatID Index of the VAT table entry to set.
	 * @param vatValue Tax value as a percentage.
	 * @throws JposException E_ILLEGAL daca:<br>
	 *         •The Fiscal Printer does not support VAT tables (see the CapHasVatTable or CapSetVatTable property), or<br>
	 *         •The Fiscal Printer has already begun the fiscal day (see the DayOpened property), or<br>
	 *         •The Fiscal Printer does not support changing an existing VAT value (see the CapSetVatTable property).<br>
	 */
	@Override
	public void setVatValue(int vatID, String vatValue) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * 
	 * Initiates fiscal printing to the receipt station.<br>
	 * <p>
	 * If CapFiscalReceiptType=true the receipt type must be defined in {@link #getFiscalReceiptType()} and a header line according to the specified receipt type will be printed.<br>
	 * <p>
	 * If this is the first call to the beginFiscalReceipt method, the Fiscal Day will be started and the DayOpened property will be set to true.<br>
	 * If printHeader and CapIndependentHeader are both true all defined header lines will be printed before control is returned. <br>
	 * Otherwise, header lines will be printed when the first item is sold in the case they are not printed at the end of the preceding receipt. <br>
	 * If CapAdditionalHeader is true, application specific header lines defined by the AdditionalHeader property will be printed after the fixed header lines.<br>
	 * If this method is successful, the PrinterState property will be changed to FPTR_PS_FISCAL_RECEIPT.
	 * <p>
	 * 
	 * @param printHeader Indicates if the header lines are to be printed at this time.
	 * @throws JposException ErrorCode:<br>
	 *         E_ILLEGAL = An invalid receipt type was specified, or<br>
	 *         E_EXTENDED<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition.<br>
	 *         ErrorCodeExtended = EFPTR_MISSING_SET_CURRENCY: The new receipt cannot be opened, the Fiscal Printer is expecting the current currency to be changed by calling setCurrency method.<br>
	 *         ErrorCodeExtended = EFPTR_DAY_END_REQUIRED: The completion of the fiscal day is required by calling printZReport. No further fiscal receipts or documents can be started before this is done.<br>
	 */
	@Override
	public void beginFiscalReceipt(boolean printHeader) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Terminates fiscal printing to the receipt station.<br>
	 * <p>
	 * If printHeader is false, this method will close the current fiscal receipt, print the trailer lines, if they were not already printed after the total lines, and cut it.<br>
	 * If printHeader is true additionally the header of the next receipt will be printed before cutting the receipt, otherwise the header will be printed when beginning the next receipt.<br>
	 * <p>
	 * All functions carried out by this method will be completed before this call returns.<br>
	 * <p>
	 * If CapAdditionalTrailer is true application specific trailer lines defined by the AdditionalTrailer property will be printed after the fiscal trailer lines.<br>
	 * <p>
	 * If this method is successful, the PrinterState property will be changed to FPTR_PS_MONITOR.<br>
	 * <p>
	 * 
	 * @param printHeader Indicates if the header lines of the following receipt are to be printed at this time.
	 * @throws JposException ErrorCode=E_EXTENDED <br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt Ending state<br>
	 * 
	 */
	@Override
	public void endFiscalReceipt(boolean printHeader) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void printDuplicateReceipt() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Operatii de casa<br>
	 * <p>
	 * Prints a cash-in or cash-out receipt amount.<br>
	 * <p>
	 * This method is only allowed if CapFiscalReceiptType is true and the {@link #setFiscalReceiptType(int)} is set to FPTR_RT_CASH_IN or FPTR_RT_CASH_OUT and
	 * the fiscal Fiscal Printer is in the Fiscal Receipt state.<br>
	 * <p>
	 * 
	 * @param amount Amount to be incremented or decremented.
	 * @throws JposException ErrorCode=<br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = The Fiscal Printer does not support this method.<br>
	 *         E_EXTENDED =<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN: The Fiscal Printer cover is open.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY: The journal station is out of paper.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY: The receipt station is out of paper.(Only applies if AsyncMode is false.)<br>
	 * 
	 * @see #beginFiscalReceipt(boolean)
	 */
	@Override
	public void printRecCash(long amount) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints a receipt item for a sold item on the station specified by the FiscalReceiptStation property.<br>
	 * <p>
	 * If the quantity parameter is zero, then a single item quantity will be assumed.<br>
	 * <p>
	 * Minimum parameters are description and price or description, price, quantity, and unitPrice.<br>
	 * <p>
	 * VatInfo parameter contains a VAT table identifier if CapHasVatTable is true. Otherwise, it contains a VAT amount.<br>
	 * <p>
	 * If {@link #getCapPostPreLine()} is true additional application specific lines defined by the {@link #setPostLine(String)} and {@link #setPreLine(String)}properties will be printed.<br>
	 * After printing these lines PostLine and PreLine will be reset to an empty string.<br>
	 * <p>
	 * 
	 * @param description Text describing the item sold.
	 * @param price Price of the line item.
	 * @param quantity Number of items. If zero, a single item is assumed.
	 * @param vatInfo VAT rate identifier or amount. If not used a zero must be transferred.
	 * @param unitPrice Price of each item. If not used a zero must be transferred.
	 * @param unitName Name of the unit i.e., “kg” or “ltr” or “pcs”. If not used an empty string (“”) must be transferred
	 * @throws JposException ErrorCode<br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_EXTENDED = cu ErrorCodeExtended = <br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE:The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN: The Fiscal Printer cover is open.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY: The journal station is out of paper. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY: The receipt station is out of paper.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY: The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_QUANTITY: The quantity is invalid.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_PRICE: The unit price is invalid. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION: The discount description is too long or contains a reserved word. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT: The VAT parameter is invalid. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_RECEIPT_TOTAL_OVERFLOW: The receipt total has overflowed. (Only applies if AsyncMode is false.)<br>
	 */
	@Override
	public void printRecItem(String description, long price, int quantity, int vatInfo, long unitPrice, String unitName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Cancels one or more items that has been added to the receipt and prints a void description on the station.
	 * <p>
	 * <i>price</i> is a positive number, it will be printed as a negative and will be decremented from the totals registers.<br>
	 * If CapOnlyVoidLastItem is true, only the last item transferred to the Fiscal Printer can be voided exclusive an adjustment line for this item.<br>
	 * <p>
	 * If CapPostPreLine is true, additional application specific lines defined by the PostLine and PreLine properties will be printed.<br>
	 * After printing these lines PostLine and PreLine will be reset to an empty string.<br>
	 * <br>
	 * <p>
	 * 
	 * @param description Text describing the item to be voided.
	 * @param price Price of the item to be voided.
	 * @param quantity Quantity of item to be voided. If zero, a single item is assumed.
	 * @param vatInfo VAT rate identifier or amount. If not used a zero must be transferred.
	 * @param unitPrice Price of each item. If not used a zero must be transferred.
	 * @param unitName Name of the unit i.e., “kg” or “ltr” or “pcs”.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = Cancelling is not allowed at this ticket state. May be because no item has been sold previously. (See CapOnlyVoidLastItem.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_QUANTITY: The quantity is invalid.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_PRICE: The unit price is invalid. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION: The discount description is too long or contains a reserved word. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT: The VAT parameter is invalid. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_RECEIPT_TOTAL_OVERFLOW: The receipt total has overflowed. (Only applies if AsyncMode is false.)<br>
	 */
	@Override
	public void printRecItemVoid(String description, long price, int quantity, int vatInfo, long unitPrice, String unitName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Applies and prints a discount or a surcharge to the last receipt item sold on the station.
	 * <p>
	 * This discount may be either a fixed currency amount or a percentage amount relating to the last item.
	 * <p>
	 * If CapOrderAdjustmentFirst is true, the method must be called before the corresponding printRecItem method.
	 * If CapOrderAdjustmentFirst is false, the method must be called after the printRecItem.
	 * <p>
	 * This discount/surcharge may be either a fixed currency amount or a percentage amount relating to the last item.
	 * <p>
	 * If the discount amount is greater than the receipt subtotal, an error occurs since the subtotal can never be negative.<br>
	 * <p>
	 * In many countries discount operations cause the printing of a fixed line of text expressing the kind of operation that has been performed.<br>
	 * <p>
	 * The VatInfo parameter contains a VAT table identifier if CapHasVatTable is true. Otherwise, it contains a VAT amount.<br>
	 * <p>
	 * Fixed amount discounts/surcharges are only supported if the property CapAmountAdjustment is true.<br>
	 * Percentage discounts are only supported if CapPercentAdjustment is true.<br>
	 * <p>
	 * If CapPostPreLine is true an additional application specific line defined by the PreLine property will be printed.
	 * After printing this line PreLine will be reset to an empty string.<br>
	 * <p>
	 * 
	 * @param adjustmentType Type of adjustment. See below for values.The adjustmentType parameter has one of the following values (Note: If currency value, four decimal places are used):<br>
	 *        FPTR_AT_AMOUNT_DISCOUNT = Fixed amount discount. The amount parameter contains a currency value.<br>
	 *        FPTR_AT_AMOUNT_SURCHARGE = Fixed amount surcharge. The amount parameter contains a currency value.<br>
	 *        FPTR_AT_PERCENTAGE_DISCOUNT = Percentage discount. The amount parameter contains a percentage value.<br>
	 *        FPTR_AT_PERCENTAGE_SURCHARGE = Percentage surcharge. The amount parameter contains a percentage value.<br>
	 *        FPTR_AT_COUPON_AMOUNT_DISCOUNT = Fixed amount discount for an advertising coupon. The amount parameter contains a currency value. The coupon is registered by the fiscal memory.
	 *        If coupons are not registered at fiscal memory separately from ordinary discounts in the actual country then it is recommend to use FPTR_AT_AMOUNT_DISCOUNT instead.<br>
	 *        FPTR_AT_COUPON_PERCENTAGE_DISCOUNT = Percentage discount for an advertising coupon. The amount parameter contains a percentage value. The coupon is registered by the fiscal memory. If coupons are not registered at fiscal memory separately
	 *        from ordinary discounts in the actual country then it is recommend to use FPTR_AT_PERCENTAGE_DISCOUNT instead. <br>
	 * @param description Text describing the adjustment.
	 * @param amount Amount of the adjustment.
	 * @param vatInfo VAT rate identifier or amount.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = One of the following errors occurred:<br>
	 *         •The Fiscal Printer does not support fixed amount adjustments (see the CapAmountAdjustment property).<br>
	 *         •The Fiscal Printer does not support percentage discounts (see the CapPercentAdjustment property).<br>
	 *         •The adjustmentType parameter is invalid.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = FPTR_BAD_ITEM_AMOUNT:The discount amount is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT:The VAT parameter is invalid.<br>
	 */
	@Override
	public void printRecItemAdjustment(int adjustmentType, String description, long amount, int vatInfo) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Cancels an adjustment that has been added to fiscal receipt before and prints a cancellation line with a negative amount on the station.
	 * <p>
	 * This adjustment cancellation amount may be either a fixed currency amount or a percentage amount.
	 * <p>
	 * The VatInfo parameter contains a VAT table identifier if CapHasVatTable is true. Otherwise, it contains a VAT amount.
	 * <p>
	 * Fixed amount adjustment cancellations are only supported if the property CapAmountAdjustment is true.
	 * Percentage adjustment cancellations are only supported if CapPercentAdjustment is true.
	 * <p>
	 * If CapPostPreLine is true an additional application specific line defined by the PreLine propert
	 * <p>
	 * 
	 * @param adjustmentType Type of adjustment to be voided. {@link #printRecItemAdjustment(int, String, long, int)}
	 * @param description Text describing the adjustment to be voided.
	 * @param amount Amount of the adjustment to be voided.
	 * @param vatInfo VAT rate identifier or amount.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = One of the following errors occurred:<br>
	 *         •The Fiscal Printer does not support fixed amount adjustments (see the CapAmountAdjustment property).<br>
	 *         •The Fiscal Printer does not support percentage discounts (see the CapPercentAdjustment property).<br>
	 *         •The adjustmentType parameter is invalid.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = FPTR_BAD_ITEM_AMOUNT:The discount amount is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word. (Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT:The VAT parameter is invalid.<br>
	 */
	@Override
	public void printRecItemAdjustmentVoid(int adjustmentType, String description, long amount, int vatInfo) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints a receipt fuel item on the station specified by the FiscalReceiptStation property.
	 * <p>
	 * vatInfo parameter contains a VAT table identifier
	 * <p>
	 * 
	 * @param description Text describing the fuel product.
	 * @param price Price of the fuel item.
	 * @param quantity Number of items. If zero, a single item is assumed.
	 * @param vatInfo VAT rate identifier or amount. If not used a zero must be transferred.
	 * @param unitPrice Price of the fuel item per volume.
	 * @param unitName Name of the volume unit, i.e., “ltr”. If not used an empty string (“”) must be transferred
	 * @param specialTax Special tax amount, e.g., road tax. If not used a zero must be transferred.
	 * @param specialTaxName Name of the special tax.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = This method is not supported.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_QUANTITY:The quantity is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_PRICE: The unit price is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT:The VAT parameter is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_RECEIPT_TOTAL_OVERFLOW:The receipt total has overflowed.<br>
	 * 
	 */
	@Override
	public void printRecItemFuel(String description, long price, int quantity, int vatInfo, long unitPrice, String unitName, long specialTax, String specialTaxName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Called to void a fuel item on the station specified by the FiscalReceiptStation property.<br>
	 * If CapOnlyVoidLastItem is true, only the last fuel item transferred to the Fiscal Printer can be voided.<br>
	 * This method is performed synchronously if AsyncMode is false, and asynchronously if AsyncMode is true.<br>
	 * <p>
	 * 
	 * 
	 * @param description Text describing the fuel product.
	 * @param price Price of the fuel item. If not used a zero must be transferred.
	 * @param vatInfo VAT rate identifier or amount. If not used a zero must be transferred.
	 * @param specialTax Special tax amount, e.g.,
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = This method is not supported.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_PRICE: The unit price is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT:The VAT parameter is invalid.<br>
	 * 
	 */
	@Override
	public void printRecItemFuelVoid(String description, long price, int vatInfo, long specialTax) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Processes one or more item refunds. The amount is positive, but it is printed as a negative number and the totals registers are decremented.<br>
	 * <p>
	 * If unitAmount and quantity are non zero then the amount parameter corresponds to the product of quantity and unitAmount.
	 * Otherwise this method has the same functionality as the method printRecRefund.
	 * Some fixed text, along with the description, will be printed on the station defined by the FiscalReceiptStation property to indicate that a refund has occurred.
	 * <p>
	 * The vatInfo parameter contains a VAT table identifier if CapHasVatTable is true. Otherwise it, contains a VAT amount.<br>
	 * <p>
	 * If CapPostPreLine is true an additional application specific line defined by the PreLine property will be printed.
	 * After printing this line, PreLine will be reset to an empty string. <br>
	 * <p>
	 * 
	 * @param description Text describing the refund.
	 * @param amount The amount of the refund line.
	 * @param quantity Number of items. If zero, a single item is assumed.
	 * @param vatInfo VAT rate identifier or amount. If not used a zero must be transferred.
	 * @param unitAmount Amount of each refund item. If not used a zero must be transferred.
	 * @param unitName Name of the unit i.e., “kg” or “ltr” or “pcs”.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_QUANTITY:The quantity is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_PRICE: The unit price is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_AMOUNT: The refund amount is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT:The VAT parameter is invalid.<br>
	 * 
	 * @see #printRecRefund(String, long, int)
	 * 
	 */
	@Override
	public void printRecItemRefund(String description, long amount, int quantity, int vatInfo, long unitAmount, String unitName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Processes a void of one or more item refunds. The amount is positive and the totals registers are incremented.
	 * <p>
	 * If unitAmount and quantity are non zero then the amount parameter corresponds to the product of quantity and unitAmount. Otherwise this method has the same functionality as the method printRecRefundVoid.
	 * <p>
	 * Some fixed text, along with the description, will be printed on the station defined by the FiscalReceiptStation property to indicate that a void of a refund has occurred.
	 * <p>
	 * The vatInfo parameter contains a VAT table identifier if CapHasVatTable is true. Otherwise it, contains a VAT amount.
	 * <p>
	 * If CapOnlyVoidLastItem is true, only the last refund item transferred to the Fiscal Printer can be voided.
	 * <br>
	 * <p>
	 * 
	 * @param description nume
	 * @param amount long money
	 * @param quantity long quant
	 * @param vatInfo int proc
	 * @param unitAmount
	 * @param unitName
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = Cancelling is not allowed at this ticket state. May be because no item has been sold previously.(See CapOnlyVoidLastItem.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_QUANTITY:The quantity is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_PRICE: The unit price is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_AMOUNT: The refund amount is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_VAT:The VAT parameter is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_RECEIPT_TOTAL_OVERFLOW: The receipt total has overflowed.<br>
	 * 
	 * @see #printRecItemRefund(String, long, int, int, long, String)
	 * @see #printRecRefundVoid(String, long, int)
	 * 
	 */
	@Override
	public void printRecItemRefundVoid(String description, long amount, int quantity, int vatInfo, long unitAmount, String unitName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints a message on the fiscal receipt on the station.
	 * <p>
	 * The length of an individual message is limited to the number of characters given in the {@link #getMessageLength()}
	 * <p>
	 * The kind of message to be printed is defined by the MessageType property.
	 * <p>
	 * This method is only supported if CapAdditionalLines is true.
	 * <p>
	 * This method is only supported when the Fiscal Printer is in one of the Fiscal Receipt states.
	 * <p>
	 * <br>
	 * 
	 * @param message Text message to print.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION:The discount description is too long or contains a reserved word.<br>
	 */
	@Override
	public void printRecMessage(String message) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (NOT IMPLEMENTED)
	 * <p>
	 * CapReceiptNotPaid=false!!
	 * <p>
	 * Indicates a part of the receipt’s total to not be paid.
	 * <p>
	 * Some fixed text, along with the description, will be printed on the station to indicate that part of the receipt total has not been paid. T
	 * his method is only supported if CapReceiptNotPaid is true.
	 * <p>
	 * If this method is successful, the PrinterState property will remain in FPTR_PS_FISCAL_RECEIPT_TOTAL state or
	 * change to the value FPTR_PS_FISCAL_RECEIPT_ENDING depending upon whether the entire receipt total is now accounted for or not.
	 * <br>
	 * <p>
	 * 
	 * @param description
	 * @param amount
	 * @throws JposException e
	 * 
	 */
	@Override
	public void printRecNotPaid(String description, long amount) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (NOT IMPLEMENTED)
	 * <p>
	 * CapPackageAdjustment=false!!!
	 * <p>
	 * Called to give an adjustment for a package of some items booked before.<br>
	 * This adjustment (discount/surcharge) may be either a fixed currency amount or a percentage amount relating to items combined to an adjustment package.<br>
	 * Each item of the package must be transferred before.<br>
	 * Fixed amount adjustments are only supported if CapPackageAdjustment is true.
	 * <p>
	 * 
	 * @param adjustmentType
	 * @param description
	 * @param vatAdjustment
	 * @throws JposException
	 */
	@Override
	public void printRecPackageAdjustment(int adjustmentType, String description, String vatAdjustment) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * CapPackageAdjustment=false!!!
	 * <p>
	 * <br>
	 * <p>
	 * 
	 * @param adjustmentType
	 * @param vatAdjustment
	 * @throws JposException
	 * 
	 */
	@Override
	public void printRecPackageAdjustVoid(int adjustmentType, String vatAdjustment) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (NOT IMPLEMENTED)
	 * <p>
	 * Processes a refund. The amount is positive, but it is printed as a negative number and the totals registers are decremented.
	 * <p>
	 * Some fixed text, along with the description, will be printed on the station defined by the FiscalReceiptStation property to indicate that a refund has occurred.
	 * <p>
	 * The vatInfo parameter contains a VAT table identifier if CapHasVatTable is true. Otherwise it, contains a VAT amount.
	 * <p>
	 * If CapPostPreLine is true an additional application specific line defined by the PreLine property will be printed. After printing this line PreLine will be reset to an empty string.
	 * <p>
	 * If several items of the same item type are to be refunded, then it is recommended to use printRecItemRefund.
	 * <br>
	 * <p>
	 * 
	 * @param description
	 * @param amount
	 * @param vatInfo
	 * @throws JposException
	 * 
	 */
	@Override
	public void printRecRefund(String description, long amount, int vatInfo) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (NOT IMPLEMENTED)
	 * <p>
	 * 
	 * @param description
	 * @param amount
	 * @param vatInfo
	 * @throws JposException
	 * 
	 */
	@Override
	public void printRecRefundVoid(String description, long amount, int vatInfo) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Checks and prints the current receipt subtotal on the station.
	 * <p>
	 * If {@link #getCapCheckTotal()} is true, the amount is compared to the subtotal calculated by the Fiscal Printer.<br>
	 * If the subtotals match, the subtotal is printed on the station. If the results do not match, the receipt is automatically canceled.
	 * <p>
	 * If {@link #getCapCheckTotal()} is false, then the subtotal is printed on the station and the parameter is never compared to the subtotal computed by the Fiscal Printer.<br>
	 * If {@link #getCapPostPreLine()} is true an additional application specific line defined by the {@link #getPostLine()} will be printed.
	 * After printing this line PostLine will be reset to an empty string.<br>
	 * If this method compares the application’s subtotal with the Fiscal Printer’s subtotal and they do not match, the PrinterState property will be changed to FPTR_PS_FISCAL_RECEIPT_ENDING.
	 * <p>
	 * 
	 * @param amount Amount of the subtotal.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_SLP_EMPTY:The slip station was specified, but a form is not inserted.(Only applies if AsyncMode is false.)<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_AMOUNT: The subtotal from the application does not match the subtotal computed by the Fiscal Printer.
	 *         ErrorCodeExtended = EFPTR_NEGATIVE_TOTAL: The total computed by the Fiscal Printer is less than zero.
	 * 
	 */
	@Override
	public void printRecSubtotal(long amount) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * printRecSubtotalAdjustment
	 * <p>
	 * Applies and prints a discount/surcharge to the current receipt subtotal on the station.<br>
	 * This discount/surcharge may be either a fixed currency amount or a percentage amount relating to the current receipt subtotal.
	 * p<>
	 * If the discount/surcharge amount is greater than the receipt subtotal, an error occurs since the subtotal can never be negative.
	 * <p>
	 * Discount/surcharge operations cause the printing of a fixed line of text expressing the kind of operation that has been performed.
	 * <p>
	 * Fixed amount discounts are only supported if CapSubAmountAdjustment is true.<br>
	 * Percentage discounts are only supported if CapSubPercentAdjustment is true.<br>
	 * Surcharges are only supported if CapPositiveSubtotalAdjustment is true.<br>
	 * If CapPostPreLine is true an additional application specific line defined by the PreLine property will be printed.<br>
	 * <p>
	 * After printing this line PreLine will be reset to an empty string<br>
	 * 
	 * @param adjustmentType Type of adjustment. See below for values. The adjustmentType parameter has one of the following values (Note: If currency value, four decimal places are used):<br>
	 *        FPTR_AT_AMOUNT_DISCOUNT = Fixed amount discount. The amount parameter contains a currency value.<br>
	 *        FPTR_AT_AMOUNT_SURCHARGE = Fixed amount surcharge. The amount parameter contains a currency value.<br>
	 *        FPTR_AT_PERCENTAGE_DISCOUNT = Percentage discount. The amount parameter contains a percentage value.<br>
	 *        FPTR_AT_PERCENTAGE_SURCHARGE = Percentage surcharge. The amount parameter contains a percentage value.<br>
	 * @param description Text describing the discount or surcharge.
	 * @param amount Amount of the adjustment (discount or surcharge).
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_ILLEGAL = LOne of the following errors occurred:<br>
	 *         •Fixed amount discounts are not supported (see the CapSubAmountAdjustment property).<br>
	 *         •Percentage discounts are not supported(see the CapSubPercentAdjustment property).<br>
	 *         •Surcharges are not supported (see the CapPositiveSubtotalAdjustment property).<br>
	 *         •The adjustmentType parameter is invalid.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_AMOUNT: The discount amount is invalid.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION: The discount description is too long or contains a reserved word.<br>
	 * 
	 */
	@Override
	public void printRecSubtotalAdjustment(int adjustmentType, String description, long amount) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Called to void a preceding subtotal adjustment on the station<br>
	 * <p>
	 * This discount/surcharge may be either a fixed currency amount or a percentage amount relating to the current receipt subtotal.<br>
	 * Fixed amount void discounts are only supported if {@link #getCapSubAmountAdjustment()} is true.<br>
	 * Percentage void discounts are only supported if the property CapSubPercentAdjustment is true.<br>
	 * <p>
	 * If {@link #getCapPostPreLine()}is true an additional application specific line defined by the {@link #getPreLine()} property will be printed.<br>
	 * After printing this line PreLine will be reset to an empty string.
	 * <br>
	 * <p>
	 * 
	 * @param adjustmentType Type of adjustment. See below for values. The adjustmentType parameter has one of the following values (Note: If currency value, four decimal places are used):<br>
	 *        FPTR_AT_AMOUNT_DISCOUNT = Fixed amount discount. The amount parameter contains a currency value.<br>
	 *        FPTR_AT_AMOUNT_SURCHARGE = Fixed amount surcharge. The amount parameter contains a currency value.<br>
	 *        FPTR_AT_PERCENTAGE_DISCOUNT = Percentage discount. The amount parameter contains a percentage value.<br>
	 *        FPTR_AT_PERCENTAGE_SURCHARGE = Percentage surcharge. The amount parameter contains a percentage value.<br>
	 * @param amount Amount of the adjustment (discount or surcharge).
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_ILLEGAL = One of the following errors occurred:<br>
	 *         •Fixed amount discounts are not supported (see the CapSubAmountAdjustment property).<br>
	 *         •Percentage discounts are not supported(see the CapSubPercentAdjustment property).<br>
	 *         •Surcharges are not supported (see the CapPositiveSubtotalAdjustment property).<br>
	 *         •The adjustmentType parameter is invalid.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_AMOUNT: The discount amount is invalid.<br>
	 * 
	 */
	@Override
	public void printRecSubtotalAdjustVoid(int adjustmentType, long amount) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Pentru preluare CIF!!<br>
	 * Called to print the customers tax identification on the station.<br>
	 * <p>
	 * This method is only supported when the Fiscal Printer is in the Fiscal Receipt Ending state.
	 * <p>
	 * 
	 * @param taxID Customer identification with identification characters and tax number.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_ILLEGAL = The Fiscal Printer does not support printing tax identifications..<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 * 
	 */
	@Override
	public void printRecTaxID(String taxID) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * 
	 * TOTAL
	 * <p>
	 * Checks and prints the current receipt total on the station and to tender a payment.
	 * <br>
	 * <p>
	 * If {@link #getCapCheckTotal()} is true, the total is compared to the total calculated by the Fiscal Printer.<br>
	 * If the totals match, the total is printed on both the receipt and journal along with some fixed text.
	 * If the results do not match, the receipt is automatically canceled. <br>
	 * <p>
	 * If {@link #getCapCheckTotal()} is false, then the total is printed on the receipt and journal and the parameter is never compared to the total computed by the Fiscal
	 * Printer.<br>
	 * <p>
	 * If {@link #getCapPredefinedPaymentLines()} is true, then the description parameter contains the index of one of the Fiscal Printer’s predefined payment descriptions.<br>
	 * The index is typically a single character of the alphabet.<br>
	 * The set of allowed values for this index is to be described in the description of the service and stored in the {@link #getPredefinedPaymentLines()} property.<br>
	 * <p>
	 * If payment = total, a line containing the description and payment is printed. The PrinterState property will be set to FPTR_PS_FISCAL_RECEIPT_ENDING.<br>
	 * <p>
	 * If payment > total, a line containing the description and payment is printed followed by a second line containing the change due. If CapChangeDue property is true,
	 * a description for the change due defined by the ChangeDue property is printed as the
	 * second line. The PrinterState property will be set to FPTR_PS_FISCAL_RECEIPT_ENDING.<br>
	 * <p>
	 * If payment < total, a line containing the description and payment is printed. Since the entire receipt total has not yet been tendered,
	 * the PrinterState property will be set to FPTR_PS_FISCAL_RECEIPT_TOTAL.<br>
	 * <p>
	 * If payment = 0, no line containing the description and payment is printed. The PrinterState property will be set to FPTR_PS_FISCAL_RECEIPT_TOTAL.<br>
	 * <p>
	 * If {@link #getCapAdditionalHeader()} is false, then receipt trailer lines, fiscal logotype and receipt cut are executed after the last total line,
	 * whenever receipt’s total became equal to the payment from the application.<br>
	 * Otherwise these lines are printed calling the endFiscalReceipt method.<br>
	 * <p>
	 * If {@link #getCapPostPreLine()} is true an additional application specific line defined by the {@link #getPostLine()}property will be printed.
	 * After printing this line PostLine will be reset to an empty string.<br>
	 * <p>
	 * 
	 * @param total Application computed receipt total.
	 * @param payment Amount of payment tendered.
	 * @param description Text description of the payment or the index of a predefined payment description
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_AMOUNT:<br>
	 *         •The application computed total does not match the Fiscal Printer computed total, or<br>
	 *         •the total parameter is invalid, or<br>
	 *         •the payment parameter is invalid<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION: The description is too long or contains a reserved word.<br>
	 *         ErrorCodeExtended = EFPTR_NEGATIVE_TOTAL: The computed total is less than zero.<br>
	 *         ErrorCodeExtended = EFPTR_WORD_NOT_ALLOWED: The description contains the reserved word.<br>
	 * 
	 */
	@Override
	public void printRecTotal(long total, long payment, String description) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Cancels the current receipt.<br>
	 * <p>
	 * The receipt is annulled but it is not physically canceled from the Fiscal Printer’s fiscal memory since fiscal receipts are printed with an increasing serial number and totals are accumulated in registers.<br>
	 * When a receipt is canceled, its subtotal is subtracted from the totals registers, but it is added to the canceled receipt register.
	 * <p>
	 * Some fixed text, along with the description, will be printed on the station to indicate that the receipt has been canceled.
	 * <p>
	 * Normally only a receipt with at least one transaction can be voided.<br>
	 * If {@link #getCapEmptyReceiptIsVoidable()} is true also an empty receipt (only the {@link #beginFiscalDocument(int)} method was called) can be voided.<br>
	 * If this method is successful, the PrinterState property will be changed to FPTR_PS_FISCAL_RECEIPT_ENDING.
	 * <p>
	 * 
	 * @param description Text describing the void.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_ITEM_DESCRIPTION: The description is too long or contains a reserved word.<br>
	 * 
	 */
	@Override
	public void printRecVoid(String description) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * <b>deprecated</b>
	 * 
	 * @see jpos.services.FiscalPrinterService13#printRecVoidItem(java.lang.String, long, int, int, long, int)
	 * @deprecated
	 */
	@Override
	public void printRecVoidItem(String description, long amount, int quantity, int adjustmentType, long adjustment, int vatInfo) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Fiscal Reports
	//--------------------------------------------------------------------------

	/**
	 * Prints a report of totals for a range of dates on the receipt.<br>
	 * <p>
	 * This method is always performed synchronously.<br>
	 * The dates are strings in the format “ddmmyyyyhhmm”, where:<br>
	 * dd=day of the month (1 - 31)<br>
	 * mm=month (1 - 12)<br>
	 * yyyy=year (1997-)<br>
	 * hh=hour (0-23)<br>
	 * mm=minutes (0-59)<br>
	 * <br>
	 * 
	 * @param date1 starting date of report to print.
	 * @param date2 ending date of report to print.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN: The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY: The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY: The receipt station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_BAD_DATE: One of the date parameters is invalid.<br>
	 * 
	 */
	@Override
	public void printPeriodicTotalsReport(String date1, String date2) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints on the receipt a report of a power failure that resulted in a loss of data stored in the CMOS of the Fiscal Printer.
	 * This method is only supported if CapPowerLossReport is true.
	 * <br>
	 * <p>
	 * 
	 * @throws JposException<br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support power loss reports (see the CapPowerLossReport property). <br>
	 * 
	 */
	@Override
	public void printPowerLossReport() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints a report of the fiscal EPROM contents on the receipt that occurred between two end points.
	 * <p>
	 * 
	 * @param reportType The kind of report to print.<br>
	 *        FPTR_RT_ORDINAL = Prints a report between two fiscal memory record numbers. If both startNum and endNum are valid and endNum > startNum, then a report of the period between startNum and endNum will be printed. If startNum is valid and endNum
	 *        is zero, then a report relating only to startNum will be printed.<br>
	 *        FPTR_RT_DATE = Prints a report between two dates.<br>
	 *        FPTR_RT_EOD_ORDINAL = Prints a report between two Z reports where startNum and endNum represent a Z report number. If both startNum and endNum are valid and endNum > startNum, then a report of the period between startNum and endNum will be
	 *        printed. If startNum is
	 *        valid and endNum is zero, then<br>
	 * @param startNum ASCII string identifying the starting record in Fiscal Printer memory from which to begin printing
	 * @param endNum ASCII string identifying the final record in Fiscal Printer memory at which printing is to end. See reportType table below to find out the exact meaning of this parameter.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_BUSY = Cannot perform while output is in progress.)<br>
	 *         E_ILLEGAL = One of the following errors occurred:<br>
	 *         •The reportType parameter is invalid, or<br>
	 *         •One or both of startNum and endNum are invalid, or<br>
	 *         •startNum > endNum.<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fiscal Receipt state.<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 * 
	 */
	@Override
	public void printReport(int reportType, String startNum, String endNum) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints a report of all the daily fiscal activities on the receipt. No data will be written to the fiscal EPROM as a result of this method invocation.
	 * <p>
	 * This method is only supported if CapXReport is true.
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support X reports (see the CapXReport property).. <br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition..<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 * 
	 * 
	 */
	@Override
	public void printXReport() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Prints a report of all the daily fiscal activities on the receipt.
	 * Data will be written to the fiscal EPROM as a result of this method invocation.<br>
	 * <p>
	 * Since running printZReport is implicitly a fiscal end of day function, the {@link #getDayOpened()} property will be set to false.<br>
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition..<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN:The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY:The journal station is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY:The receipt station is out of paper.<br>
	 * 
	 * 
	 */
	@Override
	public void printZReport() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Item Lists
	//--------------------------------------------------------------------------
	@Override
	public void beginItemList(int vatID) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void endItemList() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Compares itemName and its vatID with the values stored in the Fiscal Printer. <br>
	 * <p>
	 * 
	 * @param itemName item to be verified
	 * @param vatID VAT identifier of the item
	 * @throws JposException
	 * 
	 */
	@Override
	public void verifyItem(String itemName, int vatID) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Non-Fiscal
	//--------------------------------------------------------------------------

	/**
	 * Initiates non-fiscal operations on the Fiscal Printer.
	 * <p>
	 * This method is only supported if CapNonFiscalMode is true. <br>
	 * Output in this mode is accomplished using the {@link #printNormal(int, String)} method. <br>
	 * This method can be successfully called only if the current value of the {@link #getPrinterState()} property is FPTR_PS_MONITOR.<br>
	 * <p>
	 * If this method is successful, the PrinterState property will be changed to FPTR_PS_NONFISC<br>
	 * <br>
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support non-fiscal output (see the CapNonFiscalMode property).<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition..<br>
	 * 
	 * @see #getCapNonFiscalMode()
	 * @see #getPrinterState()
	 * @see #endNonFiscal()
	 * @see #printNormal(int, String)
	 */
	@Override
	public void beginNonFiscal() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Performs non-fiscal printing. Prints data on the Fiscal Printer station.
	 * <br>
	 * <p>
	 * 
	 * @param station fix FPTR_S_RECEIPT
	 * @param data The characters to be printed. May consist mostly of printable characters, escape sequences, carriage returns (13 decimal), and line feeds (10 decimal)
	 *        but in many cases these are not supported.
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state is not in the Non-Fiscal state<br>
	 *         ErrorCodeExtended = EFPTR_COVER_OPEN: The Fiscal Printer cover is open.<br>
	 *         ErrorCodeExtended = EFPTR_JRN_EMPTY: The journal station was specified but is out of paper.<br>
	 *         ErrorCodeExtended = EFPTR_REC_EMPTY: The receipt station was specified but is out of paper.<br>
	 * 
	 */
	@Override
	public void printNormal(int station, String data) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Terminates non-fiscal operations on one Fiscal Printer station.<br>
	 * <p>
	 * This method is only supported if CapNonFiscalMode is true.<br>
	 * If this method is successful, the PrinterState property will be changed to FPTR_PS_MONITOR.<br>
	 * <br>
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support non-fiscal output (see the CapNonFiscalMode property).<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition..<br>
	 * 
	 */
	@Override
	public void endNonFiscal() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Initiates training operations.<br>
	 * <p>
	 * This method is only supported if {@link #getCapTrainingMode()} is true.<br>
	 * <p>
	 * Output in this mode is accomplished using the printRec… methods in order to print a receipt or other methods to print reports.<br>
	 * <p>
	 * This method can be successfully called only if the current value of the {@link #getPrinterState()} property is FPTR_PS_MONITOR. <br>
	 * If this method is successful, the {@link #getTrainingModeActive()} property will be changed to true.<br>
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support training(see the CapTrainingMode property).<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer’s current state does not allow this state transition.<br>
	 * 
	 */
	@Override
	public void beginTraining() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Terminates training operations on either the receipt or the slip station.<br>
	 * This method is only supported if {@link #getCapTrainingMode()} is true.<br>
	 * If this method is successful, the {@link #getTrainingModeActive()} property will be changed to false. <br>
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support training mode (see the CapTrainingMode property).<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Training state.<br>
	 * 
	 */
	@Override
	public void endTraining() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Data Requests
	//--------------------------------------------------------------------------
	/**
	 * Retrieves data and counters from the printer’s fiscal module.
	 * <p>
	 * The data is returned in a string because some of the fields, such as the grand total, might overflow a 4-byte integer.
	 * <br>
	 * <p>
	 * The <b><i>dataItem</i></b> parameter has one of the following values:<br>
	 * <b>Identification data</b><br>
	 * FPTR_GD_FIRMWARE = Get the Fiscal Printer’s firmware release number.<br>
	 * FPTR_GD_PRINTER_ID = Get the Fiscal Printer’s fiscal ID.<br>
	 * <p>
	 * <b>Totals</b><br>
	 * FPTR_GD_CURRENT_TOTAL = Get the current receipt total.<br>
	 * FPTR_GD_DAILY_TOTAL = Get the daily total.<br>
	 * FPTR_GD_GRAND_TOTAL = Get the Fiscal Printer’s grand total.<br>
	 * FPTR_GD_MID_VOID = Get the total number of voided receipts.<br>
	 * FPTR_GD_NOT_PAID = Get the current total of not paid receipts.<br>
	 * FPTR_GD_RECEIPT_NUMBER = Get the number of fiscal receipts printed.<br>
	 * (no)FPTR_GD_REFUND = Get the current total of refunds.<br>
	 * (no)FPTR_GD_REFUND_VOID = Get the current total of voided refunds.<br>
	 * <p>
	 * <b>Fiscal memory counts</b><br>
	 * FPTR_GD_NUMB_CONFIG_BLOCK= Get the grand number of configuration blocks.<br>
	 * FPTR_GD_NUMB_CURRENCY_BLOCK = Get the grand number of currency blocks.<br>
	 * FPTR_GD_NUMB_HDR_BLOCK = Get the grand number of header blocks.<br>
	 * FPTR_GD_NUMB_RESET_BLOCK = Get the grand number of reset blocks.<br>
	 * FPTR_GD_NUMB_VAT_BLOCK = Get the grand number of VAT blocks.<br>
	 * <p>
	 * <b>Counter</b><br>
	 * FPTR_GD_FISCAL_DOC = Get the number of daily fiscal documents.<br>
	 * FPTR_GD_FISCAL_DOC_VOID = Get the number of daily voided fiscal documents.<br>
	 * FPTR_GD_FISCAL_REC = Get the number of daily fiscal sales receipts.<br>
	 * FPTR_GD_FISCAL_REC_VOID = Get the number of daily voided fiscal sales receipts.<br>
	 * FPTR_GD_NONFISCAL_DOC = Get the number of daily non fiscal documents<br>
	 * FPTR_GD_NONFISCAL_DOC_VOID = Get the number of daily voided non fiscal documents.<br>
	 * FPTR_GD_NONFISCAL_REC = Get the number of daily non fiscal receipts.<br>
	 * FPTR_GD_RESTART = Get the Fiscal Printer’s restart count<br>
	 * FPTR_GD_SIMP_INVOICE = Get the number of daily simplified invoices.<br>
	 * FPTR_GD_Z_REPORT = Get the Z report number.<br>
	 * <p>
	 * <b>Fixed fiscal printer text</b><br>
	 * FPTR_GD_TENDER = Get the payment description used in the printRecTotal method, defined by the given identifier in the optArgs argument.Valid only, if the CapPredefinedPaymentLines property is true.<br>
	 * + dataItem:<br>
	 * FPTR_PDL_CASH = Cash<br>
	 * FPTR_PDL_CHEQUE = Cheque<br>
	 * FPTR_PDL_CHITTY = Chitty<br>
	 * FPTR_PDL_COUPON = Coupon<br>
	 * FPTR_PDL_CURRENCY = Currency<br>
	 * FPTR_PDL_DRIVEN_OFF<br>
	 * FPTR_PDL_EFT_IMPRINTER = Printer EFT<br>
	 * FPTR_PDL_EFT_TERMINAL = Terminal EFT<br>
	 * FPTR_PDL_FREE_GIFT = Gift<br>
	 * FPTR_PDL_GIRO = Giro<br>
	 * FPTR_PDL_HOME = Home<br>
	 * FPTR_PDL_LOCAL_ACCOUNT = Local account<br>
	 * FPTR_PDL_LOCAL_ACCOUNT_CARD = Local card account<br>
	 * FPTR_PDL_PAY_CARD = Pay card<br>
	 * FPTR_PDL_PAY_CARD_MANUAL = Manual pay card<br>
	 * FPTR_PDL_PREPAY = Prepay<br>
	 * FPTR_PDL_PUMP_TEST = Pump test<br>
	 * FPTR_PDL_SHORT_CREDIT = Credit<br>
	 * FPTR_PDL_STAFF = Staff<br>
	 * FPTR_PDL_VOUCHER = Voucher<br>
	 * <p>
	 * <b>Linecounter</b><br>
	 * FPTR_GD_LINECOUNT = Get the number of printed lines, defined by the given identifier in the optArgs argument. If the CapMultiContractor property is true, line counters depend on the contractor defined by the ContractorId property.<br>
	 * + dataItem:<br>
	 * FPTR_LC_ITEM = Number of item lines.<br>
	 * FPTR_LC_ITEM_VOID = Number of voided item lines.<br>
	 * FPTR_LC_DISCOUNT = Number of discount lines.<br>
	 * FPTR_LC_DISCOUNT_VOID = Number of voided discount lines.<br>
	 * FPTR_LC_SURCHARGE = Number of surcharge lines.<br>
	 * FPTR_LC_SURCHARGE_VOID = Number of voided surcharge lines.<br>
	 * FPTR_LC_REFUND = Number of refund lines.<br>
	 * FPTR_LC_REFUND_VOID = Number of voided refund lines.<br>
	 * FPTR_LC_SUBTOTAL_DISCOUNT = Number of subtotal discount lines.<br>
	 * FPTR_LC_SUBTOTAL_DISCOUNT_VOID = Number of voided subtotal discount lines.<br>
	 * FPTR_LC_SUBTOTAL_SURCHARGE = Number of subtotal surcharge lines.<br>
	 * FPTR_LC_SUBTOTAL_SURCHARGE_VOID = Number of voided subtotal surcharge lines.<br>
	 * FPTR_LC_COMMENT = Number of comment lines.<br>
	 * FPTR_LC_SUBTOTAL = Number of subtotal lines.<br>
	 * FPTR_LC_TOTAL = Number of total lines<br>
	 * <p>
	 * <b>Description length</b><br>
	 * FPTR_GD_DESCRIPTION_LENGTH = Get the maximum number of characters that may be passed as a description parameter for a specific method, defined by the given identifier in the optArgs argument.<br>
	 * + dataItem:<br>
	 * FPTR_DL_ITEM = printRecItem method.<br>
	 * FPTR_DL_ITEM_ADJUSTMENT = printRecItemAdjustment method.<br>
	 * FPTR_DL_ITEM_FUEL = printRecItemFuel method.<br>
	 * FPTR_DL_ITEM_FUEL_VOID = printRecItemFuelVoid method.<br>
	 * FPTR_DL_NOT_PAID = printRecNotPaid method.<br>
	 * FPTR_DL_PACKAGE_ADJUSTMENT = printRecPackageAdjustment method.<br>
	 * FPTR_DL_REFUND = printRecRefund method, printRecItemRefund method.<br>
	 * FPTR_DL_REFUND_VOID = printRecRefundVoid method, printRecItemRefundVoid method.<br>
	 * FPTR_DL_SUBTOTAL_ADJUSTMENT = printRecSubtotalAdjustment method.<br>
	 * FPTR_DL_TOTAL = printRecTotal method.<br>
	 * FPTR_DL_VOID = printRecVoid method.<br>
	 * FPTR_DL_VOID_ITEMprintRecItemVoid and printRecItemAdjustmentVoid methods.<br>
	 * 
	 * 
	 * 
	 * 
	 * @param dataItem The specific data item to retrieve.
	 * @param optArgs For some dataItem this additional argument is needed.
	 * @param data Character string to hold the data retrieved.
	 * @throws JposException ErrorCode=<br>
	 *         E_BUSY = Cannot perform while output is in progress. (Only applies if AsyncMode is false.)<br>
	 *         E_ILLEGAL = The dataItem, optArgs or ContractorId specified is invalid.<br>
	 * 
	 */
	@Override
	public void getData(int dataItem, int[] optArgs, String[] data) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Gets the Fiscal Printer’s date and time specified by the DateType property.<br>
	 * <p>
	 * The date and time are returned as a string in the format “ddmmyyyyhhmm”:<br>
	 * dd = day of the month (1 - 31)<br>
	 * mm = month (1 - 12)<br>
	 * yyyy = year (1997-)<br>
	 * hh = hour (0-23)<br>
	 * mm = minutes (0-59)<br>
	 * <p>
	 * The fiscal controller may not support hours and minutes depending on the date type.
	 * In such cases the corresponding fields in the returned string are filled with “0”. <br>
	 * <p>
	 * 
	 * @param Date
	 * @throws JposException ErrorCode=<br>
	 *         E_ILLEGAL = Retrieval of the date and time is not valid at this time.<br>
	 * 
	 * @see #getDateType()
	 */
	@Override
	public void getDate(String[] Date) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Gets the totalizer specified by the optArgs argument Some of the totalizers such as item or VAT totalizers may be associated with the given vatID.<br>
	 * <p>
	 * If {@link #getCapTotalizerType()} is true the type of totalizer (grand, day, receipt specific) depends on the TotalizerType property. <br>
	 * If {@link #getCapSetVatTable()} is false, then only one totalizer is present. <br>
	 * <p>
	 * 
	 * The optArgs parameter has one of the following values:<br>
	 * <p>
	 * FPTR_GT_GROSS = Gross totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_NET = Net totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_DISCOUNT = Discount totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_DISCOUNT_VOID = Voided discount totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_ITEM = Item totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_ITEM_VOID = Voided item totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_NOT_PAID = Not paid totalizer specified by the TotalizerType property.<br>
	 * (no) FPTR_GT_REFUND = Refund totalizer specified by the TotalizerType property.<br>
	 * (no) FPTR_GT_REFUND_VOID = Voided refund totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_SUBTOTAL_DISCOUNT = Subtotal discount totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_SUBTOTAL_DISCOUNT_VOID = Voided discount totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_SUBTOTAL_SURCHARGES = Subtotal surcharges totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_SUBTOTAL_SURCHARGES_VOID = Voided surcharges totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_SURCHARGE = Surcharge totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_SURCHARGE_VOID = Voided surcharge totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_VAT = VAT totalizer specified by the TotalizerType property.<br>
	 * FPTR_GT_VAT_CATEGORY = VAT totalizer per VAT category specified by the TotalizerType property associated to the given vatID.<br>
	 * 
	 * @param vatID VAT identifier of the required totalizer.
	 * @param optArgs Specifies the required totalizer.
	 * @param data Totalizer returned as a string.
	 * @throws JposException ErrorCode=<br>
	 *         E_ILLEGAL = One of the following errors occurred:<br>
	 *         •The vatID parameter is invalid, or<br>
	 *         •The ContractorId property is invalid, or<br>
	 *         •The specified totalizer is not available.<br>
	 * 
	 * @see #getCapTotalizerType()
	 * @see #getTotalizerType()
	 */
	@Override
	public void getTotalizer(int vatID, int optArgs, String[] data) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Gets the rate associated with a given VAT identifier.
	 * <p>
	 * This method is only supported if CapHasVatTable is true.
	 * <p>
	 * 
	 * @param vatID VAT identifier of the required rate.
	 * @param optArgs
	 * @param vatRate The rate associated with the VAT identifier.
	 * @throws JposException ErrorCode=<br>
	 *         E_ILLEGAL = The vatID parameter is invalid, or CapHasVatTable is false.
	 * 
	 * @see #getCapHasVatTable()
	 */
	@Override
	public void getVatEntry(int vatID, int optArgs, int[] vatRate) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Error Corrections
	//--------------------------------------------------------------------------

	/**
	 * Clears all Fiscal Printer error conditions.<>
	 * <br>
	 * This method is always performed synchronously.
	 * <p>
	 * 
	 * @throws JposException ErrorCode=E_FAILURE: Error recovery failed.<br>
	 * 
	 */
	@Override
	public void clearError() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Forces the Fiscal Printer to return to Monitor state.<br>
	 * <p>
	 * This forces any interrupted operations to be canceled and closed.<br>
	 * This method must be invoked when the Fiscal Printer is not in a Monitor state after a successful call to the claim method and successful<br>
	 * setting of the DeviceEnabled property to true. This typically happens if a power failures occurs during a fiscal operation.<br>
	 * 
	 * Calling this method does not close the Fiscal Printer, i.e., does not force a Z report to be printed.<br>
	 * <p>
	 * The Device will handle this command as follows:<br>
	 * - If the Fiscal Printer was in either Fiscal Receipt, Fiscal Receipt Total or Fiscal Receipt Ending state, the receipt will be ended without updating any registers.<br>
	 * - If the Fiscal Printer was in a non-fiscal state, the Fiscal Printer will exit that state.<br>
	 * - If the Fiscal Printer was in the training state, the Fiscal Printer will exit the training state.<br>
	 * 
	 */
	@Override
	public void resetPrinter() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Properties
	//--------------------------------------------------------------------------

	/**
	 * Holds the number of decimal digits that the fiscal device uses for calculations.
	 * This property is initialized when the device is enabled.
	 * 
	 * @return int
	 * @throws JposException
	 * 
	 */
	@Override
	public int getAmountDecimalPlaces() throws JposException {
		return 2;
	}

	/**
	 * Holds a value identifying which actual currency is used by the Fiscal Printer.
	 * <br>
	 * 
	 * @return simbol
	 * @throws JposException
	 * 
	 */
	@Override
	public int getActualCurrency() throws JposException {
		return FPTR_AC_ROL;
	}

	private String additionalHeader = new String("");
	private String additionalTrailer = new String("");

	/**
	 * Specifies a user specific text which will be printed on the receipt after the fixed header lines when calling the beginFiscalReceipt method. <br>
	 * This property is only valid if CapAdditionalHeader is true. <br>
	 * This property is initialized to an empty string and kept current while the device is enabled <br>
	 * 
	 * @return string line
	 * @throws JposException
	 * 
	 */
	@Override
	public String getAdditionalHeader() throws JposException {
		return additionalHeader;
	}

	@Override
	public void setAdditionalHeader(String additionalHeader) throws JposException {
		this.additionalHeader = additionalHeader;
	}

	/**
	 * Specifies a user specific text which will be printed on the receipt after the fiscal trailer lines when calling the endFiscalReceipt method.
	 * This property is only valid if CapAdditionalTrailer is true.
	 * This property is initialized to an empty string and kept current while the device is enabled.
	 * <br>
	 * <p>
	 * 
	 * @return string
	 * @throws JposException
	 * 
	 */
	@Override
	public String getAdditionalTrailer() throws JposException {
		return additionalTrailer;
	}

	@Override
	public void setAdditionalTrailer(String additionalTrailer) throws JposException {
		this.additionalTrailer = additionalTrailer;
	}

	/**
	 * This property holds the text to be printed as a description for the cash return when using the {@link #printRecTotal(long, long, String)} method.<br>
	 * This property is only valid if CapChangeDue is true.<br>
	 * This property is initialized to an empty string by the open method.<br>
	 * <br>
	 * <p>
	 * 
	 * @param changeDue
	 * @throws JposException ErroCode:
	 *         E_ILLEGAL: Setting this property is not valid for this service (see CapChangeDue property).<br>
	 *         E_EXTENDED: ErrorCodeExtended = EFPTR_BAD_LENGTH: The length of the string to be printed is too long.<br>
	 * 
	 */
	@Override
	public void setChangeDue(String changeDue) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getChangeDue() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Specifies the type of date to be requested when calling the getDate method.<br>
	 * <p>
	 * Values are:<br>
	 * FPTR_DT_CONF = Date of configuration.<br>
	 * FPTR_DT_EOD = Date of last end of day.<br>
	 * FPTR_DT_RESET = Date of last reset.<br>
	 * FPTR_DT_RTC = Real time clock of the Fiscal Printer.<br>
	 * FPTR_DT_VAT = Date of last VAT change.<br>
	 * FPTR_DT_START = The date and time that the fiscal day started or of the first fiscal receipt or first fiscal document.<br>
	 * <p>
	 * It is required by law to make a Z report and therefore end the fiscal day within a 24 hour period.<br>
	 * If the 24 hour period after the first fiscal ticket or after the fiscal day opening is exceeded, then no new fiscal ticket can be started and printing of a Z report is
	 * required. Setting DateType to FPTR_DT_START and calling getDate provides the information necessary to detect this situation.<br>
	 * This property is initialized to FPTR_DT_RTC and kept current while the device is enabled, which is the functionality supported prior to Release 1.6.<br>
	 * 
	 * @throws JposException
	 *         E_ILLEGAL: The Fiscal Printer does not support the specified type.<br>
	 * 
	 * @see #getDate(String[])
	 */
	@Override
	public void setDateType(int dateType) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getDateType() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Selects the type of the fiscal receipt.
	 * <p>
	 * Setting this property is only allowed in the Monitor State.<br>
	 * <p>
	 * Values are:<br>
	 * FPTR_RT_CASH_IN = Cash-in receipt<br>
	 * FPTR_RT_CASH_OUT = Cash-out receipt<br>
	 * FPTR_RT_GENERIC = Generic receipt<br>
	 * FPTR_RT_SALES = Retail sales receipt<br>
	 * FPTR_RT_SERVICE = Service sales receipt<br>
	 * FPTR_RT_SIMPLE_INVOICE = Simplified invoice receipt<br>
	 * FPTR_RT_REFUND = Refund sales receipt<br>
	 * <p>
	 * This property is only valid if {@link #getCapFiscalReceiptType()} is true.
	 * Starting with Release 1.11, due to the need for negative receipts (e.g., in Italy), such as refund receipts, the receipt type FPTR_RT_REFUND is added.<br>
	 * This property is initialized to FPTR_RT_SALES and kept current while the device is enabled, which is the functionality supported prior to Release 1.6.<br>
	 * 
	 * @param fiscalReceiptType vezi mai sus
	 * @throws JposException ErroCode=<br>
	 *         E_ILLEGAL: The Fiscal Printer does not support the specified receipt type.<br>
	 *         E_EXTENDED + ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Monitor State<br>
	 * 
	 * @see #beginFiscalReceipt(boolean)
	 * @see #getCapFiscalReceiptType()
	 */
	@Override
	public void setFiscalReceiptType(int fiscalReceiptType) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getFiscalReceiptType() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Selects the kind of message to be printed when using the printRecMessage method.
	 * <p>
	 * Values are:<br>
	 * FPTR_MT_ADVANCE <br>
	 * FPTR_MT_ADVANCE_PAID <br>
	 * FPTR_MT_AMOUNT_TO_BE_PAID <br>
	 * FPTR_MT_AMOUNT_TO_BE_PAID_BACK <br>
	 * FPTR_MT_CARD <br>
	 * FPTR_MT_CARD_NUMBER <br>
	 * FPTR_MT_CARD_TYPE <br>
	 * FPTR_MT_CASH <br>
	 * FPTR_MT_CASHIER <br>
	 * FPTR_MT_CASH_REGISTER_NUMBER <br>
	 * FPTR_MT_CHANGE <br>
	 * FPTR_MT_CHEQUE <br>
	 * FPTR_MT_CLIENT_NUMBER <br>
	 * FPTR_MT_CLIENT_SIGNATURE <br>
	 * FPTR_MT_COUNTER_STATE <br>
	 * FPTR_MT_CREDIT_CARD <br>
	 * FPTR_MT_CURRENCY <br>
	 * FPTR_MT_CURRENCY_VALUE <br>
	 * FPTR_MT_DEPOSIT <br>
	 * FPTR_MT_DEPOSIT_RETURNED <br>
	 * FPTR_MT_DOT_LINE <br>
	 * FPTR_MT_DRIVER_NUMB <br>
	 * FPTR_MT_EMPTY_LINE <br>
	 * FPTR_MT_FREE_TEXT <br>
	 * FPTR_MT_FREE_TEXT_WITH_DAY_LIMIT <br>
	 * FPTR_MT_GIVEN_DISCOUNT <br>
	 * FPTR_MT_LOCAL_CREDIT <br>
	 * FPTR_MT_MILEAGE_KM <br>
	 * FPTR_MT_NOTE <br>
	 * FPTR_MT_PAID <br>
	 * FPTR_MT_PAY_IN <br>
	 * FPTR_MT_POINT_GRANTED <br>
	 * FPTR_MT_POINTS_BONUS <br>
	 * FPTR_MT_POINTS_RECEIPT <br>
	 * FPTR_MT_POINTS_TOTAL <br>
	 * FPTR_MT_PROFITED <br>
	 * FPTR_MT_RATE <br>
	 * FPTR_MT_REGISTER_NUMB <br>
	 * FPTR_MT_SHIFT_NUMBER <br>
	 * FPTR_MT_STATE_OF_AN_ACCOUNT <br>
	 * FPTR_MT_SUBSCRIPTION <br>
	 * FPTR_MT_TABLE <br>
	 * FPTR_MT_THANK_YOU_FOR_LOYALTY <br>
	 * FPTR_MT_TRANSACTION_NUMB <br>
	 * FPTR_MT_VALID_TO <br>
	 * FPTR_MT_VOUCHER <br>
	 * FPTR_MT_VOUCHER_PAID <br>
	 * FPTR_MT_VOUCHER_VALUE <br>
	 * FPTR_MT_WITH_DISCOUNT <br>
	 * FPTR_MT_WITHOUT_UPLIFT <br>
	 * <p>
	 * This property is initialized to FPTR_MT_FREE_TEXT by the open method, which is the functionality supported prior to Release 1.6.
	 * <br>
	 * 
	 * @param messageType
	 * @throws JposException
	 *         E_ILLEGAL = The Fiscal Printer does not support this value.
	 * 
	 */
	@Override
	public void setMessageType(int messageType) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getMessageType() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * An application specific text to be printed on the fiscal receipt after a line item invoked by some printRec... methods.
	 * <p>
	 * The property can be written in the Fiscal Receipt State.<br>
	 * The length of the text is reduced to a country specific value<br>
	 * This property is only valid if CapPostPreLine is true.<br>
	 * This property is initialized to an empty string and will be reset to an empty string after being used.<br>
	 * <br>
	 * <p>
	 * 
	 * @param postLine
	 * @throws JposException ErrorCode = <br>
	 *         E_ILLEGAL: The Fiscal Printer does not support printing post item lines or the text contains invalid characters.<br>
	 *         E_EXTENDED + ErrorCodeExtended = EFPTR_BAD_LENGTH: The length of the string is too long.<br>
	 * 
	 * @see #printRecSubtotal(long)
	 * @see #printRecTotal(long, long, String)
	 */
	@Override
	public void setPostLine(String postLine) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getPostLine() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * An application specific text to be printed on the fiscal receipt before a line item invoked by some printRec... methods.
	 * <p>
	 * The property can be written in the Fiscal Receipt State. The length of the text is reduced to a country specific value
	 * <p>
	 * This property is only valid if CapPostPreLine is true.
	 * <p>
	 * This property is initialized to an empty string and will be reset to an empty string after being used
	 * <p>
	 * 
	 * @param preLine
	 * @throws JposException ErrorCode = <br>
	 *         E_ILLEGAL: The Fiscal Printer does not support printing post item lines or the text contains invalid characters.<br>
	 *         E_EXTENDED + ErrorCodeExtended = EFPTR_BAD_LENGTH: The length of the string is too long.<br>
	 * 
	 */
	@Override
	public void setPreLine(String preLine) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getPreLine() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Specifies the type of totalizer to be requested when calling the getTotalizer method.<br>
	 * <p>
	 * Values are:<br>
	 * FPTR_TT_DOCUMENT = Document totalizer <br>
	 * FPTR_TT_DAY = Day totalizer<br>
	 * FPTR_TT_RECEIPT = Receipt totalizer<br>
	 * FPTR_TT_GRAND = Grand totalizer<br>
	 * <p>
	 * This property is only valid if CapTotalizerType is true.
	 * <p>
	 * This property is initialized to FPTR_TT_DAY and kept current while the device is enabled, which is the functionality supported prior to Release 1.6.
	 * <p>
	 * 
	 * @param totalizerType
	 * @throws JposException
	 * 
	 * @see #getTotalizer(int, int, String[])
	 */
	@Override
	public void setTotalizerType(int totalizerType) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getTotalizerType() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * If true, automatic comparison between the Fiscal Printer’s total and the application’s total is enabled. If false, automatic comparison is disabled.
	 * <p>
	 * This property can be changed if CapCheckTotal is true. Otherwise, it is read-only.
	 * <p>
	 * This property is initialized to true by the open method.
	 * <p>
	 * 
	 * @param checkTotal true/false
	 * @throws JposException
	 *         E_ILLEGAL = Setting this property is not valid for this Service (see CapCheckTotal)
	 * 
	 */
	@Override
	public void setCheckTotal(boolean checkTotal) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getCheckTotal() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getCountryCode() throws JposException {
		return FPTR_CC_ROMANIA;
	}

	/**
	 * If true, then the Fiscal Printer’s cover is open.
	 * <p>
	 * If CapCoverSensor is false, then the Fiscal Printer does not have a cover open sensor and this property is always false.
	 * <p>
	 * This property is initialized and kept current while the device is enabled.
	 * <p>
	 * 
	 * @return true/false
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getCoverOpen() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	private boolean dayOpened = false;

	/**
	 * If true, then the fiscal day has been started on the Fiscal Printer by a first call to the beginFiscalReceipt or beginFiscalDocument method at a fiscal period (day).
	 * <p>
	 * The Fiscal Day of the Fiscal Printer can be either opened or not opened.
	 * <p>
	 * The DayOpened property reflects whether or not the Fiscal Printer considers its Fiscal Day to be opened or not.
	 * <p>
	 * Some methods may only be called while the Fiscal Day is not yet opened (DayOpened is false).
	 * <p>
	 * Methods that can be called after the Fiscal Day is opened change from country to country. Usually all the configuration methods are to be called only
	 * before the Fiscal Day is opened.
	 * <p>
	 * This property changes to false after calling {@link #printZReport()}
	 * <p>
	 * The following methods may be allowed only if the Fiscal Printer is in the Monitor State and has not yet begun its Fiscal Day:<br>
	 * setCurrency<br>
	 * setDate<br>
	 * setHeaderLine<br>
	 * setPOSID<br>
	 * setStoreFiscalID<br>
	 * setTrailerLine<br>
	 * setVatTable<br>
	 * setVatValue<br>
	 * <p>
	 * This property is initialized and kept current while the device is enabled.
	 * 
	 * @return treu/false
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getDayOpened() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the maximum number of characters that may be passed as a description parameter.
	 * <p>
	 * The exact maximum number for a description parameter of a specific method can be obtained by calling getData method.
	 * <p>
	 * This property is initialized by the open method.
	 * <p>
	 * <p>
	 * 
	 * @return int
	 * @throws JposException
	 * 
	 * @see #getData(int, int[], String[])
	 */
	@Override
	public int getDescriptionLength() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the maximum number of characters that may be passed as a message line in the method {@link #printRecMessage(String)}.
	 * <p>
	 * The value may change in different modes of the Fiscal Printer. For example in the mode “Fiscal Receipt” the number of characters may be bigger than in the mode “Fiscal Receipt Total.”
	 * <p>
	 * This property is initialized by the open method.
	 * 
	 * @return len
	 * @throws JposException
	 * 
	 */
	@Override
	public int getMessageLength() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the maximum number of header lines that can be printed for each fiscal receipt.
	 * <p>
	 * Header lines usually contain information such as store address, store name, store Fiscal ID.
	 * <p>
	 * Each header line is set using the setHeaderLine method and remains set even after the Fiscal Printer is switched off.
	 * <p>
	 * Header lines are automatically printed when a fiscal receipt is initiated using the {@link #beginFiscalReceipt(boolean)} method or when the first line item
	 * <p>
	 * This property is initialized by the open method.
	 * <p>
	 * 
	 * @return number
	 * @throws JposException
	 * 
	 */
	@Override
	public int getNumHeaderLines() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the maximum number of trailer lines that can be printed for each fiscal receipt.<br>
	 * Trailer lines are usually promotional messages.<br>
	 * Each trailer line is set using the setTrailerLine method and remains set even after the Fiscal Printer is switched off.<br>
	 * Trailer lines are automatically printed either after the last printRecTotal or when a fiscal receipt is closed using the endFiscalReceipt method.<br>
	 * This property is initialized by the open method.<br>
	 * <p>
	 * 
	 * @return number
	 * @throws JposException
	 * 
	 */
	@Override
	public int getNumTrailerLines() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the maximum number of vat rates that can be entered into the Fiscal Printer’s Vat table.
	 * This property is initialized by the open method.
	 * 
	 * @return num
	 * @throws JposException
	 * 
	 */
	@Override
	public int getNumVatRates() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the list of all possible words to be used as indexes of the predefined payment lines (for example, “a, b, c, d, z”).
	 * <p>
	 * Those indexes are used in the printRecTotal method for the description parameter.<br>
	 * If CapPredefinedPaymentLines is true, only predefined payment lines are allowed.<br>
	 * This property is initialized by the open method<br>
	 * <p>
	 * 
	 * @return string
	 * @throws JposException
	 * 
	 */
	@Override
	public String getPredefinedPaymentLines() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	private int printerState = FPTR_PS_MONITOR;

	/**
	 * Holds the Fiscal Printer’s current operational state. This property controls which methods are currently legal.
	 * <p>
	 * Values are:
	 * <p>
	 * <b>FPTR_PS_MONITOR</b><br>
	 * The Fiscal Printer is currently not in a specific operational mode. In this state the Fiscal Printer will accept any of the begin… methods as well as the set… methods.
	 * (If TrainingModeActive is true:The Fiscal Printer is currently being used for training purposes. In this state the Fiscal Printer will accept any of the printRec… methods or the endTraining method.)
	 * <p>
	 * <b>FPTR_PS_FISCAL_RECEIPT</b><br>
	 * The Fiscal Printer is currently processing a fiscal receipt. In this state the Fiscal Printer will accept any of the printRec… methods.<br>
	 * If TrainingModeActive is true: The Fiscal Printer is currently being used for training purposes and a fiscal receipt is currently opened.<br>
	 * <p>
	 * <b>FPTR_PS_FISCAL_RECEIPT_TOTAL</b><br>
	 * The Fiscal Printer has already accepted at least one payment, but the total has not been completely paid. <br>
	 * In this state the Fiscal Printer will accept either the printRecTotal, printRecNotPaid, or printRecMessage methods.<br>
	 * If TrainingModeActive is true: The Fiscal Printer is currently being used for training purposes and the Fiscal Printer has already accepted at least one payment,
	 * but the total has not been completely paid.<br>
	 * <p>
	 * <b>FPTR_PS_FISCAL_RECEIPT_ENDING</b><br>
	 * The Fiscal Printer has completed the receipt up to the total line. <br>
	 * In this state the Fiscal Printer will accept either the printRecMessage or endFiscalReceipt methods.<br>
	 * If TrainingModeActive is true: The Fiscal Printer is currently being used for training purposes and a fiscal receipt is going to be closed. <br>
	 * <p>
	 * <i>FPTR_PS_FISCAL_DOCUMENT</i><br>
	 * The Fiscal Printer is currently processing a fiscal slip.<br>
	 * In this state the Fiscal Printer will accept either the printFiscalDocumentLine or endFiscalDocument methods.<br>
	 * <p>
	 * <i>FPTR_PS_FIXED_OUTPUT</i><br>
	 * The Fiscal Printer is currently processing fixed text output to one or more stations.<br>
	 * In this state the Fiscal Printer will accept either the printFixedOutput or endFixedOutput methods.<br>
	 * <p>
	 * <i>FPTR_PS_ITEM_LIST</i><br>
	 * The Fiscal Printer is currently processing an item list report.<br>
	 * In this state the Fiscal Printer will accept either the verifyItem or endItemList methods.<br>
	 * <p>
	 * <b>FPTR_PS_NONFISCAL</b><br>
	 * The Fiscal Printer is currently processing non-fiscal output.<br>
	 * In this state the Fiscal Printer will accept either the printNormal or endNonFiscal methods.<br>
	 * <p>
	 * <b>FPTR_PS_LOCKED</b><br>
	 * The Fiscal Printer has encountered a non-recoverable hardware problem.<br>
	 * An authorized Fiscal Printer technician must be contacted to exit this state.<br>
	 * <p>
	 * <b>FPTR_PS_REPORT</b><br>
	 * The Fiscal Printer is currently processing a fiscal report.<br>
	 * In this state the Fiscal Printer will not accept
	 * <p>
	 * 
	 * There are a few methods that are accepted in any state except FPTR_PS_LOCKED.<br>
	 * These are beginInsertion, endInsertion, beginRemoval, endRemoval, getDate, getData, getTotalizer, getVatEntry, resetPrinter and clearOutput.<br>
	 * This property is initialized when the device is first enabled following the open method.<br>
	 * <p>
	 *  
	 * @return state
	 * @throws JposException
	 * 
	 */
	@Override
	public int getPrinterState() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the number of decimal digits in the fractional part that should be assumed to be in any quantity parameter.
	 * <p>
	 * This property is initialized when the device is first enabled following the open method.
	 * <p>
	 * 
	 * @return number
	 * @throws JposException
	 * 
	 */
	@Override
	public int getQuantityDecimalPlaces() throws JposException {
		return 3;
	}

	/**
	 * Holds the maximum number of digits that may be passed as a quantity parameter, including both the whole and fractional parts.<br>
	 * <p>
	 * This property is initialized when the device is first enabled following the open method.
	 * <p>
	 * 
	 * @return number
	 * @throws JposException
	 * 
	 */
	@Override
	public int getQuantityLength() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * If true, the receipt is out of paper. If false, receipt paper is present. <br>
	 * <p>
	 * 
	 * @return da sau ba
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getRecEmpty() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * If true, the receipt paper is low. If false, receipt paper is not low. <br>
	 * <p>
	 * 
	 * @return da sau ba
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getRecNearEnd() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the remaining counter of Fiscal Memory.
	 * <p>
	 * This property is initialized and kept current while the device is enabled and may be updated by printZReport method.
	 * <p>
	 * 
	 * @return number
	 * @throws JposException
	 * 
	 */
	@Override
	public int getRemainingFiscalMemory() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Holds the string that is automatically printed with the total when the printRecTotal method is called.
	 * <p>
	 * This word may not occur in any string that is passed into any fiscal output methods.
	 * <p>
	 * This property is only valid if CapReservedWord is true.<br>
	 * This property is initialized by the open method.<br>
	 * <p>
	 * 
	 * @return string
	 * @throws JposException
	 * 
	 */
	@Override
	public String getReservedWord() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	private boolean trainingMode = false;

	/**
	 * Holds the current Fiscal Printer's operational state concerning the training mode.
	 * <p>
	 * Training mode allows all fiscal commands, but each receipt is marked as non-fiscal and no internal Fiscal Printer registers are updated with any data while in
	 * training mode.
	 * <p>
	 * Some countries' fiscal rules require that all blank characters on a training mode receipt be printed as some other character.
	 * Italy, for example, requires that all training mode receipts print a “?” instead of a blank.
	 * <p>
	 * This property has one of the following values:<br>
	 * 
	 * true = The Fiscal Printer is currently in training mode. That means no data are written into the EPROM of the Fiscal Printer.<br>
	 * false = The Fiscal Printer is currently in normal mode. All printed receipts will also update the fiscal memory.<br>
	 * 
	 * @return da sau ba
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getTrainingModeActive() throws JposException {
		return trainingMode;
	}

	//-----------------------------------------------------------------------
	// CAPABILITIES
	//-----------------------------------------------------------------------
	/**
	 * (must be non-Javadoc)
	 * <doar completari/chestii specifice>
	 * 
	 * @see jpos.services.FiscalPrinterService111#getCapPositiveSubtotalAdjustment()
	 */
	@Override
	public boolean getCapPositiveSubtotalAdjustment() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (must be non-Javadoc)
	 * <doar completari/chestii specifice>
	 * 
	 * @see jpos.services.FiscalPrinterService19#getCapCompareFirmwareVersion()
	 */
	@Override
	public boolean getCapCompareFirmwareVersion() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (must be non-Javadoc)
	 * <doar completari/chestii specifice>
	 * 
	 * @see jpos.services.FiscalPrinterService19#getCapUpdateFirmware()
	 */
	@Override
	public boolean getCapUpdateFirmware() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getAsyncMode() throws JposException {
		return false;
	}

	//--------------------------------------------------------------------------
	// Framework Methods
	//--------------------------------------------------------------------------
	@Override
	protected void setDeviceService(BaseService service, int nServiceVersion) throws JposException {}

	@Override
	protected EventCallbacks createEventCallbacks() {
		return new MFiscalCallbacks();
	}

	//--------------------------------------------------
	// Framework Methods - specific Power Model
	//--------------------------------------------------

	/**
	 * The application may set this property to enable power reporting via StatusUpdateEvents and the PowerState property.
	 * <p>
	 * This property may only be changed while the device is disabled (that is, before DeviceEnabled is set to true).<br>
	 * This restriction allows simpler implementation of power notification with no adverse effects on the application.<br>
	 * The application is either prepared to receive notifications or doesn't want them, and has no need to switch between these cases.<br>
	 * <p>
	 * This property may be one of:<br>
	 * •PN_DISABLED<br>
	 * •PN_ENABLED<br>
	 * 
	 * @param powerNotify vezi mai sus
	 * @throws JposException
	 * 
	 */
	@Override
	public void setPowerNotify(int powerNotify) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getPowerNotify() throws JposException {
		return JPOS_PN_DISABLED;
	}

	/**
	 * Maintained by the Service at the current power condition, if it can be determined.<br>
	 * <p>
	 * This property may be one of:<br>
	 * •PS_UNKNOWN<br>
	 * •PS_ONLINE<br>
	 * •PS_OFF<br>
	 * •PS_OFFLINE<br>
	 * •PS_OFF_OFFLINE<br>
	 * <br>
	 * <p>
	 * 
	 * @return vezi mai sus
	 * @throws JposException
	 * 
	 */
	@Override
	public int getPowerState() throws JposException {
		return JPOS_PS_UNKNOWN;
	}

	//--------------------------------------------------
	// Framework Methods - specific Statistics
	//--------------------------------------------------
	@Override
	public boolean getCapStatisticsReporting() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getCapUpdateStatistics() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
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

	//--------------------------------------------------
	// Framework Methods - specific Firmware
	//--------------------------------------------------

	@Override
	public void compareFirmwareVersion(String firmwareFileName, int[] result) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void updateFirmware(String firmwareFileName) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Event Listener Methods
	//--------------------------------------------------------------------------

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
	public void addErrorListener(ErrorListener l) {
		synchronized (errorListeners) {
			errorListeners.addElement(l);
		}
	}

	@Override
	public void removeErrorListener(ErrorListener l) {
		synchronized (errorListeners) {
			errorListeners.removeElement(l);
		}
	}

	@Override
	public void addOutputCompleteListener(OutputCompleteListener l) {
		synchronized (outputCompleteListeners) {
			outputCompleteListeners.addElement(l);
		}
	}

	@Override
	public void removeOutputCompleteListener(OutputCompleteListener l) {
		synchronized (outputCompleteListeners) {
			outputCompleteListeners.removeElement(l);
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

	//--------------------------------------------------------------------------
	// EventCallbacks inner class
	//--------------------------------------------------------------------------
	protected class MFiscalCallbacks implements EventCallbacks {
		@Override
		public BaseControl getEventSource() {
			return MFiscalControl.this;
		}

		@Override
		public void fireDataEvent(DataEvent e) {}

		@Override
		public void fireDirectIOEvent(DirectIOEvent e) {
			synchronized (MFiscalControl.this.directIOListeners) {
				// deliver the event to all registered listeners
				for (int x = 0; x < MFiscalControl.this.directIOListeners.size(); x++) {
					MFiscalControl.this.directIOListeners.elementAt(x).directIOOccurred(e);
				}
			}
		}

		/**
		 * Notifies the application that a POS Printer error has been detected and that a suitable response by the application
		 * is necessary to process the error condition.
		 * <p>
		 * Doar in mod asynchron
		 * 
		 * @see jpos.services.EventCallbacks#fireErrorEvent(jpos.events.ErrorEvent)
		 */
		@Override
		public void fireErrorEvent(ErrorEvent e) {
			synchronized (MFiscalControl.this.errorListeners) {
				// deliver the event to all registered listeners
				for (int x = 0; x < MFiscalControl.this.errorListeners.size(); x++) {
					MFiscalControl.this.errorListeners.elementAt(x).errorOccurred(e);
				}
			}
		}

		@Override
		public void fireOutputCompleteEvent(OutputCompleteEvent e) {
			synchronized (MFiscalControl.this.outputCompleteListeners) {
				// deliver the event to all registered listeners
				for (int x = 0; x < MFiscalControl.this.outputCompleteListeners.size(); x++) {
					MFiscalControl.this.outputCompleteListeners.elementAt(x).outputCompleteOccurred(e);
				}
			}
		}

		/**
		 * Notifies the application that a printer has had an operation status change.<br>
		 * <p>
		 * This event contains the following attribute:<br>
		 * <p>
		 * <b>Status</b> = Indicates the status change, and has one of the following values:<br>
		 * <p>
		 * PTR_SUE_COVER_OPEN = Printer cover is open.<br>
		 * PTR_SUE_COVER_OK = Printer cover is closed.<br>
		 * PTR_SUE_REC_EMPTY = No receipt paper.<br>
		 * PTR_SUE_REC_NEAREMPTY = Receipt paper is low.<br>
		 * PTR_SUE_REC_PAPEROK = Receipt paper is ready.<br>
		 * PTR_SUE_IDLE = All asynchronous output has finished, either successfully or because output has been cleared. The printer State is now S_IDLE. The FlagWhenIdle property must be true for this event to be delivered, and the property is
		 * automatically reset to false just before the event is delivered. <br>
		 * 
		 * @see jpos.services.EventCallbacks#fireStatusUpdateEvent(jpos.events.StatusUpdateEvent)
		 */
		@Override
		public void fireStatusUpdateEvent(StatusUpdateEvent e) {
			synchronized (MFiscalControl.this.statusUpdateListeners) {
				// deliver the event to all registered listeners
				for (int x = 0; x < MFiscalControl.this.statusUpdateListeners.size(); x++) {
					MFiscalControl.this.statusUpdateListeners.elementAt(x).statusUpdateOccurred(e);
				}
			}
		}
	}

	//--------------------------------------------------------------------------
	// Capabilities
	//--------------------------------------------------------------------------

	@Override
	public boolean getCapAdditionalHeader() throws JposException {
		return true;
	}

	@Override
	public boolean getCapAdditionalTrailer() throws JposException {
		return true;
	}

	@Override
	public boolean getCapChangeDue() throws JposException {
		return true;
	}

	@Override
	public boolean getCapEmptyReceiptIsVoidable() throws JposException {
		return true;
	}

	@Override
	public boolean getCapFiscalReceiptStation() throws JposException {
		return false;
	}

	/**
	 * If true, then the Fiscal Printer supports printing different types of fiscal receipts defined by the FiscalReceiptType property.
	 * <p>
	 * This property is initialized by the open method
	 * <p>
	 * 
	 * @return da sau ba
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getCapFiscalReceiptType() throws JposException {
		return true;
	}

	@Override
	public boolean getCapMultiContractor() throws JposException {
		return false;
	}

	/**
	 * If true, then only the last printed item can be voided.<br>
	 * <p>
	 * This property is initialized by the open method.
	 * <p>
	 * 
	 * @return da sau nu
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getCapOnlyVoidLastItem() throws JposException {
		return false;
	}

	@Override
	public boolean getCapPackageAdjustment() throws JposException {
		return false;
	}

	@Override
	public boolean getCapPostPreLine() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSetCurrency() throws JposException {
		return false;
	}

	@Override
	public boolean getCapTotalizerType() throws JposException {
		return true;
	}

	@Override
	public boolean getCapAdditionalLines() throws JposException {
		return true;
	}

	@Override
	public boolean getCapAmountAdjustment() throws JposException {
		return true;
	}

	@Override
	public boolean getCapAmountNotPaid() throws JposException {
		return false;
	}

	@Override
	public boolean getCapCheckTotal() throws JposException {
		return true;
	}

	@Override
	public boolean getCapCoverSensor() throws JposException {
		return true;
	}

	@Override
	public boolean getCapDoubleWidth() throws JposException {
		return true;
	}

	@Override
	public boolean getCapDuplicateReceipt() throws JposException {
		return false;
	}

	@Override
	public boolean getCapFixedOutput() throws JposException {
		return false;
	}

	@Override
	public boolean getCapHasVatTable() throws JposException {
		return true;
	}

	/**
	 * If true, then the Fiscal Printer supports printing the fiscal receipt header lines before the first fiscal receipt command is processed.
	 * <p>
	 * This property is initialized by the open method.
	 * 
	 * @return da sau ba
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getCapIndependentHeader() throws JposException {
		return true;
	}

	@Override
	public boolean getCapItemList() throws JposException {
		return false;
	}

	@Override
	public boolean getCapNonFiscalMode() throws JposException {
		return true;
	}

	@Override
	public boolean getCapOrderAdjustmentFirst() throws JposException {
		return false;
	}

	@Override
	public boolean getCapPercentAdjustment() throws JposException {
		return true;
	}

	/**
	 * If true, then it is possible to apply surcharges via the printRecItemAdjustment method.<br>
	 * This property is initialized by the open method
	 * <p>
	 * 
	 * @return da sau nu
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getCapPositiveAdjustment() throws JposException {
		return false;
	}

	@Override
	public boolean getCapPowerLossReport() throws JposException {
		return true;
	}

	/**
	 * Identifies the reporting capabilities of the device. <br>
	 * <p>
	 * This property may be one of:<br>
	 * - PR_NONE. The Service cannot determine the state of the device. Therefore, no power reporting is possible.<br>
	 * - PR_STANDARD. The Service can determine and report two of the power states - OFF_OFFLINE (that is, off or offline) and ONLINE.<br>
	 * - PR_ADVANCED. The Service can determine and report all three power states - ONLINE, OFFLINE, and OFF.<br>
	 * <p>
	 * 
	 * @return state
	 * @throws JposException
	 * 
	 */
	@Override
	public int getCapPowerReporting() throws JposException {
		return JposConst.JPOS_PR_STANDARD;
	}

	@Override
	public boolean getCapPredefinedPaymentLines() throws JposException {
		return false;
	}

	@Override
	public boolean getCapReceiptNotPaid() throws JposException {
		return false;
	}

	@Override
	public boolean getCapRecEmptySensor() throws JposException {
		return true;
	}

	@Override
	public boolean getCapRecNearEndSensor() throws JposException {
		return true;
	}

	@Override
	public boolean getCapRecPresent() throws JposException {
		return true;
	}

	@Override
	public boolean getCapRemainingFiscalMemory() throws JposException {
		return true;
	}

	@Override
	public boolean getCapReservedWord() throws JposException {
		return false;
	}

	@Override
	public boolean getCapSetHeader() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSetPOSID() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSetStoreFiscalID() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSetTrailer() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSetVatTable() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSubAmountAdjustment() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSubPercentAdjustment() throws JposException {
		return true;
	}

	@Override
	public boolean getCapSubtotal() throws JposException {
		return true;
	}

	@Override
	public boolean getCapTrainingMode() throws JposException {
		return false;
	}

	@Override
	public boolean getCapValidateJournal() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getCapXReport() throws JposException {
		return true;
	}

	//-----------------------------
	//NOT IMPLEMENTED
	//-----------------------------
	//--------------------------------------------------------------------------
	// Methods - Specific - Fiscal Document
	//--------------------------------------------------------------------------
	/**
	 * @param documentAmount
	 * @throws JposException
	 * 
	 */
	@Override
	public void beginFiscalDocument(int documentAmount) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void endFiscalDocument() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void printFiscalDocumentLine(String documentLine) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}
	//--------------------------------------------------------------------------
	// Methods - Specific - Non-Fiscal Document
	//--------------------------------------------------------------------------

	/**
	 * (NOT IMPLEMENTED)<br>
	 * 
	 * @param station
	 * @param documentType
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support non-fiscal output (see the CapNonFiscalMode property).<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Non-Fiscal state.<br>
	 * 
	 */
	@Override
	public void beginFixedOutput(int station, int documentType) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void printFixedOutput(int documentType, int lineNumber, String data) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (NOT IMPLEMENTED)<br>
	 * Terminates non-fiscal fixed text printing on a Fiscal Printer station.
	 * <p>
	 * This method is only supported if CapFixedOutput is true. If this method is successful, the PrinterState property will be changed to FPTR_PS_MONITOR.
	 * <p>
	 * 
	 * @throws JposException <br>
	 *         <b>ErrorCode</b> <br>
	 *         E_ILLEGAL = The Fiscal Printer does not support fixed output (see the CapFixedOutput property).<br>
	 *         E_EXTENDED:<br>
	 *         ErrorCodeExtended = EFPTR_WRONG_STATE: The Fiscal Printer is not currently in the Fixed Output state.<br>
	 * 
	 */
	@Override
	public void endFixedOutput() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//--------------------------------------------------------------------------
	// Methods - Specific - Slip Insertion
	//--------------------------------------------------------------------------
	/**
	 * 
	 * @param timeout
	 * @throws JposException
	 * 
	 */
	@Override
	public void beginInsertion(int timeout) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * 
	 * @see jpos.services.FiscalPrinterService13#beginRemoval(int)
	 */
	@Override
	public void beginRemoval(int timeout) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (must be non-Javadoc)
	 * <doar completari/chestii specifice>
	 * 
	 * @see jpos.services.FiscalPrinterService13#endInsertion()
	 */
	@Override
	public void endInsertion() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * (must be non-Javadoc)
	 * <doar completari/chestii specifice>
	 * 
	 * @see jpos.services.FiscalPrinterService13#endRemoval()
	 */
	@Override
	public void endRemoval() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//----------------------------------------------
	// PROPERTIES FOR ERRORS IN ASYNCHRONOUS MODE
	//---------------------------------------------
	@Override
	public int getOutputID() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void clearOutput() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setAsyncMode(boolean asyncMode) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getErrorLevel() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getErrorOutID() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getErrorState() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getErrorStation() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getErrorString() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getFlagWhenIdle() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setFlagWhenIdle(boolean flagWhenIdle) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	//---------------------
	//
	//---------------------
	@Override
	public boolean getCapSlpEmptySensor() throws JposException {
		return false;
	}

	@Override
	public boolean getCapSlpFiscalDocument() throws JposException {
		return false;
	}

	@Override
	public boolean getCapSlpFullSlip() throws JposException {
		return false;
	}

	@Override
	public boolean getCapSlpNearEndSensor() throws JposException {
		return false;
	}

	@Override
	public boolean getCapSlpPresent() throws JposException {
		return false;
	}

	@Override
	public boolean getCapSlpValidation() throws JposException {
		return false;
	}

	@Override
	public boolean getSlpEmpty() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getSlpNearEnd() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getSlipSelection() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setSlipSelection(int slipSelection) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getCapJrnEmptySensor() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getCapJrnNearEndSensor() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getCapJrnPresent() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getJrnEmpty() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean getJrnNearEnd() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getContractorId() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setContractorId(int contractorId) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getFiscalReceiptStation() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setFiscalReceiptStation(int fiscalReceiptStation) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setDuplicateReceipt(boolean duplicateReceipt) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * If true, all the printing commands inside a fiscal receipt will be buffered and they can be printed again via the printDuplicateReceipt method.
	 * <p>
	 * This property is only valid if CapDuplicateReceipt is true.
	 * <p>
	 * This property is initialized to false by the open method.
	 * <p>
	 * 
	 * @return false/true
	 * @throws JposException
	 * 
	 */
	@Override
	public boolean getDuplicateReceipt() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Called to change to a new currency, e.g., EURO.<br>
	 * This method is only supported if CapSetCurrency is true and can only be called while DayOpened is false.<br>
	 * The actual currency is kept in the ActualCurrency property.<br>
	 * <p>
	 * 
	 * @param newCurrency The new currency.
	 * @throws JposException
	 * 
	 * @see #getActualCurrency()
	 * @see #getDayOpened()
	 */
	@Override
	public void setCurrency(int newCurrency) throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * <br>
	 * <p>
	 * 
	 * @return 2
	 * @throws JposException
	 */
	@Override
	public int getAmountDecimalPlace() throws JposException {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}
}

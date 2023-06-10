/**
 * CommandEnum.java
 * 
 * 
 * @author omatei01 (13 iul. 2017)
 * 
 */
package ro.anzisoft.common.model;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;

/**
 * CommandEnum
 * 
 * @author omatei01 (13 iul. 2017 14:15:01)
 *
 * @version 1.0
 */

public enum CommandType {
    // int id, String name, String description, KeyCode key, int maskContext, context_before, context_after, param_required, String caption, String textColor, String backgroundColor
	NONE(0, "NONE", "", null, ContextClass.CTX_LOGOUT, null, null, false, null, null, null),
	RECEIPT_START(1, "START BON", "Inceput de bon", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.INBON, false, null, null, null),
	RECEIPT_VOID(2, "RECEIPT VOID", "Anularea bonului curent", KeyCode.F9, ContextClass.CTX_LOGIN | ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.INBON, false, "ANULARE BON", null, null),
	RECEIPT_END(3, "RECEIPT END", "Terminare & salvare bon", null, ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.LOGIN, false, null, null, null),
	RECEIPT_WAIT_ON(4, "RECEIPT WAIT ON", "Punerea bonului in asteptare", KeyCode.CIRCUMFLEX, ContextClass.CTX_INBON, ContextType.LOGIN, null, false, "BON PUT", null, null),
	RECEIPT_WAIT_OFF(5, "RECEIPT WAIT OFF", "Reactivarea unui bon aflat in asteptare", KeyCode.CIRCUMFLEX, ContextClass.CTX_LOGIN, ContextType.INBON, null, false, "BON GET", null, null),
	ITEM(6, "ITEM", "Introducere produs", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, true, null, null, null),
	LAST_ITEM_VOID(7, "VOID LAST ITEM", "Anularea ultimului produs", KeyCode.F6, ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, false, "ITEM VOID", null, null),
	ITEM_VOID(8, "ITEM VOID", "Anularea unui produs", KeyCode.F7, ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, false, "ITEM LIST VOID", null, null),
	ITEM_RETUR(9, "RETUR", "Anularea unui produs", KeyCode.SUBTRACT, ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, true, "RETUR", null, null),
	ITEM_DISCOUNT(10, "DISCOUNT", "Discount suma", KeyCode.F2, ContextClass.CTX_INBON, ContextType.ITEM_DISCOUNT, ContextType.INBON, true, "REDUCERE $", null, null),
	ITEM_DISCOUNT_VOID(12, "DISCOUNT VOID", "Anulare discount", null, ContextClass.CTX_INBON | ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.NONE, false, "ANULARE REDUCERE", null, null),

	ITEM_DISCOUNT_PROC(11, "DISCOUNT %", "Discount procentual", KeyCode.F3, ContextClass.CTX_INBON | ContextClass.CTX_ITEM_DISCOUNT, ContextType.ITEM_DISCOUNT_PROC, ContextType.INBON, true, "REDUCERE %", "black", "aquamarine "),
	ITEM_DISCOUNT_PROC_VOID(12, "DISCOUNT VOID", "Anulare discount", null, ContextClass.CTX_INBON | ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.NONE, false, "ANULARE REDUCERE", "red", "white"),

	ITEM_CHANGE_PRICE(13, "CHANGE PRICE", "Modificare pret", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.ITEM_PRICE, ContextType.INBON, true, "PRET", null, null),
	ITEM_CHECK(14, "ITEM CHECK", "Verificare produs", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON | ContextClass.CTX_TOTAL, ContextType.ITEM_CHECK, ContextType.NONE, true, "VERIFICARE", null, null), // daca e context_after = NONE se revine
    // la cel initial!!
	ITEM_SEARCH(15, "ITEM SEARCH", "Cautare produs", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.ITEM_SEARCH, ContextType.NONE, true, "CAUTARE PRODUS", null, null),
	ITEM_REPEAT(16, "REPEAT LAST ITEM", "Repetarea ultimului produs", null, ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, false, "REPETARE", null, null),

    // int id, String name, String description, KeyCode key, int maskContext, context_before, context_after, param_required, String caption, String textColor, String backgroundColor
	SUBTOTAL(17, "SUBTOTAL", "Subtotal", null, ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, false, "SUBTOTAL", null, null),
	TOTAL(18, "TOTAL", "Total", null, ContextClass.CTX_INBON, ContextType.NONE, ContextType.TOTAL, false, "TOTAL", null, null),
	TOTAL_VOID(19, "TOTAL VOID", "Anulare total", null, ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.INBON, false, "ANULARE TOTAL", "red", "white"),
	TOTAL_DISCOUNT(10, "DISCOUNT", "Discount suma", KeyCode.F2, ContextClass.CTX_INBON | ContextClass.CTX_TOTAL | ContextClass.CTX_TOTAL_DISCOUNT, ContextType.TOTAL_DISCOUNT, ContextType.TOTAL, true, "REDUCERE BON", null, null),
	TOTAL_DISCOUNT_VOID(11, "DISCOUNT_VOID%", "Anulare discountl", KeyCode.F3, ContextClass.CTX_TOTAL | ContextClass.CTX_TOTAL_DISCOUNT_PROC, ContextType.TOTAL_DISCOUNT_PROC, ContextType.TOTAL, true, "ANULARE REDUCERE", null, null),
	TOTAL_DISCOUNT_PROC(11, "DISCOUNT %", "Discount procentual", KeyCode.F3, ContextClass.CTX_TOTAL | ContextClass.CTX_TOTAL_DISCOUNT_PROC, ContextType.TOTAL_DISCOUNT_PROC, ContextType.TOTAL, true, "REDUCERE % BON", null, null),
	TOTAL_DISCOUNT_PROC_VOID(11, "DISCOUNT_VOID%", "Anulare discount", KeyCode.F3, ContextClass.CTX_TOTAL | ContextClass.CTX_TOTAL_DISCOUNT_PROC, ContextType.TOTAL_DISCOUNT_PROC, ContextType.TOTAL, true, "ANULARE REDUCERE", null, null),

	PAYMENT(20, "PAYMENT", "Plata", null, ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.NONE, true, "CASH", null, null),
	TENDER_CARD(21, "PAYMENT", "Plata card", null, ContextClass.CTX_TOTAL, ContextType.TENDER_CARD, ContextType.TOTAL, true, "CARD", null, null),
	TENDER_CARD_EFT(22, "PAYMENT", "Plata card direct prin EFT", null, ContextClass.CTX_TOTAL, ContextType.TENDER_CARD_EFT, ContextType.TOTAL, true, "EFT CARD", null, null),
	TENDER_TA(23, "PAYMENT", "Plata cu tichet din lista", null, ContextClass.CTX_TOTAL, ContextType.TENDER_TA, ContextType.TOTAL, true, null, null, null),
	TENDER_TG(24, "PAYMENT", "Plata cu tichet generic", null, ContextClass.CTX_TOTAL, ContextType.TENDER_TG, ContextType.TOTAL, true, "TICHET", null, null),

    // int id, String name, String description, KeyCode key, int maskContext, context_before, context_after, param_required, String caption, String textColor, String backgroundColor
	CUSTOMER(25, "CUSTOMER", "Introducere client", null, ContextClass.CTX_LOGIN | ContextClass.CTX_TOTAL, ContextType.CUSTOMER, ContextType.NONE, true, "CLIENT", "white", "#1e90ff"),
	CUSTOMER_VOID(26, "CUSTOMER_VOID", "Anulare client", null, ContextClass.CTX_LOGIN | ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.NONE, false, "ANULARE CLIENT", "red", "white"),

	SALESPERSON(27, "SALESPERSON", "Vinzator", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.SALES, ContextType.NONE, true, "VINZATOR", null, null),
	SALESPERSON_VOID(28, "SALESPERSON_VOID", "Anulare vinzator", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, true, "ANULARE VINZATOR", null, null),

	QUANTITY(29, "QUANTITY", "Cantitate", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.QUANTITY, ContextType.NONE, true, "CANTITATE", null, null),
	QUANTITY_VOID(30, "QUANTITY_VOID", "Anulare cantitate", null, ContextClass.CTX_LOGIN | ContextClass.CTX_INBON, ContextType.NONE, ContextType.NONE, false, "ANULARE CANT", null, null),

	LOGIN(31, "LOGIN", "Conectare utilizator", null, ContextClass.CTX_LOGOUT | ContextClass.CTX_PAUSE, ContextType.NONE, ContextType.LOGIN, true, "LOGIN", null, null),
	PAUSE(32, "PAUSE", "Intrare in pauza utilizator", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.PAUSE, false, "PAUZA", null, null),
	LOGOUT(33, "LOGOUT", "Deconectare utilizator", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.LOGOUT, false, null, null, null),
	EXIT(34, "SHUTDOWN", "Oprire casa", null, ContextClass.CTX_LOGIN | ContextClass.CTX_LOGOUT, ContextType.NONE, ContextType.NONE, false, "IESIRE", null, null),

	DRAWER_OPEN(35, "DRAWER OPEN", "Deschidere sertar", null, ContextClass.CTX_LOGIN | ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.NONE, false, "SERTAR", null, null),

	MENU_PLU(36, "PLU MENU", "Meniu plu", null, ContextClass.CTX_INBON | ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "PLU", null, null),
	MENU_TENDER(37, "TENDER MENU", "Meniu plati", null, ContextClass.CTX_TOTAL, ContextType.NONE, ContextType.NONE, false, "PLATI", null, null),
	MENU_CASHIER(38, "CASHIER MENU", "Operatii casier", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "CASIER", null, null),
	MENU_ADMIN(39, "ADMIN MENU", "Operatii supervizor", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "ADMIN", null, null),
	MENU_SERVICE(40, "SERVICE MENU", "Operatii service", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "SERVICE", null, null),

	CMD_RAP_Z(41, "Z REPORT", "Inchidere zi fiscala", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "Z", null, null),
	CMD_RAP_X(42, "X REPORT", "Raport vinzari MFA", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "X", null, null),
	CMD_RAP_MEM(43, "MEM REPORT", "Raport memorie fiscala ANAF", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "Citire MFA", null, null),
	CMD_INVOICE(44, "INVOICE", "FActura la ultimul bon", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, "FACTURA", null, null),
	CMD_RAP_1(45, "-", "Raport 1", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, null, null, null),
	CMD_RAP_2(46, "-", "", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, null, null, null),
	CMD_RAP_3(47, "-", "", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, null, null, null),
	CMD_RAP_4(48, "-", "", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, null, null, null),
	CMD_RAP_5(49, "-", "", null, ContextClass.CTX_LOGIN, ContextType.NONE, ContextType.NONE, false, null, null, null),

	CMD_OP_REMITERE(50, "CASH_IN", "Alimentare casa", null, ContextClass.CTX_LOGIN, ContextType.OPCASA, ContextType.NONE, true, null, null, null),
	CMD_OP_INCASARE(51, "CASH_OUT", "Remitere din casa", null, ContextClass.CTX_LOGIN, ContextType.OPCASA, ContextType.NONE, true, null, null, null);

	/*    // auxiliare si sunt folosite doar intern
		CMD_POS_START(100, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_STOP(101, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_START_AFTER_Z(102, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_ITEM_UNKONWN(103, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_RAP_Z_START(104, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_RAP_Z_STOP(105, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_START_ANORMAL(106, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN),
		CMD_POS_START_ANORMAL_INBON(107, "-", "Discount procentual", "Discount %", null, ContextClass.LOGIN);
	*/

	final int CMD_MAX = 51; // numar maxim de comenzi

	private int id;
	private String name;
	private String description;
	private KeyCode key;
	private int maskContext;
	private ContextType contextBeforeCmd; // contextul in care trece app inainte de executie/executie fara params -> get_params_input -> cind apesi pe buton comanda!!! (de ex cind apesi quantity, astepti)
	private ContextType contextAfterCmd; // contextul in care trece app dupa executie -> cind executia cu succes determina alt context
	private boolean paramsRequired; // daca sunt necesari parametrii

	private String caption;
	private String colorText;
	private String colorBackground;

	// private boolean withAuthrz; -> separat caci se configureaza

	/**
	 * 
	 * CTOR
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param caption
	 * @param key
	 * @param maskContext
	 * @param contextBeforeCmd
	 * @param contextAfterCmd
	 * @param paramsRequired
	 * @since 1.0
	 */
	private CommandType(int id, String name, String description, KeyCode key, int maskContext, ContextType contextBeforeCmd, ContextType contextAfterCmd, boolean paramsRequired, String caption, String colorText, String colorBackground) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.key = key;
		this.maskContext = maskContext;
		this.contextBeforeCmd = contextBeforeCmd;
		this.contextAfterCmd = contextAfterCmd;
		this.paramsRequired = paramsRequired;

		this.caption = caption;
		this.colorText = colorText;
		this.colorBackground = colorBackground;

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	//
	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	// daca null inseamna ca nu are tasta asociata
	public KeyCode getKeyCode() {
		return this.key;
	}

	// context inainte de exec/exec without params; daca e null ramine ca app sa stabileasca
	public ContextType getPreContextBeforeExecution() {
		return this.contextBeforeCmd;
	}

	// context dupa exec/exec cu succes; daca e null ramine ca app sa stabileasca
	public ContextType getPreContextAfterExecution() {
		return this.contextAfterCmd;
	}

	public boolean isRequiredParams() {
		return paramsRequired;
	}

	public boolean isValidInContext(ContextType c) {
		return (maskContext & c.getId()) != 0;
	}

	public String getCaption() {
		return this.caption;
	}

	public String getColorText() {
		return this.colorText;
	}

	public String getColorBackground() {
		return this.colorBackground;
	}

	// pentru cautare dupa id gen CommandEnum.get(ID_COMMAND)
	private static final Map<Integer, CommandType> idMap;

	// pentru cautare dupa tasta gen CommandEnum.get(keyCode) -. daca e null inseamna ca nu am nicio comanda pe tasta ceruta
	private static final Map<KeyCode, CommandType> keyMap;
	static {
		idMap = new HashMap<Integer, CommandType>(CommandType.values().length);
		keyMap = new HashMap<KeyCode, CommandType>();
		for (final CommandType c : CommandType.values()) {
			idMap.put(c.getId(), c);
			if (c.getKeyCode() != null) {
				keyMap.put(c.getKeyCode(), c);
			}
		}
	}

	public static CommandType get(int id) {
		return idMap.get(id);
	}

	public static CommandType get(KeyCode key) {
		return keyMap.get(key);
	}

	private static class ContextClass {
		private ContextClass() {};

		private static final int CTX_LOGOUT = ContextType.LOGOUT.getId();
		private static final int CTX_LOGIN = ContextType.LOGIN.getId();
		private static final int CTX_INBON = ContextType.INBON.getId();
		private static final int CTX_TOTAL = ContextType.TOTAL.getId();
		private static final int CTX_ERR = ContextType.ERR.getId();
		private static final int CTX_ERRPOS = ContextType.ERRPOS.getId();
		private static final int CTX_AUTHORIZE = ContextType.AUTHORIZE.getId();
		private static final int CTX_QUANTITY = ContextType.QUANTITY.getId();
		private static final int CTX_CUSTOMER = ContextType.CUSTOMER.getId();
		private static final int CTX_SALESPERSON = ContextType.SALES.getId();
		private static final int CTX_ITEM_DISCOUNT = ContextType.ITEM_DISCOUNT.getId();
		private static final int CTX_ITEM_DISCOUNT_PROC = ContextType.ITEM_DISCOUNT_PROC.getId();
		private static final int CTX_ITEM_PRICE = ContextType.ITEM_PRICE.getId();
		private static final int CTX_ITEM_CHECK = ContextType.ITEM_CHECK.getId();
		private static final int CTX_ITEM_VOID = ContextType.ITEM_VOID.getId();
		private static final int CTX_ITEM_SEARCH = ContextType.ITEM_SEARCH.getId();
		private static final int CTX_OPCASA = ContextType.OPCASA.getId();
		private static final int CTX_PAUSE = ContextType.PAUSE.getId();
		private static final int CTX_JE = ContextType.JE.getId();
		private static final int CTX_TENDER_TG = ContextType.TENDER_TG.getId(); // generic
		private static final int CTX_TENDER_TA = ContextType.TENDER_TA.getId();
		private static final int CTX_TENDER_CARD = ContextType.TENDER_CARD.getId();
		private static final int CTX_TENDER_CARD_EFT = ContextType.TENDER_CARD_EFT.getId();
		private static final int CTX_TOTAL_DISCOUNT = ContextType.TOTAL_DISCOUNT.getId();
		private static final int CTX_TOTAL_DISCOUNT_PROC = ContextType.TOTAL_DISCOUNT_PROC.getId();
	}
}

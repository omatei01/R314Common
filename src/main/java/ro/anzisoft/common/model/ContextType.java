/**
 * 
 * 
 * @author omatei01 (14 iul. 2017)
 * 
 */
package ro.anzisoft.common.model;

/**
 * ContextEnum
 * <p>
 * <b>Contextele de lucru POS</b>
 * <p>
 * Sesiunea Posapp se poate regasi intr-unul din aceste contexte. 
 * In fc de context se poate determina ce comenzi sunt acceptate.
 * <p>
 * In fc de context se pot accesa diverse proprietati:
 * revenire din context
 * 
 * @author omatei01 (14 iul. 2017 15:29:22)
 *
 * @version 1.0
 */
public enum ContextType {

    // vezi si CommandUtil
	NONE(0, "NONE"),
	LOGOUT(1, "CTX_LOGOUT"),
	LOGIN(1 << 1, "CTX_LOGIN"), // iesire din tura sau intrare in app
	INBON(1 << 2, "CTX_INBON"), // dupa primul produs
	TOTAL(1 << 3, "CTX_TOTAL"), // dupa apasare tasta total
	ERR(1 << 4, "CTX_ERROR"), // eroare app -> oricind cind apare o err care necesita interventia explicita a userului -> numai clear
	ERRPOS(1 << 5, "ERROR DEVICE"), // eroare periferic
	AUTHORIZE(1 << 6, "CTX_AUTORIZARE"), // astept autorizare sau corectie -> adica renunta la comanda care necesita atrz
	QUANTITY(1 << 7, "CTX_QUANTITY"), // dupa apasare tasta cantitate fara cant si asteapta introducerea cantitatii
	CUSTOMER(1 << 8, "CTX_CUSTOMER"), // dupa tasta client fara param -> astept cod client sau corectie
	SALES(1 << 9, "SALESPERSON"), // dupa tasta sales fara param -> astept cod sales sau corectie
	ITEM_DISCOUNT(1 << 10, "DISCOUNT"), // dupa tasta discount fara param ->astept discount
	ITEM_DISCOUNT_PROC(1 << 11, "DISCOUNT%"),	
	ITEM_PRICE(1 << 12, "CHANGE PRICE"), // dupa tasta pret fara pret ->astept pret nou
	ITEM_CHECK(1 << 13, "CHECK ITEM"), // dupa tasta discount fara discount ->astept discount
	ITEM_SEARCH(1 << 14, "SEARCH ITEM"), // dupa tasta discount fara discount ->astept discount
	ITEM_VOID(1 << 15, "VOID ITEM"),
	OPCASA(1 << 16, "OPCASA"),
	PAUSE(1 << 17, "PAUSE"),
	TENDER_TG(1 << 18, "TG"), // generic
	TENDER_TA(1 << 19, "TA"), // cu val nominala
	TENDER_CARD(1 << 20, "CARD"), // cu card
	TENDER_CARD_EFT(1 << 21, "EFT"), // cu card
	TOTAL_DISCOUNT(1 << 22, "DISCOUNT"), // dupa tasta discount fara param ->astept discount
	TOTAL_DISCOUNT_PROC(1 << 23, "DISCOUNT%"),
	JE(1 << 24, "JE");

	final int CTX_MAX = 18;

	int id;
	String description;
	// CommandEnum defaultCommand; -> vezi CommandUtil

	private ContextType(int id, String descr) {
		this.id = id;
		this.description = descr;
	}

	public int getId() {
		return this.id;
	}

	public String getDescr() {
		return this.description;
	}

}

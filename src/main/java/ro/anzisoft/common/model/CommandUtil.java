/**
 * Command.java
 * 
 * 
 * @author omatei01 (12 iul. 2017)
 * 
 */
package ro.anzisoft.common.model;

import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

/**
 * Command
 * <p>
 * <b>Description</b>
 * <p>
 * -- descriere --
 * <p>
 * <b>Sectiune 1</b>
 * <p>
 * -- informatii --
 * <p>
 * TODO
 * <p>
 * 
 * @author omatei01 (12 iul. 2017 10:02:03)
 *
 * @version 1.0
 */
public class CommandUtil {

	// astea sunt comenzile default: cind se da ENTER intr-un anumit context
	// vezi CommandEnum pentru indecsi
	// ATENTIE: nu se poate pune direct in enum caci am o dependenta ciclica
	private static final ImmutableMap<ContextType, CommandType> defaultCommandInContext = ImmutableMap
	        .<ContextType, CommandType> builder()
	        .put(ContextType.LOGOUT, CommandType.LOGIN)
	        .put(ContextType.LOGIN, CommandType.ITEM)
	        .put(ContextType.INBON, CommandType.ITEM)
	        .put(ContextType.TOTAL, CommandType.PAYMENT)
	        .put(ContextType.ERR, CommandType.NONE)
	        .put(ContextType.ERRPOS, CommandType.NONE)
	        .put(ContextType.QUANTITY, CommandType.QUANTITY)
	        .put(ContextType.CUSTOMER, CommandType.CUSTOMER)
	        .put(ContextType.SALES, CommandType.SALESPERSON)
	        .put(ContextType.ITEM_DISCOUNT, CommandType.ITEM_DISCOUNT)
	        .put(ContextType.ITEM_DISCOUNT_PROC, CommandType.ITEM_DISCOUNT_PROC)
	        .put(ContextType.ITEM_PRICE, CommandType.ITEM_CHANGE_PRICE)
	        .put(ContextType.ITEM_CHECK, CommandType.ITEM_CHECK)
	        .put(ContextType.ITEM_SEARCH, CommandType.ITEM_SEARCH)
	        .put(ContextType.ITEM_VOID, CommandType.ITEM_VOID)
	        .put(ContextType.OPCASA, CommandType.NONE)
	        .put(ContextType.PAUSE, CommandType.LOGIN)
	        .put(ContextType.TENDER_TG, CommandType.TENDER_TG) // generic
	        .put(ContextType.TENDER_TA, CommandType.TENDER_TA) // cu val nominala
	        .put(ContextType.TENDER_CARD, CommandType.TENDER_CARD) // cu card
	        .put(ContextType.TENDER_CARD_EFT, CommandType.TENDER_CARD_EFT) // cu card
	        .put(ContextType.JE, CommandType.NONE)
	        .build();

	// doar comenzile care se pot pune pe butoane/cu context propriu
	private static final ImmutableMap<ContextType, String> defaultMessageInContext = ImmutableMap
	        .<ContextType, String> builder()
	        .put(ContextType.LOGIN, "Introduceti produsul")
	        .put(ContextType.LOGOUT, "Introduceti parola")
	        .put(ContextType.INBON, "Introduceti produsul")
	        .put(ContextType.ITEM_CHECK, "Introduceti produsul")
	        .put(ContextType.ITEM_SEARCH, "Introduceti produsul")
	        .put(ContextType.CUSTOMER, "Introduceti clientul")
	        .put(ContextType.QUANTITY, "Introduceti cantitatea")
	        .put(ContextType.TOTAL, "Introduceti suma")
	        .put(ContextType.SALES, "Cod vinzator")
	        .put(ContextType.ITEM_DISCOUNT, "Reducerea")
	        .put(ContextType.ITEM_DISCOUNT_PROC, "Reducerea %")
	        .put(ContextType.ITEM_PRICE, "Pret")
	        .put(ContextType.ITEM_VOID, "Anulare item")
	        .put(ContextType.OPCASA, "Introduceti suma")
	        .put(ContextType.PAUSE, "Introduceti codul")
	        .put(ContextType.TENDER_TG, "Plata") // generic
	        .put(ContextType.TENDER_TA, "Plata") // cu val nominala
	        .put(ContextType.TENDER_CARD, "Plata") // cu card
	        .put(ContextType.TENDER_CARD_EFT, "Plata cu EFT") // cu card
	        .put(ContextType.JE, "JE")	        
	        .build();
	//
	private static HashMap<CommandType, Boolean> commandAuhtorize = new HashMap<>();
	private static boolean _initCommandAuhtorize = false;

	private static void initCommandAuthorize() {
		// resetez ca sa init. dupa aia
		for (int i = 0; i < CommandType.values().length; i++) {
			commandAuhtorize.put(CommandType.values()[i], false);
		}

		// de aici ar tr sa preiau din tabela de setari
		commandAuhtorize.put(CommandType.ITEM_DISCOUNT, true);
		commandAuhtorize.put(CommandType.ITEM_DISCOUNT_PROC, true);
		commandAuhtorize.put(CommandType.TOTAL_DISCOUNT, true);
		commandAuhtorize.put(CommandType.TOTAL_DISCOUNT_PROC, true);
		commandAuhtorize.put(CommandType.CUSTOMER, true);
		_initCommandAuhtorize = true;
	}

	//
	public static boolean getAuthorize(CommandType command) {
		if (!_initCommandAuhtorize)
			initCommandAuthorize();
		return commandAuhtorize.get(command);
	}

	public static CommandType getCommandDefault(ContextType context) {
		return defaultCommandInContext.get(context);
	}

	// mesajele se pot prelua dintr-un fisier de configurari
	public static String getMessageDefault(ContextType context) {
		return defaultMessageInContext.get(context);
	}

}

/**
 * 
 */
package ro.anzisoft.common.db.domain;

import java.sql.Timestamp;
import java.util.Calendar;

import com.google.common.base.Strings;

import lombok.Data;
import ro.anzisoft.common.types.TokenStatusEnum;
import ro.anzisoft.common.util.MTypes;

/**
 * in mod normal, la fiecare pornire se genereaza un token nou; la logout se invalideaza token-ul
 * <p>
 * Mod de folosire UUID cu sqlite:
 * <p>
 * - configurare: vezi db\helper\UUIDArgument si DataProvider
 * <p>
 * - vezi TokenMapper: cum se foloseste cu ResultSet: UUID.fromString(r.getString("tokenuuid"))
 * <p>
 * - cum se foloseste cu Bind: MyDto myMethod(@Bind("myField") UUID myField);
 * <p>
 * - in tabela cimpul e definit ca String[36]
 * <p>
 * <p>
 * 
 * UUID.randomUUID().toString()
 * 
 * @author omatei01
 *
 */
@Data
public class TokenDb {

	private String tokenUUID;// STRING (36) PRIMARY KEY NOT NULL,
	private String instanceUUID;// STRING (36) REFERENCES instance NOT NULL,
	private String userCode;// STRING (30) REFERENCES user (usercode) NOT NULL,
	private Timestamp dateCreation = MTypes.getCurrentTimestamp(); // JDBI: STRING ;DATETIME DEFAULT (CURRENT_TIMESTAMP), YYYY-MM-DD HH:MM:SS.SSS"
	private Timestamp dateTermination = MTypes.getCalculateTimestamp(Calendar.HOUR, 24); // JDBI: STRING; DATETIME DEFAULT (CURRENT_TIMESTAMP), YYYY-MM-DD HH:MM:SS.SSS"
	private TokenStatusEnum status = TokenStatusEnum.ACTIV; // JDBI: INT

	/**
	 * 
	 * CTOR
	 * <p>
	 * ATentie: acest ctor e apelat de jdbi-mapper - parametrii vin cu tipuri de date JDBI-SQLITE si se convertesc JAVA-SQL
	 * <p>
	 * 
	 * @param tokenUUID
	 * @param instanceUUID
	 * @param userCode
	 * @param dateTermination_optional
	 * @param status_optional
	 */
	public TokenDb(String tokenUUID, String instanceUUID, String userCode, String dateTermination_optional, Integer status_optional) {
		this.instanceUUID = instanceUUID;
		this.tokenUUID = tokenUUID;
		this.userCode = userCode;

		if (!Strings.isNullOrEmpty(dateTermination_optional)) dateTermination = MTypes.toTimestamp(dateTermination_optional, MTypes.PATTERN_DATETIME_DB);
		if (status_optional != null) status = TokenStatusEnum.fromInt(status_optional);
	}

	/**
	 * UUID.randomUUID().toString()
	 * 
	 * @param id
	 */

}

/**
 * 
 */
package ro.anzisoft.common.db.domain;

import java.sql.Timestamp;

import lombok.Data;
import ro.anzisoft.common.types.InstanceStatusEnum;
import ro.anzisoft.common.util.MTypes;

/**
 * 
 * Instance
 * <p>
 * Orice instanta de R314Srv trebuie definita la instalare.
 * Pe baza datelor de identificare se tabileste si mecanismul de licentiere
 * 
 * @author omatei01 (19 iul. 2017)
 *
 * @version 1.0
 */
@Data
public class InstanceDb {
	private String instanceUUID;// STRING (36) PRIMARY KEY,
	private String company;// STRING (30) REFERENCES company (companyCode),
	private String store;// STRING (30),
	private String pos;// STRING (30),
	private String serialMfa;// STRING (30),
	private String secretKey;// STRING,
	private Timestamp dateInstall;// DATETIME,
	private String userInstall;// STRING (30),
	private InstanceStatusEnum status;// INTEGER DEFAULT (1)

	/**
	 * 
	 * CTOR necesar petntru mapper
	 * 
	 * @param instanceUUID
	 * @param company
	 * @param store
	 * @param pos
	 * @param secretKey
	 * @param dateInstall format: MTypes.PATTERN_DATETIME_DB
	 * @param userInstall
	 * @param status
	 * @since 1.0
	 */
	public InstanceDb(String instanceUUID, String company, String store, String pos, String serialMfa, String secretKey, String dateInstall, String userInstall, Integer status) {
		super();
		this.instanceUUID = instanceUUID;
		this.company = company;
		this.store = store;
		this.pos = pos;
		this.serialMfa = serialMfa;
		this.secretKey = secretKey;
		this.dateInstall = MTypes.toTimestamp(dateInstall, MTypes.PATTERN_DATETIME_DB);
		this.userInstall = userInstall;
		this.status = InstanceStatusEnum.fromInt(status);
	}

}

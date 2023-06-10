package ro.anzisoft.common.db.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

import lombok.Data;
import ro.anzisoft.common.types.UserRoleEnum;
import ro.anzisoft.common.types.UserStatusEnum;
import ro.anzisoft.common.util.MTypes;

/**
 * <b>UserDb</b><br>
 * @author omatei01 (2018)
 */
@Data
public class UserDb {

/*	@NotNull
	@Length(min = 3, max = 30)*/
	private String userCode; // STRING (30) PRIMARY KEY NOT NULL,
/*	@NotNull
	@Length(min = 2, max = 100)
*/	private String name;// STRING (100) NOT NULL,
/*	@NotNull
	@Length(min = 2, max = 100)
*/	private String pwd;
/*	@Pattern(regexp = ".+@.+\\.[a-z]+")
	@Length(min = 2, max = 100)
*/	private String email;// STRING (100) NOT NULL,

	//cimpuri auxiliare
	@JsonIgnore
	private UserRoleEnum role2; // STRING (50) DEFAULT (1) NOT NULL, role -> l-am lasat string ca sa fie mai simplu: admin, regular
	@JsonIgnore
	private UserStatusEnum status2 = UserStatusEnum.OK;
	@JsonIgnore
	private Timestamp dateEnd2 = MTypes.getMaxTimestamp();

	// -- continuare cimpuri (au corespondente in aux!)
	private String role;
	private int status;
	private String dateEnd = MTypes.toStringFromTimestamp(dateEnd2, MTypes.PATTERN_DATETIME_DB); // DATETIME DEFAULT (CURRENT_DATE + 10), YYYY-MM-DD HH:MM:SS.SSS"

	/**
	 * 
	 * CTOR
	 * 
	 * @param userCode
	 * @param name
	 * @param email
	 * @param pwd
	 * @param role
	 * @param dateEnd_optional
	 * @param status_optional
	 * @since 1.0
	 */
	public UserDb(String userCode, String name, String email, String pwd, String role, String dateEnd_optional, Integer status_optional) {
		this.userCode = userCode;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.role = role.toUpperCase();
		this.role2 = UserRoleEnum.fromString(role);

		// tratare optionale
		if (status_optional != null) {
			this.status = status_optional;
			this.status2 = UserStatusEnum.fromInt(status);
		}

		if (!Strings.isNullOrEmpty(dateEnd_optional)) {
			//System.out.println("create user: (data termination):" + dateEnd_optional);
			this.dateEnd = dateEnd_optional;
			this.dateEnd2 = MTypes.toTimestamp(this.dateEnd, MTypes.PATTERN_DATETIME_DB);
		}
	}

	@Override
	public String toString() {
		return "User [userCode=" + userCode + ", name=" + name + ", pwd=" + pwd + ", email=" + email + ", data terminare=" + dateEnd2 + " ]";
	}

}

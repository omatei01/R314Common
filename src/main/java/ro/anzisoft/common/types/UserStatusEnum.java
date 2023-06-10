/**
 * UserStatusEnum.java
 * 
 * 
 * @author omatei01 (26 iul. 2017)
 * 
 */
package ro.anzisoft.common.types;

import java.util.Optional;

/**
 * UserStatusEnum
 * <p>
 * un user poate avea urmatoarele status-uri:<br>
 * - nou/doar inregistrat dar fara confimare<br>
 * - ok - user creat, activat ok cu adresa de email verificata<br>
 * - blocat<br>
 * - inactiv<br>
 * 
 * @author omatei01 (26 iul. 2017 10:26:23)
 *
 * @version 1.0
 */
public enum UserStatusEnum {
	OK(0, "Activ"),
	NEW(1, "New"),
	INACTIV(2, "Inactiv"),
	TERMINAT(3, "Sters");

	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	private UserStatusEnum(int level, String longDescription) {
		this.level = level;
		this.description = longDescription;
	}

	public static UserStatusEnum fromInt(int status) {
		UserStatusEnum r = UserStatusEnum.TERMINAT;
		switch (status) {
			case 0:
				r = UserStatusEnum.OK;
				break;
			case 1:
				r = UserStatusEnum.NEW;
				break;
			case 2:
				r = UserStatusEnum.INACTIV;
				break;
			case 3:
				r = UserStatusEnum.TERMINAT;
				break;

		}
		return r;
	}
	
}

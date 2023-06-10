/**
 * TokenStatusEnum.java
 * 

 * @author omatei01 (26 iul. 2017)
 * 
 */
package ro.anzisoft.common.types;

/**
 * TokenStatusEnum
 * <p>
 * <b>Description</b>
 * <p>
 * @author omatei01 (26 iul. 2017 10:47:36)
 *
 * @version 1.0
 */
public enum TokenStatusEnum {
	ACTIV(0, "Activ"),
	EXPIRATED(1, "Expirat");

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

	private TokenStatusEnum(int level, String longDescription) {
		this.level = level;
		this.description = longDescription;
	}

	public static TokenStatusEnum fromInt(int status) {
		TokenStatusEnum r = TokenStatusEnum.ACTIV;
		switch (status) {
			case 0:
				r = TokenStatusEnum.ACTIV;
				break;
			case 1:
				r = TokenStatusEnum.EXPIRATED;
				break;
		}
		return r;
	}

}

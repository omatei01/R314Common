/**
 * InstanceStatusEnum.java
 * 
 * 
 * @author omatei01 (1 aug. 2017)
 * 
 */
package ro.anzisoft.common.types;

/**
 * InstanceStatusEnum
 * <p>
 * <b>Description</b>
 * <p>
 * 
 * @author omatei01 (1 aug. 2017 17:13:23)
 *
 * @version 1.0
 */
public enum InstanceStatusEnum {
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

	private InstanceStatusEnum(int level, String longDescription) {
		this.level = level;
		this.description = longDescription;
	}

	public static InstanceStatusEnum fromInt(int status) {
		InstanceStatusEnum r = InstanceStatusEnum.ACTIV;
		switch (status) {
			case 0:
				r = InstanceStatusEnum.ACTIV;
				break;
			case 1:
				r = InstanceStatusEnum.EXPIRATED;
				break;
			default:
				break;
		}
		return r;
	}
}

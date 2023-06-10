/**
 * Tva.java
 * 
 * 
 * @author omatei01 (2017)
 * 
 */
package ro.anzisoft.common.model;

/**
 * <b>Tva</b>
 * <p>
 * -- descriere -- <br>
 * TODO
 * <p>
 * 
 * @author omatei01 (16 nov. 2017)
 *
 * @version 1.0
 */
public enum Tva {
	A(1, "A", "Cota A"),
	B(2, "B", "Cota B"),
	C(3, "C", "Cota C"),
	D(4, "D", "Cota D"),
	E(5, "E", "Cota E");

	static final int TVA_COUNT = 5;

	int id;
	String code;
	String descr;

	private Tva(int id, String code, String descr) {
		this.id = id;
		this.code = code;
		this.descr = descr;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return descr;
	}
}

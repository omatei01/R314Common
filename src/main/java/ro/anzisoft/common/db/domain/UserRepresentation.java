/**
 * UserRepresentation.java
 * 
 * 
 * @author omatei01 (16 aug. 2017)
 * 
 */
package ro.anzisoft.common.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * UserRepresentation
 * <p>
 * <b>Exemplu de obj domain cu lombock</b>
 * <p>
 * @author omatei01 (16 aug. 2017 17:44:13)
 *
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRepresentation {
	/**
	 * userCode STRING (30) PRIMARY KEY NOT NULL,
	 * name STRING (100) NOT NULL,
	 * email STRING (100) NOT NULL,
	 * pwd STRING (250),
	 * role STRING (50) DEFAULT (1) NOT NULL,
	 * dateTermination DATETIME DEFAULT (CURRENT_DATE + 10), YYYY-MM-DD HH:MM:SS.SSS"
	 * status INTEGER DEFAULT (1),
	 * securityGroup1 STRING (30),
	 * securityGroup2 STRING (30)
	 */
/*	@NotNull
	@Length(min = 3, max = 30)*/
	private String userCode;
/*	@NotBlank
	@Length(min = 2, max = 100)
*/	private String name; 
/*	@NotBlank
	@Length(min = 2, max = 100)
*/	private String pwd;
/*	@Pattern(regexp = ".+@.+\\.[a-z]+")
	@Length(min = 2, max = 100)
*/	private String email;
	private String role; // role -> l-am lasat string ca sa fie mai simplu: admin, regular
	private int status;

	private String dateTermination;//yyyy-MM-dd hh:mm
}

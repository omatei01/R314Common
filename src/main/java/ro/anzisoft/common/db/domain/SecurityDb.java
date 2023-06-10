package ro.anzisoft.common.db.domain;

import java.sql.Timestamp;

/**
 * 
 * Security

 * @author omatei01 (19 iul. 2017)
 *
 * @version 1.0
 */
public class SecurityDb {
	private String userid; // varchar(50) Unchecked
	private String email;// varchar(200) Checked
	String generatedkey; // varchar(20) Checked
	private Timestamp creationdate;// datetime2(7) Checked
	private Timestamp lastmodifieddate;// datetime2(7) Checked
	private int type;// smallint Unchecked
	private boolean deleted;// bit Unchecked
	private String info1;// varchar(200) Checked
	private String info2;// varchar(200) Checked
	private String pwd;// varchar(200) Checked
	private String ip;// varchar(50) Checked
	private int verificationcode;

	int id;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVerificationcode() {
		return this.verificationcode;
	}

	public void setVerificationcode(int verificationcode) {
		this.verificationcode = verificationcode;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGeneratedkey() {
		return this.generatedkey;
	}

	public void setGeneratedkey(String generatedkey) {
		this.generatedkey = generatedkey;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public Timestamp getLastmodifieddate() {
		return this.lastmodifieddate;
	}

	public void setLastmodifieddate(Timestamp lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getInfo1() {
		return this.info1;
	}

	public void setInfo1(String info1) {
		if (info1 == null)
			info1 = "";
		this.info1 = info1.substring(0, 200);
	}

	public String getInfo2() {
		return this.info2;
	}

	public void setInfo2(String info2) {
		if (info2 == null)
			info2 = "";
		this.info2 = info2.substring(0, 200);
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}

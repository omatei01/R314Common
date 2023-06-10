/**
 * MfaDataMapper.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.db.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import ro.anzisoft.common.db.domain.MfaDataDb;

/**
 * <b>MfaDataMapper</b><br>
 * 
 * @author omatei01 (2018)
 */
public class MfaDataMapper implements RowMapper<MfaDataDb> {
	@Override
	public MfaDataDb map(ResultSet rs, StatementContext ctx) throws SQLException {
		MfaDataDb rec = new MfaDataDb();

		rec.setCode(rs.getString("code"));
		rec.setDescr(rs.getString("descr"));
		rec.setType(rs.getString("type"));
		rec.setTax(rs.getString("tax"));
		rec.setValText(rs.getString("valText"));
		rec.setValNum(rs.getLong("valNum"));
		rec.setFlash(rs.getBoolean("flash"));
		return rec;
	}
}

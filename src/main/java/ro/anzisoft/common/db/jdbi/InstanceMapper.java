/**
 * 
 */
package ro.anzisoft.common.db.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import ro.anzisoft.common.db.domain.InstanceDb;

/**
 * <b>InstanceMapper</b><br>
 * @author omatei01 (2018)
 */
public class InstanceMapper implements RowMapper<InstanceDb> {
	@Override
	public InstanceDb map(ResultSet r, StatementContext ctx) throws SQLException {
		return new InstanceDb(
		        r.getString("instanceUUID"),
		        r.getString("company"),
		        r.getString("store"),
		        r.getString("pos"),
		        r.getString("serialMfa"),
		        r.getString("secretKey"),
		        r.getString("dateInstall"),
		        r.getString("userInstall"),
		        r.getInt("status"));
	}
}

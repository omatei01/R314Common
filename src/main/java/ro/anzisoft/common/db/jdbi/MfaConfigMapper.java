/**
 * MfaMemConfig.java
 * 
 * 
 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.db.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import ro.anzisoft.common.db.domain.MfaConfigDb;

/**
 * <b>MfaMemConfig</b><br>
 * 
 * @author omatei01 (2018)
 */
public class MfaConfigMapper implements RowMapper<MfaConfigDb>{
    @Override
    public MfaConfigDb map(ResultSet rs, StatementContext ctx) throws SQLException{
        MfaConfigDb rec = new MfaConfigDb();

        rec.setCode(rs.getString("code"));// mmcName;// STRING (20)
        rec.setDescr(rs.getString("descr"));// String descr;// STRING
        rec.setOpt(rs.getString("opt"));// String mmcOpt;// STRING
        rec.setVal(rs.getString("val"));// String val;// STRING (50)
        rec.setFlash(rs.getBoolean("flash"));//      BOOLEAN
        rec.setDefa(rs.getString("defa"));// STRING/

        return rec;
    }
}

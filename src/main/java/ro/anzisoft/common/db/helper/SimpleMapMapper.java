/**
 * SimpleMapMapper.java
 * 

 * @author omatei01 (2017)
 * 
 */
package ro.anzisoft.common.db.helper;

/**
 * <b>SimpleMapMapper</b>
 * <p>
 *  mapper for regular SqlQuery in the same manner like handle.select that is returning List of Map String, Object
 *  similar cu DefaultMapMapper
 * @author omatei01 (15 dec. 2017)
 *
 * @version 1.0
 */
public class SimpleMapMapper {}/*implements ResultSetMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {
            map.put(r.getMetaData().getColumnLabel(i), r.getObject(i));
        }
        return map;
    }
}*/
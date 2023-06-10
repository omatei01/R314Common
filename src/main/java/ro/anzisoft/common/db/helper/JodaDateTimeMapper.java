package ro.anzisoft.common.db.helper;

public class JodaDateTimeMapper {
}
/*implements ResultColumnMapper <DateTime> {

	@Override
	public DateTime mapColumn(ResultSet rs, int columnNumber, StatementContext ctx) throws SQLException {
		final Timestamp timestamp = rs.getTimestamp(columnNumber, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		if (timestamp == null) { return null; }
		return new DateTime(timestamp.getTime());
	}

	@Override
	public DateTime mapColumn(ResultSet rs, String columnLabel, StatementContext ctx) throws SQLException {
		final Timestamp timestamp = rs.getTimestamp(columnLabel, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		if (timestamp == null) { return null; }
		return new DateTime(timestamp.getTime());
	}
}
*/
package ro.anzisoft.common.db.helper;

/**
 * DBI argument factory for converting joda DateTime to sql timestamp
 * 
 * @author omatei01
 *
 */
public class DateTimeArgumentFactory {}
/*implements ArgumentFactory <DateTime> {
	@Override
	public boolean accepts(Class <?> expectedType, Object value, StatementContext ctx) {
		return value != null && DateTime.class.isAssignableFrom(value.getClass());
	}

	@Override
	public Argument build(Class <?> expectedType, final DateTime value, StatementContext ctx) {
		return new Argument() {
			@Override
			public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
				long millis = value.withZone(DateTimeZone.UTC).getMillis();
				statement.setTimestamp(position, new Timestamp(millis), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			}
		};
	}
}
*/
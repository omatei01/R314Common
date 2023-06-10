package ro.anzisoft.common.db.helper;

/**
 * DBI argument factory for converting joda LocalDate to sql string
 */
public class LocalDateArgumentFactory{}
/*implements ArgumentFactory <LocalDate> {
	@Override
	public boolean accepts(Class <?> expectedType, Object value, StatementContext ctx) {
		return value != null && LocalDate.class.isAssignableFrom(value.getClass());
	}

	@Override
	public Argument build(Class <?> expectedType, final LocalDate value, StatementContext ctx) {
		return new Argument() {
			@Override
			public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
				statement.setString(position, value.toString("yyyy-MM-dd"));
			}
		};
	}
}
*/
/**
 * DbBase.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.db.helper;

import java.io.File;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.jodatime2.JodaTimePlugin;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.anzisoft.common.db.DbException;

/**
 * <b>DbBase</b><br>
 * <p>
 * NU tr sa fie singleton; e in common si poate folosita catre diverse fisier db<br>
 * Daca s-ar face pooling pe o sg conexiune at ar fi bine sa fie singleton<br>
 * <p>
 * 
 * @author omatei01 (2018)
 */
public class DbBase {
	private static final Logger LOG = LoggerFactory.getLogger(DbBase.class);
	private DbBase instance;

	private Jdbi jdbi;
	private Handle conn;

	private boolean isLoadedDb = false;

	private String pathToDb;

	public DbBase(String pathToDb) throws DbException {
		this.pathToDb = pathToDb;
		init();
	}

	private void init() throws DbException {
		if (isLoadedDb) return;

		final String sDriverName = "org.sqlite.JDBC";
		try {
			Class.forName(sDriverName);

			final File dbFile = new File(pathToDb);
			if (!dbFile.exists()) {
				//DbUtil.createDbFromTemplate();
				throw new DbException("Lipsește fișierul cu date");
			}

			// setup the connection pool
			/*			final HikariConfig config = new HikariConfig();
						config.setJdbcUrl("jdbc:sqlite:" + LocalPathResolver.getLocalPath(MfaConst.FOLDER_APP_DATA, MfaConst.DB_FILE_APP));
						config.setUsername("");
						config.setPassword("");
						config.setAutoCommit(true);
						config.addDataSourceProperty("characterEncoding", "utf8");
						config.addDataSourceProperty("useUnicode", "true");
						config.setPoolName("DBConnectionPool_R314");
						config.setLeakDetectionThreshold(5000);
						connectionPool = new HikariDataSource(config);*/

			//DataSource ds = new HikariDataSource(config);
			//jdbi = new DBI(connectionPool);
			//mainConnection = jdbi.open();

			jdbi = Jdbi
			        .create("jdbc:sqlite:" + pathToDb)
			        .installPlugin(new SQLitePlugin());
			jdbi.installPlugin(new GuavaPlugin());
			jdbi.installPlugin(new JodaTimePlugin());

			conn = jdbi.open();

			LOG.debug("Database driver loaded and config and pooling set");
			isLoadedDb = true;

		} catch (final ClassNotFoundException ex) {
			LOG.error("DB driver not found: {}", ex.getMessage(), ex);
			throw new DbException("Probleme la acces baza de date", ex);
		}
	}

	public Handle getConnection() throws DbException {
		if (isLoadedDb == false) throw new DbException("Database connection is not ready");
		return conn;
	}

	public Jdbi getDBI() throws DbException {
		if (isLoadedDb == false) throw new DbException("Database connection is not ready");
		return jdbi;
	}

	public void closeConnection() {
		//if (connectionPool != null) connectionPool.close();
		conn.close();
		isLoadedDb = false;
	}

}

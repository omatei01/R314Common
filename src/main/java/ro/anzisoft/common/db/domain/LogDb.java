package ro.anzisoft.common.db.domain;

import java.sql.Timestamp;

import lombok.Data;
import ro.anzisoft.common.util.MTypes;

@Data
public class LogDb {
	private long logid;// INTEGER PRIMARY KEY AUTOINCREMENT,
	private long logIdRef;// INTEGER,
	private long logIdRefD;// INTEGER,
	private Timestamp timestamp = MTypes.getCurrentTimestamp();// DATETIME,
	private String tokenUUID;// STRING (36),
	private String userCode;// STRING (30) REFERENCES user,
	private int zid; // INTEGER,
	private long logTypeId; // INTEGER,
	private boolean canceled; // BOOLEAN,
	private int commandTypeId; // INTEGER,
	private String commandParams; // STRING (250),
	private int commandResponse; // INTEGER,
	private String displayTextLine11; // STRING (50),
	private String displayTextLine12; // STRING (50),
	private String displayTextLine21; // STRING (50),
	private String displayTextLine22; // STRING (50),
	private String displayTextCurrentOp; // STRING (50),
	private String displayTextJe; // STRING (50),
	private String errorMessage; // STRING (250),
	private String supervisor; // STRING (30),
	private boolean refreshUI; // BOOLEAN,
	private byte inputSource; // INTEGER, keyboard,
	private String displayTextForUndoLine; // STRING (50),
	private String displayTextForUndoJE; // STRING (50),
	private boolean executedFromLog; // BOOLEAN DEFAULT (0),
	private long reExecutedLogId; // INTEGER

	/**
	 * CTOR
	 * 
	 * @param logid
	 * @param logIdRef
	 * @param logIdRefD
	 * @param timestamp
	 * @param tokenUUID
	 * @param userCode
	 * @param zid
	 * @param logTypeId
	 * @param canceled
	 * @param commandTypeId
	 * @param commandParams
	 * @param commandResponse
	 * @param displayTextLine11
	 * @param displayTextLine12
	 * @param displayTextLine21
	 * @param displayTextLine22
	 * @param displayTextCurrentOp
	 * @param displayTextJe
	 * @param errorMessage
	 * @param supervisor
	 * @param refreshUI
	 * @param inputSource
	 * @param displayTextForUndoLine
	 * @param displayTextForUndoJE
	 * @param executedFromLog
	 * @param reExecutedLogId
	 * @since 1.0
	 */
	public LogDb(long logid,
	        long logIdRef,
	        long logIdRefD,
	        String timestamp,
	        String tokenUUID,
	        String userCode,
	        int zid,
	        long logTypeId,
	        boolean canceled,
	        int commandTypeId,
	        String commandParams,
	        int commandResponse,
	        String displayTextLine11,
	        String displayTextLine12,
	        String displayTextLine21,
	        String displayTextLine22,
	        String displayTextCurrentOp,
	        String displayTextJe,
	        String errorMessage,
	        String supervisor,
	        boolean refreshUI,
	        byte inputSource,
	        String displayTextForUndoLine,
	        String displayTextForUndoJE,
	        boolean executedFromLog,
	        long reExecutedLogId) {
		super();
		this.logid = logid;
		this.logIdRef = logIdRef;
		this.logIdRefD = logIdRefD;
		this.timestamp = MTypes.toTimestamp(timestamp,MTypes.PATTERN_DATETIME_DB);
		this.tokenUUID = tokenUUID;
		this.userCode = userCode;
		this.zid = zid;
		this.logTypeId = logTypeId;
		this.canceled = canceled;
		this.commandTypeId = commandTypeId;
		this.commandParams = commandParams;
		this.commandResponse = commandResponse;
		this.displayTextLine11 = displayTextLine11;
		this.displayTextLine12 = displayTextLine12;
		this.displayTextLine21 = displayTextLine21;
		this.displayTextLine22 = displayTextLine22;
		this.displayTextCurrentOp = displayTextCurrentOp;
		this.displayTextJe = displayTextJe;
		this.errorMessage = errorMessage;
		this.supervisor = supervisor;
		this.refreshUI = refreshUI;
		this.inputSource = inputSource;
		this.displayTextForUndoLine = displayTextForUndoLine;
		this.displayTextForUndoJE = displayTextForUndoJE;
		this.executedFromLog = executedFromLog;
		this.reExecutedLogId = reExecutedLogId;
	}

}

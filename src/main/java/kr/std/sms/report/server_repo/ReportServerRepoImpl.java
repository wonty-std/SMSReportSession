package kr.std.sms.report.server_repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.session.ReportSession;

@Repository
public class ReportServerRepoImpl implements  ReportServerRepo {

	private static final Logger LOGGER = LogManager.getLogger(ReportServerRepoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportServerRepoImpl(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
    	
    	LOGGER.info("ReportServerRepoImpl()");
    }
   
    public List<ReportServer> GetReportServerList() {
		  String sqlQuery = "SELECT SVR_ID,SERVER_IP,USER_ID,PWD,FILE_PATH,IDC_LOC FROM SUPPORT.RPT_SERVER_LIST";
		  return jdbcTemplate.query(sqlQuery, this::mapRowToReportServer);
	}
    
    public int DelReportServerList(String svr_id) {
		  String sqlQuery = "DELETE FROM SUPPORT.RPT_SERVER_LIST WHERE SVR_ID=?";
          jdbcTemplate.update(sqlQuery, svr_id);
		  return(1);
    }
    
    public int AddReportServerList(String svr_id, String server_ip, String  idc_loc, String user_id, String pwd, String file_path) {
		  String sqlQuery = "INSERT INTO SUPPORT.RPT_SERVER_LIST "
		  		+ "(SVR_ID, SERVER_IP, IDC_LOC, USER_ID, PWD, FILE_PATH) "
		  		+ "VALUES "
		  		+ "(?, ?, ?, ?, ?, ?)";
          jdbcTemplate.update(sqlQuery, svr_id, server_ip, idc_loc, user_id, pwd, file_path);
		  return(1);
    }
    
    public int UpdateReportServerList(String svr_id, String server_ip, String  idc_loc, String user_id, String pwd, String file_path) {
		  String sqlQuery = "UPDATE SUPPORT.RPT_SERVER_LIST "
		  		+ "SET "
		  		+ "SERVER_IP=?, IDC_LOC=?, USER_ID=?, PWD=?, FILE_PATH=? "
		  		+ "WHERE "
		  		+ "SVR_ID=?";
          jdbcTemplate.update(sqlQuery, server_ip, idc_loc, user_id, pwd, file_path, svr_id);
		  return(1);
    }
	
    private ReportServer mapRowToReportServer(ResultSet resultSet, int rowNum) throws SQLException {
	    // This method is an implementation of the functional interface RowMapper.
	    // It is used to map each row of a ResultSet to an object.
	    return ReportServer.builder()
	            .Svr_Id(resultSet.getString("Svr_Id"))
	            .Server_Ip(resultSet.getString("Server_Ip"))
	            .User_Id(resultSet.getString("User_Id"))
	            .Pwd(resultSet.getString("Pwd"))
	            .File_Path(resultSet.getString("File_Path"))
	            .Idc_Loc(resultSet.getString("Idc_Loc"))
	            .build();
	}
    
    public void SaveReportSessionData(ReportSession RptSession)
    {
        String sqlQuery = "MERGE INTO SUPPORT.RPT_SESSION_INFO rpt "
        		+ "USING dual ON (rpt.GRP_CD=? AND rpt.AUTH_SEQ=?) "
        		+ "WHEN MATCHED THEN "
        		+ "UPDATE SET rpt.SVR_ID=?, rpt.CHECK_DTTM=?, rpt.IDC_LOC=? "
        		+ "WHEN NOT MATCHED THEN "
        		+ "INSERT (SVR_ID,CHECK_DTTM,GRP_CD,AUTH_SEQ,IDC_LOC) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery, RptSession.getGrp_Cd(), RptSession.getAuth_Seq(), RptSession.getSvr_Id(), RptSession.getCheck_Dttm(), RptSession.getIdc_Loc(), RptSession.getSvr_Id(), RptSession.getCheck_Dttm(), RptSession.getGrp_Cd(), RptSession.getAuth_Seq(), RptSession.getIdc_Loc());
    }
    
    public List<ReportSession> GetReportSessionList() {
		  String sqlQuery = "SELECT GRP_CD,AUTH_SEQ,SVR_ID,IDC_LOC,CHECK_DTTM FROM SUPPORT.RPT_SESSION_INFO";
		  return jdbcTemplate.query(sqlQuery, this::mapRowToReportSession);
	}
    
    public List<ReportSession> GetReportSessionList(String grp_cd) {
		  String sqlQuery = "SELECT GRP_CD,AUTH_SEQ,SVR_ID,IDC_LOC,CHECK_DTTM FROM SUPPORT.RPT_SESSION_INFO WHERE GRP_CD='"+grp_cd+"'";
		  return jdbcTemplate.query(sqlQuery, this::mapRowToReportSession);
	}
    
    public List<ReportSession> GetReportSessionList(String grp_cd, String auth_seq) {
		  String sqlQuery = "SELECT GRP_CD,AUTH_SEQ,SVR_ID,IDC_LOC,CHECK_DTTM FROM SUPPORT.RPT_SESSION_INFO WHERE GRP_CD='"+grp_cd+"' AND AUTH_SEQ='"+auth_seq+"'";
		  return jdbcTemplate.query(sqlQuery, this::mapRowToReportSession);
	}
    
    private ReportSession mapRowToReportSession(ResultSet resultSet, int rowNum) throws SQLException {
	    // This method is an implementation of the functional interface RowMapper.
	    // It is used to map each row of a ResultSet to an object.
	    return ReportSession.builder()
	            .Grp_Cd(resultSet.getString("Grp_Cd"))
	            .Auth_Seq(resultSet.getString("Auth_Seq"))
	            .Svr_Id(resultSet.getString("Svr_Id"))
	            .Idc_Loc(resultSet.getString("Idc_Loc"))
	            .Check_Dttm(resultSet.getString("Check_Dttm"))
	            .build();
	}
}




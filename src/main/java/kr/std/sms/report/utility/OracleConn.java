package kr.std.sms.report.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleConn {

	public Connection conn = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;
	public int cnt = 0;
	
	public OracleConn() {
	}

	public void connect(String db_url, String db_id, String db_pw) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(db_url, db_id, db_pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
	public int insert(String svr_id, String grp_cd, String auth_seq, String idc_loc, String check_dttm) {
		
		String sql = "insert into support.RPT_SESSION_INFO (SVR_ID,GRP_CD,AUTH_SEQ,IDC_LOC,CHECK_DTTM) values (?,?,?,?,?)";
		try{
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, svr_id);
			psmt.setString(2, grp_cd);
			psmt.setString(3, auth_seq);
			psmt.setString(4, idc_loc);
			psmt.setString(5, check_dttm);
			cnt = psmt.executeUpdate();
            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	close();
        }
   		return cnt;
    }
}

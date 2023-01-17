package kr.std.sms.report.server_repo;

import java.util.List;

import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.session.ReportSession;

public interface ReportServerRepo {
	// report server 
	public List<ReportServer> GetReportServerList();
	public int DelReportServerList(String svr_id);
	public int AddReportServerList(String svr_id, String server_ip, String  idc_loc, String user_id, String pwd, String file_path);
	public int UpdateReportServerList(String svr_id, String server_ip, String  idc_loc, String user_id, String pwd, String file_path);
	public void SaveReportSessionData(ReportSession RptSession);
	// report session
	public List<ReportSession> GetReportSessionList();
	public List<ReportSession> GetReportSessionList(String grp_cd);
	public List<ReportSession> GetReportSessionList(String grp_cd, String auth_seq);
}

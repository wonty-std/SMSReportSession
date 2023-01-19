package kr.std.sms.report.server_svc;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.std.sms.report.schedule.ReportFileCollect;
import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.server_repo.ReportServerRepo;
import kr.std.sms.report.session.ReportSession;

@Service
public class ReportServerService {
	private static final Logger LOGGER = LogManager.getLogger(ReportServerService.class);
	private final ReportServerRepo RepSvrRepo;

	@Autowired
	public ReportServerService(ReportServerRepo RepSvrRepo) {
	    this.RepSvrRepo = RepSvrRepo;
	}

    public List<ReportServer> getlist(){
        return RepSvrRepo.GetReportServerList();
    }
    
    public int dellist(String svr_id){
        return RepSvrRepo.DelReportServerList(svr_id);
    }
    
    public int addlist(String svr_id, String server_ip, String  idc_loc, String user_id, String pwd, String file_path) {
    	return RepSvrRepo.AddReportServerList(svr_id, server_ip, idc_loc, user_id, pwd, file_path);
    }
    
    public int updatelist(String svr_id, String server_ip, String  idc_loc, String user_id, String pwd, String file_path) {
    	return RepSvrRepo.UpdateReportServerList(svr_id, server_ip, idc_loc, user_id, pwd, file_path);
    }
    
    public List<ReportSession> getsessionlist(){
        return RepSvrRepo.GetReportSessionList();
    }
    
    public List<ReportSession> getsessionlist(String grp_cd){
        return RepSvrRepo.GetReportSessionList(grp_cd);
    }
    
    public List<ReportSession> getsessionlist(String grp_cd, String auth_seq){
        return RepSvrRepo.GetReportSessionList(grp_cd, auth_seq);
    }
    
    public int loaddata(List<ReportServer> list) throws Exception {

    	int thd_num = list.size();
	    LOGGER.info("server_list.size : {}", thd_num);
	    if(thd_num > 0)
	    {
	    	for(int i = 0; i < thd_num; i++) {
		    	ReportFileCollect arr_collector = new ReportFileCollect(RepSvrRepo, i, list.get(i));
				arr_collector.start();
	    	}
	    }
	    
        return(1);
    }    
}

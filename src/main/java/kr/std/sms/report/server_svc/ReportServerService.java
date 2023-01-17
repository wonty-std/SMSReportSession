package kr.std.sms.report.server_svc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.std.sms.report.alvfile.ReportFileCollect;
import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.server_repo.ReportServerRepo;
import kr.std.sms.report.session.ReportSession;
import kr.std.sms.report.utility.FTPUtil;

@Service
public class ReportServerService {
	private static final Logger LOGGER = LogManager.getLogger(ReportFileCollect.class);
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
    
    public int loaddata(List<ReportServer> list) throws Exception {
    	
    	for(ReportServer svr: list)
    	{
    		String host = svr.getServer_Ip();
    		String uid = svr.getUser_Id();
    		String pwd = svr.getPwd();
    		
	    	FTPUtil sftp_session = new FTPUtil();
	    	try {
	    		sftp_session.connect(uid, host, pwd);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		throw new Exception("JSchException");
	    	}

	    	String file_path = svr.getFile_Path();
	    	sftp_session.download(file_path, "ALIVE_LIST.txt", "./ALIVE_LIST.txt");
	    	sftp_session.disconnect();
	    
	    	// parse file.
	    	File file = new File("./ALIVE_LIST.txt");
	
	    	if(file.exists())
	    	{
	    	    BufferedReader inFile = new BufferedReader(new FileReader(file));
	    	    String sLine = null;
	    	    while( (sLine = inFile.readLine()) != null )
	    	    {
	    	    	LOGGER.info(sLine);

	    	    	String[] data = sLine.split(" ");
	    	    	
	    		    ReportSession rpt_session = ReportSession.builder()
	    		            .Grp_Cd("")
	    		            .Auth_Seq("")
	    		            .Svr_Id("")
	    		            .Idc_Loc("")
	    		            .Check_Dttm("")
	    		            .build();
	    	    	
	    	    	rpt_session.setSvr_Id(data[2]);
	    	    	rpt_session.setCheck_Dttm(data[5]);
	    	    	rpt_session.setGrp_Cd(data[0]);
	    	    	rpt_session.setAuth_Seq(data[1]);
	    	    	rpt_session.setIdc_Loc(data[4]);
	    	    	
	    	    	RepSvrRepo.SaveReportSessionData(rpt_session);
	    	    }
	    	    inFile.close();
	    	}
    	}
    	
        return(1);
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
}


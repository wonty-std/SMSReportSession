package kr.std.sms.report.schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.server_repo.ReportServerRepo;
import kr.std.sms.report.session.ReportSession;
import kr.std.sms.report.utility.FTPUtil;

public class ReportFileCollect extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(ReportFileCollect.class);
	private ReportServerRepo RepSvrRepo;
	private int pno = 0;
	private ReportServer rep_svr;

	public ReportFileCollect(ReportServerRepo RepSvrRepo, int pno, ReportServer rep_svr) {
	    this.RepSvrRepo = RepSvrRepo;
		this.pno = pno;
		this.rep_svr = rep_svr;
		LOGGER.info("ReportFileCollect(pno={} pid={} started!", pno, this.getId());
	}

	@Override
	public void run() {
		
		LOGGER.info("ReportFileCollect(pno={},pid={} run()!", pno, this.getId());
		
		String host = rep_svr.getServer_Ip();
		String uid = rep_svr.getUser_Id();
		String pwd = rep_svr.getPwd();
		
		FTPUtil sftp_session = new FTPUtil();
		try {
			sftp_session.connect(uid, host, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String file_path = rep_svr.getFile_Path();
		String alive_file = "./ALIVE_LIST_" +  Integer.toString(pno) + ".txt";
		sftp_session.download(file_path, "ALIVE_LIST.txt", alive_file);
		sftp_session.disconnect();

		// parse file.
		File file = new File(alive_file);

		if(file.exists())
		{
		    BufferedReader inFile = null;
			try {
				inFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String sLine = null;
		    while( true )
		    {
		    	try {
					if((sLine = inFile.readLine()) == null)
						break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	LOGGER.info("pno:{} {}",  pno, sLine);

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
		    	
				try {
					TimeUnit.MICROSECONDS.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    try {
				inFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
   }	
}




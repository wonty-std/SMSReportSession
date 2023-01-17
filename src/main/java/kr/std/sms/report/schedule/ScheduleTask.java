package kr.std.sms.report.schedule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// import com.jcraft.jsch.JSchException;

import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.server_repo.ReportServerRepoImpl;
import kr.std.sms.report.server_svc.ReportServerService;
import kr.std.sms.report.utility.SMSUtility;

@Component
public class ScheduleTask {
	private static final Logger LOGGER = LogManager.getLogger(ReportServerRepoImpl.class);
	private String start_min = "99";
	
	@Autowired
    ReportServerService server_svc;
	
    @Scheduled(cron = "0/1 * * * * ?")
    public void cronRun() {
		try {
			LOGGER.info("cronRun()");
			
			String str_dttm = SMSUtility.getCurrentDTTM();
			String str_min = str_dttm.substring(10, 12);
			if(start_min.equals(str_min) == false)
			{
				String str_sec = str_dttm.substring(12, 14);
				if(str_sec.equals("59") == true)
				{
					TimeUnit.MILLISECONDS.sleep(1000);
					start_min = str_min;
					
			        List<ReportServer> server_list = server_svc.getlist();
			        
			        server_svc.loaddata(server_list);
				}
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }	
}

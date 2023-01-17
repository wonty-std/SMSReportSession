package kr.std.sms.report.alvfile;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportFileCollect extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(ReportFileCollect.class);

	@PostConstruct
	public void init() {
		start();
		LOGGER.info("ReportFileCollect started!");
	}
	
	@Override
	public void run() {

		while(true) {
			try {
				TimeUnit.MICROSECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
   }	
}


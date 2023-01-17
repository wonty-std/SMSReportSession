package kr.std.sms.report.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSUtility {
	// 현재 14자리 시간 문자열을 리턴.
	static public String getCurrentDTTM() {
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat time = new SimpleDateFormat("hhmmss");
		
		Date today = new Date();
		String str_dttm = date.format(today) + time.format(today);
		
		return(str_dttm);
	}
}

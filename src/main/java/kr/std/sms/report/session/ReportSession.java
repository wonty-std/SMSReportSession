package kr.std.sms.report.session;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportSession {
	String Svr_Id;
	String Session_Ip;
	String Check_Dttm;
	String Grp_Cd;
	String Auth_Seq;
	String Idc_Loc;
}

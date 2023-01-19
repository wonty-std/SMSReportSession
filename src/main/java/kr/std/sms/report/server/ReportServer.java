package kr.std.sms.report.server;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportServer {
	String Svr_Id;
	String Server_Ip;
	String User_Id;
	String Pwd;
	String File_Path;
	String Idc_Loc;
}

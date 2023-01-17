package kr.std.sms.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import kr.std.sms.report.server.ReportServer;
import kr.std.sms.report.server_svc.ReportServerService;
import kr.std.sms.report.session.ReportSession;

@RestController
public class ReportController {

    private final ReportServerService report_svc;

    @Autowired
    public ReportController(ReportServerService report_svc) {
        this.report_svc = report_svc;
    }
    
    JsonObject MakeReportResultJSON(int result_no, int err_code, String err_str) {
    	JsonObject jobj = new JsonObject();
    	
    	jobj.addProperty("result", Integer.toString(result_no));
    	jobj.addProperty("error_cd", Integer.toString(err_code));
    	jobj.addProperty("err_str", err_str);
    	
    	return(jobj);
    }
    
    @GetMapping("/report_server/list")
    public String report_server_list() {
    	
    	List<ReportServer> list = report_svc.getlist();
    	JsonObject rep_result = MakeReportResultJSON(list.size(), 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[[";
    	str_json += gson.toJson(rep_result);
    	str_json += "],[";
    	str_json += gson.toJson(list);
    	str_json += "]]";
    			
        return(str_json);
    }
    
    @GetMapping("/report_server/delete/{svr_id}")
    public String report_server_delete(@PathVariable("svr_id") String svr_id) {

    	int ret = report_svc.dellist(svr_id);
    	
    	JsonObject rep_result = MakeReportResultJSON(ret, 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[";
    	str_json += gson.toJson(rep_result);
    	str_json += "]";
    	
        return(str_json);
    }    
    
    @GetMapping("/report_server/insert")
    public String report_server_insert(@RequestParam String svr_id, @RequestParam String server_ip, @RequestParam String idc_loc, @RequestParam String user_id, @RequestParam String pwd, @RequestParam String file_path) {

    	int ret = report_svc.addlist(svr_id, server_ip, idc_loc, user_id, pwd, file_path);
    	
    	JsonObject rep_result = MakeReportResultJSON(ret, 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[";
    	str_json += gson.toJson(rep_result);
    	str_json += "]";
    	
        return(str_json);
    }   
    
    @GetMapping("/report_server/update")
    public String report_server_update(@RequestParam String svr_id, @RequestParam String server_ip, @RequestParam String idc_loc, @RequestParam String user_id, @RequestParam String pwd, @RequestParam String file_path) {

    	int ret = report_svc.updatelist(svr_id, server_ip, idc_loc, user_id, pwd, file_path);
    	
    	JsonObject rep_result = MakeReportResultJSON(ret, 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[";
    	str_json += gson.toJson(rep_result);
    	str_json += "]";
    	
        return(str_json);
    }
    
    @GetMapping("/report_session/list")
    public String report_session_list() {
    	
    	List<ReportSession> list = report_svc.getsessionlist();
    	JsonObject rep_result = MakeReportResultJSON(list.size(), 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[[";
    	str_json += gson.toJson(rep_result);
    	str_json += "],[";
    	str_json += gson.toJson(list);
    	str_json += "]]";
    			
        return(str_json);
    }
    
    @GetMapping("/report_session/{grp_cd}")
    public String report_session_grp_cd(@PathVariable("grp_cd") String grp_cd) {
    	List<ReportSession> list = report_svc.getsessionlist(grp_cd);
    	JsonObject rep_result = MakeReportResultJSON(list.size(), 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[[";
    	str_json += gson.toJson(rep_result);
    	str_json += "],[";
    	str_json += gson.toJson(list);
    	str_json += "]]";
    			
        return(str_json);
    }
    
    @GetMapping("/report_session/{grp_cd}/{auth_seq}")
    public String report_session_grp_cd(@PathVariable("grp_cd") String grp_cd, @PathVariable("auth_seq") String auth_seq) {
    	List<ReportSession> list = report_svc.getsessionlist(grp_cd, auth_seq);
    	JsonObject rep_result = MakeReportResultJSON(list.size(), 0, "");

    	Gson gson = new Gson();
    	
    	String str_json = "[[";
    	str_json += gson.toJson(rep_result);
    	str_json += "],[";
    	str_json += gson.toJson(list);
    	str_json += "]]";
    			
        return(str_json);
    }
}


package kr.std.sms.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

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
    
    @RequestMapping(value="/report_server/list", method=RequestMethod.GET, produces="application/json;charset=utf-8")     
    public String report_server_list() {
    	
    	List<ReportServer> list = report_svc.getlist();
    	
    	Map<String,Object> result = new HashMap<>();
    	
    	result.put("result", Integer.toString(list.size()));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", list);
    	
    	Map<String, Object> sorted_result = new TreeMap<>(result);

    	Gson gson = new Gson();
    	
        return(gson.toJson(sorted_result));
    }
    
    @RequestMapping(value="/report_server/delete/{svr_id}", method=RequestMethod.GET, produces="application/json;charset=utf-8")     
    public String report_server_delete(@PathVariable("svr_id") String svr_id) {

    	int ret = report_svc.dellist(svr_id);
    	
    	Map<String,Object> result = new HashMap<>();
    	result.put("result", Integer.toString(ret));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", null);
    	Map<String, Object> sorted_result = new TreeMap<>(result);
    	
    	Gson gson = new Gson();
    	    	
    	return(gson.toJson(sorted_result));
    }    
    
    @RequestMapping(value="/report_server/insert", method=RequestMethod.GET, produces="application/json;charset=utf-8")
    public String report_server_insert(@RequestParam String svr_id, @RequestParam String server_ip, @RequestParam String idc_loc, @RequestParam String user_id, @RequestParam String pwd, @RequestParam String file_path) {

    	int ret = report_svc.addlist(svr_id, server_ip, idc_loc, user_id, pwd, file_path);
    	
    	Map<String,Object> result = new HashMap<>();
    	result.put("result", Integer.toString(ret));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", null);
    	Map<String, Object> sorted_result = new TreeMap<>(result);
    	
    	Gson gson = new Gson();
    	
    	return(gson.toJson(sorted_result));
    }   
    
    @RequestMapping(value="/report_server/update", method=RequestMethod.GET, produces="application/json;charset=utf-8")
    public String report_server_update(@RequestParam String svr_id, @RequestParam String server_ip, @RequestParam String idc_loc, @RequestParam String user_id, @RequestParam String pwd, @RequestParam String file_path) {

    	int ret = report_svc.updatelist(svr_id, server_ip, idc_loc, user_id, pwd, file_path);
    	
    	Map<String,Object> result = new HashMap<>();
    	result.put("result", Integer.toString(ret));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", null);
    	Map<String, Object> sorted_result = new TreeMap<>(result);

    	Gson gson = new Gson();
    	
    	return(gson.toJson(sorted_result));
    }

    @RequestMapping(value="/report_session/list", method=RequestMethod.GET, produces="application/json;charset=utf-8")
    public String report_session_list() {
    	
    	List<ReportSession> list = report_svc.getsessionlist();
    	
    	Map<String,Object> result = new HashMap<>();
    	result.put("result", Integer.toString(list.size()));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", list);
    	Map<String, Object> sorted_result = new TreeMap<>(result);

    	Gson gson = new Gson();
    	
    	return(gson.toJson(sorted_result));
    }
    
    @RequestMapping(value="/report_session/{grp_cd}", method=RequestMethod.GET, produces="application/json;charset=utf-8")
    public String report_session_grp_cd(@PathVariable("grp_cd") String grp_cd) {
    	
    	List<ReportSession> list = report_svc.getsessionlist(grp_cd);

    	Map<String,Object> result = new HashMap<>();
    	result.put("result", Integer.toString(list.size()));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", list);
    	Map<String, Object> sorted_result = new TreeMap<>(result);
    	
    	Gson gson = new Gson();
    	
    	return(gson.toJson(sorted_result));
    }
    
    @RequestMapping(value="/report_session/{grp_cd}/{auth_seq}", method=RequestMethod.GET, produces="application/json;charset=utf-8")
    public String report_session_grp_cd(@PathVariable("grp_cd") String grp_cd, @PathVariable("auth_seq") String auth_seq) {
    	
    	List<ReportSession> list = report_svc.getsessionlist(grp_cd, auth_seq);
    	
    	Map<String,Object> result = new HashMap<>();
    	result.put("result", Integer.toString(list.size()));
    	result.put("error_cd", "0");
    	result.put("err_str", "OK");
    	result.put("values", list);
    	Map<String, Object> sorted_result = new TreeMap<>(result);
    	
    	Gson gson = new Gson();
    	
    	return(gson.toJson(sorted_result));
    }
}


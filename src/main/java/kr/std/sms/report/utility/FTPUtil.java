package kr.std.sms.report.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FTPUtil {

	private static final Logger LOGGER = LogManager.getLogger(FTPUtil.class);

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

   // SFTP 서버연결
    public void connect(String user, String url, String password) throws Exception{

    	LOGGER.info(url);
        
        //JSch 객체 생성
        JSch jsch = new JSch();
        try {
            //세션객체 생성 ( user , host, port )
        	LOGGER.info("session");
            session = jsch.getSession(user, url);
            //password 설정
            session.setPassword(password);
            //세션관련 설정정보 설정
            java.util.Properties config = new java.util.Properties();
            //호스트 정보 검사하지 않는다.
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            //접속
            session.connect();
            //sftp 채널 접속
            channel = session.openChannel("sftp");
            channel.connect();

        } catch (JSchException e) {
        	LOGGER.error("connect JSchException error : " +e.toString());
            throw new Exception("JSchException");
        } catch (Exception e) {
        	LOGGER.error("connect error : " +e.toString());
            throw new Exception(e.toString());
		}
        channelSftp = (ChannelSftp) channel;
    }

    // 단일 파일 업로드
    public void upload( String dir , File file){
        FileInputStream in = null;
        try{ //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
            in = new FileInputStream(file);
            channelSftp.cd(dir);
            channelSftp.put(in,file.getName());
        }catch(SftpException se){
            se.printStackTrace();
        }catch(FileNotFoundException fe){
            fe.printStackTrace();
        }finally{
            try{
                in.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }


    public ChannelSftp moveDirectory(String dir){
    	 try{ //경로탐색후 inputStream에 데이터를 넣음
             channelSftp.cd(dir); // 결로 이동
         }catch(SftpException se){
             se.printStackTrace();
         }
		return channelSftp;
    }

    /**
     *
     * @param dir  서버의 저장되어 있는 경로
     * @param fileNm 다운받으려는 파일명
     * @param saveDir 현 서버의 다운받으려고 하는 경로
     */
    public void download(String dir, String fileNm, String saveDir){
        InputStream in = null;
        FileOutputStream out = null;
        //String path = "...";
        try{ //경로탐색후 inputStream에 데이터를 넣음
            channelSftp.cd(dir); // 결로 이동
            in = channelSftp.get(fileNm);

        }catch(SftpException se){
        	LOGGER.error("download " +se.toString());
        }
        try {
            out = new FileOutputStream(new File(saveDir));
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }

        } catch (IOException e) {
        	LOGGER.error("download io error : " +e.toString());
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
            	LOGGER.error("download close error : " +e.toString());
            }
        }

    }

    /**
     * 인자로 받은 경로의 파일 리스트를 리턴한다.
     * @param path
     * @return
     */
    @SuppressWarnings("unchecked")
    public Vector<ChannelSftp.LsEntry> getFileList(String path, String lsCmd) {

    	Vector<ChannelSftp.LsEntry> list = null;
    	try {
    		channelSftp.cd(path);
    		list = channelSftp.ls(lsCmd);
		} catch (SftpException e) {
			e.printStackTrace();
			return null;
		}
    	return list;
    }

    // 파일서버와 세션 종료
    public void disconnect(){
        channelSftp.quit();
        session.disconnect();
    }	
}

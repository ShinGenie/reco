package com.example.demo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class StartSftp {
	private Session session;
	private Channel channel;
	private ChannelSftp channelSftp;
	private BufferedInputStream bis;
	
	// 시스템 정보
	private SystemAccount account;
	private JSch jsch;
	
	public StartSftp(SystemAccount account) {
		jsch = new JSch();
		this.account = account;
	}
	
	// 파일 전송
	public void fileSender(Path srcFileName, Path tarFileName, String cdCommand) throws Exception {
//		String srcPath = URLEncoder.encode(srcFile.toString(), "EUC-KR");
//		String tarPath = URLEncoder.encode(tarFile.toString(), "EUC-KR");
		
		bis = new BufferedInputStream(new FileInputStream(srcFileName.toFile()));
		
		if (getChannelSftp() != null) {
			// 리눅스 경로중에 /<--- 겹치는거 정규화 시킴 
			log.error("====================== CD : " + cdCommand.toString());
			getChannelSftp().cd(cdCommand);
			
			// 출력 향상 스트림으로 버퍼 더줌
			log.error("====================== Sending....");
			getChannelSftp().put(bis, tarFileName.toString());
			bis.close();
			
			log.error("====================== Sending File.... Finished");
		} else { // 만들어진 커넥션이 존재 하지 않으면...
			bis.close();
			log.error("sftp 커넥션 없어요...");
		}
	}
	
	// 커넥션 얻어오기
	public boolean openSftp() throws JSchException {
		log.error("====================== OpenSftp");
		log.error(account.getUserName() + " " + account.getHost() + " " + account.getPort());
		
		session = jsch.getSession( // 세션 얻어오기
				account.getUserName(), 
				account.getHost(), 
				account.getPort());
		
		session.setPassword(getAccount().getPassword()); // 패스워드 셋팅
		
		// SET RSA PROP
		Properties prop = new Properties();
		prop.put("StrictHostKeyChecking", "no");
		session.setConfig(prop);
		session.connect(3 * 1000); // 3초
		
		// 채널 접속
		channel = session.openChannel(ShellCode.SFTP.getCmd());
		channel.connect(ShellCode.TIME_WAIT_5.getIdx());
		channelSftp = (ChannelSftp) channel;
		
		return true;
	}
	
	// 커넥션 끊기
	public boolean closeSftp() throws Exception {
		// 소켓 반환
		if (channel != null) {
			channel.disconnect();
			return true;
		}
		
		return false;
	}
}
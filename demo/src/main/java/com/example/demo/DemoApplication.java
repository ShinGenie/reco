package com.example.demo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jcraft.jsch.JSchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DemoApplication {

	static String HOST = "106.240.247.44";
	static String USER_ACCOUNT = "con_ftp";
	static String PASS_WORD = "reco4123!";
	static int PORT = 22;
	
//	public static void upload() throws JSchException {
//		
//		JSch jsch = new JSch();
//		ChannelSftp channelSftp = null;
//		// InputStream in = null;
//		Channel channel = null;
//
//		String host = "106.240.247.44";
//		String userName = "con_ftp";
//		int port = 22;
//		String password = "reco4123!";
//
//		try {
//			Session session = jsch.getSession(userName, host, port);
//			session.setPassword(password);
//
//			java.util.Properties config = new java.util.Properties();
//			config.put("StrictHostKeyChecking", "no");
//			// config.put("PreferredAuthentications", "password");
//			session.setConfig(config);
//			session.connect(60000);
//
//			channel = session.openChannel("sftp"); // 방식설정
//			channel.connect(60000);
//			channelSftp = (ChannelSftp) channel;
//			System.out.println("session" + session);
//
//			String filePath = "C:\\gradu.jpg";
//			String dir = "/home/con_ftp/genie";
//			boolean result = true;
//			FileInputStream in = null;
//			try {
//				File file = new File(filePath);
//				String fileName = file.getName();
//				fileName = URLEncoder.encode(fileName, "EUC-KR");
//
//				in = new FileInputStream(file);
//				
//				channelSftp.cd(dir);
//				
//				channelSftp.put(in, fileName);
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				result = false;
//			} finally {
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			System.out.println("result " + result);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		channel.disconnect();
//	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
		
		String filePath = "C:\\gradu.jpg";
		String dir = "/home/con_ftp/genie";
		
		Path src = Paths.get("C:\\gradu.jpg").normalize().toAbsolutePath();
		Path cd = Paths.get("/home/con_ftp/genie");
		
		StartSftp sftp = new StartSftp(SystemAccount.builder()
												    .host(HOST)
												    .userName(USER_ACCOUNT)
												    .password(PASS_WORD)
												    .port(PORT)
												    .build());
		
		if (sftp.openSftp()) {
			log.error("====================== Welcome OpenSftp");
			sftp.fileSender(src, src.getFileName(), dir);
		} else {
			System.out.println("커넥션 안됨");
		}
	}
}

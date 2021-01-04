package com.example.demo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SystemAccount {

	private String host;
	private String userName;
	private String password;
	private int port;
	
	@Builder
	public SystemAccount(String host, String userName, String password, int port) {
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}
}

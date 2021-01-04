package com.example.demo;

public enum ShellCode {

	SFTP(0, "sftp"),
	
	TIME_WAIT_5(5000, "5000ms");
	
	private int idx;
	private String cmd;
	
	private ShellCode(int idx, String cmd) {
		this.idx = idx;
		this.cmd = cmd;
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public int getIdx() {
		return idx;
	}
}

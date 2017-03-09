package cn.com.sandi.hawkeye.minaclient.object;


import java.io.File;

import org.apache.log4j.Logger;

import cn.com.sandi.hawkeye.minaclient.util.Constant;


public class RecInfo {
		
    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(RecInfo.class);

	
	private long serialNum = 0;
	private String eventStr = "";
	private String serverSerialNum = "";
	private String localAddr = "";
	private String fileName = "";
	private String remoteAddr = "";	
		
	//解析String为RecInfo对象
	public RecInfo(Object message){
		
		String recMsg = message.toString();  
		String []msgSplit = recMsg.split(Constant.one + "|" + Constant.three + "|" + Constant.four);
		
		this.serialNum = Integer.parseInt(msgSplit[0]);
		this.eventStr = msgSplit[1];

		if(eventStr.trim().equalsIgnoreCase(Constant.RECORD)){
			
			this.serverSerialNum = msgSplit[2];
			
			/**
			 * 需检查系统路径分隔符
			 * 获取本地文件目录，文件名，远程文件目录
			 */
			String mSeparator = File.separatorChar + "";
			String localaddrTemp = "";
		
			if(mSeparator.equalsIgnoreCase("\\")){
				localaddrTemp = msgSplit[3].replaceAll("/", "\\\\");
			}else{
				localaddrTemp = msgSplit[3].replaceAll("\\\\","/");
			}			
			
			this.localAddr = localaddrTemp + mSeparator;
//			this.localAddr = msgSplit[3] + "/";
			
			String fileNameTemp = msgSplit[4];
			boolean isWav = msgSplit[4].endsWith("wav");
			if(!isWav){
				fileNameTemp = msgSplit[4].replaceAll(msgSplit[4].substring(msgSplit[4].lastIndexOf("."), msgSplit[4].length()), ".wav");
			}			
			this.fileName = fileNameTemp;			
			
			
			this.remoteAddr = "/" + msgSplit[5] + "/";					
			logger.info("serialnum: " + serialNum + " localAddr:" +localAddr + " file:"+ fileName + " remoteAddr:"+ remoteAddr);	
		}
				
	}
	
	
	public String getServerSerialNum() {
		return this.serverSerialNum;
	}

	public String getEventStr(){
		return eventStr;
	}
	
	public String getLocalAddr() {
		return localAddr;
	}

	public String getFileName() {
		return fileName;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

}

package com.object;


import java.io.File;

<<<<<<< HEAD
import org.apache.log4j.Logger;

import com.util.Constant;



public class RecInfo {
		
    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(RecInfo.class);
=======
import com.util.Constant;
import com.util.Log;

public class RecInfo {
		
	private final String TAG = RecInfo.this.getClass().getSimpleName();
>>>>>>> origin/master

	
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
<<<<<<< HEAD
=======
		//Constant.serialNum = this.serialNum;
>>>>>>> origin/master
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
			this.localAddr = Constant.headAddr + localaddrTemp + mSeparator;
			
<<<<<<< HEAD
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
=======
			this.fileName = msgSplit[4];
			
			
			this.remoteAddr = "/" + msgSplit[5] + "/";					
			Log.d(TAG,"serialnum: " + serialNum + " localAddr:" +localAddr + " file:"+ fileName + " remoteAddr:"+ remoteAddr);	
		}
		
		
//		char[] serialNum = new char[10];
//		recMsg.getChars(0, 10, serialNum, 0);
//		this.serialNum = Integer.parseInt(String.valueOf(serialNum)); 
//		Constant.seriaNum = this.serialNum + 1;	
//		
//		Pattern pattern = Pattern.compile("(\\D+)3(\\S*)");
//		Matcher matcher = pattern.matcher(recMsg);
//		StringBuffer buffer = new StringBuffer();
//		
//		if(matcher.find()){ 
//			buffer.append(matcher.group(1)); 
//			String eventStr = matcher.group(1);			
//			
//			if(eventStr.trim().equalsIgnoreCase(Constant.RECORD)){
//				
//				String parmAll = matcher.group(2);
//				//System.out.println(parmAll); 
//				String[] parmList = parmAll.split("4");
//				
//				//获取本地文件目录，文件名，远程文件目录
//				this.path = parmList[0];
//				this.fileName = parmList[1];
//				this.remoteAddr = parmList[2];				
//				//System.out.println("path: " + path + " file: "+ fileName + " remoteAddr: "+ remoteAddr);				
//				Log.d(TAG,"path: " + path + " file: "+ fileName + " remoteAddr: "+ remoteAddr);
//				
//
//			}else{
//				System.out.println("client receives error format");
//			}
//		}else{
//			System.out.println("match null"); 
//		}
	}
	
	
	public long getSerialNum() {
		return serialNum;
>>>>>>> origin/master
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

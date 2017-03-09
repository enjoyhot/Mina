package cn.com.sandi.hawkeye.minaclient.object;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import cn.com.sandi.hawkeye.minaclient.util.Constant;

public class SendInfo {
	
    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(SendInfo.class); 
	
	private long serialNum;
	private String eventStr = "";
	private String msg0 = "";
	
	
	//构造ClientInfo对象
	public SendInfo(long serialNum, String event, String reqSerialNum, String msg1){
		this.serialNum = serialNum;
		this.eventStr = event;
		this.msg0 = reqSerialNum + Constant.four + msg1;
	}
	
	//构造ClientInfo对象
	public SendInfo(long serialNum, String event, String msg0){
		if(serialNum > Constant.maxSerialNum){
			this.serialNum = 1;
			Constant.serialNum = 1;
		}else{
			this.serialNum = serialNum;
		}
		this.eventStr = event;
		this.msg0 = msg0;
	}
		
	//应用于发送信息到server
	public String getSendInfo(){
		
		DecimalFormat df=new DecimalFormat("0000000000");
	    String num=df.format(this.serialNum);	    
		String msg = num + Constant.one + eventStr + Constant.three + msg0;		
		logger.info(msg);		
		return msg;
	}
	
}


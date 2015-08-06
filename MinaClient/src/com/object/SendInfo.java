package com.object;

import java.text.DecimalFormat;
<<<<<<< HEAD

import org.apache.log4j.Logger;

import com.util.Constant;


public class SendInfo {
	
    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(SendInfo.class); 
	
=======
import com.util.Constant;
import com.util.Log;
public class SendInfo {
	
	private final String TAG = "SendInfo";
>>>>>>> origin/master
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
<<<<<<< HEAD
		logger.info(msg);		
=======
		Log.d(TAG,msg);
>>>>>>> origin/master
		return msg;
	}
	
}
<<<<<<< HEAD

=======
>>>>>>> origin/master

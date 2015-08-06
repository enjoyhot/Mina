package com.clientHandler;

import java.io.File;
import java.io.FileInputStream;
<<<<<<< HEAD
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.main.MinaClient;
import com.object.FtpClientUtil;
import com.object.RecInfo;
import com.object.SendInfo;
import com.util.Constant;
import com.util.ConvertTools;


/**
 * é»˜è®¤çŸ­è¿žæŽ¥ï¼Œé•¿è¿žæŽ¥é˜»å¡žå¿…é¡»å®šä¹‰ä¸€ä¸ªthread poolå¦åˆ™æ— æ³•å¤„ç†æ›´å¤šçš„è¿žæŽ¥æ•°
 * @author Gogary @Email gugugujiawei@gmail.com
 * @time 2015-7-19 ä¸‹åˆ10:13:47
=======
import java.text.DecimalFormat;
import java.util.HashMap;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.object.RecInfo;
import com.object.SendInfo;
import com.object.FtpClientUtil;
import com.util.Constant;
import com.util.Log;

/**
 * Ä¬ÈÏ¶ÌÁ¬½Ó£¬³¤Á¬½Ó×èÈû±ØÐë¶¨ÒåÒ»¸öthread pool·ñÔòÎÞ·¨´¦Àí¸ü¶àµÄÁ¬½ÓÊý
 * @author Gogary @Email gugugujiawei@gmail.com
 * @time 2015-7-19 ÏÂÎç10:13:47
>>>>>>> origin/master
 */

public class TimeClientHandler extends IoHandlerAdapter{ 
	
<<<<<<< HEAD
	private static Logger logger = Logger.getLogger(TimeClientHandler.class);
=======
	private final String TAG = "TimeClientHandler";
>>>>>>> origin/master
	
	@Override 
	public void messageReceived(IoSession session, Object message) throws Exception { 	
		
<<<<<<< HEAD
		logger.debug(message.toString());
		logger.info(message.toString());
		
		//è§£æžæœåŠ¡å™¨å‘æ¥çš„æ¶ˆæ¯
		RecInfo recObj = new RecInfo(message);		

		if(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD)){
			FtpClientUtil ftputil = new FtpClientUtil(false);
			//æ³¨å†Œå›žè°ƒå‡½æ•°
			Callback mCallback = new ClientCallback(session,recObj.getServerSerialNum());
			ftputil.registCallback(mCallback);
			
	    	
	    	ConvertTools.recSrcDir = recObj.getLocalAddr();
	    	
	    	String fileName = ConvertTools.wavToMp3(recObj.getFileName());
	    	//è®¾ç½®é”™è¯¯
	    	if(fileName.charAt(0) == '&'){
	    		return;
	    	}
	    	while(fileName.charAt(0) == '*'){
	    		fileName = ConvertTools.wavToMp3(recObj.getFileName());
	    	}
				
			
			/**
			 * è®¡ç®—æ–‡ä»¶å¤§å°å¹¶è¿”å›ž
			 */
	    	
			File f = new File(ConvertTools.recTrgDir + fileName);
			while(!f.exists()){
				logger.info("wait for one sec");
				Thread.sleep(1000);
			}
			
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(f);
			}catch(Exception e){
				logger.info("FileInputStream error");
				e.printStackTrace();
			}			
			int byteSize = fis.available();
			//DecimalFormat df=new DecimalFormat("0000000000");
		    //String num=df.format(recObj.getSerialNum());	
			String num = recObj.getServerSerialNum();
		    logger.info("æ–‡ä»¶å¤§å°ï¼š" + byteSize);
			SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_OK,num,byteSize+"");
			String sendMsg = sendByteSize.getSendInfo();		
			session.write(sendMsg);
			fis.close();
			/**
			 * å°†ä¼ é€ä»»åŠ¡æ’å…¥åˆ°é˜Ÿåˆ—ä¸­
			 */
			HashMap<String,String> queElement = new HashMap<String,String>();
			queElement.put("1",ConvertTools.recTrgDir);			
			queElement.put("2",fileName);		
			queElement.put("3",recObj.getRemoteAddr());
			logger.info("setQueElement");
			ftputil.setQueElement(queElement);
			
		}else if(recObj.getEventStr().equalsIgnoreCase(Constant.REC_ALIVE)){
			Constant.reqCount = 0;
=======
		Log.d(TAG,message.toString());		
		
		//½âÎö·þÎñÆ÷·¢À´µÄÏûÏ¢
		RecInfo recObj = new RecInfo(message);		
		
		//FtpClientUtil ftputil = new FtpClientUtil("172.16.23.28", 9703, "admin", "admin", false);
		//ftputil.setFileSave("F:\\fileRes\\", "minaTest.txt", "/2015/temp/");
		
		//FtpClientUtil ftputil = FtpClientUtil.getInstance();
		//Log.d(TAG,(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD))+"");
		if(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD)){
			FtpClientUtil ftputil = new FtpClientUtil(false);
			//×¢²á»Øµ÷º¯Êý
			Callback mCallback = new ClientCallback(session,recObj.getSerialNum());
			ftputil.registCallback(mCallback);
			
			
			HashMap<String,String> queElement = new HashMap<String,String>();
			queElement.put("1",recObj.getLocalAddr());
			queElement.put("2",recObj.getFileName());
			queElement.put("3",recObj.getRemoteAddr());
			ftputil.setQueElement(queElement);
	
			
			/**
			 * ¼ÆËãÎÄ¼þ´óÐ¡²¢·µ»Ø
			 */
			File f = new File(recObj.getLocalAddr() + recObj.getFileName());
			FileInputStream fis = new FileInputStream(f);
			int byteSize = fis.available();
			
			DecimalFormat df=new DecimalFormat("0000000000");
		    String num=df.format(recObj.getSerialNum());		
			SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_OK,num,byteSize+"");
			String sendMsg = sendByteSize.getSendInfo();		
			session.write(sendMsg);
>>>>>>> origin/master
		}

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
<<<<<<< HEAD
		// è®¾ç½®IoSessionä¸€å®šé—´éš”æ—¶é—´å‘é€ç‰¹å®šå‘½ä»¤ï¼Œå‚æ•°å•ä½æ˜¯ç§’
=======
		// ÉèÖÃIoSessionÒ»¶¨¼ä¸ôÊ±¼ä·¢ËÍÌØ¶¨ÃüÁî£¬²ÎÊýµ¥Î»ÊÇÃë
>>>>>>> origin/master
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 9);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
<<<<<<< HEAD
		// å®šæ—¶å‘é€ALIVEä¿¡å·
		
		if (status == IdleStatus.BOTH_IDLE){
			Constant.reqCount++;
			//å¦‚æžœå›žåº”æ¬¡æ•°ä¸€ç›´æ²¡è¢«æ¸…é›¶ï¼Œé‡æ–°å¼€å¯è¿žæŽ¥
			// æ¬¡æ²¡å›žåº”é‡æ–°è¿žæŽ¥
			if(Constant.reqCount > Constant.mReconnect){		
				Constant.reqCount = 0;
				// sessionå…³äº†è™½è¯´ä¹‹åŽresetä½†æ˜¯è¿˜æ˜¯ä¼šä¿ƒå‘adapteré‡è¿ž
				session.close();				
				MinaClient.connector = null;			
				MinaClient.reset();
				return;
			}
			
=======
		// ¶¨Ê±·¢ËÍALIVEÐÅºÅ
		if (status == IdleStatus.BOTH_IDLE){
>>>>>>> origin/master
			SendInfo obj1 = new SendInfo(++Constant.serialNum,Constant.ALIVE,"");
			String sendMsg = obj1.getSendInfo();		
			session.write(sendMsg);
		}
	}	
		
}

class ClientCallback implements Callback{
<<<<<<< HEAD
	
	private static Logger logger = Logger.getLogger(ClientCallback.class);
	private IoSession session = null;
	private String serverSerialNum = ""; 
	
	public ClientCallback(IoSession session,String serverSerialNum){
		this.session = session;
		this.serverSerialNum = serverSerialNum;
=======
	private IoSession session = null;
	private long serialNum = 0; 
	
	public ClientCallback(IoSession session,long serialNum){
		this.session = session;
		this.serialNum = serialNum;
>>>>>>> origin/master
	}
	@Override
	public void callback(boolean result) {
		// TODO Auto-generated method stub		
		int resultNum = (result == true ? 0:-1);
<<<<<<< HEAD
		logger.info("resultNum:" + "\""+resultNum+"\"");	
		//DecimalFormat df=new DecimalFormat("0000000000");
	    //String num=df.format(serialNum);	    
		SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_RET,serverSerialNum,resultNum+"");
		String sendMsg = sendByteSize.getSendInfo();		
		session.write(sendMsg);
=======
		Log.d("resultNum","\""+resultNum+"\"");
		DecimalFormat df=new DecimalFormat("0000000000");
	    String num=df.format(serialNum);	    
		SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_RET,num,resultNum+"");
		String sendMsg = sendByteSize.getSendInfo();		
		session.write(sendMsg);
		//´«ÊäÍêÎÄ¼þºóftp·þÎñÆ÷¹Ø±ÕftpÁ¬½Ó
		//FtpClientUtil.getInstance().close();
>>>>>>> origin/master
	}
}


<<<<<<< HEAD

=======
>>>>>>> origin/master

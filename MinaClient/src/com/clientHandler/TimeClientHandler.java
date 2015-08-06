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
 * 默认短连接，长连接阻塞必须定义一个thread pool否则无法处理更多的连接数
 * @author Gogary @Email gugugujiawei@gmail.com
 * @time 2015-7-19 下午10:13:47
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
 * Ĭ�϶����ӣ��������������붨��һ��thread pool�����޷���������������
 * @author Gogary @Email gugugujiawei@gmail.com
 * @time 2015-7-19 ����10:13:47
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
		
		//解析服务器发来的消息
		RecInfo recObj = new RecInfo(message);		

		if(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD)){
			FtpClientUtil ftputil = new FtpClientUtil(false);
			//注册回调函数
			Callback mCallback = new ClientCallback(session,recObj.getServerSerialNum());
			ftputil.registCallback(mCallback);
			
	    	
	    	ConvertTools.recSrcDir = recObj.getLocalAddr();
	    	
	    	String fileName = ConvertTools.wavToMp3(recObj.getFileName());
	    	//设置错误
	    	if(fileName.charAt(0) == '&'){
	    		return;
	    	}
	    	while(fileName.charAt(0) == '*'){
	    		fileName = ConvertTools.wavToMp3(recObj.getFileName());
	    	}
				
			
			/**
			 * 计算文件大小并返回
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
		    logger.info("文件大小：" + byteSize);
			SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_OK,num,byteSize+"");
			String sendMsg = sendByteSize.getSendInfo();		
			session.write(sendMsg);
			fis.close();
			/**
			 * 将传送任务插入到队列中
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
		
		//������������������Ϣ
		RecInfo recObj = new RecInfo(message);		
		
		//FtpClientUtil ftputil = new FtpClientUtil("172.16.23.28", 9703, "admin", "admin", false);
		//ftputil.setFileSave("F:\\fileRes\\", "minaTest.txt", "/2015/temp/");
		
		//FtpClientUtil ftputil = FtpClientUtil.getInstance();
		//Log.d(TAG,(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD))+"");
		if(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD)){
			FtpClientUtil ftputil = new FtpClientUtil(false);
			//ע��ص�����
			Callback mCallback = new ClientCallback(session,recObj.getSerialNum());
			ftputil.registCallback(mCallback);
			
			
			HashMap<String,String> queElement = new HashMap<String,String>();
			queElement.put("1",recObj.getLocalAddr());
			queElement.put("2",recObj.getFileName());
			queElement.put("3",recObj.getRemoteAddr());
			ftputil.setQueElement(queElement);
	
			
			/**
			 * �����ļ���С������
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
		// 设置IoSession一定间隔时间发送特定命令，参数单位是秒
=======
		// ����IoSessionһ�����ʱ�䷢���ض����������λ����
>>>>>>> origin/master
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 9);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
<<<<<<< HEAD
		// 定时发送ALIVE信号
		
		if (status == IdleStatus.BOTH_IDLE){
			Constant.reqCount++;
			//如果回应次数一直没被清零，重新开启连接
			// 次没回应重新连接
			if(Constant.reqCount > Constant.mReconnect){		
				Constant.reqCount = 0;
				// session关了虽说之后reset但是还是会促发adapter重连
				session.close();				
				MinaClient.connector = null;			
				MinaClient.reset();
				return;
			}
			
=======
		// ��ʱ����ALIVE�ź�
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
		//�������ļ���ftp�������ر�ftp����
		//FtpClientUtil.getInstance().close();
>>>>>>> origin/master
	}
}


<<<<<<< HEAD

=======
>>>>>>> origin/master

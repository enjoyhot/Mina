package cn.com.sandi.hawkeye.minaclient.clientHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import cn.com.sandi.hawkeye.interphase.Manager;
import cn.com.sandi.hawkeye.minaclient.MinaClient;
import cn.com.sandi.hawkeye.minaclient.object.FtpClientUtil;
import cn.com.sandi.hawkeye.minaclient.object.RecInfo;
import cn.com.sandi.hawkeye.minaclient.object.SendInfo;
import cn.com.sandi.hawkeye.minaclient.util.Constant;
import cn.com.sandi.hawkeye.minaclient.util.ConvertTools;


/**
 * 默认短连接，长连接阻塞必须定义一个thread pool否则无法处理更多的连接数
 * @author Gogary @Email gugugujiawei@gmail.com
 * @time 2015-7-19 下午10:13:47
 */

public class TimeClientHandler extends IoHandlerAdapter{ 
	
	private static Logger logger = Logger.getLogger(TimeClientHandler.class);
	
	@Override 
	public void messageReceived(IoSession session, Object message) throws Exception { 	
		
		logger.debug(message.toString());
		logger.info(message.toString());
		
		//解析服务器发来的消息
		RecInfo recObj = new RecInfo(message);		

		if(recObj.getEventStr().equalsIgnoreCase(Constant.RECORD)){			
				    		   
	    	
	    	String fileName = ConvertTools.wavToMp3(recObj.getLocalAddr(),recObj.getFileName());
	    	String []msgSplit = fileName.split(Constant.one);
	    	
	    	//设置错误或转换失败
	    	if(msgSplit[0].equalsIgnoreCase("*")){
	    		Manager.printMessage((msgSplit[1]));
	    		return;
	    	}
	    	//1115973 237789	    	
	    	fileName = msgSplit[0];			
			/**
			 * 计算文件大小并返回
			 */
	    	
			File f = new File(ConvertTools.recTrgDir + msgSplit[1] + fileName);
			while(!f.exists()){
				logger.debug("wait for one sec");
				Thread.sleep(1000);
			}
			Manager.printMessage("转换录音成功！");
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(f);
			}catch(Exception e){
				logger.info("FileInputStream error");
				e.printStackTrace();
			}	
			
			// 等待直到转换成功,即字节数不变
			int byteSize = fis.available();
			int temp = -1;
			while(temp != byteSize){				
				byteSize = fis.available();
				Thread.sleep(100);
				temp = fis.available();
			}
			
		    logger.info("文件大小：" + byteSize);
		    
		    String num = recObj.getServerSerialNum();
			SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_OK,num,byteSize+"");
			String sendMsg = sendByteSize.getSendInfo();		
			session.write(sendMsg);
			fis.close();
			/**
			 * 将传送任务插入到队列中
			 */
			FtpClientUtil ftputil = new FtpClientUtil(false);
			//注册回调函数
			Callback mCallback = new ClientCallback(session,recObj.getServerSerialNum(),fileName);
			ftputil.registCallback(mCallback);
			HashMap<String,String> queElement = new HashMap<String,String>();
			queElement.put("1",ConvertTools.recTrgDir + msgSplit[1]);			
			queElement.put("2",fileName);				
			queElement.put("3",recObj.getRemoteAddr());
			logger.info("setQueElement");
			ftputil.setQueElement(queElement);
			
		}else if(recObj.getEventStr().equalsIgnoreCase(Constant.REC_ALIVE)){
			Constant.reqCount = 0;
		}

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
		// 设置IoSession一定间隔时间发送特定命令，参数单位是秒
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 9);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
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
			
			SendInfo obj1 = new SendInfo(++Constant.serialNum,Constant.ALIVE,"");
			String sendMsg = obj1.getSendInfo();		
			session.write(sendMsg);
		}
	}	
		
}

class ClientCallback implements Callback{
	
	private static Logger logger = Logger.getLogger(ClientCallback.class);
	private IoSession session = null;
	private String serverSerialNum = ""; 
	private String recTrgFile = "";
	
	public ClientCallback(IoSession session,String serverSerialNum, String recTrgFile){
		this.session = session;
		this.serverSerialNum = serverSerialNum;
		this.recTrgFile = recTrgFile;
	}
	@Override
	public void callback(boolean result) {
		// TODO Auto-generated method stub	
		String successStr = "上传成功！" ;
		if(!result)
			successStr = "上传失败！"; 
		Manager.printMessage("录音" + this.recTrgFile + successStr);
		int resultNum = (result == true ? 0:-1);
		logger.info("resultNum:" + "\""+resultNum+"\"");	
		//DecimalFormat df=new DecimalFormat("0000000000");
	    //String num=df.format(serialNum);	    
		SendInfo sendByteSize = new SendInfo(++Constant.serialNum,Constant.RE_RET,serverSerialNum,resultNum+"");
		String sendMsg = sendByteSize.getSendInfo();		
		session.write(sendMsg);
	}
}




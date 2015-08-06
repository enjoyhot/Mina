package com.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream; 
import java.net.InetSocketAddress;
import java.util.Properties;

<<<<<<< HEAD
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
=======
>>>>>>> origin/master
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
<<<<<<< HEAD
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
=======
>>>>>>> origin/master
import com.clientHandler.TimeClientHandler;
import com.object.SendInfo;
import com.util.CharsetCodecFactory;
import com.util.Constant;
<<<<<<< HEAD
import com.util.ConvertTools;


public class MinaClient {	

    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(MinaClient.class);
    
	public static ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath*:conf/applicationContext.xml");
	
	public static NioSocketConnector connector;
	
    public MinaClient() {
    	logger.info("MinaClient init()");
=======
import com.util.Log;


public class MinaClient {

	public static String TAG = "MinaClient";
	
	public static NioSocketConnector connector;
	
    //public static IoSession ioSession;
    
    public MinaClient() {
>>>>>>> origin/master
        init();
    }
    
    public static void initConfig(){
<<<<<<< HEAD
    	//日志管理
    	PropertyConfigurator.configure("conf/log4j.properties");
    	//其他配置
=======
    	
>>>>>>> origin/master
    	Properties properties = new Properties();
    	String filePath = System.getProperty("user.dir") + "/conf/init.properties";        	 
    	try {
    		InputStream in = new BufferedInputStream(new FileInputStream(filePath)); 
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Constant.UID = (String)properties.getProperty("UID");
    	
    	Constant.serverName = (String) properties.get("serverName");
    	Constant.serverPort = Integer.parseInt((String)properties.get("serverPort"));
<<<<<<< HEAD
    	Constant.mReconnect = Integer.parseInt((String)properties.get("mReconnect"));
=======
>>>>>>> origin/master
    	
    	Constant.ftpHostName = (String)properties.getProperty("ftpHostName");
    	Constant.ftpPort = Integer.parseInt((String)properties.getProperty("ftpPort"));
    	Constant.userName = (String)properties.getProperty("userName");
    	Constant.password = (String)properties.getProperty("password");
    	
<<<<<<< HEAD
    	//Constant.threadChannel = Integer.parseInt((String)properties.getProperty("threadChannel"));
    	Constant.headAddr = (String)properties.getProperty("headAddr");
    	
    	//wav2mp3 properties
    	ConvertTools.recTrgDir = (String) properties.get("recTrgDir");
    	ConvertTools.ffmpegPath = "ffmpeg/ffmpeg.exe";
    	
=======
    	Constant.threadChannel = Integer.parseInt((String)properties.getProperty("threadChannel"));
    	Constant.headAddr = (String)properties.getProperty("headAddr");
    	
>>>>>>> origin/master
    }
    
    public void init() {
		
		
		//创建客户端连接器. 
		//AbstractIoService\AbstractIoConnector
		connector = new NioSocketConnector(); 
<<<<<<< HEAD
=======
		
		//connector.getFilterChain().addLast( "logger", new LoggingFilter() ); 
>>>>>>> origin/master

		/**
		 * 使用字符串编码
		 * ProtocolCodecFilter(new TextLineCodecFactory())
		 * 这个过滤器的作用是将收到的信息转换成一行行的文本后传递给 IoHandler，因此我们可以在 messageReceived 中直接将 msg 对象强制转换成 String 对象。
		 */
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory())); //设置编码过滤器 
		connector.setConnectTimeoutMillis(30000); //设置连接超时 
		connector.setHandler(new TimeClientHandler());//设置事件处理器 
<<<<<<< HEAD
						
		//断线重连回调拦截器  		
=======
		
		//断线重连回调拦截器  
>>>>>>> origin/master
        connector.getFilterChain().addFirst("reconnection", new IoFilterAdapter(){  
        	
            @Override  
            public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {  
<<<<<<< HEAD
                for(;!Constant.reset;){  
                    try{                     	
                        Thread.sleep(3000);
                        logger.info("重新连接 " + Constant.serverName);
                        ConnectFuture future = connector.connect(new InetSocketAddress(Constant.serverName, Constant.serverPort));  
                        future.awaitUninterruptibly();// 等待连接创建成功  
                        ioSession = future.getSession();// 获取会话                          
                		//发送第一个带userid的消息
                        sendMsgWithID(ioSession);                        
                        if(ioSession.isConnected()){                              
                            break;  
                        }  
                    }catch(Exception ex){  
                        logger.error("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());  
                    }  
                }
                Constant.reset = false;
=======
                for(;;){  
                    try{  
                        Thread.sleep(3000);  
                        ConnectFuture future = connector.connect(new InetSocketAddress(Constant.serverName, Constant.serverPort));  
                        future.awaitUninterruptibly();// 等待连接创建成功  
                        ioSession = future.getSession();// 获取会话  
                        
                		//发送第一个带userid的消息
                        sendMsgWithID(ioSession);
                        
                        if(ioSession.isConnected()){  
                            Log.d("断线重连["+ connector.getDefaultRemoteAddress().getHostName() +":"+ connector.getDefaultRemoteAddress().getPort()+"]成功");  
                            break;  
                        }  
                    }catch(Exception ex){  
                        Log.d("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());  
                    }  
                }  
>>>>>>> origin/master
            }  
        });
		
		ConnectFuture cf = connector.connect( 
		new InetSocketAddress(Constant.serverName, Constant.serverPort));//建立连接 
		cf.awaitUninterruptibly();//等待连接创建完成 		
		//发送第一个带userid的消息
		sendMsgWithID(cf.getSession());

<<<<<<< HEAD
=======
		//cf.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开 
		//connector.dispose(); 
>>>>>>> origin/master
    }
    
	/**
	 * 发送带有设备ID的函数
	 * @param ioSession
	 */
    public void sendMsgWithID(IoSession ioSession){
		SendInfo sendObj = new SendInfo(++Constant.serialNum,Constant.USER_ID,Constant.UID);
		String sendMsgStr = sendObj.getSendInfo();
<<<<<<< HEAD
		ioSession.write(sendMsgStr);		
    }
    
	private static class MinaClientHolder {   
	  static MinaClient instance = new MinaClient();   
	}   
	
	
	public static MinaClient getInstance() {		
	    return MinaClientHolder.instance;   
	}
    
	public static void reset(){		
		Constant.serialNum = 0;	
		Constant.reset = true;
		MinaClientHolder.instance = null;
		MinaClientHolder.instance = new MinaClient();
	}
	
	public static void main(String[] args)  throws Exception { 
		
		initConfig();		
		MinaClient.getInstance();
=======
		ioSession.write(sendMsgStr);
    }
    
	public static void main(String[] args)  throws Exception { 
		
		initConfig();
		MinaClient client = new MinaClient();

>>>>>>> origin/master
	}

}

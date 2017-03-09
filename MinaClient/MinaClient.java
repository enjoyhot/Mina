package cn.com.sandi.hawkeye.minaclient;


import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.com.sandi.hawkeye.minaclient.clientHandler.TimeClientHandler;
import cn.com.sandi.hawkeye.minaclient.object.SendInfo;
import cn.com.sandi.hawkeye.minaclient.util.CharsetCodecFactory;
import cn.com.sandi.hawkeye.minaclient.util.Constant;


public class MinaClient {

    private MinaClient() {
    	logger.info("MinaClient init()");
        init();
    }
	
    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(MinaClient.class);
    
    public static ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath*:conf/applicationContext.xml");
    
	//public static ApplicationContext beanFactory = new FileSystemXmlApplicationContext("applicationContext.xml");
	
	public static NioSocketConnector connector;	    
    
    public void init() {
		
		
		//创建客户端连接器. 
		//AbstractIoService\AbstractIoConnector
		connector = new NioSocketConnector(); 

		/**
		 * 使用字符串编码
		 * 例：ProtocolCodecFilter(new TextLineCodecFactory())
		 * 这个过滤器的作用是将收到的信息转换成一行行的文本后传递给 IoHandler，因此我们可以在 messageReceived 中直接将 msg 对象强制转换成 String 对象。
		 */
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory())); //设置编码过滤器 
		connector.setConnectTimeoutMillis(30000); //设置连接超时 
		connector.setHandler(new TimeClientHandler());//设置事件处理器 
						
		//断线重连回调拦截器  		
        connector.getFilterChain().addFirst("reconnection", new IoFilterAdapter(){  
        	
            @Override  
            public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {  
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
            }  
        });
		
		ConnectFuture cf = connector.connect( 
		new InetSocketAddress(Constant.serverName, Constant.serverPort));//建立连接 
		cf.awaitUninterruptibly();//等待连接创建完成 		
		//发送第一个带userid的消息
		sendMsgWithID(cf.getSession());

    }
    
	/**
	 * 发送带有设备ID的函数
	 * @param ioSession
	 */
    public void sendMsgWithID(IoSession ioSession){
		SendInfo sendObj = new SendInfo(++Constant.serialNum,Constant.USER_ID,Constant.UID);
		String sendMsgStr = sendObj.getSendInfo();
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
	
}

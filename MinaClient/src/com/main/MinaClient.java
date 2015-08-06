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
    	//��־����
    	PropertyConfigurator.configure("conf/log4j.properties");
    	//��������
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
		
		
		//�����ͻ���������. 
		//AbstractIoService\AbstractIoConnector
		connector = new NioSocketConnector(); 
<<<<<<< HEAD
=======
		
		//connector.getFilterChain().addLast( "logger", new LoggingFilter() ); 
>>>>>>> origin/master

		/**
		 * ʹ���ַ�������
		 * ProtocolCodecFilter(new TextLineCodecFactory())
		 * ����������������ǽ��յ�����Ϣת����һ���е��ı��󴫵ݸ� IoHandler��������ǿ����� messageReceived ��ֱ�ӽ� msg ����ǿ��ת���� String ����
		 */
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory())); //���ñ�������� 
		connector.setConnectTimeoutMillis(30000); //�������ӳ�ʱ 
		connector.setHandler(new TimeClientHandler());//�����¼������� 
<<<<<<< HEAD
						
		//���������ص�������  		
=======
		
		//���������ص�������  
>>>>>>> origin/master
        connector.getFilterChain().addFirst("reconnection", new IoFilterAdapter(){  
        	
            @Override  
            public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {  
<<<<<<< HEAD
                for(;!Constant.reset;){  
                    try{                     	
                        Thread.sleep(3000);
                        logger.info("�������� " + Constant.serverName);
                        ConnectFuture future = connector.connect(new InetSocketAddress(Constant.serverName, Constant.serverPort));  
                        future.awaitUninterruptibly();// �ȴ����Ӵ����ɹ�  
                        ioSession = future.getSession();// ��ȡ�Ự                          
                		//���͵�һ����userid����Ϣ
                        sendMsgWithID(ioSession);                        
                        if(ioSession.isConnected()){                              
                            break;  
                        }  
                    }catch(Exception ex){  
                        logger.error("������������¼ʧ��,3��������һ��:" + ex.getMessage());  
                    }  
                }
                Constant.reset = false;
=======
                for(;;){  
                    try{  
                        Thread.sleep(3000);  
                        ConnectFuture future = connector.connect(new InetSocketAddress(Constant.serverName, Constant.serverPort));  
                        future.awaitUninterruptibly();// �ȴ����Ӵ����ɹ�  
                        ioSession = future.getSession();// ��ȡ�Ự  
                        
                		//���͵�һ����userid����Ϣ
                        sendMsgWithID(ioSession);
                        
                        if(ioSession.isConnected()){  
                            Log.d("��������["+ connector.getDefaultRemoteAddress().getHostName() +":"+ connector.getDefaultRemoteAddress().getPort()+"]�ɹ�");  
                            break;  
                        }  
                    }catch(Exception ex){  
                        Log.d("������������¼ʧ��,3��������һ��:" + ex.getMessage());  
                    }  
                }  
>>>>>>> origin/master
            }  
        });
		
		ConnectFuture cf = connector.connect( 
		new InetSocketAddress(Constant.serverName, Constant.serverPort));//�������� 
		cf.awaitUninterruptibly();//�ȴ����Ӵ������ 		
		//���͵�һ����userid����Ϣ
		sendMsgWithID(cf.getSession());

<<<<<<< HEAD
=======
		//cf.getSession().getCloseFuture().awaitUninterruptibly();//�ȴ����ӶϿ� 
		//connector.dispose(); 
>>>>>>> origin/master
    }
    
	/**
	 * ���ʹ����豸ID�ĺ���
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

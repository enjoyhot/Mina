package cn.com.sandi.hawkeye.minaclient.object;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

import cn.com.sandi.hawkeye.interphase.Manager;
import cn.com.sandi.hawkeye.minaclient.MinaClient;
import cn.com.sandi.hawkeye.minaclient.clientHandler.Callback;
import cn.com.sandi.hawkeye.minaclient.util.Constant;


public class FtpClientUtil{

    /** 
     * Logger for this class 
     */  
    private static Logger logger = Logger.getLogger(FtpClientUtil.class); 
	
	 
	public FTPClient ftpClient = null;  
	      
	//ftp服务器地址  
	private String hostName;  
	      
	//ftp服务器默认端口  
	public int port = 21;  
	          
	//登录名  
	private String userName;  
	          
	//登录密码     
	private String password;  
	         
	
	//文件名
	private Callback mCallback = null;
	
	//普通队列，接收来自服务器的传送文件消息
	@SuppressWarnings("rawtypes")
	public static Queue<Map> queue = new LinkedList<Map>();	
	
	//线程队列，每次只能有5个进入，如果BlockQueue没有空间,则调用此方法的线程被阻断
	//public static BlockingQueue<Runnable> threadQueue = new ArrayBlockingQueue<Runnable>(Constant.threadChannel); 
	
	//线程池  
	public static TaskExecutor taskExecutor = (TaskExecutor) MinaClient.beanFactory.getBean("taskExecutor");
	
	//public static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(Constant.threadChannel, Constant.threadChannel, 1, TimeUnit.HOURS, threadQueue, new ThreadPoolExecutor.CallerRunsPolicy());
	
	
	//线程对象Runnable
    Runnable run = new Runnable() {  
    	
        @SuppressWarnings("unchecked")
		@Override  
	    public void run() {  		
        	Map<String,String> queElement;
			synchronized(queue){
				queElement = queue.element();
				queue.remove();
			}
			String localDir = queElement.get("1");
			String fileName = queElement.get("2");
			String remoteDir = queElement.get("3");
			
			if(!isDirExist(remoteDir)){
				try {
					mkDirs(remoteDir);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			BufferedInputStream buffIn = null;  
		    try{  
		    	FtpClientUtil.logger.info("Runable()");		    	

		        buffIn = new BufferedInputStream(new FileInputStream(localDir + fileName));  
		        //设置PASV方式,被动式连接
		        ftpClient.enterLocalPassiveMode();
		        Manager.printMessage("开始上传录音" + localDir + fileName +"到ftp服务器");
		        boolean result = ftpClient.storeFile(remoteDir + fileName, buffIn); 
		        mCallback.callback(result);
		        close();		        
		    }catch(Exception e){  
		        e.printStackTrace();  
		        close();
		    }finally{
		        try{  
		            if(buffIn!=null) 		            	
		                buffIn.close();  
		        }catch(Exception e){  
		        	e.printStackTrace();  
		        	close();
		        }  
		    }		    		    				        		       
       } 
    };
	
	/** 
	 * hostName    主机地址 
	 * port        端口号 
	 * userName    用户名 
	 * password    密码 
	 * @param is_zhTimeZone    是否是中文FTP Server端 
	 * @return 
	 */ 
	
	public FtpClientUtil(boolean is_zhTimeZone){		
		this.hostName = Constant.ftpHostName;  
		this.port = Constant.ftpPort;
	    this.userName = Constant.userName;  
	    this.password = Constant.password;       
	    	          	   
        
	    //this.fixedThreadPool = Executors.newFixedThreadPool(threadNum);
	    
	    this.ftpClient = new FTPClient();  
	    ftpClient.setConnectTimeout(60000);       //连接超时为60秒	
	    
	    //当需要中文编码时
        if(is_zhTimeZone){  
            this.ftpClient.configure(FtpClientUtil.Config());  
            this.ftpClient.setControlEncoding("GBK");  
        }  
               
        this.login();        
        this.setFileType(FTPClient.BINARY_FILE_TYPE);     
        
	}

	
//	private static class FtpClientUtilHolder {   
//        static FtpClientUtil instance = new FtpClientUtil(false);   
//    }   
//  
//  
//    public static FtpClientUtil getInstance() {   
//        return FtpClientUtilHolder.instance;   
//    } 
	 

    /**
     * 注册回调对象到ftpClient中 
     * @param callback
     */
	public void registCallback(Callback callback){
		mCallback = callback;
	}
    
    /** 
     * 登录FTP服务器 
     */  
    private void login(){
    	boolean isLogined = false;
        try{          	
            ftpClient.connect(this.hostName,this.port);
            int i = 0;
            while(!isLogined && i<3){          
            	isLogined = ftpClient.login(this.userName, this.password);
            	i++;
            }
        } catch (FTPConnectionClosedException e){  
            // TODO Auto-generated catch block 
        	logger.error("FTP连接失败");    
            e.printStackTrace();  
        } catch (IOException e){  
            // TODO Auto-generated catch block
        	logger.error("IO错误，FTP服务器连接失败");
            e.printStackTrace();  
        }  
        if(isLogined){
        	logger.info("成功连接并登陆到ftp服务器:" + this.hostName);
        }else{
        	//用户名或密码错误
        	logger.error("3次登录失败");
        }
    }  
    
      
    private static FTPClientConfig Config(){  
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
        conf.setRecentDateFormatStr("MM月dd日 HH:mm");  
        conf.setServerLanguageCode("zh"); 
        return conf;  
    }
    
    /** 
     * 设置传输文件的类型[文本文件或者二进制文件] 	
     * @param fileType--BINARY_FILE_TYPE,ASCII_FILE_TYPE  
     */  
	public void setFileType(int fileType){  
		try{  
	        ftpClient.setFileType(fileType);  
	    }catch(IOException e){  
	        e.printStackTrace();  
        }  
    }  
	
	/**
	 * 设置文件传输源路径、文件名（保持不变）、FTP目标路径,用hashmap包装
	 * @param queElement
	 */
	
	public void setQueElement(HashMap<String,String> queElement){
				
		queue.add(queElement);		
		//每次add后都会进行upload等待
		uploadFile();
	}
	
	      
	/**  
     * 加锁等待，从队列中取对象上传文件到FTP服务器 
	 */  
	public void uploadFile(){
		
		if(!ftpClient.isConnected()){
			login();
		}		
		taskExecutor.execute(run);		
				
	} 
	
	
	/**
	 * 检查目录是否存在
	 * @param dir
	 * @return
	 */
	public boolean isDirExist(String dir) {
		try {
			return ftpClient.changeWorkingDirectory(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			return false;
		}		
	}
	
	/**
	 * 逐级创建目录
	 * @param path
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void mkDirs(String path) throws IllegalStateException,IOException{
		if (null == path) {
			return;
		}

		ftpClient.changeWorkingDirectory("/");// 切换到根目录
		StringTokenizer dirs = new StringTokenizer(path, "/");
		String temp = null;
		while (dirs.hasMoreElements()) {
			temp = dirs.nextElement().toString();
			if (!isDirExist(temp)) {
				ftpClient.makeDirectory(temp);// 创建目录
				ftpClient.changeWorkingDirectory(temp);// 进入创建的目录
			}
		}
	}
	
	/** 
     * 关闭FTP连接 
     */  
    public void close(){  
    	try{      		
	        if(ftpClient!=null){  
	            ftpClient.logout();  
	            ftpClient.disconnect();
	            logger.info("current ftpclient close()");
	        }  
	    }catch (Exception e){  
	        e.printStackTrace();  
	    }  
    } 
}
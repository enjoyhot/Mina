package com.util;

<<<<<<< HEAD

=======
>>>>>>> origin/master
import org.apache.mina.filter.codec.textline.LineDelimiter;

public class Constant {
	
	/**
<<<<<<< HEAD
	 * 避免服务器假连接没发送alive的计数器，用于记录
	 */
	public static int reqCount = 0;
	
	/**
	 * 与reqCount进行比较
	 */
	public static int mReconnect = 10;
	
	/**
	 * 判定是由于重连丢失了session还是单session断了
	 */
	
	public static boolean reset = false;
	/**
=======
>>>>>>> origin/master
	 * Client UID
	 */
	public static String UID = "72025"; 
	
	/**
	 * 流水号
	 * 规则：每次取得流水号需+1
	 */
<<<<<<< HEAD
	
=======
>>>>>>> origin/master
	public static long serialNum = 0;
	public static long maxSerialNum = Long.parseLong("9999999999");
	
	/**
	 * 分割字符
	 */
	public static String one = "" + (char)1;	
	public static String three = "" + (char)3;	
	public static String four = "" + (char)4;	
	
	public static final LineDelimiter SPLIT_END = new LineDelimiter(""+(char)5);
	
	/**
<<<<<<< HEAD
	 * 发送通信事件名称
	 */
	public static String USER_ID = "EVT_ID";
	public static String ALIVE = "EVT_ALIVE";
	public static String RE_OK = "EVT_GET_RECORD_OK";	
	public static String RE_RET = "EVT_GET_RECORD_RET";
	
	/**
	 * 接收通信事件名称
	 */
	public static String RECORD = "FUN_GET_RECORD";
	public static String REC_ALIVE = "FUN_ALIVE";
=======
	 * 通信事件名称
	 */
	public static String USER_ID = "EVT_ID";
	public static String ALIVE = "EVT_ALIVE";
	public static String RE_OK = "EVT_GET_RECORD_OK";
	public static String RECORD = "FUN_GET_RECORD";
	public static String RE_RET = "EVT_GET_RECORD_RET";
>>>>>>> origin/master
	
	/**
	 * MINA server信息
	 */
<<<<<<< HEAD
	//public static String serverName = "127.0.0.1";
	public static String serverName = "172.16.23.28";
=======
	public static String serverName = "127.0.0.1";
	//public static String serverName = "172.16.23.28";
>>>>>>> origin/master
	public static int serverPort = 9702;
	
	/**
	 * ftp服务器信息
	 */
<<<<<<< HEAD
	public static String ftpHostName = "172.16.23.28";
	public static int ftpPort = 9703;
	
//	public static String ftpHostName = "127.0.0.1";
//	public static int ftpPort = 21;
	
	public static String userName = "admin";
	public static String password = "admin";	
=======
//	public static String hostName = "172.16.23.28";
//	public static int port = 9703;
	
	public static String ftpHostName = "127.0.0.1";
	public static int ftpPort = 21;
	
	public static String userName = "admin";
	public static String password = "admin";
	
	public static int threadChannel = 5;
>>>>>>> origin/master
	
	/**
	 * 本地文件路径
	 */
	public static String headAddr = "F:\\fileRes\\";
	
	
}
<<<<<<< HEAD

=======
>>>>>>> origin/master

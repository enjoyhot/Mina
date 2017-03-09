package cn.com.sandi.hawkeye.minaclient.util;


import org.apache.mina.filter.codec.textline.LineDelimiter;

public class Constant {
	
	/**
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
	 * Client UID
	 */
	public static String UID = "72025"; 
	
	/**
	 * 流水号
	 * 规则：每次取得流水号需+1
	 */
	
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
	
	/**
	 * MINA server信息
	 */
	//public static String serverName = "127.0.0.1";
	public static String serverName = "172.16.23.28";
	public static int serverPort = 9702;
	
	/**
	 * ftp服务器信息
	 */
	public static String ftpHostName = "172.16.23.28";
	public static int ftpPort = 9703;
	public static String userName = "admin";
	public static String password = "admin";	
	
	/**
	 * 本地文件路径
	 */
	public static String headAddr[] = null;
	
	/**
	 * 共享目录信息
	 */
	public static String shareUrl[] = null;
	public static String shareDir[] = null;
	public static String shareUserName[] = null;
	public static String sharePassword[] = null;
}


package com.util;

<<<<<<< HEAD

=======
>>>>>>> origin/master
import org.apache.mina.filter.codec.textline.LineDelimiter;

public class Constant {
	
	/**
<<<<<<< HEAD
	 * ���������������û����alive�ļ����������ڼ�¼
	 */
	public static int reqCount = 0;
	
	/**
	 * ��reqCount���бȽ�
	 */
	public static int mReconnect = 10;
	
	/**
	 * �ж�������������ʧ��session���ǵ�session����
	 */
	
	public static boolean reset = false;
	/**
=======
>>>>>>> origin/master
	 * Client UID
	 */
	public static String UID = "72025"; 
	
	/**
	 * ��ˮ��
	 * ����ÿ��ȡ����ˮ����+1
	 */
<<<<<<< HEAD
	
=======
>>>>>>> origin/master
	public static long serialNum = 0;
	public static long maxSerialNum = Long.parseLong("9999999999");
	
	/**
	 * �ָ��ַ�
	 */
	public static String one = "" + (char)1;	
	public static String three = "" + (char)3;	
	public static String four = "" + (char)4;	
	
	public static final LineDelimiter SPLIT_END = new LineDelimiter(""+(char)5);
	
	/**
<<<<<<< HEAD
	 * ����ͨ���¼�����
	 */
	public static String USER_ID = "EVT_ID";
	public static String ALIVE = "EVT_ALIVE";
	public static String RE_OK = "EVT_GET_RECORD_OK";	
	public static String RE_RET = "EVT_GET_RECORD_RET";
	
	/**
	 * ����ͨ���¼�����
	 */
	public static String RECORD = "FUN_GET_RECORD";
	public static String REC_ALIVE = "FUN_ALIVE";
=======
	 * ͨ���¼�����
	 */
	public static String USER_ID = "EVT_ID";
	public static String ALIVE = "EVT_ALIVE";
	public static String RE_OK = "EVT_GET_RECORD_OK";
	public static String RECORD = "FUN_GET_RECORD";
	public static String RE_RET = "EVT_GET_RECORD_RET";
>>>>>>> origin/master
	
	/**
	 * MINA server��Ϣ
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
	 * ftp��������Ϣ
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
	 * �����ļ�·��
	 */
	public static String headAddr = "F:\\fileRes\\";
	
	
}
<<<<<<< HEAD

=======
>>>>>>> origin/master

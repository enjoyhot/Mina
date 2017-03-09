package cn.com.sandi.hawkeye.minaclient.util;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;

import cn.com.sandi.hawkeye.interphase.Manager;



public class ConvertTools {

	private static Logger logger=Logger.getLogger(ConvertTools.class.getName());
	public static String[] diskLetter = new String[]{"W:","X:","Y:","Z:"};
	public static String ffmpegPath = "";	
	public static String recTrgDir = "";
	
	public static String wavToMp3(String recSrcDir, String fileName){
		Manager.printMessage("接收到录音传输请求: " + recSrcDir + fileName);
		if(ffmpegPath==null || "".equals(ffmpegPath)){
			logger.error("转换文件失败，尚未设置ffmpeg工具路径");
			return "*"+Constant.one+"转换文件失败，尚未设置ffmpeg工具路径";
		}
			
		String srcFile = null;
		File file = null;
		for(int i=0; i<Constant.headAddr.length; i++){
			srcFile = Constant.headAddr[i] + recSrcDir + fileName;
			file = new File(srcFile);
			if(!file.exists()){
				file = null;
				srcFile = null;
				continue;
			}else{
				break;
			}	
		}
		if(file==null && srcFile==null){	

			int shareLen = Constant.shareDir.length;
			for(int i=0;i<shareLen;i++){
				if(Constant.shareUrl[0].equals(""))
					break;
				srcFile = conn2Share(Constant.shareUrl[i] + "/" +recSrcDir + fileName,i);
				//文件不存在，后续统一处理
				if(srcFile == null){
					if(i!=shareLen-1)
						continue;
					else{
						logger.info("共享文件也不存在");
						break;
					}
				}
				//挂载错误，直接返回
				String []msgSplit = srcFile.split(Constant.one);
				if(msgSplit[0].equalsIgnoreCase("*")){
					return srcFile;
				}
				//读取正常
				srcFile = srcFile + recSrcDir + fileName;
				break;
			}
		}
		

		
		//File file=new File(srcFile);
	
		if(srcFile == null){
			logger.info(fileName+"文件不存在，无法进行转换");
			return "*"+Constant.one+fileName+"文件不存在，无法进行转换";
		}
		
		//获取文件名，转换成mp3文件
		String fName=fileName.substring(0, fileName.lastIndexOf("."));
		String trgFile=recTrgDir + recSrcDir + fName+".mp3";
		logger.info(trgFile);

	
		File mp3file=new File(trgFile);
		
		if(mp3file.exists()){
			logger.info(trgFile+"已存在,直接传输到ftp服务器");
			return fName+".mp3" + Constant.one + recSrcDir;
		}		
		//创建目录
		mkdir(recTrgDir + recSrcDir);
		
		Manager.printMessage("正在转换录音文件到" + trgFile + "...");
		logger.info("目标文件："+srcFile);
		logger.info("转换文件："+trgFile);
		List<String> command=new ArrayList<String>();
		command.add(ffmpegPath);
		command.add("-y");  
		command.add("-loglevel");
		command.add("quiet");
		command.add("-i");
		command.add(srcFile);
		command.add(trgFile);
		ProcessBuilder builder = new ProcessBuilder(); 
		try {
			builder.command(command);  
			builder.start();				
		} catch (IOException e) {
			logger.error("转换录音文件失败",e);
			return "*"+Constant.one+"转换录音文件失败,请检查配置";
		}
		return fName+".mp3"  + Constant.one + recSrcDir;
	}
	
	public static String conn2Share(String url,int index){
		try {
			logger.info("尝试连接到" + url);
			SmbFile file = new SmbFile(url);
			if(!(file.exists())){
				return null;
			}
			String srcDir = mountZ(index);
			return srcDir;
		} catch (MalformedURLException e) {
			logger.error("连接共享文件库失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "*"+Constant.one+"连接共享文件库失败";
		} catch (SmbException e) {
			// TODO Auto-generated catch block
			logger.error("共享文件库指定文件不存在");
			e.printStackTrace();
			return null;
		} catch(Exception e){
			logger.error("共享文件库指定文件不存在");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 挂载远程共享目录到W盘等
	 * 取消挂载命令为"net use * \/del \/y"
	 * 但这里并不实现，意味着挂载了之后找不到才挂载其它
	 */
	public static String mountZ(int index){
		
		List<String> command=new ArrayList<String>();
		command.add("net");
		command.add("use");  
		command.add(diskLetter[index]);
//		command.add("\\172.16.21.4\d$\agtrec");
//		command.add("sandi");
//		command.add("/user:administrator");
		command.add(Constant.shareDir[index]);
		command.add(Constant.sharePassword[index]);
		command.add("/user:" + Constant.shareUserName[index]);
		ProcessBuilder builder = new ProcessBuilder(); 
		try {
			builder.command(command);  
			builder.start();	
			return diskLetter[index]+"\\";
		} catch (IOException e) {
			logger.error("挂载共享硬盘失败",e);
			return "*"+Constant.one+"挂载共享硬盘失败";
		}
	}
	
	public static void mkdir(String path) {  
        File fd = null;  
        try {  
            fd = new File(path);
            if (!fd.exists()){
            	fd.mkdirs();  
            }
        } catch (Exception e) {
        	logger.error("创建目录失败",e);  
        } finally {  
            fd = null;  
        }  
    }
}



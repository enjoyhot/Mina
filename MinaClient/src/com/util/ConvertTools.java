package com.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class ConvertTools {
	private static Logger logger=Logger.getLogger(ConvertTools.class.getName());
	public static String ffmpegPath = "";
	public static String recSrcDir = "";
	public static String recTrgDir = "";
	
	public static String wavToMp3(String fileName){
		if(ffmpegPath==null || "".equals(ffmpegPath)){
			logger.error("转换文件失败，尚未设置ffmpeg工具路径");
			return "&"+Constant.one+"转换文件失败，尚未设置ffmpeg工具路径";
		}
				
		String srcFile=recSrcDir + fileName;
		File file=new File(srcFile);
	
		if(!file.exists()){
			logger.info(srcFile+"文件不存在，无法进行转换");
			return "&"+Constant.one+srcFile+"文件不存在，无法进行转换";
		}
		
		//获取文件名，转换成mp3文件
		String fName=fileName.substring(0, fileName.lastIndexOf("."));
		String trgFile=recTrgDir + fName+".mp3";

	
		file=new File(trgFile);
		
		if(file.exists()){
			logger.info(trgFile+"已存在,直接传输到ftp服务器");
			return fName+".mp3";
		}		
		
		//创建目录
		mkdir(recTrgDir);
		
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
			return "*"+Constant.one+"转换录音文件失败";
		}
		return fName+".mp3";
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



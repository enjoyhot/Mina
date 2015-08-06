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
			logger.error("ת���ļ�ʧ�ܣ���δ����ffmpeg����·��");
			return "&"+Constant.one+"ת���ļ�ʧ�ܣ���δ����ffmpeg����·��";
		}
				
		String srcFile=recSrcDir + fileName;
		File file=new File(srcFile);
	
		if(!file.exists()){
			logger.info(srcFile+"�ļ������ڣ��޷�����ת��");
			return "&"+Constant.one+srcFile+"�ļ������ڣ��޷�����ת��";
		}
		
		//��ȡ�ļ�����ת����mp3�ļ�
		String fName=fileName.substring(0, fileName.lastIndexOf("."));
		String trgFile=recTrgDir + fName+".mp3";

	
		file=new File(trgFile);
		
		if(file.exists()){
			logger.info(trgFile+"�Ѵ���,ֱ�Ӵ��䵽ftp������");
			return fName+".mp3";
		}		
		
		//����Ŀ¼
		mkdir(recTrgDir);
		
		logger.info("Ŀ���ļ���"+srcFile);
		logger.info("ת���ļ���"+trgFile);
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
			logger.error("ת��¼���ļ�ʧ��",e);
			return "*"+Constant.one+"ת��¼���ļ�ʧ��";
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
        	logger.error("����Ŀ¼ʧ��",e);  
        } finally {  
            fd = null;  
        }  
    }
}



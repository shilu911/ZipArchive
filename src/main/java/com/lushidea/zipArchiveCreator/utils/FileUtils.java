package com.lushidea.zipArchiveCreator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu.shi on 24/05/2017.
 * <p>This is collection of file operations</p>
 */
public class FileUtils {
    /**
     * This method is to get all the files including files in the subdirectories of a folder
     * @param file the folder you want to check
     * @return A list contains all the files
     */
    public static List<String> generateFileList(File file){
        List<String> fileList = new ArrayList<String>();
        if(file.isFile()){
            fileList.add(file.getAbsolutePath());
        }else if(file.isDirectory()){
            String[] files = file.list();
            for(String fileName : files){
                fileList.addAll(generateFileList(new File(file,fileName)));
            }
        }
        return fileList;
    }


    /**
     * This method is to get the size of given file/folder
     * @param file Can be either a file or a folder
     * @return The size of given file/folder in Bytes
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception{
        long result = 0;
        if(!file.exists()){
            return 0;
        }
        if(file.isFile()){
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            result += fis.available();
        }else if(file.isDirectory()){
            File[] fileList = file.listFiles();
            for(int i = 0; i < fileList.length; i++){
                result += getFileSize(fileList[i]);
            }
        }
        return result;
    }

    /**
     * This method is to format the size, make it readable
     * @param size
     * @return based on the value of size, return a string like xxx.xx Bytes/KB/MB/GB
     */
    public static String formatFileSize(long size){
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "Bytes";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * Check if the given path exist or not, if not exist, create a new file.
     * @param path The file you want to check
     * @throws Exception
     */
    public static void creatFileIfNotExist(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
    }
}


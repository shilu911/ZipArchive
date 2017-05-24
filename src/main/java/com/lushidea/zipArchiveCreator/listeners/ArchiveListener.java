package com.lushidea.zipArchiveCreator.listeners;

import com.lushidea.zipArchiveCreator.Main;
import com.lushidea.zipArchiveCreator.utils.FileUtils;
import com.lushidea.zipArchiveCreator.utils.IniUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by lu.shi on 24/05/2017.
 * <p>This class handle the click event of Archive button</p>
 */
public class ArchiveListener implements ActionListener {

    private Main main;
    public ArchiveListener(Main main){
        this.main = main;
    }

    /**
     * This is the handler of click event of Archive button
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //check if use has selected a folder
        if(main.getSelectedFolder() == null){
            //if user hasn't selected a folder, pop up a warning
            JOptionPane.showMessageDialog(main.getFrame(), "Please pick a folder first.");
        }else{
            //start a new thread to create the zip file so that we can update the progress at the mean time.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];

                    try{
                        //get desktop path
                        final String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
                        //get target file name
                        String targetFileName = desktopPath+File.separator+new File(main.getSelectedFolder()).getName()+".zip";

                        FileOutputStream fos = new FileOutputStream(targetFileName);
                        ZipOutputStream zos = new ZipOutputStream(fos);

                        List<String> fileList = FileUtils.generateFileList(new File(main.getSelectedFolder()));
                        String folderName = (new File(main.getSelectedFolder())).getName();
                        int count = 1;
                        //go through all the files and add everyone into the zip file
                        for(String file : fileList){
                            ZipEntry ze = new ZipEntry(file.substring(file.indexOf(folderName)));
                            zos.putNextEntry(ze);

                            FileInputStream fis = new FileInputStream(file);
                            int len;
                            while((len = fis.read(buffer)) > 0){
                                zos.write(buffer, 0, len);
                            }
                            fis.close();
                            //update the text of Progress label
                            main.setProgress("Progress: "+count+"/"+fileList.size());
                            count++;
                        }
                        zos.closeEntry();
                        zos.close();
                        //save the current used folder
                        IniUtils.save(IniUtils.CONFIG_SECTION, IniUtils.FOLDER_KEY, main.getSelectedFolder());
                        JOptionPane.showMessageDialog(main.getFrame(), "Done! "+"You can find the "+targetFileName+" on your desktop.");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

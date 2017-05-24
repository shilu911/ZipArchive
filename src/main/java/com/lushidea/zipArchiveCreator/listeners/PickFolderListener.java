package com.lushidea.zipArchiveCreator.listeners;

import com.lushidea.zipArchiveCreator.Main;
import com.lushidea.zipArchiveCreator.utils.IniUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by lu.shi on 24/05/2017.
 * <p>This class handles the click event of Pick Folder button</p>
 */
public class PickFolderListener implements ActionListener{

    private Main main;
    public PickFolderListener(Main main){
        this.main = main;
    }

    /**
     * This is the handler of the click event of Pick Folder button
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //initilize the file chooser
        JFileChooser chooser = new JFileChooser();
        Object currentPath = null;
        try{
            currentPath = IniUtils.read(IniUtils.CONFIG_SECTION, IniUtils.FOLDER_KEY, String.class);
        }catch(Exception ex){

        }
        if(currentPath == null){
            currentPath = ".";
        }
        System.out.println(currentPath);
        chooser.setCurrentDirectory(new File(currentPath.toString()));
        chooser.setDialogTitle("Please choose a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //disable the "All files" option
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showOpenDialog(((JButton)e.getSource()).getParent()) == JFileChooser.APPROVE_OPTION){
            main.setSelectedFolder(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}

package com.lushidea.zipArchiveCreator;
import com.lushidea.zipArchiveCreator.listeners.ArchiveListener;
import com.lushidea.zipArchiveCreator.listeners.PickFolderListener;
import com.lushidea.zipArchiveCreator.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by lu.shi on 24/05/2017.
 */
public class Main {

    private String selectedFolder = null;
    private JFrame frame;
    private JLabel folderNameLabel;
    private JLabel sizeLabel;
    private JLabel progressLabel;

    /**
     * This is the main method that generates the UI
     */
    public void init(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //add main panel
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(400,100));
        BorderLayout borderLayout = new BorderLayout();
        jp.setLayout(borderLayout);
        frame.getContentPane().add(jp);

        //add label panel
        JPanel labelPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(3,1);
        labelPanel.setLayout(gridLayout);
        jp.add(labelPanel, BorderLayout.NORTH);

        //add folder name label
        folderNameLabel = new JLabel("You have picked: ", JLabel.CENTER);
        labelPanel.add(folderNameLabel);

        //add size label
        sizeLabel = new JLabel("Total size: ", JLabel.CENTER);
        labelPanel.add(sizeLabel);

        //add progress label
        progressLabel = new JLabel("Progress: ", JLabel.CENTER);
        labelPanel.add(progressLabel);

        //add a control panel which contains tow buttons
        JPanel controlPanel = new JPanel();
        jp.add(controlPanel, BorderLayout.SOUTH);

        //add pick folder button
        JButton pickFolderBtn = new JButton();
        pickFolderBtn.setText("Pick a Folder");
        pickFolderBtn.addActionListener(new PickFolderListener(this));
        controlPanel.add(pickFolderBtn);

        //add archive button
        JButton archiveBtn = new JButton();
        archiveBtn.setText("Archive");
        archiveBtn.addActionListener(new ArchiveListener(this));
        controlPanel.add(archiveBtn);

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This method is to update the labels including which folder the user has picked,what the size of the selected folder is as well as initializing the progress label
     * @param str the path and name of the folder the user has selected
     */
    public void setSelectedFolder(String str){
        this.selectedFolder = str;
        this.folderNameLabel.setText("You have picked: "+str);
        //update size label
        try {
            this.sizeLabel.setText("Total size: " + FileUtils.formatFileSize(FileUtils.getFileSize(new File(str))));
            this.progressLabel.setText("Progress: 0/"+FileUtils.generateFileList(new File(str)).size());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getSelectedFolder(){
        return this.selectedFolder;
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public void setProgress(String str){
        this.progressLabel.setText(str);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main main = new Main();
                main.init();
            }
        });
    }
}

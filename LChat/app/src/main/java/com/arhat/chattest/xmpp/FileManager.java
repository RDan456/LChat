package com.arhat.chattest.xmpp;

import com.arhat.chattest.constants.Constants;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import java.io.File;

/**
 * Created by Arhat on 2016/9/17.
 */
public class FileManager {


    /**
     * 发送在线文件
     * 该种文件只提供了文件的路径和对方的名称
     * @return
     */
    public boolean sendFile(String toUserName,String filePath,String descrbile) {

        if(XmppConnection.getIntence().getConnection() == null) {

            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        if(!toUserName.contains("@")) {
            toUserName = toUserName+"@"+ Constants.SERVER_NAME;
        }
        Presence presence = Roster.getInstanceFor(XmppConnection.getIntence().getConnection()).getPresence(toUserName);
        if(presence == null) {
            throw  new NullPointerException("该用户处于非在线状态，请稍后再试");
        }
        toUserName = presence.getFrom();
        try {
            //获取文件管理类并且创造一个发送文件的对象
            FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(XmppConnection.getIntence().getConnection());
            OutgoingFileTransfer outgoingFileTransfer = fileTransferManager.createOutgoingFileTransfer(toUserName);
            File file = new File(filePath);//根据提供的路径创建一个文件，并提供给outgoingFileTransfer对象使用
            outgoingFileTransfer.sendFile(file,descrbile);

            long startTime = 0;//计算文件发送的时间
            while(!outgoingFileTransfer.isDone()) {
                if(outgoingFileTransfer.getStatus().equals(FileTransfer.Status.error)) {
                    System.out.println("文件发送出错");
                    return false;
                }else {
                    //查看文件传送的效率
                    double progress = outgoingFileTransfer.getProgress();
                    if(progress>0.00 && startTime==-1) {
                        startTime = System.currentTimeMillis();
                    }
                    progress*=100;
                    System.out.println("progress="+ progress+"%");
                }
                try {
                    Thread.sleep(1000);
                }catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 发送在线文件
     * 该文件的类型是文件流的转发
     * @return
     */
    public boolean sendFile(String fileName, long fileSize, String description,String toUserName) {

        if(XmppConnection.getIntence().getConnection() == null) {

            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        if(!toUserName.contains("@")) {
            toUserName = toUserName+"@"+ Constants.SERVER_NAME;
        }
        Presence presence = Roster.getInstanceFor(XmppConnection.getIntence().getConnection()).getPresence(toUserName);
        if(presence == null) {
            throw  new NullPointerException("该用户处于非在线状态，请稍后再试");
        }
        try {
            FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(XmppConnection.getIntence().getConnection());
            OutgoingFileTransfer outgoingFileTransfer = fileTransferManager.createOutgoingFileTransfer(toUserName);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 接受在线文件
     */
    public boolean receiveFile() {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new   NullPointerException("服务器已断开连接，请重新连接服务器");
        }
        //增加文件监听器
        final FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(XmppConnection.getIntence().getConnection());
        fileTransferManager.addFileTransferListener(new FileTransferListener() {
            @Override
            public void fileTransferRequest(FileTransferRequest fileTransferRequest) {

                final IncomingFileTransfer incomingFileTransfer = fileTransferRequest.accept();
                final String fileName = fileTransferRequest.getFileName();
                long fileLength = fileTransferRequest.getFileSize();
                final String description = fileTransferRequest.getDescription();
                final String fromUser = fileTransferRequest.getRequestor().split("/")[0];

                System.out.println("好友"+fromUser+"发来文件"+fileName+"的长度是"+fileLength+"描述是"+description);
                //是否接收文件
                try {
                    if(true) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    incomingFileTransfer.recieveFile();
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else {
                        incomingFileTransfer.cancel();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    /**
     * 发送离线文件
     * @return
     */


    /**
     * 暂停发送
     * @return
     */


    /**
     * 重新开始发送
     * @return
     */


    /**
     * 取消发送
     * @return
     */

    /**
     * 文件发送的效率
     * @return
     */
}

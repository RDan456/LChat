package com.arhat.chattest.sqllit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.ChatItem;
import com.arhat.chattest.model.Dialogue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arhat on 2016/9/8.
 */
public class NewMessageDBHelper {

    //采用单列模式
    private static NewMessageDBHelper instance = null;

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqlLiteHelper;

    public NewMessageDBHelper(Context context) {

        sqlLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
    }

    //单例模式获得一个实例
    public static NewMessageDBHelper getInstance(Context context) {

        if (instance == null) {
            instance = new NewMessageDBHelper(context);
        }
        return instance;
    }

    //关闭连接
    public void closeDB() {

        sqLiteDatabase.close();
        sqlLiteHelper.close();
    }

    class SQLiteHelper extends SQLiteOpenHelper {

        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "newMessageRecord.db";
        private static final String TABLE_NAME = "newMessage";

        public SQLiteHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "create table if not exists newMessage" +
                    "( id integer primary key autoincrement,messageId text,messageCount integer, whoseMessage text," +
                    "i_field1 integer, t_field1 text)";
            db.execSQL(sql);
        }

        //升级后先删后重新建表
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            dropTable(db);
            onCreate(db);
        }

        public void dropTable(SQLiteDatabase db) {

            String sql = "drop table if exists newMessage";
            db.execSQL(sql);
        }
    }

    /**
     * 保存信息
     */
    public void saveNewMessage(String messageId,String inOrOut) {

        int nowMessageCount = getMessageCount(messageId);
        ContentValues contentValues = new ContentValues();
        if (nowMessageCount == 0) {
            contentValues.put("messageId", messageId);
            if(inOrOut.equals("out")) {
                contentValues.put("messageCount", 0);
            }else {
                contentValues.put("messageCount", 1);
            }
            contentValues.put("whoseMessage", Constants.USER_NAME);

            sqLiteDatabase.insert(sqlLiteHelper.TABLE_NAME, null, contentValues);
        } else {
            if(inOrOut.equals("in")) {
                contentValues.put("messageCount", nowMessageCount + 1);
                sqLiteDatabase.update(sqlLiteHelper.TABLE_NAME, contentValues, " messageId=? and whoseMessage=?",
                        new String[]{messageId, Constants.USER_NAME});
            }else{ }
        }
    }

    /**
     * 将新消息数目设置为0
     */
    public void chanageMessageCount(String messageId) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("messageCount", 0);
        sqLiteDatabase.update(sqlLiteHelper.TABLE_NAME, contentValues, " messageId=? and whoseMessage=?",
                new String[]{messageId, Constants.USER_NAME});
    }

    /**
     * 获取所有的新消息
    * @return
     */
    public List<Dialogue> getAllNewMessage() {

        String sql = "select distinct messageId,messageCount from newMessage where whoseMessage=?";
        Cursor c = sqLiteDatabase.rawQuery(sql,new String[]{Constants.USER_NAME});
        Dialogue dialogue;
        List<Dialogue> dialogues = new ArrayList<Dialogue>();
        while (c.moveToNext()) {
            List<ChatItem> items =  MessageDBHelper.getInstance(MyApplication.getInstance())
                    .getChatMessage(c.getString(c.getColumnIndex("messageId")));//获取最后一条消息
            if(items.size()==0) {
                continue;
            }
            ChatItem item = items.get(items.size()-1);
            dialogue = new Dialogue("",c.getString(c.getColumnIndex("messageId")),c.getInt(c.getColumnIndex("messageCount")),c.getInt(c.getColumnIndex("messageCount")),item.msg);
            dialogues.add(dialogue);
        }
        return dialogues;
    }

    /**
     * 删除新消息
     *
     * @param messageId
     */
    public void deleteNewMessage(String messageId) {

        sqLiteDatabase.delete(sqlLiteHelper.DB_NAME, " messageId=? and whoseMessage=?", new String[]{messageId, Constants.USER_NAME});
    }

    /**
     * 获取关于某个人的新消息数目
     */
    public int getMessageCount(String messageId) {

        int count = 0;
        String sql = "select messageCount from newMessage where messageId=? and whoseMessage=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{messageId, Constants.USER_NAME});

        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    /**
     * 获取所有的消息数目
     */
    public int getMessageCount() {

        int count = 0;
        String sql = "select sum(messageCount) from newMessage where whoseMessage=? and messageId !=0";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{Constants.USER_NAME});
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public void clear() {
        sqLiteDatabase.delete(sqlLiteHelper.DB_NAME, "id>?", new String[]{"0"});
    }
}

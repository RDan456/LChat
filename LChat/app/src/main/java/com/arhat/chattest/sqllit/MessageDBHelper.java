package com.arhat.chattest.sqllit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.model.ChatItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arhat on 2016/9/8.
 */
public class MessageDBHelper {

    private static MessageDBHelper instance = null;
    private SQLitHelper sqLitHelper;
    private SQLiteDatabase database;
    private final int SHOW_MSG_COUNT = 18;//只能显示18条
    private final int MORE_MSG_COUNT = 8;//下拉刷新出来的消息数

    public MessageDBHelper(Context context) {

        sqLitHelper = new SQLitHelper(context);
        database = sqLitHelper.getWritableDatabase();
    }

    public static MessageDBHelper getInstance(Context context) {

        if(instance == null) {
            instance = new MessageDBHelper(context);
        }
        return instance;
    }

    public void databaseClose() {

        database.close();
        sqLitHelper.close();
    }

    class SQLitHelper extends SQLiteOpenHelper {

        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "messageManger.db";
        private static final String TABLE_NAME="chatRecord";

        public SQLitHelper(Context context) {

            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "create table if not exists chatRecord (" +
                    "id integer primary key autoincrement, chatType integer , chatName text ," +
                    " userName text , head text , message text , sendData text , inOrOut integer ," +
                    " whoseMessage text, i_filed integer,t_field text)";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            deleteTable(db);
            onCreate(db);
        }

        public void deleteTable(SQLiteDatabase db) {

            String sql = "drop table if exists chatRecord";
            db.execSQL(sql);
        }
    }

    /**
     * 保存消息
     */
    public void saveMassage(ChatItem message) {

        ContentValues values = new ContentValues();

        values.put("chatType",message.chatType);
        values.put("chatName",message.chatName);
        values.put("userName",message.username);
        values.put("head",message.head);
        values.put("message",message.msg);
        values.put("sendData",message.sendDate);
        values.put("inOrOut",message.inOrOut);
        values.put("whoseMessage",Constants.USER_NAME);

        database.insert(sqLitHelper.TABLE_NAME,"id",values);
    }

    /**
     * 取出当前对话的消息
     */
    public List<ChatItem> getChatMessage(String chatName) {

        List<ChatItem> messages = new ArrayList<>();
        ChatItem item;
        String sql = "select a.chatType,a.chatName,a.userName,a.head,a.message,a.sendData,a.inOrOut " +
                " from( select * from chatRecord " +
                " where chatName = ? and whoseMessage = ? order by id desc LIMIT " +SHOW_MSG_COUNT+")a order by a.id";
        Cursor cursor = database.rawQuery(sql, new String[]{chatName,Constants.USER_NAME});

        while (cursor.moveToNext()) {

            item = new ChatItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getInt(6));
            messages.add(item);
            item = null;
        }
        cursor.close();
        return messages;
    }

    /**
     * 获取更多的消息
     */
    public List<ChatItem> getMoreMessage(int startOrder,String chatName) {

        List<ChatItem> messages = new ArrayList<>();
        ChatItem item;
        String sql ="select a.chatType,a.chatName,a.userName,a.head,a.message,a.sendData,a.inOrOut " +
                " from(select * from chatRecord " +
                " where chatName = ? and whoseMessage = ? order by id desc LIMIT " +MORE_MSG_COUNT+" offset "+startOrder+")a order by a.id";
        Cursor cursor = database.rawQuery(sql, new String[]{chatName,Constants.USER_NAME});
        while(cursor.moveToNext()){
            item = new ChatItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getInt(6));
            messages.add(item);
            item = null;
        }
        cursor.close();
        return messages;
    }

    /**
     * 获取最新的消息
     */
    public List<ChatItem> getNewMessage() {

        List<ChatItem> messages = new ArrayList<>();
        ChatItem item;
        String sql ="select chatType,chatName,userName,head,message,sendData,inOrOut from chatRecord " +
                " where whoseMessage = ? "+
                " GROUP BY chatName "+
                "order by id desc";
        final Cursor cursor = database.rawQuery(sql, new String[]{Constants.USER_NAME});
        while (cursor.moveToNext()) {
            item = new ChatItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getInt(6));
            messages.add(item);
            item = null;
        }
        cursor.close();
        return messages;
    }

    /**
     * 取得我的的最新消息，模糊搜索,显示在好友表
     */

    public List<ChatItem> getNewMessageBySearch(String keywords){

        List<ChatItem> messages = new ArrayList<ChatItem>();
        ChatItem item;
        String sql ="select chatType,chatName,userName,head,message,sendData,inOrOut from chatRecord "+
                " where userName like ? and whoseMessage= ? "+
                " GROUP BY chatName "+
                " order by id desc";
        final Cursor cursor = database.rawQuery(sql, new String[]{"%"+keywords+"%",Constants.USER_NAME});
        while (cursor.moveToNext()) {
            item = new ChatItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getInt(6));
            messages.add(item);
            item = null;
        }
        cursor.close();
        return messages;
    }

    /**
     * 删除聊天信息
     * @param messageId
     */
    public void delChatMsg(String messageId){
        database.delete(sqLitHelper.TABLE_NAME, "chatName=? and whoseMessage=?", new String[]{messageId,Constants.USER_NAME});
    }

    /**
     * 清除信息
     */
    public void clear(){

        database.delete(sqLitHelper.TABLE_NAME, "id>?", new String[]{"0"});
    }
}

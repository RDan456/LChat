package com.arhat.chattest.sqllit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arhat on 2016/9/8.
 */
public class NewFriendsDBHelper {

    private static NewFriendsDBHelper instance = null;
    private SQLiteDatabase database;
    private SQLitHelper sqLitHelper;

    public NewFriendsDBHelper(Context context) {

        sqLitHelper = new SQLitHelper(context);
        database = sqLitHelper.getWritableDatabase();
    }

    public void closeDatabase() {

        database.close();
        sqLitHelper.close();
    }

    public static NewFriendsDBHelper getInstance(Context context) {

        if(instance == null) {
            instance = new NewFriendsDBHelper(context);
        }
        return instance;
    }

    class SQLitHelper extends SQLiteOpenHelper {


        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "newFriends";
        public SQLitHelper (Context context) {

            super(context, DB_NAME, null, DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "create table if not exists " + DB_NAME
                    + "( id integer primary key autoincrement,"+
                    "userName text ,sendData text,isDeal integer," +
                    "whoseMessage text,i_filed integer,t_field text)";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            deleteTable(db);
            onCreate(db);
        }

        public void deleteTable(SQLiteDatabase db) {

            String sql = "drop table if exists "+DB_NAME;
            db.execSQL(sql);
        }
    }

    //某个人
    public int getCount(String username){

        int count = 0 ;
        String sql ="select count(0) from "+sqLitHelper.DB_NAME+" where userName=? and whoseMessage=?";
        Cursor cursor = database.rawQuery(sql, new String[]{username, Constants.USER_NAME});
        while(cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    //某个人是否已处理
    public boolean isDeal(String username){
        boolean isDeal = false ;
        String sql ="select isDeal from "+sqLitHelper.DB_NAME+" where userName=? and whoseMessage=?";
        Cursor cursor = database.rawQuery(sql, new String[]{username,Constants.USER_NAME});
        while(cursor.moveToNext()){
            if (cursor.getInt(0) == 0) {
                isDeal = false;
            }
            else {
                isDeal = true;
            }
        }
        cursor.close();
        return isDeal;
    }

    /**
     * 取邀请我的
     */
    public List<String> getNewFriend(){
        List<String> friends = new ArrayList<String>();
        String sql = "select username from " +sqLitHelper.DB_NAME +
                " where whoseMessage = ? order by sendData desc";
        Cursor cursor = database.rawQuery(sql, new String[]{Constants.USER_NAME});
        while(cursor.moveToNext()){
            friends.add(cursor.getString(0));
        }
        cursor.close();
        return friends;
    }

    //保存好友
    public void saveNewFriend(String username){
        int nowCount = getCount(username);
        ContentValues values = new ContentValues();
        if (nowCount == 0) {
            values.put("userName", username);
            values.put("sendData", DateUtil.now_MM_dd_HH_mm_ss());
            values.put("whoseMessage", Constants.USER_NAME);
            database.insert(sqLitHelper.DB_NAME, "id", values);
        }
        else{
            values.put("sendDate", DateUtil.now_MM_dd_HH_mm_ss());
            values.put("isDeal", 0);
            database.update(sqLitHelper.DB_NAME, values, " username=? and whos=?",
                    new String[]{username,Constants.USER_NAME});
        }

        NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(""+0,"in");
    }

    //是否已经处理请求
    public void delFriend(String username){
        ContentValues values = new ContentValues();
        values.put("isDeal", 1);
        database.update(sqLitHelper.DB_NAME, values, " userName=? and whoseMessage=?",
                new String[]{username,Constants.USER_NAME});
    }

}

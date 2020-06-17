package com.example.m.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.m.myapplication.Db.MyDatabaseHelper;
import com.example.m.myapplication.PinlunAdapter.pinlun;
import com.example.m.myapplication.atadapter.MsgAdapter;
import com.example.m.myapplication.bean.Msg;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TalkActivity extends AppCompatActivity {
    private List<Msg> msgList=new ArrayList<>();
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private boolean Send=true;
    private MyDatabaseHelper myDatabaseHelper;
    public static final String NaMe = "in_name";
    public static final String NaINTENT = "in_ll";
    private Cursor cursor;
    private String stringname;
    private String str;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues values;
    private static final String HOST = "192.168.43.87";
    private static final int PORT =9090;
    DataOutputStream dos;
    private DatagramSocket msocketClient;
    private Socket socket;
    private  Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        myDatabaseHelper = new MyDatabaseHelper(this, "Talk.db", null, 1);
        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
//获得上个活动信息
        values=new ContentValues();
        editText=findViewById(R.id.input_text);
        button=findViewById(R.id.send);
        recyclerView=findViewById(R.id.msg_recycle_view);
        adapter=new MsgAdapter(msgList);
        Intent intent=getIntent();
        stringname=intent.getStringExtra(NaMe);
        str=intent.getStringExtra(NaINTENT);
//初始化
        initmsg();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

         handler = new MyHandler();
        recyclerView.scrollToPosition(msgList.size()-1);

        R();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=editText.getText().toString();
                if (!"".equals(content))
                {

                    Msg msg=new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
//判断点击来自哪个活动
                    if(str.equals("goods"))
                    {
                        banduan();
                    }
                    values.put("name",stringname);
                    values.put("neixin", 2);
                    values.put("contex", content);
                    sqLiteDatabase.insert("talkinf",null,values);
                    values.clear();


                    adapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size()-1);
                    S();
                    editText.setText("");
                }
            }
        });
    }

private void initmsg() {

//  ContentValues values=new ContentValues();
//		for (int i = 0; i < 3; i++) {
//			values.put("name", "小明");
//			values.put("neixin", 1);
//            values.put("contex", "你好。。。");
//			sqLiteDatabase.insert("talkinf", null, values);
//			values.clear();
//		}
//    for (int i = 0; i < 3; i++) {
//        values.put("name", "小明");
//        values.put("neixin", 2);
//        values.put("contex", "你好。。。");
//        sqLiteDatabase.insert("talkinf", null, values);
//        values.clear();
//    }
//    for (int i = 0; i < 1; i++) {
//        values.put("name", "张三");
//        values.put("neixin", 1);
//        values.put("contex", "我很好。。。");
//        sqLiteDatabase.insert("talkinf", null, values);
//        values.clear();
//    }
//    for (int i = 0; i < 2; i++) {
//        values.put("name", "张三");
//        values.put("neixin", 2);
//        values.put("contex", "我很好。。。");
//        sqLiteDatabase.insert("talkinf", null, values);
//        values.clear();
//    }

//数据库操作
    cursor = sqLiteDatabase.query("talkinf", null, null, null, null, null, null);
    if (cursor.moveToFirst()) {
        do {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if (stringname.equals(name)) {
                int neixin = cursor.getInt(cursor.getColumnIndex("neixin"));
                String content = cursor.getString(cursor.getColumnIndex("contex"));
                if (neixin == 1) {
                    Msg msg1 = new Msg(content, Msg.TYPE_RECEIVED);
                    msgList.add(msg1);
                } else {
                    Msg msg1 = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg1);
                }
            }

        } while (cursor.moveToNext());
    }
    cursor.close();
}
private void banduan(){
    cursor=sqLiteDatabase.query("inf",null,null,null,null,null,null);
    boolean i=true;
    if(cursor.moveToFirst()){
        do{
            String name2=cursor.getString(cursor.getColumnIndex("name"));
            if(stringname.equals(name2)){
                i=false;
                break;
            }
        }while (cursor.moveToNext());
    }
    if (i){
        values.put("name",stringname);
                values.put("time","1:00");
                sqLiteDatabase.insert("inf",null,values);
                values.clear();
    }
    cursor.close();
}
    private void R(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    socket = new Socket("10.0.2.2", 10010);
                    InputStream inputStream = socket.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        String data = new String(buffer, 0, len);
                        // 发到主线程中 收到的数据
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private class  MyHandler extends Handler{

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Msg msg2=new Msg((String) msg.obj,Msg.TYPE_RECEIVED);
                msgList.add(msg2);
                adapter.notifyItemInserted(msgList.size()-1);
                recyclerView.scrollToPosition(msgList.size()-1);

                values.put("name",stringname);
                values.put("neixin", 1);
                values.put("contex", (String) msg.obj);
                sqLiteDatabase.insert("talkinf",null,values);
                values.clear();

            }
        }
    }
    private void S(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String data = editText.getText().toString();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(data.getBytes("utf-8"));
                    outputStream.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

package com.example.m.myapplication;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.m.myapplication.Db.MyDatabaseHelper;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText account;
    private EditText pwd;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.bt_login);
        account=findViewById(R.id.et_data_uname);
        pwd=findViewById(R.id.et_data_upass);
        myDatabaseHelper = new MyDatabaseHelper(this, "Talk.db", null, 1);
        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        init();

    }
    /**
     * 测试okhttp的post方法
     */
    private void init(){

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String acc=account.getText().toString();
                String pw=pwd.getText().toString();
                testOkhttpPost(acc,pw);
            }
        });
    }
    private void testOkhttpPost(String acc,String pw) {
        String url = "http://10.0.2.2:8080/servertest/LoginDateServlet";

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", acc)
                .add("password", pw)
                .add("condition","login")
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                final String string = response.body().string();
                   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           if(parseJson(string)){
                               Toast.makeText(LoginActivity.this,"登陆成功。",Toast.LENGTH_SHORT).show();

                               LoginActivity.this.finish();
                           }
                        }
                    });
            }
        });
    }
//解析数据
    private boolean parseJson(String jsonData) {
        if (!TextUtils.isEmpty(jsonData)) {
            try {

                    JSONObject jsonObject = new JSONObject(jsonData);
                    String con= jsonObject.getString("con");
                    if (con.equals("ok")){
                        //本地数据库
                        String name = jsonObject.getString("name");
                        ContentValues values=new ContentValues();
                        values.put("cookier",name);
                        sqLiteDatabase.insert("cook",null,values);
                        values.clear();
                        return true;
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        return false;
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

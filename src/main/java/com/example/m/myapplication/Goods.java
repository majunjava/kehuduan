package com.example.m.myapplication;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.m.myapplication.PinlunAdapter.pinlun;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Goods extends AppCompatActivity {
    String re;
    private pinlun pinlunos;
    private List<pinlun> pinluns=new ArrayList<>();
    public static final String UP_name = "inf_name";
    public static final String UP_img = "img_id";
    private TextView textView;
    private  TextView textViewinf;
    private  TextView infedit;
    private  ImageView imageView;
    private InputStream in=null;
    private String address;
    private Button buttontalk;
    private String inname;
    private String []namestr;
    private String con;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        Intent intent=getIntent();
        inname=intent.getStringExtra(UP_name);
        String img1=intent.getStringExtra(UP_img);
        namestr=inname.split(":");

       textView = findViewById(R.id.instructionedit);
       textViewinf=findViewById(R.id.inf);
       infedit=findViewById(R.id.infedit);
        imageView=findViewById(R.id.imagett);
        buttontalk=findViewById(R.id.Button_talk);
        Glide.with(this).load(img1).into(imageView);
        textView.setText(namestr[1]);
        init();
        address="http://10.0.2.2:8080/servertest/goodsinf?name="+namestr[0];

       send(address);

//        String url = "http://10.0.2.2:8080/servertest/ContentServlet?name=name";
//        Glide.with(this)
//                .load(url)
//                .into(imageView);
    }

    private void init(){
        buttontalk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0){
                Intent A = new Intent(Goods.this, TalkActivity.class);
                A.putExtra(TalkActivity.NaMe,namestr[1]);
                A.putExtra(TalkActivity.NaINTENT,"goods");
                startActivity(A);}
        });
    }

private  void send(String address1){
 net.sendOkHttpRequest(address1, new Callback() {
     @Override
     public void onFailure(Call call, IOException e) {

     }

     @Override
     public void onResponse(Call call, Response response) throws IOException {
         final String string=response.body().string();
         if (parseJson(string)){

         runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewinf.setText(con);
                infedit.setText(price);

            }
        });
         }
     }
 });

}
    private boolean parseJson(String jsonData) {
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                con= jsonObject.getString("inf");
                price=jsonObject.getString("price");



                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}


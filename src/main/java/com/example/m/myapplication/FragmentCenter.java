package com.example.m.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FragmentCenter extends Fragment {
    private Button sangcuan;
    private  Button picadd;
    private static final int CHOOSE_PHOTO = 16;
    private ImageView a;
    private String imagePath = null;
    private EditText editText1;
    private  EditText address;
    private EditText price;
    private RadioButton fabu;
    private RadioButton rent;
    private String con="1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_center, container, false);
        sangcuan=view.findViewById(R.id.tijiao);
        a=view.findViewById(R.id.centerimg1);
        picadd=view.findViewById(R.id.centerbutton);
        editText1=view.findViewById(R.id.centerEdit);
        address=view.findViewById(R.id.address);
        price=view.findViewById(R.id.prise);
        fabu=view.findViewById(R.id.fabu);
        rent=view.findViewById(R.id.rent);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    private void init(){
        picadd .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if ( ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    //用户如果之前就允许了权限之后进行的操作
                    openAlbum();
                }
             }
        });
        sangcuan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                uploadImage("http://10.0.2.2:8080/servertest/UploadFile",imagePath);
                if (fabu.isChecked()){
                    con="1";
                    send("http://10.0.2.2:8080/servertest/Uploadtext",con);
                }
                if (rent.isChecked()){
                    con="2";
                    send("http://10.0.2.2:8080/servertest/Uploadtext",con);
                }

            }
        });
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //如果用户允许请求进行的操作
                    openAlbum();
                } else {
                    //如果用户拒绝请求进行的操作
                    Toast.makeText(getActivity(), "You denid the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    //判断手机系统版本号,因为4.4版本开始，选取相册图片不再返回图片真实Uri，而是一个封装过的Uri,要进行解析
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上版本
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下版本
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    //4.4及以上版本
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        //如果document类型的Uri，则通过document id 处理
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                //根据Uri和selection来获取真实的图片路径
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
            //如果是content类型的Uri,则通过普通方式处理
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);

            //如果是file类型的Uri,直接获取图片路径即可
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        //根据图片路径显示图片
        displayImage(imagePath);
    }

    //4.4以下版本
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }
    //根据Uri和selection来获取真实的图片路径
    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    //根据图片路径显示图片
    private void displayImage(String imagePath) {
        if(imagePath!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            a.setImageBitmap(bitmap);

        }else{
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    public  void uploadImage(String url, String imagePath){
        File file=new File(imagePath);
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
            OkHttpClient okHttpClient = new OkHttpClient();
            //构建指定文件实例
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("img",imagePath)
                    //传入文件实例
                    .post(RequestBody.create(mediaType, file))

                    .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

//                final String string = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }
    private  void send(String address1,String b) {
        StringBuilder a=new StringBuilder();
        a.append(address1);//http://localhost:8080/servertest/Uploadtext?name=aa&url=ll&inf=dd&add=as&price=1&con=1
        String a1=editText1.getText().toString();
        String a2=address.getText().toString();
        String a3=price.getText().toString();
        a.append("?name=");
        a.append("aa");
        a.append("&url=");
        a.append(imagePath);
        a.append("&inf=");
        a.append(a1);
        a.append("&add=");
        a.append(a2);
        a.append("&price=");
        a.append(a3);
        a.append("&con=");
        a.append(b);
        net.sendOkHttpRequest(a.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!TextUtils.isEmpty(string)){

                            editText1.setText("");
                           address.setText("");
                           price.setText("");
                            Toast.makeText(getActivity(),"发布成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
}

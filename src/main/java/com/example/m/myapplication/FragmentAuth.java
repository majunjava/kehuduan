package com.example.m.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.m.myapplication.PinlunAdapter.pinlun;
import com.example.m.myapplication.PinlunAdapter.pinlunAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FragmentAuth extends Fragment {
	private List<pinlun>pinluns=new ArrayList<>();
	private EditText mAmEtMsg;
	private pinlunAdapter adapter;
	private LinearLayout linearLayout;
	private SwipeRefreshLayout mAmSrlReflush;
	private FloatingActionButton fab;
	private Button huifu;
	private pinlun pinlunos;
	private ProgressDialog progressDialog;
	private ListView listView;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view=inflater.inflate(R.layout.fragment_auth, container, false);
	linearLayout=view.findViewById(R.id.am_ll_liuyan);
	fab=view.findViewById(R.id.fab) ;
	listView=view.findViewById(R.id.listviewdongtai);
	mAmSrlReflush=view.findViewById(R.id.am_reflush);
	mAmSrlReflush.setColorSchemeResources(R.color.colorPrimary);
	huifu=view.findViewById(R.id.am_b_save);
	//添加头部
	View headView = getLayoutInflater().inflate(R.layout.dongtaihead, null);
	listView.addHeaderView(headView);

	return view;
}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		send("http://10.0.2.2:8080/servertest/Dongtai?name=name");
		click();

}
//请求数据
	private  void send(String address1) {
	showprogress();
	net.sendOkHttpRequest(address1, new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						closeprogress();
//				//comment(false);
//						//fab.setVisibility(View.GONE);
//				Toast.makeText(getActivity(),"没有网络！！！",Toast.LENGTH_SHORT).show();
//					}
//				});
			}
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				final String string = response.body().string();
				if (parseJson(string)){

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						closeprogress();
						fab.setVisibility(View.VISIBLE);
						adapter=new pinlunAdapter(getActivity(),R.layout.pinlun,pinluns);
						listView.setAdapter(adapter);
//						adapter.notifyDataSetChanged();
					}
				});}
			}
		});
	pinluns.clear();
	}
	private void click(){
		fab.setOnClickListener(new View.OnClickListener(){
								   public void onClick(View v){
									   mAmEtMsg=getActivity().findViewById(R.id.am_et_msg);
									   comment(true);
								   }
							   }
		);
		//刷新
		mAmSrlReflush.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
			public void onRefresh(){
				refreshNei();
			}

		});
		huifu.setOnClickListener(new View.OnClickListener(){
									 public void onClick(View v){
										 saveComment();
										 mAmEtMsg.setText("");
									 }
								 }
		);

	}
	private void saveComment() {
		if (!TextUtils.isEmpty(mAmEtMsg.getText())) {
			String info = mAmEtMsg.getText().toString();
			updateComment(info);
		} else {
			Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_SHORT).show();
		}
	}
	//上传用户留言，然后刷新界面
	private void updateComment(String info) {
		pinlun pinlunos=new pinlun(R.drawable.skin_tabbar_btn,"name1","time",info);
		pinluns.add(0,pinlunos);
		adapter.notifyDataSetChanged();
		//上传
		comment(false);
//		sangcaun();
		Toast.makeText(getActivity(),"发表成功！",Toast.LENGTH_SHORT).show();

	}
	//出现和消失输入框
	private void comment(boolean flag) {
		if(flag){
			linearLayout.setVisibility(View.VISIBLE);
			fab.setVisibility(View.GONE);
			onFocusChange(flag);
		}else{
			linearLayout.setVisibility(View.GONE);
			fab.setVisibility(View.VISIBLE);
			onFocusChange(flag);
		}
	}
	private void onFocusChange(boolean hasFocus) {
		final boolean isFocus = hasFocus;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager)
						getActivity().getSystemService(INPUT_METHOD_SERVICE);
				if (isFocus) {
					//显示输入法
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					mAmEtMsg.setFocusable(true);
					mAmEtMsg.requestFocus();
				} else {
					//隐藏输入法
					imm.hideSoftInputFromWindow(mAmEtMsg.getWindowToken(), 0);
				}
			}
		}, 100);}
//	//解析服务器返回的json
	private boolean parseJson(String jsonData) {
		if (!TextUtils.isEmpty(jsonData)) {
			try {
				JSONArray jsonArray = new JSONArray(jsonData);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);//获取数组中第i各数据
					String name = jsonObject.getString("name");
					String time = jsonObject.getString("time");
					String content = jsonObject.getString("content");
					pinlunos = new pinlun(R.drawable.login1, name, time, content);
					pinluns.add(pinlunos);//加载到自定义的ListView中
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}
	//刷新
	private void refreshNei(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						send("http://10.0.2.2:8080/servertest/Dongtai?name=name");

						mAmSrlReflush.setRefreshing(false);
					}
				});
			}
		}).start();
	}
	private void showprogress(){
		if(progressDialog==null){
			progressDialog=new ProgressDialog(getActivity());
			progressDialog.setMessage("正在加载");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	private void closeprogress(){
		if (progressDialog!=null){
			progressDialog.dismiss();
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}

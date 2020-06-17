package com.example.m.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m.myapplication.Db.MyDatabaseHelper;

//5
public class FragmentMore extends Fragment{
	private ImageView LoGin;
	private LinearLayout myaccount;
	private MyDatabaseHelper myDatabaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Cursor cursor;
	private TextView me_homepage_username;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_more, container, false);
		LoGin=view.findViewById(R.id.login_account) ;
		myaccount=view.findViewById(R.id.myaccount);
		me_homepage_username=view.findViewById(R.id.me_homepage_username);

		return view;

	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		data();

	}
	private void init(){
		LoGin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0){
				Intent A = new Intent(getActivity(), LoginActivity.class);
				startActivity(A);
			}
		});
		myaccount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0){
				Toast.makeText(getActivity(),"登陆成功。",Toast.LENGTH_SHORT).show();

			}
		});
	}
	private void data(){
		myDatabaseHelper = new MyDatabaseHelper(getActivity(), "Talk.db", null, 1);
		sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
		cursor = sqLiteDatabase.query("cook", null, null, null, null, null, null);
		if (cursor.moveToFirst()){
			String name = cursor.getString(cursor.getColumnIndex("cookier"));
			me_homepage_username.setText(name);

		}

	}

	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
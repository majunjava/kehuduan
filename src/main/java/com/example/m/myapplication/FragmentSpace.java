package com.example.m.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.m.myapplication.Db.MyDatabaseHelper;
import com.example.m.myapplication.PinlunAdapter.pinlun;
import com.example.m.myapplication.PinlunAdapter.pinlunAdapter;

import java.util.ArrayList;
import java.util.List;

//4
public class FragmentSpace extends Fragment {
	private ListView listView;
	private pinlunAdapter adapter;
	private List<pinlun> pinluns=new ArrayList<>();
	private pinlun pinlunos;
	private MyDatabaseHelper myDatabaseHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_space, container, false);
		listView=view.findViewById(R.id.lv_contents);
		return view;

	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myDatabaseHelper=new MyDatabaseHelper(getActivity(),"Talk.db",null,1);
//		myDatabaseHelper.getWritableDatabase();

		init();

	}
	private void init(){
		data();
		adapter=new pinlunAdapter(getActivity(),R.layout.item_content_delete,pinluns);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(),TalkActivity.class);
				intent.putExtra(TalkActivity.NaMe,pinluns.get(position).getNamepl());
				intent.putExtra(TalkActivity.NaINTENT,"liebiao");

				getActivity().startActivity(intent);


			}
		});


		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			private View delview;

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
				builder.setMessage("确定删除?");
				builder.setTitle("提示");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(pinluns.remove(position)!=null){
							Toast.makeText(getActivity(), "删除列表项"+position, Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(getActivity(), "出错了"+position, Toast.LENGTH_SHORT).show();
						}
						adapter.notifyDataSetChanged();

					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.create().show();
				return false;
			}
		});
	}
	private void data(){
		SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
//		for (int i = 0; i < 1; i++) {
//			values.put("name", "小明");
//			values.put("time", "12:00");
//			sqLiteDatabase.insert("inf", null, values);
//			values.clear();
//		}
//		for (int i = 0; i < 5; i++) {
//			values.put("name", "张三");
//			values.put("time", "12:00");
//			sqLiteDatabase.insert("inf", null, values);
//			values.clear();
//		}
		Cursor cursor=sqLiteDatabase.query("inf",null,null,null,null,null,null);

		if(cursor.moveToLast()){
			do {
				String name=cursor.getString(cursor.getColumnIndex("name"));
				String time=cursor.getString(cursor.getColumnIndex("time"));
				pinlunos = new pinlun(R.drawable.login1, name, time, "  ");
				pinluns.add(pinlunos);


			}while (cursor.moveToPrevious());
		}
		cursor.close();

//		for (int i = 0; i < 20; i++) {
//			pinlunos = new pinlun(R.drawable.login1, "aaa", "fffff", "  ");
//			pinluns.add(pinlunos);
//		}
	}
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
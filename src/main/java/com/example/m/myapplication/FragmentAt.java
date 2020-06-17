package com.example.m.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m.myapplication.PinlunAdapter.pinlun;
import com.example.m.myapplication.atadapter.MyAdapter;
import com.example.m.myapplication.bean.ExampleBaseBean;
import com.example.m.myapplication.bean.TitleBean;
import com.example.m.myapplication.bean.FootBean;
import com.example.m.myapplication.bean.BodyBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentAt extends Fragment {
    private MyAdapter adapter;
    private List<ExampleBaseBean> mlist = new ArrayList<>();
    private RecyclerView rv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_at, container, false);
        rv=view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);


        return view;
	}
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        initTitle();
        initBody();
//        initFoot();
        initAdapter();
      datainit("http://10.0.2.2:8080/servertest/SouYe?name=name");
	}
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    private void initTitle() {
        List<Integer> titles = new ArrayList<>(5);

        titles.add(R.drawable.ban1);
        titles.add(R.drawable.ban3);
        titles.add(R.drawable.ban2);
        titles.add(R.drawable.ban4);

        TitleBean titleBean = new TitleBean();
        titleBean.setTitles(titles);
        titleBean.setViewType(MyAdapter.TITLE);//设置为轮播类型
        mlist.add(titleBean);
    }
    private void initBody() {
//        List<Integer> res = new ArrayList<>(6);
//        res.add(R.drawable.skin_tabbar_btn);
//        res.add(R.drawable.skin_tabbar_btn);
        BodyBean bodyBean = new BodyBean(" ");
        bodyBean.setViewType(MyAdapter.BODY);//设置横向列表的类型
        mlist.add(bodyBean);
    }
//    private void initFoot() {
//for (int i=1;i<10;i++){
//            FootBean footBean=new FootBean(i+":"+"foot:"+i,"http://10.0.2.2:8080/servertest/ContentServlet?name=name","20",i+":"+"foot","http://10.0.2.2:8080/servertest/ContentServlet?name=name","10");
//
//            footBean.setViewType(MyAdapter.FOOT);//正常列表
//            mlist.add(footBean);}
//
//    }

    private void datainit(String address){

	    net.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(parseJson(string)){
//                           initAdapter();
                           adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
    private boolean parseJson(String jsonData) {
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                JSONArray jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length()-1; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);//获取数组中第i各数据
                    String did1 = jsonObject.getString("id");
                    String dname1 = jsonObject.getString("name");
                    String durl1 = jsonObject.getString("url");
                    String dprice1 = jsonObject.getString("price");

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i+1);
                    String did2 = jsonObject2.getString("id");
                    String dname2 = jsonObject2.getString("name");
                    String durl2 = jsonObject2.getString("url");
                    String dprice2= jsonObject2.getString("price");
                    i++;
                    FootBean footBean=new FootBean(did1+":"+dname1,"http://10.0.2.2:8080/servertest/ContentServlet?name="+durl1,dprice1,did2+":"+dname2,"http://10.0.2.2:8080/servertest/ContentServlet?name="+durl2,dprice2);

                    footBean.setViewType(MyAdapter.FOOT);//正常列表
                    mlist.add(footBean);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }
    private void initAdapter() {
        if (adapter == null) {
            adapter = new MyAdapter(mlist);
            rv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}


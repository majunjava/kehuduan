package com.example.m.myapplication.PinlunAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2020/4/11 0012.
 */
import com.example.m.myapplication.R;

import java.util.List;
public class pinlunAdapter extends ArrayAdapter<pinlun>{
    private int rsourceId;
    public pinlunAdapter(Context context,int textViewResourceId,List<pinlun> objects){
        super(context,textViewResourceId,objects);
        rsourceId=textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        pinlun Pin=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null)
        {
            view=LayoutInflater.from(getContext()).inflate(rsourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.pImg=view.findViewById(R.id.detail_page_userLogo);
            viewHolder.pName = view.findViewById(R.id.pname);
            viewHolder.pDesc = view.findViewById(R.id.pneirong);
            viewHolder.pTime = view.findViewById(R.id.ptime);
            view.setTag(viewHolder);

        }
        else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.pImg.setImageResource(Pin.getImgtouxiang());
        viewHolder.pName.setText(Pin.getNamepl());
        viewHolder.pDesc.setText(Pin.getNeirongpl());
        viewHolder.pTime.setText(Pin.getTimepl());
        return view;
    }
    class ViewHolder{
        ImageView pImg;
        TextView pName;
        TextView pTime;
        TextView pDesc;


    }

}
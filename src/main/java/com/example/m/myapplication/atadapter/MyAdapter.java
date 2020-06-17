package com.example.m.myapplication.atadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.m.myapplication.Goods;
import com.example.m.myapplication.R;
import com.example.m.myapplication.bean.ExampleBaseBean;
import com.example.m.myapplication.bean.TitleBean;
import com.example.m.myapplication.bean.FootBean;
import com.example.m.myapplication.bean.BodyBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter  {

    public final static int TITLE = 1001;//标题的viewType
    public final static int BODY = 1002;//横向列表的viewType
    public final static int FOOT = 1003;//正常列表的viewType

    private List<ExampleBaseBean> mlist;//adapter的数据源
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(List<ExampleBaseBean> mlist) {
        this.mlist = mlist;
    }
    private class TitleHolder extends RecyclerView.ViewHolder {

        ViewPager vp;

        public TitleHolder(View itemView) {
            super(itemView);
            vp = itemView.findViewById(R.id.vp);
        }
    }

    private class BodyHolder extends RecyclerView.ViewHolder {

        TextView rv;

        public BodyHolder(View itemView) {
            super(itemView);
           // rv = itemView.findViewById(R.id.rv);
        }
    }
    private class FootHolder extends RecyclerView.ViewHolder {
        ImageView img_foot;
        TextView tv_foot;
        TextView tv_foot2;
        ImageView img_foot2;
        TextView tv_foot3;
        TextView tv_foot4;

        public FootHolder(View itemView) {
            super(itemView);
            tv_foot = itemView.findViewById(R.id.tv_foot);
            img_foot=itemView.findViewById(R.id.tv_image);
            tv_foot2=itemView.findViewById(R.id.tv_foot2);
            img_foot2=itemView.findViewById(R.id.tv_image2);
            tv_foot3=itemView.findViewById(R.id.tv_foot3);
            tv_foot4=itemView.findViewById(R.id.tv_foot4);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (mlist.size() > 0) {
            return mlist.get(position).getViewType();
        }
        return super.getItemViewType(position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case TITLE:
                view = inflater.inflate(R.layout.banner, parent, false);
                return new TitleHolder(view);
            case BODY:
                view = inflater.inflate(R.layout.iconitem, parent, false);
                return new BodyHolder(view);
            case FOOT:
                view = inflater.inflate(R.layout.contentitem, parent, false);
                return new FootHolder(view);
        }
        return null;
    }
    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleHolder) {
            TitleBean titleBean = (TitleBean) mlist.get(position);
            //
            ((TitleHolder) holder).vp.setPageMargin(20);
            ((TitleHolder) holder).vp.setOffscreenPageLimit(2);
            ((TitleHolder) holder).vp.setPageTransformer(false,new Scalltransformer());
            ((TitleHolder) holder).vp.setAdapter(new PagerAdapter(titleBean.getTitles()));
            ((TitleHolder) holder).vp.setCurrentItem(1);
            //
        }

        if (holder instanceof BodyHolder) {
//            BodyBean bodyBean = (BodyBean) mlist.get(position);
//            ((BodyHolder) holder).rv.setText(bodyBean.getStr1());
        }
        if (holder instanceof FootHolder) {
           final FootBean footBean = (FootBean) mlist.get(position);
            ((FootHolder) holder).tv_foot.setText(footBean.getStr().split(":")[1]);
            ((FootHolder) holder).tv_foot2.setText(footBean.getPrice());
           //
            ((FootHolder) holder).img_foot.setImageDrawable(context.getDrawable(R.drawable.loading));
            final String url = ((FootBean) mlist.get(position)).getImgfoot();
            ((FootHolder) holder).img_foot.setTag(url);
            AsyncTask asyncTask = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    try {
                        URL url = new URL(((FootBean) mlist.get(position)).getImgfoot());
                        Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                        return bitmap;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    //加载完毕后判断该imageView等待的图片url是不是加载完毕的这张
                    //如果是则为imageView设置图片,否则说明imageView已经被重用到其他item
                    if(url.equals(((FootHolder) holder).img_foot.getTag())) {
                        ((FootHolder) holder).img_foot.setImageBitmap(bitmap);
                    }
                }
            }.execute();
//
      // Glide.with(context).load(footBean.getImgfoot()).error(R.drawable.loading).into(((FootHolder) holder).img_foot);
            ((FootHolder) holder).tv_foot3.setText(footBean.getStr2().split(":")[1]);
            ((FootHolder) holder).tv_foot4.setText(footBean.getPrice2());
            //
            ((FootHolder) holder).img_foot2.setImageDrawable(context.getDrawable(R.drawable.loading));
            final String url2 = ((FootBean) mlist.get(position)).getImgfoot2();
            ((FootHolder) holder).img_foot2.setTag(url);
            AsyncTask asyncTask2 = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    try {
                        URL url2 = new URL(((FootBean) mlist.get(position)).getImgfoot2());
                        Bitmap bitmap = BitmapFactory.decodeStream(url2.openStream());
                        return bitmap;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    //加载完毕后判断该imageView等待的图片url是不是加载完毕的这张
                    //如果是则为imageView设置图片,否则说明imageView已经被重用到其他item
                    if(url.equals(((FootHolder) holder).img_foot2.getTag())) {
                        ((FootHolder) holder).img_foot2.setImageBitmap(bitmap);
                    }
                }
            }.execute();
            //
      //  Glide.with(context).load(footBean.getImgfoot2()).error(R.drawable.loading).placeholder( R.drawable.loading).into(((FootHolder) holder).img_foot2);
//            Glide.with(context).load("http://10.0.2.2:8080/servertest/ContentServlet?name=name").into(((FootHolder) holder).img_foot2);
            //设置监听
            ((FootHolder) holder).img_foot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    ((FragmentActivity)context).getSupportFragmentManager()
//                .beginTransaction()
//         .addToBackStack(null)  //将当前fragment加入到返回栈中
//             .replace(R.id.frame_content2,new GoodsInf()).commit();
                    Intent intent = new Intent(context,Goods.class);
                    intent.putExtra(Goods.UP_name,footBean.getStr());
                    intent.putExtra(Goods.UP_img,footBean.getImgfoot());
                    context.startActivity(intent);

//                   Toast.makeText(v.getContext(), "Good! Clicked"+footBean.getStr(), Toast.LENGTH_SHORT).show();

                }
            });

            ((FootHolder) holder).img_foot2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Goods.class);
                    intent.putExtra(Goods.UP_name,footBean.getStr2());
                    intent.putExtra(Goods.UP_img,footBean.getImgfoot2());
                    context.startActivity(intent);

//                    Toast.makeText(v.getContext(), "Good! Clicked"+footBean.getStr(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
//轮播适配器(内部类)
    private class PagerAdapter extends android.support.v4.view.PagerAdapter {

        List<Integer> stringList;

        public PagerAdapter(List<Integer> stringList) {
            this.stringList = stringList;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.width = ViewPager.LayoutParams.WRAP_CONTENT;
            params.height = ViewPager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            ImageView  imageView=new ImageView(container.getContext());
            imageView.setImageResource(stringList.get(position));
            imageView.setMaxHeight(180);
            imageView.setLayoutParams(params);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
        @Override
        public int getCount() {
            return stringList.size();
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    //banner动画效果
    public class Scalltransformer implements ViewPager.PageTransformer {
        private float MINSCALE=0.8f;//最小缩放值

        /**
         * position取值特点：
         * 假设页面从0～1，则：
         * 第一个页面position变化为[0,-1]
         * 第二个页面position变化为[1,0]
         */
        @Override
        public void transformPage(@NonNull View view, float v) {

            float scale;//view  应缩放的值
            if(v>1||v<-1){
                scale=MINSCALE;
            }else if(v<0){
                scale=MINSCALE+(1+v)*(1-MINSCALE);
            }else{
                scale=MINSCALE+(1-v)*(1-MINSCALE);
            }
            view.setScaleY(scale);
            view.setScaleX(scale);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}

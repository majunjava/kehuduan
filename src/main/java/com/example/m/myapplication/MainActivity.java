package com.example.m.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity implements OnClickListener {
    // 定义Fragment页面
    private FragmentAt fragmentAt;
    private FragmentAuth fragmentAuth;
    private FragmentCenter fragmentCenter;
    private FragmentSpace fragmentSpace;
    private FragmentMore fragmentMore;

    // 定义布局对象
    private FrameLayout at, auth,center, space, more;

    // 定义图片组件对象
    private ImageView atIv, authIv, spaceIv, moreIv;

    // 定义按钮图片组件
    private ImageView tImageView,tImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        // 初始化默认为选中点击了“首页”按钮
        clickAtBtn();
    }
    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        at = findViewById(R.id.layout_at);
        auth=  findViewById(R.id.layout_auth);
        center=findViewById(R.id.layout_center);
        space =  findViewById(R.id.layout_space);
        more= findViewById(R.id.layout_more);

        // 实例化图片组件对象
        atIv =  findViewById(R.id.image_at);
        authIv = findViewById(R.id.image_space);
        spaceIv = findViewById(R.id.image_space);
        moreIv = findViewById(R.id.image_more);
        // 实例化按钮图片组件
        tImageView = findViewById(R.id.toggle_btn);
        tImageView2=findViewById(R.id.plus_btn);

    }
    /**
     * 初始化数据
     */
    private void initData() {
        // 给布局对象设置监听
        at.setOnClickListener(this);
        auth.setOnClickListener(this);
        center.setOnClickListener(this);
        space.setOnClickListener(this);
        more.setOnClickListener(this);

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击动态按钮
            case R.id.layout_at:
                clickAtBtn();
                break;
            // 点击与我相关按钮
            case R.id.layout_auth:
                clickAuthBtn();
                break;
            // 点击我的空间按钮
            case R.id.layout_space:
                clickSpaceBtn();
                break;
            // 点击更多按钮
            case R.id.layout_more:
                clickMoreBtn();
                break;
            // 点击中间按钮
            case R.id.layout_center:
                clickToggleBtn();
                break;
        }
    }

    /**
     * 点击了“首页”按钮
     */
    private void clickAtBtn() {
        // 实例化Fragment页面
      fragmentAt = new FragmentAt();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
//        fragmentTransaction.replace(R.id.frame_content, fragmentAt);
        if(fragmentAt.isAdded()){
            fragmentTransaction.show(fragmentAt);
        }
        else {
            fragmentTransaction.add(R.id.frame_content, fragmentAt);
            fragmentTransaction.show(fragmentAt);
        }
        // 事务管理提交
        fragmentTransaction.commit();
        // 改变选中状态
        at.setSelected(true);
        atIv.setSelected(true);

        auth.setSelected(false);
        authIv.setSelected(false);

        center.setSelected(false);
        tImageView.setSelected(false);

        space.setSelected(false);
        spaceIv.setSelected(false);

        more.setSelected(false);
        moreIv.setSelected(false);
    }

    /**
     * 点击了“动态”按钮
     */
    private void clickAuthBtn() {
       fragmentAuth = new FragmentAuth();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
     fragmentTransaction.replace(R.id.frame_content, fragmentAuth);

        fragmentTransaction.commit();

        at.setSelected(false);
        atIv.setSelected(false);

        auth.setSelected(true);
        authIv.setSelected(true);
        center.setSelected(false);
        tImageView.setSelected(false);

        space.setSelected(false);
        spaceIv.setSelected(false);

        more.setSelected(false);
        moreIv.setSelected(false);
    }
    /**
     * 点击了中间按钮
     */
    private void clickToggleBtn() {
        fragmentCenter = new FragmentCenter();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragmentCenter);
        fragmentTransaction.commit();

        at.setSelected(false);
        atIv.setSelected(false);

        auth.setSelected(false);
        authIv.setSelected(false);

        space.setSelected(false);
        spaceIv.setSelected(false);

        more.setSelected(false);
        moreIv.setSelected(false);

        center.setSelected(true);
        tImageView.setSelected(true);
    }

    /**
     * 点击了“消息”按钮
     */
    private void clickSpaceBtn() {
       fragmentSpace = new FragmentSpace();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.frame_content, fragmentSpace);
        fragmentTransaction.commit();

        at.setSelected(false);
        atIv.setSelected(false);

        auth.setSelected(false);
        authIv.setSelected(false);

        center.setSelected(false);
        tImageView.setSelected(false);

        space.setSelected(true);
        spaceIv.setSelected(true);

        more.setSelected(false);
        moreIv.setSelected(false);
    }
    /**
     * 点击了“我的”按钮
     */
    private void clickMoreBtn() {
       fragmentMore = new FragmentMore();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.frame_content, fragmentMore);
        fragmentTransaction.commit();

        at.setSelected(false);
        atIv.setSelected(false);

        auth.setSelected(false);
        authIv.setSelected(false);

        center.setSelected(false);
        tImageView.setSelected(false);

        space.setSelected(false);
        spaceIv.setSelected(false);

        more.setSelected(true);
        moreIv.setSelected(true);
    }


}

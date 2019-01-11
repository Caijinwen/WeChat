package com.example.administrator.wechat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.wechat.FragmentView.Fragment_chat;
import com.example.administrator.wechat.FragmentView.Fragment_contacts;
import com.example.administrator.wechat.view.MyInfoView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//主界面
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private FrameLayout mBodyLayout;
    private LinearLayout mBottomLayout;
    private RelativeLayout mChatBtn;
    private RelativeLayout mContactsBtn;
    private RelativeLayout mMyInfoBtn;
    private TextView tv_chat;
    private TextView tv_contacts;
    private TextView tv_myInfo;
    private ImageView iv_chat;
    private ImageView iv_contacts;
    private ImageView iv_myinfo;
    static boolean sBoolean=true;
    private String user,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//获取登录界面传过来的用户和名称
        Intent intent=getIntent();
        user = intent.getStringExtra("user");
        name = intent.getStringExtra("name");

        init();
        initBottomBar();
        setListener();
        setInitStatus();



    }
    //初始化状态
    private void setInitStatus() {
        clearBoottomImageState();
        setSelectedStatus(0);
        createView(0);
    }
//监听底部控件
    private void setListener() {
        for (int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

//初始化控件
    private void initBottomBar() {
        mBottomLayout = (LinearLayout) findViewById(R.id.main_bottom_bar);
        mChatBtn = (RelativeLayout) findViewById(R.id.bottom_bar_chat_btn);
        mContactsBtn = (RelativeLayout) findViewById(R.id.bottom_bar_contacts_btn);
        mMyInfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);

        tv_chat = (TextView) findViewById(R.id.bottom_bar_text_chat);
        tv_contacts = (TextView) findViewById(R.id.bottom_bar_text_contacts);
        tv_myInfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);

        iv_chat = (ImageView) findViewById(R.id.bottom_bar_image_chat);
        iv_contacts = (ImageView) findViewById(R.id.bottom_bar_image_contacts);
        iv_myinfo = (ImageView) findViewById(R.id.bottom_bar_image_myinfo);
    }

    //初始化控件
    private void init() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("微信");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        // rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        initBodyLayout();
    }
//初始化控件
    private void initBodyLayout() {
        mBodyLayout = (FrameLayout) findViewById(R.id.main_body);
    }
//底部栏的全部控件监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_bar_chat_btn:
                clearBoottomImageState();
                selectDisplayView(0);
                break;
            case R.id.bottom_bar_contacts_btn:
                clearBoottomImageState();
                selectDisplayView(1);
                break;
            case R.id.bottom_bar_myinfo_btn:
                clearBoottomImageState();
                selectDisplayView(2);
                break;
        }
    }

    private void selectDisplayView(int index) {
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }

    private void setSelectedStatus(int index) {
        switch (index){
            case 0:
                mChatBtn.setSelected(true);
                iv_chat.setImageResource(R.drawable.main_chat_icon_selected);
                tv_chat.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("聊天");
                break;
            case 1:
                mContactsBtn.setSelected(true);
                iv_contacts.setImageResource(R.drawable.main_contacts_icon_selected);
                tv_contacts.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("联系人");
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myinfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("我");
                break;

        }
    }

    private MyInfoView mMyInfoView;

   private Fragment_chat fragment_chat=new Fragment_chat();//new一个聊天碎片
    private Fragment_contacts fragment_contacts=new Fragment_contacts();//new一个联系人碎片

    private void fragment() {
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_body,fragment_chat)
                .add(R.id.main_body,fragment_contacts)//添加碎片
                .hide(fragment_chat)//隐藏碎片
                .hide(fragment_contacts)
                .commit();
    }
    //创建视图
    private void createView(int viewIndex) {
        switch (viewIndex){
            case 0:
                //聊天界面

                if (!sBoolean){

                    this.getSupportFragmentManager().beginTransaction()
                            .hide(fragment_contacts)
                            .show(fragment_chat)
                            .commit();
                }
                if (sBoolean){
                    fragment();
                    this.getSupportFragmentManager()
                            .beginTransaction()
                            .show(fragment_chat)
                            .commit();
                    sBoolean=false;

                }


                break;
            case 1:
               //联系人界面

                if (true){
                    this.getSupportFragmentManager().beginTransaction()
                            .hide(fragment_chat)
                            .show(fragment_contacts)
                            .commit();
                }


                break;
            case 2:
               //我的界面
               if (mMyInfoView==null){
                    mMyInfoView=new MyInfoView(this,name);
                    mBodyLayout.addView(mMyInfoView.getView());//中间添加视图
                }else {
                    mMyInfoView.getView();
                }
                mMyInfoView.showView();
                break;
        }

    }
//删除视图
    private void removeAllView() {
        for (int i=2;i<mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
        this.getSupportFragmentManager().beginTransaction().hide(fragment_chat).commit();
    }
//清除状态
    private void clearBoottomImageState() {
        tv_chat.setTextColor(Color.parseColor("#666666"));
        tv_contacts.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));

        iv_chat.setImageResource(R.drawable.main_chat_icon);
        iv_contacts.setImageResource(R.drawable.main_contacts_icon);
        iv_myinfo.setImageResource(R.drawable.main_my_icon);

        for (int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }


    public String Date_User(){
        return user;
    }
    public String Date_Name(){
        return name;
    }
}

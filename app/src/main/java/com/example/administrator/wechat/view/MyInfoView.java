package com.example.administrator.wechat.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.wechat.R;


public class MyInfoView {
    //
    private Context mContext;
    private final LayoutInflater mInflater;
    private View mCurrentView;
    private LinearLayout ll_head;
    private ImageView iv_head_icon;
    private RelativeLayout rl_wallet;
    private RelativeLayout rl_setting;
    private TextView tv_user_name;

    private String name;

    public MyInfoView(Context mContext,String name) {
        this.name=name;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    public View getView(){
        if (mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }
//创建视图
    private void createView() {
        initView();
    }

    //初始化控件
    private void initView(){
       mCurrentView = mInflater.inflate(R.layout.main_view_myinfo, null);
       ll_head = (LinearLayout) mCurrentView.findViewById(R.id.ll_head);
       iv_head_icon = (ImageView) mCurrentView.findViewById(R.id.iv_head_icon);

        rl_wallet = (RelativeLayout) mCurrentView.findViewById(R.id.rl_wallet);
       rl_setting = (RelativeLayout) mCurrentView.findViewById(R.id.rl_setting);
       tv_user_name = (TextView) mCurrentView.findViewById(R.id.tv_user_name);
       tv_user_name.setText(name);

   }

//显示视图
   public void showView() {
        if (mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}

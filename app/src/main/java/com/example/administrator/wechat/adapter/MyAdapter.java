package com.example.administrator.wechat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wechat.R;
import com.example.administrator.wechat.been.UserChatMessage;

import java.util.List;


public class MyAdapter extends BaseAdapter {

    private List<UserChatMessage> list;
    private Context context;
    private String name;

    public MyAdapter(List<UserChatMessage> list, Context context, String name) {
        this.list = list;
        this.context = context;
        this.name=name;
    }

   public   void setData(List<UserChatMessage> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=View.inflate(context, R.layout.item,null);
        TextView tv_left_name=view.findViewById(R.id.tv_left_name);
        TextView tv_rigth_name=view.findViewById(R.id.tv_rigth_name);
        ImageView iv_left_img=view.findViewById(R.id.iv_left_img);
        ImageView iv_right_img=view.findViewById(R.id.iv_right_img);
        TextView tv_chat=view.findViewById(R.id.tv_chat);
        TextView tv_date=view.findViewById(R.id.tv_date);

        if (!name.equals(list.get(i).getName())) {
            tv_left_name.setText(list.get(i).getName());
            iv_left_img.setVisibility(View.VISIBLE);
            iv_right_img.setVisibility(View.GONE);
        }else{
            tv_rigth_name.setText(list.get(i).getName());
            iv_right_img.setVisibility(View.VISIBLE);
            iv_left_img.setVisibility(View.GONE);
        }
        tv_chat.setText(list.get(i).getChat());
        tv_date.setText(list.get(i).getDate());
        return view;
    }

}

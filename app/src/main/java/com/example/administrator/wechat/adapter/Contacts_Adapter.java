package com.example.administrator.wechat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.wechat.R;
import com.example.administrator.wechat.been.ContactsMessage;

import java.util.List;

public class Contacts_Adapter extends BaseAdapter {

   private List<ContactsMessage> lists;
   private Context context;
    public Contacts_Adapter(List<ContactsMessage> lists,Context context) {
        this.lists=lists;
        this.context=context;

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context,R.layout.list_contacts_title,null);
        TextView tv_contacts_name = (TextView) convertView.findViewById(R.id.tv_contacts_name);
        TextView tv_contacts_user = (TextView) convertView.findViewById(R.id.tv_contacts_user);
        tv_contacts_name.setText(lists.get(position).getName());
        tv_contacts_user.setText(lists.get(position).getUser());
        return convertView;
    }
}

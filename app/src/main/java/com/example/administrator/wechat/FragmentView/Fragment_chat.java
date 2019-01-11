package com.example.administrator.wechat.FragmentView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.wechat.MainActivity;
import com.example.administrator.wechat.R;
import com.example.administrator.wechat.adapter.MyAdapter;
import com.example.administrator.wechat.been.UserChatMessage;
import com.example.administrator.wechat.utils.HttpUtil;
import com.example.administrator.wechat.utils.StreamTool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragment_chat extends android.support.v4.app.Fragment{

    private String content,user,name;
    private View mCurrentView;
    private EditText etInput;
    private Button btnSend;
    private List<UserChatMessage> list;
    private ListView lvTable ;
    private MyAdapter myAdapter;

    //Handler处理消息
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            myAdapter.setData((List<UserChatMessage>)msg.obj);
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         mCurrentView = inflater.inflate(R.layout.fragment_chat, container, false);
            initView();
        return mCurrentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        user = ((MainActivity) context).Date_User();
        name = ((MainActivity) context).Date_Name();

    }

    //获取碎片布局中的控件，发信息控件监听
    private void initView(){

        etInput = mCurrentView.findViewById(R.id.et_input);
        btnSend = mCurrentView.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = etInput.getText().toString().trim();
                etInput.setText("");
                new Thread(send).start();
            }
        });
        list=new ArrayList<>();
        lvTable =mCurrentView.findViewById(R.id.lv_table);
        myAdapter=new MyAdapter(list,getActivity(),name);
        lvTable.setAdapter(myAdapter);
    }

    Runnable send = new Runnable() {
        public void run() {

            String path = new HttpUtil("chat1.php").getUrl;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                String outData =
                        "user=" + URLEncoder.encode(user)
                                + "&chat=" + URLEncoder.encode(content);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(outData.getBytes());
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream in = conn.getInputStream();

                    byte[] data = StreamTool.read(in);
                    String json = new String(data, "UTF-8");
                    parseJson(json);
                    new Thread(){
                        @Override
                        public void run() {
                            Message message =new Message();
                            message.obj=list;
                            mHandler.sendMessage(message);
                        }
                    }.start();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "连网失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

//解析JSON对象
    public void parseJson(String json) {
        String status = "";
        list.clear();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                UserChatMessage userChatMessage=new UserChatMessage();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                userChatMessage.setDate(jsonObject.getString("time"));
                userChatMessage.setChat(jsonObject.getString("chat"));
                userChatMessage.setName(jsonObject.getString("name"));
                list.add(userChatMessage);
            }
        }catch (Exception e){e.printStackTrace();}
        Collections.reverse(list);
    }

}

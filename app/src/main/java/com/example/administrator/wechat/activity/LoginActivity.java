package com.example.administrator.wechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wechat.MainActivity;
import com.example.administrator.wechat.R;
import com.example.administrator.wechat.utils.HttpUtil;
import com.example.administrator.wechat.utils.StreamTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText etUser;
    private EditText etPassword;
    private String user,password,name;
    private TextView tvNext,tv_main_title,tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();         //初始化控件
    }

    private void init() {
        tv_back=findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tvNext=findViewById(R.id.tv_next);
        tvNext.setText("注册");
        btnLogin=findViewById(R.id.btn_login);
        tv_main_title =findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        btnLogin.setOnClickListener(this);
        etUser=findViewById(R.id.et_user);
        etPassword=findViewById(R.id.et_password);
        tvNext.setOnClickListener(this);
    }

    //注册和登录监听
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.btn_login:
                data();
                break;
        }

    }

    //获取用户和密码
    private void data() {
        user=etUser.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        if (user.isEmpty()){

        }else if (password.isEmpty()){

        }else {
            new Thread(login).start();
        }

    }
    Runnable login=new Runnable() {
        @Override
        public void run() {
            String path = new HttpUtil("login.php").getUrl;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                String outData ="user="+URLEncoder.encode(user)
                        +"&password="+URLEncoder.encode(password);
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setDoOutput(true);
                OutputStream os= conn.getOutputStream();
                os.write(outData.getBytes());
                int code = conn.getResponseCode();
                if (code == 200){
                    InputStream in = conn.getInputStream();
                    byte[] data = StreamTool.read(in);
                    String json = new String(data, "UTF-8");
                    final String status = parseJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, status, Toast.LENGTH_SHORT).show();
                            if (status.equals("登陆成功")){
                            startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("user",user).putExtra("name",name));
                            finish();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //解析JSON对象
    public String parseJson(String json){
        String status="";
        try {
            JSONObject jsonObject = new JSONObject(json);
            status = jsonObject.getString("status");
            user = jsonObject.getString("user");
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

}

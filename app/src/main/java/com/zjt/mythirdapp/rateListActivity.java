package com.zjt.mythirdapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

public class rateListActivity extends ListActivity implements Runnable {

    TextView res;
    String TAG="main";

    Handler handler;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



        //开启子线程
        Thread t=new Thread(this);
        //等待开始运行的代码
        t.start();

        handler = new Handler(){
            @Override
            public  void handleMessage(Message msg){
                if(msg.what==5){
                    List<String> rateList = (List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(rateListActivity.this,android.R.layout.simple_list_item_1,rateList);
                    setListAdapter(adapter);


                }
            }
        };





    }


    public void run(){


        List<String> list1=new ArrayList<String>();
        try{
            String url="http://www.usd-cny.com/";
            Document doc= Jsoup.connect(url).get();
            Log.i("thread","run:"+doc.title());
            Elements tables=doc.getElementsByTag("table");
            Element table=tables.get(0);
            Elements tds=table.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=5){
                Element td1=tds.get(i);
                Element td2=tds.get(i+1);

                String name=td1.text();
                String value=td2.text();

                list1.add(name+"==>"+value);
                Log.i("结果","run:  "+name + "==>"+value);




            }
        }catch (IOException e){

        }

        //获取message对象，用于传递给主线程
        Message msg=handler.obtainMessage(5);
        msg.obj=list1;
        handler.sendMessage(msg);

    }

}

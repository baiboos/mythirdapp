package com.zjt.mythirdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rateList2Activity extends ListActivity implements Runnable{
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list2);

        //开启子线程
        Thread t=new Thread(this);
        //等待开始运行的代码
        t.start();

        handler = new Handler(){
            @Override
            public  void handleMessage(Message msg){
                if(msg.what==5){
                    ArrayList<HashMap<String,String>> listItems = ( ArrayList<HashMap<String,String>>)msg.obj;
                    /*ListAdapter adapter=new SimpleAdapter(rateList2Activity.this,listItems,R.layout.list_item,new String[]{"ItemName","ItemValue"},new int[]{R.id.ItemTitle, R.id.ItemDetail});
                    setListAdapter(adapter);*/
                    MyAdapter myAdapter = new MyAdapter(rateList2Activity.this,R.layout.list_item,listItems);
                    setListAdapter(myAdapter);


                }
            }
        };
    }

    public void run(){


        List<HashMap<String,String>> list1=new ArrayList<HashMap<String,String>>();
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

                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemName",name);
                map.put("ItemValue",value);

                list1.add(map);

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


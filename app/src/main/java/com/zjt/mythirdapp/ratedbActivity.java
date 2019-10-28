package com.zjt.mythirdapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ratedbActivity extends ListActivity implements Runnable {
    Handler handler;
    private static final String TAG = "ratedb";
    private String logDate = "" ;
    private final String DATE_SP_KEY = "lastRateDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY,"");
        Log.i(TAG,"lastRateDate="+logDate);

        //开启子线程
        Thread t = new Thread(this);
        //等待开始运行的代码
        t.start();



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    //RateManager manager = new RateManager(ratedbActivity.this);
                    List<String> rateList = (List<String>)msg.obj;

                    ListAdapter adapter=new ArrayAdapter<String>(ratedbActivity.this,android.R.layout.simple_list_item_1,rateList);
                    setListAdapter(adapter);



                }
            }
        };
    }

    public void run() {
        List<String> list1 = new ArrayList<String>();

        String curDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.i("run","current date is"+curDateStr+"log Date is:"+logDate);

        if(curDateStr.equals(logDate)){
            Log.i(TAG,"日期相等，从数据库获得数据");
            RateManager manager = new RateManager(this);
            for(RateItem item:manager.listAll()){
                list1.add(item.getCurName()+"-->"+item.getCurRate());
            }
        }
        else {
            Log.i(TAG,"日期不相等，从数据库获得数据");
            try {
                String url = "http://www.usd-cny.com/";
                Document doc = Jsoup.connect(url).get();
                Log.i("thread", "run:" + doc.title());
                Elements tables = doc.getElementsByTag("table");
                Element table = tables.get(0);
                Elements tds = table.getElementsByTag("td");

                List<RateItem> list2 = new ArrayList<RateItem>();
                for (int i = 0; i < tds.size(); i += 5) {
                    //RateItem item = new RateItem();
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i + 1);

                    String name = td1.text();
                    String value = td2.text();

              /*  HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemName", name);
                map.put("ItemValue", value);*/

                    list1.add(name+"=>"+value);
                    list2.add(new RateItem(name,value));



                    Log.i("结果", "run:  " + name + "==>" + value);


                }
                //把数据写入到数据库中
                RateManager manager = new RateManager(this);
                manager.deleteAll();
                manager.addAll(list2);

                //更新记录日期
                SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(DATE_SP_KEY,curDateStr);
                editor.commit();
                Log.i(TAG,"更新日期结束");
            } catch (IOException e) {

            }
        }


        //获取message对象，用于传递给主线程
        Message msg = handler.obtainMessage(5);
        msg.obj = list1;
        handler.sendMessage(msg);

    }
}

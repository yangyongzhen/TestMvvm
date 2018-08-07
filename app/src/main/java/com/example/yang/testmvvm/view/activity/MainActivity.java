package com.example.yang.testmvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yang.testmvvm.R;
import com.example.yang.testmvvm.database.RecordApi;
import com.example.yang.testmvvm.database.table.Record01;
import com.example.yang.testmvvm.model.bean.LoginType;
import com.example.yang.testmvvm.viewmodel.MyViewModel;

import static com.example.yang.testmvvm.app.App.sysCfg;

public class MainActivity extends AppCompatActivity {

    private MyViewModel myViewModel;
    private Thread mThread;
    private int count = 0;
    private boolean RUN = true;

    Button btn1,btn2;
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        edit1 = findViewById(R.id.editText);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getName().observe(this, name -> {
            // update UI
            edit1.setText(name);
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String anotherName = "John Doe";
                //myViewModel.getName().setValue(anotherName);
                //myViewModel.loginmodel.login("qq8864","123456", LoginType.TYPE_LOGIN);
                Record01 rec = new Record01();
                rec.recType = 1;//赋值
                RecordApi api = RecordApi.getInstance();
                api.saveRec01(rec);//存储
                long unrec = api.getRecUnNum();//获取未上传记录数
                Record01 rdrec;
                Log.d("un send rec num:",String.valueOf(unrec));
                //依此上传未上传记录
                for(int i =0 ;i < unrec;i++){
                    rdrec = api.readRecNotServer(1+i);
                    Log.d("recDebug:",rdrec.toString());
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mThread = new LongTimeWork();
                //mThread.start();
                /*
                sysCfg.ver.value = "1234";
                sysCfg.ip.value = "218.28.133.181";
                sysCfg.saveConfig();
                sysCfg.printConfig();
                sysCfg.ver.value = "5678";
                sysCfg.saveConfig();
                sysCfg.printConfig();
                */
                RecordApi api = RecordApi.getInstance();
                api.decRec01(1);
                Log.d("del rec:",String.format("curwriteid:%d,curreadid:%d",api.recDir01.getCurWriteID(),api.recDir01.getCurReadID()));

            }
        });

    }

    private class LongTimeWork extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    if (!RUN) {
                        Thread.sleep(Long.MAX_VALUE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                count++;
                myViewModel.getName().postValue(String.valueOf(count));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

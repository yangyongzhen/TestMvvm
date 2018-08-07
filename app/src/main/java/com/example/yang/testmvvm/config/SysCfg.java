package com.example.yang.testmvvm.config;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyongzhen on 2018/08/04
 * 配置文件的全局管理，摒弃SharedPreference漫天飞
 * 系统配置SysCfg类
 * 在应用启动时，先初始化调用 SysCfg sysCfg = SysCfg.getInstance(context);
 * 接着sysCfg.readConfig();完成加载。
 * 然后全局的配置就可以使用，如sysCfg.ip.value即为IP地址的配置。
 * sysCfg.ver.value = "5678";
 * sysCfg.saveConfig();
 * 即完成了参数的保存。
 * 相比工程代码中到处穿梭的
 * SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
 * userId = sp.getInt(SettingsUtil.USERID, -1);
 * 是不是更清晰？更简单？更直观？
 */
public final class SysCfg {
    public static final String TAG = "SysCfg"; //做为配置文件的文件名

    private static Context context;
    private static SysCfg instance = null;
    SharedPreferencesHelper helper;

    public class Field{
        public String key;
        public String value;
        public Field(){
            key   = "null";
            value = "null";
        }
    }
    List<Field> list;
    //fields,步骤1，用到的配置
    public  Field ver = new Field(); //版本
    public  Field ip =  new Field(); //IP地址
    public  Field port= new Field(); //端口
    public  Field username = new Field(); //用户名
    public  Field password = new Field(); //密码

    /**
     * 需要增加或删除的变量配置都在这里修改
     * @param contx
     */
    private SysCfg(Context contx) {
        context = contx;
        helper = new SharedPreferencesHelper(context,TAG);
        list = new ArrayList<>();

        //步骤2:配置key
        ver.key  = "ver";
        ip.key   = "ip";
        port.key = "port";
        username.key = "username";
        password.key = "password";

        //步骤3:添加到list
        list.add(ver);
        list.add(ip);
        list.add(port);
        list.add(username);
        list.add(password);
    }

    /**
     * 保存参数时只需赋值，然后调用saveConfig()即可。
     * @return
     */
    public int saveConfig(){
        for(Field filed :list){
            helper.put(filed.key,filed.value);
        }
        return 0;
    }

    /**
     *仅在应用启动时加载一次，调用readConfig();
     * @return
     */
    public int readConfig(){
        for(Field filed :list){
            helper.get(filed.key,filed.value);
        }
        return 0;
    }
    public void printConfig(){
        String str="";
        for(Field filed :list){
            str += String.format("key=%s,value= %s\n",filed.key,filed.value);
        }
        Log.d(TAG, str);
    }

    public static SysCfg getInstance(Context contx){
        if(instance == null){
            instance = new SysCfg(contx);
        }
        return instance;
    }
}

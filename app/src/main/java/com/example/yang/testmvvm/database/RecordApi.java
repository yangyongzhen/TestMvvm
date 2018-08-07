package com.example.yang.testmvvm.database;

import android.content.Context;
import android.util.Log;

import com.example.yang.testmvvm.app.App;
import com.example.yang.testmvvm.database.table.Record01;
import com.example.yang.testmvvm.database.table.RecordDir;

import org.litepal.LitePal;

/**
 * Created by yangyongzhen on 2018/08/07
 * 实现记录的常用操作接口:记录存储，查询未上传记录数，读取未上传记录，删除记录
 */
public class RecordApi {

    public RecordDir recDir01;

    private static Context context;
    private static RecordApi instance = null;

    private RecordApi(Context contxt){
        context = contxt;
        recDir01 = LitePal.find(RecordDir.class, 1);
        if(recDir01 == null){
            recDir01 = new RecordDir();
            recDir01.setWriteID(0);
            recDir01.setReadID1(0);
            recDir01.setRecNO(0);
            recDir01.setUpDateFlag(0);
            recDir01.save();
        }

    }

    /**
     * 保存记录
     * @param rec
     * @return
     */
    public int saveRec01( Record01 rec){
        if(rec == null){
            return 1;
        }
        if(recDir01.getWriteID()+1 > RecordDir.MaxRecNO){
            if((recDir01.getWriteID() + 1 - RecordDir.MaxRecNO) == recDir01.getReadID1()){
                return 2;//记录满
            }
            recDir01.setWriteID(1);
            recDir01.setUpDateFlag(1);
            rec.update(1);
        }
        else {
            if(recDir01.getUpDateFlag() == 1){
                if((recDir01.getWriteID() + 1) == recDir01.getReadID1()){
                    return 3;//记录满
                }
                rec.update(recDir01.getWriteID()+1);
                recDir01.setCurWriteID(recDir01.getWriteID()+1);
                recDir01.setRecNO(recDir01.getRecNO() + 1);
                recDir01.setWriteID(recDir01.getWriteID() + 1);
                recDir01.update(1);
            }
            else {
                rec.save();
                recDir01.setRecNO(recDir01.getRecNO() + 1);
                recDir01.setWriteID(recDir01.getWriteID() + 1);
                recDir01.setCurWriteID(recDir01.getWriteID()+1);
                recDir01.update(1);
            }
        }
        Log.d("WriteRec:",recDir01.toString());
        return 0;
    }

    /**
     * 删除记录，实际上只更改记录目录表的读指针，并不删除记录表的数据
     * 记录表的数据采取循环存储，循环覆盖的模式，保证安全性
     * @param recnum
     * @return
     */
    public int decRec01(long recnum){

        long id = recDir01.getReadID1();
        if(recDir01.getWriteID() == recDir01.getReadID1()){
            return 0;
        }
        if((id + recnum) > RecordDir.MaxRecNO){
            if((id + recnum - RecordDir.MaxRecNO) > recDir01.getWriteID() ){
                recDir01.setReadID1(recDir01.getWriteID());
                recDir01.setCurReadID(recDir01.getWriteID());
                recDir01.update(1);
                return 0;
            }
            recDir01.setReadID1(id + recnum - RecordDir.MaxRecNO);
            recDir01.setCurReadID(id + recnum - RecordDir.MaxRecNO);
            recDir01.update(1);
        }else {
            if(recDir01.getWriteID() > recDir01.getReadID1()){
                if(id + recnum > recDir01.getWriteID()){
                    recDir01.setReadID1(recDir01.getWriteID());
                    recDir01.setCurReadID(recDir01.getWriteID());
                    recDir01.update(1);
                    return 0;
                }
            }
            recDir01.setReadID1(id + recnum);
            recDir01.setCurReadID(id + recnum);
            recDir01.update(1);
        }
        return 0;
    }

    /**
     * 获取未上传的记录条数
     * @return
     */
    public long getRecUnNum()
    {
        long num = 0;
        if(recDir01.getWriteID() == recDir01.getReadID1()){
            num = 0;
            return num;
        }
        if(recDir01.getUpDateFlag() == 0){
            num = (recDir01.getWriteID() - recDir01.getReadID1());
        }else{
            if(recDir01.getWriteID() > recDir01.getReadID1()){
                num = (recDir01.getWriteID() - recDir01.getReadID1());
            }else{
                num = RecordDir.MaxRecNO - recDir01.getReadID1() + recDir01.getWriteID();
            }
        }
        return num;
    }

    /**
     * 读取未上传的记录数据，顺序读取
     * sn取值 1-到-->未上传记录数目
     * @param sn
     * @return
     */
    public Record01 readRecNotServer(long sn){

        Record01 rec;
        long id = recDir01.getReadID1();
        if((id + sn) > RecordDir.MaxRecNO){
            if(id + sn - RecordDir.MaxRecNO > recDir01.getWriteID()){
                return null;
            }
            rec = LitePal.find(Record01.class, id + sn - RecordDir.MaxRecNO );
        }else {
            if(recDir01.getReadID1() < recDir01.getWriteID()){
                if((id + sn) > recDir01.getWriteID()){
                    return null;
                }
            }
            rec = LitePal.find(Record01.class, recDir01.getReadID1() + sn);

        }
        return rec;
    }

    public static RecordApi getInstance(){
        if(instance == null){
            instance = new RecordApi(App.getContext());
        }
        return instance;
    }
}

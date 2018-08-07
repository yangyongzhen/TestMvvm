package com.example.yang.testmvvm.database.table;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * Created by yangyongzhen on 2018/08/07
 *
 * RecordDir，记录表对应的目录表，用来对记录表进行管理。
 * RecordDir表，记录了当前记录的写的位置及记录读的位置。
 * 可据此实现，查询未上传记录数量，依次上传未上传记录，
 * 记录顺序存储，存满指定容量后从头覆盖存储的方式。
 * 删除记录操作只更新记录目录表的读的位置，从不真正的从记录表删除数据，保证数据的安全性。
 */
public class RecordDir extends LitePalSupport {
    @Column(nullable = false)
    //Litepal数据库自动生成的自增的ID
    private long recNO;
    private long writeID;
    private long readID1;
    private long readID2;
    private long readID3;
    private int  mode;

    private int  upDateFlag;
    private long curWriteID;
    private long curReadID;
    @Column(ignore = true)
    public static final long MaxRecNO = 10;

    public long getRecNO() {
        return recNO;
    }

    public long getWriteID() {
        return writeID;
    }

    public long getReadID1() {
        return readID1;
    }

    public long getReadID2() {
        return readID2;
    }

    public long getReadID3() {
        return readID3;
    }

    public long getCurReadID() {
        return curReadID;
    }

    public int getMode() {
        return mode;
    }
    public long getCurWriteID() {
        return curWriteID;
    }

    public void setRecNO(long recNO) {
        this.recNO = recNO;
    }

    public void setWriteID(long writeID) {
        this.writeID = writeID;
    }

    public void setReadID1(long readID1) {
        this.readID1 = readID1;
    }

    public void setReadID2(long readID2) {
        this.readID2 = readID2;
    }

    public void setReadID3(long readID3) {
        this.readID3 = readID3;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setCurWriteID(long curWriteID) {
        this.curWriteID = curWriteID;
    }
    public void setCurReadID(long curReadID) {
        this.curReadID = curReadID;
    }

    public int getUpDateFlag() {
        return upDateFlag;
    }

    public void setUpDateFlag(int upDateFlag) {
        this.upDateFlag = upDateFlag;
    }

    @Override
    public String toString() {
        return "RecordDir{" +
                "recNO=" + recNO +
                ", writeID=" + writeID +
                ", readID1=" + readID1 +
                ", readID2=" + readID2 +
                ", readID3=" + readID3 +
                ", mode=" + mode +
                ", upDateFlag=" + upDateFlag +
                ", curWriteID=" + curWriteID +
                ", curReadID=" + curReadID +
                '}';
    }
}

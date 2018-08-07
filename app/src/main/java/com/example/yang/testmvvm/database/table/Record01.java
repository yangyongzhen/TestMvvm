package com.example.yang.testmvvm.database.table;

import org.litepal.crud.LitePalSupport;

/**
 * Created by yangyongzhen on 2018/08/07
 * 记录表01，记录存储区一
 * 如果有多种记录，依此格式分别建立不同的记录表。
 */
public class Record01 extends LitePalSupport {
    //Litepal数据库自动生成的自增的ID
    private long id;
    public long physn;
    public long recType;
    public String psamTid;
    public long lineID;
    public String dealTime;
    public String cardAsn;
    public long dealMoney;
    public long oldBalance;

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Record01{" +
                "id=" + id +
                ", physn=" + physn +
                ", recType=" + recType +
                ", psamTid='" + psamTid + '\'' +
                ", lineID=" + lineID +
                ", dealTime='" + dealTime + '\'' +
                ", cardAsn='" + cardAsn + '\'' +
                ", dealMoney=" + dealMoney +
                ", oldBalance=" + oldBalance +
                '}';
    }
}

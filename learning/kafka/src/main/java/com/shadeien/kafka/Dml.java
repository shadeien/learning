package com.shadeien.kafka;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DML操作转换对象
 *
 * @author rewerma 2018-8-19 下午11:30:49
 * @version 1.0.0
 */
public class Dml implements Serializable {

    private static final long         serialVersionUID = 2611556444074013268L;

    private String                    destination;                            // 对应canal的实例或者MQ的topic
    private String                    database;                               // 数据库或schema
    private String                    table;                                  // 表名
    private String                    type;                                   // 类型: INSERT UPDATE DELETE
    // binlog executeTime
    private Long                      es;                                     // 执行耗时
    // dml build timeStamp
    private Long                      ts;                                     // 同步时间

    private Long                      cpt;                                     // canal server 拉取到的时间
    private Long                      qt;                                     // 写队列的时间


    private String                    sql;                                    // 执行的sql, dml sql为空
    private List<Map<String, Object>> data;                                   // 数据列表
    private List<Map<String, Object>> old;                                    // 旧数据列表, 用于update, size和data的size一一对应


    public Long getCpt() {
        return cpt;
    }

    public void setCpt(Long cpt) {
        this.cpt = cpt;
    }

    public Long getQt() {
        return qt;
    }

    public void setQt(Long qt) {
        this.qt = qt;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public List<Map<String, Object>> getOld() {
        return old;
    }

    public void setOld(List<Map<String, Object>> old) {
        this.old = old;
    }

    public Long getEs() {
        return es;
    }

    public void setEs(Long es) {
        this.es = es;
    }

    public void clear() {
        database = null;
        table = null;
        type = null;
        ts = null;
        es = null;
        data = null;
        old = null;
        sql = null;
    }

    @Override
    public String toString() {
        return "Dml{" + "destination='" + destination + '\'' + ", database='" + database + '\'' + ", table='" + table
               + '\'' + ", type='" + type + '\'' + ", es=" + es + ", ts=" + ts + ", sql='" + sql + '\'' + ", data="
               + data + ", old=" + old + '}';
    }
}

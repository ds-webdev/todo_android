package com.example.dsiko.dawidsikorski_todo.Model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressLint("NewApi")
public class Task implements Serializable {

    private Integer id;
    private String name;
    private String desc;
    private String status;
    private String priority;
    private LocalDateTime create_date;
    private LocalDateTime end_date;

    public Task(Integer id, String name, String desc, String status, String priority, LocalDateTime create_date, LocalDateTime end_date){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.priority = priority;
        this.create_date =  create_date;
        this.end_date = end_date;
    }

    public Task(String name, String desc, String status, String priority, LocalDateTime create_date){
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.priority = priority;
        this.create_date =  create_date;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public String getPriority(){
        return this.priority;
    }

    public LocalDateTime  getCreateDate(){
        return this.create_date;
    }

    public LocalDateTime getEndDate(){
        return this.end_date;
    }

    public void setEndDate(LocalDateTime localDateTime){
        this.end_date = localDateTime;
    }

    public Integer getId(){
        return this.id;
    }

}

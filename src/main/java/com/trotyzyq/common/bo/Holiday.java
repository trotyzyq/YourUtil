package com.trotyzyq.common.bo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Holiday {
    /** 日期 **/
    private String date;

    private Timestamp dateTime;

    /** 1 放假 2补班 **/
    private Integer status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return Objects.equals(date, holiday.date) &&
                Objects.equals(status, holiday.status);
    }

    @Override
    public int hashCode() {
        if(this.date != null){
            return this.date.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "date='" + date + '\'' +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }

    public static void main(String[] args) {
        Holiday holiday = new Holiday();
        holiday.setDate("fdsfasdfasdfasdfasdf");
        Holiday holiday1 = new Holiday();
        holiday1.setDate("fdsfasdfasdfasdfasdf");
        Set set = new HashSet<>();
        set.add(holiday);
        set.add(holiday1);
        System.out.println(set);
    }
}

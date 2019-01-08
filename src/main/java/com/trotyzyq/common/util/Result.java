package com.trotyzyq.common.util;

/**
 * Created by trotyzyq on 2018/8/16.
 */
public class Result<Treq, Tresp>{

    private String code = "1";
    private String message  = "success";

    private Treq   reqBean;

    private Tresp  respBean;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Treq getReqBean() {
        return reqBean;
    }

    public void setReqBean(Treq reqBean) {
        this.reqBean = reqBean;
    }

    public Tresp getRespBean() {
        return respBean;
    }

    public void setRespBean(Tresp respBean) {
        this.respBean = respBean;
    }

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Result(String code, String msg, Treq reqBean) {
        this.code = code;
        this.message = msg;
        this.reqBean = reqBean;
    }

    public Result(String code, String msg, Treq reqBean, Tresp respBean) {
        this.code = code;
        this.message = msg;
        this.reqBean = reqBean;
        this.respBean = respBean;
    }

    public Result success(){
        return new Result(code,message);
    }

    public Result success(Treq reqBean, Tresp respBean ){
        return new Result(code,message,reqBean,respBean);
    }

    public Result success(Treq reqBean){
        return new Result(code,message,reqBean);
    }


}

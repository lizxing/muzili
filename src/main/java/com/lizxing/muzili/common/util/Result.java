package com.lizxing.muzili.common.util;

import com.lizxing.muzili.common.constant.ResultConstant;

import java.util.HashMap;

/**
 * @author lizxing
 * @date 2021/8/12
 */
public class Result extends HashMap<String, Object> {

    private Result(int code, String msg){
        this.put("code", code);
        this.put("msg", msg);
    }

    public static  Result ok() {
        return new Result(ResultConstant.SUCCESS, null);
    }

    public static  Result ok(String msg) {
        return new Result(ResultConstant.SUCCESS, msg);
    }

    public static  Result failed() {
        return new Result(ResultConstant.ERROR, null);
    }

    public static  Result failed(String msg) {
        return new Result(ResultConstant.ERROR, msg);
    }

    public Result data(Object data) {
        this.put("data", data);
        return this;
    }

    public Result data(String key, Object data) {
        this.put(key, data);
        return this;
    }

}

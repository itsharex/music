package com.music.common;

/**
 * @author lyj
 * @data 2022/6/20 14:24
 */
public enum CommonCode implements ErrorCode{
    /**
     * TODO
     *  success表示成功，faild表示失败,unauthorized表示没有登录，forbidden表示账号不可用
     * @Param
     * @return
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    FORBIDDEN(403, "账号不可用");

    private long code;
    private String message;

    private CommonCode(long code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

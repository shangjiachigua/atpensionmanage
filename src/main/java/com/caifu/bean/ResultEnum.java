package com.caifu.bean;

/**
 * @Auther: Lyf
 * @Date: 2020/7/2 13:27
 * @Description:
 */
public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(-1,"未知错误！"),
    SUCCESS(200,"成功！"),
    GETDATASUCCESS(200,"获取数据成功！"),
    DATA_IS_NULL(101,"数据为空！"),
    DATA_IS_WRONGFUL(102,"数据不合法！"),
    ERROR(9999,"系统错误！"),
    TOKEN_ERROR(9991,"无效token！"),
    PARAMETER_ERROR(9992,"参数错误！"),
    PAGE_LIST(0,"成功"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

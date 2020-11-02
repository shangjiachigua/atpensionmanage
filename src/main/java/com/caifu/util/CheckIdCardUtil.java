package com.caifu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 验证身份证号码 身份证号码, 可以解析身份证号码的各个字段，
 * 以及验证身份证号码是否有效; 身份证号码构成：6位地址编码+8位生日+3位顺序码+1位校验码
 *
 * @author
 * @ClassName: CheckIdCardUtil
 * @Description: TODO
 */
public class CheckIdCardUtil {


    /**
     * 简单检验身份证
     *
     * @param idCard
     * @return
     * @throws Exception
     */
    public static boolean check(String idCard)  {
        if (idCard == null) {
            return false;
        }
        String regex = "\\d{15}(\\d{2}[0-9xX])?";
        if (idCard.matches(regex)) {
            return true;
        } else {
            return false;

        }
    }


    /**
     * 根据身份证号获取年龄
     * @param certId
     * @return
     */
    public static Integer getAgeByCertId(String certId) {
        String birthday = "";
        if (certId.length() == 18) {
            birthday = certId.substring(6, 10) + "/"
                    + certId.substring(10, 12) + "/"
                    + certId.substring(12, 14);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        Date birth = new Date();
        try {
            birth = sdf.parse(birthday);
        } catch (ParseException e) {
        }
        long intervalMilli = now.getTime() - birth.getTime();
        int age = (int) (intervalMilli/(24 * 60 * 60 * 1000))/365;
        return age ;
    }


    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    private static boolean isDigital(String str) {
        return str.matches("^[0-9]*$");
    }


    public static String getBirthDayPart(String cardNumber) {
        return cardNumber.substring(6, 14);
    }

    /**
     * 通过身份证查性别
     * @param identityNumber identityNumber
     * @return 性别 1-男 2-女
     */
    public static String getSexByIdentityNumber(String identityNumber) {
        String sex = "";
        if (Integer.parseInt(identityNumber.substring(16).substring(0, 1)) % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }


    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isChinaPhoneLegal(""));
    }
}
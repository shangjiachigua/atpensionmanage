package com.caifu.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Constance {
public final static Logger logger= LoggerFactory.getLogger(Constance.class);


	/** 微信开发使用的参数  **/
	//获取微信accessToken接口
	public static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//自定义菜单
	public static final String SELF_MAKE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//未关注用户获取用户信息
	public static final String SELF_MESSAGE_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//已关注获取用户基本信息
	public static final String OBTAIN_USER_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//获取网页授权码
	public static final String WEB_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//获取jspTicket
	public static final String JSAPI_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	//长征大屏地图数据接口
	public static final String CHANGZHENG_MAP_INTERFACE="http://cztest.ptzhcz.cn:9063/CFBusinessInterface/";

	//
	public static final String LOGIN_URL ="http://218.78.49.18:6060/SilverMountain/login";

	//用户信息对象Session
	public static final String WEBUSERINFO_SESSION="webUserInfo";  //目前使用
	public static final String OPEN_ID="openId";  //目前使用
	public static final String WECHAT_INFO_SESSION="weChatInfo";  //微信信息session

	public static final String TELLER_LOGIN_INFO_SESSION="teller_login_info_session";
	public static final String ENTERPRISE_LOGIN_INFO_SESSION="enterprise_login_info_session";
	
	public final static int PAGE_SIZE_TEN=10;
	public final static int PAGE_SIZE_FIFTEEN=15;
	public final static int PAGE_SIZE_TWENTY=20;

	//彩付人生测试账号
//	public static final String APP_ID ="wx39c376bcde966d07";
//	public static final String APP_SECRET="776d2e16efb9c4214009f20699a6a8ae";
//	public static final String APP_PATH = "https://www.caifusmartpark.cn/SarsCtrl/";

	//普陀生产账号
	public static final String APP_ID ="wx9f1f0195ea18d2fc";
	public static final String APP_SECRET="00d69464a7a30a166e0a727deda3f1e5";
	public static final String APP_PATH = "https://www.caifusmartpark.cn/SarsCtrl/";

	public static final String SNSAPI_BASE="snsapi_userinfo";
	public static final String SCOPE="snsapi_base";

	public static final String[] HQLForbiddenStr = new String[] { "'" };
	
	public static final String DATE_PATTERN_YYYYMMDDHHMMSS_01="yyyyMMddHHmmss";
	public static final String DATE_PATTERN_YYYYMMDDHHMMSS_02="yyyy-MM-dd HH:mm:ss";
	public static final String TIME_PATTERN_HHMMSS_01="HHmmss";
	public static final String TIME_PATTERN_HHMMSS_02="HH:mm:ss";
	public static final String DATE_PATTERN_YYYYMMDD_01="yyyyMMdd";
	public static final String DATE_PATTERN_YYYYMMDD_02="yyyy-MM-dd";
	
	
	private static final int BUFFER_SIZE = 16 * 1024;
	
	//图片压缩大小规则定义-图片压缩大小,KB
	public static final int IMAGE_SIZE_200 = 200;  				//【演出】
	public static final int IMAGE_SIZE_500 = 500;
	//图片APP端备份规则
	public static final String WEB_IMG_WEB="webck";
	public static final String WEB_IMG_APP="appck";
	
	
	public static String formatDate(String formater){
		SimpleDateFormat sdf=new SimpleDateFormat(formater);
		String date=sdf.format(new Date(System.currentTimeMillis()));
		return date;
	}
	
	public static String trim(String obj){
		if(obj==null){
			return "";
		}
		return obj.trim();
	}
	
	/**
     * 取deploy.properties文件中的单个内容
     * @param key
     * @return
     */
    public static String getDeployValue(String key) {
    	
    	Properties prop = new Properties();
		String rpath="/"+ Constance.class.getResource("").getPath().substring(1)+"deploy.properties";
		//System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
		//System.out.println(rpath);
		try {
			//针对中文空格的路径转译
			rpath=java.net.URLDecoder.decode(rpath,"UTF-8");
			prop.load(new FileInputStream(new File(rpath)));
			return prop.getProperty(key);
		} catch (UnsupportedEncodingException e) {
			System.out.println("文件路径转换异常");
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			System.out.println("文件路径异常");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO异常");
			e.printStackTrace();
		}
    	return null;
    }
	

	public static boolean isNotEmpty(String param){
		  if(param==null||param.trim().length()==0){
			  return false;
		  }
		  return true;
    }
	
	public static String numberFormat(double obj){
		  DecimalFormat   myFormatter   =   new   DecimalFormat("##########.##");  
		   
		  return myFormatter.format(obj);

    }
	
	public static int numberFormat(String str){
		int i = Integer.valueOf(str);
		return i;
	}
	
	public static String Null2EmptyTrim(String str) {
		if (str == null||"".equals(str)) {
			return "";
		} 
		return str.trim();
	}
	public static String Null2EmptyTrim(Object obj) {
		if(obj==null){
			return "";
		}
		String str=String.valueOf(obj);
		if (str == null||"".equals(str)) {
			return "";
		} 
		return str.trim();
	}
	public static int Null2EmptyTrimInt(Object obj) {
		if(obj==null){
			return 0;
		}
		String str=String.valueOf(obj);
		if (str == null||"".equals(str)) {
			return 0;
		} 
		return Integer.parseInt(str);
	}
	public static long Null2EmptyTrimLong(Object obj) {
		if(obj==null){
			return 0;
		}
		String str=String.valueOf(obj);
		if (str == null||"".equals(str)) {
			return 0;
		} 
		return Long.parseLong(str);
	}
	
	public static Double Null2EmptyTrimDouble(Object obj) {
		if(obj==null){
			return 0.00;
		}
		String str=String.valueOf(obj);
		if (str == null||"".equals(str)) {
			return 0.00;
		} 
		return Double.parseDouble(str);
	}
	
	public static String subString(String str,int number){
		String sub=str.substring(0,number);
		return sub;
	}
	public static String filterHQL(String orgStr) {
		for (int i = 0; i < HQLForbiddenStr.length; i++) {
			orgStr.replaceAll(HQLForbiddenStr[i], "\'" + HQLForbiddenStr[i]);
			//orgStr = String.replace(orgStr, HQLForbiddenStr[i], "\'" + HQLForbiddenStr[i]);
		}
		return orgStr;
    }
	public static String join(Object[] objVal,String joinFlag){
		  StringBuffer sb=new StringBuffer();
		  if(objVal!=null&&objVal.length>0){
			  for(Object obj:objVal){
				  sb.append(joinFlag).append(obj);
			  }
			  return sb.toString().substring(1);
		  }
		  
		  return null;
	  }
	public static java.sql.Date formateDate(String date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date temp=null;
		try {
		   temp=new java.sql.Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static String formateDatePattern(String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		String patternStr=sdf.format(new Date());
		
		return patternStr;
	}
	
	public static String ChangeDatePattern(String dateVal,String fromPattern,String toPattern){
		SimpleDateFormat sdf1=new SimpleDateFormat(fromPattern);
		SimpleDateFormat sdf2=new SimpleDateFormat(toPattern);
		String patternStr="";
		try {
			Date date=sdf1.parse(dateVal);
			patternStr=sdf2.format(date);
			
			return patternStr;
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return patternStr;
	}
	
	//字符串格式化输出
	
	
	
	public static Properties getInitPro(){
		 Properties prop = new Properties();
		 String path = Constance.class.getResource("/").getPath();
		 logger.info(path);
		 InputStream is= Constance.class.getResourceAsStream("/config.properties");
		  try {
			prop.load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String getAge(String dateTime) throws ParseException {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String twoDate = sdf.format(new Date());
		Date d1=sdf.parse(dateTime);
		Date d2=sdf.parse(twoDate);

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = d2.getTime() - d1.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		//System.out.println(day + "天" + hour + "小时" + min + "分钟");

		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(d1);
		aft.setTime(d2);
		int month = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		// 计算差多少年
		int year = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR));

		if(year > 0 && month >=6){
			return year+1+"岁";
		}else if(year > 0 && month < 6){
			return year+"岁";
		} else {
			return "0岁";
		}
	}

	/**
	 * 通过出生日期 算出年龄
	 * @param dateTime 格式 yyyyMMdd
	 * @return Integer
	 * @throws ParseException ParseException
	 */
	public static Integer getAgeByBirthDate(String dateTime)  {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String twoDate = sdf.format(new Date());
		Date d1= null;
		Date d2 = null;
		try {
			d1 = sdf.parse(dateTime);
			d2=sdf.parse(twoDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = d2.getTime() - d1.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		//System.out.println(day + "天" + hour + "小时" + min + "分钟");

		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(d1);
		aft.setTime(d2);
		int month = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		// 计算差多少年
		int year = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR));

		if(year > 0 && month >=6){
			return year+1;
		}else if(year > 0 && month < 6){
			return year;
		} else {
			return 0;
		}
	}
	
	//是否为空
	public static boolean isNotNull(Object param){
		  if(param==null){
			  return false;
		  }
		  return true;
	 }

	public static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) { in.close(); }
				if (null != out) { out.close(); }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 获取当前系统时间 14位
     * @return
     */
	public static String currentDateTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}
	
	/**
	 * 判断长度进行格式化日期
	 * @param date
	 * @return
	 */
	public static String simpleDate(String date){
		String newDate = "";
		if(date == null || date.trim().equals("")){
			return newDate;
		}
		
		if(date.length() == 6){
			newDate = simpleMonthFormat(date);
		}
		
		if(date.length() == 8){
			newDate = simpleDateFormatOne(date);
		}
		
		if(date.length() == 12){
			newDate = simpleDateFormat12(date);
		}
		
		if(date.length() == 14){
			newDate = simpleDateFormat(date);
		}
		
		return newDate;
	}
	
	//格式化日期12位
	public static String simpleDateFormat12(String dateTime){
		if(dateTime == null || dateTime.trim().equals("")){
			return "";
		};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time.format(date);
	}
	
	//格式化日期14位
	public static String simpleDateFormat(String dateTime){
		if(dateTime == null || dateTime.trim().equals("")){
			return "";
		};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time.format(date);
	}
	
	/**
	 * 格式化日期时间yyyy-MM-dd HH:mm:ss -->yyyyMMddHHmmss
	 * @param dateTime
	 * @return
	 */
	public static String formatDateTime(String dateTime) {
		if(dateTime == null || dateTime.trim().equals("")) {
			return "";
		};
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");		
		Date date = null;
		try {
			date = time.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(date);
	}
	
	//格式化日期
	public static String simpleDateFormatTwo(String dateTime){
		if(dateTime == null || dateTime.trim().equals("")){
			return "";
		};
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time.format(date);
	}
	//格式化日期格式8位  yyyyMMdd---------->yyyy-MM-dd
	public static String simpleDateFormatOne(String dateTime){
		if(dateTime == null){
			return "";
		}
		SimpleDateFormat sdf1=new SimpleDateFormat(DATE_PATTERN_YYYYMMDD_01);
		SimpleDateFormat sdf2=new SimpleDateFormat(DATE_PATTERN_YYYYMMDD_02);
		try {
			return sdf2.format(sdf1.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//格式化日期格式8位  yyyy-MM-dd---------->yyyyMMdd
	public static String simpleDateFormatThree(String dateTime){
		if(dateTime == null){
			return "";
		}
		SimpleDateFormat sdf1=new SimpleDateFormat(DATE_PATTERN_YYYYMMDD_02);
		SimpleDateFormat sdf2=new SimpleDateFormat(DATE_PATTERN_YYYYMMDD_01);
		try {
			return sdf2.format(sdf1.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//格式化日期格式  hhmmss---------->HH:mm:ss
	public static String simpleTimeFormatOne(String dateTime){
		SimpleDateFormat sdf1=new SimpleDateFormat(TIME_PATTERN_HHMMSS_01);
		SimpleDateFormat sdf2=new SimpleDateFormat(TIME_PATTERN_HHMMSS_02);
		try {
			return sdf2.format(sdf1.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//去除日期的-（yyyy-mm-DD ----------------->YYYYMMDD）
	public static String  disponseDate(String  date){
		date=date.replace("-", "");
		
		return date;
	}
	
	/**
	 * 格式化年月格式6位  yyyyMM---------->yyyy年MM月
	 * @param ym
	 * @return
	 */
	public static String simpleMonthFormat(String ym){
		if(ym == null){
			return "";
		}
		
		SimpleDateFormat time=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");	
		try {
			return df.format(time.parse(ym));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 格式化年月格式6位  yyyyMMdd---------->yyyy年MM月dd日
	 * @param ym
	 * @return
	 */
	public static String simpleDayFormat(String ym){
		if(ym == null){
			return "";
		}
		
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");	
		try {
			return df.format(time.parse(ym));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//自动补位
	public static  String fillTo8(int obj,int totalLen){
		String temp=String.valueOf(obj);
		int len=temp.length();
		String str="";
		for(int i=0;i<totalLen-len;i++){
			str+="0";
		}
		str+=temp;
		return str;
	}
	
	/**
	 * 获取当前时间    yyyyMMdd
	 * @return
	 */
	public static String simpleDateOne(){
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_PATTERN_YYYYMMDD_01);
		return dateFormat.format(date);
	}
	/**
	 * 获取当前时间HHmmss
	 * @return
	 */
	public static String simpleTime(){
		Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");//时:分:秒:毫秒-HH:mm:ss.SSS
		return sdf.format(d);
	}
	//获取当前月日
	public static String simpleMMdd(){
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MMdd");
		return dateFormat.format(date);
	}
	
	//生成订单号(15位)
	public static String orderFormNumber(){
		int r1=new Random().nextInt(9) + 1;;//产生2个0-9的随机数
		int r2=(int)(Math.random()*(10));
		long now = System.currentTimeMillis();//一个13位的时间戳
		String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);// 订单ID
		return paymentID;
	}

	/**
	 * 计算日期距离当前时间 X天X小时X分钟  传递14日期格式
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String dateDiff(String dateTime) throws ParseException { 
    	
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");  
		String twoDate = sdf.format(new Date());
        Date d1=sdf.parse(dateTime);  
        Date d2=sdf.parse(twoDate);  
    	
    	long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = d2.getTime() - d1.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        //System.out.println(day + "天" + hour + "小时" + min + "分钟");
        if(day > 0){
        	return day+"天前";
        }else if(hour > 0){
        	return hour+"小时前";
        }else if(min > 0){
        	return min+"分钟前";
        }else{
        	return "刚刚";
        }
	}
	
	public static String  currentDatetime(){
		String  currentDatetime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return currentDatetime;
	}

	/* 算时间差 */
	public static long dateDiffMin(String startTime, String endTime) throws ParseException {
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		//long ns = 1000;//一秒钟的毫秒数long diff;try {
		//获得两个时间的毫秒时间差异
		long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		//long day = diff/nd;//计算差多少天
//		long hour = diff/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		//long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果

		return min;
	}

	
	/* 算时间差 */
	public static long dateDiff(String startTime, String endTime) throws ParseException {
	//按照传入的格式生成一个simpledateformate对象
	SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
	long nd = 1000*24*60*60;//一天的毫秒数
	long nh = 1000*60*60;//一小时的毫秒数
	//long nm = 1000*60;//一分钟的毫秒数
	//long ns = 1000;//一秒钟的毫秒数long diff;try {
	//获得两个时间的毫秒时间差异
	long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
	//long day = diff/nd;//计算差多少天
	long hour = diff/nh;//计算差多少小时
	//long min = diff%nd%nh/nm;//计算差多少分钟
	//long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
	
	return hour;
	}
	
	/* 算时间差 */
	public static long dayDiff(String startTime, String endTime) throws ParseException {
	//按照传入的格式生成一个simpledateformate对象
	SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
	long nd = 1000*24*60*60;//一天的毫秒数
	long nh = 1000*60*60;//一小时的毫秒数
	//long nm = 1000*60;//一分钟的毫秒数
	//long ns = 1000;//一秒钟的毫秒数long diff;try {
	//获得两个时间的毫秒时间差异
	long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
	long day = diff/nd;//计算差多少天
	long hour = diff/nh;//计算差多少小时
	//long min = diff%nd%nh/nm;//计算差多少分钟
	//long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
	
	return day;
	}
	
	//日期延后7天，并去除非工作日
	//appintDate 后延多长时间
	public static List<String>  appointDate(int appintDate){
		List<String> dateList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int i=0;
		while(i<appintDate){
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if((calendar.get(Calendar.DAY_OF_WEEK))!=1&& (calendar.get(Calendar.DAY_OF_WEEK))!=7){
				Date date1 = calendar.getTime();
				dateList.add(sdf.format(date1));
				i++;
			}
		}
		
		return dateList;
	}
	
	/**
	 * 推前或后退若干天的日期 
	 * @param currentDate 起始日期yyyyMMdd
	 * @param dateNum 天数（前进正数，后退负数） 
	 * @return
	 */
	public static String nextDate(String currentDate,int dateNum) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(currentDate);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, dateNum);			
			Date d = calendar.getTime();
	        String newDate = sdf.format(d);
	        return newDate;
		} catch(Exception e) {
			logger.error("出现异常",e);
			return null;
		}
	}
	
	public static String trimBlank(String str){
		str = str.replace(" ", "");
		return str;
	}
	
	//验证字符串是否为纯数字
	public final static boolean isNumeric(String s) {  
        if (s != null && !"".equals(s.trim()))  
            return s.matches("^[0-9]*$");  
        else  
            return false;  
    }     

	/** 
	 * 字符串的日期格式的计算  
	 * @param smdate 起始日期 yyyyMMdd
	 * @param bdate 截止日期 yyyyMMdd
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate,String bdate) {  
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
			Calendar cal = Calendar.getInstance();    
			cal.setTime(sdf.parse(smdate));    
			long time1 = cal.getTimeInMillis();                 
			cal.setTime(sdf.parse(bdate));    
			long time2 = cal.getTimeInMillis();         
			long between_days=(time2-time1)/(1000*3600*24);  
            
			return Integer.parseInt(String.valueOf(between_days)); 
		} catch(Exception e) {
			logger.error("计算日期出错"+e);
			return 0;
		}
   }  
	
	/**
	 * 比较两个数字之间差额的百分比
	 * @param p1
	 * @param p2
	 * @return
	 */
	 public static String percent(double p1, double p2) {
	    	try {
	    		String str;
	    		double p3 = 0;
	    		if(p1!=0 || p2 != 0) {
	    			 p3 =  (p1 / p2)-1;
	    		}
		        
		        NumberFormat nf = NumberFormat.getPercentInstance();
		        nf.setMinimumFractionDigits(2);
		        str = nf.format(p3);
		        return str;
	    	} catch(Exception e) {
				logger.error("出现异常",e);
				return null;
			}
	    }

	 /**
	  * 根据文件地址得到文件名称(无后缀)
	  * @param path
	  * @return
	  */
	 public static String getFileNameByPath(String path) {
		 try {

			 if(path == null || path.length()<1) {
				 return null;
			 }
			 String[] s = path.split("/");
			 if(s.length<1) {
				 return null;
			 }
			 String fileAllName = s[s.length-1];
			 String fileName = fileAllName.substring(0, fileAllName.indexOf("."));
			 return fileName;
		 } catch(Exception e) {
			 logger.error("出现异常",e);
			return null;
		 }		 
	 }
	 
	 /**
	 * 删除指定路径文件
	 * path 本地文件路径(不能是http开头的，类似D:/temp或者/usr/local)
	 * @param path
	 */
	public static boolean delectFile(String path) {
		boolean falg = true;
		try {
			File file = new File(path);
			if(file.exists()) {
				file.delete();
			}	
		} catch(Exception e) {
			System.out.println("删除文件失败"+e);
			falg = false;
		}
		return falg;
	}
	
	/**
	 * 根据一个10长度时间字符串 计算返回内容
	 * @param dateHour yyyyMMddhh
	 * @return yyyy-MM-dd hh至hh
	 */
	public static String getTimeBucket(String dateHour) {
		try {
			if(dateHour==null || dateHour.length()<10) {
				return null;
			}
			//日期转译
			String date = simpleDateFormatOne(dateHour.substring(0,8));
			//时间
			String hour = dateHour.substring(8);
			
			String[] str = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
			
			for(int n=0;n<str.length;n++) {					
				if((!hour.equals("23")) && hour.equals(str[n])) {						
					return date+" "+ hour+"点00分 至 "+str[n+1]+"点00分";//显示内容					
				} 
				if(hour.equals("23")){
					return date+" 23点00分 至 00点00分";//显示内容
				}
			}
			
			
			
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	   /**
     * 取文件后缀
     */
    public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	public static void main(String[] args) {
//		int r1=new Random().nextInt(9) + 1;;//产生2个0-9的随机数
//		int r2=(int)(Math.random()*(10));
//		long now = System.currentTimeMillis();//一个13位的时间戳
//		String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);// 订单ID
//		System.out.println(paymentID);
//		for (int i = 0; i < 8; i++) {
//			String order = orderFormNumber();
//			System.out.println(order);
//		}
		
//		System.out.println(daysBetween("20170101","201701012"));
		
		System.out.println(Constance.getFixLen(12));
		System.out.println(orderFormNumber());
	}
	
	/**
	 * 生成不重复数字
	 * @param digit 位数
	 * @return
	 */
	public static String randNumber(int digit){
		String str = "";
		if(digit==10){
			str = Constance.simpleDateOne().substring(0, 5) ;
			int a = (int)(Math.random()*(99999-10000+1))+10000;
			str = str+a; 			
		}
		if(digit==18){
			str = Constance.currentDateTime();
			int a = (int)(Math.random()*(9999-1000+1))+1000;		
			str = str+a; 	
		}
		return str;
	}

	/**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    public static String autoGenericCode(int code, int num) {
        String result = "";

        result = String.format("%0" + num + "d", code);

        return result;
    }
	
	public static String reserveHoursFormat(String hours) {  
		String strHours="";
		Integer intHours= Integer.parseInt(hours);
		try {
			strHours= Constance.autoGenericCode(intHours, 2)+":00-"+ Constance.autoGenericCode(intHours+1, 2)+":00";
			
		} catch(Exception e) {
			logger.error("计算日期出错"+e);
			
		}
		return strHours;
   }  
	
	//去除日期的-（HH:ss:mm ----------------->HHssmm）
	public static String  disponseTime(String  date){		
		date=date.replace(":", "");
		return date;
	}
	
	/**
	 * 生成随机数字和字母 
	 * @param length  生成长度
	 * @return
	 */
    public static String getStringRandom(int length) {  
          
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
            	
            	//大小写混排
//                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
//                val += (char)(random.nextInt(26) + temp);  
            	
            	//纯大写、 
                val += (char)(random.nextInt(26) + 65); 
            	
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
    
  //格式化日期 yyyyMMddHHmmss------------------->yyyy-MM-dd HH:mm:ss
  	public static String simpleDateTimeFormat(String dateTime){
  		if(dateTime == null || dateTime.equals("")){
  			return "";
  		}
  		try {
  			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
  			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  			Date date=df.parse(dateTime);
  			return time.format(date);
  		} catch (ParseException e) {
  			e.printStackTrace();
  		}
  		return null;
  	}
  //根据1234567返回星期几
  	public static String getWeekByNumber(int number){
  		String week="";
  		switch(number){
  			case 1:
  				week="周一";
  				break;
  			case 2:
  				week="周二";
  				break;
  			case 3:
  				week="周三";
  				break;
  			case 4:
  				week="周四";
  				break;
  			case 5:
  				week="周五";
  				break;
  			case 6:
  				week="周六";
  				break;
  			case 7:
  				week="周日";
  				break;
  		}
  		return week;
  	}
  	
  	
  	public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
  	
  	
  	/**
  	 * 随机生成19位数字
  	 * @return shd
  	 */
  	 public static String getFixLen(int length) {
  		Random random = new Random();
		// 64位数字数组
		int[] number = new int[length];
		// 循环变量
		int i = 0;
		String a="";
		// 生成64位随机数算法
		for (i = 0; i < length; i++) {
		if (number[i] == 0) {
		// 产生0-10之间的随机小数，强制转换成正数
		number[i] = (int) (Math.random() * 10);
		}
		 a+=(number[i]+"" );
  	 }
		return a;
  	 }

	public static String nextHour(int num) {
		long currentTime = System.currentTimeMillis() ;
		currentTime += num*60*1000;
		Date date=new Date(currentTime);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String lastYear(int num) {
		String year = "";
		int nowYear = Integer.valueOf(simpleDateOne().substring(0, 4));
		year = (nowYear - num) +"";
		return year;
	}


}
	

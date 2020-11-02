/**
 * 
 */
package com.caifu.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Administrator
 */
public class Utils {

	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);


	private Utils() {
	}

	public static boolean isBlank(String value) {
		return StringUtils.isBlank(value);
	}
	
	public static Properties getProperties(){
		Properties prop = new Properties();   
		InputStream is= Utils.class.getResourceAsStream("/config.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	public static String getConfigValue(String objKey){
		Properties pro= Utils.getProperties();
		return  pro.getProperty(objKey);
	}
	
	public static String getRandom() {
	   String code="";
	   int random = Math.abs(new java.util.Random().nextInt()) % 900000 + 100000;
	   code=String.valueOf(random);
	   return code;
    }
	
	public static String getRandomAmount() {
		   String amount="";
		   int random = Math.abs(new java.util.Random().nextInt()) % 900000 + 100000;
		   amount=String.valueOf(random/100.00);
		   return amount;
	    }
	
	  /**
     * 取resource/jdbc.properties文件中的单个内容
     * @param key
     * @return
     */
    public static String getDeployValue(String key) {
    	Properties prop = new Properties();
    	String temp = Utils.class.getResource("").getPath().substring(1);
    	String filepath = temp.substring(0, temp.indexOf("classes"));
		String rpath="/"+filepath+"classes/resource/jdbc.properties";
//		System.out.println(rpath);
		try {
			//针对中文空格的路径转译
			rpath=java.net.URLDecoder.decode(rpath,"UTF-8");
			prop.load(new FileInputStream(new File(rpath)));
			return prop.getProperty(key);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("文件路径转换异常");
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("文件路径异常");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO异常");
			e.printStackTrace();
		}
    	return null;
    }    
    
    /**
     *  获取百分比
     *  @param  p1
     *  @param  p2
     *  @return 
     */ 
    public static String percent(double p1, double p2)   {
        String str;
        double p3 =  p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p3);
        return str;
    }
    
    
    
    /**
     * 对流水号配置文件的读写
     * @param
     * @return 流水号
     */
    public static String getRevampValue() {
    	StringBuffer buf = new StringBuffer();
    	Long log = 1L;
    	Properties prop = new Properties();
    	String path = Utils.class.getResource("").getPath();
    	
		try {
			//针对中文空格的路径转译
			path=java.net.URLDecoder.decode(path,"UTF-8");
			path = path.substring(0, path.indexOf("classes"));
			path = path.concat("classes/resource/serial.properties");
			prop.load(new FileInputStream(new File(path)));
			Long num = Long.parseLong(prop.getProperty("serial.number"));
			log = num<=999999?num:0;
			OutputStream fos = new FileOutputStream(path);
			//流水号结果
			String r = (log+1)+"";
			prop.setProperty("serial.number",r);
			prop.store(fos, "Update 'serial.number' value");
			
			//组装标准结果
			if(r.length()<6){
				for(int i=0;i< 6 - r.length();i++) {
					buf.append("0");
				}
				buf.append(r);
			} else {
				buf.append(r);
			}			
		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();			
		}
		return buf.toString();
    }

    //获取指定长度的一串随机数
    public static String getAppointlenRandom(int n) {
    	StringBuffer buf = new StringBuffer();
    	for(int i=0;i<n;i++) {
    		buf.append(new java.util.Random().nextInt(9));
    	}
    	return buf.toString();
    }
  
    public static JSONObject generate(List<?> list, int count) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	map.put("total", count);
    	
    	map.put("result_set", list);
    	
    	if(count == 0){	
    		map.put("codeDesc", "查无数据");
    	}else{
    		map.put("codeDesc", "查询成功");
    	}
    	
    	map.put("code", "0000");
    	
		
    	
    	JSONObject obj = JSONObject.fromObject(map);
    	
//    	System.out.println(obj);
    	
    	return obj;
    	
    }
    
    /**
     * 拷贝文件
     * @param rawPath
     * @param newPath
     * @return
     */
    public static boolean copyFile(String rawPath,String newPath) {
    	
    	 try {  
    		 //要拷贝的图片
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(rawPath)));
             //目标的地址
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(newPath)));  
           
             int val = -1;  
             
             while((val=bis.read())!=-1){  
            	 
            	 bos.write(val);  
            	 
             }  
             
             bos.flush();  
             
             bos.close();  
             
             bis.close();
             
             return true;
    		 
         } catch (IOException e) {  
        	 
             e.printStackTrace(); 
             
         } 
    	 
         return false;
    }
    
    /**
     * 把指定内容转成指定编码获得其长度
     * @param str
     * @param coding utf-8 gb2312 gbk 
     * @return
     */
    public static int getLengthString(String str,String coding) {
    	try{
    		
    		byte[] b = str.getBytes(coding);
    		
    		return b.length;
    		
    	}catch(Exception ex) {
    	   return 0;
    	}
	}

	/**
	 * 判断传入的字符串是否为空 || 为空串
	 * @param str ： 需要判断的字符串
	 * @return ： 判断的结果
	 * <li> true ：为空
	 * <li> false ：不为空
	 */
	public static boolean isNull(String str) {
		return str == null || "".equals(str.trim())? true : false;
	}

	/**
	 * 判断传入的字符串是否不为空 && 不为空串
	 * @param str ： 需要判断的字符串
	 * @return ： 判断的结果
	 * <li> true ：不为空
	 * <li> false ：为空
	 */
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	/**
	 * 重载方法,判断多个字符串或可转换字符窜的对象是否为空
	 * @param flag : 判断条件
	 * <li> true : 逻辑且
	 * <li> false : 逻辑或
	 * @param objArr : 需要判断的所有字符串或可以tostring的对象
	 * @return : 判断的结果
	 * <li> true : 不为空
	 * <li> false : 为空
	 */
	public static boolean isNotNull(boolean flag, Object... objArr) {
		boolean resultFlag = false;

		// 判断传入的数组是否为空
		if (objArr != null && objArr.length > 0) {
			// 不为空 --> 迭代数组
			for (Object obj : objArr) {
				// 判断是逻辑且 | 逻辑或
				if (flag) {
					// 逻辑且 --> 都不为空 --> 有一个为空就为false
					if (obj == null || isNull(obj.toString())) {
						// 有为空 --> 直接结束返回false
						return false;
					}
					// 不为空，继续循环
				} else {
					// 逻辑或 --> 有一个不为空就行
					if (obj != null && isNotNull(obj.toString())) {
						// 有不为空的 --> 直接返回true
						return true;
					}
					// 为空，继续循环
				}
			}
			// 迭代结束 --> 判断是逻辑且 | 逻辑或
			if (flag) {
				// 逻辑且 --> 全不为空 --> 返回true
				resultFlag = true;

			}
			// 逻辑或 --> 全为空 --> 返回默认的false
		}
		// 为空 --> 返回默认的false

		return resultFlag;
	}

	/**
	 * 重载方法,判断多个字符串或可转换字符窜的对象是否不为空
	 * @param flag : 判断条件
	 * <li> true : 逻辑且
	 * <li> false : 逻辑或
	 * @param objArr : 需要判断的所有字符串或可以tostring的对象
	 * @return : 判断的结果
	 * <li> true : 为空
	 * <li> false : 不为空
	 */
	public static boolean isNull(boolean flag, Object... objArr) {
		boolean resultFlag = false;

		// 判断是逻辑且 | 逻辑或
		if (flag) {
			// 逻辑且 --> isNotNull的逻辑或,取反
			resultFlag = !isNotNull(false, objArr);

		} else {
			// 逻辑或 --> isNotNull的逻辑且,取反
			resultFlag = !isNotNull(true, objArr);
		}

		return resultFlag;
	}

	/**
	 * 判断传入的对象是否为空
	 * @param obj : 需要判断的对象
	 * @return : 判断的结果
	 * <li>true : 为空</li>
	 * <li>false : 不为空</li>
	 */
	public static boolean isNull(Object obj)
	{
		return obj == null || "".equals(obj.toString().trim());
	}


	/**
	 * 判断传入的对象是否不为空
	 * @param obj : 需要判断的对象
	 * @return : 判断的结果
	 * <li>true : 不为空</li>
	 * <li>false : 为空</li>
	 */
	public static boolean isNotNull(Object obj)
	{
		return !isNull(obj);
	}







	public static void main(String[] args) {
		
		String random = Utils.getAppointlenRandom(4);
		System.out.println("random"+random);
		//System.out.println(Utils.getDeployValue("jdbc.url"));
		//System.out.println(Utils.getConfigValue("user"));
		//System.out.println(Utils.getConfigValue("pass"));
		//System.out.println(Utils.getConfigValue("httpUrl"));
		
//		System.out.println(MD5Utils.MD5Encode("123456"));
		//Long num = getRevampValue();
		//System.out.println(num);
	}
	

}

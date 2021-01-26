package com.xkx.chick.common.util;

import cn.hutool.core.text.StrFormatter;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import org.apache.shiro.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author sdyy
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';
    // private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个集合是否为空
     *
     * @return true：为空 false：非空
     */
    public static boolean isNull(Collection c) {
        return CollectionUtils.isEmpty(c);
    }

    public static boolean isNotNull(Collection c) {
        return !isNull(c);
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * describe 只能传入一个字符判断是否是中文字符,只要编码在\u4e00到\u9fa5之间的都是汉字
     *
     * @param c
     * @return
     * @Author: Created by Daisk on 2019/9/20 10:28.
     */
    public static boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }


    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    // public static String abbr(String str, int length) {
    // 	if (str == null) {
    // 		return "";
    // 	}
    // 	try {
    // 		StringBuilder sb = new StringBuilder();
    // 		int currentLength = 0;
    // 		for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
    // 			currentLength += String.valueOf(c).getBytes("GBK").length;
    // 			if (currentLength <= length - 3) {
    // 				sb.append(c);
    // 			} else {
    // 				sb.append("...");
    // 				break;
    // 			}
    // 		}
    // 		return sb.toString();
    // 	} catch (UnsupportedEncodingException e) {
    // 		e.printStackTrace();
    // 	}
    // 	return "";
    // }


    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * describe 转为BigDecimal用于有精度的小数计算
     *
     * @param val
     * @return
     * @Author: Created by Daisk on 2019/9/25 14:34.
     */
    public static BigDecimal toBigDecimal(String val) {
        return new BigDecimal(val);
    }

    public static BigDecimal toBigDecimal(int val) {
        return new BigDecimal(val);
    }

    /**
     * describe 底层是将double转string再转bigdecimal,防止精度丢失
     *
     * @param val
     * @return
     * @Author: Created by Daisk on 2019/9/27 14:53.
     */
    public static BigDecimal toBigDecimal(Double val) {
        return BigDecimal.valueOf(val);
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }


    /**
     * 获得用户远程地址
     */
    // public static String getRemoteAddr(HttpServletRequest request) {
    // 	String remoteAddr = request.getHeader("X-Real-IP");
    // 	if (isNotBlank(remoteAddr)) {
    // 		remoteAddr = request.getHeader("X-Forwarded-For");
    // 	} else if (isNotBlank(remoteAddr)) {
    // 		remoteAddr = request.getHeader("Proxy-Client-IP");
    // 	} else if (isNotBlank(remoteAddr)) {
    // 		remoteAddr = request.getHeader("WL-Proxy-Client-IP");
    // 	}
    // 	return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    // }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    // public static String toUnderScoreCase(String s) {
    // 	if (s == null) {
    // 		return null;
    // 	}
    //
    // 	StringBuilder sb = new StringBuilder();
    // 	boolean upperCase = false;
    // 	for (int i = 0; i < s.length(); i++) {
    // 		char c = s.charAt(i);
    //
    // 		boolean nextUpperCase = true;
    //
    // 		if (i < (s.length() - 1)) {
    // 			nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
    // 		}
    //
    // 		if ((i > 0) && Character.isUpperCase(c)) {
    // 			if (!upperCase || !nextUpperCase) {
    // 				sb.append(SEPARATOR);
    // 			}
    // 			upperCase = true;
    // 		} else {
    // 			upperCase = false;
    // 		}
    //
    // 		sb.append(Character.toLowerCase(c));
    // 	}
    //
    // 	return sb.toString();
    // }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString 对象串
     *                     例如：row.user.id
     *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i = 0; i < vals.length; i++) {
            val.append("." + vals[i]);
            result.append("!" + (val.substring(1)) + "?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    private final static int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
            Integer.MAX_VALUE};

    /**
     * 计算一个整数的大小
     *
     * @param x
     * @return
     */
    public static int sizeOfInt(int x) {
        for (int i = 0; ; i++) {
            if (x <= SIZE_TABLE[i]) {
                return i + 1;
            }
        }
    }

    /**
     * 判断字符串的每个字符是否相等
     *
     * @param str
     * @return
     */
    public static boolean isCharEqual(String str) {
        return str.replace(str.charAt(0), ' ').trim().length() == 0;
    }

    /**
     * 确定字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空格、空(“)”或null。
     *
     * @param str
     * @return
     */
    public static boolean equalsNull(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || "null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * describe 根据map集合匹配相似包含项
     *
     * @param map
     * @param keyLike
     * @return
     * @Author: Created by liuda on 2019/7/15 16:26.
     */
    public static boolean getLikeByMap(Map<String, Object> map, String keyLike) {
        boolean flag = false;
        for (Map.Entry<String, Object> entity : map.entrySet()) {
            if ("anon".equalsIgnoreCase((String) entity.getValue())) {
                if (entity.getKey().contains("**")) {
                    if (keyLike.indexOf(entity.getKey().replace("/**", "")) > -1) {
                        flag = true;
                        break;
                    }
                } else {
                    if (Pattern.compile(keyLike).matcher(entity.getKey().replace("**", "*")).find()) {
                        System.out.println(entity.getKey().replace("**", "*"));
                        flag = true;
                        break;
                    }
                }
            } else {
                continue;
            }

        }
        return flag;
    }

    /**
     * describe 生成36位长UUID(大写)
     *
     * @param
     * @return
     * @Author: Created by Daisk on 2019/6/20 15:03.
     * @MethodName: uniqueKey36
     */
    public static String uniqueKey36() {// 36位长
        String key = UUID.randomUUID().toString();
        return key.toUpperCase();
    }

    /**
     * describe object 转list<T>
     *
     * @param
     * @return
     * @Author: Created by Daisk on 2019/7/19 16:36.
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    public static String toString(Object object) {
        if (object != null) {
            return object.toString();
        }
        return "";
    }

    /**
     * @param obj
     * @return
     * @throws
     * @Description: 获取trim后的字符串，如果是NULL则返回空字符串
     * @Author: lizhengtang
     * @CreateDate: 2019/6/27 3:50 PM
     * @Version: 1.0
     */
    public static String getTrimStr(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * describe 将url中的中文字符转码（原因：url中不支持中文）
     *
     * @param urlStr
     * @return
     * @Author: Created by Daisk on 2019/9/20 10:51.
     */
    public static String encodeUrl(String urlStr) {
        String resultURL = "";
        for (int i = 0; i < urlStr.length(); i++) {
            char charAt = urlStr.charAt(i);
            //只对汉字处理
            if (isChineseChar(charAt)) {
                String encode = null;
                try {
                    encode = URLEncoder.encode(String.valueOf(charAt), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                resultURL += encode;
            } else {
                resultURL += charAt;
            }
        }
        return resultURL;
    }

    public static <T> void listToModel(List<Object> list, T t) throws Exception {
        Field[] fields = t.getClass().getDeclaredFields();
        if (list.size() != fields.length) {
            return;
        }
        for (int k = 0, len = fields.length; k < len; k++) {
            // 根据属性名称,找寻合适的set方法
            String fieldName = fields[k].getName();
            String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
            Method method = null;
            Class<?> clazz = t.getClass();
            try {
                method = clazz.getMethod(setMethodName, new Class[]{list.get(k).getClass()});
                System.out.println("list.get(" + k + ").getClass():" + list.get(k).getClass());
            } catch (SecurityException e1) {
                e1.printStackTrace();
                return;
            } catch (NoSuchMethodException e1) {
                String newMethodName = "set" + fieldName.substring(0, 1).toLowerCase()
                        + fieldName.substring(1);
                try {
                    method = clazz.getMethod(newMethodName, new Class[]{list.get(k).getClass()});
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (method == null) {
                return;
            }
            method.invoke(t, new Object[]{list.get(k)});
        }
    }
}

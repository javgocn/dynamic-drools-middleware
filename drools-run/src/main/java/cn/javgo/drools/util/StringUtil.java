package cn.javgo.drools.util;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;

/**
 * 字符串工具类
 */
public class StringUtil implements Serializable {

    /**
     * 私有化构造器: 防止被实例化
     */
    private StringUtil() {
    }

    //==========================================  判空  ==========================================

    /**
     * 判断字符串是否为空，空的定义如下：
     * 1、为 null
     * 2、为 ""
     * 3、为 " " 或 "\t" 等空白符
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isNull(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否为空，空的定义如下：
     * 1、为 null
     * 2、为 ""
     * 3、为 " " 或 "\t" 等空白符
     * 4、为 "null"、"NULL"、"Null" 等
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isNullOrNullValue(String str){
        return str == null || str.trim().length() == 0 || str.trim().equalsIgnoreCase("null");
    }

    //==========================================  比较  ==========================================

    /**
     * 判断两个字符串是否相等，判断条件如下：
     * 1、两个字符串都不为 null
     * 2、两个字符串相等（底层使用 equals 方法比较两个字符串的值）
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 是否相等
     */
    public static boolean equals(String str1, String str2){
        return str1 != null && str1.equals(str2);
    }

    /**
     * 判断两个字符串是否相等，判断条件如下：
     * 1、两个字符串都不为 null
     * 2、两个字符串去掉首尾空白符后相等（底层使用 equals 方法比较两个字符串的值）
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 是否相等
     */
    public static boolean equalsTrim(String str1, String str2){
        return str1 != null && str1.trim().equals(str2.trim());
    }

    /**
     * 判断两个字符串是否相等，判断条件如下：
     * 1、两个字符串都不为 null、""、" "、"\t" 等空白符
     * 2、两个字符串去掉首尾空白符后相等（底层使用 equals 方法比较两个字符串的值）
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 是否相等
     */
    public static boolean equalsNull(String str1, String str2){
        return !isNull(str1) && !isNull(str2) && str1.trim().equals(str2.trim());
    }

    //==========================================  补齐  ==========================================

    /**
     * 左补齐（用于编码生成，默认长度为3，不足补0）
     * 示例：leftPad("1") = "001"、leftPad("001") = "001"
     *
     * @param str 字符串
     * @return 补齐后的字符串
     */
    public static String leftPad(String str) {
        return leftPad(str, 3, '0');
    }

    /**
     * 左补齐
     *
     * @param str 字符串
     * @param length 长度
     * @param padChar 补齐字符
     * @return 补齐后的字符串
     */
    public static String leftPad(String str, int length, char padChar) {
        // 如果字符串为空或者长度大于等于指定长度，则直接返回
        if (isNull(str) || str.length() >= length) {
            return str;
        }
        return StringUtils.leftPad(str, length, padChar);
    }
}

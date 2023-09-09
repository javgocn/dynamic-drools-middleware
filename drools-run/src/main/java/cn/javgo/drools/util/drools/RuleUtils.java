package cn.javgo.drools.util.drools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 规则工具类
 */
public class RuleUtils {

    /**
     * 私有化构造器: 防止被实例化
     */
    private RuleUtils() {
    }

    //==========================================  判空  ==========================================

    /**
     * 判断字符串是否为空，定义空的标准如下：
     * 1、为 null
     * 2、为 ""（空字符串）
     *
     * @param str 字符串
     * @return true 为空，false 不为空
     */
    private static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断字符串是否不为空，定义空的标准如下：
     * 1、不为 null
     * 2、不为 ""（空字符串）
     *
     * @param str 字符串
     * @return true 不为空，false 为空
     */
    private static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    //==========================================  截取占位符内容  ==========================================


    /**
     * 截取两个 $ 之间的内容
     * 目的：获取规则条件中的占位符参数，即构成一个条件的所有因子配置单元的参数
     * 约定：占位符参数的格式为：$参数名$，如：$age$、$age2$、$age3$ 等
     * 示例：$age$ > 18 -->  age 或 $age$ > $age2$ --> age,age2
     *
     * @param str 字符串
     * @return 截取到的内容
     */
    public static List<String> getConditionParamBetweenChar(String str){

        // 通过正则表达式来匹配到两个 $ 之间的内容
        Matcher matcher = Pattern.compile("\\$(.*?)\\$").matcher(str);

        // 新建一个 List<String> 对象
        List<String> list = new ArrayList<>();

        // 循环匹配到的内容
        while (matcher.find()){
            // 将匹配到的内容添加到 list 中
            list.add(matcher.group(1));
        }

        // 返回 list
        return list;
    }

    /**
     * 截取两个 @ 之间的内容
     * 目的：?
     * 约定：?
     * 示例：?
     *
     * @param str 字符串
     * @return 截取到的内容
     */
    public static List<String> getValueBetweenChar(String str){
        // 通过正则表达式来匹配到两个 @ 之间的内容
        Matcher matcher = Pattern.compile("\\@(.*?)\\@").matcher(str);

        // 新建一个 List<String> 对象
        List<String> list = new ArrayList<>();

        // 循环匹配到的内容
        while (matcher.find()){
            // 将匹配到的内容添加到 list 中
            list.add(matcher.group(1));
        }

        // 返回 list
        return list;
    }

    /**
     * 截取两个 # 之间的内容
     * 目的：获取规则动作中的占位符参数
     * 约定：占位符参数的格式为：#参数名#，如：#age#、#age2#、#age3# 等
     * 示例：#age# > 18 -->  age 或 #age# > #age2# --> age,age2
     *
     * @param str 字符串
     * @return 截取到的内容
     */
    public static List<String> getActionParamBetweenChar(String str){
        // 通过正则表达式来匹配到两个 # 之间的内容
        Matcher matcher = Pattern.compile("\\#(.*?)\\#").matcher(str);

        // 新建一个 List<String> 对象
        List<String> list = new ArrayList<>();

        // 循环匹配到的内容
        while (matcher.find()){
            // 将匹配到的内容添加到 list 中
            list.add(matcher.group(1));
        }

        // 返回 list
        return list;
    }

    /**
     * 截取两个指定符号之间的内容（通用）
     *
     * @param str 字符串
     * @param charStr 指定符号（如：$、@、# 等）
     * @return 截取到的内容
     */
    public static List<String> getParamBetweenChar(String str, String charStr){

        // 定义正则表达式
        String regex = "\\₩(.*?)\\₩".replaceAll("₩", charStr);

        // 通过正则表达式来匹配到两个指定符号之间的内容
        Matcher matcher = Pattern.compile(regex).matcher(str);

        // 新建一个 List<String> 对象
        List<String> list = new ArrayList<>();

        // 循环匹配到的内容
        while (matcher.find()){
            // 将匹配到的内容添加到 list 中
            list.add(matcher.group(1));
        }

        // 返回 list
        return list;
    }

    //==========================================  检查  ==========================================

    /**
     * 检查字符串是否符合规则
     * 规则：字符串只能由数字、小数点、正负号组成（即：正负整数、正负小数）
     *
     * @param str 字符串
     * @return true 符合，false 不符合
     */
    public static boolean checkStyleOfString(String str){
        return str.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$");
    }

    /**
     * 检查字符串是否包含算数运算符
     * 算数运算符：+、-、*、/、%
     *
     * @param str 字符串
     * @return true 包含，false 不包含
     */
    public static boolean checkContainOfOperator(String str){
        return     str.contains("+")
                || str.contains("-")
                || str.contains("*")
                || str.contains("/")
                || str.contains("%");
    }

    /**
     * 检查字符串是否包含指定符号（charStr）内容（通用）
     *
     * @param str 字符串
     * @param charStr 指定符号（如：$、@、# 等）
     * @return true 包含，false 不包含
     */
    public static boolean checkContainOfOperator(String str, String charStr){
        return str.contains(charStr);
    }

    //==========================================  获取  ==========================================

    /**
     * 获取规则条件中比较运算符后面的值（条件变量）
     * 比较运算符：>、>=、<、<=、=、!=
     * 示例：age > 18 --> 18
     *
     * @param str 字符串
     * @return 比较运算符后面的值
     */
    public static String getConditionOfVariable(String str){

        // 按照比较运算符进行分割得到一个数组
        String[] split = str.split(">(=)?|<(=)?|={1,2}|!=");

        // 如果数组元素个数小于 2，则说明没有比较运算符，直接返回空字符串
        if (split.length < 2){
            return "";
        }

        // 返回数组的第二个元素（即：比较运算符后面的值）
        return split[1];
    }

    /**
     * 根据属性获取对应的 get 方法名
     * 目的：将属性名转换为 get 方法名
     * 示例：name --> getName
     *
     * @param property 属性名
     * @return get 方法名
     */
    public static String getMethodByProperty(String property){

        // 为防止原属性名被修改，所以这里新建一个变量来接收原属性名
        String tempStr = property;

        // 如果属性不为空
        if (RuleUtils.isNotEmpty(property)){
            // 将属性名的第一个字母转换为大写
            tempStr = tempStr.substring(0,1).toUpperCase() + tempStr.substring(1);

            // 返回 get 方法名
            return "get" + tempStr;
        }

        // 如果属性为空，则返回 null
        return null;
    }

    /**
     * 根据属性获取对应的 set 方法名
     * 目的：将属性名转换为 set 方法名
     * 示例：name --> setName
     *
     * @param property 属性名
     * @return set 方法名
     */
    public static String setMethodByProperty(String property){

        // 为防止原属性名被修改，所以这里新建一个变量来接收原属性名
        String tempStr = property;

        // 如果属性不为空
        if (RuleUtils.isNotEmpty(property)){
            // 将属性名的第一个字母转换为大写
            tempStr = tempStr.substring(0,1).toUpperCase() + tempStr.substring(1);

            // 返回 set 方法名
            return "set" + tempStr;
        }

        // 如果属性为空，则返回 null
        return null;
    }
}

package cn.javgo.drools.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 */
public class CollectionUtil implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionUtil.class);

    /**
     * 私有化构造器
     */
    private CollectionUtil() {
    }

    //==========================================  判空  ==========================================

    /**
     * 判断集合是否为空，空的定义如下：
     * 1、为 null
     * 2、集合中没有元素
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean collectionIsNull(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断 map 是否为空，空的定义如下：
     * 1、为 null
     * 2、map 中没有元素
     *
     * @param map map
     * @return 是否为空
     */
    public static boolean mapIsNull(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    //==========================================  拆分  ==========================================

    /**
     * 把 List 集合拆成最大容量为指定值的新 List 集合
     *
     * @param list 要拆分的集合
     * @param maxCountPerList 单个集合中元素数量
     * @return 返回封装拆分后集合的集合
     * @param <T> 泛型T
     */
    public static <T> List<List<T>> splitList(List<T> list,int maxCountPerList){
        // 如果集合为 null 或者集合中没有元素，则返回空集合
        if (collectionIsNull(list)){
            return new ArrayList<>(0);
        }

        // 获取集合的长度
        int len = list.size();

        // 集合长度对 maxCountPerList 取余，得到的结果为 0 时，表示集合长度刚好是 maxCountPerList 的整数倍，不需要再增加一个集合
        // 集合长度对 maxCountPerList 取余，得到的结果不为 0 时，表示集合长度不是 maxCountPerList 的整数倍，需要再增加一个集合存储剩余的元素
        int size = len % maxCountPerList;
        if (size == 0){
            // 原集合长度 / maxCountPerList = 需要拆分成几个集合
            size = len / maxCountPerList;
        }else {
            // 原集合长度 / maxCountPerList = 需要拆分成几个集合 + 1
            size = (len / maxCountPerList) + 1;
        }

        // 创建一个集合，用于存储拆分后的集合
        List<List<T>> packList = new ArrayList<>();

        // 遍历需要拆分成几个集合
        for (int i = 0; i < size; i++) {
            // 计算每个集合的开始索引
            int fromIndex = i * maxCountPerList;
            // 计算每个集合的结束索引（取 fromIndex + maxCountPerList 和 len 中较小的那个）
            int toIndex = Math.min(fromIndex + maxCountPerList, len);

            // 截取原集合中的元素，放入新集合中
            packList.add(list.subList(fromIndex, toIndex));
        }

        // 返回封装拆分后集合的集合
        return packList;
    }

    /**
     * 把 List 集合拆成指定数量的新 List 集合
     *
     * @param collection 要拆分的集合
     * @param count 拆分后每个集合中元素数量
     * @return 返回封装拆分后集合的集合
     * @param <T> 泛型T
     */
    public static <T> List<List<T>> splitList(Collection<T> collection, int count){
        // 如果集合为 null 或者集合中没有元素，则返回空集合
        if (collectionIsNull(collection)){
            return new ArrayList<>(0);
        }

        // 创建一个与原集合大小相同的集合，存储原集合中的元素（对该集合进行拆分，不影响原集合）
        List<T> list = new ArrayList<>(collection.size());
        list.addAll(collection);

        // 进行拆分
        return splitList(list, count);
    }

    /**
     * 把数组拆成最大容量为指定值的新 List 集合
     *
     * @param objs 数组
     * @param count 单个集合中元素数量
     * @return 返回封装拆分后集合的集合
     * @param <T> 泛型T
     */
    public static <T> List<List<T>> splitList(T[] objs, int count){
        // 如果数组为 null 或者数组中没有元素，则返回空集合
        if (objs == null || objs.length == 0){
            return new ArrayList<>(0);
        }

        // 创建一个与原数组大小相同的集合，存储原数组中的元素（对该集合进行拆分，不影响原数组）
        List<T> list = Arrays.asList(objs);

        // 进行拆分
        return splitList(list, count);
    }

    /**
     * 把 Map 集合拆成最大容量为指定值的新 Map 集合
     *
     * @param map 要拆分的集合
     * @param count 单个集合中元素数量
     * @return 返回封装拆分后集合的集合
     * @param <K> 泛型K
     * @param <V> 泛型V
     */
    public static <K, V> List<Map<K, V>> splitMap(Map<K, V> map, int count){
        // 如果集合为 null 或者集合中没有元素，则返回空集合
        if (mapIsNull(map)){
            return new ArrayList<>(0);
        }

        // 获取 map 的所有 key-value 映射关系
        Set<Map.Entry<K, V>> entrySet = map.entrySet();

        // 进行拆分
        List<List<Map.Entry<K, V>>> list = splitList(entrySet, count);

        // 创建一个集合，用于存储拆分后的集合
        List<Map<K, V>> mapList = new ArrayList<>(list.size());

        // 遍历拆分后的集合
        for (List<Map.Entry<K, V>> entryList : list) {

            // 创建一个 Map 集合，用于存储拆分后的 key-value 映射关系
            Map<K, V> mapMin = new HashMap<>(entryList.size());

            // 遍历拆分后的 key-value 映射关系
            for (Map.Entry<K, V> entry : entryList) {
                // 把 key-value 映射关系存入 Map 集合
                mapMin.put(entry.getKey(), entry.getValue());
            }

            // 把 Map 集合存入集合
            mapList.add(mapMin);
        }
        return mapList;
    }

    //==========================================  深拷贝  ==========================================

    /**
     * 深拷贝 List 集合 (使用传统序列化方式)
     *
     * @param list 要拷贝的集合
     * @return 返回拷贝后的集合
     * @param <T> 泛型T
     */
    @Deprecated // 该方法还未测试, 请勿使用
    @SuppressWarnings("unchecked")
    public static <T> List<T> deepCopy(List<T> list){
        try{
            // 创建一个字节数组输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 创建一个对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            // 把集合写入对象输出流
            oos.writeObject(list);

            // 创建一个字节数组输入流
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            // 创建一个对象输入流
            ObjectInputStream ois = new ObjectInputStream(bis);
            // 从对象输入流中读取对象
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) ois.readObject();

            // 关闭流
            oos.close();
            ois.close();

            // 返回拷贝后的集合
            return dest;
        }catch (IOException | ClassNotFoundException e){
            LOGGER.error("深拷贝 List 集合失败", e.getMessage());
        }
        // 深拷贝失败，返回空集合
        return new ArrayList<>();
    }

    /**
     * 深拷贝 List 集合(使用 JSON 序列化)
     *
     * @param list 要拷贝的集合
     * @param classOfT 泛型T的类型
     * @return 返回拷贝后的集合
     * @param <T> 泛型T
     */
    public static <T> List<T> deepCopyList(List<T> list, Class<T> classOfT){
        // 如果集合为 null 或者集合中没有元素，则返回空集合
        if (collectionIsNull(list)){
            return new ArrayList<>();
        }

        // 把集合转换成 JSON 字符串（序列化）
        String json = JsonSerializeUtil.toJson(list);

        // 把 JSON 字符串转换成集合（反序列化）
        return JsonSerializeUtil.fromJsonList(json, classOfT);
    }

    //==========================================  去重  ==========================================

    /**
     * 去重 List 集合，返回新集合
     *
     * @param list 要去重的集合
     * @return 返回去重后的集合
     * @param <T> 泛型T
     */
    public static <T> List<T> removeDuplicate(List<T> list){
        // 如果集合为 null 或者集合中没有元素，则返回空集合
        if (collectionIsNull(list)){
            return new ArrayList<>();
        }

        // 去重并返回新集合
        return new ArrayList<>(new HashSet<>(list));
    }

    //==========================================  转换  ==========================================

    /**
     * 将字符串转换成 Integer 类型的 List 集合
     *
     * @param str 字符串（元素按照逗号分隔）
     * @return 返回转换后的集合
     */
    public static List<Integer> getIntegerList(String str){
        // 如果字符串为 null 或者字符串为空串，则返回空集合
        if (StringUtil.isNull(str)){
            return new ArrayList<>();
        }

        return Arrays.stream(str.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

    }

    /**
     * 将字符串转换成 Long 类型的 List 集合
     *
     * @param str 字符串（元素按照逗号分隔）
     * @return 返回转换后的集合
     */
    public static List<Long> getLongList(String str){
        // 如果字符串为 null 或者字符串为空串，则返回空集合
        if (StringUtil.isNull(str)){
            return new ArrayList<>();
        }

        return Arrays.stream(str.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    /**
     * 将字符串转换成 String 类型的 List 集合
     *
     * @param str 字符串（元素按照逗号分隔）
     * @return 返回转换后的集合
     */
    public static List<String> getStringList(String str){
        // 如果字符串为 null 或者字符串为空串，则返回空集合
        if (StringUtil.isNull(str)){
            return new ArrayList<>();
        }

        return Arrays.asList(str.split(","));
    }
}

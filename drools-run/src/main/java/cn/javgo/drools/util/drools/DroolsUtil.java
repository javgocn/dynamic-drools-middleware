package cn.javgo.drools.util.drools;

import cn.javgo.drools.exception.UtilsException;
import cn.javgo.drools.exception.enums.DroolsExceptionEnum;
import cn.javgo.drools.listener.AgendaCallBackEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kie.api.KieBase;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Drools 工具类
 */
public class DroolsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsUtil.class);

    /**
     * KieBase 为规则引擎中的知识仓库，它包含了若干的规则、流程、函数、类型模型等。（这里主要指规则仓库）<br/>
     * 键值对说明：<br/><br/>
     * - key：场景标识。每个场景对应一个 KieBase，其中包含了若干的规则。<br/>
     * - value：KieBase 对象<br/><br/>
     * 注意：使用一个线程安全的 Map 来存储 KieBase 对象，这样就可以在多线程环境下使用了。
     */
    private static Map<String,KieBase> ruleMap = new ConcurrentHashMap<>();


    /**
     * 私有化构造器: 防止被实例化
     */
    private DroolsUtil() {
    }

    /**
     * 获取 DroolsUtil 实例 (单例模式)
     */
    private static class SingletonHolder {
        static DroolsUtil instance = new DroolsUtil();
    }

    /**
     * 获取 DroolsUtil 实例 (单例模式)<br/><br/>
     * 说明：因为 KieBase 是线程安全的，而 DroolsUtil 主要就是操作 KieBase，所以 DroolsUtil 也是线程安全的。
     * 因此，这里使用单例模式来获取 DroolsUtil 实例。这样做的好处是：在多线程环境下，不会重复创建 DroolsUtil 实例。
     * 从而避免了重复创建 DroolsUtil 实例的开销。
     */
    public static DroolsUtil getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 根据特定场景的规则字符串重新编译规则，并将编译后的 KieBase 存入缓存
     *
     * @param rule 规则字符串
     * @param scene 场景标识
     * @return KieSession 对象
     */
    public KieSession getDrlSession(final String rule,final String scene){
        try{
            // 设置系统的时间格式（因为 Drools 规则文件中的时间格式必须与系统时间格式一致，否则会报错）
            System.setProperty("drools.dateformat","yyyy-MM-dd");

            // 为了防止规则文件名字重复，此处加上时间戳( 格式：场景标识 + 时间戳 + .drl)
            String ruleFileName = scene + System.currentTimeMillis() + ".drl";

            // 获取 KieServices 对象（这是 KIE 模块提供的 API，用于获取其他对象）
            KieServices kieServices = KieServices.Factory.get();

            // 获取 KieFileSystem 对象（用于加载规则文件）
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            // 将规则文件存入 KieFileSystem 对象中
            // 这是一个重要的步骤，它允许你动态地加载和编译规则，而不需要预先在项目中有物理的 DRL 文件
            kieFileSystem.write(
                    // 规则文件在 KIE 虚拟文件系统中的路径
                    // 该路径模仿了 Maven 项目的结构，但实际上它是虚拟的，不代表实际的文件系统路径
                    // ruleFileName 是动态生成的文件名，以确保每次都是唯一的
                    "src/main/resources/drools/rules/" + ruleFileName,
                    // 将规则字符串转换为字节数组，这是 write 方法需要的格式
                    rule.getBytes(StandardCharsets.UTF_8));

            // 获取 KieBuilder 对象（用于编译规则文件）
            // Drools 会从这个虚拟文件系统中读取规则内容，然后编译它
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

            // 如果编译规则文件出错，则打印错误信息
            if (!kieBuilder.getResults().getMessages(Message.Level.ERROR).isEmpty()){
                // 记录错误日志
                LOGGER.error("规则文件编译出错：{}",kieBuilder.getResults().getMessages());
                // 抛出运行时异常
                throw new UtilsException(DroolsExceptionEnum.DROOLS_INIT_RULE_FAIL_ERROR);
            }

            // 获取 KieContainer 对象（用于加载 KieBase 对象到缓存）
            KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

            // 获取 KieBase 对象（用于创建 KieSession 对象）
            KieBase kieBase = kieContainer.getKieBase();

            // 将 KieBase 对象存入缓存
            ruleMap.put(scene,kieBase);

            // 获取 KieSession 对象（用于执行规则）
            KieSession kieSession = kieBase.newKieSession();

            // 添加监听器到 KieSession 对象中，用于监听规则执行过程中的事件
            // 主要目的：当监听到规则被触发时，触发回调函数
            kieSession.addEventListener(new DebugRuleRuntimeEventListener());
            kieSession.addEventListener(new AgendaCallBackEventListener());

            // 返回 KieSession 对象
            return kieSession;
        }catch (Exception e){
            // 记录错误日志
            LOGGER.error("规则引擎初始化规则失败，请查看错误信息:{}",e.getMessage());
            throw new UtilsException(DroolsExceptionEnum.DROOLS_INIT_RULE_FAIL_ERROR);
        }
    }

    /**
     * 根据特定场景的规则文件重新编译规则，并将编译后的 KieBase 存入缓存(不需要在本地编写规则文件，因此此方法暂时不用)
     *
     * @param filePath 规则文件路径
     * @param scene 场景标识
     * @return KieSession 对象
     */
    public KieSession getDrlSessionFormDrlFile(final String filePath,final String scene){
        try {

            // 设置系统的时间格式（因为 Drools 规则文件中的时间格式必须与系统时间格式一致，否则会报错）
            System.setProperty("drools.dateformat","yyyy-MM-dd");

            // 获取 KieServices 对象（这是 KIE 模块提供的 API，用于获取其他对象）
            KieServices kieServices = KieServices.Factory.get();

            // 获取 KieFileSystem 对象（用于加载规则文件）
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

            // 获取本地规则文件（即：本地编写的规则文件）
            Resource resource = kieServices.getResources().newFileSystemResource(filePath);
            // 设置资源文件类型为 DRL 文件
            resource.setResourceType(ResourceType.DRL);

            // 将规则文件存入 KieFileSystem 对象中
            kieFileSystem.write(resource);

            // 获取 KieBuilder 对象（用于编译规则文件）
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

            // 如果编译规则文件出错，则打印错误信息
            if (!kieBuilder.getResults().getMessages(Message.Level.ERROR).isEmpty()){
                // 记录错误日志
                LOGGER.error("规则文件编译出错：{}",kieBuilder.getResults().getMessages());
                // 抛出运行时异常
                throw new UtilsException(DroolsExceptionEnum.DROOLS_UTIL_INIT_FAIL_ERROR);
            }

            // 获取 KieContainer 对象（用于加载 KieBase 对象到缓存）
            KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

            // 获取 KieBase 对象（用于创建 KieSession 对象）
            KieBase kieBase = kieContainer.getKieBase();

            // 将 KieBase 对象存入缓存
            ruleMap.put(scene,kieBase);

            // 获取 KieSession 对象（用于执行规则）
            KieSession kieSession = kieBase.newKieSession();

            // 添加监听器到 KieSession 对象中，用于监听规则执行过程中的事件
            kieSession.addEventListener(new DebugRuleRuntimeEventListener());

            // 返回 KieSession 对象
            return kieSession;
        }catch (Exception e){
            // 记录错误日志
            LOGGER.error("规则引擎初始化规则失败，请查看错误信息:{}",e.getMessage());
            throw new UtilsException(DroolsExceptionEnum.DROOLS_UTIL_INIT_FAIL_ERROR);
        }
    }

    /**
     * 根据场景获取缓存中的 KieBase，创建 KieSession 对象<br/><br/>
     * 1、如果缓存中不存在 KieBase，则重新编译规则文件，并将编译后的 KieBase 存入缓存<br/>
     * 2、如果缓存中存在 KieBase，则直接创建 KieSession 对象
     *
     * @param scene 场景标识
     * @return KieSession 对象
     */
    public KieSession getDrlSessionInCache(final String scene){
        try {
            // 从缓存中查找对应场景的 KieBase
            KieBase kieBase = ruleMap.get(scene);

            // 如果缓存中不存在 KieBase，则直接返回 null
            if (kieBase == null){
                return null;
            }

            // 获取 KieSession 对象（用于执行规则）
            return kieBase.newKieSession();
        }catch (Exception e){
            // 记录错误日志
            LOGGER.error("获取 KieBase 信息错误:{}",e.getMessage());
            throw new UtilsException(DroolsExceptionEnum.DROOLS_UTIL_INIT_FAIL_ERROR);
        }
    }

    /**
     * 根据规则字符串编译规则，并验证规则是否正确（用于规则测试，目前支持测试本地规则文件，不支持测试远程规则文件）
     *
     * @param rule 规则字符串
     * @return true：编译成功；false：编译失败
     */
    public Boolean compileRule(final String rule){
        // 为了防止规则文件名字重复，此处加上时间戳( 格式：场景标识（测试场景） + 时间戳 + .drl)
        String fileName = "testRule" + System.currentTimeMillis() + ".drl";

        try {

            // 获取 KieServices 对象（这是 KIE 模块提供的 API，用于获取其他对象）
            KieServices kieServices = KieServices.Factory.get();

            // 获取 KieFileSystem 对象（用于加载规则文件）
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

            // 获取本地规则文件（即：本地编写的规则文件）
            kieFileSystem.write("src/main/resources/drools/rules" + fileName,rule.getBytes(StandardCharsets.UTF_8));

            // 获取 KieBuilder 对象（用于编译规则文件）
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

            // 如果没有编译出错，则返回 true 表示编译成功，测试通过
            if (kieBuilder.getResults().getMessages(Message.Level.ERROR).isEmpty()){
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            // 记录错误日志
            LOGGER.error("获取 KieBase 信息错误:{}", e.getMessage());
            throw new UtilsException(DroolsExceptionEnum.DROOLS_UTIL_INIT_FAIL_ERROR);
        }

        // 返回 false 表示编译失败，测试不通过
        return Boolean.FALSE;
    }

    /**
     * 删除指定场景的 KieBase 规则缓存
     *
     * @param scene 场景标识
     */
    public static void removeRuleMap(final String scene){
        ruleMap.remove(scene);
    }

    /**
     * 清空所有规则缓存
     */
    public static void clearRuleMap() {
        ruleMap.clear();
    }
}

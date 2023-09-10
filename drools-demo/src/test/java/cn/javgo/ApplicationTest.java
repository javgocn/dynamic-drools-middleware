package cn.javgo;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    @Test
    public void testGetKieContainer() {
        // 从工厂中创建 KieServices 对象
        KieServices kieServices = KieServices.Factory.get();
        // 从 KieServices 中获取 KieContainer 对象，其会加载 kmodule.xml 文件并 load 规则文件
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        // 从 KieContainer 中获取 KieBase 对象(不提供 kBaseName 则获取默认的 KieBase)
        KieBase kieBase = kieContainer.getKieBase("KBase1");
        // 从 KieBase 中获取 KieSession 对象(不提供 kSessionName 则获取默认的 KieSession)
        // 有状态的 KieSession
        KieSession kieSession1 = kieContainer.newKieSession("KSession2_1");
        // 无状态的 KieSession
        kieContainer.newStatelessKieSession("KSession2_2");
    }

    @Test
    public void testKieModuleModel(){
        // 从工厂中创建 KieServices 对象
        KieServices kieServices = KieServices.Factory.get();
        // 创建 KieModuleModel 对象
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        // 通过 KieModuleModel 创建 KieBaseModel 对象
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel("KBase1")
                .setDefault(true) // 设置为默认的 KieBase
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY) // 设置 KieBase 的 equals 行为
                .setEventProcessingMode(EventProcessingOption.STREAM); // 设置 KieBase 的事件处理模式

        // 通过 KieBaseModel 创建 KieSessionModel 对象
        KieSessionModel kieSessionModel = kieBaseModel.newKieSessionModel("KSession1")
                .setDefault(true) // 设置为默认的 KieSession
                .setType(KieSessionModel.KieSessionType.STATEFUL) // 设置 KieSession 的类型
                .setClockType(ClockTypeOption.get("realtime")); // 设置 KieSession 的时钟类型

        // 通过 KieServices 创建 KieFileSystem 对象（虚拟文件系统）
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        // 将 KieModuleModel 转换成 XML 文件后存入虚拟文件系统
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
    }
}

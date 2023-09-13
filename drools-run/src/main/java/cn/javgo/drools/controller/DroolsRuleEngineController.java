package cn.javgo.drools.controller;

import cn.javgo.drools.api.CommonResult;
import cn.javgo.drools.dto.fact.RuleExecutionObject;
import cn.javgo.drools.service.drools.DroolsRuleEngineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * Drools 规则引擎 Controller
 */
@Api(tags = "DroolsRuleEngineController")
@Tag(name = "DroolsRuleEngineController", description = "Drools 规则引擎 Controller")
@RestController
public class DroolsRuleEngineController {

    @Resource
    private DroolsRuleEngineService droolsRuleEngineService;


    @ApiOperation(value = "执行规则引擎")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleExecutionObject", value = "规则执行对象", required = true, dataType = "RuleExecutionObject"),
            @ApiImplicitParam(name = "scene", value = "业务场景名称", required = true, dataType = "String")
    })
    public CommonResult<RuleExecutionObject> execute(RuleExecutionObject ruleExecutionObject,String scene){
        RuleExecutionObject executionObject = droolsRuleEngineService.execute(ruleExecutionObject, scene);
        return CommonResult.success(executionObject);
    }
}

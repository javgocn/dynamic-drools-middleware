package cn.javgo.drools.mapper;

import cn.javgo.drools.model.MetaRuleAttribute;
import cn.javgo.drools.model.MetaRuleAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetaRuleAttributeMapper {
    long countByExample(MetaRuleAttributeExample example);

    int deleteByExample(MetaRuleAttributeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetaRuleAttribute row);

    int insertSelective(MetaRuleAttribute row);

    List<MetaRuleAttribute> selectByExample(MetaRuleAttributeExample example);

    MetaRuleAttribute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MetaRuleAttribute row, @Param("example") MetaRuleAttributeExample example);

    int updateByExample(@Param("row") MetaRuleAttribute row, @Param("example") MetaRuleAttributeExample example);

    int updateByPrimaryKeySelective(MetaRuleAttribute row);

    int updateByPrimaryKey(MetaRuleAttribute row);
}
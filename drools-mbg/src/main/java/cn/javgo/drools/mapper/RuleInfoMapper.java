package cn.javgo.drools.mapper;

import cn.javgo.drools.model.RuleInfo;
import cn.javgo.drools.model.RuleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleInfoMapper {
    long countByExample(RuleInfoExample example);

    int deleteByExample(RuleInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RuleInfo row);

    int insertSelective(RuleInfo row);

    List<RuleInfo> selectByExample(RuleInfoExample example);

    RuleInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RuleInfo row, @Param("example") RuleInfoExample example);

    int updateByExample(@Param("row") RuleInfo row, @Param("example") RuleInfoExample example);

    int updateByPrimaryKeySelective(RuleInfo row);

    int updateByPrimaryKey(RuleInfo row);
}
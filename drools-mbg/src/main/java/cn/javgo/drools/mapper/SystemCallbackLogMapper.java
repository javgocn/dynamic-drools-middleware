package cn.javgo.drools.mapper;

import cn.javgo.drools.model.SystemCallbackLog;
import cn.javgo.drools.model.SystemCallbackLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemCallbackLogMapper {
    long countByExample(SystemCallbackLogExample example);

    int deleteByExample(SystemCallbackLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemCallbackLog row);

    int insertSelective(SystemCallbackLog row);

    List<SystemCallbackLog> selectByExample(SystemCallbackLogExample example);

    SystemCallbackLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") SystemCallbackLog row, @Param("example") SystemCallbackLogExample example);

    int updateByExample(@Param("row") SystemCallbackLog row, @Param("example") SystemCallbackLogExample example);

    int updateByPrimaryKeySelective(SystemCallbackLog row);

    int updateByPrimaryKey(SystemCallbackLog row);
}
package cn.javgo.drools.mapper;

import cn.javgo.drools.model.ApiAccessLog;
import cn.javgo.drools.model.ApiAccessLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApiAccessLogMapper {
    long countByExample(ApiAccessLogExample example);

    int deleteByExample(ApiAccessLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ApiAccessLog row);

    int insertSelective(ApiAccessLog row);

    List<ApiAccessLog> selectByExample(ApiAccessLogExample example);

    ApiAccessLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") ApiAccessLog row, @Param("example") ApiAccessLogExample example);

    int updateByExample(@Param("row") ApiAccessLog row, @Param("example") ApiAccessLogExample example);

    int updateByPrimaryKeySelective(ApiAccessLog row);

    int updateByPrimaryKey(ApiAccessLog row);
}
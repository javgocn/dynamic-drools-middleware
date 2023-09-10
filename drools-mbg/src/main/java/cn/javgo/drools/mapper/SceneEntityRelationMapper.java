package cn.javgo.drools.mapper;

import cn.javgo.drools.model.SceneEntityRelation;
import cn.javgo.drools.model.SceneEntityRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SceneEntityRelationMapper {
    long countByExample(SceneEntityRelationExample example);

    int deleteByExample(SceneEntityRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SceneEntityRelation row);

    int insertSelective(SceneEntityRelation row);

    List<SceneEntityRelation> selectByExample(SceneEntityRelationExample example);

    SceneEntityRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") SceneEntityRelation row, @Param("example") SceneEntityRelationExample example);

    int updateByExample(@Param("row") SceneEntityRelation row, @Param("example") SceneEntityRelationExample example);

    int updateByPrimaryKeySelective(SceneEntityRelation row);

    int updateByPrimaryKey(SceneEntityRelation row);
}
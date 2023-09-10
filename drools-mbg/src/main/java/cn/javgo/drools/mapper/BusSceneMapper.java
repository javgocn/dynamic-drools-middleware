package cn.javgo.drools.mapper;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.BusSceneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusSceneMapper {

    /**
     * 根据条件查询
     *
     * @param example 条件
     * @return 记录数
     */
    long countByExample(BusSceneExample example);

    /**
     * 根据条件删除
     *
     * @param example 条件
     * @return 删除结果
     */
    int deleteByExample(BusSceneExample example);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入（匹配有值的字段，不会插入 null）
     *
     * @param row 插入的数据
     * @return 插入结果
     */
    int insert(BusScene row);

    /**
     * 插入非空字段
     * @param row 插入的数据
     * @return 插入结果
     */
    int insertSelective(BusScene row);

    /**
     * 根据条件查询
     *
     * @param example 条件
     * @return 业务场景集合
     */
    List<BusScene> selectByExample(BusSceneExample example);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 业务场景
     */
    BusScene selectByPrimaryKey(Long id);

    /**
     * 根据条件更新非空字段
     *
     * @param row 更新的数据
     * @param example 条件
     * @return 更新结果
     */
    int updateByExampleSelective(@Param("row") BusScene row, @Param("example") BusSceneExample example);

    /**
     * 根据条件更新
     *
     * @param row 更新的数据
     * @param example 条件
     * @return 更新结果
     */
    int updateByExample(@Param("row") BusScene row, @Param("example") BusSceneExample example);

    /**
     * 根据主键更新非空字段
     *
     * @param row 更新的数据
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(BusScene row);

    /**
     * 根据主键更新
     *
     * @param row 更新的数据
     * @return 更新结果
     */
    int updateByPrimaryKey(BusScene row);
}
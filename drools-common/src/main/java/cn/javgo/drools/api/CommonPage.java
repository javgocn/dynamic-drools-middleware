package cn.javgo.drools.api;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * 通用分页封装类
 */
@Data
public class CommonPage<T> {

    // 当前页码（前端传过来的）
    private Integer pageNum;

    // 每页数量（前端传过来的）
    private Integer pageSize;

    // 总页数（通过计算得到的）
    private Integer totalPage;

    // 总条数（数据库中查询出来的总条数）
    private Long total;

    // 分页数据（数据库中查询出来的分页数据）
    private List<T> list;

    /**
     * 将 PageHelper 分页后的 list 转为分页信息（项目中使用的是 PageHelper 分页）
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalPage(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 将 Spring Data 分页后的 list 转为分页信息（留作备用）
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo){
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotalPage(pageInfo.getTotalPages());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }
}

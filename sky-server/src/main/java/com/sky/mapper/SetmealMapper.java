package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemBySetmealId(Long id);

    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    Page<SetmealVO> search(SetmealPageQueryDTO setmealPageQueryDTO);

//    @Insert("insert into setmeal (category_id,name,price,status,description,image,create_time,update_time,create_user,update_user) " +
//            "value (#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    Setmeal getByIds(List<Long> ids);

    @Delete("delete from setmeal where id = #{id}")
    void delete(Long id);

    @Update("update setmeal set status = #{status} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void updateStatus(Long id, Integer status);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);
}

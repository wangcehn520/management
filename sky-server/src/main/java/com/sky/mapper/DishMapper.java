package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getDish(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    @Delete("delete from dish where id = #{id}")
    void deletebyId(Long id);

    List<Dish> getDishByIds(List<Long> ids);

    List<DishDTO> getDishAndCategoryByIds(List<Long> ids);

    void updateStatus(Integer status, Long id);

    List<Dish> list(Dish dish);

    @Select("select * from dish where category_id = #{categoryId} and status = 1")
    List<DishDTO> getBycategoryId(Long categoryId);
}

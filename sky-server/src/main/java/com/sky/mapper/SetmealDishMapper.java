package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/16 18:30
 * @Description:
 */
@Mapper
public interface SetmealDishMapper {

//    @Insert("insert  into setmeal_dish (setmeal_id,dish_id,name,price,copies)" +
//            "value (#{setmealId},#{dishId},#{name},#{price},#{copies})")
     void save(List<SetmealDish> setmealDishes);

     @Select("select * from setmeal_dish where setmeal_id = #{id}")
     List<SetmealDish> getSetmealDsihBySetmealId(Long id);


     @Delete("delete from setmeal_dish where setmeal_id = #{id}")
     void deleteBySetmealId(Long id);
}

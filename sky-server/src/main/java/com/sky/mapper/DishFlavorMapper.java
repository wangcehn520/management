package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/7 2:56
 * @Description:
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量保存
     * @param flavors
     */
    void saveBatchDishFlavors(List<DishFlavor> flavors);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    List<DishFlavor> getDishFlavorById(Long id);

    int deleteDishFlavorById(Long id);
}

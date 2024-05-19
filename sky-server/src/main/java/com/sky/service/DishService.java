package com.sky.service;


import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增操作
     * @param dishDTO
     */
    void saveDish(DishDTO dishDTO);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id获取dishDTO
     * @param id
     * @return
     */
    DishDTO getDish(Long id);

    /**
     * 修改数据
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    /**
     * 删除数据
     * @param ids
     */
    void deleteIds(List<Long> ids);

    void updateStatus(Integer status, Long id);

    List<DishVO> listWithFlavor(Dish dish);

    List<DishDTO> getBycategoryId(Long categoryId);
}

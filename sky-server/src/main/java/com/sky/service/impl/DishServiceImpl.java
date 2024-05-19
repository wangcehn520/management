package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/7 1:59
 * @Description:
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void saveDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.save(dish);
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (!flavors.isEmpty()){
           flavors.forEach(flavor->flavor.setDishId(dishId));
        }
        dishFlavorMapper.saveBatchDishFlavors(flavors);
    }

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page pagerResult =dishMapper.pageDish(dishPageQueryDTO);
        PageResult result = new PageResult();
        result.setTotal(pagerResult.getTotal());
        result.setRecords(pagerResult.getResult());
        return result;
    }

    /**
     * 根据id获取dishDTO
     *
     * @param id
     * @return
     */
    @Override
    public DishDTO getDish(Long id) {
        DishDTO dishDTO = new DishDTO();
        Dish dish = dishMapper.getDish(id);
        List<DishFlavor> dishFlavors=dishFlavorMapper.getDishFlavorById(dish.getId());
        BeanUtils.copyProperties(dish,dishDTO);
        dishDTO.setFlavors(dishFlavors);
        return dishDTO;
    }

    /**
     * 修改数据
     *
     * @param dishDTO
     */
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteDishFlavorById(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(flavor -> flavor.setDishId(dishDTO.getId()));
        dishFlavorMapper.saveBatchDishFlavors(flavors);
    }

    /**
     * 删除数据
     *
     * @param ids
     */
    @Override
    public void deleteIds(List<Long> ids) {
        List<Dish> dishes = dishMapper.getDishByIds(ids);
        List<DishDTO> dtoList= dishMapper.getDishAndCategoryByIds(ids);
        if (dishes.isEmpty() && dtoList.isEmpty()){
            ids.forEach(id -> {
                dishMapper.deletebyId(id);
                dishFlavorMapper.deleteDishFlavorById(id);
            });
        }else {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH + "或" + MessageConstant.DISH_ON_SALE);
        }
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        dishMapper.updateStatus(status,id);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getDishFlavorById(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    @Override
    public List<DishDTO> getBycategoryId(Long categoryId) {
        if (categoryId==null){
            throw new BaseException("空id异常！");
        }
        return dishMapper.getBycategoryId(categoryId);
    }
}

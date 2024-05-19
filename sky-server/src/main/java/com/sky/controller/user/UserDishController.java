package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/7 1:56
 * @Description:
 */
@RestController("UserDishController")
@RequestMapping("/user/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class UserDishController {
    @Autowired
    private DishService dishService;
    @GetMapping("list")
    public Result<List<DishVO>> list(@RequestParam Long categoryId){
        log.info("categoryId:{}",categoryId);
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }
}

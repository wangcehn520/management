package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/7 1:56
 * @Description:
 */
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result saveDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品信息：{}",dishDTO);
        dishService.saveDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        return Result.success(dishService.pageDish(dishPageQueryDTO));
    }

    @GetMapping("/{id}")
    @ApiOperation("回显数据")
    public Result<DishDTO> getDish(@PathVariable Long id){
        return Result.success(dishService.getDish(id));
    }
    @PutMapping
    @ApiOperation("修改数据")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("删除数据")
    public Result delete(@RequestParam List<Long> ids){
        dishService.deleteIds(ids);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status,Long id){
        log.info("status:{} id :{}",status,id);
        dishService.updateStatus(status,id);
        return Result.success();
    }

    @GetMapping("list")
    public Result<List<DishDTO>> list(Long categoryId){
        List<DishDTO> list=dishService.getBycategoryId(categoryId);
        return Result.success(list);
    }
}

package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/17 18:10
 * @Description:
 */
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult result=setmealService.page(setmealPageQueryDTO);
        return Result.success(result);
    }

    @PostMapping
    @ApiOperation("添加套餐")
    public Result saveSetmealDish(@RequestBody SetmealDTO setmealDTO){
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmeal(@PathVariable Long id){
        SetmealVO setmeal=setmealService.getSetmeal(id);
        return Result.success(setmeal);
    }

    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids){
        setmealService.delete(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result status(@RequestParam("id") Long id,@PathVariable Integer status){
        setmealService.updateStatus(id,status);
     return Result.success();
    }

    @PutMapping
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
}

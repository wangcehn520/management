package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LeLe
 * @date 2024/5/16 21:24
 * @Description:
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
public class ShopCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("add")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.add(shoppingCartDTO);
        return Result.success("添加成功");
    }
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        return shoppingCartService.list();
    }

    @DeleteMapping("clean")
    public Result clear(){
        shoppingCartService.clearAll();
        return Result.success("操作成功");
    }

    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }
}

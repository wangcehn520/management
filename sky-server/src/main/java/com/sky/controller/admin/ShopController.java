package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author LeLe
 * @date 2024/5/9 16:12
 * @Description:
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("修改营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        stringRedisTemplate.opsForValue().set(KEY,status.toString());
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        String s = stringRedisTemplate.opsForValue().get(KEY);

        return Result.success(Integer.parseInt(s));
    }
}

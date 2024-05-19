package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LeLe
 * @date 2024/5/9 16:12
 * @Description:
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        String s = stringRedisTemplate.opsForValue().get(KEY);
        return Result.success(Integer.parseInt(s));
    }
}

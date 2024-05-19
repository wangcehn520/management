package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;

import java.util.List;

public interface ShoppingCartService {
    void add(ShoppingCartDTO shoppingCartDTO);

    Result<List<ShoppingCart>> list();

    void clearAll();

    void sub(ShoppingCartDTO shoppingCartDTO);
}

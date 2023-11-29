package com.itheima.prize.commons.db.service;

import com.itheima.prize.commons.utils.ApiResult;

import javax.servlet.http.HttpServletRequest;

public interface CardUserService {

    ApiResult login(String account, String password, HttpServletRequest request);
}

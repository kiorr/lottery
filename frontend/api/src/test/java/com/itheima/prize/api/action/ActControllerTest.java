package com.itheima.prize.api.action;


import com.alibaba.fastjson.JSON;
import com.itheima.prize.commons.db.entity.CardGame;
import com.itheima.prize.commons.db.entity.CardGameProduct;
import com.itheima.prize.commons.db.entity.CardGameRules;
import com.itheima.prize.commons.db.entity.CardProductDto;
import com.itheima.prize.commons.db.mapper.CardGameMapper;
import com.itheima.prize.commons.db.mapper.CardGameProductMapper;
import com.itheima.prize.commons.db.mapper.CardGameRulesMapper;
import com.itheima.prize.commons.db.mapper.GameLoadMapper;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ActControllerTest {

    @Autowired
    private CardGameMapper gameMapper;

    @Autowired
    private CardGameProductMapper gameProductMapper;

    @Autowired
    private GameLoadMapper gameLoadMapper;

    @Autowired
    private CardGameRulesMapper gameRulesMapper;
    @Test
    void selectTest() throws InterruptedException {

        List<CardGameRules> cardGameRules = gameRulesMapper.selectByGameid(43);
        System.out.println(cardGameRules.toString());
        System.out.println();
    }
}

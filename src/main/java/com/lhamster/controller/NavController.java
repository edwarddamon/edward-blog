package com.lhamster.controller;

import com.lhamster.domain.BlogNav;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.NavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
@Slf4j
@RestController
public class NavController {
    @Autowired
    private NavService navService;

    /**
     * 导航栏数据列表
     *
     * @return
     */
    @GetMapping("/navs")
    public Result<List<BlogNav>> navs() {
        return new Result<>(ResultCode.SUCCESS, navService.navs());
    }

    /**
     * 查询某个导航栏数据
     *
     * @param id
     * @return
     */
    @GetMapping("/nav")
    public Result nav(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        return new Result<>(ResultCode.SUCCESS, navService.nav(id));
    }

    /**
     * 添加导航栏数据
     *
     * @param name   内容|不能为空
     * @param navUrl 地址|不能为空
     * @return
     */
    @PostMapping("/nav")
    public Result addNav(String name, String navUrl) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(navUrl)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 添加nav
            navService.addNav(name, navUrl);
            return new Result(ResultCode.NAV_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.NAV_ADD_FAILED);
        }
    }

    /**
     * 更新导航栏数据
     *
     * @param id     navId|不能为空
     * @param name   nav内容|不能为空
     * @param navUrl nav地址|不能为空
     * @return
     */
    @PutMapping("/nav")
    public Result updateNav(Integer id, String name, String navUrl) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(name) || StringUtils.isEmpty(navUrl)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 更新nav
            navService.updateNav(id, name, navUrl);
            return new Result(ResultCode.NAV_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.NAV_UPDATE_FAILED);
        }
    }

    /**
     * 删除导航栏数据
     *
     * @param id nav的id|不能为空
     * @return
     */
    @DeleteMapping("/nav")
    public Result deleteNav(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除nav
            navService.deleteNav(id);
            return new Result(ResultCode.NAV_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.NAV_DELETE_FAILED);
        }
    }
}

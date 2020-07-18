package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogInform;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.InformService;
import com.lhamster.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/18
 */
@RestController
public class InformController {
    @Autowired
    private Audience audience;

    @Autowired
    private InformService informService;

    /**
     * 消息列表
     *
     * @param vo
     * @param read
     * @param request
     * @return
     */
    @GetMapping("/inform")
    public Result<List<BlogInform>> inform(QueryVo vo, Boolean read, HttpServletRequest request) {
        if (StringUtils.isEmpty(vo.getPageNum()) || StringUtils.isEmpty(vo.getPageSize())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        return informService.selectAll(vo, read, userId);
    }

    /**
     * 设置消息已读
     *
     * @param informId
     * @return
     */
    @PutMapping("/inform")
    public Result setReadInform(Integer informId) {
        if (StringUtils.isEmpty(informId)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 设置已读
            informService.setRead(informId);
            return new Result(ResultCode.INFORM_READ_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.INFORM_READ_FAILED);
        }
    }

    /**
     * 设置全部已读
     *
     * @param request
     * @return
     */
    @PatchMapping("/inform")
    public Result setAllRead(HttpServletRequest request) {
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            // 设置已读
            informService.setAllRead(userId);
            return new Result(ResultCode.INFORM_READ_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.INFORM_READ_FAILED);
        }
    }

    /**
     * 删除消息通知
     *
     * @param informId
     * @return
     */
    @DeleteMapping("/inform")
    public Result deleteInform(Integer informId) {
        if (StringUtils.isEmpty(informId)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除消息通知
            informService.deleteInform(informId);
            return new Result(ResultCode.INFORM_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.INFORM_DELETE_FAILED);
        }
    }
}

package com.lhamster.controller;

import com.lhamster.domain.BlogUpdatelog;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.UpdateLogService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/23
 */
@Slf4j
@RestController
public class UpdateLogController {
    @Autowired
    private UpdateLogService updateLogService;

    /**
     * 更新日志列表
     *
     * @return
     */
    @GetMapping("/updateLog")
    public Result<List<BlogUpdatelog>> updateLog() {
        return new Result<>(ResultCode.SUCCESS, updateLogService.updateLogList());
    }

    /**
     * 添加更新日志
     *
     * @param content markdown原数据
     * @return
     */
    @PostMapping("/update-log")
    public Result addUpdateLog(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 添加日志
            updateLogService.addUpdateLog(content);
            return new Result(ResultCode.LOG_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.LOG_ADD_FAILED);
        }
    }

    /**
     * 修改更新日志
     *
     * @param id
     * @param content
     * @return
     */
    @PutMapping("/update-log")
    public Result updateUpdateLog(Integer id, String content) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 修改日志
            updateLogService.updateUpdateLog(id, content);
            return new Result(ResultCode.LOG_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.LOG_UPDATE_FAILED);
        }
    }

    /**
     * 删除更新日志
     *
     * @param id
     * @return
     */
    @DeleteMapping("/update-log")
    public Result deleteLog(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除日志
            updateLogService.deleteLog(id);
            return new Result(ResultCode.LOG_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.LOG_DELETE_FAILED);
        }
    }
}

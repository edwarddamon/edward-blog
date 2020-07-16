package com.lhamster.controller;

import com.lhamster.domain.BlogCategory;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分类列表
     *
     * @return
     */
    @GetMapping("/category")
    public Result<List<BlogCategory>> category() {
        return categoryService.queryAllCategory();
    }

    /**
     * 添加分类
     *
     * @param name
     * @return
     */
    @PostMapping("/category")
    public Result insertCategory(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 查询该分类是否已存在
            BlogCategory res = categoryService.selectOne(name);
            if (!StringUtils.isEmpty(res)) {
                return new Result(ResultCode.CATEGORY_ADD_EXISTED);
            }
            // 添加分类
            categoryService.insertCategory(name);
            return new Result(ResultCode.CATEGORY_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.CATEGORY_ADD_FAILED);
        }
    }

    /**
     * 修改分类
     *
     * @param blogCategory
     * @return
     */
    @PutMapping("/category")
    public Result updateCategory(BlogCategory blogCategory) {
        if (StringUtils.isEmpty(blogCategory.getCateId())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 修改分类
            categoryService.updateCategory(blogCategory);
            return new Result(ResultCode.CATEGORY_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.CATEGORY_UPDATE_FAILED);
        }
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/category")
    public Result deleteCategory(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            categoryService.deleteCategory(id);
            return new Result(ResultCode.CATEGORY_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.CATEGORY_DELETE_FAILED);
        }
    }
}

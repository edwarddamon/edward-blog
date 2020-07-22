package com.lhamster.controller;

import com.lhamster.domain.BlogBook;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.BookService;
import com.lhamster.util.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/22
 */
@Slf4j
@RestController
public class BookController {
    /*图片格式*/
    private static final List<String> suffix = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));

    @Autowired
    private BookService bookService;

    /**
     * 书籍列表
     *
     * @return
     */
    @GetMapping("/books")
    public Result<List<BlogBook>> book() {
        return bookService.book();
    }

    /**
     * 上传书籍头像
     *
     * @param file
     * @param session
     * @return
     */
    @PostMapping("/book_pic")
    public Result article_pic(@RequestParam("file") MultipartFile file, HttpSession session) {
        if (file.isEmpty()) {
            throw new ResultException(ResultCode.ARTICLE_UPLOAD_FAILED);
        }
        String filename = file.getOriginalFilename();
        log.info("文件名:" + filename);
        // uuid生成随机的文件名
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        // 新的文件名+文件后缀
        String fileSuffix = filename.substring(filename.lastIndexOf("."));
        if (!suffix.contains(fileSuffix)) { // 不是指定格式
            throw new ResultException(ResultCode.USER_HEADPIC_TYPE_ERROR);
        }
        filename = uuid + fileSuffix;
        // 即将写入磁盘的地址
        File localFile = new File(session.getServletContext().getRealPath("/") + filename);
        try {
            // 将MultipartFile转为File从内存中写入磁盘
            file.transferTo(localFile);
            // 新头像的地址
            String articlePicUrl = TencentCOSUtil.uploadObject(localFile, "bookPicture/" + filename);
            return new Result<>(ResultCode.BOOK_UPLOAD_SUCCESS, articlePicUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BOOK_UPLOAD_FAILED);
        }
    }

    /**
     * 添加书籍
     *
     * @param name
     * @param des
     * @param picUrl
     * @return
     */
    @PostMapping("/book")
    public Result addBook(String name, String des, String picUrl) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(des) || StringUtils.isEmpty(picUrl)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 添加书籍
            bookService.addBook(name, des, picUrl);
            return new Result(ResultCode.BOOK_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BOOK_ADD_FAILED);
        }
    }

    /**
     * 查询一本书的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/book")
    public Result<BlogBook> queryBook(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        return new Result<>(ResultCode.SUCCESS, bookService.queryBook(id));
    }

    /**
     * 修改书籍
     *
     * @param id
     * @param name
     * @param des
     * @param newPicUrl
     * @return
     */
    @PutMapping("/book")
    public Result updateBook(Integer id, String name, String des, String newPicUrl) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            //  修改书籍内容
            bookService.updateBook(id, name, des, newPicUrl);
            return new Result(ResultCode.BOOK_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BOOK_UPDATE_FAILED);
        }
    }

    /**
     * 删除书籍
     *
     * @param id
     * @return
     */
    @DeleteMapping("/book")
    public Result deleteBook(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除书籍
            bookService.deleteBook(id);
            return new Result(ResultCode.BOOK_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BOOK_DELETE_FAILED);
        }
    }
}

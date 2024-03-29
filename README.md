# vue+springboot前后端分离的个人博客（前台+后台）
介绍：个人博客系统，前端包括前台和后台，前台和后台分别采用vue-cli脚手架实现的两个项目；后端采用springboot实现，前后端采用resutful风格的API接口，json传递数据。

## 1.项目地址

### 项目源码地址：
前端前台：https://gitee.com/edwarddamon/edward-blog-front

前端后台：https://gitee.com/edwarddamon/edward-blog-back

后端服务器：https://github.com/edwarddamon/edward-blog/tree/center （这里一定记得从center分支下载，master出问题之前push不了，切记切记！！！）

### 项目使用：
- 服务器端的application.yml中修改mysql账号密码和redis的ip
- 服务器端util类中的腾讯云静态信息修改成自己的就ok了（默认的无效）

## 2.功能模块
用户管理模块、博客模块（博客分类、博客及博客评论）、消息通知模块、留言模块、博主推荐书籍模块、博主日记模块、关于博主及更新日志模块、友链模块、网站优化建议模块（功能建议、BUG反馈）和首页展示的一些琐碎数据（导航栏、标签、博主信息等）

## 3.技术栈&&相关工具
### 前端
- vue
- vuex
- iview ui组件库
- mavon-editor
### 后端
 - JWT鉴权
 - Restful风格API
 - springboot整合springmvc、spring
 - mybatis
### 数据库
 - mysql
 - redis做缓存
### 部署
 - 阿里云CentOs7云服务器
 - nginx反向代理服务器部署前端vue项目
 - springboot项目打包成jar包直接运行
## 4.第三方
 - QQ三方登录
 - 腾讯云短信服务
 - 腾讯云存储桶存放图片文件
 - Markdown 编辑器：mavon-editor

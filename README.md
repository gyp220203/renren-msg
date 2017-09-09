**项目说明** 
- renren-security是一个轻量级权限管理系统，其核心设计目标是开发迅速、学习简单、轻量级、易扩展
- 使用renren-security搭建项目，只需编写30%左右代码，其余的代码交给系统自动生成
- 一个月的工作量，一周就能完成，剩余的时间可以陪家人、朋友、撩妹、钓凯子等，从此踏入高富帅、白富美行业
- 也是接私活的利器，能快速完成项目并交付，轻松赚取外快，实现财务自由，走向人生巅峰（接私活赚了钱，可以给作者打赏点辛苦费，让作者更有动力持续优化、完善）
 


**具有如下特点** 
- 轻量级的权限系统，只涉及Spring、Shiro、Mybatis后端框架，降低学习使用成本
- 友好的代码结构及注释，便于阅读及二次开发
- 支持HTML、JSP、Velocity、Freemarker等视图，零技术门槛
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 页面交互使用Vue2.x，极大的提高了开发效率
- 完善的代码生成机制，可在线生成entity、xml、dao、service、html、js、sql代码，减少70%以上的开发任务
- 引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
- 引入API模板，根据token作为登录令牌，极大的方便了APP接口开发
- 引入Hibernate Validator校验框架，轻松实现后端校验
- 引入云存储服务，已支持：七牛云、阿里云、腾讯云等
- 引入路由机制，刷新页面会停留在当前页


**项目结构** 
```
renren-security
├─doc  项目SQL语句
├─renren-common 公共模块
│  ├─annotation 公共注解
│  ├─oss 云存储服务
│  ├─validator 后台校验
│  └─db.properties 数据库配置文件
│ 
├─renren-api API模块（接口开发）
│ 
├─renren-gen 代码生成器模块
│  ├─template 代码生成器模板（可增加或修改相应模板）
│  └─generator.properties 配置文件（配置包名、类型转换等）
│ 
├─renren-schedule 定时任务模块
│
├─renren-shiro 权限模块
│ 
├─renren-shiro-dept 部门权限模块（待开发）
│ 
├──renren-web 管理后台模块
│  ├─js 系统业务js代码
│  ├─statics 第三方库、插件等静态资源
│  ├─index.html AdminLTE主题风格（默认主题）
│  └─index1.html Layui主题风格
│
├──renre-msg 短信模块
│  ├─/io/renren/msgApi/MsgSendController.java API请求接口
│  ├─/renren-msg/src/test/java/test/ HttpPostMsg.java、MsgTO.java API请求Client
│  ├─/io/renren/service/impl/IHUYI_SendService.java 互亿短信接口服务，供参考
│  ├─/io/renren/service/impl/MsgPrepareServiceImpl.java 短信校验
│  ├─/io/renren/util/Sendsms.java 短信HTTP请求
│  └─/io/renren/task/BatchSendTask.java 短信定时批量发送任务
│
```

短信平台可以实现短信通道管理，短信模版匹配，短信服务和批量短信发送，可通过上传excel或api接口实现短信录入。

**通道管理**
-	短信通道可以理解为短信服务商的一个账户，通道需要配置访问接口登录名和密码等基本信息，为了和系统解耦，这里采用反向授权，配置授权用户后该用户就可以使用相应通道了，通道与用户是多对多关系。通过绑定服务实现具体发送逻辑。

**短信服务**
-	短信平台服务实现，不同短信服务商的接口、协议等需要不同的短信服务类去实现，这里需要添加SpringBean的注册id，并在短信通道中绑定，一个通道只能绑定一个服务。

**模版管理**
-	短信模版通过特定格式设置模版变量，签名可根据服务商要求选填。需要绑定发送的通道，一个模版只能绑定一个通道。模版设定之后，可以通过上传文件或接口请求发送变量内容来完成短信发送。

**批量发送**
-	批量发送指批次发送数据，可以每个批次只发送一条数据，也可以发送多条数据。为了防止拥堵，除了通过api接口发送单条短信时会进行实时发送，其他方式（api多条短信，excel上传短信）会在0~5分钟内统一发送。通过模版下载可以得到excel格式的模版文件，修改后点击上传文件即可。

**短信校验规则**
-	通过excel上传时会做内容为空校验。允许有空行，系统会自动略过。
-	通过api请求时，系统会先进行登录认证，再解析数据，登录失败、数据为空则会直接返回相应错误信息。

*短信校验一共分为九步:*
	-1.校验电话号码→
	-2.校验模版不能为空→
	-3.校验模版存在→
	-4.校验模版是否绑定通道→
	-5.校验短信内容非空→
	-6.校验短信内容与模版匹配（变量数一致）→
	-7.校验模版通道是否被禁用→
	-8.校验通道是否绑定短信服务→
	-9.校验用户使用权限。
		
		
本短信功能基于renren开源系统开发，如有疑问请参考一下内容，如对短信功能有任何疑问，您可发送邮件到gyp220203@gmail.com。

**如何交流、反馈、参与贡献？** 
- 项目主页：http://www.renren.io/open/
- 开发文档：http://www.renren.io/open/doc.html
- oschina仓库：http://git.oschina.net/babaio/renren-security
- github仓库：https://github.com/sunlightcs/renren-security
- [编程入门教程](http://www.renren.io)：http://www.renren.io   
- 官方QQ群：324780204、145799952
- 如需关注项目最新动态，请Watch、Star项目，同时也是对项目最好的支持
- 技术讨论、二次开发等咨询、问题和建议，请移步到QQ群324780204、145799952，我会在第一时间进行解答和回复！

**Layui主题风格：**
![输入图片说明](http://cdn.renren.io/img/2f6a43b9081e421ab8aa596155cd0ffc "在这里输入图片标题")

**AdminLTE主题风格：**
![输入图片说明](http://cdn.renren.io/img/44907148dd254064922a80cfddcc9b53 "在这里输入图片标题")
![输入图片说明](http://cdn.renren.io/img/f38a062145b141bf81157b495277d224 "在这里输入图片标题")
![输入图片说明](http://cdn.renren.io/img/65d7fb1906934e56abf8b8ca7e1c4541 "在这里输入图片标题")
![输入图片说明](http://cdn.renren.io/img/de740e471280429cb888f521e02ee787 "在这里输入图片标题")
![输入图片说明](http://cdn.renren.io/img/a8bc68f69288424697682f170ee40744 "在这里输入图片标题")
![输入图片说明](http://cdn.renren.io/img/92cd56f397754292a1a182f662a7e883 "在这里输入图片标题")
![输入图片说明](http://cdn.renren.io/img/0b56efe56fd64ed18e33a9e6dbb6e88c "在这里输入图片标题")







 **技术选型：** 
- 核心框架：Spring Framework 4.3
- 安全框架：Apache Shiro 1.3
- 视图框架：Spring MVC 4.3
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.2
- 数据库连接池：Druid 1.0
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x


 **软件需求** 
- JDK1.7+
- MySQL5.5+
- Tomcat7.0+
- Maven3.0+



 **本地部署**
- 通过git下载源码
- 创建数据库renren-security，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据【按需导入表结构及数据】
- 修改db.properties文件，更新MySQL账号和密码
- Eclipse、IDEA执行【clean package tomcat7:run】命令，即可运行项目
- 项目访问路径：http://localhost
- 非Maven方式启动，则默认访问路径为：http://localhost:8080/renren-security
- 账号密码：admin/admin


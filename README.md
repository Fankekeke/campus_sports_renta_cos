### 基于SpringBoot + Vue的校园运动器械租借系统.

体育器材管理系统、校园体育资源预约、智慧场馆器械管理、高校体育资产、校园运动器材领用

###### 管理员：
公告信息 、信用积分记录 、器械管理 、器械类型 、供应商管理 、消息通知 、支付记录 、器械采购 、租借订单 、器械维修 、员工管理 、用户管理、订单评价 、数据统计。

###### 用户：
账户注册登录、密码修改、个人信息 、我的消息 、我的订单 、支付记录 、信用积分 、在线支付 、订单评价 、订单评价。

##### 器械资源与全生命周期管理
###### 器械管理/类型： 分类录入球类、健身器材等信息，实时监控器械在库状态，确保资源高效调配。

###### 器械采购/供应商： 规范器材入库流程，记录供应商资质与采购详情，从源头把控校园体育设施质量。

###### 器械维修/保养： 记录器材破损及修复进度，定期进行安全检查，保障学生运动过程中的使用安全。

##### 租借流程与交易体系
###### 租借订单/我的： 提供便捷的器械预约与借还登记，实现从扫码租借到限时归还的全流程闭环。

###### 在线支付/记录： 整合押金及租金结算功能，自动生成清晰的财务流水，支持用户随时核对消费明细。

###### 订单评价： 收集用户对器械性能及服务满意度的反馈，通过真实评价引导器械更新与服务优化。

##### 用户信用与管理体系
###### 信用积分/记录： 建立运动诚信档案，通过逾期扣分、爱护加分机制，规范校园器材的公共使用行为。

###### 用户/员工管理： 统一管控师生权限与工作人员排班，确保租借窗口及线上系统的平稳高效运行。

##### 信息交互与智能提醒
###### 公告信息/通知： 实时发布场馆开放时间及器械上新动态，确保重要校园体育资讯精准触达师生。

###### 我的消息/通知： 自动推送预约成功、归还提醒及扣费通知，通过多端提醒防止用户产生违约费用。

##### 运营决策与数据支持
###### 数据统计： 自动汇总器械周转率、热门器械排行及营收趋势，通过数据驱动体育资源的科学配置。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

###### 管理员：
公告信息 、信用积分记录 、器械管理 、器械类型 、供应商管理 、消息通知 、支付记录 、器械采购 、租借订单 、器械维修 、员工管理 、用户管理、订单评价 、数据统计。

###### 用户：
账户注册登录、密码修改、个人信息 、我的消息 、我的订单 、支付记录 、信用积分 、在线支付 、订单评价 、订单评价。

#### 项目截图
暂无

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056863362.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056730428.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056854315.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056721340.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056844459.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1704083886487.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056821138.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056979066.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056809986.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056969074.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056804211.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056951314.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056793603.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056937611.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056787019.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056931074.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056780116.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056922420.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056773366.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056913924.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056753219.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056892987.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1740056745884.png) |  |

![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png)

#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>

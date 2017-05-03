# Cash_Book
以greenDao3为基础打造的记账类型的APP，类似于口袋记账。



![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/IMG_1256_%E5%89%AF%E6%9C%AC.jpg)    ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/IMG_1257_%E5%89%AF%E6%9C%AC.jpg)       ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/IMG_1258_%E5%89%AF%E6%9C%AC.jpg)       ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/IMG_1259_%E5%89%AF%E6%9C%AC.jpg)       ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/S70413-172347_%E5%89%AF%E6%9C%AC.jpg)  ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/S70413-173614_%E5%89%AF%E6%9C%AC.jpg)  ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/S70413-172515_%E5%89%AF%E6%9C%AC.jpg)  ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/S70413-172358_%E5%89%AF%E6%9C%AC.jpg)  ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/S70413-172406_%E5%89%AF%E6%9C%AC.jpg)  ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/S70413-172457_%E5%89%AF%E6%9C%AC.jpg)  ![](https://github.com/liuwen370494581/Cash_Book/blob/master/image/IMG_1424_%E5%89%AF%E6%9C%AC.jpg)    



本项目是业余时间编写，纯粹是享受编写代码的乐趣，由于本身工作也是十分繁重 
所以修改的bug和增加的新功能 每天也都只增加一点点 
* 2017-3-28 v1.41 更新日志： 解决了支出和收入页面跳转到HomeFragemnet页面的bug.
* 2017-3-29 v1.42 更新日志： 增加了WalletFragment中可以选择更多的账户功能.
* 2017-3-30 v1.45 更新日志： 解决了主页面添加删除 和支出收入添加删除的bug.
* 2017-3-31 v1.46 更新日志： 增加了钱包页面添加账户功能 修改了部分UI 让页面更加美观.
* 2017-4-03 v1.47 更新日志： 完善了钱包页面添加余额的一些bug，增加了转账页面.
* 2017-4-04 v1.48 更新日志： 修改了主页面的UI 并且修改了删除数据的时候报其他联动数据没有实时更新的bug。
* 2017-4-05 v1.49 更新日志： 大换血报表Ui 让其更美观，更加利于数据的展示。
* 2017-4-06 v1.50 更新日志： 完善报表UI的一些显示问题，解决了数据混乱的问题。数据库的读写使用了RxJava1.0的异步调用，插入数据和读写数据不影响UI绘制了。
* 2017-4-07 v1.51 更新日志：	解决了主页面数据过多，Ui显示错乱的问题。解决了部分内存泄漏的问题。
* 2017-4-10 v1.52 更新日志： 解决了账户消费页面数据联动的问题 增加按时间消费排序的显示 更改账户余额和消费终于可以在同一个页面显示拉
* 2017-4-11 v1.53 更新日志： 添加了消费详情页面 可以让用户更直观的了解消费了多少。增加了可以删除消费明细的功能。
* 2017-4-12 v1.54 更新日志： 经过多次测试 发现支出和消费页面删除item  当删除item的数量到0的时候 下次进入页面 无数据展示 解决了这个bug
* 2017-4-13 v1.55 更新日志： 添加了注册和登陆页面实现了部分功能。修改了首页下拉刷新的UI
* 2017-4-14 v1.60 更新日志： 登陆注册功能都已完成  修改了一些小bug 算是build(1)的版本啦 
* 2017-4-17 v1.61 更新日志： 添加了分享QQ和微博分享等功能 微信和朋友圈因为app么有审核暂不能分享。
* 2017-4-18 v1.62 更新日志： 添加了轻量级9宫格解锁页面 app可以保护隐私拉.
* 2017-4-19 v1.63 更新日志： 更新了首页UI 让其显示更加美观，添加了九宫格解锁跳转登陆页面的逻辑。
* 2017-4-21 v1.65 更新日志： 消费和收入终于和账户里的余额关联了 并修复了 如果是添加了相同账户 点击item 数据相同的bug 

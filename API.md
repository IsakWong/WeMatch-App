# WeMatch API #

API基于HTTP协议。

## 主服务器 

[http://60.205.218.75:8980/api](http://60.205.218.75:8980/api "http://60.205.218.75:8980/api")

JSON格式字符串，具体调用方式参考 Sessions 和 Bean。

### 标签 

0. 大数据
1. 互联网
2. 移动开发
3. 人工智能
4. 数学建模
5. 电子设计
6. 信息安全
7. 高性能
8. 创新创业
9. ACM
10. 游戏开发
11. 嵌入式开发
12. 机器学习


## 社区服务器 ##

社区首页

[http://wematchcommunity.applinzi.com/main.php](http://wematchcommunity.applinzi.com/main.php "http://wematchcommunity.applinzi.com/main.php")


### 社区API及调用 ###

社区文章管理后台

[http://wematchcommunity.applinzi.com](http://wematchcommunity.applinzi.com "http://wematchcommunity.applinzi.com")

多标签使用方法：

示例：

	http://wematchcommunity.applinzi.com/main-label.php?labelname=社区管&labelname2=灌水&labelname3=经验

labelname为首添项，必填，否则会出现状态错误。
labelname2和labelname3无所谓，可有可无，无先后使用次序。

示例：

	http://wematchcommunity.applinzi.com/main-label.php?labelname=社区管&labelname3=灌水

注意使用GET传参为保持稳定性 需要把汉字转换为url编码


### 社区后台管理页 ###

[http://wematchcommunity.applinzi.com/](http://wematchcommunity.applinzi.com/main.php "http://wematchcommunity.applinzi.com/")




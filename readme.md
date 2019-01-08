@author trotyzyq   
聆听的车辙

## 介绍
这个项目源自于自己平时的积累，以及公司里用到的一些工具类，当然啦，这是可以公布的。。。

## 工具类介绍
###  DateUtil2
日期工具类，时间戳，时间字符串，以及TimeStamp之间的转换，毫秒以及秒的转换都有  
相信你需要用到的都能在这里找到

### PageUtil
在PageHelper使用中自己搞的一个工具类，当然他没有`PageHelper`实现`mybatis`拦截器后的SQL注入这个功能
```$xslt
/** 初始化分页插件**/
        PageUtil pageUtil = new PageUtil();
        pageUtil.startPage(page,pageSize);
        novelVO.setPageCount(pageInfo.getPages());
        novelVO.setCurrentPage(pageUtil.getCurrentPage());
```

### RedisUtil
来自网上抄的一个工具类，但是没有实现序列化，redis-cli打开也看不出来。于是自己用了公司的代码，自己封装了。

## ProtoStuffUtil
`ProtoStuff`是基于`ProtoBuf`的序列化方式之一，这是java版本的。  
这边有个问题就是序列化的POJO属性问题，时间方面字段的序列化会有问题。

## HttpsClient
这个是自己特别为了个人项目专门封装的，Http和Https(忽略证书)都实现了，传递的参数从map到json甚至到文件。

## SolrUtil
为了小说网站，发现网上不管是python版本的或者java版本的都很少。这个自己封装的`SolrUtil`还是可以的。

## 等等



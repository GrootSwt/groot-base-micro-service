## 一、基础服务

### 1、micro-base-common

```text
bean:
    共用的非web相关的bean
dto:
    共用的dto
util:
    非web把相关的工具类
```

### 2、micro-base-web

```text
annotation:
    EnableAuditLog: 
        (1) 在服务的启动类上添加该注解会启动全局日志服务
        (2) 如果有一个服务使用了该注解，必须启动micro-log服务
    EnableMicroBaseWeb:
        (1) 所有服务上都要使用该注解
        (2) 该注解功能：
            自定义全局异常处理
            自定义的条件查询用的入参解析器（前端条件的key以's_'开始就会自动解析封装进SearchData中）
            统一自定义的服务启动结尾提示信息
            Swagger
            RestTemplate
    config:
        自定义配置类
    convertor:
        model和dto转换器
    exception:
        自定义异常
    repository:
        抽象Repository
    result:
        通用返回类：ResultDTO
        List返回类：ResultListDTO
        Page返回类：ResultPageDTO
    util:
        web相关的工具类
```

### 3、micro-log

    日志服务

### 4、micro-gateway

    网关服务

### 5、micro-user

    用户、权限服务

### 6、micro-oss

    文件服务

### 7、micro-dict
    
    数据字典服务

# Log

### 10/20/2017
1. 修改了[获取订阅的用户列表会话](https://github.com/IsakWong/WeMatch-App/blob/master/app/src/main/java/nullref/dlut/wematch/sessions/GetSubscribeUserSession.java " ")\
相关Bean:\
[UserInfo](https://github.com/IsakWong/WeMatch-App/blob/master/app/src/main/java/nullref/dlut/wematch/bean/UserInfo.java " ") \
[UserListInfo](https://github.com/IsakWong/WeMatch-App/blob/master/app/src/main/java/nullref/dlut/wematch/bean/UserListInfo.java " ") 
2. 修改了[获取用户信息会话](https://github.com/IsakWong/WeMatch-App/blob/master/app/src/main/java/nullref/dlut/wematch/sessions/GetSubscribeUserSession.java " ")\
相关Bean:\
[UserInfo](https://github.com/IsakWong/WeMatch-App/blob/master/app/src/main/java/nullref/dlut/wematch/bean/UserInfo.java " ") \
[UserListInfo](https://github.com/IsakWong/WeMatch-App/blob/master/app/src/main/java/nullref/dlut/wematch/bean/UserListInfo.java " ") 

#  会话

每个 Session 包含 Request 和 Response 两个部分。

其中 Java 代码中的变量名对应 JSON 格式中的 键名。

**Request 基类**

所有的 Request 都包含 auth 键值对，type 键值对。

auth 为用户身份认证，type 标明这次请求的目的。 

**Response 基类**

所有的Response 都包含 result 键值对，description 键值对。

举例如 LoginSession 中

```

public class Response extends Session.Response {
	public String auth;
}

public class Request extends Session.Request {
	public String type = "login";
	public String email;
    public String pwd;
}

```

对应的JSON键值对为

**Response**

"auth":"a123dxvasdf"

**Requset**

"auth":""

"type":"login"

"email":"11111@qq.com"

"pwd":"123123123"

# 
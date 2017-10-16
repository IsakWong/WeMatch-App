package nullref.dlut.wematch.sessions;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import nullref.dlut.wematch.utils.NetworkManager;
import rx.Observer;


public abstract class Session<T1 extends Session.Request, T2 extends Session.Response> {

    public T1 request;
    public T2 response;

    public boolean isFinished = true;
    /**
     * 配置好参数后调用Session的Send发送会话
     */
    public void send() {
        if(isFinished == true){
            isFinished = false;
            NetworkManager.newSession(this);
        }
        else
        {
            error(new Throwable("请求已发送"));
        }
    }


    
    public void success(T2 t) {
   
    }

    public void error(Throwable cause) {
    }

    public void complete() {
        isFinished = true;
    }



    //所有Request和Response共有的属性
    public class Request {
        String auth;
        public Request() {
            auth = NetworkManager.getUserAuth();
        }
    }
    public class Response {
        public boolean result;
        public String description;
        }



}

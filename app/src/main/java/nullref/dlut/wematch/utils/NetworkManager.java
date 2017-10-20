package nullref.dlut.wematch.utils;

/**
 * Created by IsakWong on 2017/5/18.
 */

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import nullref.dlut.wematch.sessions.Session;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import okhttp3.*;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkManager {


    //NetWork 初始化代码段
    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        avatarPrefix = ConfigDbHelper.getInstance().query(ConfigDbHelper.AvatarUrlPrefix);
        serverIp = ConfigDbHelper.getInstance().query(ConfigDbHelper.MainServerIp);
        userAuth = "";
    }

    private static OkHttpClient client;

    private static String serverIp;
    private static String userAuth;
    private static String avatarPrefix;


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static String getUserAuth() {
        return userAuth;
    }

    public static void setUserAuth(String userAuth) {
        NetworkManager.userAuth = userAuth;
    }


    public static void LoadAvatar(ImageView avatarView,String avatarUrl){
        Glide
                .with(avatarView)
                .load(avatarPrefix+avatarUrl)
                .into(avatarView);
    }
    public static <T2 extends Session.Response> void newSession(final Session session) {

        final Gson g = new Gson();
        final Observable<T2> sender = Observable.create(new Observable.OnSubscribe<T2>() {
            @Override
            public void call(final Subscriber<? super T2> subscriber) {

                String requestString = "";
                try {
                    requestString = g.toJson(session.request, session.request.getClass());
                    RequestBody requestBody = RequestBody.create(JSON, requestString);

                    Request request = new Request.Builder()
                            .url(serverIp)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                            LogToFile.e(e,"");
                            subscriber.onCompleted();
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if(null!=response){
                                String body = "";
                                body = response.body().string();
                                T2 s = (T2)g.fromJson(body, session.response.getClass());
                                subscriber.onNext(s);
                            }
                            else
                            {

                            }
                            subscriber.onCompleted();
                        }

                    });
                }
                 catch (Exception e) {
                    LogToFile.e(e,"requeset: "+requestString);
                }
            }
        });

        Subscriber<T2> subscriber = new Subscriber<T2>() {

            @Override
            public void onCompleted() {
                session.complete();
            }

            @Override
            public void onError(Throwable e) {
                session.error(e);
            }

            @Override
            public void onNext(T2 t2) {
                session.success(t2);
            }
        };
        sender
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}

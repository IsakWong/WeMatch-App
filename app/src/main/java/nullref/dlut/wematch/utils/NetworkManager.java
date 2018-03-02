package nullref.dlut.wematch.utils;

/**
 * Created by IsakWong on 2017/5/18.
 */

import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import nullref.dlut.wematch.sessions.Session;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkManager {


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client;

    private static String serverIp;
    private static String userAuth;
    private static String avatarPrefix;
    private static String imageUrlPrefix;

    //NetWork 初始化代码段
    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        avatarPrefix = ConfigDbHelper.getInstance().query(ConfigDbHelper.AvatarUrlPrefix);
        imageUrlPrefix = ConfigDbHelper.getInstance().query(ConfigDbHelper.imageUrlPrefix);
        serverIp = ConfigDbHelper.getInstance().query(ConfigDbHelper.MainServerIp);
        userAuth = "";
    }

    public static String getUserAuth() {
        return userAuth;
    }

    public static void setUserAuth(String userAuth) {
        NetworkManager.userAuth = userAuth;
    }


    public static void LoadAvatar(ImageView avatarView, String avatarUrl) {
        Glide
                .with(avatarView)
                .load(avatarPrefix + avatarUrl)
                .into(avatarView);
    }
    public static void LoadPic(ImageView imageView, String imageUrl) {
        Glide
                .with(imageView)
                .load(imageUrlPrefix + imageUrl)
                .into(imageView);
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
                            LogToFile.e(e, "");
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try{
                                String body = "";
                                body = response.body().string();
                                T2 s = (T2) g.fromJson(body, session.response.getClass());
                                subscriber.onNext(s);
                                subscriber.onCompleted();
                            }
                            catch (Exception exp){
                                subscriber.onError(exp);
                            }
                        }

                    });
                } catch (Exception e) {
                    LogToFile.e(e, "requeset: " + requestString);
                }
            }
        });

        Observer<T2> subscriber = new Observer<T2>() {

            @Override
            public void onCompleted() {
                session.complete();
            }

            @Override
            public void onError(Throwable e) {
                session.error(e);
                session.complete();
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

package nullref.dlut.wematch.sessions;

/**
 * Created by isakwong on 2017/7/16.
 */

public class UploadImgSession extends Session<UploadImgSession.Request, UploadImgSession.Response> {

    Listener listener;


    public interface Listener {
        void onUploadImg();
        void onUploadImgError(String cause);
    }

    public UploadImgSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onUploadImg();
        if (response.result == false)
            listener.onUploadImgError("上传图片失败");
    }

    public class Response extends Session.Response {


    }

    public class Request extends Session.Request{
        public String type = "uploadImage";
        //base64编码后的位图，Utils有相关编码的方法,bitmapToBase64
        public String imgBase64;
        //服务器回应的图片上传Key，没有key无法上传
        public String key;

    }
}

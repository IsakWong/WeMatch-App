package nullref.dlut.wematch.bean;

/**
 * Created by IsakWong on 2017/7/9.
 * <p>
 * 本地用户类（登陆的用户类）
 */

public class LocalUser extends UserListInfo {
    static LocalUser single;
    String key;

    public static LocalUser getSingle() {
        return single;
    }

    public static void setSingle(LocalUser single) {
        LocalUser.single = single;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

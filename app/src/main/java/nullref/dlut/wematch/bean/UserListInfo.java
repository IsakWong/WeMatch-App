package nullref.dlut.wematch.bean;

import java.io.Serializable;

/**
 * Created by IsakWong on 2017/7/9.
 *
 *
 * 用户列表的信息类，具体内容仍可能修改。
 *
 */

public class UserListInfo implements Serializable {
    public int ID;//用户的ID
    public String name;//用户昵称
    public String major;//专业
    public String avatarUrl;
    public String school;//学校
    public String shortInfo;//个人简介
    public int gender;//0女1男2保密
}

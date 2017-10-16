package nullref.dlut.wematch.bean;

import java.io.Serializable;

/**
 * Created by IsakWong on 2017/7/9.
 *
 *
 * 用户列表的信息类
 *
 */

public class User implements Serializable {
    public String name;//用户昵称
    public String major;//专业
    public String avatarUrl;
    public String school;//学校
    public String shortInfo;//个人简介
    public String qq;
    public int gender;//0女1男2保密
}

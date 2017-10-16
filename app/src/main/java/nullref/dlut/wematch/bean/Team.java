package nullref.dlut.wematch.bean;

import java.io.Serializable;

/**
 * Created by IsakWong on 2017/7/9.
 *
 *
 * 用户列表的信息类
 *
 */

public class Team implements Serializable {
    public int matchID;//服务器分配
    public String name;//昵称
    public String shortInfo;//简介
    public int number;//人数
    public User[] members;
}

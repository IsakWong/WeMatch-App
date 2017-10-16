package nullref.dlut.wematch.bean;

import java.io.Serializable;

/**
 * Created by IsakWong on 2017/7/4.
 *
 * 提交比赛页面的信息类
 *
 */

public class CommitMatch implements Serializable{
    public int ID;//比赛ID，服务器分配
    public String name;//比赛名字，25字以内
    public String shortInfo;//简短介绍 40字以内
    public String website;
    public int type;       //0 offline 1 online 2 other
    public String loc;//线下比赛的地址 40字以内
    public String content;//比赛内容 无限制
    public String startTime;//开始时间格式为 xxxx.xx.xx
    public String endTime;//比赛结束时间 格式为xxxx.xx.xx
    public int minPersons;//小队最少人数
    public int maxPersons;//小队最多人数

    public int[] labels;//比赛标签数组
    public String host;//举办方





}
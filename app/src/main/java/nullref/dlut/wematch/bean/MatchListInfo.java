package nullref.dlut.wematch.bean;

import java.io.Serializable;

/**
 * Created by isakwong on 2017/8/16.
 * <p>
 * <p>
 * 比赛列表的信息类
 */

public class MatchListInfo implements Serializable {

    public int matchID;
    public String imgUrl;
    public String name;
    public String shortInfo;
    public Label[] labels;
    public boolean liked = false;

}

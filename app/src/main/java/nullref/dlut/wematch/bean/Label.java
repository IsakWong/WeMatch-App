package nullref.dlut.wematch.bean;

import java.io.Serializable;

/**
 * Created by IsakWong on 2017/7/10.
 * <p>
 * 标签类
 */

public class Label implements Serializable {
    int id;//标签的id
    String name;//标签的名字

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

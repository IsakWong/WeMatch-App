package nullref.dlut.wematch.widgets.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import nullref.dlut.wematch.bean.Team;
import nullref.dlut.wematch.bean.User;

/**
 * Created by isakwong on 2017/10/8.
 */

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder,T2> extends RecyclerView.Adapter<T> {

    protected ArrayList<T> holders = new ArrayList<T>();
    protected ArrayList<T2> datas = new ArrayList<T2>();




    protected float mPosX;
    protected float mPosY;
    protected float mCurPosX;
    protected float mCurPosY;

    public void addCard(T2 data) {
        int i = datas.size();
        datas.add(data);
        notifyItemInserted(i);
    }

    public void clearCard() {
        int size = datas.size();
        datas.clear();
        notifyItemRangeRemoved(0,size);
    }

    public ArrayList<T2> getDatas(){
        return datas;
    }

    public T2 getCardData(int position) {
        return datas.get(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}

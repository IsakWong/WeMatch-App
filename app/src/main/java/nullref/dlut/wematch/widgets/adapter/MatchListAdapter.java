package nullref.dlut.wematch.widgets.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.utils.NetworkManager;

/**
 * Created by IsakWong on 2017/5/27.
 * <p>
 * <p>
 * CardList适配器
 * <p>
 * 支持添加卡片，删除卡片
 */

public class MatchListAdapter extends BaseAdapter<MatchListAdapter.MatchCardViewHolder, MatchListInfo> {


    CardListener listener;

    public void setListener(CardListener listener) {
        for (MatchCardViewHolder holder : holders
                ) {
            holder.setListener(listener);
        }
        this.listener = listener;
    }

    @Override
    public MatchCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_match_list, parent, false);
        MatchCardViewHolder holder = new MatchCardViewHolder(card);
        holders.add(holder);
        holder.setListener(listener);
        return holder;
    }


    @Override
    public void addCard(MatchListInfo match) {
        int position = datas.size();
        int i = datas.size() - 1;
        for (; i >= 0; i--) {
            if (match.matchID < datas.get(i).matchID) {
                break;
            }
        }
        datas.add(i + 1, match);
        notifyItemInserted(position);
    }


    @Override
    public void onBindViewHolder(MatchCardViewHolder holder, int position) {
        MatchListInfo data = (MatchListInfo) datas.get(position);
        holder.onBindView(data, position);


    }

    public interface CardListener {
        void onViewClicked(View view, int position);

        void onViewLongPressed(View view, int postion);

    }

    /**
     * ViewHolder 比赛卡片的视图容器
     */

    public class MatchCardViewHolder extends RecyclerView.ViewHolder {


        AppCompatImageView mMatchImg;
        TextView mTitle;
        TextView mLabels[] = new TextView[3];
        TextView mIntro;
        AppCompatImageView likeBtn;
        MatchListInfo data;
        CardListener listener;
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motion) {
                int actionType = motion.getAction();
                mCurPosX = motion.getX();
                mCurPosY = motion.getY();
                switch (actionType) {
                    case MotionEvent.ACTION_DOWN:
                        motion.getAction();
                        mPosX = motion.getX();
                        mPosY = motion.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        motion.getAction();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosX - mPosX > 250) {

                        } else {
                            if (mCurPosX - mPosX < -250) {
                            }
                        }
                        if (Math.abs(mCurPosX - mPosX) < 25) {
                            listener.onViewClicked(v, getLayoutPosition());
                        }

                        break;
                }
                return true;
            }
        };

        public MatchCardViewHolder(View view) {
            super(view);
            mMatchImg = (AppCompatImageView) view.findViewById(R.id.match_card_pic);
            likeBtn = (AppCompatImageView) view.findViewById(R.id.match_card_like);
            mTitle = (TextView) view.findViewById(R.id.match_card_title);
            mIntro = (TextView) view.findViewById(R.id.match_card_short_info);
            mLabels[0] = (TextView) view.findViewById(R.id.match_card_label1);
            mLabels[0].setTag(0);
            mLabels[1] = (TextView) view.findViewById(R.id.match_card_label2);
            mLabels[1].setTag(1);
            mLabels[2] = (TextView) view.findViewById(R.id.match_card_label3);
            mLabels[2].setTag(2);

            likeBtn.setOnTouchListener(onTouchListener);
            for (int i = 0; i < mLabels.length; i++) {
                mLabels[i].setOnTouchListener(onTouchListener);
            }
            view.setOnTouchListener(onTouchListener);
        }

        public CardListener getListener() {
            return listener;
        }

        public void setListener(CardListener listener) {
            this.listener = listener;
        }

        public TextView getmTitle() {
            return mTitle;
        }

        public AppCompatImageView getmMatchImg() {
            return mMatchImg;
        }

        public TextView getmIntro() {
            return mIntro;
        }

        public void onBindView(MatchListInfo data, int position) {
            this.data = data;
            getmTitle().setText(data.name);
            getmIntro().setText(data.shortInfo);
            int i = 0;
            for (i = 0; i < data.labels.length; i++) {
                mLabels[i].setText(data.labels[i].getName());
            }
            for (; i < mLabels.length; i++) {
                mLabels[i].setText("");
            }
            ViewCompat.setTransitionName(itemView.findViewById(R.id.match_card_pic), position + "");
            if (data.liked) {
                likeBtn.setImageResource(R.drawable.ic_like_red);
            } else {
                likeBtn.setImageResource(R.drawable.ic_like_gray);
            }
            NetworkManager.LoadPic(mMatchImg,data.imgUrl);
        }
    }

}




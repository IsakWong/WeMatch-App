package nullref.dlut.wematch.widgets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.widgets.RoundImageView;

/**
 * Created by IsakWong on 2017/5/27.
 * <p>
 * <p>
 * CardList适配器
 * <p>
 * 支持添加卡片，删除卡片
 */

public class UserListAdapter extends BaseAdapter<UserListAdapter.UserCardViewHolder, UserListInfo> {


    CardListener listener;


    @Override
    public UserCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user_list, parent, false);
        UserCardViewHolder holder = new UserCardViewHolder(card);
        holders.add(holder);
        holder.setListener(listener);
        return holder;
    }

    public void setListener(CardListener listener) {
        for (UserCardViewHolder holder : holders
                ) {
            holder.setListener(listener);
        }
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(UserCardViewHolder holder, int position) {
        UserListInfo data = datas.get(position);
        holder.onBindView(data, position);
    }

    public interface CardListener {
        void onViewClicked(View view, int position);

        void onViewLongPressed(View view, int postion);

    }

    /**
     * ViewHolder
     */

    public class UserCardViewHolder extends RecyclerView.ViewHolder {


        TextView userName;
        TextView userInfo;
        TextView userMajor;
        RoundImageView userAvatar;

        UserListInfo data;
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

        public UserCardViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.user_card_title);
            userInfo = (TextView) view.findViewById(R.id.user_card_short_info);
            userMajor = (TextView) view.findViewById(R.id.user_card_major);
            userAvatar = (RoundImageView) view.findViewById(R.id.user_card_avatar);

            view.setOnTouchListener(onTouchListener);
        }

        public CardListener getListener() {
            return listener;
        }

        public void setListener(CardListener listener) {
            this.listener = listener;
        }

        public void onBindView(UserListInfo data, int position) {
            this.data = data;
            if (data.name == "") {
                userInfo.setText("");
            } else {
                userName.setText(data.name);
            }
            if (data.shortInfo == "") {
                userInfo.setText("");
            } else {
                userInfo.setText(data.shortInfo);
            }
            if (data.major == "") {
                userMajor.setText("");

            } else {
                userMajor.setText(data.major);
            }

            Glide
                    .with(userAvatar)
                    .load("http://60.205.218.75:8980/image/" + data.avatarUrl)
                    .into(userAvatar);
        }
    }

}




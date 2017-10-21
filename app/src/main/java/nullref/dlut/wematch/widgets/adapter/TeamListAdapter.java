package nullref.dlut.wematch.widgets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.bean.Team;

/**
 * Created by IsakWong on 2017/5/27.
 * <p>
 * <p>
 * CardList适配器
 * <p>
 * 支持添加卡片，删除卡片
 */

public class TeamListAdapter extends BaseAdapter<TeamListAdapter.TeamCardViewHolder, Team> {


    CardListener listener;

    public void setListener(CardListener listener) {
        for (TeamCardViewHolder holder : holders
                ) {
            holder.setListener(listener);
        }
        this.listener = listener;
    }

    @Override
    public TeamCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team, parent, false);
        TeamCardViewHolder holder = new TeamCardViewHolder(card);
        holders.add(holder);
        holder.setListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TeamCardViewHolder holder, int position) {
        Team data = (Team) datas.get(position);
        holder.onBindView(data, position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface CardListener {
        void onViewClicked(View view, int position);

        void onViewLongPressed(View view, int postion);
    }

    /**
     * ViewHolder 比赛卡片的视图容器
     */

    public class TeamCardViewHolder extends RecyclerView.ViewHolder {


        TextView teamName;
        TextView teamInfo;
        ImageView userAvatar;
        Team team;
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

        public TeamCardViewHolder(View view) {
            super(view);
            teamName = (TextView) view.findViewById(R.id.team_card_title);
            teamInfo = (TextView) view.findViewById(R.id.team_card_short_info);
            userAvatar = (ImageView) view.findViewById(R.id.team_card_avatar);

            view.setOnTouchListener(onTouchListener);
        }

        public CardListener getListener() {
            return listener;
        }

        public void setListener(CardListener listener) {
            this.listener = listener;
        }

        public void onBindView(Team team, int position) {
            this.team = team;
            teamInfo.setText(team.shortInfo);
            teamName.setText(team.name);

        }
    }

}




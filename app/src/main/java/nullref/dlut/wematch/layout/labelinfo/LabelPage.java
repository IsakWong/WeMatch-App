package nullref.dlut.wematch.layout.labelinfo;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.widgets.adapter.MatchListAdapter;


/**
 * Created by IsakWong on 2017/5/14.
 */


public class LabelPage extends ColorStatusPage implements LabelContract.View {

    LabelinfoPresenter presenter = new LabelinfoPresenter(this);

    MatchListAdapter matchListAdapter;

    Unbinder unbinder;
    Label data;


    @BindView(R.id.label_background)
    ImageView labelBackground;

    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;

    @BindView(R.id.label_title)
    TextView labelTitle;

    @BindView(R.id.label_info)
    TextView labelInfo;

    @BindView(R.id.fab_like)
    FloatingActionButton fabLike;

    public LabelPage() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle saveedInstance) {
        super.onCreate(saveedInstance);
        matchListAdapter = new MatchListAdapter();
        data = (Label) getArguments().getSerializable("label");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_label_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            String encodeurl = "http://wematchcommunity.applinzi.com/main-label.php?labelname=" + Utils.toURLEncoded(data.getName());
            //labelInfoWeb.loadUrl(encodeurl);
        } catch (Exception ex) {

        }
        labelTitle.setText(data.getName());


        return view;
    }

    @Override
    public void onFollowLabel() {
        makeToast("订阅标签成功！");
    }

    @Override
    public void onFollowLabelError(String cause) {
        makeToast(cause);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        onBackPressed();
    }

    @OnClick(R.id.fab_like)
    public void onFabLikeClicked() {
        if (data != null) {
            presenter.followLabel(data);
        }
    }
}


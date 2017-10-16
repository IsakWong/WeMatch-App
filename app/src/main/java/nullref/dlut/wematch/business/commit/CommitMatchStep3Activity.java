package nullref.dlut.wematch.business.commit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.bean.Label;

/**
 * Created by IsakWong on 2017/5/14.
 */

public class CommitMatchStep3Activity extends BaseActivity implements CommitMatchPageContract.ViewStep3 {

    Label labels[];
    String labelsName[];
    int labelsID[] = new int[3];

    CommitMatchPresenter presenter;

    Unbinder unbinder;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.add_match_online)
    Spinner addMatchOnline;
    @BindView(R.id.add_match_loc)
    EditText addMatchLoc;
    @BindView(R.id.add_match_content)
    TextInputLayout addMatchContent;
    @BindView(R.id.add_match_label1)
    Spinner addMatchLabel1;
    @BindView(R.id.add_match_label2)
    Spinner addMatchLabel2;
    @BindView(R.id.add_match_label3)
    Spinner addMatchLabel3;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match_step3);
        presenter.viewStep3 = this;
        unbinder = ButterKnife.bind(this);
        addMatchLabel1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                labelsID[0] = labels[pos].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                labelsID[0] = -1;
            }
        });
        addMatchLabel2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                labelsID[1] = labels[pos].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                labelsID[1] = -1;
            }
        });
        addMatchLabel3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                labelsID[2] = labels[pos].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                labelsID[2] = -1;
            }
        });
        presenter.init();
    }


    @Override
    public void onCommitInfo() {
        makeToast("比赛信息上传完成，正在上传图片");
    }


    @Override
    public void onCommitMatch() {
        onBackPressed();
    }

    @Override
    public void error(String cause) {

    }


    @Override
    public void onLabelReceived(Label[] labels) {
        this.labels = labels;
        labelsName = new String[labels.length];
        for (int i = 0; i < labels.length; i++) {
            labelsName[i] = labels[i].getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labelsName);
        addMatchLabel1.setAdapter(adapter);
        addMatchLabel2.setAdapter(adapter);
        addMatchLabel3.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件

    }




    @OnClick(R.id.next_btn)
    public void onNextBtnClicked() {
        CommitMatchPresenter.commitMatch.content = addMatchContent.getEditText().getText().toString();
        CommitMatchPresenter.commitMatch.loc = addMatchLoc.getText().toString();
        CommitMatchPresenter.commitMatch.type = addMatchOnline.getSelectedItemPosition();
        CommitMatchPresenter.commitMatch.labels = labelsID;
        presenter.commitMatch();
    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {

    }

}




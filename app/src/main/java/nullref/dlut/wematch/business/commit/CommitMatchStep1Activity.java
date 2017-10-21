package nullref.dlut.wematch.business.commit;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.utils.Utils;

/**
 * Created by IsakWong on 2017/5/14.
 */

public class CommitMatchStep1Activity extends BaseActivity implements CommitMatchPageContract.ViewStep1 {


    CommitMatchPresenter presenter = new CommitMatchPresenter();

    Unbinder unbinder;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.add_match_img)
    ImageView addMatchImg;
    @BindView(R.id.add_match_name)
    EditText addMatchName;
    @BindView(R.id.add_match_short_info)
    EditText addMatchShortInfo;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match_step1);
        ButterKnife.bind(this);

        //ScrollUtils.addOnSoftKeyBoardVisibleListener(CommitMatchStep1Activity.this, addMatchScroll);

        /*addMatchName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollUtils.curentView = v;
                }
            }
        });

        addMatchShortInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                if (hasFocus) {
                   ScrollUtils.curentView = v;
                }
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                CommitMatchPresenter.base64 = Utils.bitmapToBase64(bitmap);
                addMatchImg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.next_btn)
    public void onNextBtnClicked() {

        CommitMatchPresenter.commitMatch.name = addMatchName.getText().toString();
        if (CommitMatchPresenter.commitMatch.name == "") {
            makeToast("比赛的名称不能为空");
            return;
        }
        try {
            jumpTo(CommitMatchStep2Activity.class, true);

        } catch (Exception excp) {
            Log.e("", "");
        }
    }


    @OnClick(R.id.add_match_img)
    public void onAddMatchImgClicked() {
    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        onBackPressed();
    }

/*

*/
}


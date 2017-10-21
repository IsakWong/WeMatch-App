package nullref.dlut.wematch.business.commit;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.utils.ScrollUtils;
import nullref.dlut.wematch.widgets.DatePickerDialog;

/**
 * Created by IsakWong on 2017/5/14.
 */

public class CommitMatchStep2Activity extends BaseActivity implements CommitMatchPageContract.ViewStep2 {

    CommitMatchPresenter presenter = new CommitMatchPresenter();

    Unbinder unbinder;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.add_match_web)
    TextInputLayout addMatchWeb;
    @BindView(R.id.add_match_host)
    TextInputLayout addMatchHost;
    @BindView(R.id.add_match_min)
    TextInputLayout addMatchMin;
    @BindView(R.id.add_match_max)
    TextInputLayout addMatchMax;
    @BindView(R.id.add_match_time_start)
    TextInputLayout addMatchTimeStart;
    @BindView(R.id.add_match_time_end)
    TextInputLayout addMatchTimeEnd;
    @BindView(R.id.fill_space)
    Space fillSpace;
    @BindView(R.id.add_match_scroll)
    ScrollView addMatchScroll;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match_step2);
        ButterKnife.bind(this);
        this.presenter = new CommitMatchPresenter();
        presenter.viewStep2 = this;

        ScrollUtils.addOnSoftKeyBoardVisibleListener(this, addMatchScroll);

        addMatchHost.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollUtils.curentView = v;
                }
            }
        });
        addMatchWeb.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollUtils.curentView = v;
                }
            }
        });
        addMatchMin.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollUtils.curentView = v;
                }
            }
        });
        addMatchMax.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollUtils.curentView = v;
                }
            }
        });
        addMatchTimeStart.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    new DatePickerDialog(getBaseContext(), 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                              int startDayOfMonth) {
                            String textString = String.format(Locale.CHINA, "%d.%d.%d", startYear,
                                    startMonthOfYear + 1, startDayOfMonth);
                            addMatchTimeStart.getEditText().setText(textString);
                            CommitMatchPresenter.commitMatch.startTime = textString;
                        }
                    }, curDate.getYear() + 1900, curDate.getMonth(), curDate.getDate()).show();
                }
            }
        });
        addMatchTimeEnd.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Date curEndTime = new Date(System.currentTimeMillis());//获取当前时间
                    new DatePickerDialog(getBaseContext(), 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                              int startDayOfMonth) {

                            String textString = String.format(Locale.CHINA, "%d.%d.%d", startYear,
                                    startMonthOfYear + 1, startDayOfMonth);
                            addMatchTimeEnd.getEditText().setText(textString);
                            CommitMatchPresenter.commitMatch.endTime = textString;
                        }
                    }, curEndTime.getYear() + 1900, curEndTime.getMonth(), curEndTime.getDate()).show();
                }
            }
        });
        addMatchTimeEnd.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        nextBtn.performClick();
                        break;
                }
                return false;
            }
        });
        /*
        inputImage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
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
                presenter.selectImage(bitmap);
                //inputImage.setText(uri.getPath());
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.next_btn)
    public void onNextBtnClicked() {

        CommitMatchPresenter.commitMatch.host = addMatchHost.getEditText().getText().toString();
        CommitMatchPresenter.commitMatch.website = addMatchWeb.getEditText().getText().toString();
        try {
            CommitMatchPresenter.commitMatch.maxPersons = Integer.parseInt(addMatchMax.getEditText().getText().toString());
            CommitMatchPresenter.commitMatch.minPersons = Integer.parseInt(addMatchMin.getEditText().getText().toString());

        } catch (NumberFormatException exception) {
            makeToast("格式不对");
        }
        Bundle bundle = new Bundle();
        jumpTo(CommitMatchStep3Activity.class, true, bundle);
    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        super.onBackPressed();
    }

}


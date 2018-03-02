package nullref.dlut.wematch.layout.commit;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.widgets.DatePickerDialog;

/**
 * Created by IsakWong on 2017/5/14.
 */

public class CommitMatchActivity extends BaseActivity<CommitMatchActivityContract.Presenter> implements CommitMatchActivityContract.View {


    Label labels[];
    String labelsName[];
    int labelsID[] = new int[3];


    Unbinder unbinder;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.add_match_img)
    ImageView addMatchImg;
    @BindView(R.id.add_match_name)
    TextInputLayout addMatchName;
    @BindView(R.id.add_match_short_info)
    TextInputLayout addMatchShortInfo;
    @BindView(R.id.add_match_min)
    TextInputLayout addMatchMin;
    @BindView(R.id.add_match_max)
    TextInputLayout addMatchMax;
    @BindView(R.id.add_match_time_start)
    TextInputLayout addMatchTimeStart;
    @BindView(R.id.add_match_time_end)
    TextInputLayout addMatchTimeEnd;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.add_match_web)
    TextInputLayout addMatchWeb;
    @BindView(R.id.add_match_host)
    TextInputLayout addMatchHost;
    @BindView(R.id.add_match_online)
    Spinner addMatchOnline;
    @BindView(R.id.add_match_loc)
    EditText addMatchLoc;
    @BindView(R.id.add_match_label1)
    Spinner addMatchLabel1;
    @BindView(R.id.add_match_label2)
    Spinner addMatchLabel2;
    @BindView(R.id.add_match_label3)
    Spinner addMatchLabel3;
    @BindView(R.id.add_match_content)
    TextInputLayout addMatchContent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match_step1);
        ButterKnife.bind(this);
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
                            presenter.getCommitMatch().startTime = textString;
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
                            presenter.getCommitMatch().endTime = textString;
                        }
                    }, curEndTime.getYear() + 1900, curEndTime.getMonth(), curEndTime.getDate()).show();
                }
            }
        });

        Spinner[] matchLabelSpinner = new Spinner[3];
        matchLabelSpinner[0] = addMatchLabel1;
        matchLabelSpinner[1] = addMatchLabel2;
        matchLabelSpinner[2] = addMatchLabel3;
        for(int i = 0 ;i <matchLabelSpinner.length;i++){
            final int index = i;
            matchLabelSpinner[index].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                    labelsID[index] = labels[pos].getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    labelsID[index] = -1;
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                addMatchImg.setImageBitmap(bitmap);
                presenter.setImage(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }






    @OnClick(R.id.add_match_img)
    public void onAddMatchImgClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onCommitMatchImage() {

    }

    @Override
    public void onCommitMatchInfo() {

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
        presenter.getCommitMatch().host = addMatchHost.getEditText().getText().toString();
        presenter.getCommitMatch().website = addMatchWeb.getEditText().getText().toString();
        try {
            presenter.getCommitMatch().maxPersons = Integer.parseInt(addMatchMax.getEditText().getText().toString());
            presenter.getCommitMatch().minPersons = Integer.parseInt(addMatchMin.getEditText().getText().toString());

        } catch (NumberFormatException exception) {
            makeToast("格式不对");
        }
        presenter.getCommitMatch().content = addMatchContent.getEditText().getText().toString();
        presenter.getCommitMatch().loc = addMatchLoc.getText().toString();
        presenter.getCommitMatch().type = addMatchOnline.getSelectedItemPosition();
        presenter.getCommitMatch().labels = labelsID;
        presenter.commitMatch();
    }
    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        onBackPressed();
    }

/*

*/
}


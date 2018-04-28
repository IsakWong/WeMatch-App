package nullref.dlut.wematch.layout.edtior;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;

/**
 * Created by ICCD on 2017/9/11.
 */

public class EditorActivity extends BaseActivity {


    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.edit_title)
    TextView editTitle;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.close)
    ImageButton close;
    Intent result = new Intent();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(EditorActivity.this);
        Bundle arguments = getIntent().getExtras();
        editTitle.setText(arguments.getString("title"));
        //edit.setInputType(arguments.getInt("input_type"));
        edit.setHint(arguments.getString("default_value"));
        edit.setLines(arguments.getInt("lines"));
        result.putExtra("key", arguments.getString("key"));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        setResult(RESULT_CANCELED, result);
        onBackPressed();
    }

    @OnClick(R.id.edit_title)
    public void onEditTitleClicked() {
    }

    @OnClick(R.id.save_btn)
    public void onSaveBtnClicked() {
        result.putExtra("value", edit.getText().toString());
        setResult(RESULT_OK, result);
        finishAfterTransition();
    }

    @OnClick(R.id.close)
    public void onCloseClicked() {
    }
}

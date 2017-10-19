package nullref.dlut.wematch.business.infoset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.business.edtior.EditorActivity;
import nullref.dlut.wematch.utils.LogToFile;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.widgets.AvatarImageTarget;

/**
 * Created by ICCD on 2017/9/11.
 */

public class UserInfoSetActivity extends BaseActivity implements UserInfoSetContract.View {


    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.info_save)
    ImageButton infoSave;
    @BindView(R.id.info_set_avatar_bg)
    ImageView infoSetAvatarBg;
    @BindView(R.id.info_set_nick_name)
    LinearLayout infoSetNickName;
    @BindView(R.id.info_set_intro)
    LinearLayout infoSetIntro;
    @BindView(R.id.info_set_major)
    LinearLayout infoSetMajor;
    @BindView(R.id.info_set_school)
    LinearLayout infoSetSchool;
    @BindView(R.id.info_avatar)
    CircleImageView infoAvatar;
    @BindView(R.id.info_set_qq)
    LinearLayout infoSetQq;

    private UserInfoSetPresenter presenter = new UserInfoSetPresenter(UserInfoSetActivity.this);
    private UserListInfo userListInfo = null;
    private static final int REQUEST_EDITOR_RESULT = 0;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private String avatarUrlPath;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_sets);
        ButterKnife.bind(UserInfoSetActivity.this);
        Bundle bundle = getIntent().getExtras();
        if(null!=bundle){
            userListInfo = (UserListInfo) bundle.getSerializable("userListInfo");
            avatarUrlPath= "https://wematch.oss-cn-shanghai.aliyuncs.com/"+ userListInfo.avatarUrl;
            RequestOptions options2 = new RequestOptions()
                    .placeholder(R.drawable.bg_blue)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy( DiskCacheStrategy.NONE )
                    //.signature(new MediaStoreSignature())
                    .skipMemoryCache( true );
            Glide
                    .with(UserInfoSetActivity.this)
                    .asBitmap()
                    .load(avatarUrlPath)
                    .apply(options2)
                    .into(new AvatarImageTarget(infoAvatar,infoSetAvatarBg));

        }else {
            onBackPressed();
        }
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case REQUEST_EDITOR_RESULT:
                if (res == RESULT_OK) {
                    presenter.modifyInfo(data.getStringExtra("key"), data.getStringExtra("value"));
                }
                break;
            case REQUEST_CODE_PICK_IMAGE:
                if (res == RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                        infoAvatar.setDrawingCacheEnabled(true);
                        Bitmap outBitmap = Utils.blurBitmap(UserInfoSetActivity.this, bit, 20f);
                        infoSetAvatarBg.setImageBitmap(outBitmap);
                        infoAvatar.setImageBitmap(bit);
                        presenter.UpdateImg(Utils.bitmapToBase64(bit));
                    } catch (Exception e) {
                        LogToFile.e(e,"");
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onUploadImg() {
        onBackPressed();
    }

    @Override
    public void onUploadImgError(String cause) {

    }

    @Override
    public void onUpdateUserInfo() {

    }

    @Override
    public void onUpdateUserInfoError(String cause) {
        makeToast(cause);
    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        onBackPressed();
    }

    @OnClick(R.id.info_save)
    public void onInfoSaveClicked() {
        presenter.UpdateInfo();
        makeToast("保存成功");
    }

    @OnClick(R.id.info_set_avatar_bg)
    public void onInfoSetAvatarBgClicked() {
    }

    @OnClick(R.id.info_set_nick_name)
    public void onInfoSetNickNameClicked() {
        Bundle argument = new Bundle();
        argument.putInt("lines", 1);
        argument.putString("title", "昵称");
        argument.putInt("input_type", InputType.TYPE_TEXT_VARIATION_NORMAL);
        argument.putString("key", "name");
        if (userListInfo.name != null)
            argument.putString("default_value", userListInfo.name);
        Intent intent = new Intent();
        intent.setClass(this, EditorActivity.class);
        intent.putExtras(argument);
        startActivityForResult(intent, REQUEST_EDITOR_RESULT);
    }

    @OnClick(R.id.info_set_intro)
    public void onInfoSetIntroClicked() {
        Bundle argument = new Bundle();
        argument.putInt("lines", 2);
        argument.putString("title", "个人简介");
        argument.putInt("input_type", InputType.TYPE_TEXT_VARIATION_NORMAL);
        argument.putString("key", "shortInfo");
        if (userListInfo.shortInfo != null)
            argument.putString("default_value", userListInfo.shortInfo);
        Intent intent = new Intent();
        intent.setClass(this, EditorActivity.class);
        intent.putExtras(argument);
        startActivityForResult(intent, REQUEST_EDITOR_RESULT);
    }

    @OnClick(R.id.info_set_major)
    public void onInfoSetMajorClicked() {

        Bundle argument = new Bundle();
        argument.putInt("lines", 1);
        argument.putString("title", "专业");
        argument.putInt("input_type", InputType.TYPE_TEXT_VARIATION_NORMAL);
        argument.putString("key", "major");

        if (userListInfo.major != null)
            argument.putString("default_value", userListInfo.major);
        Intent intent = new Intent();
        intent.setClass(this, EditorActivity.class);
        intent.putExtras(argument);
        startActivityForResult(intent, REQUEST_EDITOR_RESULT);
    }

    @OnClick(R.id.info_set_school)
    public void onInfoSetSchoolClicked() {
        Bundle argument = new Bundle();
        argument.putInt("lines", 1);
        argument.putString("title", "学校");
        argument.putInt("input_type", InputType.TYPE_TEXT_VARIATION_NORMAL);
        argument.putString("key", "school");

        if (userListInfo.school != null)
            argument.putString("default_value", userListInfo.school);
        Intent intent = new Intent();
        intent.setClass(this, EditorActivity.class);
        intent.putExtras(argument);
        startActivityForResult(intent, REQUEST_EDITOR_RESULT);
    }

    @OnClick(R.id.info_avatar)
    public void onInfoAvatarClicked() {
        UserInfoSetPresenter.AvaChanges = true;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @OnClick(R.id.info_set_qq)
    public void onInfoSetQqClicked() {
        Bundle argument = new Bundle();
        argument.putInt("lines", 1);
        argument.putString("title", "QQ号码");
        argument.putInt("input_type", InputType.TYPE_TEXT_VARIATION_NORMAL);
        argument.putString("key", "qq");

        if (userListInfo.school != null)
            argument.putString("default_value", userListInfo.qq);
        Intent intent = new Intent();
        intent.setClass(this, EditorActivity.class);
        intent.putExtras(argument);
        startActivityForResult(intent, REQUEST_EDITOR_RESULT);
    }



}

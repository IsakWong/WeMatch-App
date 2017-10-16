package nullref.dlut.wematch.widgets;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

import nullref.dlut.wematch.utils.Utils;

/**
 * Created by glory on 2017/9/28.
 */

public class AvatarImageTarget extends ImageViewTarget<Bitmap> {

    private ImageView infoAvatarBg;
    private ImageView infoAvatar;

    public AvatarImageTarget(ImageView view) {
        super(view);
    }

    public AvatarImageTarget(ImageView view, ImageView bg) {
        super(view);
        this.infoAvatar = view;
        this.infoAvatarBg = bg;
    }

    @Override
    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

        if(infoAvatarBg!=null) {
            Bitmap outBitmap = Utils.blurBitmap(infoAvatar.getContext(), bitmap, 20f);
            infoAvatarBg.setImageBitmap(outBitmap);
        }
        if(infoAvatar!=null) {
            infoAvatar.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
    }

}
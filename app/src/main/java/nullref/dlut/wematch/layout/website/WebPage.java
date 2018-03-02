package nullref.dlut.wematch.layout.website;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;

/**
 * Created by IsakWong on 2017/5/14.
 */

public class WebPage extends ColorStatusPage {


    Unbinder unbinder;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.community_web)
    WebView communityWeb;


    @Override
    public void onCreate(Bundle saveedInstance) {
        super.onCreate(saveedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_community, container, false);
        unbinder = ButterKnife.bind(this, view);

        communityWeb.getSettings().setJavaScriptEnabled(true);
        communityWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view.canGoBack()) {
                    if (navigationIcon != null) {
                        navigationIcon.setImageResource(R.drawable.ic_back);
                        navigationIcon.setClickable(true);
                    }
                } else {
                    if (navigationIcon != null) {
                        navigationIcon.setImageResource(R.drawable.ic_menu_white);
                    }
                }
            }
        });
        communityWeb.loadUrl("http://wematchcommunity.applinzi.com/main.php");
        return view;
    }

    @Override
    public void onBackPressed() {
        if (communityWeb.canGoBack()) {
            communityWeb.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.navigation_icon)
    public void onViewClicked() {
        this.onBackPressed();
    }
}


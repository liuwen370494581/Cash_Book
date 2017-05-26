package star.liuwen.com.cash_books.Activity;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.netState.NetworkUtils;

/**
 * Created by liuwen on 2017/5/26.
 * webView和H5和android 交汇一些常用的知识点都在这汇总了
 * 数据加载
 * 加载本地资源
 * webView.loadUrl("file:///android_asset/text.html");
 * 加载网络资源
 * webView.loadUrl("www.xxx.com/text.html");
 * 添加请求头信息
 * Map<String,String> map=new HashMap<String,String>();
 * map.put("User-Agent","Android");
 * webView.loadUrl("www.xxx.com/text.html",map);
 * 直接加载html代码片段
 * String html = "数据";
 * webView.loadDataWithBaseURL(null,html, "text/html", "utf-8",null);
 */
public class WebViewActivity extends BaseActivity {
    WebView mWebview;
    WebSettings mWebSettings;
    private String webViewTitle;


    @Override
    public int activityLayoutRes() {
        return R.layout.web_view_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));
        mWebview = (WebView) findViewById(R.id.web_view);
        mWebSettings = mWebview.getSettings();

        String typeWebView = getIntent().getStringExtra(Config.IntentTag_ToWebView);
        if (typeWebView.equals(Config.NewPersonGift)) {
            mWebview.loadUrl(Config.NewPersonGift);
        } else if (typeWebView.equals(Config.EveryDaySignIn)) {
            mWebview.loadUrl(Config.EveryDaySignIn);
        } else if (typeWebView.equals(Config.InviteAReward)) {
            mWebview.loadUrl(Config.InviteAReward);
        } else if (typeWebView.equals(Config.SafetyGuarantee)) {
            mWebview.loadUrl(Config.SafetyGuarantee);
        }
        mWebSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        mWebSettings.setLoadWithOverviewMode(true);//适配
        mWebSettings.setJavaScriptEnabled(true);  //支持js
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置jS支持弹窗
        mWebSettings.setDomStorageEnabled(true);//开启DOM缓存，关闭的话H5自身的一些操作是无效的
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        mWebSettings.setDomStorageEnabled(true);// 开启 DOM storage API 功能
        mWebSettings.setDatabaseEnabled(true);//开启 database storage API 功能
        mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//HTTPS，注意这个是在LOLLIPOP以上才调用的
        mWebSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        mWebSettings.setBlockNetworkImage(true);//关闭加载网络图片，在一开始加载的时候可以设置为true，当加载完网页的时候再设置为false

        //设置支持缩放
        mWebSettings.setBuiltInZoomControls(true);
        //设置出现缩放工具
        mWebSettings.setBuiltInZoomControls(true);

        if (NetworkUtils.isConnected(this)) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//不使用网络，只加载缓存
        }

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                webViewTitle = title;
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    setTitle(getString(R.string.loading_ing, progress));
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                }
            }
        });
        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                setTitle(webViewTitle);
                //先加载网页 然后在加载图片 可以提供webView的访问速度
                mWebSettings.setBlockNetworkImage(false);

            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}

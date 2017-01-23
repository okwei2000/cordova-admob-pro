/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.content.Context
 *  android.util.Log
 *  android.view.OrientationEventListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  com.google.android.gms.ads.identifier.AdvertisingIdClient
 *  com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
 *  com.rjfun.cordova.ext.CordovaPluginExt
 *  org.apache.cordova.CallbackContext
 *  org.apache.cordova.CordovaInterface
 *  org.apache.cordova.CordovaWebView
 *  org.apache.cordova.PluginResult
 *  org.apache.cordova.PluginResult$Status
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.rjfun.cordova.ad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.rjfun.cordova.ext.CordovaPluginExt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class GenericAdPlugin
extends CordovaPluginExt {
    private static final String LOGTAG = "GenericAdPlugin";
    public static final String ACTION_GET_AD_SETTINGS = "getAdSettings";
    public static final String ACTION_SET_OPTIONS = "setOptions";
    public static final String ACTION_CREATE_BANNER = "createBanner";
    public static final String ACTION_REMOVE_BANNER = "removeBanner";
    public static final String ACTION_HIDE_BANNER = "hideBanner";
    public static final String ACTION_SHOW_BANNER = "showBanner";
    public static final String ACTION_SHOW_BANNER_AT_XY = "showBannerAtXY";
    public static final String ACTION_PREPARE_INTERSTITIAL = "prepareInterstitial";
    public static final String ACTION_SHOW_INTERSTITIAL = "showInterstitial";
    public static final String ACTION_IS_INTERSTITIAL_READY = "isInterstitialReady";
    public static final String ACTION_PREPARE_REWARD_VIDEO_AD = "prepareRewardVideoAd";
    public static final String ACTION_SHOW_REWARD_VIDEO_AD = "showRewardVideoAd";
    public static final String ADTYPE_BANNER = "banner";
    public static final String ADTYPE_INTERSTITIAL = "interstitial";
    public static final String ADTYPE_NATIVE = "native";
    public static final String ADTYPE_REWARDVIDEO = "rewardvideo";
    public static final String EVENT_AD_LOADED = "onAdLoaded";
    public static final String EVENT_AD_FAILLOAD = "onAdFailLoad";
    public static final String EVENT_AD_PRESENT = "onAdPresent";
    public static final String EVENT_AD_LEAVEAPP = "onAdLeaveApp";
    public static final String EVENT_AD_DISMISS = "onAdDismiss";
    public static final String EVENT_AD_WILLPRESENT = "onAdWillPresent";
    public static final String EVENT_AD_WILLDISMISS = "onAdWillDismiss";
    public static final String ADSIZE_BANNER = "BANNER";
    public static final String ADSIZE_SMART_BANNER = "SMART_BANNER";
    public static final String ADSIZE_FULL_BANNER = "FULL_BANNER";
    public static final String ADSIZE_MEDIUM_RECTANGLE = "MEDIUM_RECTANGLE";
    public static final String ADSIZE_LEADERBOARD = "LEADERBOARD";
    public static final String ADSIZE_SKYSCRAPER = "SKYSCRAPER";
    public static final String ADSIZE_CUSTOM = "CUSTOM";
    public static final String OPT_ADID = "adId";
    public static final String OPT_AUTO_SHOW = "autoShow";
    public static final String OPT_IS_TESTING = "isTesting";
    public static final String OPT_LOG_VERBOSE = "logVerbose";
    public static final String OPT_AD_SIZE = "adSize";
    public static final String OPT_WIDTH = "width";
    public static final String OPT_HEIGHT = "height";
    public static final String OPT_OVERLAP = "overlap";
    public static final String OPT_ORIENTATION_RENEW = "orientationRenew";
    public static final String OPT_POSITION = "position";
    public static final String OPT_X = "x";
    public static final String OPT_Y = "y";
    public static final String OPT_BANNER_ID = "bannerId";
    public static final String OPT_INTERSTITIAL_ID = "interstitialId";
    protected String bannerId = "";
    protected String interstialId = "";
    protected String rewardvideoId = "";
    public static final int NO_CHANGE = 0;
    public static final int TOP_LEFT = 1;
    public static final int TOP_CENTER = 2;
    public static final int TOP_RIGHT = 3;
    public static final int LEFT = 4;
    public static final int CENTER = 5;
    public static final int RIGHT = 6;
    public static final int BOTTOM_LEFT = 7;
    public static final int BOTTOM_CENTER = 8;
    public static final int BOTTOM_RIGHT = 9;
    public static final int POS_XY = 10;
    protected boolean isTesting = false;
    protected boolean logVerbose = false;
    protected int adWidth = 0;
    protected int adHeight = 0;
    protected boolean overlap = false;
    protected boolean orientationRenew = true;
    protected int adPosition = 8;
    protected int posX = 0;
    protected int posY = 0;
    protected boolean autoShowBanner = true;
    protected boolean autoShowInterstitial = false;
    protected boolean autoShowRewardVideo = false;
    protected OrientationEventListener orientation = null;
    protected int widthOfView = 0;
    protected RelativeLayout overlapLayout = null;
    protected LinearLayout splitLayout = null;
    protected ViewGroup parentView = null;
    protected View adView = null;
    protected Object interstitialAd = null;
    protected Object rewardVideoAd = null;
    protected boolean bannerVisible = false;
    protected boolean interstitialReady = false;
    private static final String USER_AGENT = "Mozilla/5.0";

    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;
        if ("getAdSettings".equals(action)) {
            this.getAdSettings(callbackContext);
            return true;
        }
        if ("setOptions".equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            this.setOptions(options);
            result = new PluginResult(PluginResult.Status.OK);
        } else if ("createBanner".equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            if (options.length() > 1) {
                this.setOptions(options);
            }
            String adId = options.optString("adId");
            boolean autoShow = !options.has("autoShow") || options.optBoolean("autoShow");
            boolean isOk = this.createBanner(adId, autoShow);
            result = new PluginResult(isOk ? PluginResult.Status.OK : PluginResult.Status.ERROR);
        } else if ("removeBanner".equals(action)) {
            this.removeBanner();
            result = new PluginResult(PluginResult.Status.OK);
        } else if ("hideBanner".equals(action)) {
            this.hideBanner();
            result = new PluginResult(PluginResult.Status.OK);
        } else if ("showBanner".equals(action)) {
            int nPos = inputs.optInt(0);
            this.showBanner(nPos, 0, 0);
            result = new PluginResult(PluginResult.Status.OK);
        } else if ("showBannerAtXY".equals(action)) {
            JSONObject args = inputs.optJSONObject(0);
            int x = args.optInt("x");
            int y = args.optInt("y");
            this.showBanner(10, x, y);
            result = new PluginResult(PluginResult.Status.OK);
        } else if ("prepareInterstitial".equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            if (options.length() > 1) {
                this.setOptions(options);
            }
            String adId = options.optString("adId");
            boolean autoShow = !options.has("autoShow") || options.optBoolean("autoShow");
            boolean isOk = this.prepareInterstitial(adId, autoShow);
            result = new PluginResult(isOk ? PluginResult.Status.OK : PluginResult.Status.ERROR);
        } else if ("showInterstitial".equals(action)) {
            this.showInterstitial();
            result = new PluginResult(PluginResult.Status.OK);
        } else if ("isInterstitialReady".equals(action)) {
            result = new PluginResult(PluginResult.Status.OK, this.interstitialReady);
        } else if ("prepareRewardVideoAd".equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            if (options.length() > 1) {
                this.setOptions(options);
            }
            String adId = options.optString("adId");
            boolean autoShow = !options.has("autoShow") || options.optBoolean("autoShow");
            boolean isOk = this.prepareRewardVideoAd(adId, autoShow);
            result = new PluginResult(isOk ? PluginResult.Status.OK : PluginResult.Status.ERROR);
        } else if ("showRewardVideoAd".equals(action)) {
            boolean isOk = this.showRewardVideoAd();
            result = new PluginResult(isOk ? PluginResult.Status.OK : PluginResult.Status.ERROR);
        } else {
            Log.w((String)"GenericAdPlugin", (String)String.format("Invalid action passed: %s", action));
            result = new PluginResult(PluginResult.Status.INVALID_ACTION);
        }
        this.sendPluginResult(result, callbackContext);
        return true;
    }

    public void getAdSettings(final CallbackContext callbackContext) {
        final Activity activity = this.getActivity();
        this.cordova.getThreadPool().execute(new Runnable(){

            @Override
            public void run() {
                AdvertisingIdClient.Info adInfo = null;
                try {
                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo((Context)activity);
                    if (adInfo != null) {
                        JSONObject json = new JSONObject();
                        json.put("adId", (Object)adInfo.getId());
                        json.put("adTrackingEnabled", !adInfo.isLimitAdTrackingEnabled());
                        PluginResult result = new PluginResult(PluginResult.Status.OK, json);
                        GenericAdPlugin.this.sendPluginResult(result, callbackContext);
                        return;
                    }
                }
                catch (Exception e) {
                    // empty catch block
                }
                GenericAdPlugin.this.sendPluginResult(new PluginResult(PluginResult.Status.ERROR), callbackContext);
            }
        });
    }

    public void fireEvent(String obj, String eventName, String jsonData) {
        if (this.isTesting) {
            Log.d((String)"GenericAdPlugin", (String)(obj + ", " + eventName + ", " + jsonData));
        }
        super.fireEvent(obj, eventName, jsonData);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static String httpGet(String url) {
        String result = "";
        try {
            String inputLine;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "UTF-8");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();
            result = response.toString();
            return result;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

    protected void pluginInitialize() {
        super.pluginInitialize();
        this.orientation = new OrientationEventWatcher((Context)this.getActivity());
        this.orientation.enable();
    }

    public void checkOrientationChange() {
        int w = this.getView().getWidth();
        if (w == this.widthOfView) {
            return;
        }
        this.widthOfView = w;
        this.onViewOrientationChanged();
    }

    public void setOptions(JSONObject options) {
        if (options != null) {
            if (options.has("isTesting")) {
                this.isTesting = options.optBoolean("isTesting");
            }
            if (options.has("logVerbose")) {
                this.logVerbose = options.optBoolean("logVerbose");
            }
            if (options.has("width")) {
                this.adWidth = options.optInt("width");
            }
            if (options.has("height")) {
                this.adHeight = options.optInt("height");
            }
            if (options.has("overlap")) {
                this.overlap = options.optBoolean("overlap");
            }
            if (options.has("orientationRenew")) {
                this.orientationRenew = options.optBoolean("orientationRenew");
            }
            if (options.has("position")) {
                this.adPosition = options.optInt("position");
            }
            if (options.has("x")) {
                this.posX = options.optInt("x");
            }
            if (options.has("y")) {
                this.posY = options.optInt("y");
            }
            if (options.has("bannerId")) {
                this.bannerId = options.optString("bannerId");
            }
            if (options.has("interstitialId")) {
                this.interstialId = options.optString("interstitialId");
            }
        }
    }

    public boolean createBanner(String adId, boolean autoShow) {
        Log.d((String)"GenericAdPlugin", (String)("createBanner: " + adId + ", " + autoShow));
        this.autoShowBanner = autoShow;
        if (adId != null && adId.length() > 0) {
            this.bannerId = adId;
        } else {
            adId = this.bannerId;
        }

        final String strAdUnitId = adId;
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                if (GenericAdPlugin.this.adView == null) {
                    GenericAdPlugin.this.adView = GenericAdPlugin.this.__createAdView(strAdUnitId);
                    GenericAdPlugin.this.bannerVisible = false;
                } else {
                    GenericAdPlugin.this.detachBanner();
                }
                GenericAdPlugin.this.__loadAdView(GenericAdPlugin.this.adView);
            }
        });
        return true;
    }

    public void removeBanner() {
        Log.d((String)"GenericAdPlugin", (String)"removeBanner");
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                if (GenericAdPlugin.this.adView != null) {
                    GenericAdPlugin.this.hideBanner();
                    GenericAdPlugin.this.__destroyAdView(GenericAdPlugin.this.adView);
                    GenericAdPlugin.this.adView = null;
                }
                GenericAdPlugin.this.bannerVisible = false;
            }
        });
    }

    public void showBanner(final int argPos, final int argX, final int argY) {
        Log.d((String)"GenericAdPlugin", (String)"showBanner");
        if (this.adView == null) {
            Log.e((String)"GenericAdPlugin", (String)"banner is null, call createBanner() first.");
            return;
        }
        boolean bannerAlreadyVisible = this.bannerVisible;
        final Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                View mainView = GenericAdPlugin.this.getView();
                if (mainView == null) {
                    Log.e((String)"GenericAdPlugin", (String)"Error: could not get main view");
                    return;
                }
                Log.d((String)"GenericAdPlugin", (String)("webview class: " + mainView.getClass()));
                if (GenericAdPlugin.this.bannerVisible) {
                    GenericAdPlugin.this.detachBanner();
                }
                int bw = GenericAdPlugin.this.__getAdViewWidth(GenericAdPlugin.this.adView);
                int bh = GenericAdPlugin.this.__getAdViewHeight(GenericAdPlugin.this.adView);
                Log.d((String)"GenericAdPlugin", (String)String.format("show banner: (%d x %d)", bw, bh));
                ViewGroup rootView = (ViewGroup)mainView.getRootView();
                int rw = rootView.getWidth();
                int rh = rootView.getHeight();
                Log.w((String)"GenericAdPlugin", (String)("show banner, overlap:" + GenericAdPlugin.this.overlap + ", position: " + argPos));
                if (GenericAdPlugin.this.overlap) {
                    int x = GenericAdPlugin.this.posX;
                    int y = GenericAdPlugin.this.posY;
                    int ww = mainView.getWidth();
                    int wh = mainView.getHeight();
                    if (argPos >= 1 && argPos <= 9) {
                        switch ((argPos - 1) % 3) {
                            case 0: {
                                x = 0;
                                break;
                            }
                            case 1: {
                                x = (ww - bw) / 2;
                                break;
                            }
                            case 2: {
                                x = ww - bw;
                            }
                        }
                        switch ((argPos - 1) / 3) {
                            case 0: {
                                y = 0;
                                break;
                            }
                            case 1: {
                                y = (wh - bh) / 2;
                                break;
                            }
                            case 2: {
                                y = wh - bh;
                            }
                        }
                    } else if (argPos == 10) {
                        x = argX;
                        y = argY;
                    }
                    int[] offsetRootView = new int[]{0, 0};
                    int[] offsetWebView = new int[]{0, 0};
                    rootView.getLocationOnScreen(offsetRootView);
                    mainView.getLocationOnScreen(offsetWebView);
                    x += offsetWebView[0] - offsetRootView[0];
                    y += offsetWebView[1] - offsetRootView[1];
                    if (GenericAdPlugin.this.overlapLayout == null) {
                        GenericAdPlugin.this.overlapLayout = new RelativeLayout((Context)activity);
                        rootView.addView((View)GenericAdPlugin.this.overlapLayout, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
                        GenericAdPlugin.this.overlapLayout.bringToFront();
                    }
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bw, bh);
                    params.leftMargin = x;
                    params.topMargin = y;
                    GenericAdPlugin.this.overlapLayout.addView(GenericAdPlugin.this.adView, (ViewGroup.LayoutParams)params);
                    GenericAdPlugin.this.parentView = GenericAdPlugin.this.overlapLayout;
                } else {
                    GenericAdPlugin.this.parentView = (ViewGroup)mainView.getParent();
                    if (!(GenericAdPlugin.this.parentView instanceof LinearLayout)) {
                        GenericAdPlugin.this.parentView.removeView(mainView);
                        GenericAdPlugin.this.splitLayout = new LinearLayout((Context)GenericAdPlugin.this.getActivity());
                        GenericAdPlugin.this.splitLayout.setOrientation(1);
                        GenericAdPlugin.this.splitLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1, 0.0f));
                        mainView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1, 1.0f));
                        GenericAdPlugin.this.splitLayout.addView(mainView);
                        GenericAdPlugin.this.getActivity().setContentView((View)GenericAdPlugin.this.splitLayout);
                        GenericAdPlugin.this.parentView = GenericAdPlugin.this.splitLayout;
                    }
                    if (argPos <= 3) {
                        GenericAdPlugin.this.parentView.addView(GenericAdPlugin.this.adView, 0);
                    } else {
                        GenericAdPlugin.this.parentView.addView(GenericAdPlugin.this.adView);
                    }
                }
                GenericAdPlugin.this.parentView.bringToFront();
                GenericAdPlugin.this.parentView.requestLayout();
                GenericAdPlugin.this.adView.setVisibility(0);
                GenericAdPlugin.this.bannerVisible = true;
                GenericAdPlugin.this.__resumeAdView(GenericAdPlugin.this.adView);
                mainView.requestFocus();
            }
        });
    }

    private void detachBanner() {
        if (this.adView == null) {
            return;
        }
        this.adView.setVisibility(8);
        this.bannerVisible = false;
        ViewGroup parentView = (ViewGroup)this.adView.getParent();
        if (parentView != null) {
            parentView.removeView(this.adView);
        }
    }

    public void hideBanner() {
        Log.d((String)"GenericAdPlugin", (String)"hideBanner");
        if (this.adView == null) {
            return;
        }
        this.autoShowBanner = false;
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                GenericAdPlugin.this.detachBanner();
                GenericAdPlugin.this.__pauseAdView(GenericAdPlugin.this.adView);
            }
        });
    }

    public boolean prepareInterstitial(String adId, boolean autoShow) {
        Log.d((String)"GenericAdPlugin", (String)("prepareInterstitial: " + adId + ", " + autoShow));
        this.autoShowInterstitial = autoShow;
        if (adId != null && adId.length() > 0) {
            this.interstialId = adId;
        } else {
            adId = this.interstialId;
        }

        final String strUnitId = adId;
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                if (GenericAdPlugin.this.interstitialAd != null) {
                    GenericAdPlugin.this.__destroyInterstitial(GenericAdPlugin.this.interstitialAd);
                    GenericAdPlugin.this.interstitialAd = null;
                }
                if (GenericAdPlugin.this.interstitialAd == null) {
                    GenericAdPlugin.this.interstitialAd = GenericAdPlugin.this.__createInterstitial(strUnitId);
                    GenericAdPlugin.this.__loadInterstitial(GenericAdPlugin.this.interstitialAd);
                }
            }
        });
        return true;
    }

    public void showInterstitial() {
        Log.d((String)"GenericAdPlugin", (String)"showInterstitial");
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                GenericAdPlugin.this.__showInterstitial(GenericAdPlugin.this.interstitialAd);
            }
        });
    }

    public void removeInterstitial() {
        if (this.interstitialAd != null) {
            Activity activity = this.getActivity();
            activity.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    GenericAdPlugin.this.__destroyInterstitial(GenericAdPlugin.this.interstitialAd);
                }
            });
            this.interstitialAd = null;
        }
    }

    public boolean prepareRewardVideoAd(String adId, boolean autoShow) {
        Log.d((String)"GenericAdPlugin", (String)("prepareRewardVideoAd: " + adId + ", " + autoShow));
        this.autoShowRewardVideo = autoShow;
        if (adId != null && adId.length() > 0) {
            this.rewardvideoId = adId;
        } else {
            adId = this.rewardvideoId;
        }

        final String strUnitId = adId;
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                if (GenericAdPlugin.this.rewardVideoAd == null) {
                    GenericAdPlugin.this.rewardVideoAd = GenericAdPlugin.this.__prepareRewardVideoAd(strUnitId);
                }
            }
        });
        return true;
    }

    public boolean showRewardVideoAd() {
        Log.d((String)"GenericAdPlugin", (String)"showRewardVideoAd");
        Activity activity = this.getActivity();
        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                GenericAdPlugin.this.__showRewardVideoAd(GenericAdPlugin.this.rewardVideoAd);
            }
        });
        return true;
    }

    public void onPause(boolean multitasking) {
        if (this.adView != null) {
            this.__pauseAdView(this.adView);
        }
        super.onPause(multitasking);
    }

    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        if (this.adView != null) {
            this.__resumeAdView(this.adView);
        }
    }

    public void onDestroy() {
        if (this.adView != null) {
            this.__destroyAdView(this.adView);
            this.adView = null;
        }
        if (this.interstitialAd != null) {
            this.__destroyInterstitial(this.interstitialAd);
            this.interstitialAd = null;
        }
        if (this.overlapLayout != null) {
            ViewGroup parentView = (ViewGroup)this.overlapLayout.getParent();
            if (parentView != null) {
                parentView.removeView((View)this.overlapLayout);
            }
            this.overlapLayout = null;
        }
        super.onDestroy();
    }

    public void onViewOrientationChanged() {
        if (this.logVerbose) {
            Log.d((String)"GenericAdPlugin", (String)"Orientation Changed");
        }
        if (this.adView != null && this.bannerVisible) {
            if (this.orientationRenew) {
                if (this.logVerbose) {
                    Log.d((String)"GenericAdPlugin", (String)"renew banner on orientation change");
                }
                this.removeBanner();
                this.createBanner(this.bannerId, true);
            } else {
                if (this.logVerbose) {
                    Log.d((String)"GenericAdPlugin", (String)"adjust banner position");
                }
                this.showBanner(this.adPosition, this.posX, this.posY);
            }
        }
    }

    protected void fireAdEvent(String event, String adType) {
        String obj = this.__getProductShortName();
        String json = String.format("{'adNetwork':'%s','adType':'%s','adEvent':'%s'}", obj, adType, event);
        this.fireEvent(obj, event, json);
    }

    @SuppressLint(value={"DefaultLocale"})
    protected void fireAdErrorEvent(String event, int errCode, String errMsg, String adType) {
        String obj = this.__getProductShortName();
        String json = String.format("{'adNetwork':'%s','adType':'%s','adEvent':'%s','error':%d,'reason':'%s'}", obj, adType, event, errCode, errMsg);
        this.fireEvent(obj, event, json);
    }

    protected abstract String __getProductShortName();

    protected abstract View __createAdView(String var1);

    protected abstract int __getAdViewWidth(View var1);

    protected abstract int __getAdViewHeight(View var1);

    protected abstract void __loadAdView(View var1);

    protected abstract void __pauseAdView(View var1);

    protected abstract void __resumeAdView(View var1);

    protected abstract void __destroyAdView(View var1);

    protected abstract Object __createInterstitial(String var1);

    protected abstract void __loadInterstitial(Object var1);

    protected abstract void __showInterstitial(Object var1);

    protected abstract void __destroyInterstitial(Object var1);

    protected Object __prepareRewardVideoAd(String adId) {
        return null;
    }

    protected void __showRewardVideoAd(Object rewardvideo) {
    }

    private class OrientationEventWatcher
    extends OrientationEventListener {
        public OrientationEventWatcher(Context context) {
            super(context);
        }

        public void onOrientationChanged(int orientation) {
            GenericAdPlugin.this.checkOrientationChange();
        }
    }

}
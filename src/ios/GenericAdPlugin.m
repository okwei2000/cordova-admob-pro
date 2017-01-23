//
//  CDVAdMobPlugin.m
//  TestAdMobCombo
//
//  Created by Xie Liming on 14-10-20.
//
//

#import <AdSupport/ASIdentifierManager.h>

#import <GoogleMobileAds/GoogleMobileAds.h>

#import <CoreLocation/CLLocation.h>

#import "CDVAdMobPlugin.h"
#import "AdMobMediation.h"

#define TEST_BANNER_ID           @"ca-app-pub-3940256099942544/4480807092"
#define TEST_INTERSTITIALID      @"ca-app-pub-3940256099942544/4411468910"
#define TEST_REWARDVIDEOID       @"ca-app-pub-3940256099942544/3995920692"

#define OPT_ADCOLONY        @"AdColony"
#define OPT_ADCOLONY        @"AdColony"
#define OPT_FLURRY          @"Flurry"
#define OPT_MMEDIA          @"mMedia"
#define OPT_INMOBI          @"InMobi"
#define OPT_FACEBOOK        @"Facebook"
#define OPT_MOBFOX          @"MobFox"
#define OPT_IAD             @"iAd"

#define OPT_GENDER          @"gender"
#define OPT_LOCATION        @"location"
#define OPT_FORCHILD        @"forChild"
#define OPT_CONTENTURL      @"contentURL"
#define OPT_CUSTOMTARGETING @"customTargeting"
#define OPT_EXCLUDE         @"exclude"

@interface CDVAdMobPlugin()<GADBannerViewDelegate, GADInterstitialDelegate, GADRewardBasedVideoAdDelegate>

@property (assign) GADAdSize adSize;
@property (nonatomic, retain) NSDictionary* adExtras;
@property (nonatomic, retain) NSMutableDictionary* mediations;

@property (nonatomic, retain) NSString* mGender;
@property (nonatomic, retain) NSArray* mLocation;
@property (nonatomic, retain) NSString* mForChild;
@property (nonatomic, retain) NSString* mContentURL;

@property (nonatomic, retain) NSDictionary* mCustomTargeting;
@property (nonatomic, retain) NSArray* mExclude;

@property (nonatomic, retain) NSString* rewardVideoAdId;

- (GADAdSize)__AdSizeFromString:(NSString *)str;
- (GADRequest*) __buildAdRequest:(BOOL)forBanner forDFP:(BOOL)fordfp;
- (NSString *) __getAdMobDeviceId;

@end

@implementation CDVAdMobPlugin

- (void) getAdSettings:(CDVInvokedUrlCommand *)command {

}

- (void) setOptions:(CDVInvokedUrlCommand *)command {

}

- (void)createBanner:(CDVInvokedUrlCommand *)command {

}

- (void)showBanner:(CDVInvokedUrlCommand *)command {

}

- (void)showBannerAtXY:(CDVInvokedUrlCommand *)command {

}

- (void)hideBanner:(CDVInvokedUrlCommand *)command {

}

- (void)removeBanner:(CDVInvokedUrlCommand *)command {

}

- (void)prepareInterstitial:(CDVInvokedUrlCommand *)command {

}

- (void)showInterstitial:(CDVInvokedUrlCommand *)command {

}

- (void)removeInterstitial:(CDVInvokedUrlCommand *)command {

}

- (void)isInterstitialReady:(CDVInvokedUrlCommand*)command {

}


- (void) prepareRewardVideoAd:(CDVInvokedUrlCommand *)command {

}

- (void) showRewardVideoAd:(CDVInvokedUrlCommand *)command {

}

@end

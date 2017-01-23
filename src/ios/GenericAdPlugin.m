//
//  GenericAdPlugin.m
//  TestAdMobCombo
//
//  Created by Xie Liming on 14-10-20.
//
//

#import "GenericAdPlugin.h"

@implementation GenericAdPlugin

- (void) getAdSettings:(CDVInvokedUrlCommand *)command {
    // TODO not implemented
}

- (void) setOptions:(CDVInvokedUrlCommand *)command {
    NSDictionary* options = [command argumentAtIndex: 0];
    [self parseOptions: options];
}

- (void)createBanner:(CDVInvokedUrlCommand *)command {
    NSDictionary* options = [command argumentAtIndex: 0];
    NSString* adId = [options valueForKey:@"adId"];
    bool autoShow = [options valueForKey:@"autoShow"] && [[options valueForKey:@"autoShow"] boolValue];

    self.autoShowBanner = autoShow;
    if (adId != nil) {
        self.bannerId = adId;
    } else {
        adId = self.bannerId;
    }
    
    dispatch_async(dispatch_get_main_queue(), ^{
        if (self.banner == nil) {
            self.banner = [self __createAdView:adId];
            self.bannerVisible = false;
        }
        [self __loadAdView: self.banner];
    });
}

- (void)showBanner:(CDVInvokedUrlCommand *)command {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self __showBanner: 0 atX: 0 atY: 0];
    });
}

- (void)showBannerAtXY:(int) position atX:(int)x atY:(int)y {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self __showBanner: position atX: x atY: y];
    });
}

- (void) __showBanner:(int) position atX:(int)x atY:(int)y {
    if (self.banner != nil) {
        UIView* view = [self getView];
//        int bw = [self __getAdViewWidth: self.banner];
        int bh = [self __getAdViewHeight: self.banner];

        self.banner.frame = CGRectMake(0, view.frame.size.height - bh, self.banner.frame.size.width, self.banner.frame.size.height);
        self.banner.hidden = NO;
        [[self getView] addSubview: self.banner];
    }
}

- (void)hideBanner:(CDVInvokedUrlCommand *)command {
    if (self.banner != nil) {
        self.banner.hidden = YES;
    }
}

- (void)removeBanner:(CDVInvokedUrlCommand *)command {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self __destroyAdView: self.banner];
    });
}

- (void)prepareInterstitial:(CDVInvokedUrlCommand *)command {
    NSDictionary* options = [command argumentAtIndex: 0];
    [self parseOptions: options];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        if (self.interstitial != nil) {
            [self __destroyInterstitial: self.interstitial];
            self.interstitial = nil;
        }

        NSString* adId = [options valueForKey:@"adId"];
        bool autoShow = [options valueForKey:@"autoShow"] && [[options valueForKey:@"autoShow"] boolValue];
        self.autoShowInterstitial = autoShow;

        self.interstitial = [self __createInterstitial: adId];
        [self __loadInterstitial: self.interstitial];
    });
}

- (void)showInterstitial:(CDVInvokedUrlCommand *)command {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self __showInterstitial: self.interstitial];
    });
}

- (void)removeInterstitial:(CDVInvokedUrlCommand *)command {
    dispatch_async(dispatch_get_main_queue(), ^{
        if (self.interstitial != nil) {
            [self __destroyInterstitial: self.interstitial];
            self.interstitial = nil;
        }
    });
}

- (void)isInterstitialReady:(CDVInvokedUrlCommand*)command {
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool: self.interstitialReady];
    [self sendPluginResult: result to:command.callbackId];
}

- (void) parseOptions:(NSDictionary*) options {
    if (options != nil) {
        if ([options valueForKey:@"isTesting"]) {
            self.isTesting = [[options valueForKey:@"isTesting"] boolValue];
        }
        if ([options valueForKey:@"logVerbose"]) {
            self.logVerbose = [[options valueForKey:@"logVerbose"] boolValue];
        }
        if ([options valueForKey:@"width"]) {
            self.adWidth = [[options valueForKey:@"width"] intValue];
        }
        if ([options valueForKey:@"height"]) {
            self.adHeight = [[options valueForKey:@"height"] intValue];
        }
        if ([options valueForKey:@"overlap"]) {
            self.logVerbose = [[options valueForKey:@"overlap"] boolValue];
        }
        if ([options valueForKey:@"orientationRenew"]) {
            self.orientationRenew = [[options valueForKey:@"orientationRenew"] boolValue];
        }
        if ([options valueForKey:@"position"]) {
            self.adPosition = [[options valueForKey:@"position"] intValue];
        }
        if ([options valueForKey:@"x"]) {
            self.adPosition = [[options valueForKey:@"x"] intValue];
        }
        if ([options valueForKey:@"y"]) {
            self.adPosition = [[options valueForKey:@"y"] intValue];
        }
        if ([options valueForKey:@"bannerId"]) {
            self.bannerId = [[options valueForKey:@"bannerId"] string];
        }
        if ([options valueForKey:@"interstitialId"]) {
            self.interstitialId = [[options valueForKey:@"interstitialId"] string];
        }
    }
}

- (void) prepareRewardVideoAd:(CDVInvokedUrlCommand *)command {
    NSDictionary* options = [command argumentAtIndex: 0];
    [self parseOptions: options];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [self __prepareRewardVideoAd: [options valueForKey:@"adId"]];
    });
}

- (void) showRewardVideoAd:(CDVInvokedUrlCommand *)command {
    NSDictionary* options = [command argumentAtIndex: 0];
    [self parseOptions: options];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [self __showRewardVideoAd: nil];
    });
}

- (bool) __isLandscape {
    return [[UIDevice currentDevice] orientation] == UIDeviceOrientationLandscapeLeft || [[UIDevice currentDevice] orientation] == UIDeviceOrientationLandscapeRight;
}

- (void) fireAdEvent:(NSString*)event withType:(NSString*)adType {
    NSString* obj = [self __getProductShortName];
    [self fireEvent:obj event:event withData:nil];
}

- (void) fireAdErrorEvent:(NSString*)event withCode:(int)errCode withMsg:(NSString*)errMsg withType:(NSString*)adType {
    NSString* obj = [self __getProductShortName];
    NSString* json = [NSString stringWithFormat:@"{'adNetwork':'%@','adType':'%@','adEvent':'%@','error':'%d','reason':%@}",
                      obj, adType, event, errCode, errMsg];

    [self fireEvent:obj event:event withData:json];
}

@end

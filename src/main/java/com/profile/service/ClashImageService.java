package com.profile.service;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.profile.client.ClashApiClient;
import com.profile.data.Profile;
import com.profile.service.imageGenerator.*;

public class ClashImageService {
    private final LoadingCache<String, Profile> profileCache;
    private final ProfileImageGenerator profileImageGenerator;
    private final TroopShowcaseImageGenerator troopShowcaseImageGenerator;
    private final XpImageGenerator xpImageGenerator;

    public ClashImageService() {

        this.profileCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)     
            .maximumSize(1000)                       
            .build(tag -> ClashApiClient.fetchProfile(tag));

        this.profileImageGenerator = new ProfileImageGenerator();
        this.troopShowcaseImageGenerator = new TroopShowcaseImageGenerator();
        this.xpImageGenerator = new XpImageGenerator();
    }

    public byte[] getProfile(String tag) throws Exception {
        Profile profile = profileCache.get(tag);
        return ImageFactory.getCachedRender("stats", tag, profile, profileImageGenerator::generateImage);
    }

    public byte[] getTroops(String tag) throws Exception {
        Profile profile = profileCache.get(tag);
        return ImageFactory.getCachedRender("troops", tag, profile, troopShowcaseImageGenerator::generateImage);
    }
    public byte[] getXP(String tag) throws Exception {
        Profile profile = profileCache.get(tag);
        return ImageFactory.getCachedRender("xp", tag, profile, xpImageGenerator::generateImage);
    }
}
package com.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clan {
    public String tag;
    public String name;
    public URLs badgeUrls;
}

package com.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Achievement {
    public String name;
    public int stars;
    public int value;
    public int target;
    public String info;
    public String completionInfo;
    public String village;
}

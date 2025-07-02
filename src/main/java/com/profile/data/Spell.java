package com.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spell {
    public String name;
    public int level;
    public int maxLevel;
    public String village;
}

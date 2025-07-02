package com.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegendSeason {
    public String id;
    public Integer rank;
    public Integer trophies;
}

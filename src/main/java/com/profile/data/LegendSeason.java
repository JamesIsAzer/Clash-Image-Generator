package com.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegendSeason {
    public String id;
    public int rank;
    public int trophies;
}

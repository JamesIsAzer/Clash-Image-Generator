package com.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegendStatistics {
    public int legendTrophies;
    public LegendSeason previousSeason;
    public LegendSeason bestSeason;
    public LegendSeason currentSeason;
}

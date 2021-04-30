package com.brk.timeshift.model;


import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Time table for multiple days
 */
@Data
public class TimeTable {

  private final Map<String, DailyTimeTable> timeTable = new HashMap<>(); // dayid : time shift

}

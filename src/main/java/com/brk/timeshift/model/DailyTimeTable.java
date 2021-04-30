package com.brk.timeshift.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * 24 hour time table for: 0-8, 8-16, 16-24
 */
@Data
public class DailyTimeTable {

  public enum TimeSlot {TIME_0_8, TIME_8_16, TIME_16_24}

  private final Map<TimeSlot, Collection<WorkerId>> assignments = new HashMap<>();

  public DailyTimeTable() {
    for (TimeSlot slot : TimeSlot.values()) {
      assignments.put(slot, new ArrayList<>());
    }
  }

}

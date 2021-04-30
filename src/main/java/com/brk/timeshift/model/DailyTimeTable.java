package com.brk.timeshift.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 24 hour time table for: 0-8, 8-16, 16-24
 */
@Data
@AllArgsConstructor
public class DailyTimeTable {

  public enum TimeSlot {TIME_0_8, TIME_8_16, TIME_16_24}

  private final Map<TimeSlot, Collection<WorkerId>> assignments = new HashMap<>();

}

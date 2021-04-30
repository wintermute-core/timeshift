package com.brk.timeshift.service;

import com.brk.timeshift.model.DailyTimeTable;
import com.brk.timeshift.model.DailyTimeTable.TimeSlot;
import com.brk.timeshift.model.TimeTable;
import com.brk.timeshift.model.WorkerId;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TimePlanningService {

  @Getter
  private final TimeTable timeTable;

  TimePlanningService() {
    timeTable = new TimeTable();
  }

  public void addWorker(String day, TimeSlot timeSlot, WorkerId worker) {
    if (!timeTable.getTimeTable().containsKey(day)) {
      log.debug("Creating new day " + day);
      timeTable.getTimeTable().put(day, new DailyTimeTable());
    }

    Map<TimeSlot, Collection<WorkerId>> dailyAssignments = timeTable.getTimeTable().get(day)
        .getAssignments();

    for (Map.Entry<TimeSlot, Collection<WorkerId>> entry : dailyAssignments.entrySet()) {
      if (entry.getValue().contains(worker)) {
        throw new TimePlanningException(
            "Worker " + worker + " is already scheduled on " + entry.getKey());
      }
    }
    dailyAssignments.get(timeSlot).add(worker);
  }

}

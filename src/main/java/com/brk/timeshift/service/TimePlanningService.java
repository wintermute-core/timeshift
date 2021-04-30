package com.brk.timeshift.service;

import com.brk.timeshift.model.DailyTimeTable;
import com.brk.timeshift.model.DailyTimeTable.TimeSlot;
import com.brk.timeshift.model.TimeTable;
import com.brk.timeshift.model.WorkerId;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

  public Map<String, TimeSlot> getWorkerSchedule(WorkerId workerId) {
    Map<String, TimeSlot> schedule = new HashMap<>();

    // iterate over time table and build schedule for one worker
    for (Entry<String, DailyTimeTable> timeTableEntry : getTimeTable().getTimeTable()
        .entrySet()) {
      for (Entry<TimeSlot, Collection<WorkerId>> assignment : timeTableEntry.getValue()
          .getAssignments().entrySet()) {
        if (assignment.getValue().contains(workerId)) {
          schedule.put(timeTableEntry.getKey(), assignment.getKey());
          break;
        }
      }
    }
    return schedule;

  }

}

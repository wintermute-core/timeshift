package com.brk.timeshift.rest;

import com.brk.timeshift.model.DailyTimeTable.TimeSlot;
import com.brk.timeshift.model.TimeTable;
import com.brk.timeshift.model.WorkerId;
import com.brk.timeshift.service.TimePlanningService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timeplan")
@AllArgsConstructor
public class TimePlan {

  private final TimePlanningService timePlanningService;

  @GetMapping
  public TimeTable fetchTimePlan() {
    return timePlanningService.getTimeTable();
  }

  @GetMapping("/worker/{workerId}")
  public Map<String, TimeSlot> fetchWorkerSchedule(@PathVariable String workerId) {
    return timePlanningService.getWorkerSchedule(WorkerId.builder().id(workerId).build());
  }

  @PostMapping("/schedule/{day}/{timeSlot}/{workerId}")
  public void addWorker(@PathVariable String day, @PathVariable TimeSlot timeSlot,
      @PathVariable String workerId) {
    timePlanningService.addWorker(day, timeSlot, WorkerId.builder().id(workerId).build());
  }

  @DeleteMapping("/schedule/{day}/{timeSlot}/{workerId}")
  public void removeWorker(@PathVariable String day, @PathVariable TimeSlot timeSlot,
      @PathVariable String workerId) {
    timePlanningService.removeWorker(day, timeSlot, WorkerId.builder().id(workerId).build());
  }


}

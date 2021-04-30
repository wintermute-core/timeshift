package com.brk.timeshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.brk.timeshift.model.DailyTimeTable;
import com.brk.timeshift.model.DailyTimeTable.TimeSlot;
import com.brk.timeshift.model.WorkerId;
import com.brk.timeshift.rest.TimePlanController;
import com.brk.timeshift.service.TimePlanningService;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class TimePlanControllerTest {

  @Autowired
  TimePlanController timePlanController;

  @Autowired
  TimePlanningService timePlanningService;

  @BeforeEach
  void initPlanningService() {
    timePlanningService.getTimeTable().getTimeTable().clear();
  }

  @Test
  void addWorkerAndFetchTimeTable() {
    timePlanController.addWorker("2021-05-01", TimeSlot.TIME_8_16, "VladimirV");

    DailyTimeTable dailyTimeTable = timePlanController.fetchTimePlan().getTimeTable()
        .get("2021-05-01");
    assertNotNull(dailyTimeTable);
    Collection<WorkerId> workerIds = dailyTimeTable.getAssignments().get(TimeSlot.TIME_8_16);
    assertNotNull(workerIds);
    assertTrue(workerIds.contains(WorkerId.builder().id("VladimirV").build()));
  }

  @Test
  void addWorkerFetchTimeRemove() {
    Map<String, TimeSlot> schedule1 = timePlanController.fetchWorkerSchedule("VladimirV");
    assertTrue(schedule1.isEmpty());

    timePlanController.addWorker("2021-05-01", TimeSlot.TIME_8_16, "VladimirV");
    Map<String, TimeSlot> schedule2 = timePlanController.fetchWorkerSchedule("VladimirV");
    assertFalse(schedule2.isEmpty());
    assertEquals(1, schedule2.size());
    assertEquals(TimeSlot.TIME_8_16, schedule2.get("2021-05-01"));

    timePlanController.removeWorker("2021-05-01", TimeSlot.TIME_8_16, "VladimirV");

    Map<String, TimeSlot> schedule3 = timePlanController.fetchWorkerSchedule("VladimirV");
    assertTrue(schedule3.isEmpty());
  }

  @Test
  void addWorkerSameTimeSchedule() {
    timePlanController.addWorker("2021-05-01", TimeSlot.TIME_8_16, "VladimirV");
    try {
      timePlanController.addWorker("2021-05-01", TimeSlot.TIME_0_8, "VladimirV");
    } catch (ResponseStatusException e) {
      assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
      return;
    }
    fail("Adding of worker to same day should fail");
  }

}

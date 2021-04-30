package com.brk.timeshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.brk.timeshift.model.DailyTimeTable;
import com.brk.timeshift.model.DailyTimeTable.TimeSlot;
import com.brk.timeshift.model.WorkerId;
import com.brk.timeshift.service.TimePlanningException;
import com.brk.timeshift.service.TimePlanningService;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeshiftApplicationTests {

	@Autowired
	TimePlanningService timePlanningService;

	@Test
	void contextLoads() {
	}

	@Test
	void workersAdding() {
		timePlanningService
				.addWorker("2020-04-29", TimeSlot.TIME_0_8, WorkerId.builder().id("DonaldT").build());
		timePlanningService
				.addWorker("2020-04-30", TimeSlot.TIME_8_16, WorkerId.builder().id("VladimirV").build());

		Map<String, DailyTimeTable> timeTable = timePlanningService.getTimeTable().getTimeTable();
		assertNotNull(timeTable);
		assertEquals(2, timeTable.size());
		assertTrue(timeTable.containsKey("2020-04-29"));
		assertTrue(timeTable.containsKey("2020-04-30"));
		DailyTimeTable dailyTimeTable = timeTable.get("2020-04-29");
		assertNotNull(dailyTimeTable);
		Map<TimeSlot, Collection<WorkerId>> assignments = dailyTimeTable.getAssignments();
		assertNotNull(assignments);
		assertTrue(assignments.containsKey(TimeSlot.TIME_0_8));
		assertTrue(
				assignments.get(TimeSlot.TIME_0_8).contains(WorkerId.builder().id("DonaldT").build()));

		assertTrue(
				timeTable.get("2020-04-30").getAssignments().get(TimeSlot.TIME_8_16)
						.contains(WorkerId.builder().id("VladimirV").build()));
	}

	@Test
	void sameWorkerCanBeAddedToDifferentDays() {
		timePlanningService
				.addWorker("2020-04-29", TimeSlot.TIME_0_8, WorkerId.builder().id("DonaldT").build());
		timePlanningService
				.addWorker("2020-04-30", TimeSlot.TIME_0_8, WorkerId.builder().id("DonaldT").build());
		timePlanningService
				.addWorker("2020-05-01", TimeSlot.TIME_8_16, WorkerId.builder().id("DonaldT").build());

		Map<String, DailyTimeTable> timeTable = timePlanningService.getTimeTable().getTimeTable();
		assertNotNull(timeTable);

		assertTrue(
				timeTable.get("2020-04-29").getAssignments().get(TimeSlot.TIME_0_8)
						.contains(WorkerId.builder().id("DonaldT").build()));
		assertTrue(
				timeTable.get("2020-04-30").getAssignments().get(TimeSlot.TIME_0_8)
						.contains(WorkerId.builder().id("DonaldT").build()));
		assertTrue(
				timeTable.get("2020-05-01").getAssignments().get(TimeSlot.TIME_8_16)
						.contains(WorkerId.builder().id("DonaldT").build()));
	}

	@Test
	void workerScheduleCanBeLoaded() {
		timePlanningService
				.addWorker("2020-04-29", TimeSlot.TIME_0_8, WorkerId.builder().id("VladimirV").build());

		timePlanningService
				.addWorker("2020-04-28", TimeSlot.TIME_0_8, WorkerId.builder().id("DonaldT").build());

		Map<String, TimeSlot> schedule = timePlanningService
				.getWorkerSchedule(WorkerId.builder().id("VladimirV").build());

		assertNotNull(schedule);
		assertEquals(1, schedule.size());
		assertTrue(schedule.containsKey("2020-04-29"));
		assertEquals(TimeSlot.TIME_0_8, schedule.get("2020-04-29"));
	}

	@Test
	void workersAddingShouldFailWhenAddingOnSameDay() {
		timePlanningService.addWorker("1", TimeSlot.TIME_0_8, WorkerId.builder().id("DonaldT").build());

		try {
			timePlanningService
					.addWorker("1", TimeSlot.TIME_8_16, WorkerId.builder().id("DonaldT").build());
		} catch (TimePlanningException e) {
			return;
		}

		Assertions.fail("Adding of workers on the same day should fail");
	}

}

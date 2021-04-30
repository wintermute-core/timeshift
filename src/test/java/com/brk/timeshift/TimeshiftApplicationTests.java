package com.brk.timeshift;

import com.brk.timeshift.model.DailyTimeTable.TimeSlot;
import com.brk.timeshift.model.WorkerId;
import com.brk.timeshift.service.TimePlanningException;
import com.brk.timeshift.service.TimePlanningService;
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
		timePlanningService.addWorker("1", TimeSlot.TIME_0_8, WorkerId.builder().id("DonaldT").build());
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

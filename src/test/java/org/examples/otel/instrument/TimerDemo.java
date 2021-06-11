/*
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.examples.otel.instrument;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Not real tests.
 */
public class TimerDemo {
	private static final Meter METER = new Meter();
	private static final MockClock CLOCK = new MockClock();

	@Test
	void showRecordingElapsedTimeGood() {
		Timer timer = METER.createTimer("exampleTimer", "just an example");
		Timer.Sample sample = timer.start(CLOCK);
		goodWayToSimulateTaskDuration(Duration.ofSeconds(1));
		long recordedDuration = timer.stop(sample);

		assertThat(recordedDuration).isEqualTo(Duration.ofSeconds(1).toNanos());
	}

	@Test
	void showRecordingElapsedTimeBad() {
		Timer timer = METER.createTimer("exampleTimer", "just an example");
		Timer.Sample sample = timer.start();
		badWayToSimulateTaskWithDuration(Duration.ofSeconds(1));
		long recordedDuration = timer.stop(sample);

		// bad joke; also, it can fail, especially if you lower the offset
		assertThat(recordedDuration).isCloseTo(Duration.ofSeconds(1).toNanos(), offset(Duration.ofMillis(3).toNanos()));
	}

	private void goodWayToSimulateTaskDuration(Duration duration) {
		CLOCK.add(duration);
	}

	private void badWayToSimulateTaskWithDuration(Duration duration) {
		try {
			Thread.sleep(duration.toMillis());
		}
		catch (InterruptedException e) {
			System.err.println("ouch: " + e);
		}
	}
}

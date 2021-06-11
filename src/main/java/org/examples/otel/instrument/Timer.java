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
import java.util.concurrent.TimeUnit;

/**
 * See original code, comments and license and authors: https://github.com/micrometer-metrics/micrometer/blob/main/micrometer-core/src/main/java/io/micrometer/core/instrument/Timer.java
 */
public interface Timer {
	default Sample start() {
		return start(Clock.SYSTEM);
	}

	default Sample start(Clock clock) {
		return new Sample(clock);
	}

	default long stop(Sample sample) {
		return sample.stop(this);
	}

	default void record(Duration duration) {
		record(duration.toNanos(), TimeUnit.NANOSECONDS);
	}

	void record(long amount, TimeUnit unit);

	class Sample {
		private final long startTime;
		private final Clock clock;

		Sample(Clock clock) {
			this.clock = clock;
			this.startTime = clock.monotonicTime();
		}

		long stop(Timer timer) {
			long durationNs = clock.monotonicTime() - startTime;
			timer.record(durationNs, TimeUnit.NANOSECONDS);
			return durationNs;
		}
	}
}

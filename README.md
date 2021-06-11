Example and demo for https://github.com/open-telemetry/opentelemetry-specification/issues/464

A few things to notice:
- The `Meter` class is just a dummy one, just to have something similar to the OTel Metrics Specs
- There are no `Attibutes` used
- The reporting is just printing to `stdout`, see: `SimpleTimer`
- Most of the components are from Micrometer see original code, comments and license and authors there: https://github.com/micrometer-metrics/micrometer/
- `Clock` is important, it guides and helps testing, see: `MockClock`
- `Timer` is also important it guides (see: `Timer.Sample`) the user to record duration, see: `TimerDemo`
  (There are other arrangements to do this, this is just an example)

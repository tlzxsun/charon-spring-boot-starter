package com.github.mkopylec.charon.test.stubs

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry

class MeterRegistryProvider {

    private static MeterRegistry meterRegistry = new SimpleMeterRegistry()

    static MeterRegistry meterRegistry() {
        return meterRegistry
    }

    static void resetMetrics() {
        meterRegistry.meters.each { meterRegistry.remove(it.id) }
    }
}

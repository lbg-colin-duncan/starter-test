package __PACKAGE_PREFIX__.util;

import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.samplers.Samplers;

public class __CLASS__Util {

    private __CLASS__Util(){
        throw new AssertionError();
    }

    public static SpanBuilder buildSpan(Tracer tracer, String name) {
        return tracer.spanBuilder(name)
                .setRecordEvents(true)
                .setSampler(Samplers.alwaysSample());
    }
}

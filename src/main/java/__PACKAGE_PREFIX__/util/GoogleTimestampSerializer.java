package __PACKAGE_PREFIX__.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.cloud.Timestamp;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class GoogleTimestampSerializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(new SimpleDateFormat(Patterns.ISO_DATE_TIME_FORMAT).format(value.toSqlTimestamp()));
    }
}

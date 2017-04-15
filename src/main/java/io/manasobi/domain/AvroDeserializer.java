package io.manasobi.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by manasobi on 2017-04-15.
 */
@Slf4j
public class AvroDeserializer<T> implements Deserializer<Point> {

    protected final Class<Point> targetType = Point.class;

    @Override
    public void close() {
        // No-op
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        // No-op
    }

    public Point deserialize(String topic, byte[] data) {
        try {

            if (data != null) {
                //log.debug("data='{}'", DatatypeConverter.printHexBinary(data));

                DatumReader<GenericRecord> datumReader =
                        new SpecificDatumReader<>(targetType.newInstance().getSchema());
                Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);

                GenericRecord result = datumReader.read(null, decoder);

                Point point1 = new Point();

                point1.setTimestamp((Long) result.get("timestamp"));

                //log.debug("deserialized data='{}'", result);
                return point1;
            }

        } catch (Exception ex) {
            throw new SerializationException(
                    "Can't deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", ex);
        }

        return null;
    }
}

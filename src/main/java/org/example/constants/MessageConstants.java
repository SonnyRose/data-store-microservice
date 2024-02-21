package org.example.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {
    //Constance for creating data from payload in DebeziumEventConsumerImpl.class
    public final static String ID_KEY = "id";
    public final static String SENSOR_ID_KEY = "sensor_id";
    public final static String MEASUREMENT_KEY = "measurement";
    public final static String TIMESTAMP_KEY = "timeStamp";
    public final static String MEASUREMENT_TYPE_KEY = "type";
}

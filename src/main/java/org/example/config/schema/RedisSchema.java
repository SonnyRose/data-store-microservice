package org.example.config.schema;

import org.example.config.helper.KeyHelper;
import org.example.model.MeasurementType;

public class RedisSchema {
    //set
    public static String sensorKeys(){
        return KeyHelper.getKey("sensors");
    }
    //hash with summaryTypes
    public static String summaryKey(long sensorId,
                                    MeasurementType measurementType
    ){
        return KeyHelper.getKey("sensors:" + sensorId + ":" + measurementType.name().toLowerCase());
    }

}

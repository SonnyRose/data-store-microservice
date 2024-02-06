package org.example.config.schema;

import lombok.Getter;
import org.example.config.helper.KeyHelper;
import org.example.model.types.MeasurementType;

@Getter
public class RedisSchema {
    public static String getKey(String key){
        return key;
    }
    public static String sensorKeys(){
        return KeyHelper.getKey("sensors");
    }
    public static String summaryKey(long sensorId,
                                    MeasurementType measurementType
    ){
        return KeyHelper.getKey("sensors:" + sensorId + ":" + measurementType.name().toLowerCase());
    }

}

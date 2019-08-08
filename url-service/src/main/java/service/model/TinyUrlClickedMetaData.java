package service.model;

import java.util.HashMap;
import java.util.Map;

public class TinyUrlClickedMetaData {
    private Map<String, String> metaData = new HashMap<>();

    public String getMetaDataValue(String field){
        return metaData.getOrDefault(field, "N/A");
    }

    public void addMetaData(String field, String value){
        metaData.put(field, value);
    }

    @Override
    public String toString() {
        return "TinyUrlClickedMetaData{" +
                "metaData=" + metaData +
                '}';
    }
}

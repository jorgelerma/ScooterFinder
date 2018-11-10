package grin.com.challenge.models;

import java.util.Map;

public class Scooter {
    public String id;
    public String code;
    public float latitude;
    public float longitude;

    public Scooter(Map<String, String> data) {
        id = data.get("id");
        code = data.get("code");
        latitude = Float.parseFloat(data.get("latitude"));
        longitude = Float.parseFloat(data.get("longitude"));
    }
}

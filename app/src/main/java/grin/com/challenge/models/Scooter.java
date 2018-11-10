package grin.com.challenge.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Map;

public class Scooter implements Parcelable {
    public String id;
    public String code;
    public float latitude;
    public float longitude;
    public float battery;


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(code);
        parcel.writeFloat(latitude);
        parcel.writeFloat(longitude);
        parcel.writeFloat(battery);
    }


    public Scooter(Parcel in) {
        this.id = in.readString();
        this. code = in.readString();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.battery = in.readFloat();
    }


    public static final Creator<Scooter> CREATOR = new Creator<Scooter>() {
        @Override
        public Scooter createFromParcel(Parcel in) {
            return new Scooter(in);
        }

        @Override
        public Scooter[] newArray(int size) {
            return new Scooter[size];
        }
    };
}

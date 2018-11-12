package grin.com.challenge.models;

import android.app.Service;
import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {

    public String id;
    public String code;
    public float latitude;
    public float longitude;
    public float battery;

    protected Users(Parcel in) {
        this.id = in.readString();
        this.code = in.readString();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.battery = in.readFloat();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

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
}

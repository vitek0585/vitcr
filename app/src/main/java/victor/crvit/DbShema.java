package victor.crvit;

/**
 * Created by Viktor on 1/22/2017.
 */

public class DbShema {
    public static final class LocationTable {
        public static final String NAME = "location";
    }

    public static final class Cols {
        public static final String UUID = "id";
        public static final String Latitude = "latitude";
        public static final String Longitude = "longitude";
        public static final String Date = "time";
        public static final String DateAdd = "createdDate";
    }
}

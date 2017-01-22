package victor.crvit.repositories;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import victor.crvit.DbShema;


public class CursorWraper extends CursorWrapper {
    public CursorWraper(Cursor cursor) {
        super(cursor);
    }

    public Location getLocation() {
        Location location = new Location();
        location.uuiid = getString(getColumnIndex(DbShema.Cols.UUID));
        location.Latitude = getDouble(getColumnIndex(DbShema.Cols.Latitude));
        location.Longitude = getDouble(getColumnIndex(DbShema.Cols.Longitude));
        location.time = getLong(getColumnIndex(DbShema.Cols.Date));
        location.createdDate = getLong(getColumnIndex(DbShema.Cols.DateAdd));
        return location;
    }
}

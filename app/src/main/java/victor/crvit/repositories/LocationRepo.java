package victor.crvit.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import victor.crvit.DbShema;
import victor.crvit.service.LocationHelper;

/**
 * Created by Viktor on 1/22/2017.
 */

public class LocationRepo {

    private static LocationRepo locationRepo;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private LocationRepo(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new LocationHelper(mContext)
                .getWritableDatabase();
    }

    public static LocationRepo Create(Context context){
        if(locationRepo ==null){
            locationRepo = new LocationRepo(context);
        }

        return locationRepo;
    }
    public void add(Location location) {
        ContentValues values = new ContentValues();
        values.put(DbShema.Cols.Latitude, location.Latitude);
        values.put(DbShema.Cols.Longitude, location.Longitude);
        values.put(DbShema.Cols.Date, location.time);
        values.put(DbShema.Cols.DateAdd, location.createdDate);
        mDatabase.insert(DbShema.LocationTable.NAME,null,values);
    }

    private CursorWraper getAll() {
        Cursor cursor =  mDatabase.query(
                DbShema.LocationTable.NAME,
                null, // Columns - null выбирает все столбцы
                null,
                null ,
                null, // groupBy
                null, // having
                DbShema.Cols.UUID +" DESC" // orderBy
        );

        return new CursorWraper(cursor);
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        CursorWraper cursor = getAll();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                locations.add(cursor.getLocation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return locations;
    }
}

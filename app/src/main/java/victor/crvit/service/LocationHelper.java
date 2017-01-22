package victor.crvit.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import victor.crvit.DbShema;

/**
 * Created by Viktor on 1/22/2017.
 */

public class LocationHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "location.db";

    public LocationHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DbShema.LocationTable.NAME
                + " (id integer primary key autoincrement, "
                + DbShema.Cols.Latitude + " text, "
                + DbShema.Cols.Longitude + " text, "
                + DbShema.Cols.Date + " long, "
                + DbShema.Cols.DateAdd + " long);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

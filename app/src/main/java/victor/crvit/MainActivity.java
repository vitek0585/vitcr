package victor.crvit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import victor.crvit.adapters.LocationAdapter;
import victor.crvit.repositories.Location;
import victor.crvit.repositories.LocationRepo;
import victor.crvit.service.DoSomething;
import victor.crvit.service.GPSTracker;

public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;

    // GPSTracker class
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnShowLocation = (Button) findViewById(R.id.gprs_button);
        gps = new GPSTracker(MainActivity.this);
        final Context context = getApplicationContext();
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                context.startService(new Intent(context, DoSomething.class));

                // check if GPS enabled
                if (gps.canGetLocation()&&false) {

                    gps.getLocation();
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    long time = gps.getLocationTime();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude + '\n' + time,
                            Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                   // gps.showSettingsAlert();
                }

            }

        });
    }

    public void stopServ(View view){
        stopService(new Intent(this,DoSomething.class));
    }
    public void updateView(View view){
        LocationRepo locationRepo = LocationRepo.Create(this);
        List<Location> locations = locationRepo.getLocations();
        LocationAdapter locationAdapter = new LocationAdapter(locations.toArray(new Location[locations.size()]), this);
        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(locationAdapter);
    }

}

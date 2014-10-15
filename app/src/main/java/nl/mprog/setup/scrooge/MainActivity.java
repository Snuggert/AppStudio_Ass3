package nl.mprog.setup.scrooge;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMapLongClickListener {
    /* Might be null if Google Play services APK is not available. */
    private GoogleMap mMap;
    private Marker ownPosMarker;

    /* Stores the current instantiation of the location client in this object */
    private LocationClient mLocationClient;

    /* Define an object that holds accuracy and frequency parameters. */
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();

        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(
                LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(
                LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);

        Button gpsButton = (Button) findViewById(R.id.GPS);
        gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mLocationClient.isConnected()){
                    mLocationClient.disconnect();
                }else{
                    mLocationClient.connect();
                }
            }
        });
    }

    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {
        if (mLocationClient.isConnected()) {
            mLocationClient.removeLocationUpdates(this);
        }
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        mLocationClient.disconnect();
        super.onStop();
    }

    /*
     * Called when the Activity is going into the background.
     * Parts of the UI may be visible, but the Activity is inactive.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();
        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }
    @Override
    public void onDisconnected() {}
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location) {
        /* Reset MarkerPosition */
        if(ownPosMarker != null)
            ownPosMarker.remove();
        ownPosMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(),
                                     location.getLongitude()))
                .title("Your Position")
                .icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_BLUE)));
    }


    @Override
    public void onMapLongClick(LatLng point) {
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED)));
    }

    private void setUpMapIfNeeded() {
        /*
         * Do a null check to confirm that we have not already instantiated
         * the map.
         */
        if (mMap == null) {
            /* Try to obtain the map from the SupportMapFragment. */
            mMap = ((SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            /* Check if we were successful in obtaining the map. */
            if (mMap != null) {
                mMap.setOnMapLongClickListener(this);
            }
        }
    }
}

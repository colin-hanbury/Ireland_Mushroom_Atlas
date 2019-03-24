package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private double lastLocationLat;
    private double lastLocationLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //set up Firebase reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setUpMapIfNeeded();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                marker.getTag();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MapsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MapsActivity.this);
                }
                builder.setTitle("View Observation")
                        .setMessage("Would you like to view this mushroom observation?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent viewObservation = new Intent(MapsActivity.this, ViewObservationActivity.class);
                                Bundle b = new Bundle();
                                b.putString("id", (String) marker.getTag());
                                viewObservation.putExtras(b);
                                startActivity(viewObservation);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    private void setUpMap() {

        final DatabaseReference ref = mDatabase.child("mushroom_locations").getRef();
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long max = 0;
                //read from database
                for (DataSnapshot locSnapshot : dataSnapshot.getChildren()) {
                    //get location from database
                    LocationData loc = (LocationData) locSnapshot.getValue(LocationData.class);
                    //get the observation ID
                    String tag = locSnapshot.getKey().toString();
                    if (loc != null) {
                        //add marker to the map with the observation ID as the title
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(loc.getLatitude(), loc.getLongitude())))
                        .setTag(tag);

                        long recordedTime = Long.parseLong(locSnapshot.getKey());
                        if (recordedTime > max) {
                            max = recordedTime;
                            lastLocationLat = loc.latitude;
                            lastLocationLong = loc.longitude;

                        }
                    }
                    //adjust camera to most recent marker created
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(lastLocationLat,
                                    lastLocationLong));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                    ref.removeEventListener(this);
                }
            }

           @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    @Override
    public void onInfoWindowClick(Marker marker) {}
}

package org.uitm.ice;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.map_tab2);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(2.16, 102.43);
        LatLng merlimau = new LatLng(2.153708, 102.427699);
        LatLng jamaluddin = new LatLng(2.153708, 102.427699);
        LatLng malaya = new LatLng(2.155456, 102.427411);
        LatLng ting = new LatLng(2.147843, 102.426639);
        LatLng limHang = new LatLng(2.146331, 102.425942);
        LatLng mariam = new LatLng(2.145006, 102.422510);
        LatLng yunus = new LatLng(2.145441, 102.421779);

        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("Taman Harmoni")
                .snippet("Ramai Budak Poli dan U duduk disini"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        mMap.addMarker(new MarkerOptions().position(merlimau)
                .title("Klinik Kesihatan merlimau ")
                .snippet("Klinik Kesihatan merlimau"));
        mMap.addMarker(new MarkerOptions().position(jamaluddin)
                .title("Klinik Dr Jamaludin dan surgery ")
                .snippet("Klinik Jr Jamaludin dan surgery"));
        mMap.addMarker(new MarkerOptions().position(malaya)
                .title("Poliklinik Malaya ")
                .snippet("Ramai Budak Poli dan U duduk disini"));
        mMap.addMarker(new MarkerOptions().position(ting)
                .title("Klinik Dr Ting ")
                .snippet("Ramai Budak Poli dan U duduk disini"));
        mMap.addMarker(new MarkerOptions().position(limHang)
                .title("Limhang Klinik & surgery ")
                .snippet("Ramai Budak Poli dan U duduk disini"));
        mMap.addMarker(new MarkerOptions().position(yunus)
                .title("Poliklinik Mohd Yunus ")
                .snippet("Ramai Budak Poli dan U duduk disini"));
        mMap.addMarker(new MarkerOptions().position(mariam)
                .title("Klinik Dr Mariam Poliklinik & surgery ")
                .snippet("Ramai Budak Poli dan U duduk disini"));


        mMap.setMyLocationEnabled(true);
    }
}

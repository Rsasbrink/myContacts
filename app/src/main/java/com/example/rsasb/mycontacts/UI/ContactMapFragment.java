package com.example.rsasb.mycontacts.UI;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ContactMapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private static List<Contact> mContacts = new ArrayList<>();
    public final static int TASK_GET_ALL_CONTACTS = 0;

    //Database related local variables

    static AppDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_map, container, false);
        db = AppDatabase.getInstance(getActivity());
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mContacts = db.contactDao().getAll();

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Contact contact : db.contactDao().getAll()) {
                      if (contact.getFullName().trim() != "" && !contact.getFullName().isEmpty() && contact.getFullAddress().trim() != "" && !contact.getFullAddress().trim().isEmpty()) {
                            LatLng location = getLocationFromAddress(getActivity(), contact.getFullAddress().trim());
                            googleMap.addMarker(new MarkerOptions().position(location).title(contact.getFullName()).snippet("Marker Description"));
                          builder.include(location);
                      }
                    }


                LatLngBounds bounds = builder.build();


                int padding = 300; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);


                googleMap.moveCamera(cu);

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = (Address) address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    public class ContactAsyncTask extends AsyncTask<Contact, Void, List> {

        private int taskCode;

        public ContactAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(Contact... contacts) {
            switch (taskCode) {


            }

            //To return a new list with the updated data, we get all the data from the database again.
            return db.contactDao().getAll();
        }


    }
}

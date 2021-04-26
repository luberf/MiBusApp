package com.example.yisela.mibusnavidrawer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class UbicaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private static final String TAG = "PlaceAutocompleteAd";
    public LatLng miubicacion;
    public LatLng direccionorigen = null;
    public LatLng direcciondestino = null;
    double latorigen;
    double lngorigen;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubica);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.origen);
        PlaceAutocompleteFragment autocompleteFragmentdes = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.destino);
        int permissionCheck = ContextCompat.checkSelfPermission(UbicaActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
        {
        } else
            {
            ActivityCompat.requestPermissions(UbicaActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            }
        LocationManager locationManager = (LocationManager) UbicaActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener()

        {
            @Override
            public void onLocationChanged(Location location)
            {
                latorigen = location.getLatitude();
                lngorigen = location.getLongitude();
                miubicacion = new LatLng(latorigen, lngorigen);
                direccionorigen = miubicacion;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }
        };
        //locationManager.requestLocationUpdates(LocationManager, 0, 0, locationListener);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(Place place)
            {
                //Log.i(TAG, "Place: " + place.getName());
                if(place !=null)
                {
                    direccionorigen = place.getLatLng();
                }
            }

            @Override
            public void onError(Status status)
            {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        autocompleteFragment.setHint("Mi Ubicacion");
        autocompleteFragment.setBoundsBias(new LatLngBounds(//Solo busca dentro de estas coordenadas (Popayan)
                new LatLng(2.407817, -76.655555),//suroeste
                new LatLng(2.533026, -76.526767)));//noroeste

        autocompleteFragmentdes.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(Place placedes)
            {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());
                if(placedes !=null)
                {
                    direcciondestino = placedes.getLatLng();
                }
            }

            @Override
            public void onError(Status status)
            {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        autocompleteFragmentdes.setHint("¿A donde vas?");
        autocompleteFragmentdes.setBoundsBias(new LatLngBounds(
                new LatLng(2.407817, -76.655555),
                new LatLng(2.533026, -76.526767)));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        LatLng popayan = new LatLng(2.441924, -76.606339);
        LatLng inicio = new LatLng(2.435323, -76.611307);
        LatLng fin = new LatLng(2.459196, -76.593339);
        CameraPosition camPos = new CameraPosition.Builder()
                .target(popayan)   //Centramos el mapa en Popayan
                .zoom(15)         //Establecemos el zoom en 15
                .bearing(45)      //Establecemos la orientación con el noreste arriba
                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);
        mMap.addMarker(new MarkerOptions().position(inicio).title(""));
        mMap.addMarker(new MarkerOptions().position(fin).title(""));
        Polyline rutamuestra = mMap.addPolyline(new PolylineOptions()
                .visible(true)//aqui dice si la polyline es visible true-false un ejemplo
                .add(new LatLng(2.459196, -76.593339), new LatLng(2.456298, -76.596030)
                        , new LatLng(2.455201, -76.597494), new LatLng(2.451572, -76.605224)
                        , new LatLng(2.435323, -76.611307))
                .width(10)
                .color(Color.RED));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else
                    {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void onClick(View view)
    {
        if (direccionorigen != null && direcciondestino != null)
        {
            borrarOnClick();
            rutafragment();
            //Distancia();
            mostrarboton();
        }else
        {
            Toast.makeText(this, "Ingrese punto de origen y punto destino\n               *Active su ubicacion*",Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarboton()
    {
        Button resetButton= findViewById(R.id.verrutas);
        resetButton.setVisibility(View.VISIBLE); //To set visible
    }

    public void borrarOnClick() {
        mMap.clear();
    }

    public void rutafragment()
    {
        if (direccionorigen != null && direcciondestino != null )
        {
            Marker a = mMap.addMarker(new MarkerOptions().position(direccionorigen).title("Origen"));
            a.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pegmancuatro));
            mMap.addMarker(new MarkerOptions().position(direcciondestino).title("Destino"));
            try
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(direccionorigen, direcciondestino), 260));
            }
            catch(Exception e)
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(direcciondestino, direccionorigen), 260));
            }

            mMap.addPolyline(new PolylineOptions()
                    .visible(true)
                    .add(direccionorigen, direcciondestino)
                    .width(5)
                    .color(Color.GREEN));
        }
    }
    public void ruta()//Sirbvio para saber como se utiliza pero solo sirve con edit text en el xml
    {
        EditText location_start = findViewById(R.id.origen);
        String locationstart = location_start.getText().toString();
        List<Address> addressListstart = null;
        if (locationstart != null || locationstart.equals(""))
        {
            Geocoder geocoderstart = new Geocoder(this);
            try
            {
                addressListstart = geocoderstart.getFromLocationName(locationstart, 1);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Address addressstart = addressListstart.get(0);
        LatLng latLngstart = new LatLng(addressstart.getLatitude(), addressstart.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLngstart).title("Marcador"));

        EditText location_tf = findViewById(R.id.destino);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        if (location != null || location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try
            {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador"));

        //mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(latLngstart, latLng),250));
        mMap.addPolyline(new PolylineOptions()
                    .visible(true)
                    .add(latLngstart, latLng)
                    .width(5)
                    .color(Color.GREEN));
    }

    public void Distancia()
    {
        Location locationA = new Location("punto A");

        locationA.setLatitude(direccionorigen.latitude);
        locationA.setLongitude(direccionorigen.longitude);

        Location locationB = new Location("punto B");

        locationB.setLatitude(direcciondestino.latitude);
        locationB.setLongitude(direcciondestino.longitude);

        float distancia = locationA.distanceTo(locationB);
        Toast.makeText(this, "La distancia entre\n los puntos es :\n" + distancia+ "Metros",Toast.LENGTH_SHORT).show();
    }

    public void verRutas(View view)
    {
        Intent intent= new Intent (UbicaActivity.this, ConfirmarActivity.class);
        intent.putExtra("Latlngorigen", direccionorigen);
        intent.putExtra("Latlngdestino",direcciondestino);
        startActivity(intent);
    }
}
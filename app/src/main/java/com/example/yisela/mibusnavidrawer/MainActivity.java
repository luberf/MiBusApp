package com.example.yisela.mibusnavidrawer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AyudaFragment.OnFragmentInteractionListener,
        ContactosFragment.OnFragmentInteractionListener,
        LegalFragment.OnFragmentInteractionListener,
        BusFragment.OnFragmentInteractionListener,
        ViajesFragment.OnFragmentInteractionListener,
        MapaFragment.OnFragmentInteractionListener
{
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Fragment fragment = new MapaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main,fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment miFragment=null;
        boolean fragmentSeleccionado=false;

        if (id == R.id.nav_camera)
        {
            // Handle the camera action
            miFragment=new MapaFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_gallery)
        {
            miFragment=new ViajesFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_slideshow)
        {
            Intent i = new Intent(MainActivity.this, ConfiguraActivity.class);
            startActivity(i);
            //miFragment=new ConfigFragment();
            //fragmentSeleccionado=true;
        } else if (id == R.id.nav_manage)
        {
            miFragment=new AyudaFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_share)
        {
            miFragment=new ContactosFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_send)
        {
            miFragment=new LegalFragment();
            fragmentSeleccionado=true;
        }
        if(fragmentSeleccionado)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
            {
                super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}

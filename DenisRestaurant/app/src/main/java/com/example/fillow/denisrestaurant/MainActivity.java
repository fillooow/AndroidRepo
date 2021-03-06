package com.example.fillow.denisrestaurant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fillow.denisrestaurant.LogInFragm.ChangePasswordCodeFragment;
import com.example.fillow.denisrestaurant.UserInfoFragm.ChangePhone.ChangePhoneCodeFragment;
import com.example.fillow.denisrestaurant.UserInfoFragm.ChangePhone.ChangePhoneNumberFragment;
import com.example.fillow.denisrestaurant.LogInFragm.LogInFragment;
import com.example.fillow.denisrestaurant.UserInfoFragm.UserInfoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        Fragment fragment = new RecomendationsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frgmCont, fragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        switch (item.getItemId()){
            case R.id.nav_camera:
                fragment = new RecomendationsFragment();
                break;
            case R.id.nav_gallery:
                fragment = new MapFragment();
                break;
            case R.id.nav_slideshow:
                fragment = new ChangePhoneNumberFragment();
                break;
            case R.id.nav_manage:
                fragment = new LogInFragment();
                break;
            case R.id.nav_user_account:
                fragment = new UserInfoFragment();
                break;
            case R.id.nav_share:
                fragment = new ChangePhoneCodeFragment();
                break;
            case R.id.nav_send:
                break;
            default:
                fragment = new RecomendationsFragment();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        ft.replace(R.id.frgmCont, fragment).commit();
        return true;
    }
}

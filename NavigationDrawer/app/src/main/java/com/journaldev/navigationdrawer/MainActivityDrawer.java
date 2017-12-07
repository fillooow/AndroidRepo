package com.journaldev.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.journaldev.navigationdrawer.API.APIUtils;
import com.journaldev.navigationdrawer.API.Model;
import com.journaldev.navigationdrawer.API.PenyokService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityDrawer extends AppCompatActivity{

    public static int navItemIndex = 0;
    private Handler mHandler;
    private PenyokService mainService;
    private String[] activityTitles;
    public static String sid = "";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView statusTV;
    private TextView userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        mainService = APIUtils.getPService();
        mHandler = new Handler();
        if ( getIntent().getExtras() == null) {
            Intent intent = new Intent(MainActivityDrawer.this, LoginActivity.class);
            // Запрещаем возвращаться назад на логин активити
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            sid = getIntent().getStringExtra("sid");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statusTV = (TextView) findViewById(R.id.textStatus);
        userNickname = (TextView) findViewById(R.id.userNickName);

        drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        activityTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        loadHeader();
        setupNavigationMenu();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            loadFragment();
        }
    }

    private void loadHeader() {
        loadStatus();
        loadNickname();
    }

    private void loadNickname() {
    }

    private void loadStatus() {
        mainService.getStatus().enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                    if(response.body().getFillState() != null & statusTV != null){
                        statusTV.setText(response.body().getFillState());
                    } else
                        Log.d("anus", "anus панды");
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
            }
        });
    }

    private void setupNavigationMenu() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                Fragment fragment;
                switchFragment();

                switch (item.getItemId()) {
                    case R.id.nav_stats:
                        navItemIndex = 0;
                        fragment = new StatsFragment();
                        break;
                    case R.id.nav_loans:
                        navItemIndex = 1;
                        fragment = new TransactionsFragment();
                        break;
                    case R.id.nav_friends:
                        navItemIndex = 2;
                        fragment = new FriendsFragment();
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 3;
                        fragment = new StatsFragment();
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 4;
                        fragment = new StatsFragment();
                        break;
                    default:
                        navItemIndex = 0;
                        fragment = new StatsFragment();
                }

                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                //TODO: тут остановился, я хз что надо, залипаю уже
                setTitle(activityTitles[navItemIndex]);
                drawer.closeDrawers();

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void loadFragment(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        drawer.closeDrawers();
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
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

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new StatsFragment();
            case 1:
                return new TransactionsFragment();
            case 2:
                return new FriendsFragment();
            case 3:
                return new StatsFragment();

            case 4:
                return new StatsFragment();
            default:
                return new StatsFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_drawer, menu);
        return true;
    }


    private void switchFragment() {
    }
}

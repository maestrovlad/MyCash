package com.example.vb.mycash;

import android.content.Context;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DayFragment.OnFragmentInteractionListener,
        WeekFragment.OnFragmentInteractionListener ,
        MounthFragment.OnFragmentInteractionListener,
        YearFragment.OnFragmentInteractionListener,
        CategoriesFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener{

    public static ArrayList<Cash> expenses = new ArrayList<Cash>();
    public static ArrayList<Cash> incomes = new ArrayList<Cash>();
    public static ArrayList<CategoriesCash> categoriesExpenses = new ArrayList<CategoriesCash>();
    public static ArrayList<CategoriesCash> categoriesIncomes = new ArrayList<CategoriesCash>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
       // dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dataBaseHelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        dataBaseHelper.getItemCategoryIncome();
        dataBaseHelper.getItemCategoryExpenses();
        displayView(R.id.day_cash);
        Log.d("start", "onCreate: ");
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.day_cash) {
            // Handle the camera action
        } else if (id == R.id.week_cash) {

        } else if (id == R.id.mounth_cash) {

        } else if (id == R.id.year_cash) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        displayView(id);
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.day_cash:
                fragment = new DayFragment();
                title = "Day";

                break;
            case R.id.week_cash:
                fragment = new WeekFragment();
                title = "Week";

                break;
            case R.id.mounth_cash:
                fragment = new MounthFragment();
                title = "Mounth";

                break;
            case R.id.year_cash:
                fragment = new YearFragment();
                title = "Year";

                break;
            case R.id.calendar_cash:
                fragment = new CalendarFragment();
                title = "Calendar";

                break;
            case R.id.setting_categories:
                fragment = new CategoriesFragment();
                title = "Categories";

                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @Override
    public void onFragmentInteraction(String dayCalendar, String weekCalendar, String mounthCalendar, String yearCalendar) {
        Log.d("qrrr3444", dayCalendar + " mainact");
        DayFragment fragmentDay = new DayFragment();
        fragmentDay.oneDayDate = dayCalendar;
        WeekFragment fragmentWeek = new WeekFragment();
        fragmentWeek.oneWeekDate = dayCalendar;
        MounthFragment fragmentMounth = new MounthFragment();
        fragmentMounth.oneMounthDate = dayCalendar;
        YearFragment fragmentYear = new YearFragment();
        fragmentYear.oneYearDate = dayCalendar;

    }
}

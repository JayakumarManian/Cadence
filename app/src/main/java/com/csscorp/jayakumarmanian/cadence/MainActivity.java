package com.csscorp.jayakumarmanian.cadence;

import android.app.ActionBar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static String LoginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Intent intent=getIntent();
        Toast.makeText(getApplicationContext(),
                "Welcome :"+LoginUser, Toast.LENGTH_LONG).show();

        LoginUser = intent.getStringExtra("MobileNo");
        Toast.makeText(getApplicationContext(),
                "Welcome :"+LoginUser, Toast.LENGTH_LONG).show();


        onSectionAttached(
                1);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        MainView view=new MainView();
        fragmentManager.beginTransaction()
                .replace(R.id.container, view)
                .commit();
            }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        onSectionAttached(
                 position+1);
        switch (position+1) {
            case 1:
                MainView mainView=new MainView();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mainView)
                        .commit();
                break;
            case 2:
                HistoryScreen historyScreen=new HistoryScreen();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, historyScreen)
                        .commit();
                break;
            case 3:
                StatisticsScreen StatisticsScreen=new StatisticsScreen();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StatisticsScreen)
                        .commit();
                break;
            case 4:
                AboutScreen aboutScreen=new AboutScreen();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, aboutScreen)
                        .commit();
                break;
            case 5:

                finish();
                          break;
        }
    }

   public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_history);
                break;
            case 3:
                mTitle = getString(R.string.title_statistics);
                break;
            case 4:
                mTitle = getString(R.string.title_about);
                break;
            case 5:
                mTitle = getString(R.string.title_exit);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsView settingsView=new SettingsView();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, settingsView)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

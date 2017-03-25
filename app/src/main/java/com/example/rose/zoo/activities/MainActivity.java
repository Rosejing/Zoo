package com.example.rose.zoo.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rose.zoo.R;
import com.example.rose.zoo.events.DrawerSectionItemClickedEvent;
import com.example.rose.zoo.fragments.ExhibitsListFragment;
import com.example.rose.zoo.fragments.GalleryFragment;
import com.example.rose.zoo.fragments.ZooMapFragment;
import com.example.rose.zoo.utils.EventBus;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String mCurrentFragmentTitle;
    private Button Login, Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setHomeButtonEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.drawer_open);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.drawer_closed);
            }
        };

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        displayInitialFragment();
    }

    private void displayInitialFragment(){
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, ExhibitsListFragment.getInstance()).commit();
        //mCurrentFragmentTitle = getString(R.string.section_exhibits);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, GalleryFragment.getInstance()).commit();
        mCurrentFragmentTitle = getString(R.string.section_gallery);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
            //if this is true, the actionbar has its home button hit, and return true through its own option selected
            return true;

        int id = item.getItemId();
        if(id == R.id.action_login) {
            int status = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("status",status);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.update) {
            int status = 2;
            //Bundle bundle = new Bundle();
            //bundle.putInt("status", status);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getInstance().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onDrawerSectionItemClickEvent(DrawerSectionItemClickedEvent event) {
        mDrawerLayout.closeDrawers();

        if( event == null || TextUtils.isEmpty( event.section) || event.section.equalsIgnoreCase(mCurrentFragmentTitle)) {
            return;
        }

        Toast.makeText(this, "MainActivity: Section Clicked: " + event.section, Toast.LENGTH_SHORT ).show();

        if( event.section.equalsIgnoreCase(getString(R.string.section_map))){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ZooMapFragment.getInstance()).commit();
        } else if (event.section.equalsIgnoreCase(getString(R.string.section_gallery))) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, GalleryFragment.getInstance()).commit();
        }
        else if( event.section.equalsIgnoreCase(getString(R.string.section_exhibits))){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ExhibitsListFragment.getInstance()).commit();
        }
        else {
            return;
        }

        mCurrentFragmentTitle = event.section;
    }
}

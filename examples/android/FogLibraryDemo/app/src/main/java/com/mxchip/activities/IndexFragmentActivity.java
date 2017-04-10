package com.mxchip.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mxchip.fragments.BookFragment;
import com.mxchip.fragments.DeviceListFragment;
import com.mxchip.fragments.EasyLinkFragment;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

public class IndexFragmentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager = getSupportFragmentManager();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设备列表");
        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction().replace(R.id.content, new DeviceListFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toadddevice:
                        Intent intent = new Intent(IndexFragmentActivity.this, AddDeviceActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
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
        getMenuInflater().inflate(R.menu.devicelist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_easylink) {
            // Handle the camera action
            toolbar.setTitle("配网");
            fragmentManager.beginTransaction().replace(R.id.content, new EasyLinkFragment()).commit();
        } else if (id == R.id.nav_cloudrecipe) {
            toolbar.setTitle("云食谱");
            fragmentManager.beginTransaction().replace(R.id.content, new BookFragment()).commit();
        } else if (id == R.id.nav_userinfo) {

//        } else if (id == R.id.nav_other) {

        } else if (id == R.id.nav_devicelist) {
            toolbar.setTitle("设备列表");
            fragmentManager.beginTransaction().replace(R.id.content, new DeviceListFragment()).commit();
        }else if(id == R.id.nav_logout){
            SharePreHelper shareph = new SharePreHelper(IndexFragmentActivity.this);
            shareph.removeData(CommonPara.SHARE_TOKEN);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}

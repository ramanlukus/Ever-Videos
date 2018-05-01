package com.viaviapp.allinonevideosapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.ItemAbout;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FragmentManager fragmentManager;
    NavigationView navigationView;
    private AdView mAdView;
    Toolbar toolbar;
    LinearLayout lay_dev;
    ArrayList<ItemAbout> mListItem;
    TextView txt_develop,txt_devname;
    JsonUtils jsonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        jsonUtils = new JsonUtils(this);
        jsonUtils.forceRTLIfSupported(getWindow());

         mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        lay_dev=(LinearLayout)findViewById(R.id.dev_lay);
        txt_develop=(TextView)findViewById(R.id.text_develop);
        txt_devname=(TextView)findViewById(R.id.dev_name);

        Typeface tf = Typeface.createFromAsset(getAssets(),"myfonts/custom.ttf");
        Typeface tfbold = Typeface.createFromAsset(getAssets(),"myfonts/custom.ttf");
        txt_devname.setTypeface(tfbold);
        txt_develop.setTypeface(tf);


        mListItem = new ArrayList<>();
         if (JsonUtils.isNetworkAvailable(MainActivity.this)) {
            new MyTaskDev().execute(Constant.ABOUT_US_URL);
        }else {
            showToast(getString(R.string.network_msg));
        }
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        fragmentManager = getSupportFragmentManager();
        HomeFragment currenthome = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();

    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_home:
                                HomeFragment currenthome = new HomeFragment();
                                fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();
                                toolbar.setTitle(getString(R.string.menu_home));

                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_latest:
                                LatestFragment latefragment = new LatestFragment();
                                fragmentManager.beginTransaction().replace(R.id.fragment1, latefragment).commit();
                                toolbar.setTitle(getString(R.string.menu_latest));
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_cat:
                                CategoryFragment catfragment = new CategoryFragment();
                                fragmentManager.beginTransaction().replace(R.id.fragment1, catfragment).commit();
                                toolbar.setTitle(getString(R.string.menu_category));
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_fav:
                                FavoriteFragment favfragment = new FavoriteFragment();
                                fragmentManager.beginTransaction().replace(R.id.fragment1, favfragment).commit();
                                toolbar.setTitle(getString(R.string.menu_favorite));
                                mDrawerLayout.closeDrawers();
                                break;

                            case R.id.sub_abus:
                                Intent intentab=new Intent(MainActivity.this,AboutUsActivity.class);
                                startActivity(intentab);
                                 mDrawerLayout.closeDrawers();
                                break;

                            case R.id.sub_shareapp:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareapp_msg)+"\n"+"\n"+"https://play.google.com/store/apps/details?id="+getPackageName());
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                                 mDrawerLayout.closeDrawers();
                                break;
                            case R.id.sub_rateapp:
                                final String appName = MainActivity.this.getPackageName();
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id="
                                                    + appName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id="
                                                    + appName)));
                                }
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.sub_privacy:
                                Intent intenpri=new Intent(MainActivity.this,Privacy_Activity.class);
                                startActivity(intenpri);
                                 mDrawerLayout.closeDrawers();
                                break;
                        }
                        return true;
                    }
                });
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class MyTaskDev extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null == result || result.length() == 0) {
                showToast(getString(R.string.no_data_found));
            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
                    JSONObject objJson;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        ItemAbout itemAbout = new ItemAbout();

                        itemAbout.setappDevelop(objJson.getString(Constant.APP_DEVELOP));
                        mListItem.add(itemAbout);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setResult();
            }
        }
    }

    private void setResult() {

        ItemAbout itemAbout = mListItem.get(0);
        txt_develop.setText(itemAbout.getappDevelop());


    }

    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder alert = new AlertDialog.Builder(
                    MainActivity.this);
            alert.setTitle(getString(R.string.app_name));
            alert.setIcon(R.mipmap.app_icon);
            alert.setMessage("Are You Sure You Want To Quit?");

            alert.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            finish();
                        }

                    });
            alert.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
            alert.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
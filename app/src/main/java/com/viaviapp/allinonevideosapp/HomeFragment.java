package com.viaviapp.allinonevideosapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.adapter.LatestGridAdapter;
import com.example.item.ItemLatest;
import com.example.item.ItemSlider;
import com.example.util.Constant;
import com.example.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener {


    private SliderLayout mDemoSlider;
    List<ItemSlider> arrayofSlider;
    ItemSlider itemSlider;
    RelativeLayout mainlay;
    ProgressBar pbar;
    List<ItemLatest> arrayofLatest;
    List<ItemLatest> arrayofLatestVideoall;
    GridView gridView,gridViewallvideo;
    LatestGridAdapter objAdapter;
    private int columnWidth;
    Button btn_more,btn_moreall;
    private FragmentManager fragmentManager;
    private ItemLatest objAllBean;
    TextView txt_latest,txt_latestall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        arrayofSlider = new ArrayList<>();
        arrayofLatest = new ArrayList<>();
        arrayofLatestVideoall = new ArrayList<>();
        getActivity().setTitle(getString(R.string.app_name));
        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        mainlay = (RelativeLayout) rootView.findViewById(R.id.main);
        pbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        fragmentManager = getActivity().getSupportFragmentManager();

        gridView = (GridView) rootView.findViewById(R.id.gridcat);
        gridViewallvideo = (GridView) rootView.findViewById(R.id.gridcat_allvideo);
        btn_more=(Button)rootView.findViewById(R.id.btn_more);
        btn_moreall=(Button)rootView.findViewById(R.id.btn_moreall);
        txt_latest=(TextView)rootView.findViewById(R.id.text_title_latest);
        txt_latestall=(TextView)rootView.findViewById(R.id.text_title_latestall);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"myfonts/custom.ttf");
        Typeface tfbold = Typeface.createFromAsset(getActivity().getAssets(),"myfonts/custom.ttf");
        btn_more.setTypeface(tf);
        btn_moreall.setTypeface(tf);
        txt_latest.setTypeface(tfbold);
        txt_latestall.setTypeface(tfbold);

        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTaskSlider().execute(Constant.SLIDER_URL);
        } else {
            showToast(getString(R.string.network_msg));
         }

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatestFragment currenthome = new LatestFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();
            }
        });

        btn_moreall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllVideoFragment currenthome = new AllVideoFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();
            }
        });
        return rootView;
    }

    public class MyTaskSlider extends AsyncTask<String, Void, String> {

         @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbar.setVisibility(View.VISIBLE);
            mainlay.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pbar.setVisibility(View.INVISIBLE);
            mainlay.setVisibility(View.VISIBLE);

            if (null == result || result.length() == 0) {
                showToast(getString(R.string.no_data_found));

            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.SLIDER_ARRAY);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);

                        ItemSlider objItem = new ItemSlider();
                        objItem.setName(objJson.getString(Constant.SLIDER_NAME));
                        objItem.setImage(objJson.getString(Constant.SLIDER_IMAGE));
                        objItem.setLink(objJson.getString(Constant.SLIDER_LINK));
                        arrayofSlider.add(objItem);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setAdapterToFeatured();
            }

        }
    }

    public void setAdapterToFeatured() {

        for (int i = 0; i < arrayofSlider.size(); i++) {
            itemSlider = arrayofSlider.get(i);
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(itemSlider.getName());
            textSliderView.image(itemSlider.getImage().toString().replace(" ", "%20"));
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.getBundle().putString("extra", itemSlider.getLink());
            textSliderView.setOnSliderClickListener(this);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());

        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTaskVideo().execute(Constant.HOME_VIDOE_URL);

        } else {
            showToast(getString(R.string.network_msg));
        }

    }

    private class MyTaskVideo extends AsyncTask<String, Void, String> {

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
                    JSONObject mainJsonob = mainJson.getJSONObject(Constant.LATEST_ARRAY_NAME);
                    JSONArray jsonArray = mainJsonob.getJSONArray(Constant.HOME_LATEST_ARRAY_NAME);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);

                        ItemLatest objItem = new ItemLatest();

                        objItem.setId(objJson.getString(Constant.LATEST_ID));
                        objItem.setCategoryId(objJson.getString(Constant.LATEST_CATID));
                        objItem.setCategoryName(objJson.getString(Constant.LATEST_CAT_NAME));
                        objItem.setVideoUrl(objJson.getString(Constant.LATEST_VIDEO_URL));
                        objItem.setVideoId(objJson.getString(Constant.LATEST_VIDEO_ID));
                        objItem.setVideoName(objJson.getString(Constant.LATEST_VIDEO_NAME));
                        objItem.setDuration(objJson.getString(Constant.LATEST_VIDEO_DURATION));
                        objItem.setDescription(objJson.getString(Constant.LATEST_VIDEO_DESCRIPTION));
                        objItem.setImageUrl(objJson.getString(Constant.LATEST_IMAGE_URL));
                        objItem.setVideoType(objJson.getString(Constant.LATEST_VIDEOTYPE));

                        arrayofLatest.add(objItem);

                    }
                    JSONArray jsonArray2 = mainJsonob.getJSONArray(Constant.HOME_ALLVIDEO_ARRAY_NAME);
                    JSONObject objJson2 = null;
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        objJson2 = jsonArray2.getJSONObject(i);

                        ItemLatest objItem = new ItemLatest();

                        objItem.setId(objJson2.getString(Constant.LATEST_ID));
                        objItem.setCategoryId(objJson2.getString(Constant.LATEST_CATID));
                        objItem.setCategoryName(objJson2.getString(Constant.LATEST_CAT_NAME));
                        objItem.setVideoUrl(objJson2.getString(Constant.LATEST_VIDEO_URL));
                        objItem.setVideoId(objJson2.getString(Constant.LATEST_VIDEO_ID));
                        objItem.setVideoName(objJson2.getString(Constant.LATEST_VIDEO_NAME));
                        objItem.setDuration(objJson2.getString(Constant.LATEST_VIDEO_DURATION));
                        objItem.setDescription(objJson2.getString(Constant.LATEST_VIDEO_DESCRIPTION));
                        objItem.setImageUrl(objJson2.getString(Constant.LATEST_IMAGE_URL));
                        objItem.setVideoType(objJson2.getString(Constant.LATEST_VIDEOTYPE));

                        arrayofLatestVideoall.add(objItem);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAdapterToListview();
            }

        }
    }

    public void setAdapterToListview() {
        objAdapter = new LatestGridAdapter(getActivity(), R.layout.latest_row_item,
                arrayofLatest, columnWidth);
        gridView.setAdapter(objAdapter);

        objAdapter = new LatestGridAdapter(getActivity(), R.layout.latest_row_item,
                arrayofLatestVideoall, columnWidth);
        gridViewallvideo.setAdapter(objAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objAllBean=arrayofLatest.get(position);
                Constant.VIDEO_IDD=objAllBean.getId();
                Intent intplay=new Intent(getActivity(),VideoPlay.class);
                startActivity(intplay);
            }
        });

        gridViewallvideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objAllBean=arrayofLatestVideoall.get(position);
                Constant.VIDEO_IDD=objAllBean.getId();
                 Intent intplay=new Intent(getActivity(),VideoPlay.class);
                startActivity(intplay);
            }
        });

    }
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(slider.getBundle().getString("extra"))));
    }
 }

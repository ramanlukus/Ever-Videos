package com.viaviapp.allinonevideosapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adapter.LatestGridAdapter;
import com.example.item.ItemLatest;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllVideoFragment extends Fragment {

	GridView lsv_latest;
	List<ItemLatest> arrayOfLatestVideo;
	LatestGridAdapter objAdapter;
	private ItemLatest objAllBean;
	private int columnWidth;
	JsonUtils util;
 	ProgressBar pbar;
	InterstitialAd mInterstitial;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
 		View rootView = inflater.inflate(R.layout.fragment_latest, container, false);
		
		setHasOptionsMenu(true);
		getActivity().setTitle(getString(R.string.menu_allvideo));
		lsv_latest=(GridView)rootView.findViewById(R.id.gridcat);
		pbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		arrayOfLatestVideo=new ArrayList<ItemLatest>();

		util=new JsonUtils(getActivity());
 
 		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.ALL_VIDOE_URL);
		} else {
			showToast(getString(R.string.network_msg));
 		}
		
 		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
									long arg3) {
				// TODO Auto-generated method stub

				Constant.AD_COUNT++;
				if (Constant.AD_COUNT == Constant.AD_COUNT_SHOW) {
					Constant.AD_COUNT = 0;
					mInterstitial = new InterstitialAd(getActivity());
					mInterstitial.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
					mInterstitial.loadAd(new AdRequest.Builder().build());
					mInterstitial.setAdListener(new AdListener() {
						@Override
						public void onAdLoaded() {
							// TODO Auto-generated method stub
							super.onAdLoaded();
							if (mInterstitial.isLoaded()) {
								mInterstitial.show();
							}
						}

						public void onAdClosed() {
							objAllBean=arrayOfLatestVideo.get(position);
							Constant.VIDEO_IDD=objAllBean.getId();
							Intent intplay=new Intent(getActivity(),VideoPlay.class);
							startActivity(intplay);
						}

						@Override
						public void onAdFailedToLoad(int errorCode) {
							objAllBean=arrayOfLatestVideo.get(position);
							Constant.VIDEO_IDD=objAllBean.getId();
							Intent intplay=new Intent(getActivity(),VideoPlay.class);
							startActivity(intplay);
						}
					});
				} else {
					objAllBean=arrayOfLatestVideo.get(position);
					Constant.VIDEO_IDD=objAllBean.getId();
					Intent intplay=new Intent(getActivity(),VideoPlay.class);
					startActivity(intplay);
				}

			}
		});
		
		return rootView;
	}

	private	class MyTask extends AsyncTask<String, Void, String> {

 		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pbar.setVisibility(View.VISIBLE);
			lsv_latest.setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			pbar.setVisibility(View.INVISIBLE);
			lsv_latest.setVisibility(View.VISIBLE);

			if (null == result || result.length() == 0) {
				showToast(getString(R.string.no_data_found));
			}else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
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
						
						arrayOfLatestVideo.add(objItem);

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
				arrayOfLatestVideo,columnWidth);
		lsv_latest.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.search, menu);

		final SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();

		final MenuItem searchMenuItem = menu.findItem(R.id.search);
		searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					searchMenuItem.collapseActionView();
					searchView.setQuery("", false);
				}
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				String text = newText.toString().toLowerCase(Locale.getDefault());
				if(objAdapter!=null)
				{
					objAdapter.filter(text);
				}

				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
		});


	}
}


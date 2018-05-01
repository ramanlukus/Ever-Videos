package com.example.util;

import java.io.Serializable;

public class Constant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//server url
	public static final String SERVER_URL="http://www.viaviweb.in/envato/cc/all_in_one_videos_demo/";

	public static final String IMAGE_PATH_URL =SERVER_URL+"images/";

 	public static final String LATEST_URL =SERVER_URL+"api.php?latest";
	
 	public static final String CATEGORY_URL =SERVER_URL+"api.php?cat_list";
	
 	public static final String CATEGORY_ITEM_URL =SERVER_URL+"api.php?cat_id=";

 	public static final String ALL_VIDOE_URL =SERVER_URL+"api.php?all_videos";

 	public static final String HOME_VIDOE_URL =SERVER_URL+"api.php?home";

 	public static final String SINGLE_VIDEO_URL = SERVER_URL+"api.php?video_id=";

 	public static final String ABOUT_US_URL = SERVER_URL+"api.php";

	public static final String SLIDER_URL = SERVER_URL+"api.php?home_banner";

	public static final String YOUTUBE_IMAGE_FRONT="http://img.youtube.com/vi/";
	public static final String YOUTUBE_SMALL_IMAGE_BACK="/hqdefault.jpg";

	public static final String DAILYMOTION_IMAGE_PATH="http://www.dailymotion.com/thumbnail/video/";

	public static final String LATEST_ARRAY_NAME="ALL_IN_ONE_VIDEO";
	public static final String HOME_LATEST_ARRAY_NAME="latest_video";
	public static final String HOME_ALLVIDEO_ARRAY_NAME="all_video";
	public static final String LATEST_ID="id";
	public static final String LATEST_CATID="cat_id";
	public static final String LATEST_CAT_NAME="category_name";
	public static final String LATEST_VIDEO_URL="video_url";
	public static final String LATEST_VIDEO_ID="video_id";
	public static final String LATEST_VIDEO_DURATION="video_duration";
	public static final String LATEST_VIDEO_NAME="video_title";
	public static final String LATEST_VIDEO_DESCRIPTION="video_description";
	public static final String LATEST_IMAGE_URL="video_thumbnail_s";
	public static final String LATEST_IMAGE_URL_BIG="video_thumbnail_b";
	public static final String LATEST_VIDEOTYPE="video_type";

	public static final String CATEGORY_ARRAY_NAME="ALL_IN_ONE_VIDEO";
	public static final String CATEGORY_NAME="category_name";
	public static final String CATEGORY_CID="cid";
	public static final String CATEGORY_IMAGE="category_image_thumb";

	//for title display in CategoryItemF
	public static String CATEGORY_TITLE;
	public static int CATEGORY_ID;
	public static String VIDEO_IDD;

	public static final String CATEGORY_ITEM_ARRAY_NAME="ALL_IN_ONE_VIDEO";
	public static final String CATEGORY_ITEM_ID="id";
	public static final String CATEGORY_ITEM_CATID="cat_id";
	public static final String CATEGORY_ITEM_CAT_NAME="category_name";
	public static final String CATEGORY_ITEM_VIDEO_URL="video_url";
	public static final String CATEGORY_ITEM_VIDEO_ID="video_id";
	public static final String CATEGORY_ITEM_VIDEO_DURATION="video_duration";
	public static final String CATEGORY_ITEM_VIDEO_NAME="video_title";
	public static final String CATEGORY_ITEM_VIDEO_DESCRIPTION="video_description";
	public static final String CATEGORY_ITEM_IMAGE_URL="video_thumbnail";
	public static final String CATEGORY_ITEM_VIDEOTYPE="video_type";

	public static final String RELATED_ARRAY="related";
	public static final String RELATED_ID="id";
	public static final String RELATED_NAME="video_title";
	public static final String RELATED_TYPE="video_type";
	public static final String RELATED_CNAME="category_name";
	public static final String RELATED_PID="video_id";
	public static final String RELATED_IMG="video_thumbnail_s";
 	public static final String RELATED_TIME="video_duration";

	/*
	 * Slider
	 */

	public static final String SLIDER_ARRAY="ALL_IN_ONE_VIDEO";
	public static final String SLIDER_NAME="banner_name";
	public static final String SLIDER_IMAGE="banner_image";
	public static final String SLIDER_LINK="banner_url";

	public static final String APP_NAME="app_name";
	public static final String APP_IMAGE="app_logo";
	public static final String APP_VERSION="app_version";
	public static final String APP_AUTHOR="app_author";
	public static final String APP_CONTACT="app_contact";
	public static final String APP_EMAIL="app_email";
	public static final String APP_WEBSITE="app_website";
	public static final String APP_DESC="app_description";
	public static final String APP_PRIVACY="app_privacy_policy";
	public static final String APP_DEVELOP="app_developed_by";

	public static int AD_COUNT=0;
	public static int AD_COUNT_SHOW=5;
}

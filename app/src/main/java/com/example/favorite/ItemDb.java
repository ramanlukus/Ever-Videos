package com.example.favorite;

public class ItemDb {
	
	private String  vid;
	private String  videoId;
	private String VideoName;
 	private String Duration;
 	private String CategoryName;
 	private String ImageUrl;
	private String VideoType;

	public ItemDb(String vid, String vcatname, String vtype, String vplayid, String vtitle, String vthumbsmall, String vduration)
	{
		this.vid=vid;
		this.CategoryName=vcatname;
		this.VideoType=vtype;
		this.videoId=vplayid;
		this.VideoName=vtitle;
		this.ImageUrl=vthumbsmall;
		this.Duration=vduration;

	}
	public ItemDb()
	{
 	}
	public ItemDb(String vid) {
		// TODO Auto-generated constructor stub
		this.vid=vid;
	}
	public String getvid() {
		return vid;
	}
	public void setvid(String vid) {
		this.vid = vid;
	}

	public String getvideoId() {
		return videoId;
	}
	public void setvideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return VideoName;
	}
	public void setVideoName(String VideoName) {
		this.VideoName = VideoName;
	}

	public String getDuration() {
		return Duration;
	}
	public void setDuration(String Duration) {
		this.Duration = Duration;
	}

	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String CategoryName) {
		this.CategoryName = CategoryName;
	}

	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String ImageUrl) {
		this.ImageUrl = ImageUrl;
	}

	public String getVideoType() {
		return VideoType;
	}
 	public void setVideoType(String videotype) {
		this.VideoType = videotype;
	}

}

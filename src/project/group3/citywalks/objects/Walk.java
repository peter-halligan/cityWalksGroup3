package project.group3.citywalks.objects;

import java.util.ArrayList;

public class Walk {

	private int walkId;
	private String walkName;
	private ArrayList<POI> poiList = new ArrayList<POI>();
		
	public Walk(int walkId, String walkName, ArrayList<POI> poiList) {
		super();
		this.walkId = walkId;
		this.walkName = walkName;
		this.poiList = poiList;
	}
	public int getWalkId() {
		return walkId;
	}
	public void setWalkId(int walkId) {
		this.walkId = walkId;
	}
	public String getWalkName() {
		return walkName;
	}
	public void setWalkName(String walkName) {
		this.walkName = walkName;
	}
	public ArrayList<POI> getPoiList() {
		return poiList;
	}
	public void setPoiList(ArrayList<POI> poiList) {
		this.poiList = poiList;
	}
}

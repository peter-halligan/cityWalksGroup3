package project.group3.citywalks.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Walk  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7284347310569689823L;
	private int walkId;
	private int cityId;
	private String description;
	private String walkName;
	private ArrayList<POI> poiList = new ArrayList<POI>();
	private ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		
	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}
	public Walk() {
		super();
		this.walkId = 0;
		this.walkName = "";
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
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

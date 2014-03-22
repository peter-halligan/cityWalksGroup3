package project.group3.citywalks.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class City  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1851695322891039295L;
	
	private int cityId;
	private String cityName;
	private Coordinate midpoint;
	
	public City(int cityId, String cityName, Coordinate midpoint,
			ArrayList<Walk> walkList){
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.midpoint = midpoint;
		this.walkList = walkList;
	}
	
	private ArrayList<Walk> walkList = new ArrayList<Walk>();
	
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Coordinate getMidpoint() {
		return midpoint;
	}
	public void setMidpoint(Coordinate midpoint) {
		this.midpoint = midpoint;
	}
	public ArrayList<Walk> getWalkList() {
		return walkList;
	}
	public void setWalkList(ArrayList<Walk> walkList) {
		this.walkList = walkList;
	}
	
	
	
}

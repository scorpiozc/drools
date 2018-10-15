package com.sample.entity;

import java.util.Date;

public class ODTrade {

	private String inStation;
	private String outStation;
	private Date inStationTime;
	private Date outStationTime;

	public String getInStation() {
		return inStation;
	}

	public void setInStation(String inStation) {
		this.inStation = inStation;
	}

	public String getOutStation() {
		return outStation;
	}

	public void setOutStation(String outStation) {
		this.outStation = outStation;
	}

	public Date getInStationTime() {
		return inStationTime;
	}

	public void setInStationTime(Date inStationTime) {
		this.inStationTime = inStationTime;
	}

	public Date getOutStationTime() {
		return outStationTime;
	}

	public void setOutStationTime(Date outStationTime) {
		this.outStationTime = outStationTime;
	}

}

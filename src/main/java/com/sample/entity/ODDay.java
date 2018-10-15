package com.sample.entity;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class ODDay {

	private String inStationDay;
	private String outStationDay;

	public String getInStationDay() {
		return inStationDay;
	}

	public void setInStationDay(String inStationDay) {
		this.inStationDay = inStationDay;
	}

	public String getOutStationDay() {
		return outStationDay;
	}

	public void setOutStationDay(String outStationDay) {
		this.outStationDay = outStationDay;
	}

	public ODDay(Date inStationTime, Date outStationTime) {
		String pattern = "yyyyMMdd";
		this.inStationDay = DateFormatUtils.format(inStationTime, pattern);
		this.outStationDay = DateFormatUtils.format(outStationTime, pattern);
	}
}

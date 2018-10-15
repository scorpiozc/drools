package com.sample;

import java.text.DateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.kie.api.runtime.KieSession;

import com.sample.entity.ODTrade;

public class ODVerify {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static boolean incompleteOD(KieSession kSession, ODTrade trade) {
		if (trade.getInStationTime() != null && trade.getOutStationTime() != null) {
			if (StringUtils.isBlank(trade.getInStation()) || StringUtils.isBlank(trade.getOutStation())) {
				System.out.println("有进出站时间，但O站或D站编码缺失");
				return true;
			}
		}

		if (trade.getInStationTime() != null && StringUtils.isNotBlank(trade.getInStation())) {
			if (trade.getOutStationTime() == null && StringUtils.isBlank(trade.getOutStation())) {
				System.out.println("有O站的编码和时间，但D站的编码和时间缺失");
				return true;
			}
		}

		if (trade.getOutStationTime() != null && StringUtils.isNotBlank(trade.getOutStation())) {
			if (trade.getInStationTime() == null && StringUtils.isBlank(trade.getInStation())) {
				System.out.println("有D站的编码和时间，但O站的编码和时间缺失");
				return true;
			}
		}
		return false;
	}

	public static boolean odTimeLose(ODTrade trade) {
		if (StringUtils.isNotBlank(trade.getInStation()) && StringUtils.isNotBlank(trade.getOutStation())) {
			if (trade.getInStationTime() == null || trade.getOutStationTime() == null) {
				System.out.println("有进出站编码，但O站或D站时间缺失");
				return true;
			}
		}
		return false;
	}

	public static boolean differentDayOD(ODTrade trade) {
		String pattern = "yyyyMMdd";
		String inStationDay = DateFormatUtils.format(trade.getInStationTime(), pattern);
		String outStationDay = DateFormatUtils.format(trade.getOutStationTime(), pattern);
		if (StringUtils.contains(inStationDay, outStationDay)) {
			return true;
		}
		return false;
	}
}

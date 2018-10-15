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
				System.out.println("�н���վʱ�䣬��Oվ��Dվ����ȱʧ");
				return true;
			}
		}

		if (trade.getInStationTime() != null && StringUtils.isNotBlank(trade.getInStation())) {
			if (trade.getOutStationTime() == null && StringUtils.isBlank(trade.getOutStation())) {
				System.out.println("��Oվ�ı����ʱ�䣬��Dվ�ı����ʱ��ȱʧ");
				return true;
			}
		}

		if (trade.getOutStationTime() != null && StringUtils.isNotBlank(trade.getOutStation())) {
			if (trade.getInStationTime() == null && StringUtils.isBlank(trade.getInStation())) {
				System.out.println("��Dվ�ı����ʱ�䣬��Oվ�ı����ʱ��ȱʧ");
				return true;
			}
		}
		return false;
	}

	public static boolean odTimeLose(ODTrade trade) {
		if (StringUtils.isNotBlank(trade.getInStation()) && StringUtils.isNotBlank(trade.getOutStation())) {
			if (trade.getInStationTime() == null || trade.getOutStationTime() == null) {
				System.out.println("�н���վ���룬��Oվ��Dվʱ��ȱʧ");
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

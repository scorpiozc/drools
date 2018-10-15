package com.sample;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.entity.ODDay;
import com.sample.entity.ODTrade;

public class AACEngine {

	public static void main(String[] args) {
		ODTrade trade = init();
//		normal(trade);
		drools(trade);
	}

	private static ODTrade init() {
		ODTrade trade = new ODTrade();
		try {
			Date inStationTime = DateUtils.parseDate("2018-10-12 09:13:33", new String[] { "yyyy-MM-dd HH:mm:ss" });
			Date outStationTime = DateUtils.parseDate("2018-10-13 09:33:13", new String[] { "yyyy-MM-dd HH:mm:ss" });
			String inStation = "0115";
//			String inStation = "";
			String outStation = "0124";
//			String outStation = "";
			trade.setInStation(inStation);
			trade.setOutStation(outStation);
			trade.setInStationTime(inStationTime);
			trade.setOutStationTime(outStationTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return trade;
	}

	private static void normal(ODTrade trade) {
		System.out.println(trade.getInStation() + "#" + trade.getOutStation() + "#" + trade.getInStationTime() + "#"
				+ trade.getOutStationTime());
		// OD��Ϣ������
		if (ODVerify.incompleteOD(null, trade)) {
			// ͬվ��������
			System.out.println("ͬվ��������");
		} else if (ODVerify.odTimeLose(trade)) {
			// ������
			System.out.println("�����ʹ���");
		} else if (ODVerify.differentDayOD(trade)) {
			// ������
			System.out.println("�����ʹ���");
		} else if (true) {// ʵ��OD������ʱ������ƫ���ƫС
			// ������
			System.out.println("�����ʹ���");
		}
		//
	}

	private static void drools(ODTrade trade) {
		KieSession kSession = getKieSession();
		kSession.insert(trade);
		kSession.insert(new ODDay(trade.getInStationTime(), trade.getOutStationTime()));

		int count = kSession.fireAllRules();
		System.out.println("�ܹ�ִ����" + count + "������");
		kSession.dispose();
	}

	private static KieSession getKieSession() {
		// �ӹ����л��KieServicesʵ��
		KieServices kieServices = KieServices.Factory.get();
		// ��KieServices�л��KieContainerʵ����������kmodule.xml�ļ���load�����ļ�
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		// ����KieSession�������ļ���ͨ�Źܵ�
		KieSession kSession = kieContainer.newKieSession("ksession-rules");
		return kSession;
	}
}

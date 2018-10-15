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
		// OD信息不完整
		if (ODVerify.incompleteOD(null, trade)) {
			// 同站进出处理
			System.out.println("同站进出处理");
		} else if (ODVerify.odTimeLose(trade)) {
			// 分配型
			System.out.println("分配型处理");
		} else if (ODVerify.differentDayOD(trade)) {
			// 分配型
			System.out.println("分配型处理");
		} else if (true) {// 实际OD的旅行时间显著偏大或偏小
			// 分配型
			System.out.println("分配型处理");
		}
		//
	}

	private static void drools(ODTrade trade) {
		KieSession kSession = getKieSession();
		kSession.insert(trade);
		kSession.insert(new ODDay(trade.getInStationTime(), trade.getOutStationTime()));

		int count = kSession.fireAllRules();
		System.out.println("总共执行了" + count + "条规则");
		kSession.dispose();
	}

	private static KieSession getKieSession() {
		// 从工厂中获得KieServices实例
		KieServices kieServices = KieServices.Factory.get();
		// 从KieServices中获得KieContainer实例，其会加载kmodule.xml文件并load规则文件
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		// 建立KieSession到规则文件的通信管道
		KieSession kSession = kieContainer.newKieSession("ksession-rules");
		return kSession;
	}
}

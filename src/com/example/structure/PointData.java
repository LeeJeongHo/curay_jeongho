package com.example.structure;

import java.util.*;

/**
 * 그래프에 대한 데이터. 시간과 값을 저장.
 * 
 * @author leejeongho
 * @since 2014.09.10
 */
public class PointData {
	long longDateTime = 0;
	Calendar mDateTime = Calendar.getInstance();
	int value = 0;
	int medicineFlag = 0;
	
	public static int medicineFlagAfter = 10000;
	public static int medicineFlagBefore = 10001;
	public static int medicineFlagInsulin = 10002;
	public static int medicineFlagOutside = 10003;

	public PointData() {
	}

	public PointData(long longDateTime, int value) {
		super();
		this.value = value;
		this.longDateTime = longDateTime;
		setDateTime(longDateTime);
	}

	/**
	 * String 으로된 datetime 을 넣으면 값을 변환해서 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.10
	 * @param dateTime
	 *            format: 2014-09-10 12:31
	 */
	public void setDateTime(long dateTime) {
		mDateTime.setTimeInMillis(dateTime);
	}
	
	public void setMedicineFlag(int flag){
		this.medicineFlag = flag;
	}

	public int getMedicineFlag(){
		return this.medicineFlag;
	}
	public int getValue() {
		return this.value;
	}

	public Calendar getDateTime() {
		return this.mDateTime;
	}

	/**
	 * 시간에 대한 비교를 하기위해 시간,분을 합쳐서 int 타입으로 변환해서 반환.
	 * 
	 * @author leejeongho
	 * @since 2014.09.10
	 * @return int 타입으로 변환된 값.
	 */
	public int getTimeToInt() {
		return mDateTime.get(Calendar.HOUR_OF_DAY) * 60 + mDateTime.get(Calendar.MINUTE);
	}
	
	public int getHour(){
		return mDateTime.get(Calendar.HOUR_OF_DAY);
	}
}

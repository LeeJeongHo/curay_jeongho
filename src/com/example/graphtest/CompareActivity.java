package com.example.graphtest;

import java.util.*;

import com.example.module.*;
import com.example.structure.*;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class CompareActivity extends ActionBarActivity {

	RelativeLayout rlGraphRootFirst;
	RelativeLayout rlGraphRootSecond;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		
		rlGraphRootFirst = (RelativeLayout)findViewById(R.id.rl_graph_root_first);
		rlGraphRootSecond = (RelativeLayout) findViewById(R.id.rl_graph_root_second);
		
		ArrayList<PointData> arrDataFirst = setGlucoseData(60, 300, GraphLine.dateFlagWeek);
		ArrayList<PointData> arrDataSecond = setGlucoseData(60, 300, GraphLine.dateFlagWeek);
		GraphLine glucoseGraph
		= new GraphLine(getApplicationContext(), rlGraphRootFirst, GraphLine.graphFlagGlucose, GraphLine.dateFlagWeek, arrDataFirst, arrDataSecond);
		
		ArrayList<PointData> arrDataFood = setWorkoutOrFoodData(2000, GraphBar.dateFlagWeek);
		GraphBar foodGraph 
		= new GraphBar(getApplicationContext(),	rlGraphRootSecond, GraphBar.graphFlagFood, GraphBar.dateFlagWeek, arrDataFood);
	}

	
	private ArrayList<PointData> setGlucoseData(int min, int max, int maxX) {
		ArrayList<PointData> arrData = new ArrayList<PointData>();

		long dateToLong = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < maxX; i++) {
			if (((int) (Math.random() * maxX) % 2) == 0) {
				int value = (int) (min + Math.random() * (max - min));
				arrData.add(new PointData(dateToLong, value));
			} else {
				arrData.add(new PointData(dateToLong, 0));
			}
		}

		return arrData;
	}

	private ArrayList<PointData> setPressureData(int value, int maxX) {
		ArrayList<PointData> arrData = new ArrayList<PointData>();

		long dateToLong = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < maxX; i++) {
			arrData.add(new PointData(dateToLong, value));
		}

		return arrData;
	}

	private ArrayList<PointData> setWeightData(int goalValue, int maxX) {
		ArrayList<PointData> arrData = new ArrayList<PointData>();

		long dateToLong = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < maxX; i++) {
			if (((int) (Math.random() * maxX) % 2) == 0) {
				int value = (int) (goalValue - 10 + Math.random() * 20);
				arrData.add(new PointData(dateToLong, value));
			} else {
				arrData.add(new PointData(dateToLong, 0));
			}
		}

		return arrData;
	}

	private ArrayList<PointData> setWorkoutOrFoodData(int maxValue, int maxX) {
		ArrayList<PointData> arrData = new ArrayList<PointData>();

		long dateToLong = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < maxX; i++) {
			int value = (int) (Math.random() * maxValue);
			arrData.add(new PointData(dateToLong, value));
		}

		return arrData;
	}
	
	private ArrayList<PointData> setMedicineData(int maxX) {
		ArrayList<PointData> arrData = new ArrayList<PointData>();

		long dateToLong = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < maxX; i++) {
			int value = (int) (Math.random() * 2);
			PointData point = new PointData(dateToLong, value);
			int medicineFlag = (int)(Math.random()*4);
			point.setMedicineFlag(medicineFlag);
			arrData.add(point);
		}

		return arrData;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.example.graphtest;

import java.util.*;

import com.echo.holographlibrary.*;
import com.example.module.*;
import com.example.structure.*;

import android.support.v7.app.ActionBarActivity;
import android.content.*;
import android.graphics.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.webkit.*;
import android.widget.*;

public class MainActivity extends ActionBarActivity {

	RelativeLayout rlGraphRoot;
	GraphLine glucoseGraph;
	GraphLine pressureGraph;
	GraphLine weightGraph;
	GraphBar workoutGraph;
	GraphBar foodGraph;
	GraphBar medicineGraph;

	int graphFlag = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rlGraphRoot = (RelativeLayout) findViewById(R.id.rl_graph_root);

		Button btnGlucose = (Button) findViewById(R.id.btn_glucose_main);
		Button btnPressure = (Button) findViewById(R.id.btn_pressure_main);
		Button btnWeight = (Button) findViewById(R.id.btn_weight_main);
		Button btnWorkout = (Button) findViewById(R.id.btn_workout_main);
		Button btnFood = (Button) findViewById(R.id.btn_food_main);
		Button btnMedicine = (Button) findViewById(R.id.btn_medicine_main);

		btnGlucose.setOnClickListener(mGraph);
		btnPressure.setOnClickListener(mGraph);
		btnWeight.setOnClickListener(mGraph);
		btnWorkout.setOnClickListener(mGraph);
		btnFood.setOnClickListener(mGraph);
		btnMedicine.setOnClickListener(mGraph);

		Button btnDay = (Button) findViewById(R.id.btn_day);
		Button btnWeek = (Button) findViewById(R.id.btn_week);
		Button btnMonth = (Button) findViewById(R.id.btn_month);
		Button btnYear = (Button) findViewById(R.id.btn_year);

		btnDay.setOnClickListener(mDate);
		btnWeek.setOnClickListener(mDate);
		btnMonth.setOnClickListener(mDate);
		btnYear.setOnClickListener(mDate);
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
	
		

	OnClickListener mGraph = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			rlGraphRoot.removeAllViews();
			ArrayList<PointData> arrDataFirst = new ArrayList<PointData>();
			ArrayList<PointData> arrDataSecond = new ArrayList<PointData>();
			switch (v.getId()) {
			case R.id.btn_glucose_main:
				graphFlag = GraphLine.graphFlagGlucose;

				arrDataFirst = setGlucoseData(60, 300, GraphLine.dateFlagDay);
				arrDataSecond = setGlucoseData(60, 300, GraphLine.dateFlagDay);
				glucoseGraph 
				= new GraphLine(getApplicationContext(), rlGraphRoot, graphFlag, GraphLine.dateFlagDay, arrDataFirst, arrDataSecond);
				break;
			case R.id.btn_pressure_main:
				graphFlag = GraphLine.graphFlagPressure;

				arrDataFirst = setPressureData(150, GraphLine.dateFlagWeek);
				arrDataSecond = setPressureData(110, GraphLine.dateFlagWeek);
				pressureGraph 
				= new GraphLine(getApplicationContext(), rlGraphRoot, graphFlag, GraphLine.dateFlagWeek, arrDataFirst, arrDataSecond);
				break;
			case R.id.btn_weight_main:
				graphFlag = GraphLine.graphFlagWeight;

				arrDataFirst = setWeightData(70, GraphLine.dateFlagWeek);
				weightGraph 
				= new GraphLine(getApplicationContext(), rlGraphRoot, graphFlag, GraphLine.dateFlagWeek, arrDataFirst, null);
				break;
			case R.id.btn_workout_main:
				graphFlag = GraphBar.graphFlagWorkout;

				arrDataFirst = setWorkoutOrFoodData(2000, GraphBar.dateFlagWeek);
				workoutGraph 
				= new GraphBar(getApplicationContext(),	rlGraphRoot, graphFlag, GraphBar.dateFlagWeek, arrDataFirst);
				break;
			case R.id.btn_food_main:
				graphFlag = GraphBar.graphFlagFood;
				
				arrDataFirst = setWorkoutOrFoodData(2300, GraphBar.dateFlagWeek);
				foodGraph 
				= new GraphBar(getApplicationContext(), rlGraphRoot, graphFlag, GraphBar.dateFlagWeek, arrDataFirst);
				break;
			case R.id.btn_medicine_main:
				graphFlag = GraphBar.graphFlagMedicine;
				
				arrDataFirst = setMedicineData(GraphBar.dateFlagDay);
				medicineGraph 
				= new GraphBar(getApplicationContext(),	rlGraphRoot, graphFlag, GraphBar.dateFlagDay, arrDataFirst);
				break;
			default:
				break;
			}
		}
	};

	OnClickListener mDate = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ArrayList<PointData> arrDataFirst = new ArrayList<PointData>();
			ArrayList<PointData> arrDataSecond = new ArrayList<PointData>();
			switch (graphFlag) {
			case GraphLine.graphFlagGlucose:
				switch (v.getId()) {
				case R.id.btn_day:
					arrDataFirst = setGlucoseData(60, 300, GraphLine.dateFlagDay);
					arrDataSecond = setGlucoseData(60, 300,	GraphLine.dateFlagDay);
					glucoseGraph.setData(arrDataFirst, arrDataSecond);
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagDay);
					break;
				case R.id.btn_week:
					arrDataFirst = setGlucoseData(60, 300, GraphLine.dateFlagWeek);
					arrDataSecond = setGlucoseData(60, 300,	GraphLine.dateFlagWeek);
					glucoseGraph.setData(arrDataFirst, arrDataSecond);
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagWeek);
					break;
				case R.id.btn_month:
					arrDataFirst = setGlucoseData(60, 300, GraphLine.dateFlagMonth);
					arrDataSecond = setGlucoseData(60, 300,	GraphLine.dateFlagMonth);
					glucoseGraph.setData(arrDataFirst, arrDataSecond);
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagMonth);
					break;
				case R.id.btn_year:
					arrDataFirst = setGlucoseData(60, 300, GraphLine.dateFlagYear);
					arrDataSecond = setGlucoseData(60, 300,	GraphLine.dateFlagYear);
					glucoseGraph.setData(arrDataFirst, arrDataSecond);
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagYear);
					break;
				}
				break;
			case GraphLine.graphFlagPressure:
				switch (v.getId()) {
				case R.id.btn_day:
					arrDataFirst = setPressureData(150, GraphLine.dateFlagDay);
					arrDataSecond = setPressureData(110, GraphLine.dateFlagDay);
					pressureGraph.setData(arrDataFirst, arrDataSecond);
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagDay);
					break;
				case R.id.btn_week:
					arrDataFirst = setPressureData(150, GraphLine.dateFlagWeek);
					arrDataSecond = setPressureData(110, GraphLine.dateFlagWeek);
					pressureGraph.setData(arrDataFirst, arrDataSecond);
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagWeek);
					break;
				case R.id.btn_month:
					arrDataFirst = setPressureData(150, GraphLine.dateFlagMonth);
					arrDataSecond = setPressureData(110, GraphLine.dateFlagMonth);
					pressureGraph.setData(arrDataFirst, arrDataSecond);
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagMonth);
					break;
				case R.id.btn_year:
					arrDataFirst = setPressureData(150, GraphLine.dateFlagYear);
					arrDataSecond = setPressureData(110, GraphLine.dateFlagYear);
					pressureGraph.setData(arrDataFirst, arrDataSecond);
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagYear);
					break;
				}
				break;
			case GraphLine.graphFlagWeight:
				switch (v.getId()) {
				case R.id.btn_day:
					arrDataFirst = setWeightData(70, GraphLine.dateFlagDay);
					weightGraph.setData(arrDataFirst, null);
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagDay);
					break;
				case R.id.btn_week:
					arrDataFirst = setWeightData(70, GraphLine.dateFlagWeek);
					weightGraph.setData(arrDataFirst, null);
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagWeek);
					break;
				case R.id.btn_month:
					arrDataFirst = setWeightData(70, GraphLine.dateFlagMonth);
					weightGraph.setData(arrDataFirst, null);
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagMonth);
					break;
				case R.id.btn_year:
					arrDataFirst = setWeightData(70, GraphLine.dateFlagYear);
					weightGraph.setData(arrDataFirst, null);
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagYear);
					break;
				}
				break;
			case GraphBar.graphFlagWorkout:
				switch (v.getId()) {
				case R.id.btn_day:
					arrDataFirst = setWorkoutOrFoodData(2000, GraphBar.dateFlagDay);
					workoutGraph.setData(arrDataFirst);
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagDay);
					break;
				case R.id.btn_week:
					arrDataFirst = setWorkoutOrFoodData(2000, GraphBar.dateFlagWeek);
					workoutGraph.setData(arrDataFirst);
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagWeek);
					break;
				case R.id.btn_month:
					arrDataFirst = setWorkoutOrFoodData(2000, GraphBar.dateFlagMonth);
					workoutGraph.setData(arrDataFirst);
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagMonth);
					break;
				case R.id.btn_year:
					arrDataFirst = setWorkoutOrFoodData(2000, GraphBar.dateFlagYear);
					workoutGraph.setData(arrDataFirst);
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagYear);
					break;
				}
				break;
			case GraphBar.graphFlagFood:
				switch (v.getId()) {
				case R.id.btn_day:
					arrDataFirst = setWorkoutOrFoodData(2300, GraphBar.dateFlagDay);
					foodGraph.setData(arrDataFirst);
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagDay);
					break;
				case R.id.btn_week:
					arrDataFirst = setWorkoutOrFoodData(2300, GraphBar.dateFlagWeek);
					foodGraph.setData(arrDataFirst);
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagWeek);
					break;
				case R.id.btn_month:
					arrDataFirst = setWorkoutOrFoodData(2300, GraphBar.dateFlagMonth);
					foodGraph.setData(arrDataFirst);
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagMonth);
					break;
				case R.id.btn_year:
					arrDataFirst = setWorkoutOrFoodData(2300, GraphBar.dateFlagYear);
					foodGraph.setData(arrDataFirst);
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagYear);
					break;
				}
				break;
			case GraphBar.graphFlagMedicine:
				switch (v.getId()) {
				case R.id.btn_day:
					arrDataFirst = setMedicineData(GraphBar.dateFlagDay);
					medicineGraph.setData(arrDataFirst);
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagDay);
					break;
				case R.id.btn_week:
					arrDataFirst = setMedicineData(GraphBar.dateFlagWeek);
					medicineGraph.setData(arrDataFirst);
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagWeek);
					break;
				case R.id.btn_month:
					arrDataFirst = setMedicineData(GraphBar.dateFlagMonth);
					medicineGraph.setData(arrDataFirst);
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagMonth);
					break;
				case R.id.btn_year:
					arrDataFirst = setMedicineData(GraphBar.dateFlagYear);
					medicineGraph.setData(arrDataFirst);
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagYear);
					break;
				}
				break;
			}

		}
	};
}

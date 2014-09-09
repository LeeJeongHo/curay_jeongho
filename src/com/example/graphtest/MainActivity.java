package com.example.graphtest;

import com.example.module.*;

import android.support.v7.app.ActionBarActivity;
import android.content.*;
import android.os.Bundle;
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
		
		rlGraphRoot = (RelativeLayout)findViewById(R.id.rl_graph_root);
							
	    Button btnGlucose = (Button)findViewById(R.id.btn_glucose_main);
	    Button btnPressure = (Button)findViewById(R.id.btn_pressure_main);
	    Button btnWeight = (Button)findViewById(R.id.btn_weight_main);
	    Button btnWorkout = (Button)findViewById(R.id.btn_workout_main);
	    Button btnFood = (Button)findViewById(R.id.btn_food_main);
	    Button btnMedicine = (Button)findViewById(R.id.btn_medicine_main);
	    
		btnGlucose.setOnClickListener(mGraph);
		btnPressure.setOnClickListener(mGraph);
		btnWeight.setOnClickListener(mGraph);
		btnWorkout.setOnClickListener(mGraph);
		btnFood.setOnClickListener(mGraph);
		btnMedicine.setOnClickListener(mGraph);
		
		Button btnDay = (Button)findViewById(R.id.btn_day);
		Button btnWeek = (Button)findViewById(R.id.btn_week);
		Button btnMonth = (Button)findViewById(R.id.btn_month);
		Button btnYear = (Button)findViewById(R.id.btn_year);
		
		btnDay.setOnClickListener(mDate);
		btnWeek.setOnClickListener(mDate);
		btnMonth.setOnClickListener(mDate);
		btnYear.setOnClickListener(mDate);
	}
	
	OnClickListener mGraph = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			rlGraphRoot.removeAllViews();
			switch (v.getId()) {
			case R.id.btn_glucose_main:
				graphFlag = GraphLine.graphFlagGlucose;
				glucoseGraph = new GraphLine(getApplicationContext(), rlGraphRoot, graphFlag, GraphLine.dateFlagDay);
				break;
			case R.id.btn_pressure_main:
				graphFlag = GraphLine.graphFlagPressure;
				pressureGraph = new GraphLine(getApplicationContext(), rlGraphRoot, graphFlag, GraphLine.dateFlagWeek);
				break;
			case R.id.btn_weight_main:
				graphFlag = GraphLine.graphFlagWeight;
				weightGraph = new GraphLine(getApplicationContext(), rlGraphRoot, graphFlag, GraphLine.dateFlagWeek);
				break;
			case R.id.btn_workout_main:
				graphFlag = GraphBar.graphFlagWorkout;
				workoutGraph = new GraphBar(getApplicationContext(), rlGraphRoot, graphFlag, GraphBar.dateFlagWeek);
				break;
			case R.id.btn_food_main:
				graphFlag = GraphBar.graphFlagFood;
				foodGraph = new GraphBar(getApplicationContext(), rlGraphRoot, graphFlag, GraphBar.dateFlagWeek);
				break;
			case R.id.btn_medicine_main:
				graphFlag = GraphBar.graphFlagMedicine;
				medicineGraph = new GraphBar(getApplicationContext(), rlGraphRoot, graphFlag, GraphBar.dateFlagDay);
				break;
			default:
				break;
			}  	
		}
	};
	
	OnClickListener mDate = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(graphFlag){
			case GraphLine.graphFlagGlucose:
				switch(v.getId()){
				case R.id.btn_day:
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagDay);
					break;
				case R.id.btn_week:
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagWeek);
					break;
				case R.id.btn_month:
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagMonth);
					break;
				case R.id.btn_year:
					glucoseGraph.initGraph(graphFlag, GraphLine.dateFlagYear);
					break;
				}	
				break;
			case GraphLine.graphFlagPressure:
				switch(v.getId()){
				case R.id.btn_day:
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagDay);
					break;
				case R.id.btn_week:
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagWeek);
					break;
				case R.id.btn_month:
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagMonth);
					break;
				case R.id.btn_year:
					pressureGraph.initGraph(graphFlag, GraphLine.dateFlagYear);
					break;
				}	
				break;
			case GraphLine.graphFlagWeight:
				switch(v.getId()){
				case R.id.btn_day:
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagDay);
					break;
				case R.id.btn_week:
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagWeek);
					break;
				case R.id.btn_month:
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagMonth);
					break;
				case R.id.btn_year:
					weightGraph.initGraph(graphFlag, GraphLine.dateFlagYear);
					break;
				}	
				break;
			case GraphBar.graphFlagWorkout:
				switch(v.getId()){
				case R.id.btn_day:
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagDay);
					break;
				case R.id.btn_week:
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagWeek);
					break;
				case R.id.btn_month:
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagMonth);
					break;
				case R.id.btn_year:
					workoutGraph.initGraph(graphFlag, GraphBar.dateFlagYear);
					break;
				}
				break;
			case GraphBar.graphFlagFood:
				switch(v.getId()){
				case R.id.btn_day:
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagDay);
					break;
				case R.id.btn_week:
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagWeek);
					break;
				case R.id.btn_month:
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagMonth);
					break;
				case R.id.btn_year:
					foodGraph.initGraph(graphFlag, GraphBar.dateFlagYear);
					break;
				}
				break;
			case GraphBar.graphFlagMedicine:
				switch(v.getId()){
				case R.id.btn_day:
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagDay);
					break;
				case R.id.btn_week:
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagWeek);
					break;
				case R.id.btn_month:
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagMonth);
					break;
				case R.id.btn_year:
					medicineGraph.initGraph(graphFlag, GraphBar.dateFlagYear);
					break;
				}
				break;
			}
			
		}
	};
}

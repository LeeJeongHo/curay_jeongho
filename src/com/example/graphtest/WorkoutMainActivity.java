package com.example.graphtest;

import java.util.*;

import com.echo.holographlibrary.*;
import com.echo.holographlibrary.BarGraph.*;

import android.support.v7.app.ActionBarActivity;
import android.text.format.*;
import android.graphics.*;
import android.os.Bundle;
import android.util.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.widget.RelativeLayout.*;

public class WorkoutMainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_main);
		
		initGraph();
	}
	
	/**
	 * Graph를 그리기 위해 값과 Graph에 대한 값을 설정하고 각 포인트에 대한 ClickEvent역시 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 */
	private void initGraph(){
		int maxValue = 2000;
		int minValue = 0;
		int goalValue = 1200;
		
		Date today = new Date();
		TextView tvDate = (TextView)findViewById(R.id.tv_date);
		tvDate.setText(DateFormat.format("MMMM d, yyyy ", today.getTime()));
		
		TextView tvMaxY = (TextView)findViewById(R.id.tv_max_y);
		TextView tvHalfY = (TextView)findViewById(R.id.tv_half_y);
		TextView tvMinY = (TextView)findViewById(R.id.tv_min_y);
		tvMaxY.setText(""+maxValue);
		tvHalfY.setText(""+(((maxValue-minValue)/2)+minValue));
		tvMinY.setText(""+minValue);
		
		String colorOver = "#d6d138";
		String colorDefault = "#5aaecc";
		final ArrayList<Bar> points = new ArrayList<Bar>();
		Bar d1 = new Bar();
		d1.setColor(Color.parseColor(colorOver));
		d1.setName("1");
		d1.setValue(maxValue);
		points.add(d1);
		
		for(int i=2;i<=24;i++){
			Bar d = new Bar();
			float value = (float)(Math.random()*maxValue);
			if(value >= goalValue){
				d.setColor(Color.parseColor(colorOver));
			}
			else{
				d.setColor(Color.parseColor(colorDefault));
			}
			d.setName(""+i);
			d.setValue(value);
			points.add(d);
		}

        BarGraph g = (BarGraph)findViewById(R.id.bargraph);
        
        g.setMaxValue(maxValue);
		g.setBars(points);
		
		g.setOnBarClickedListener(new OnBarClickedListener(){

			@Override
			public void onClick(int index) {
				Toast.makeText(getApplicationContext(), "index = "+(index+1)+", value = "+((int)points.get(index).getValue()), Toast.LENGTH_SHORT).show();
			}
			
		});
		
		setGoal(minValue, maxValue, goalValue);
	}
	
	/**
	 * Graph에 목표수치 표시.
	 * 
	 * @param minY Y 범위의 최소값.
	 * @param maxY Y 범위의 최대값.
	 * @param value 목표수치 값.
	 */
	private void setGoal(int minY, int maxY, int value){
		/** 목표 표시를 위한 별도의 View */
		RelativeLayout rlGoal = (RelativeLayout)findViewById(R.id.ll_goal);

		int px_height = toPix(150);
		float per_height = (float)px_height / (maxY - minY);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, toPix(15));
		rlParams.setMargins(toPix(40), (int)(toPix(56-15)+((maxY-value)*per_height)), toPix(20), 0);
		rlGoal.setLayoutParams(rlParams);
		TextView tvGoal = (TextView)findViewById(R.id.tv_goal);
		tvGoal.setText(value+" kcal");
	}
	
	/**
	 * DIP 수치를 Pixel 수치로 변환.
	 * 동적으로 View를 그려넣을때 필요.
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param value 변환할 DIP 수치.
	 * @return 변환된 Pixel 수치.
	 */
	private int toPix(int value){
		
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getApplicationContext().getResources().getDisplayMetrics());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout_main, menu);
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

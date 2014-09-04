package com.example.graphtest;

import java.util.*;

import com.echo.holographlibrary.*;
import com.echo.holographlibrary.BarGraph.*;

import android.support.v7.app.ActionBarActivity;
import android.text.format.*;
import android.graphics.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;

public class WorkoutMainActivity extends ActionBarActivity {
	/** 포인트를 클릭했을때 Tooltip 표시를 위한 영역 */
	RelativeLayout rlTooltip;
	
	/** graph 의 x축 범위를 지정하기 위한 flag */
	final static int dateFlagDay = 24;
	final static int dateFlagWeek = 7;
	final static int dateFlagMonth = 28;
	final static int dateFlagYear = 12;
	
	/** graph 의 색상 값. */
	final static String colorOver = "#d6d138";
	final static String colorDefault = "#5aaecc";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_main);
		rlTooltip = (RelativeLayout) findViewById(R.id.rl_tooltip);
		initGraph(dateFlagWeek);
	}

	/**
	 * Graph를 그리기 위해 값과 Graph에 대한 값을 설정하고 각 포인트에 대한 ClickEvent역시 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 */
	private void initGraph(int dateFlag) {
		int maxValue = 2000;
		int minValue = 0;
		int goalValue = 1200;

		TextView tvMaxY = (TextView) findViewById(R.id.tv_max_y);
		TextView tvHalfY = (TextView) findViewById(R.id.tv_half_y);
		TextView tvMinY = (TextView) findViewById(R.id.tv_min_y);
		tvMaxY.setText("" + maxValue);
		tvHalfY.setText("" + (((maxValue - minValue) / 2) + minValue));
		tvMinY.setText("" + minValue);

		final ArrayList<Bar> points = new ArrayList<Bar>();
		Bar d1 = new Bar();
		d1.setColor(Color.parseColor(colorOver));
		d1.setName("1");
		d1.setValue(maxValue);
		points.add(d1);

		for (int i = 2; i <= dateFlag; i++) {
			Bar d = new Bar();
			float value = (float) (Math.random() * maxValue);
			if (value >= goalValue) {
				d.setColor(Color.parseColor(colorOver));
			} else {
				d.setColor(Color.parseColor(colorDefault));
			}
			d.setName("" + i);
			d.setValue(value);
			points.add(d);
		}

		BarGraph g = (BarGraph) findViewById(R.id.bargraph);

		g.setMaxValue(maxValue);
		g.setBars(points);

		g.setOnBarClickedListener(new OnBarClickedListener() {

			@Override
			public void onClick(int index) {
			    Coordinates coordinates = points.get(index).getCoordinates();
			    addTooltip(coordinates);
			}

		});

		setGoal(minValue, maxValue, goalValue);
		setXLabel(dateFlag);
	}

	/**
	 * x축의 label 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @param flag x축에 대한 단위. Day: 24, Week: 7, Month: 28, Year: 12
	 */
	private void setXLabel(int flag) {
		Calendar mDate = Calendar.getInstance();
		TextView tvDate = (TextView) findViewById(R.id.tv_date);
		switch (flag) {
		case dateFlagDay:
			tvDate.setText(DateFormat.format("MM월 dd일", mDate.getTime()));
			RelativeLayout rlXLabelDay = (RelativeLayout) findViewById(R.id.rl_graph_x_label_day);
			rlXLabelDay.setVisibility(View.VISIBLE);
			break;
		case dateFlagWeek:
			String dateEnd = (String)DateFormat.format("MM월 dd일", mDate.getTime());
			mDate.add(Calendar.DAY_OF_MONTH, -6);
			String dateStart = (String)DateFormat.format("MM월 dd일 - ", mDate.getTime());
			tvDate.setText(dateStart+dateEnd);
			
			LinearLayout llXLabelWeek = (LinearLayout) findViewById(R.id.ll_graph_x_label_week);
			llXLabelWeek.setVisibility(View.VISIBLE);
			ArrayList<TextView> arrLabels = new ArrayList<TextView>();
			for (int i = 0; i < 7; i++) {
				arrLabels.add((TextView) llXLabelWeek.getChildAt(i));
				arrLabels.get(i).setText(mDate.get(Calendar.DAY_OF_MONTH) + "");
				mDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			break;
		}
	}

	/**
	 * Graph에 목표수치 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @param minY
	 *            Y 범위의 최소값.
	 * @param maxY
	 *            Y 범위의 최대값.
	 * @param value
	 *            목표수치 값.
	 */
	private void setGoal(int minY, int maxY, int value) {
		/** 목표 표시를 위한 별도의 View */
		RelativeLayout rlGoal = (RelativeLayout) findViewById(R.id.rl_goal);

		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, toPix(15));
		rlParams.setMargins(toPix(40),
				(int) (toPix(50 - 15) + ((maxY - value) * per_height)),
				toPix(20), 0);
		rlGoal.setLayoutParams(rlParams);
		TextView tvGoal = (TextView) findViewById(R.id.tv_goal);
		tvGoal.setText(value + " kcal");
	}

	/**
	 * 포인트를 클릭했을때 값을 표시. ** 라이브러리에서 포인트를 그려넣을때의 Pixel좌표를 그대로 사용하므로 dip 변환을 안해도됨.
	 * **
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @param coordinates
	 *            {@link Coordinates}
	 */
	private void addTooltip(Coordinates coordinates) {
		rlTooltip.removeAllViews();

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlParams.setMargins((int) coordinates.getX(),
				(int) coordinates.getY(), 0, 0);
		TextView tvTooltip = new TextView(getApplicationContext());
		tvTooltip.setLayoutParams(rlParams);
		tvTooltip.setText("" + ((int)coordinates.getValue()));
		tvTooltip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		tvTooltip.setTextColor(Color.WHITE);
		tvTooltip.setBackgroundColor(Color.GRAY);
		tvTooltip.setGravity(Gravity.CENTER);
		rlTooltip.addView(tvTooltip);
	}

	/**
	 * DIP 수치를 Pixel 수치로 변환. 동적으로 View를 그려넣을때 필요.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @param value
	 *            변환할 DIP 수치.
	 * @return 변환된 Pixel 수치.
	 */
	private int toPix(int value) {

		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, getApplicationContext().getResources()
						.getDisplayMetrics());
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

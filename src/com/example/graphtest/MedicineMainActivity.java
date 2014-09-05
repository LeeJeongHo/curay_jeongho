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
import android.view.View.*;
import android.widget.*;
import android.widget.RelativeLayout.*;

public class MedicineMainActivity extends ActionBarActivity {
	/** 포인트를 클릭했을때 Tooltip 표시를 위한 영역 */
	RelativeLayout rlTooltip;

	/** graph 의 x축 범위를 지정하기 위한 flag */
	final static int dateFlagDay = 24;
	final static int dateFlagWeek = 7;
	final static int dateFlagMonth = 28;
	final static int dateFlagYear = 12;

	/** graph 의 색상 값. */
	final static String colorBefore = "#5aaccc";
	final static String colorAfter = "#ff8c9c";
	final static String colorInsulin = "#f5a700";
	final static String colorOutside = "#61bf52";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicine_main);

		rlTooltip = (RelativeLayout) findViewById(R.id.rl_tooltip);
		initGraph(dateFlagDay);

		Button btnDay = (Button) findViewById(R.id.btn_day);
		Button btnWeek = (Button) findViewById(R.id.btn_week);
		Button btnMonth = (Button) findViewById(R.id.btn_month);
		Button btnYear = (Button) findViewById(R.id.btn_year);
		btnDay.setOnClickListener(mClick);
		btnWeek.setOnClickListener(mClick);
		btnMonth.setOnClickListener(mClick);
		btnYear.setOnClickListener(mClick);
	}

	OnClickListener mClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			rlTooltip.removeAllViews();
			switch (v.getId()) {
			case R.id.btn_day:
				initGraph(dateFlagDay);
				break;
			case R.id.btn_week:
				initGraph(dateFlagWeek);
				break;
			case R.id.btn_month:
				initGraph(dateFlagMonth);
				break;
			case R.id.btn_year:
				initGraph(dateFlagYear);
				break;
			}
		}
	};

	/**
	 * Graph를 그리기 위해 값과 Graph에 대한 값을 설정하고 각 포인트에 대한 ClickEvent역시 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.05
	 */
	private void initGraph(int dateFlag) {
		int maxValue = 2;
		int minValue = 0;

		TextView tvMaxY = (TextView) findViewById(R.id.tv_max_y);
		TextView tvHalfY = (TextView) findViewById(R.id.tv_half_y);
		TextView tvMinY = (TextView) findViewById(R.id.tv_min_y);
		tvMaxY.setText("" + maxValue);
		tvHalfY.setText("" + (((maxValue - minValue) / 2) + minValue));
		tvMinY.setText("" + minValue);

		final ArrayList<Bar> points = new ArrayList<Bar>();

		for (int i = 1; i <= dateFlag; i++) {
			Bar d = new Bar();
			int value = (int) (Math.random() * maxValue);
			int colorFlag = (int)(Math.random()*4);
			switch (colorFlag) {
			case 0:
				d.setColor(Color.parseColor(colorAfter));
				break;
			case 1:
				d.setColor(Color.parseColor(colorBefore));
				break;
			case 2:
				d.setColor(Color.parseColor(colorInsulin));
				break;
			case 3:
				d.setColor(Color.parseColor(colorOutside));
				break;
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

		setXLabel(dateFlag);
		g.update();
	}

	/**
	 * x축의 label 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.05
	 * @param flag
	 *            x축에 대한 단위. Day: 24, Week: 7, Month: 28, Year: 12
	 */
	private void setXLabel(int flag) {
		Calendar mDate = Calendar.getInstance();
		TextView tvDate = (TextView) findViewById(R.id.tv_date);
		RelativeLayout rlXLabelDay = (RelativeLayout) findViewById(R.id.rl_graph_x_label_day);
		LinearLayout llXLabelWeek = (LinearLayout) findViewById(R.id.ll_graph_x_label_week);
		LinearLayout llXLabelMonth = (LinearLayout) findViewById(R.id.ll_graph_x_label_month);
		LinearLayout llXLabelYear = (LinearLayout) findViewById(R.id.ll_graph_x_label_year);
		switch (flag) {
		case dateFlagDay:
			tvDate.setText(DateFormat.format("MM월 dd일", mDate.getTime()));
			rlXLabelDay.setVisibility(View.VISIBLE);
			llXLabelWeek.setVisibility(View.INVISIBLE);
			llXLabelMonth.setVisibility(View.INVISIBLE);
			llXLabelYear.setVisibility(View.INVISIBLE);
			break;
		case dateFlagWeek:
			String dateEndWeek = (String) DateFormat.format("MM월 dd일",
					mDate.getTime());
			mDate.add(Calendar.DAY_OF_MONTH, -6);
			String dateStartWeek = (String) DateFormat.format("MM월 dd일 - ",
					mDate.getTime());
			tvDate.setText(dateStartWeek + dateEndWeek);

			rlXLabelDay.setVisibility(View.INVISIBLE);
			llXLabelWeek.setVisibility(View.VISIBLE);
			llXLabelMonth.setVisibility(View.INVISIBLE);
			llXLabelYear.setVisibility(View.INVISIBLE);

			ArrayList<TextView> arrLabelWeek = new ArrayList<TextView>();
			for (int i = 0; i < 7; i++) {
				arrLabelWeek.add((TextView) llXLabelWeek.getChildAt(i));
				arrLabelWeek.get(i).setText(
						mDate.get(Calendar.DAY_OF_MONTH) + "");
				mDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			break;
		case dateFlagMonth:
			String dateEndMonth = (String) DateFormat.format("MM월 dd일",
					mDate.getTime());
			mDate.add(Calendar.DAY_OF_MONTH, -27);
			String dateStartMonth = (String) DateFormat.format("MM월 dd일 - ",
					mDate.getTime());
			tvDate.setText(dateStartMonth + dateEndMonth);

			rlXLabelDay.setVisibility(View.INVISIBLE);
			llXLabelWeek.setVisibility(View.INVISIBLE);
			llXLabelMonth.setVisibility(View.VISIBLE);
			llXLabelYear.setVisibility(View.INVISIBLE);
			ArrayList<TextView> arrLabelMonth = new ArrayList<TextView>();
			for (int i = 0; i < 9; i++) {
				arrLabelMonth.add((TextView) llXLabelMonth.getChildAt(i));
				if (i==0){
					arrLabelMonth.get(i).setText(mDate.get(Calendar.DAY_OF_MONTH) + "");
					mDate.add(Calendar.DAY_OF_MONTH, 6);
				}
				else if (i % 2 == 0) {
					arrLabelMonth.get(i).setText(mDate.get(Calendar.DAY_OF_MONTH) + "");
					mDate.add(Calendar.DAY_OF_MONTH, 7);
				}
			}
			break;
		case dateFlagYear:
			tvDate.setText(mDate.get(Calendar.YEAR) + "년");
			rlXLabelDay.setVisibility(View.INVISIBLE);
			llXLabelWeek.setVisibility(View.INVISIBLE);
			llXLabelMonth.setVisibility(View.INVISIBLE);
			llXLabelYear.setVisibility(View.VISIBLE);
			mDate.add(Calendar.MONTH, -11);
			ArrayList<TextView> arrLabelYear = new ArrayList<TextView>();
			for (int i = 0; i < 12; i++) {
				arrLabelYear.add((TextView) llXLabelYear.getChildAt(i));
				arrLabelYear.get(i).setText(
						(mDate.get(Calendar.MONTH) + 1) + "");
				mDate.add(Calendar.MONTH, 1);
			}
			break;
		}
	}

	/**
	 * 포인트를 클릭했을때 값을 표시. ** 라이브러리에서 포인트를 그려넣을때의 Pixel좌표를 그대로 사용하므로 dip 변환을 안해도됨.
	 * **
	 * 
	 * @author leejeongho
	 * @since 2014.09.05
	 * @param coordinates
	 *            {@link Coordinates}
	 */
	private void addTooltip(Coordinates coordinates) {
		rlTooltip.removeAllViews();

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlParams.setMargins((int) coordinates.getX(), (int) coordinates.getY(),
				0, 0);
		TextView tvTooltip = new TextView(getApplicationContext());
		tvTooltip.setLayoutParams(rlParams);
		tvTooltip.setText("" + ((int) coordinates.getValue()));
		tvTooltip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		tvTooltip.setTextColor(Color.WHITE);
		tvTooltip.setBackgroundColor(Color.GRAY);
		tvTooltip.setPadding(10, 0, 10, 0);
		rlTooltip.addView(tvTooltip);
	}

	/**
	 * DIP 수치를 Pixel 수치로 변환. 동적으로 View를 그려넣을때 필요.
	 * 
	 * @author leejeongho
	 * @since 2014.09.05
	 * @param value
	 *            변환할 DIP 수치.
	 * @return 변환된 Pixel 수치.
	 */
	private int toPix(int value) {

		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, getApplicationContext().getResources()
						.getDisplayMetrics());
	}

}

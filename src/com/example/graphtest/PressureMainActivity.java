package com.example.graphtest;

import java.util.*;

import com.echo.holographlibrary.*;
import com.echo.holographlibrary.LineGraph.*;

import android.support.v7.app.ActionBarActivity;
import android.text.format.*;
import android.graphics.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RelativeLayout.*;

public class PressureMainActivity extends ActionBarActivity {
	/** Graph 를 그리는 Canvas 영역 */
	LineGraph li;
	/** 수축기 정상범위 표시를 위한 ImageView */
	ImageView ivNormalContract;
	/** 이완기 정상범위 표시를 위한 ImageView */
	ImageView ivNormalRelax;
	/** 포인트를 클릭했을때 Tooltip 표시를 위한 영역 */
	RelativeLayout rlTooltip;

	/** graph 의 x축 범위를 지정하기 위한 flag */
	final static int dateFlagDay = 24 * 60;
	final static int dateFlagWeek = 7;
	final static int dateFlagMonth = 28;
	final static int dateFlagYear = 12;

	/** graph 의 색상 값. */
	final static String colorContract = "#5aaccc";
	final static String colorRelax = "#02619c";

	// final static String colorRelax = "#ff0000";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pressure_main);

		ivNormalContract = (ImageView) findViewById(R.id.iv_normal_contract);
		ivNormalRelax = (ImageView) findViewById(R.id.iv_normal_relax);
		rlTooltip = (RelativeLayout) findViewById(R.id.rl_tooltip);
		initGraph(dateFlagWeek);

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
	 * **주, 월 , 년 일때는 x축의 시작과 끝값을 사용안함.
	 * 
	 * @author leejeongho
	 * @since 2014.09.02
	 */
	private void initGraph(int dateFlag) {

		int maxY = 180;
		int minY = 80;

		TextView tvMaxY = (TextView) findViewById(R.id.tv_max_y);
		TextView tvHalfY = (TextView) findViewById(R.id.tv_half_y);
		TextView tvMinY = (TextView) findViewById(R.id.tv_min_y);
		tvMaxY.setText("" + ((int) maxY));
		tvHalfY.setText("" + ((int) (((maxY - minY) / 2) + minY)));
		tvMinY.setText("" + ((int) minY));

		li = (LineGraph) findViewById(R.id.linegraph);
		li.removeAllLines();
		li.setRangeY(minY, maxY);

		int maxX = dateFlag;
		int maxI = maxX;
		int increaseValue = 1;

		switch (dateFlag) {
		case dateFlagDay:
			increaseValue = 60;
			li.setMaxX(maxX);
			break;
		case dateFlagWeek:
			maxI = maxX + 1;
			li.setMaxX(maxX + 1);
			break;
		default:
			li.setMaxX(maxX+1);
			break;
		}

		ArrayList<Line> arrLine = new ArrayList<Line>();
		int index = 0;
		for (int i = 1; i <= maxI; i += increaseValue) {
			if (!(dateFlag == dateFlagWeek && maxI == i)) {
				arrLine.add(new Line());
				LinePoint p = new LinePoint();
				p.setX(i);
				p.setY(150);
				arrLine.get(index).addPoint(p);
				p = new LinePoint();
				p.setX(i);
				p.setY(110);
				arrLine.get(index).addPoint(p);
				arrLine.get(index).setColor(Color.parseColor(colorContract));
				arrLine.get(index).setIsPressure(true);
				arrLine.get(index).setColorPressure(
						Color.parseColor(colorRelax));

				li.addLine(arrLine.get(index));
				index++;
			}
		}

		li.setOnPointClickedListener(new OnPointClickedListener() {

			@Override
			public void onClick(int lineIndex, int pointIndex) {
				Coordinates coordinates = li.getLine(lineIndex).getCoordinates(
						pointIndex);
				addTooltip(coordinates);
			}
		});

		setNormalContractRange(minY, maxY, 120, 150);
		setNormalRelaxRange(minY, maxY, 90, 110);
		setXLabel(dateFlag);
	}

	/**
	 * Graph에 수축기 정상범위 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @param minY
	 *            Y 범위의 최소값.
	 * @param maxY
	 *            Y 범위의 최대값.
	 * @param minNormalContract
	 *            정상범위의 최소값.
	 * @param maxNormalContract
	 *            정상범위의 최대값.
	 */
	private void setNormalContractRange(int minY, int maxY,
			int minNormalContract, int maxNormalContract) {
		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		float iv_normal_height = per_height
				* (maxNormalContract - minNormalContract);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) iv_normal_height);
		rlParams.setMargins(toPix(34),
				(int) (toPix(50) + ((maxY - maxNormalContract) * per_height)),
				toPix(20), 0);
		ivNormalContract.setLayoutParams(rlParams);
	}

	/**
	 * Graph에 이완기 정상범위 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @param minY
	 *            Y 범위의 최소값.
	 * @param maxY
	 *            Y 범위의 최대값.
	 * @param minNormalRelax
	 *            정상범위의 최소값.
	 * @param maxNormalRelax
	 *            정상범위의 최대값.
	 */
	private void setNormalRelaxRange(int minY, int maxY, int minNormalRelax,
			int maxNormalRelax) {
		if (minY > minNormalRelax) {
			minNormalRelax = minY;
		}
		if (maxY < maxNormalRelax) {
			maxNormalRelax = maxY;
		}
		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		float iv_adnormal_height = per_height
				* (maxNormalRelax - minNormalRelax);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) iv_adnormal_height);
		rlParams.setMargins(toPix(34),
				(int) (toPix(50) + ((maxY - maxNormalRelax) * per_height)),
				toPix(20), 0);
		ivNormalRelax.setLayoutParams(rlParams);
	}

	/**
	 * x축의 label 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
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
	 * @since 2014.09.04
	 * @param coordinates
	 *            {@link Coordinates}
	 */
	private void addTooltip(Coordinates coordinates) {
		rlTooltip.removeAllViews();

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlParams.setMargins((int) coordinates.getX() + 10,
				(int) coordinates.getY() + 10, 0, 0);
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
		getMenuInflater().inflate(R.menu.pressure_main, menu);
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

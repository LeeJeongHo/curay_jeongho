package com.example.module;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.text.format.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;

import com.example.graphtest.R;
import com.example.structure.*;

import com.echo.holographlibrary.*;
import com.echo.holographlibrary.BarGraph.*;

/**
 * 막대 그래프를 그리기 위한 모듈.
 * 
 * @author leejeongho
 * @since 2014.09.09
 */
public class GraphBar {

	/** Context */
	Context mContext;

	/** Graph 영역의 root view */
	RelativeLayout graphRoot;
	/** Graph 를 그리는 Canvas 영역 */
	BarGraph mGraph;
	/** 포인트를 클릭했을때 Tooltip 표시를 위한 영역 */
	RelativeLayout rlTooltip;
	/** 날짜 표시 텍스트뷰 */
	TextView tvDate;
	/** graph y축 단위 */
	String unit = "kcal";

	/** graph 데이터 */
	ArrayList<PointData> arrData = new ArrayList<PointData>();

	/** graph 의 종류 구분 */
	final public static int graphFlagWorkout = 10003;
	final public static int graphFlagFood = 10004;
	final public static int graphFlagMedicine = 10005;

	/** graph 의 x축 범위를 지정하기 위한 flag */
	final public static int dateFlagDay = 24;
	final public static int dateFlagWeek = 7;
	final public static int dateFlagMonth = 28;
	final public static int dateFlagYear = 12;

	/** graph 의 색상 값. */
	final static String colorWorkoutOver = "#d6d138";
	final static String colorWorkoutDefault = "#5aaecc";
	final static String colorFoodOver = "#f0874f";
	final static String colorFoodDefault = "#d6d138";
	final static String colorMedicineBefore = "#5aaccc";
	final static String colorMedicineAfter = "#ff8c9c";
	final static String colorMedicineInsulin = "#f5a700";
	final static String colorMedicineOutside = "#61bf52";

	public GraphBar(Context context) {
		this(context, null, -1, -1, new ArrayList<PointData>());
	}

	/**
	 * 클래스 생성자.
	 * 
	 * @author leejeongho
	 * @since 2014.09.11
	 * @param context
	 *            context
	 * @param rlRoot
	 *            graph 를 넣을 부모 뷰.
	 * @param graphFlag
	 *            graph 종류. {@link GraphBar} 참고.
	 * @param dateFlag
	 *            x축 날짜 범위 종류. {@link GraphBar} 참고.
	 * @param arrData
	 *            graph 에 넣을 데이터.
	 */
	public GraphBar(Context context, RelativeLayout rlRoot, int graphFlag,
			int dateFlag, ArrayList<PointData> arrData) {
		super();
		this.mContext = context;
		this.arrData = arrData;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		graphRoot = (RelativeLayout) inflater.inflate(R.layout.graph_bar,
				rlRoot, false);

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				toPix(332), toPix(236));
		rlRoot.addView(graphRoot, rlParams);

		rlTooltip = (RelativeLayout) graphRoot.findViewById(R.id.rl_tooltip);
		tvDate = (TextView) graphRoot.findViewById(R.id.tv_date);

		if (graphFlag != -1 && dateFlag != -1) {
			initGraph(graphFlag, dateFlag);
		} else {
			// To-Do 예외처리.
		}
	}

	public void setData(ArrayList<PointData> arrData) {
		this.arrData = arrData;
	}

	/**
	 * Graph를 그리기 위해 값과 Graph에 대한 값을 설정하고 각 포인트에 대한 ClickEvent역시 설정. **주, 월 , 년
	 * 일때는 x축의 시작과 끝값을 사용안함.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param graphFlag
	 *            graph의 종류 구분.
	 * @param dateFlag
	 *            x축의 단위 설정을 위한 구분.
	 */
	public void initGraph(int graphFlag, int dateFlag) {
		mGraph = (BarGraph) graphRoot.findViewById(R.id.bargraph);

		int maxValue = 0, minValue = 0, goalValue = 0;

		final ArrayList<Bar> points = new ArrayList<Bar>();
		switch (graphFlag) {
		case graphFlagWorkout:
			setUnit("kcal");

			maxValue = getMaxValue(arrData) + 100;
			minValue = 0;
			goalValue = 1200;

			if (dateFlag == dateFlagDay) {
				int index = 0;
				for (int i = 0; i < dateFlagDay; i++) {
					Bar point = new Bar();
					int value = 0;
					if (i == arrData.get(index).getHour()) {
						value = arrData.get(index).getValue();
						index++;
					}
					if (value >= goalValue) {
						point.setColor(Color.parseColor(colorWorkoutOver));
					} else {
						point.setColor(Color.parseColor(colorWorkoutDefault));
					}
					point.setName("" + i);
					point.setValue(value);
					points.add(point);
				}

			} else {
				for (int i = 0; i < arrData.size(); i++) {
					Bar point = new Bar();
					int value = arrData.get(i).getValue();
					if (value >= goalValue) {
						point.setColor(Color.parseColor(colorWorkoutOver));
					} else {
						point.setColor(Color.parseColor(colorWorkoutDefault));
					}
					point.setName("" + i);
					point.setValue(value);
					points.add(point);
				}
			}
			setGoal(minValue, maxValue, goalValue);
			break;
		case graphFlagFood:
			setUnit("kcal");

			maxValue = getMaxValue(arrData) + 100;
			minValue = 0;
			goalValue = 1300;
			if (dateFlag == dateFlagDay) {
				int index = 0;
				for (int i = 0; i < dateFlagDay; i++) {
					Bar point = new Bar();
					int value = 0;
					if (i == arrData.get(index).getHour()) {
						value = arrData.get(index).getValue();
						index++;
					}
					if (value >= goalValue) {
						point.setColor(Color.parseColor(colorFoodOver));
					} else {
						point.setColor(Color.parseColor(colorFoodDefault));
					}
					point.setName("" + i);
					point.setValue(value);
					points.add(point);
				}

			} else {

				for (int i = 0; i < arrData.size(); i++) {
					Bar point = new Bar();
					int value = arrData.get(i).getValue();
					if (value >= goalValue) {
						point.setColor(Color.parseColor(colorFoodOver));
					} else {
						point.setColor(Color.parseColor(colorFoodDefault));
					}
					point.setName("" + i);
					point.setValue(value);
					points.add(point);
				}
			}
			setGoal(minValue, maxValue, goalValue);
			break;
		case graphFlagMedicine:
			setUnit("횟수");

			maxValue = 2;
			minValue = 0;

			if (dateFlag == dateFlagDay) {
				int index = 0;
				for (int i = 0; i < dateFlagDay; i++) {
					Bar point = new Bar();
					int value = 0;
					if (i == arrData.get(index).getHour()) {
						value = arrData.get(index).getValue();
						index++;
					}
					int medicineFlag = arrData.get(i).getMedicineFlag();
					switch (medicineFlag) {
					case 0:
						point.setColor(Color.parseColor(colorMedicineAfter));
						break;
					case 1:
						point.setColor(Color.parseColor(colorMedicineBefore));
						break;
					case 2:
						point.setColor(Color.parseColor(colorMedicineInsulin));
						break;
					case 3:
						point.setColor(Color.parseColor(colorMedicineOutside));
						break;
					}

					point.setName("" + i);
					point.setValue(value);
					points.add(point);
				}

			} else {

				for (int i = 0; i < arrData.size(); i++) {
					Bar point = new Bar();
					int value = arrData.get(i).getValue();
					int medicineFlag = arrData.get(i).getMedicineFlag();
					switch (medicineFlag) {
					case 0:
						point.setColor(Color.parseColor(colorMedicineAfter));
						break;
					case 1:
						point.setColor(Color.parseColor(colorMedicineBefore));
						break;
					case 2:
						point.setColor(Color.parseColor(colorMedicineInsulin));
						break;
					case 3:
						point.setColor(Color.parseColor(colorMedicineOutside));
						break;
					}
					point.setName("" + i);
					point.setValue(value);
					points.add(point);
				}
			}
			break;
		}

		TextView tvMaxY = (TextView) graphRoot.findViewById(R.id.tv_max_y);
		TextView tvHalfY = (TextView) graphRoot.findViewById(R.id.tv_half_y);
		TextView tvMinY = (TextView) graphRoot.findViewById(R.id.tv_min_y);
		tvMaxY.setText("" + maxValue);
		tvHalfY.setText("" + (((maxValue - minValue) / 2) + minValue));
		tvMinY.setText("" + minValue);

		setXLabel(dateFlag);

		mGraph.setBars(points);
		mGraph.setMaxValue(maxValue);
		mGraph.setOnBarClickedListener(new OnBarClickedListener() {

			@Override
			public void onClick(int index) {
				Coordinates coordinates = points.get(index).getCoordinates();
				addTooltip(coordinates);
			}

		});

		mGraph.update();
	}

	/**
	 * 그래프 영역에 날짜를 보여줄 것인지 설정. 기본은 보여짐.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param isShow
	 *            날짜를 보여줄것인가에 대한 true, false
	 */
	public void showDate(boolean isShow) {
		if (isShow) {
			tvDate.setVisibility(View.VISIBLE);
		} else {
			tvDate.setVisibility(View.GONE);
		}
	}

	/**
	 * y축 단위 표시를 위한 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param unit
	 *            단위. ex) mg/dL, mmHg, kcal
	 */
	private void setUnit(String unit) {
		this.unit = unit;
		TextView tvUnit = (TextView) graphRoot.findViewById(R.id.tv_unit);
		tvUnit.setText(unit);
	}

	/**
	 * DIP 수치를 Pixel 수치로 변환. 동적으로 View를 그려넣을때 필요.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param value
	 *            변환할 DIP 수치.
	 * @return 변환된 Pixel 수치.
	 */
	private int toPix(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, mContext.getResources().getDisplayMetrics());
	}

	/**
	 * Graph에 목표수치 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param minY
	 *            Y 범위의 최소값.
	 * @param maxY
	 *            Y 범위의 최대값.
	 * @param value
	 *            목표수치 값.
	 */
	private void setGoal(int minY, int maxY, int value) {
		/** 목표 표시를 위한 별도의 View */
		RelativeLayout rlGoal = (RelativeLayout) graphRoot
				.findViewById(R.id.rl_goal);
		rlGoal.setVisibility(View.VISIBLE);

		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, toPix(15));
		rlParams.setMargins(toPix(40),
				(int) (toPix(50 - 15) + ((maxY - value) * per_height)),
				toPix(20), 0);
		rlGoal.setLayoutParams(rlParams);
		TextView tvGoal = (TextView) graphRoot.findViewById(R.id.tv_goal);
		tvGoal.setText(value + unit);
	}

	/**
	 * 포인트를 클릭했을때 값을 표시. ** 라이브러리에서 포인트를 그려넣을때의 Pixel좌표를 그대로 사용하므로 dip 변환을 안해도됨.
	 * **
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param coordinates
	 *            {@link Coordinates}
	 */
	private void addTooltip(Coordinates coordinates) {
		rlTooltip.removeAllViews();

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlParams.setMargins((int) coordinates.getX(), (int) coordinates.getY(),
				0, 0);
		TextView tvTooltip = new TextView(mContext);
		tvTooltip.setLayoutParams(rlParams);
		tvTooltip.setText("" + ((int) coordinates.getValue()));
		tvTooltip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		tvTooltip.setTextColor(Color.WHITE);
		tvTooltip.setBackgroundColor(Color.GRAY);
		tvTooltip.setPadding(10, 0, 10, 0);
		rlTooltip.addView(tvTooltip);
	}

	/**
	 * x축의 label 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.094
	 * @param flag
	 *            x축에 대한 단위. Day: 24, Week: 7, Month: 28, Year: 12
	 */
	private void setXLabel(int flag) {
		Calendar mDate = Calendar.getInstance();
		RelativeLayout rlXLabelDay = (RelativeLayout) graphRoot
				.findViewById(R.id.rl_graph_x_label_day);
		LinearLayout llXLabelWeek = (LinearLayout) graphRoot
				.findViewById(R.id.ll_graph_x_label_week);
		LinearLayout llXLabelMonth = (LinearLayout) graphRoot
				.findViewById(R.id.ll_graph_x_label_month);
		LinearLayout llXLabelYear = (LinearLayout) graphRoot
				.findViewById(R.id.ll_graph_x_label_year);
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
				if (i == 0) {
					arrLabelMonth.get(i).setText(
							mDate.get(Calendar.DAY_OF_MONTH) + "");
					mDate.add(Calendar.DAY_OF_MONTH, 6);
				} else if (i % 2 == 0) {
					arrLabelMonth.get(i).setText(
							mDate.get(Calendar.DAY_OF_MONTH) + "");
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
	 * array 의 최대값 구하기.
	 * 
	 * @author leejeongho
	 * @since 2014.09.11
	 * @param arrData
	 *            {@link PointData} 의 ArrayList
	 * @return array 의 최대값.
	 */
	private int getMaxValue(ArrayList<PointData> arrData) {
		int maxValue = arrData.get(0).getValue();
		for (int i = 1; i < arrData.size(); i++) {
			int currentValue = arrData.get(i).getValue();
			if (maxValue < currentValue) {
				maxValue = currentValue;
			}
		}
		return maxValue;
	}

}

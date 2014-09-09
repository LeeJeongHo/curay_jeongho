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

import com.echo.holographlibrary.*;
import com.echo.holographlibrary.LineGraph.*;

public class GraphLine extends View {

	/** Context */
	Context mContext;

	/** Graph 영역의 root view */
	RelativeLayout graphRoot;
	/** Graph 를 그리는 Canvas 영역 */
	LineGraph mGraph;
	/** 범위 표시를 위한 ImageView */
	ImageView ivRangeFirst;
	/** 비정상범위 표시를 위한 ImageView */
	ImageView ivRangeSecond;
	/** 포인트를 클릭했을때 Tooltip 표시를 위한 영역 */
	RelativeLayout rlTooltip;
	/** 날짜 표시 텍스트뷰 */
	TextView tvDate;
	/** graph y축 단위 */
	String unit = "kcal";

	/** graph 의 종류 구분 */
	final public static int graphFlagGlucose = 10000;
	final public static int graphFlagPressure = 10001;
	final public static int graphFlagWeight = 10002;

	/** graph 의 x축 범위를 지정하기 위한 flag */
	final public static int dateFlagDay = 24 * 60;
	final public static int dateFlagWeek = 7;
	final public static int dateFlagMonth = 28;
	final public static int dateFlagYear = 12;

	/** graph 의 색상 값. */
	final static String colorWeight = "#5aaccc";
	final static String colorContract = "#5aaccc";
	final static String colorRelax = "#02619c";
	final static String colorBefore = "#f5aa00";
	final static String colorAfter = "#5aaccc";

	final static String colorRangeNormal = "#eaf4f8";
	final static String colorRangeAdnormal = "#fef5e0";
	final static String colorRangeContract = "#eaf4f8";
	final static String colorRangeRelax = "#e0ecf4";

	public GraphLine(Context context) {
		this(context, null, -1, -1);
	}

	public GraphLine(Context context, RelativeLayout rlRoot, int graphFlag,	int dateFlag) {
		super(context);
		mContext = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		graphRoot = (RelativeLayout) inflater.inflate(R.layout.graph_line, rlRoot, false);

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(toPix(332), toPix(236));
		rlRoot.addView(graphRoot, rlParams);

		ivRangeFirst = (ImageView) graphRoot.findViewById(R.id.iv_range_first);
		ivRangeSecond = (ImageView) graphRoot.findViewById(R.id.iv_range_second);
		rlTooltip = (RelativeLayout) graphRoot.findViewById(R.id.rl_tooltip);
		tvDate = (TextView) graphRoot.findViewById(R.id.tv_date);

		if (graphFlag != -1 && dateFlag != -1) {
			initGraph(graphFlag, dateFlag);
		} else {
			// To-Do 예외처리.
		}
	}

	/**
	 * Graph를 그리기 위해 값과 Graph에 대한 값을 설정하고 각 포인트에 대한 ClickEvent역시 설정. 
	 * **주, 월 , 년 일때는 x축의 시작과 끝값을 사용안함.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param graphFlag graph의 종류 구분.
	 * @param dateFlag x축의 단위 설정을 위한 구분.
	 */
	public void initGraph(int graphFlag, int dateFlag) {
		setLegend(graphFlag);

		mGraph = (LineGraph) graphRoot.findViewById(R.id.linegraph);
		mGraph.removeAllLines();

		int maxX = dateFlag;
		int increaseValue = 1;

		if (dateFlag == dateFlagDay) {
			increaseValue = 60;
			mGraph.setMaxX(maxX);
		} else {
			mGraph.setMaxX(maxX + 1);
		}

		int minY = 0, maxY = 0;

		switch (graphFlag) {
		case graphFlagGlucose:
			setUnit("mg/dL");
			maxY = 300;
			minY = 60;

			Line lineAfter = new Line();
			Line lineBefore = new Line();
			for (int i = 1; i <= maxX; i += increaseValue) {
				if (((int) (Math.random() * maxX) % 2) == 0) {
					float value = (float) (minY + Math.random() * (maxY - minY));
					LinePoint p = new LinePoint();
					p.setX(i);
					p.setY(value);
					lineAfter.addPoint(p);
					lineAfter.setColor(Color.parseColor(colorAfter));
				}
			}
			mGraph.addLine(lineAfter);

			for (int i = 1; i <= maxX; i += increaseValue) {
				if (((int) (Math.random() * maxX) % 2) == 0) {
					float value = (float) (minY + Math.random() * (maxY - minY));
					LinePoint p = new LinePoint();
					p.setX(i);
					p.setY(value);
					lineBefore.addPoint(p);
					lineBefore.setColor(Color.parseColor(colorBefore));
				}
			}
			mGraph.addLine(lineBefore);

			setRangeFirst(minY, maxY, 100, 125, colorRangeNormal);
			setRangeSecond(minY, maxY, 0, 100, colorRangeAdnormal);

			break;
		case graphFlagPressure:
			setUnit("mmHg");
			maxY = 180;
			minY = 80;

			ArrayList<Line> arrLine = new ArrayList<Line>();
			int index = 0;
			for (int i = 1; i <= maxX; i += increaseValue) {
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
				arrLine.get(index).setColorPressure(Color.parseColor(colorRelax));

				mGraph.addLine(arrLine.get(index));
				index++;
			}

			setRangeFirst(minY, maxY, 120, 150, colorRangeContract);
			setRangeSecond(minY, maxY, 90, 110, colorRangeRelax);

			break;
		case graphFlagWeight:
			setUnit("kcal");
			maxY = 80;
			minY = 50;
			int goalValue = 70;
			
			Line lineWeight = new Line();
			for (int i = 1; i <= maxX; i += increaseValue) {
				if (((int) (Math.random() * maxX) % 2) == 0) {
					float value = (float) (goalValue - 10 + Math.random() * 20);
					LinePoint p = new LinePoint();
					p.setX(i);
					p.setY(value);
					lineWeight.addPoint(p);
					lineWeight.setColor(Color.parseColor(colorWeight));
				}
			}
			mGraph.addLine(lineWeight);

			setGoal(minY, maxY, goalValue);
			break;
		}

		TextView tvMaxY = (TextView) graphRoot.findViewById(R.id.tv_max_y);
		TextView tvHalfY = (TextView) graphRoot.findViewById(R.id.tv_half_y);
		TextView tvMinY = (TextView) graphRoot.findViewById(R.id.tv_min_y);
		tvMaxY.setText("" + ((int) maxY));
		tvHalfY.setText("" + ((int) (((maxY - minY) / 2) + minY)));
		tvMinY.setText("" + ((int) minY));

		setXLabel(dateFlag);
		mGraph.setRangeY(minY, maxY);
		mGraph.setOnPointClickedListener(new OnPointClickedListener() {

			@Override
			public void onClick(int lineIndex, int pointIndex) {
				Coordinates coordinates = mGraph.getLine(lineIndex).getCoordinates(pointIndex);
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
	 * @param isShow 날짜를 보여줄것인가에 대한 true, false
	 */
	public void showDate(boolean isShow){
		if(isShow){
			tvDate.setVisibility(View.VISIBLE);
		}
		else{
			tvDate.setVisibility(View.GONE);
		}
	}
	
	/**
	 * y축 단위 표시를 위한 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param unit 단위. ex) mg/dL, mmHg, kcal 
	 */
	private void setUnit(String unit){
		this.unit = unit;
		TextView tvUnit = (TextView)graphRoot.findViewById(R.id.tv_unit);
		tvUnit.setText(unit);
	}

	/**
	 * 범례 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param graphFlag graph의 종류.
	 */
	private void setLegend(int graphFlag){
		ImageView ivLegendFirst = (ImageView)graphRoot.findViewById(R.id.iv_legend_first);
		ImageView ivLegendSecond = (ImageView)graphRoot.findViewById(R.id.iv_legend_second);
		TextView tvLegendFirst = (TextView)graphRoot.findViewById(R.id.tv_legend_first);
		TextView tvLegendSecond = (TextView)graphRoot.findViewById(R.id.tv_legend_second);
		
		switch(graphFlag){
		case graphFlagGlucose:
			ivLegendFirst.setBackgroundResource(R.drawable.graph_icon_after);
			ivLegendSecond.setBackgroundResource(R.drawable.graph_icon_before);
			tvLegendFirst.setText("식후");
			tvLegendSecond.setText("식전");
			break;
		case graphFlagPressure:
			ivLegendFirst.setBackgroundResource(R.drawable.graph_icon_contract);
			ivLegendSecond.setBackgroundResource(R.drawable.graph_icon_relax);
			tvLegendFirst.setText("수축");
			tvLegendSecond.setText("이완");
			break;
		case graphFlagWeight:
			ivLegendFirst.setVisibility(View.INVISIBLE);
			ivLegendSecond.setVisibility(View.INVISIBLE);
			tvLegendFirst.setVisibility(View.INVISIBLE);
			tvLegendSecond.setVisibility(View.INVISIBLE);
			break;
		}
		
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
		RelativeLayout rlGoal = (RelativeLayout) graphRoot.findViewById(R.id.rl_goal);
		rlGoal.setVisibility(View.VISIBLE);

		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, toPix(15));
		rlParams.setMargins(toPix(35), (int) (toPix(50 - 15) + ((maxY - value) * per_height)), toPix(20), 0);
		rlGoal.setLayoutParams(rlParams);
		TextView tvGoal = (TextView) graphRoot.findViewById(R.id.tv_goal);
		tvGoal.setText(value + unit);
	}

	/**
	 * Graph에 정상범위 표시 첫번째.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param minY
	 *            Y 범위의 최소값.
	 * @param maxY
	 *            Y 범위의 최대값.
	 * @param minNormal
	 *            정상범위의 최소값.
	 * @param maxNormal
	 *            정상범위의 최대값.
	 */
	private void setRangeFirst(int minY, int maxY, int minNormal,
			int maxNormal, String color) {
		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		float iv_normal_height = per_height * (maxNormal - minNormal);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) iv_normal_height);
		rlParams.setMargins(toPix(34), (int) (toPix(50) + ((maxY - maxNormal) * per_height)), toPix(20), 0);
		ivRangeFirst.setLayoutParams(rlParams);
		ivRangeFirst.setBackgroundColor(Color.parseColor(color));
		ivRangeFirst.setVisibility(View.VISIBLE);
	}

	/**
	 * Graph에 정상범위 표시 두번째.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param minY
	 *            Y 범위의 최소값.
	 * @param maxY
	 *            Y 범위의 최대값.
	 * @param minAdnormal
	 *            비정상범위의 최소값.
	 * @param maxAdnormal
	 *            비정상범위의 최대값.
	 */
	private void setRangeSecond(int minY, int maxY, int minAdnormal,
			int maxAdnormal, String color) {
		if (minY > minAdnormal) {
			minAdnormal = minY;
		}
		if (maxY < maxAdnormal) {
			maxAdnormal = maxY;
		}
		int px_height = toPix(150);
		float per_height = (float) px_height / (maxY - minY);
		float iv_adnormal_height = per_height * (maxAdnormal - minAdnormal);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) iv_adnormal_height);
		rlParams.setMargins(toPix(34), (int) (toPix(50) + ((maxY - maxAdnormal) * per_height)),	toPix(20), 0);
		ivRangeSecond.setLayoutParams(rlParams);
		ivRangeSecond.setBackgroundColor(Color.parseColor(color));
		ivRangeSecond.setVisibility(View.VISIBLE);
	}
	
	/**
	 * x축의 label 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param flag
	 *            x축에 대한 단위. Day: 24, Week: 7, Month: 28, Year: 12
	 */
	private void setXLabel(int flag) {
		Calendar mDate = Calendar.getInstance();
		RelativeLayout rlXLabelDay = (RelativeLayout) graphRoot.findViewById(R.id.rl_graph_x_label_day);
		LinearLayout llXLabelWeek = (LinearLayout) graphRoot.findViewById(R.id.ll_graph_x_label_week);
		LinearLayout llXLabelMonth = (LinearLayout) graphRoot.findViewById(R.id.ll_graph_x_label_month);
		LinearLayout llXLabelYear = (LinearLayout) graphRoot.findViewById(R.id.ll_graph_x_label_year);
		switch (flag) {
		case dateFlagDay:
			tvDate.setText(DateFormat.format("MM월 dd일", mDate.getTime()));
			rlXLabelDay.setVisibility(View.VISIBLE);
			llXLabelWeek.setVisibility(View.INVISIBLE);
			llXLabelMonth.setVisibility(View.INVISIBLE);
			llXLabelYear.setVisibility(View.INVISIBLE);
			break;
		case dateFlagWeek:
			String dateEndWeek = (String) DateFormat.format("MM월 dd일", 	mDate.getTime());
			mDate.add(Calendar.DAY_OF_MONTH, -6);
			String dateStartWeek = (String) DateFormat.format("MM월 dd일 - ", 	mDate.getTime());
			tvDate.setText(dateStartWeek + dateEndWeek);

			rlXLabelDay.setVisibility(View.INVISIBLE);
			llXLabelWeek.setVisibility(View.VISIBLE);
			llXLabelMonth.setVisibility(View.INVISIBLE);
			llXLabelYear.setVisibility(View.INVISIBLE);

			ArrayList<TextView> arrLabelWeek = new ArrayList<TextView>();
			for (int i = 0; i < 7; i++) {
				arrLabelWeek.add((TextView) llXLabelWeek.getChildAt(i));
				arrLabelWeek.get(i).setText(mDate.get(Calendar.DAY_OF_MONTH) + "");
				mDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			break;
		case dateFlagMonth:
			String dateEndMonth = (String) DateFormat.format("MM월 dd일", mDate.getTime());
			mDate.add(Calendar.DAY_OF_MONTH, -27);
			String dateStartMonth = (String) DateFormat.format("MM월 dd일 - ", mDate.getTime());
			tvDate.setText(dateStartMonth + dateEndMonth);

			rlXLabelDay.setVisibility(View.INVISIBLE);
			llXLabelWeek.setVisibility(View.INVISIBLE);
			llXLabelMonth.setVisibility(View.VISIBLE);
			llXLabelYear.setVisibility(View.INVISIBLE);
			ArrayList<TextView> arrLabelMonth = new ArrayList<TextView>();
			for (int i = 0; i < 9; i++) {
				arrLabelMonth.add((TextView) llXLabelMonth.getChildAt(i));
				if (i == 0) {
					arrLabelMonth.get(i).setText(mDate.get(Calendar.DAY_OF_MONTH) + "");
					mDate.add(Calendar.DAY_OF_MONTH, 6);
				} else if (i % 2 == 0) {
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
				arrLabelYear.get(i).setText((mDate.get(Calendar.MONTH) + 1) + "");
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
	 * @since 2014.09.09
	 * @param coordinates
	 *            {@link Coordinates}
	 */
	private void addTooltip(Coordinates coordinates) {
		rlTooltip.removeAllViews();

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlParams.setMargins((int) coordinates.getX() + 10, (int) coordinates.getY() + 10, 0, 0);
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
	 * DIP 수치를 Pixel 수치로 변환. 동적으로 View를 그려넣을때 필요.
	 * 
	 * @author leejeongho
	 * @since 2014.09.09
	 * @param value
	 *            변환할 DIP 수치.
	 * @return 변환된 Pixel 수치.
	 */
	private int toPix(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,	value, mContext.getResources().getDisplayMetrics());
	}

}

package com.example.graphtest;

import java.lang.Character.*;

import android.support.v7.app.ActionBarActivity;
import android.graphics.*;
import android.os.Bundle;
import android.util.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import com.echo.holographlibrary.*;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;

public class BloodMainActivity extends ActionBarActivity {
	/** Graph 를 그리는 Canvas 영역 */
	LineGraph li;
	/** 정상범위 표시를 위한 ImageView */
	ImageView ivNormal;
	/** 비정상범위 표시를 위한 ImageView */
	ImageView ivAdnormal;
    /** 포인트를 클릭했을때 Tooltip 표시를 위한 영역 */
	RelativeLayout rlTooltip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_blood_main);
		ivNormal = (ImageView)findViewById(R.id.iv_normal);
		ivAdnormal = (ImageView)findViewById(R.id.iv_adnormal);
		rlTooltip = (RelativeLayout)findViewById(R.id.rl_tooltip);
        initGraph();
	}

	/**
	 * Graph를 그리기 위해 값과 Graph에 대한 값을 설정하고 각 포인트에 대한 ClickEvent역시 설정.
	 * 
	 * @author leejeongho
	 * @since 2014.09.02
	 */
	private void initGraph(){
		Line l = new Line();
		LinePoint p = new LinePoint();
		p.setX(0*60);
		p.setY(100);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(8*60);
		p.setY(110);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(10*60);
		p.setY(400);
		l.addPoint(p);
		l.setColor(Color.parseColor("#5caecc"));
		
		Line l2 = new Line();
		LinePoint p2 = new LinePoint();
		p2.setX(0*60);
		p2.setY(125);
		l2.addPoint(p2);
		p2 = new LinePoint();
		p2.setX(12*60);
		p2.setY(80);
		l2.addPoint(p2);
		p2 = new LinePoint();
		p2.setX(15*60);
		p2.setY(190);
		l2.addPoint(p2);
		l2.setColor(Color.parseColor("#f5a700"));
		
		int maxY = 300;
		int minY = 80;
		
		li = (LineGraph)findViewById(R.id.linegraph);
		li.addLine(l);
		li.addLine(l2);
		li.setRangeY(minY, maxY);
		li.setMaxX(24.f*60);
		
		li.setOnPointClickedListener(new OnPointClickedListener(){

			@Override
			public void onClick(int lineIndex, int pointIndex) {
				// TODO Auto-generated method stub
				LinePoint pp = li.getLine(lineIndex).getPoint(pointIndex);
				Coordinates coordinates = li.getLine(lineIndex).getCoordinates(pointIndex);
				addTooltip(coordinates);
			}
		});	
		
		
		setNormalRange(minY, maxY, 100, 125);
		setAdnormalRange(minY, maxY, 0, 100);
		setGoal(minY, maxY, 110);
	}
	
	/**
	 * Graph에 정상범위 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param minY Y 범위의 최소값.
	 * @param maxY Y 범위의 최대값.
	 * @param minNormal 정상범위의 최소값.
	 * @param maxNormal 정상범위의 최대값.
	 */
	private void setNormalRange(int minY, int maxY, int minNormal, int maxNormal) {
		int px_height = toPix(150);
		float per_height = (float)px_height / (maxY - minY);
		float iv_normal_height = per_height * (maxNormal-minNormal);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)iv_normal_height);
		rlParams.setMargins(toPix(40), (int)(toPix(56)+((maxY-maxNormal)*per_height)), toPix(20), 0);
		ivNormal.setLayoutParams(rlParams);
	}

	/**
	 * Graph에 비정상범위 표시.
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param minY Y 범위의 최소값.
	 * @param maxY Y 범위의 최대값.
	 * @param minNormal 정상범위의 최소값.
	 * @param maxNormal 정상범위의 최대값.
	 */
	private void setAdnormalRange(int minY, int maxY, int minAdnormal, int maxAdnormal) {
		if(minY > minAdnormal){
			minAdnormal = minY;
		}
		if(maxY < maxAdnormal){
			maxAdnormal = maxY;
		}
		int px_height = toPix(150);
		float per_height = (float)px_height / (maxY - minY);
		float iv_adnormal_height = per_height * (maxAdnormal-minAdnormal);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)iv_adnormal_height);
		rlParams.setMargins(toPix(40), (int)(toPix(56)+((maxY-maxAdnormal)*per_height)), toPix(20), 0);
		ivAdnormal.setLayoutParams(rlParams);
	}
	
	private void setGoal(int minY, int maxY, int value){
		/** 목표 표시를 위한 View */
		RelativeLayout rlGoal = (RelativeLayout)findViewById(R.id.ll_goal);

		int px_height = toPix(150);
		float per_height = (float)px_height / (maxY - minY);
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, toPix(15));
		rlParams.setMargins(toPix(40), (int)(toPix(56-15)+((maxY-value)*per_height)), toPix(20), 0);
		rlGoal.setLayoutParams(rlParams);
		TextView tvGoal = (TextView)findViewById(R.id.tv_goal);
		tvGoal.setText(value+" mg/dL");
	}
	
	/**
	 * 포인트를 클릭했을때 값을 표시.
	 * ** 라이브러리에서 포인트를 그려넣을때의 Pixel좌표를 그대로 사용하므로 dip 변환을 안해도됨. **
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param coordinates {@link Coordinates}
	 */
	private void addTooltip(Coordinates coordinates){
        rlTooltip.removeAllViews();
		
        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rlParams.setMargins((int)coordinates.getX()+10, (int)coordinates.getY()+10, 0, 0);
        TextView tvTooltip = new TextView(getApplicationContext());
        tvTooltip.setLayoutParams(rlParams);
        tvTooltip.setText(""+coordinates.getValue());
        tvTooltip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tvTooltip.setTextColor(0xff424242);
        tvTooltip.setBackgroundColor(0xffbcbc00);
        tvTooltip.setPadding(10, 0, 10, 0);
        rlTooltip.addView(tvTooltip);
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
		getMenuInflater().inflate(R.menu.blood_main, menu);
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

package com.example.graphtest;

import android.support.v7.app.ActionBarActivity;
import android.graphics.*;
import android.os.Bundle;
import android.util.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

public class BloodMainActivity extends ActionBarActivity {
	LineGraph li;
	ImageView ivNormal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blood_main);
		ivNormal = (ImageView)findViewById(R.id.iv_normal);
        init_graph();
	}
	
	private void init_graph(){
		Line l = new Line();
		LinePoint p = new LinePoint();
		p.setX(0);
		p.setY(100);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(8);
		p.setY(110);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(10);
		p.setY(400);
		l.addPoint(p);
		l.setColor(Color.parseColor("#5caecc"));
		
		Line l2 = new Line();
		LinePoint p2 = new LinePoint();
		p2.setX(0);
		p2.setY(125);
		l2.addPoint(p2);
		p2 = new LinePoint();
		p2.setX(12);
		p2.setY(80);
		l2.addPoint(p2);
		p2 = new LinePoint();
		p2.setX(15);
		p2.setY(190);
		l2.addPoint(p2);
		l2.setColor(Color.parseColor("#f5a700"));
		
		int maxY = 300;
		int minY = 0;
		li = (LineGraph)findViewById(R.id.linegraph);
		li.addLine(l);
		li.addLine(l2);
		li.setRangeY(minY, maxY);
		li.setLineToFill(-1);
		li.showHorizontalGrid(true);
		li.showMinAndMaxValues(true);
		li.setGridColor(0xff878787);
		li.setTextSize(40.f);
		li.setTextColor(0xff424242);
		
		
		li.setOnPointClickedListener(new OnPointClickedListener(){

			@Override
			public void onClick(int lineIndex, int pointIndex) {
				// TODO Auto-generated method stub
				LinePoint pp = li.getLine(lineIndex).getPoint(pointIndex);
				
				Toast.makeText(getApplicationContext(), ""+li.getMaxX(), Toast.LENGTH_SHORT).show();	
			}
			
		});		
		
		int px_height = toPix(150);
		float per_height = (float)px_height / (maxY - minY);
		float iv_normal_height = per_height * 25;
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)iv_normal_height);
		rlParams.setMargins(toPix(40), (int)(toPix(56)+((maxY-125)*per_height)), toPix(20), 0);
		ivNormal.setLayoutParams(rlParams);
		
	}
	
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

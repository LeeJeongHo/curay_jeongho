package com.example.graphtest;

import android.support.v7.app.ActionBarActivity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.webkit.*;
import android.widget.*;

public class MainActivity extends ActionBarActivity {
    WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
							
	    Button btnBlood = (Button)findViewById(R.id.btn_blood_main);
	    Button btnWorkout = (Button)findViewById(R.id.btn_workout_main);
	    Button btnPressure = (Button)findViewById(R.id.btn_pressure_main);
	    Button btnFood = (Button)findViewById(R.id.btn_food_main);
	    Button btnMedicine = (Button)findViewById(R.id.btn_medicine_main);
	    Button btnWeight = (Button)findViewById(R.id.btn_weight_main);
	    
		btnBlood.setOnClickListener(mClick);
		btnWorkout.setOnClickListener(mClick);
		btnPressure.setOnClickListener(mClick);
		btnFood.setOnClickListener(mClick);
		btnMedicine.setOnClickListener(mClick);
		btnWeight.setOnClickListener(mClick);
	}
	
	OnClickListener mClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_blood_main:
				startActivity(new Intent(getApplicationContext(), com.example.graphtest.BloodMainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
				break;
			case R.id.btn_workout_main:
				startActivity(new Intent(getApplicationContext(), com.example.graphtest.WorkoutMainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
				break;
			case R.id.btn_pressure_main:
				startActivity(new Intent(getApplicationContext(), com.example.graphtest.PressureMainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
				break;
			case R.id.btn_food_main:
				startActivity(new Intent(getApplicationContext(), com.example.graphtest.FoodMainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
				break;
			case R.id.btn_medicine_main:
				startActivity(new Intent(getApplicationContext(), com.example.graphtest.MedicineMainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
				break;
			case R.id.btn_weight_main:
				startActivity(new Intent(getApplicationContext(), com.example.graphtest.WeightMainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
				break;
			default:
				break;
			}  	
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

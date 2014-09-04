/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.echo.holographlibrary;

import java.util.ArrayList;

import android.view.*;

public class Line {

	private ArrayList<LinePoint> points = new ArrayList<LinePoint>();
	private ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
	private int color;
	private boolean showPoints = true;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public ArrayList<LinePoint> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<LinePoint> points) {
		this.points = points;
	}

	public void addPoint(LinePoint point) {
		points.add(point);
	}

	public LinePoint getPoint(int index) {
		return points.get(index);
	}

	public int getSize() {
		return points.size();
	}

	public boolean isShowingPoints() {
		return showPoints;
	}

	public void setShowingPoints(boolean showPoints) {
		this.showPoints = showPoints;
	}

	/**
	 * 좌표와 해당하는 값을 ArrayList에 추가.
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param x
	 *            x 좌표.
	 * @param y
	 *            y 좌표.
	 * @param value
	 *            좌표에 해당하는 값.
	 * @param width
	 *            tooltip의 넓이. bar graph 에서만 사용. line graph 에서는 0을 넣어도됨.
	 */
	public void addCoordinate(float x, float y, float value, int width) {
		coordinates.add(new Coordinates(x, y, value, width));
	}

	/**
	 * 해당하는 point 에 맞춰 ArrayList에 있는 {@link Coordinates} 를 반환.
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param index
	 *            point에 대한 index
	 * @return ArrayList에 있는 {@link Coordinates}
	 */
	public Coordinates getCoordinates(int index) {
		return coordinates.get(index);
	}

}

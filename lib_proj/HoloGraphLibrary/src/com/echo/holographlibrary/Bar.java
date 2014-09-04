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

import java.util.*;

import android.graphics.Path;
import android.graphics.Region;

public class Bar {

	private int color;
	private String name;
	private float value;
	private Path path;
	private Region region;
	private Coordinates coordinates;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * 좌표와 해당하는 값을 ArrayList에 추가.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
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
		coordinates = new Coordinates(x, y, value, width);
	}

	/**
	 * 해당하는 point 에 할당된 {@link Coordinates} 를 반환.
	 * 
	 * @author leejeongho
	 * @since 2014.09.04
	 * @return {@link Coordinates}
	 */
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
}

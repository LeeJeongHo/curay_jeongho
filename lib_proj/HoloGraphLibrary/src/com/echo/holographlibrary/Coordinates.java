package com.echo.holographlibrary;

/**
 * Graph에 그려지는 좌표와 해당하는 값을 위한 Object.
 * 
 * @author leejeongho
 * @since 2014.09.03
 */
public class Coordinates {

    private float x = 0;
	private float y = 0;
	private float value = 0;
	
	/**
	 * Graph에 그려지는 좌표와 해당하는 값을 위한 Object.
	 * 
	 * @author leejeongho
	 * @since 2014.09.03
	 * @param x x 좌표.
	 * @param y y 좌표.
	 * @param value 좌표에 해당하는 값.
	 */
	public Coordinates(float x, float y, float value) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
    public Coordinates() { }

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}

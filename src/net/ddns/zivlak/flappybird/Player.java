package net.ddns.zivlak.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {

	public static final int SPEED			= 5;

	private int m_x = 0;
	private int m_y = 0;
	private int m_velX = 0;
	private int m_velY = 0;
	private int m_width = 100;
	private int m_height = 100;

	private Bitmap m_sprite;

	public Player(Bitmap sprite) {
		m_sprite = sprite;
		m_width = sprite.getWidth();
		m_height = sprite.getHeight();
	}

	public void update() {
		m_y += m_velX;
	}

	public void render(Canvas canvas) {
		Rect src = new Rect(0, 0, m_width, m_height);
		Rect dst = new Rect(0, 0, m_width, m_height);
		canvas.drawBitmap(m_sprite, src, dst, new Paint());
	}

	public int getX() {
		return m_x;
	}

	public void setX(int x) {
		m_x = x;
	}

	public int getY() {
		return m_y;
	}

	public void setY(int y) {
		m_y = y;
	}

	public int getVelX() {
		return m_velX;
	}

	public void setVelX(int velX) {
		m_velX = velX;
	}

	public int getVelY() {
		return m_velY;
	}

	public void setVelY(int velY) {
		m_velY = velY;
	}

	public int getWidth() {
		return m_width;
	}

	public void setWidth(int width) {
		m_width = width;
	}

	public int getHeight() {
		return m_height;
	}

	public void setHeight(int height) {
		m_height = height;
	}
}

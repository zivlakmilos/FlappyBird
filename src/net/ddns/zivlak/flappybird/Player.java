package net.ddns.zivlak.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {

	public static final int SPEED			= 300;
	public static final int GRAVITY			= 500;

	private double m_x = 0.0;
	private double m_y = 0.0;
	private double m_velX = 0.0;
	private double m_velY = SPEED;
	private double m_width;
	private double m_height;
	private double m_spriteWidth;
	private double m_spriteHeight;

	private Bitmap m_sprite;

	public Player(Bitmap sprite) {
		m_sprite = sprite;
		m_width = sprite.getWidth();
		m_height = sprite.getHeight();
		m_spriteWidth = m_width;
		m_spriteHeight = m_height;
	}

	public void update(Canvas canvas, double tick) {

		m_y += m_velY * tick;
		m_velY += SPEED * tick;

		if(m_velY > SPEED)
			m_velY = SPEED;

		if(m_y + m_height > canvas.getHeight())
			m_y = canvas.getHeight() - m_height;
		else if(m_y < 0)
			m_y = 0;
	}

	public void render(Canvas canvas) {
		Rect src = new Rect(0, 0, (int)m_spriteWidth, (int)m_spriteHeight);
		Rect dst = new Rect((int)m_x, (int)m_y, (int)(m_x + m_width), (int)(m_y + m_height));
		canvas.drawBitmap(m_sprite, src, dst, new Paint());
	}

	public void jump() {
		m_velY = -SPEED;
	}

	public double getX() {
		return m_x;
	}

	public void setX(double x) {
		m_x = x;
	}

	public double getY() {
		return m_y;
	}

	public void setY(double y) {
		m_y = y;
	}

	public double getVelX() {
		return m_velX;
	}

	public void setVelX(double velX) {
		m_velX = velX;
	}

	public double getVelY() {
		return m_velY;
	}

	public void setVelY(double velY) {
		m_velY = velY;
	}

	public double getWidth() {
		return m_width;
	}

	public void setWidth(double width) {
		m_width = width;
	}

	public double getHeight() {
		return m_height;
	}

	public void setHeight(double height) {
		m_height = height;
	}
}

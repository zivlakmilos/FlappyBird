package net.ddns.zivlak.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Pipe {

	public static final int SPEED			= 100;

	private double m_x;
	private double m_yUp;
	private double m_yDown;
	private double m_width;
	private double m_heightUp;

	Pipe(int holeY, int holeHeight) {

		m_x = 0;
		m_yUp = 0;
		m_yDown = holeY + holeHeight;
		m_heightUp = holeY;
		m_width = 25;
	}

	public void update(Canvas canvas, double tick) {

		m_x -= SPEED * tick;
	}

	public void render(Canvas canvas) {

		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		Rect rectUp = new Rect((int)m_x,
							   (int)m_yUp,
							   (int)(m_x + m_width),
							   (int)(m_yUp + m_heightUp));
		Rect rectDown = new Rect((int)m_x,
								 (int)m_yDown,
								 (int)(m_x + m_width),
								 (int)(m_yDown + canvas.getHeight() - m_yUp - m_heightUp));
		canvas.drawRect(rectUp, paint);
		canvas.drawRect(rectDown, paint);
	}

	public boolean collision(Player player) {

		if(player.getX() + player.getWidth() > m_x && player.getX() < m_x + m_width &&
				(player.getY() < m_heightUp || player.getY() > m_yDown))
			return true;

		return false;
	}

	public double getX() {
		return m_x;
	}

	public void setX(double x) {
		m_x = x;
	}
}

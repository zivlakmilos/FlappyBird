package net.ddns.zivlak.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, Callback, OnClickListener {

	SurfaceHolder m_holder;
	boolean m_running = false;
	Thread m_thread = null;
	long m_timeLast;
	double m_tick;

	Player m_player;

	public GameView(Context context) {
		super(context);

		m_holder = getHolder();
		m_holder.addCallback(this);
		setOnClickListener(this);

		Bitmap playerSprite = BitmapFactory.decodeResource(getResources(), R.drawable.flappy);
		m_player = new Player(playerSprite);
	}

	public void resume() {
	}

	public void pause() {
		m_running = false;
		try {
			m_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m_thread = null;
	}

	@Override
	public void run() {
		m_timeLast = System.currentTimeMillis();
		m_tick = 0;

		init();
		while(m_running) {
			if(!m_holder.getSurface().isValid())
				continue;

			m_tick = (System.currentTimeMillis() - m_timeLast) / 1000.0;
			m_timeLast = System.currentTimeMillis();

			Canvas canvas = m_holder.lockCanvas();
			update(m_tick);
			render(canvas);
			m_holder.unlockCanvasAndPost(canvas);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void init() {
		m_player.setWidth(100);
		m_player.setHeight(100);
		m_player.setX(getWidth() / 2 - m_player.getWidth() / 2);
		m_player.setY(getHeight() / 2 - m_player.getHeight() / 2);
	}

	protected void update(double tick) {
		m_player.update(tick);
	}

	protected void render(Canvas canvas) {
		canvas.drawRGB(0, 0, 0);
		m_player.render(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		m_thread = new Thread(this);
		m_running = true;
		m_thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	public void onClick(View view) {
		m_player.jump();
	}

}

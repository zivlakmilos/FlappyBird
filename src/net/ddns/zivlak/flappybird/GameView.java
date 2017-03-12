package net.ddns.zivlak.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, Callback {

	SurfaceHolder m_holder;
	boolean m_running = false;
	Thread m_thread = null;

	Player m_player;

	public GameView(Context context) {
		super(context);

		m_holder = getHolder();
		m_holder.addCallback(this);

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
		while(m_running) {
			if(!m_holder.getSurface().isValid())
				continue;

			Canvas canvas = m_holder.lockCanvas();
			update();
			render(canvas);
			m_holder.unlockCanvasAndPost(canvas);
		}
	}

	protected void update() {
		m_player.update();
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

}

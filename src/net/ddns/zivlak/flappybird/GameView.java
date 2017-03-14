package net.ddns.zivlak.flappybird;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, Callback, OnClickListener {

	public static final int GAME_STATE_MENU					= 0;
	public static final int GAME_STATE_GAME					= 1;
	public static final int GAME_STATE_END					= 2;

	private SurfaceHolder m_holder;
	private boolean m_running = false;
	private Thread m_thread = null;

	private long m_timeLast;
	private double m_tick;

	private int m_gameState = GAME_STATE_MENU;

	private Player m_player;
	private List<Pipe> m_pipes = new ArrayList<Pipe>();

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
			update(canvas, m_tick);
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

	protected void update(Canvas canvas, double tick) {

		switch(m_gameState) {
		case GAME_STATE_GAME:
			boolean createPipe = false;
			int removePipe = -1;

			for(Pipe pipe : m_pipes) {
				pipe.update(canvas, tick);
				if(pipe.getX() < 0)
					removePipe = m_pipes.indexOf(pipe);
				if(pipe.getX() < canvas.getWidth() / 2 && m_pipes.size() < 2)
					createPipe = true;

				if(pipe.collision(m_player))
					m_gameState = GAME_STATE_END;
			}
			if(removePipe >= 0) {
				m_pipes.remove(removePipe);
			}
			if(createPipe) {
				Random random = new Random(System.currentTimeMillis());
				Pipe p = new Pipe(random.nextInt(getWidth()), (int)m_player.getHeight() * 3);
				p.setX(canvas.getWidth());
				m_pipes.add(p);
			}

			m_player.update(canvas, tick);
			if(m_player.collision(canvas))
				m_gameState = GAME_STATE_END;
			break;
		}
	}

	protected void render(Canvas canvas) {
		canvas.drawRGB(0, 0, 0);
		switch(m_gameState) {
		case GAME_STATE_GAME:
		case GAME_STATE_MENU:
			for(Pipe pipe : m_pipes) {
				pipe.render(canvas);
			}
			m_player.render(canvas);
			break;
		case GAME_STATE_END:
			String text = "Game Over";
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setTextSize(25);
			Rect bounds = new Rect();
			paint.getTextBounds(text, 0, text.length(), bounds);
			canvas.drawText(text,
							canvas.getWidth() / 2 - bounds.width() / 2,
							canvas.getHeight() / 2 - bounds.height() / 2,
							paint);
			break;
		}
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

		switch(m_gameState) {
		case GAME_STATE_GAME:
			m_player.jump();
			break;
		case GAME_STATE_MENU:
			m_gameState = GAME_STATE_GAME;
			Random random = new Random(System.currentTimeMillis());
			Pipe pipe = new Pipe(random.nextInt(getWidth()), (int)m_player.getHeight() * 3);
			pipe.setX(getWidth());
			m_pipes.add(pipe);
			break;
		}
	}

}

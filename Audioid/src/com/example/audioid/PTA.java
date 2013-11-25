package com.example.audioid;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.View;

public class PTA extends Activity {
	MediaPlayer player = new MediaPlayer();
	int max, volume;
	AudioManager audioManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pt);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pt, menu);
		return true;
	}
	
	public void playMusic(View view){
		if(player.isPlaying())
		{
			player.stop();
		}
		
		AssetFileDescriptor afd;
		try {
			afd = getAssets().openFd("riverdance.mp3");
			player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			player.prepare();
			player.start();
			audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volume = max;
			afd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopMusic(View view){
		if(player.isPlaying())
		{
			player.stop();
		}
	}
	
	public void volUpMusic(View view){
		if(player.isPlaying() && volume<max)
		{
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume++, AudioManager.FLAG_PLAY_SOUND);
		}
	}
	
	public void volDownMusic(View view){
		if(player.isPlaying() && volume>0)
		{
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume--, AudioManager.FLAG_PLAY_SOUND);
		}
	}
	
    public void getBack(View view) {
    	if(player.isPlaying())
		{
    		player.stop();
		}
    	finish();
    }
}

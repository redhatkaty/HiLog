package com.pkuhci.hilog.music;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.ShowRecord;

public class ShowMusicRecord extends Activity {
	private ListView listview;
	private MusicHistoryDatabase music_db;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_music_record);
		
		getViews();
		music_db = new MusicHistoryDatabase(this);
		List<String> list = music_db.listMusicHistory();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.adapter_item_1, list);
		listview.setAdapter(adapter);

		Button backButton = (Button) this
				.findViewById(R.id.back);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(
						ShowMusicRecord.this,
						ShowRecord.class);

				startActivity(i);
			}
		});
	

	}

	public void getViews() {
		listview = (ListView) findViewById(R.id.list01);
	}
}

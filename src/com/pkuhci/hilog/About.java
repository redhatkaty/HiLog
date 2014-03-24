package com.pkuhci.hilog;

/**
 * 说明画面
 */
import com.pkuhci.hilog.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class About extends Activity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.about_background_layout);// set the first view to
													// welcome the user
		// 显示软件的说明
		TextView tv = (TextView) this.findViewById(R.id.user_info);
		tv.setText("-----***使用说明***-----"
				+ "\n"+"HiLog掌上仙踪能够方便地自动记录下您每天的生活日志，既不需要任何额外的操作，也不会干扰到手机的正常使用。" +
						"\n\n使用掌上仙踪，可以帮助您:\n1：回忆某一天做过的事情\n2：记录下一天的行程\n3：查找被遗忘的信息\n4：电话自动录音，备份重要对话\n" +
						"5：监控是否有人动用过您的手机"+"\n"+"\n"
				+ "HiLog掌上仙踪可以方便记录下您每天：\n1：拍摄的照片\n2：电话的录音\n3：打过哪些电话\n4：收发过哪些短息\n5：到过什么地方"
				+ "\n6：播放过的音乐\n7：浏览过的网页\n8：在手机上的操作\n9：使用过的手机应用程序\n"
				+ "\n\n-----***操作方法***-----"
				+ "\n1：相关配置：采集默认自动在后台运行，不会影响到您正常使用手机。您也可在“设置”中按照画面上按钮改变相应配置。"
				+ "\n\n2: 查看所有生活日志记录：在最开始的画面选择“查看”，即可看到所有已存的生活日志记录。您可以点击感兴趣的条目，打开相应的记录文件进行查看，或者点击相应按钮，逐条呈现所采集的记录。"
				+ "\n\n3: 所有采集到的信息，包括照片、电话录音等，都统一保存在了您手机SD卡上根目录下名为“HiLog_LifeLog”的文件夹中。您也可以随时将文件传送到电脑上保存、查看。"
				+ "\n\n-----***注意事项***-----"
				+ "\n1：使用本软件记录GPS信息时，耗电量比较大，如果需要长时间使用请您留意手机电池电量。"
				+ "\n\n2: 进行电话录音时，有些手机的电话录音中对方的声音效果可能会不够清晰。"
				+ "\n\n3: 采集音乐播放记录时，只有使用本软件进行播放的音频文件才会被记录下来。" +
				"\n\n4: GPS记录中的参考地点可能不够精准，正在完善中，敬请见谅!" +
				"\n\n-----***开发信息***-----" +"\n\n开发者:PKU-GIL实验室-HCI小组"+
				"\n\n感谢您的使用，并欢迎提出您的宝贵意见至：\npku_hci@hotmail.com"+
				"\n\n期待能够与您交流~"

		);

		// tv.setText("--------***使用说明***--------"+"\n"+"目前可以使用的功能有：\n1：拍照\n2：录音\n3：通话记录\n4：短信记录\n5：GPS记录"
		// +
		// "\n6：音乐播放记录\n7：上网浏览记录\n8：查看已存记录\n9：查看手机设备信息\n10：记录无线传输" +
		// "\n\n--------***操作方法***--------" +
		// "\n1：采集各项信息：按照画面上按钮提示即可" +
		// "\n\n2: 查看所有已存记录：在最开始的画面选择“查看已存Lifelog记录”，即可看到所有已存的记录，包括存在xml格式的文件中的通话记录、短信记录、GPS记录、上网记录、音乐播放记录，"
		// +
		// "以及照片、录音等。这些文件都可以通过手机自带的软件直接点击打开。" +
		// "\n\n3: 将记录文件传输到PC上：首先，在PC端开启文件接收服务，并将手机接入无线网；其次，从起始画面进入“Lifelog记录传输”,点击“网络配置”，输入PC所在的IP地址，点击“确定”；"
		// +
		// "最后，在画面上方点击传输相应文件，或者点击“自行选择文件”自选文件进行传输即可。");
		//

		Button demoButton = (Button) this.findViewById(R.id.back_button);
		demoButton.setOnClickListener(new OnClickListener() {
				
			public void onClick(View arg0) {

				Intent i = new Intent(About.this, WelcomeScreen.class);

				startActivity(i);
			}
		});

	}

}

package com.test.chuanyi.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;



public class LoadingDialog extends Dialog {

	private Context context;

	public LoadingDialog(Context context) {
		this(context, R.style.progressdialogstyle,R.layout.dialog_loading);
	}
	
	public LoadingDialog(Context context, int theme) {
		this(context,R.style.progressdialogstyle,R.layout.dialog_loading);
	}

	protected LoadingDialog(Context context, int theme, int resLayout) {
		super(context,R.style.progressdialogstyle);
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.dialog_loading);
		ImageView img_loading = (ImageView)findViewById(R.id.img_load);
		RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils
				.loadAnimation(context, R.anim.dialog_refresh);
		img_loading.setAnimation(rotateAnimation);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}
	
}

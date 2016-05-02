package cn.efunding.fund.common;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer{
	
	private Button checking;
	
	private String name;

	public TimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		checking.setText(getName());
		checking.setClickable(true);
		
	}

	@Override
	public void onTick(long arg0) {
		// TODO Auto-generated method stub
		checking.setClickable(false);
		checking.setText(arg0 /1000+"秒");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Button getChecking() {
		return checking;
	}

	public void setChecking(Button checking) {
		this.checking = checking;
	}
	
	
}

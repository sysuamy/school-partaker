package com.zx.pinke.widget.xdialog;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.zx.pinke.R;
import com.zx.pinke.lisnter.OnDateTimeChangeLisnter;
import com.zx.pinke.util.DateUtil;
/**
 * 日期时间选择控件 
 * @author troy bee
 */
public class DateTimePickerDialog implements  OnDateChangedListener,OnTimeChangedListener{
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private long time;
	private String initDateTime;
	
	/**
	 * 日期时间弹出选择框构
	 * @param activity：调用的父activity
	 */
	public DateTimePickerDialog(Activity activity,final OnDateTimeChangeLisnter onDateTimeChangeLisnter,Calendar calendar)
	{
		LinearLayout dateTimeLayout  = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		init(datePicker,timePicker,calendar);
				
		ad = new AlertDialog.Builder(activity).setTitle(initDateTime).setView(dateTimeLayout).setPositiveButton("完成",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								onDateTimeChangeLisnter.OnDateTimeChange(time);
							}
						}).create();
		onDateChanged(null, 0, 0, 0);
	}
	
	public void init(DatePicker datePicker,TimePicker timePicker,Calendar calendar)
	{
		initDateTime=DateUtil.getDateTimeStr(new Date(calendar.getTimeInMillis()));
		datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), this);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
		timePicker.setIs24HourView(false);
		timePicker.setOnTimeChangedListener(this);
	}
		
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
	{
		onDateChanged(null, 0, 0, 0);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute());
		dateTime=DateUtil.getDateTimeStr(new Date(calendar.getTimeInMillis()));
		ad.setTitle(dateTime);
		time=calendar.getTimeInMillis();
	}
	
	public void show() {
		ad.show();	
	}
	
	public void setOnKeyListener(OnKeyListener onKeyListener) {
		ad.setOnKeyListener(onKeyListener);
	}
			
}
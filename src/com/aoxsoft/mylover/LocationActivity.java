/**
 * 
 */
package com.aoxsoft.mylover;

import junit.framework.Test;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnGeofenceTriggerListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;
import com.baidu.location.LocationClientOption.LocationMode;


/**
 * @author KKJ
 *
 */
public class LocationActivity extends Activity {

	private LocationMode _mode = LocationMode.Hight_Accuracy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener(myListener);    //注册监听函数
		InitLocation();
		RadioGroup selectModeGroup =(RadioGroup) findViewById(R.id.selectMode);
		selectModeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_hight:
					_mode = LocationMode.Hight_Accuracy;
					break;
				case R.id.radio_low:
					_mode = LocationMode.Battery_Saving;
					break;
				case R.id.radio_device:
					_mode = LocationMode.Device_Sensors;
					break;
				default:
					break;
				}
				InitLocation();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Toast.makeText(this, "This is a Toast!", Toast.LENGTH_SHORT).show();
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new Button.OnClickListener() 
		{
			   public void onClick(View v)
			   {
				   Toast.makeText(LocationActivity.this, "Begin to run baidu location!", Toast.LENGTH_SHORT).show();
				   if(mLocationClient.isStarted())
				   {
					   return;
				   }
				   else
				   {
					   mLocationClient.start();
				   }

			   }
		});
		Button btn1 = (Button)findViewById(R.id.button2);
		btn1.setOnClickListener(new Button.OnClickListener() 
		{
			   public void onClick(View v)
			   {
				   Toast.makeText(LocationActivity.this, "Begin to stop baidu location!", Toast.LENGTH_SHORT).show();
				   if(mLocationClient.isStarted())
				   {
					   mLocationClient.stop();
				   }
				   else
				   {
					  return;
				   }

			   }
		});
	}
	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	public class MyLocationListener implements BDLocationListener {
	
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			TextView tViewLat  = (TextView)findViewById(R.id.txtLat);
			TextView tViewLng  = (TextView)findViewById(R.id.txtLng);
			TextView tViewAddress =  (TextView)findViewById(R.id.txtAddress);
			if (location == null)
		            return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			tViewLat.setText(Double.toString(location.getLatitude()));
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			tViewLng.setText(Double.toString(location.getLongitude()));
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				tViewAddress.setText(location.getAddrStr());
			} 
			//Toast.makeText(LocationActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
			//Log.i("Test", sb.toString());
		}
	}
	
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(_mode);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onDestroy();
	}
	
}


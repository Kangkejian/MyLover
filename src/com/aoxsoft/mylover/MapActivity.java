package com.aoxsoft.mylover;

import com.aoxsoft.mylover.LocationActivity.MyLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// REMARK 初始化必须在绑定界面之前
		SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapView);  
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener(myListener);    //注册监听函数
		InitLocation();
		mBaiduMap = mMapView.getMap(); 
		mBaiduMap.setMyLocationEnabled(true);
		mLocationClient.start();
	}
	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	public class MyLocationListener implements BDLocationListener {
	
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
		            return ;
			LatLng point = new LatLng(location.getLatitude(),location.getLongitude());  
			//Toast.makeText(MapActivity.this, Double.toString(location.getLatitude())+","+Double.toString(location.getLongitude()), Toast.LENGTH_SHORT).show();
			//构建Marker图标  
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.love_mark);  
			Bundle bdBundle = new Bundle();
			//bdBundle.putString(key, value)
			//构建MarkerOption，用于在地图上添加Marker  
			OverlayOptions option = new MarkerOptions()  
			    .position(point)
			    .title(location.getAddrStr())
			    .icon(bitmap)
			    .extraInfo(bdBundle);
			    ;
			 
			//在地图上添加Marker，并显示  
			mBaiduMap.addOverlay(option);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
			mBaiduMap.animateMapStatus(u);  
			
		}
	}
	
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();
        if(mLocationClient!=null)
        	mLocationClient.stop();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume(); 
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
}



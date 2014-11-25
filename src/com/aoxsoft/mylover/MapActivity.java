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
		// REMARK ��ʼ�������ڰ󶨽���֮ǰ
		SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapView);  
		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
		mLocationClient.registerLocationListener(myListener);    //ע���������
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
			//����Markerͼ��  
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.love_mark);  
			Bundle bdBundle = new Bundle();
			//bdBundle.putString(key, value)
			//����MarkerOption�������ڵ�ͼ�����Marker  
			OverlayOptions option = new MarkerOptions()  
			    .position(point)
			    .title(location.getAddrStr())
			    .icon(bitmap)
			    .extraInfo(bdBundle);
			    ;
			 
			//�ڵ�ͼ�����Marker������ʾ  
			mBaiduMap.addOverlay(option);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
			mBaiduMap.animateMapStatus(u);  
			
		}
	}
	
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
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
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onDestroy();
        if(mLocationClient!=null)
        	mLocationClient.stop();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume(); 
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onPause();  
        }  
}



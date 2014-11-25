package com.aoxsoft.mylover;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {

	private ListView FunctionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	FunctionList = (ListView)findViewById(R.id.functionList);
		FunctionList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
    }

  
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		FunctionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Class TargetClass = null; 
				switch (arg2) {
				case 0:
					TargetClass = LocationActivity.class;
					break;
				case 1:
					TargetClass = MapActivity.class;
					break;
				case 2:
					TargetClass = null;
					break;
				case 3 :
					TargetClass = null;
					break;
				default:
					break;
				}
				if(TargetClass != null){
					Intent intent = new Intent(MainActivity.this, TargetClass);
					startActivity(intent);
				}
			}
		});
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private List<String> getData(){
        
        List<String> data = new ArrayList<String>();
        data.add("������λ����");
        data.add("����Χ������");
        data.add("λ����Ϣ����");
        data.add("��������˵��");
         
        return data;
    }
}

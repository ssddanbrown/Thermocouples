package com.southerntemp.thermocouples;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Info extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_info);
		// Show the Up button in the action bar.
        Toolbar toolbar = (Toolbar)findViewById(R.id.tcholder_toolbar);
		setSupportActionBar(toolbar);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		final Button stslogo = (Button)findViewById(R.id.info_button_sponsor);
		stslogo.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse("http://southerntemp.co.uk"));
		        startActivity(intent);
			}
		});
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, TcHolder.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            Info.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.removeItem(R.id.InfoItem);
		menu.removeItem(R.id.CalculatorItem);
	    return true;
	}
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	

}

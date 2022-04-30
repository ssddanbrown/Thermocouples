package com.southerntemp.thermocouples;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_info);
		// Show the Up button in the action bar.
        Toolbar toolbar = findViewById(R.id.tcholder_toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		final Button stslogo = findViewById(R.id.info_button_sponsor);
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
                this.finish();
                overridePendingTransition(0, R.anim.push_left_out);
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

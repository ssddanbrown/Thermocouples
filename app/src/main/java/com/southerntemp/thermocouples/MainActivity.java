package com.southerntemp.thermocouples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.southerntemp.thermocouples.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            if (i == R.id.SearchItem) return goToActivity(SearchActivity.class);
            if (i == R.id.CalculatorItem) return goToActivity(CalcActivity.class);
            if (i == R.id.InfoItem) return goToActivity(InfoActivity.class);
            return false;
        });
    }

    protected boolean goToActivity(Class<? extends Activity> activity) {
        Intent intentSearch = new Intent(this, activity);
        startActivity(intentSearch);
//        DetailsActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        return true;
    }

}
package com.southerntemp.thermocouples;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
            if (i == R.id.SearchItem) return goToFragment(new SearchFragment());
            if (i == R.id.CalculatorItem) return goToFragment(new CalcFragment());
            if (i == R.id.InfoItem) return goToFragment(new InfoFragment());
            if (i == R.id.DetailsItem) return goToFragment(new DetailsFragment());
            return false;
        });
    }

    protected boolean goToFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tr = manager.beginTransaction();
        tr.replace(R.id.main_activity_fragment_container, fragment);
        tr.commit();
        return true;
    }
}
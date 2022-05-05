package com.southerntemp.thermocouples;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.southerntemp.thermocouples.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {

	private FragmentInfoBinding binding;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState
	) {

		binding = FragmentInfoBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		final Button stsLogoButton = binding.infoButtonSponsor;
		stsLogoButton.setOnClickListener(v -> {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse("http://southerntemp.co.uk"));
			startActivity(intent);
		});
	}

}

package com.southerntemp.thermocouples;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TcDetailsFragment extends Fragment {

    ImageView tcImage;
    static TcRepo tcRepo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);

        // Array Position is received here
        Bundle bundle = getArguments();
        int id = bundle.getInt("ID");

        if (tcRepo == null) tcRepo = TcRepo.getInstance(getContext());
        Thermocouple tc = tcRepo.getThermocoupleAt(id);

        // Title is written to TextView
        TextView detailTitle = v.findViewById(R.id.tcTitle);
        detailTitle.setText(String.format("Type %s Thermocouple", tc.type));

        // Tab setup
        String[] standards = {"IEC", "BS", "ANSI", "NFE", "DIN", "JIS"};
        RadioButton[] rButtons = {
                v.findViewById(R.id.tcDetailRBiec),
                v.findViewById(R.id.tcDetailRBbs),
                v.findViewById(R.id.tcDetailRBansi),
                v.findViewById(R.id.tcDetailRBnfe),
                v.findViewById(R.id.tcDetailRBdin),
                v.findViewById(R.id.tcDetailRBjis)
        };

        tcImage = v.findViewById(R.id.tcDetailImageView);

        boolean selected = false;
        for (int i = 0; i < standards.length; i++) {
            String standard = standards[i];
            TcColor color = tc.colors.get(standard);
            if (color == null) continue;

            RadioButton btn = rButtons[i];
            setupTab(btn, color.drawable, i, tc);
            int selectedIndex = tc.getSelectedViewIndex();

            if (selectedIndex != -1 && selectedIndex == i) {
                btn.performClick();
                selected = true;
            }

            // Select first button with content
            if (!selected && selectedIndex == -1) {
                btn.performClick();
                selected = true;
            }
        }

        // Thermocouple text is written to views
        ((TextView)v.findViewById(R.id.tcPositiveLeg)).setText(tc.pLeg);
        ((TextView)v.findViewById(R.id.tcNegativeLeg)).setText(tc.nLeg);
        ((TextView)v.findViewById(R.id.tcTempRange)).setText(String.format("%d to %d°C", tc.minTemp, tc.maxTemp));

        TextView info = v.findViewById(R.id.tcExtra);
        View infoWrap = v.findViewById(R.id.tcExtraWrap);
        View sep = v.findViewById(R.id.tcExtraSep);

        int visibility = tc.info.length() > 0 ? View.VISIBLE : View.GONE;
        info.setText(tc.info);
        infoWrap.setVisibility(visibility);
        sep.setVisibility(visibility);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void setupTab(final RadioButton button, final int image, final int tabIndex, final Thermocouple tc) {
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup group = (RadioGroup) button.getParent();
                tc.setSelectedViewIndex(tabIndex);
                group.clearCheck();
                Log.i("CLICK", button.getText().toString());
                button.setChecked(true);
                final String imageKey = String.valueOf(image);
                final Bitmap bitmap = getBitmapFromMemCache(imageKey);
                if (bitmap != null) {
                    tcImage.setImageBitmap(bitmap);
                    addBitmapToMemoryCache(imageKey, bitmap);
                } else {
                    BitmapWorkerTask task = new BitmapWorkerTask(tcImage);
                    task.execute(image);
                }
            }
        });
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            DetailsActivity.mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return DetailsActivity.mMemoryCache.get(key);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView specImg) {
            imageViewReference = new WeakReference<ImageView>(specImg);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            int tcarray = params[0];
            String tctest = (String.valueOf(tcarray));
            Bitmap bitmap = decodeSampledBitmapFromResource(
                    getResources(), tcarray);
            addBitmapToCache(tctest, bitmap);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    public void addBitmapToCache(String key, Bitmap bitmap) {
        // Add to memory cache as before
        if (getBitmapFromMemCache(key) == null) {
            DetailsActivity.mMemoryCache.put(key, bitmap);
        }
    }

}

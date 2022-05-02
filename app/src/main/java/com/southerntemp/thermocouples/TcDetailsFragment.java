package com.southerntemp.thermocouples;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TcDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);
        Context ctx = this.getContext();

        // Thermocouple type index is received here
        Bundle bundle = getArguments();
        int id = bundle.getInt("ID");

        TcRepo tcRepo = TcRepo.getInstance(ctx);
        Thermocouple tc = tcRepo.getThermocoupleAt(id);

        // Title is written to TextView
        TextView detailTitle = v.findViewById(R.id.tcTitle);
        detailTitle.setText(String.format("Type %s", tc.type));

        // Thermocouple text is written to views
        ((TextView)v.findViewById(R.id.tcPositiveLeg)).setText(tc.pLeg);
        ((TextView)v.findViewById(R.id.tcNegativeLeg)).setText(tc.nLeg);
        ((TextView)v.findViewById(R.id.tcTempRange)).setText(String.format("%d to %d°C", tc.minTemp, tc.maxTemp));
        ((TextView)v.findViewById(R.id.tcExtra)).setText(tc.info);

        // Set extra detail view to hidden/visible based upon content
        View infoWrap = v.findViewById(R.id.tcExtraWrap);
        infoWrap.setVisibility(tc.info.length() > 0 ? View.VISIBLE : View.GONE);

        // Create thermocouple reference list for each supported standard.
        LinearLayout tcImageList = v.findViewById(R.id.tcImageList);
        for (TcColor color : tc.colors.values()) {
            LinearLayout standardRow = new LinearLayout(ctx, null, 0, R.style.tcStandardRow);
            TextView standardTextView = new TextView(ctx, null, 0, R.style.tcStandardLabel);
            FrameLayout imageWrap = new FrameLayout(ctx, null, 0, R.style.tcStandardImageWrap);
            ImageView standardImageView = new ImageView(ctx, null, 0, R.style.tcStandardImage);
            standardTextView.setText(color.standard);
            standardRow.addView(standardTextView);
            standardRow.addView(imageWrap);
            tcImageList.addView(standardRow);
            imageWrap.addView(standardImageView);
            this.renderThermocoupleImage(standardImageView, color.drawable);
        }

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

    public void renderThermocoupleImage(ImageView imageView, int image) {
        final String imageKey = String.valueOf(image);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            addBitmapToMemoryCache(imageKey, bitmap);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(image);
        }
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
            if (bitmap != null) {
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

package nl.mprog.setup.scrooge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class ComparisonActivity extends Activity {
    private double startLat, startLng, endLat, endLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);
        
        Intent mIntent = getIntent();
        startLat = mIntent.getDoubleExtra("startLat", 0.0);
        startLng = mIntent.getDoubleExtra("startLng", 0.0);
        endLat = mIntent.getDoubleExtra("endLat", 0.0);
        endLng = mIntent.getDoubleExtra("endLng", 0.0);
    }
}

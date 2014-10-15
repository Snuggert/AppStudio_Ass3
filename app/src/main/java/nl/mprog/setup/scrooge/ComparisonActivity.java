package nl.mprog.setup.scrooge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ComparisonActivity extends Activity {
    private double startLat, startLng, endLat, endLng;
    private final ArrayList<TaxiCompany> taxiCompanies =
            new ArrayList<TaxiCompany>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        taxiCompanies.add(new CompanyUBER());

        Intent mIntent = getIntent();
        startLat = mIntent.getDoubleExtra("startLat", 0.0);
        startLng = mIntent.getDoubleExtra("startLng", 0.0);
        endLat = mIntent.getDoubleExtra("endLat", 0.0);
        endLng = mIntent.getDoubleExtra("endLng", 0.0);
    }
}

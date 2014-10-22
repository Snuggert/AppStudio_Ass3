package nl.mprog.scrooge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;

import nl.mprog.setup.scrooge.R;


public class ComparisonActivity extends Activity {
    private double startLat, startLng, endLat, endLng;
    private final ArrayList<TaxiCompany> taxiCompanies =
            new ArrayList<TaxiCompany>();

    private GridView gridView;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        taxiCompanies.add(new TaxiCompanyUBER());
        taxiCompanies.add(new TaxiCompanyTCA());

        Intent mIntent = getIntent();
        startLat = mIntent.getDoubleExtra("startLat", 0.0);
        startLng = mIntent.getDoubleExtra("startLng", 0.0);
        endLat = mIntent.getDoubleExtra("endLat", 0.0);
        endLng = mIntent.getDoubleExtra("endLng", 0.0);

        imageAdapter = new ImageAdapter(this);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            }
        });

        for(int index = 0; index < taxiCompanies.size(); index++){
            taxiCompanies.get(index).getRidePrice(new LatLng(startLat, startLng),
                    new LatLng(endLat, endLng),
                    this);
        }
    }

    public void setNewItem(GridData company){
        imageAdapter.addItem(company);
        imageAdapter.notifyDataSetChanged();
    }
}

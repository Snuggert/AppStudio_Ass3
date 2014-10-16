package nl.mprog.setup.scrooge;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by TheAbe on 15-Oct-14.
 */
public class CompanyUBER extends TaxiCompany {
    private final String uberUrl = "https://api.uber.com/v1/estimates/price";
    private final String serverToken =
            "6k4OJN0UC-c4ZT97CoPxYxLScBTlU0d4RSoenuZQ";

    public CompanyUBER(){
        this.name = "UBER";
    }

    @Override
    public String getRidePrice(LatLng Start, LatLng End, Context context){
        final Context finalContext = context;
        JsonObject json = new JsonObject();
        json.addProperty("server_token", serverToken);
        json.addProperty("start_latitude", Start.latitude);
        json.addProperty("start_longitude", Start.longitude);
        json.addProperty("end_latitude", End.latitude);
        json.addProperty("end_longitude", End.longitude);

        String getRequest = "?server_token=" + serverToken + "&"
                          + "start_latitude=" + Start.latitude + "&"
                          + "start_longitude=" + Start.longitude + "&"
                          + "end_latitude=" + End.latitude + "&"
                          + "end_longitude=" + End.longitude;

        Ion.with(context).load("GET", uberUrl + getRequest)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null)
                            // do stuff with the result or error
                            Toast.makeText(finalContext, e.toString(),
                                           Toast.LENGTH_SHORT).show();
                        if(!result.isJsonNull())
                            Log.v("InternetShizzle", result.toString());
                        Log.v("InternetShizzle", "Klaar");
                    }
                });
        return "Still Testing";
    }

}

package nl.mprog.setup.scrooge;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
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
        JsonObject json = new JsonObject();
        json.addProperty("foo", "bar");

        Ion.with(context).load("http://example.com/post")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                    }
                });
    }

}

package nl.mprog.scrooge;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class TaxiCompanyUBER extends TaxiCompany {
    private final String uberUrl = "https://api.uber.com/v1/estimates/price";
    private final String serverToken =
            "6k4OJN0UC-c4ZT97CoPxYxLScBTlU0d4RSoenuZQ";

    public TaxiCompanyUBER(){
        this.name = "UBER";
    }

    @Override
    public void getRidePrice(LatLng start, LatLng end, Context context) {
        final ComparisonActivity comparisonContext = (ComparisonActivity) context;

        String getRequest = "?server_token=" + serverToken + "&"
                + "start_latitude=" + start.latitude + "&"
                + "start_longitude=" + start.longitude + "&"
                + "end_latitude=" + end.latitude + "&"
                + "end_longitude=" + end.longitude;

        JsonArray resultArray = new JsonArray();

        Ion.with(context).load("GET", uberUrl + getRequest)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    if (e != null)
                        // do stuff with the result or error
                        Log.v("IonError", e.toString());
                    else if (!result.isJsonNull()) {
                        /*
                         * Put result into JsonArray, because this is what
                         * UBER returns.
                         */
                        JsonArray resultArray = result.getAsJsonArray("prices");

                        /*
                         * Iterate over the prices given by UBER.
                         */
                        for (int index = 0; index < resultArray.size();
                        index++) {
                        comparisonContext.setNewItem(
                            jsonToGrid(resultArray.get(index)
                                    .getAsJsonObject()));
                        }

                    }
                }
            });
    }

    /*
     * Function to read a UBER specific json file into a GridData object.
     */
    private GridData jsonToGrid(JsonObject object){
        return new GridData(
        /* To lowercase for usage of drawable name */
                object.getAsJsonPrimitive("display_name").getAsString(),
                object.getAsJsonPrimitive("estimate").getAsString()
        );
    }
}

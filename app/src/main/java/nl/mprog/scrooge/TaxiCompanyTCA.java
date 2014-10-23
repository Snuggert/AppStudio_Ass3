package nl.mprog.scrooge;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaxiCompanyTCA extends TaxiCompany  {
    final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    final String googleDistanceUrl =
            "http://maps.googleapis.com/maps/api/distancematrix/json";
    final String tcaUrlOne = "http://m.taxi4me.net/mobile/account.js";
    final String tcaUrlTwo = "http://m.taxi4me.net/mobile/sofortantwort.js";

    public TaxiCompanyTCA(){
        this.name = "TCA";
    }

    @Override
    public void getRidePrice(LatLng start, LatLng end, Context context) {
        final ComparisonActivity comparisonContext =
                (ComparisonActivity) context;

        String getRequest = buildRequest(start, end, context);
        String TCAReturnOne = null;
        String TCAReturnTwo = null;

        final String accountPattern = "MBACCOUNT: \\'(.+?)\\'";
        final String sessionPattern = "setSessionId\\(\\'(.*)\\'\\)";

        final String preisPattern = "\\\"preis\\_betrag\\\"\\: \\\"(.*)\\\"\\,";

        try{
            TCAReturnOne = Ion.with(context)
                                     .load("GET", tcaUrlOne + getRequest)
                                     .setHandler(null)
                                     .asString()
                                     .get();
        }catch(java.lang.InterruptedException e){
            Log.v("routeError", e.toString());
        }catch(ExecutionException e){
            Log.v("routeError", e.toString());
        }
        if(TCAReturnOne != null){
            Pattern p = Pattern.compile(accountPattern);
            Matcher m = p.matcher(TCAReturnOne); // get a matcher object
            if(m.find()) {
                getRequest = getRequest + "&mbaccount=" +  m.group(1);
            }
            p = Pattern.compile(sessionPattern);
            m = p.matcher(TCAReturnOne);
            if(m.find()) {
                getRequest = getRequest + "&sessionid=" +  m.group(1);
            }

            try{
                TCAReturnTwo = Ion.with(context)
                        .load("GET", tcaUrlTwo + getRequest)
                        .setHandler(null)
                        .asString()
                        .get();
            }catch(java.lang.InterruptedException e){
                Log.v("routeError", e.toString());
            }catch(ExecutionException e){
                Log.v("routeError", e.toString());
            }
            if(TCAReturnTwo != null){
                p = Pattern.compile(preisPattern);
                m = p.matcher(TCAReturnTwo);
                if(m.find()){
                    comparisonContext.setNewItem(
                            new GridData(name, "€" + m.group(1)));
                }
            }
        }
    }

    private String buildRequest(LatLng start, LatLng end, Context context){
        Address startAddress = geoLocation(start, context);
        Address endAddress = geoLocation(end, context);

        String startString = startAddress.getAddressLine(0) + " " +
                             startAddress.getAddressLine(1);

        String endString = endAddress.getAddressLine(0) + " " +
                           endAddress.getAddressLine(1);

        JsonObject route = route(startString, endString, context);

        String getRequest = "?acc_action=" + "get&"
                + "acc_fromdevice=" + 1 + "&"
                + "deviceid=" + Uri.encode("You guys write terrible apis") + "&"
                + "version=" + Uri.encode("3.48 (11.01.2012 14+47)") + "&"
                + "platform=" + "ANDROID&"
                + "zentralen_kuerzel=" + 7777 + "&"
                + "sprachcode="+ "nl"+ "&"
                + "maporder="+ 1 + "&"
                + "termin=" + "sofort" + "&"
                + "_=" + Uri.encode(GetUTCdatetimeAsString()) + "&"
                + "strecke=" + route
                    .getAsJsonObject("distance")
                    .getAsJsonPrimitive("value")
                    .getAsInt() + "&"
                + "fahrzeit=" + route
                    .getAsJsonObject("distance")
                    .getAsJsonPrimitive("value")
                    .getAsInt() + "&"
                + "A.fix="+ "" + "&"
                + "A.gpsadr_y=" + start.latitude + "&"
                + "A.gpsadr_x=" + start.longitude + "&"
                + "A.ort_ortname=" + startAddress.getLocality() + "&"
                + "A.strasse=" + Uri.encode(startAddress.getThoroughfare()) + "&"
                + "A.hausnummer_ecke=" + Uri.encode(startAddress
                    .getAddressLine(0)) + "&"
                + "A.staat=" + "NL" + "&"
                + "A.plz=" + Uri.encode(startAddress.getPostalCode()) + "&"
                + "Z.setzen=" + 1 + "&"
                + "Z.fix=" + 0 + "&"
                + "Z.gpsadr_y=" + end.latitude + "&"
                + "Z.gpsadr_x="+ end.longitude + "&"
                + "Z.ort_ortname=" + Uri.encode(endAddress.getLocality()) + "&"
                + "Z.strasse=" + Uri.encode(endAddress.getThoroughfare()) + "&"
                + "Z.plz=" + Uri.encode(endAddress.getPostalCode()) + "&"
                + "Z.staat=" + "NL" + "&"
                + "Z.hausnummer_ecke=" + Uri.encode(endAddress
                    .getAddressLine(0)) + "&"
                + "fld_merkal=" + 144 + "&"
                + "afart=" + 1 + "&"
                + "afart_alt=" + 1 + "&"
                + "nur_fahrpreis=" + 1;

        return getRequest;
    }

    /*
     * Method that uses the google GeoCoder api to extrapolate an address
     * out of a gps coordinat, this returns the first option out of a list of
     * results.
     */
    private Address geoLocation(LatLng x, Context context) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(x.latitude, x.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0);
    }

    /*
     * Method that returns the distance between two adresses, return as
     * JsonObject that holds the distance and duration of the trip.
     */
    private JsonObject route(String start, String end, Context context){
        String getRequest = "?origins=" + Uri.encode(start) + "&" +
                            "destinations=" + Uri.encode(end);
        try {
            return Ion.with(context).load("GET", googleDistanceUrl + getRequest)
                                    .asJsonObject().get()
                                    .getAsJsonArray("rows").get(0)
                                    .getAsJsonObject()
                                    .getAsJsonArray("elements").get(0)
                                    .getAsJsonObject();
        }catch(java.lang.InterruptedException e){
            Log.v("routeError", e.toString());
        }catch(ExecutionException e){
            Log.v("routeError", e.toString());
        }
        return null;
    }

    private String GetUTCdatetimeAsString()
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());

    }

}

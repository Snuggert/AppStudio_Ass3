package nl.mprog.setup.scrooge;

import com.google.android.gms.maps.model.LatLng;

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
    public String getRidePrice(LatLng Start, LatLng End){
        return "Anus";
    }

}

package nl.mprog.scrooge;

/*
 * Abe Wiersma
 * 10433120
 * abe.wiersma@hotmail.nl
 */

import com.google.gson.JsonObject;

/* Class to store data assigned to a tile bitmap. */
public class GridData {
        public String Name;
        public String Price;
    public GridData(String Name, String Price) {
        this.Name = Name;
        this.Price = Price;
    }
}

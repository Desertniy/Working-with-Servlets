/*
 * this code is available under GNU GPL v3
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package info.stepanoff.trsis.lab1.model;


import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Pavel.Stepanov
 */

@Component
public class DataModel {
    public class RentalProperty {
        private String address;
        private int monthlyRent;
        private double price;

        public RentalProperty(String address, int monthlyRent, double price) {
            this.address = address;
            this.monthlyRent = monthlyRent;
            this.price = price;
        }

        public String getAddress() {
            return address;
        }


        public int getMonthlyRent() {
            return monthlyRent;
        }

        public double getPrice(){ return price; }

        public void put(double price, int monthlyrent){
            this.price = price;
            this.monthlyRent = monthlyrent;
        }

    }

    public volatile HashMap<String, RentalProperty> properties = new HashMap<>();
    public void addProperty(String address, int monthlyRent, double price) {
        properties.put(address, new RentalProperty(address, monthlyRent, price));
    }

    public void remove(String address){
        properties.remove(address);
    }

    public void put_obj(String address, double price, int monthlyrent){
        RentalProperty rp = properties.get(address);
        if (rp != null)
            rp.put(price, monthlyrent);
        else
            properties.put(address, new RentalProperty(address, monthlyrent, price));
    }

    public Set<Map.Entry<String, RentalProperty>> getObjects(){
        return properties.entrySet();
    }

}

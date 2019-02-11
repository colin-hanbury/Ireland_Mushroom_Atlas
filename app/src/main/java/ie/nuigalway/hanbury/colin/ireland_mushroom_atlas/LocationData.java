package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

public class LocationData {

    public double longitude;
    public double latitude;
    //public int numBluetoothDevices;

    public LocationData() {

    }
    // latitude, longitude, number of bluetooth devices
    public LocationData(double lati, double longi) {
        this.longitude = longi;
        this.latitude = lati;
        //this.numBluetoothDevices = BTDs;
    }

    //getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double lat){ this.latitude = lat;}

    public void setLongitude(double lon){ this.longitude = lon;}
    //public int getNumBluetoothDevices() { return numBluetoothDevices; }

    //setter
    //public void setNumBluetoothDevices(int newNumber){this.numBluetoothDevices = newNumber;}
}

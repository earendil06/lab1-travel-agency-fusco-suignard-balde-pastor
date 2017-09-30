public class HotelRental {
    private String name;
    private String place;
    private String date;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public HotelRental(String name, String place, String date) {
        this.name = name;
        this.place = place;
        this.date = date;
    }

    @Override
    public String toString() {
        return "HotelRental{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

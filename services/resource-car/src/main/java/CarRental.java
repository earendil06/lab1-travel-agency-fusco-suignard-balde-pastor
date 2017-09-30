public class CarRental {
    private String name;
    private String place;
    private String duration;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getDuration() {
        return duration;
    }

    public CarRental(String name, String place, String duration) {
        this.name = name;
        this.place = place;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CarRental{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}

package fr.unice.groupe4.flows.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.unice.groupe4.flows.data.otherdata.OtherCar;
import fr.unice.groupe4.flows.data.ourdata.Car;
import fr.unice.groupe4.flows.data.traveldata.TravelCar;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarHelper {
    public String buildGetCarForTravel(TravelCar car) {
        StringBuilder builder = new StringBuilder();
        builder.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
        builder.append(" <soap:Body>\n");
        builder.append("  <getCarByPlace xmlns=\"http://service.planner/\">\n");
        builder.append("    <place xmlns=\"\">" + car.getPlace() + "</place>\n");
        builder.append("    <duration xmlns=\"\">" + car.getDuration() + "</duration>\n");
        builder.append("  </getCarByPlace>\n");
        builder.append(" </soap:Body>");
        builder.append("</soap:Envelope>");
        return builder.toString();
    }

    public String buildGetCarForTravelOther(TravelCar car) {
        StringBuilder builder = new StringBuilder();
        builder.append("?")
                .append("location=").append(car.getPlace());
        return builder.toString();
    }

    public static AggregationStrategy mergeCars = (oldExchange, newExchange) -> {
        if (oldExchange == null) {
            return newExchange;
        } else {
            TravelCar otherCar = newExchange.getIn().getBody(TravelCar.class);
            TravelCar car = oldExchange.getIn().getBody(TravelCar.class);
            if (car == null && otherCar == null) {
                System.out.println("ALL CARS NULL");
                oldExchange.getIn().setBody(new TravelCar());
                return oldExchange;
            }
            if (car == null || otherCar.getPrice() < car.getPrice()) {
                oldExchange.getIn().setBody(otherCar);
            } else {
                oldExchange.getIn().setBody(car);
            }
        }
        return oldExchange;
    };

    public static Processor result2Car = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String response = exc.getIn().getBody(String.class);
        InputSource source = new InputSource(new StringReader(response));
        String jsonArray = xpath.evaluate("//car_planner_result", source);
        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
        List<Car> cars;
        try {
            cars = new Gson().fromJson(jsonArray, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("NO CARS");
            return;
        }
        if (cars == null || cars.isEmpty()) {
            System.out.println("NO CARS");
            return;
        }
        //get the min price
        Car min = cars.stream().min(Comparator.comparingInt(Car::getPrice)).get();
        TravelCar minCar = new TravelCar(min);
        exc.getIn().setBody(minCar);
    };

    public static Processor result2OtherCar = (Exchange exc) -> {
        int duration = (int) exc.getProperty("duration");
        String response = exc.getIn().getBody(String.class);
        Type listType = new TypeToken<ArrayList<OtherCar>>() {
        }.getType();
        List<OtherCar> cars;
        try {
            cars = new Gson().fromJson(response, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(e + " NO CARS " + response);
            return;
        }
        if (cars == null || cars.isEmpty()) {
            System.out.println("NO CARS");
            return;
        }
        OtherCar carMin = cars.stream().min(Comparator.comparingDouble(OtherCar::getPrice)).get();
        TravelCar min = new TravelCar(carMin);
        min.setDuration(duration);
        min.setPrice(min.getDuration() * min.getPrice()); // OtherCar is price per day
        exc.getIn().setBody(min);
    };
}

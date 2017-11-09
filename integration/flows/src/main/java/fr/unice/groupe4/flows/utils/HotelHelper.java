package fr.unice.groupe4.flows.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.unice.groupe4.flows.data.otherdata.OtherHotel;
import fr.unice.groupe4.flows.data.ourdata.Hotel;
import fr.unice.groupe4.flows.data.traveldata.TravelHotel;
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
import java.util.Map;

public class HotelHelper {

    public String buildGetHotelForTravel(TravelHotel hotel) {
        StringBuilder builder = new StringBuilder();
        builder.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
        builder.append(" <soap:Body>\n");
        builder.append("  <getHotelsForTravel xmlns=\"http://service.planner/\">\n");
        builder.append("    <place xmlns=\"\">" + hotel.getPlace() + "</place>\n");
        builder.append("    <dateArrival xmlns=\"\">" + hotel.getDateArrival() + "</dateArrival>\n");
        builder.append("    <dateDeparture xmlns=\"\">" + hotel.getDateDeparture() + "</dateDeparture>\n");
        builder.append("  </getHotelsForTravel>\n");
        builder.append(" </soap:Body>");
        builder.append("</soap:Envelope>");
        return builder.toString();
    }

    public String buildGetHotelForTravelOther(TravelHotel hotel) {
        StringBuilder builder = new StringBuilder();
        if(hotel == null || hotel.getPlace() == null || hotel.getDateArrival() == null) {
            return "";
        }
        builder.append("/").append(hotel.getPlace())
                .append("/").append("PastorHotel")
                .append("/").append(hotel.getDateArrival().replace(".", "-"))
                .append("/").append(hotel.getDateDeparture().replace(".", "-"));
        return builder.toString();
    }


    public static AggregationStrategy mergeHotels = (oldExchange, newExchange) -> {
        if (oldExchange == null) {
            return newExchange;
        } else {
            TravelHotel otherHotel = newExchange.getIn().getBody(TravelHotel.class);
            TravelHotel hotel = oldExchange.getIn().getBody(TravelHotel.class);
            if (hotel == null && otherHotel == null) {
                System.out.println("ALL HOTELS NULL");
                oldExchange.getIn().setBody(null);
                return oldExchange;
            }
            if (hotel == null || hotel.getPrice() == 0 || otherHotel.getPrice() < hotel.getPrice()) {
                oldExchange.getIn().setBody(otherHotel);
            } else {
                oldExchange.getIn().setBody(hotel);
            }
        }
        return oldExchange;
    };

    public static Processor result2Hotel = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String input = exc.getIn().getBody(String.class);
        if (input == null) {
            exc.getIn().setBody(null);
            return;
        }
        InputSource source = new InputSource(new StringReader(input));
        String jsonArray = xpath.evaluate("//hotel_planner_result", source);
        Type listType = new TypeToken<ArrayList<Hotel>>() {
        }.getType();
        List<Hotel> hotels;
        try {
            hotels = new Gson().fromJson(jsonArray, listType);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("NO HOTEL");
            return;
        }
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("NO HOTEL");
            return;
        }
        //get the min price
        Hotel hotelMin = hotels.stream().min(Comparator.comparingInt(Hotel::getPrice)).get();
        TravelHotel min = new TravelHotel(hotelMin);
        exc.getIn().setBody(min);
    };

    public static Processor result2OtherHotel = (Exchange exc) -> {
        String input = exc.getIn().getBody(String.class);
        if (input == null) {
            exc.getIn().setBody(null);
            return;
        }
        Type mapType = new TypeToken<Map<String, List<OtherHotel>>>() {
        }.getType();
        Map<String, List<OtherHotel>> map;
        List<OtherHotel> hotels;
        try {
            map = new Gson().fromJson(input, mapType);
            if (map == null) {
                System.out.println("NO Other HOTELS");
                return;
            }
            hotels = map.get("hotels");
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(e + " NO Other HOTELS");
            return;
        }
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("NO Other HOTELS");
            return;
        }
        OtherHotel hotelMin = hotels.stream().min(Comparator.comparingDouble(OtherHotel::getAmount)).get();
        TravelHotel min = new TravelHotel(hotelMin);
        exc.getIn().setBody(min);
    };
}

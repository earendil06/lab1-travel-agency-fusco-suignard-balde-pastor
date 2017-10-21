package fr.unice.groupe4.flows.utils;

import fr.unice.groupe4.flows.data.Car;

public class CarHelper {
    public String buildGetCarForTravel(Car car) {
        StringBuilder builder = new StringBuilder();
        builder.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
        builder.append(" <soap:Body>\n");
        builder.append("  <getCarByPlace xmlns=\"http://service.planner/\">\n");
        builder.append("    <place xmlns=\"\">"+ car.getPlace() + "</place>\n");
        builder.append("    <duration xmlns=\"\">" + car.getDuration() + "</duration>\n");
        builder.append("  </getCarByPlace>\n");
        builder.append(" </soap:Body>");
        builder.append("</soap:Envelope>");
        return builder.toString();
    }
}

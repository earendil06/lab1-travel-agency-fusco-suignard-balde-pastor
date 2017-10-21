package fr.unice.groupe4.flows.utils;

import fr.unice.groupe4.flows.data.Hotel;

public class HotelHelper {
//    private XPath xpath = XPathFactory.newInstance().newXPath();

    public String buildGetHotelForTravel(Hotel hotel) {
        StringBuilder builder = new StringBuilder();
        builder.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
        builder.append(" <soap:Body>\n");
        builder.append("  <getHotelsForTravel xmlns=\"http://service.planner/\">\n");
        builder.append("    <place xmlns=\"\">"+ hotel.getPlace() + "</place>\n");
        builder.append("    <dateArrival xmlns=\"\">" + hotel.getDateArrival() + "</dateArrival>\n");
        builder.append("    <dateDeparture xmlns=\"\">" + hotel.getDateDeparture() + "</dateDeparture>\n");
        builder.append("  </getHotelsForTravel>\n");
        builder.append(" </soap:Body>");
        builder.append("</soap:Envelope>");
        return builder.toString();
    }
}

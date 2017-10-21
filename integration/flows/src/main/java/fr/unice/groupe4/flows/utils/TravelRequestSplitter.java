package fr.unice.groupe4.flows.utils;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TravelRequestSplitter {
    public static List split(Exchange exchange) {
        List list = new ArrayList();
        String uid = UUID.randomUUID().toString();

        Map map = exchange.getIn().getBody(Map.class);
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            Message msg = new DefaultMessage();
            switch (key.toString()) {
                case "email":
                    msg.setBody(value);
                    msg.setHeader("type", "email");
                    break;
                case "flight":
                    msg.setBody(value);
                    msg.setHeader("type", "flight");
                    break;
                case "car":
                    msg.setBody(value);
                    msg.setHeader("type", "car");
                    break;
                case "hotel":
                    msg.setBody(value);
                    msg.setHeader("type", "hotel");
                    break;
                default:
                    msg.setBody("def");
            }
            msg.setHeader("uid", uid);
            list.add(msg);
        }
        return list;
    }

}
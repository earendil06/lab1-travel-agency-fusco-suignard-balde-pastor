package fr.unice.groupe4.flows.utils;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SupportingRequestSplitter {
    public static List split(Exchange exchange) {
        List list = new ArrayList();
        String uid = UUID.randomUUID().toString();

        Map map = exchange.getIn().getBody(Map.class);
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            Message msg = new DefaultMessage();
            switch (key.toString()) {
                case "livingExpenses":
                    msg.setBody(value);
                    msg.setHeader("type", "livingExpenses");
                    break;
                case "employer":
                    msg.setBody(value);
                    msg.setHeader("type", "employer");
                    break;
                case "travel":
                    msg.setBody(value);
                    msg.setHeader("type", "travel");
                    break;
                case "expenses":
                    msg.setBody(value);
                    msg.setHeader("type", "expenses");
                    break;
                default:
                    msg.setBody("FS");
            }
            msg.setHeader("uid", uid);
            list.add(msg);
        }
        return list;
    }

}
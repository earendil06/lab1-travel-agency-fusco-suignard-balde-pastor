package fr.unice.groupe4.flows.utils;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExpenseRequestSplitter {
    public static List split(Exchange exchange) {
        List list = new ArrayList();
        String uid = UUID.randomUUID().toString();

        Map map = exchange.getIn().getBody(Map.class);
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            Message msg = new DefaultMessage();
            switch (key.toString()) {
                case "Type":
                    msg.setBody(value);
                    msg.setHeader("type", "Type");
                    break;
                case "Motif":
                    msg.setBody(value);
                    msg.setHeader("type", "Motif");
                    break;
                case "Price":
                    msg.setBody(value);
                    msg.setHeader("type", "Price");
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
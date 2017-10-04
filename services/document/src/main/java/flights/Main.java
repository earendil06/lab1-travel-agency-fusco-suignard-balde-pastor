package flights;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Flight f = new Flight("paris", "london", "10/10/2017", "20:00", 120, 150, true);
        JSONObject input = f.toJson();

        Map<String, Object> parameters = new HashMap<>();

        for (Field field : Flight.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(AttributeQueryable.class)){
                try {

                    parameters.put(field.getName(), "\"" + input.get(field.getName()) +"\"");
                }catch (Exception e){

                }
            }
        }
        String filter = Handler.filter(parameters);
        System.out.println(filter);
    }

}

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Parameters> contents = new ArrayList<>();

    public static void create(Parameters parameters) {
        contents.add(parameters);
    }
}

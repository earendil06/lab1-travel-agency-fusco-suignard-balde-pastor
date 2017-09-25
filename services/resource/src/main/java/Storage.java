import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Proposal> contents = new ArrayList<>();

    public static void create(Proposal proposal) {
        contents.add(proposal);
    }
}

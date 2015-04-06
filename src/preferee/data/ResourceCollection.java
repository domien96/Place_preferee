package preferee.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstracte bovenklasse voor alle een collectie van een soort resources
 * Created by domien Van Steendam on 2/04/2015.
 *
 * type parameter: type of a single resource
 */
public abstract class ResourceCollection<R extends Resource> {

    protected R[] items;

    public Map<Integer, R> getItemsAsMap () {
        List<R> lijst = Arrays.asList(items);
        Map<Integer,R> out = new HashMap<>();
        for (R item : lijst) {
            out.put(item.getId(), item);
        }
        return out;
    }

    public List<R> getItemsAsList () { return Arrays.asList(items);}
}

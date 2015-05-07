package preferee.GUI;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;

/**
 * Een klasse gemaakt voor het gebruiken van een Lambda voor
 *  - Task objecten.
 * Created by Domien on 7/05/2015.
 */
public class Utils {

    public static <T> Task<T> task(Callable<T> callable) {
        return new Task<T>() {
            @Override
            public T call() throws Exception {
                return callable.call();
            }
        };
    }

}

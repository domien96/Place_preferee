package preferee.GUI.tableView;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import preferee.data.Movie;

/**
 * Created by Domien on 29/04/2015.
 */
public class MoviePosterTableCellValueFactory extends TableCellValueFactory<Movie,Image> {
    @Override
    protected ObservableValue<Image> createObservableValue(Movie value) {
        return new ReadOnlyObjectWrapper<>(new Image(value.getPoster()));
    }
}

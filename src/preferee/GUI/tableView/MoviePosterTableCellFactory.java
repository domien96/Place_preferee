package preferee.GUI.tableView;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import preferee.data.Movie;


/**
 * Created by Domien on 29/04/2015.
 */
public class MoviePosterTableCellFactory extends TableCellFactory<Movie,Image>{

    @Override
    protected TableCell<Movie, Image> createTableCell(TableColumn<Movie, Image> column) {
        ImageView imgVw = new ImageView();
        imgVw.setFitHeight(80);
        imgVw.setFitWidth(80);

        TableCell<Movie,Image> cell = new TableCell<Movie,Image>(){
            protected void updateItem (Image item,boolean empty) {
                super.updateItem(item,empty);
                imgVw.setImage(item);
            }
        };
        cell.setGraphic(imgVw);
        return cell;
    }


}

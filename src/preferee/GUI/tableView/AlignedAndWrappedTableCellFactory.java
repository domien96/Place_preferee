package preferee.GUI.tableView;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * Table cell factory die cellen aanmaakt waarvan de inhoud is gealigneerd en gewrapt. Per default is de
 * alignatie {@code CENTER_RIGHT} maar een alternatief kan worden meegegeven als parameter
 * van de constructor.
 * created by Kris Coolsaet and modified by Domien on 4/05/2015.
 */
public class AlignedAndWrappedTableCellFactory<S, T> extends TableCellFactory <S, T> {

    private final Pos alignment;

    public AlignedAndWrappedTableCellFactory() {
        this (Pos.CENTER_RIGHT);
    }

    public AlignedAndWrappedTableCellFactory(Pos alignment) {
        this.alignment = alignment;
    }

    private class AlignedTableCell<S, T> extends TextFieldTableCell<S, T> {

        public AlignedTableCell() {
            setAlignment(alignment);
            setWrapText(true);
        }
    }

    @Override
    protected TableCell<S, T> createTableCell (TableColumn<S, T> column) {
        return new AlignedTableCell<>();
    }
}

package preferee.GUI.tableView;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * "Gemeenschappelijke bovenklasse voor table cell factories.<p>
 * Het is gemakkelijker om deze klasse uit te breiden dan om rechtstreeks de
 * interface {@link Callback} te implementeren met al haar parameters." - k. Coolsaet
 *  ---
 * CallBack is te algemeen (ze wordt niet enkel voor table-toepassingen gebruikt) en dankzij deze klasse
 * zijn de subklasses beter leesbaar
 * Created by Domien on 29/04/2015.
 */
public abstract class TableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    public final TableCell<S, T> call(TableColumn<S, T> p) {
        return createTableCell(p);
    }

    protected abstract TableCell<S, T> createTableCell(TableColumn<S, T> column);
}
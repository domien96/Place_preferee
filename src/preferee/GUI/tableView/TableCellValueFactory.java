package preferee.GUI.tableView;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * "Gemeenschappelijke bovenklasse voor cell value factories.<p>
 * Het is gemakkelijker om deze klasse uit te breiden dan om rechtstreeks de
 * interface {@link Callback} te implementeren met al haar parameters." - k. Coolsaet
 *  ---
 * CallBack is te algemeen (ze wordt niet enkel voor table-toepassingen gebruikt) en dankzij deze klasse
 * zijn de subklasses beter leesbaar
 * Created by Domien on 29/04/2015.
 */
public abstract class TableCellValueFactory<S, T> implements Callback<TableColumn.CellDataFeatures<S,T>,ObservableValue<T>> {

    @Override
    public final ObservableValue<T> call(TableColumn.CellDataFeatures<S,T> p) {
        return createObservableValue(p.getValue());
    }

    protected abstract ObservableValue<T> createObservableValue (S value);
}
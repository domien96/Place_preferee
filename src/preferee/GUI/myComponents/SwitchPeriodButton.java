package preferee.GUI.myComponents;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Een button die van tekst verandert. De tekst komt overeen met het aantal dagen ze voorstelt.
 * Created by Domien on 4/05/2015.
 */
public class SwitchPeriodButton extends Button {

    @FXML
    private List<String> periodNames = new ArrayList<>(); // de namen van de perioden
    @FXML
    private List<Integer> periodInDays = new ArrayList<>(); // de waarden van de perioden in aantal dagen.
    /**
     * Beeldt periodenaam af op zijn aantal dagen
     * Aangezien er weinig is geweten van Maps in fxml, bouwen we de map manueel op.
     */
    private Map<String, Integer> periods;

    private IntegerProperty index; // de index van de actieve waarde in de lijst van perioden.

    public void initialize() {
        for (int i=0; i<periodNames.size(); i++) {
            periods.put(periodNames.get(i), periodInDays.get(i)) ;
        }
    }

    public SwitchPeriodButton() {
        index = new SimpleIntegerProperty(0);
        index.addListener(o -> setText(periodNames.get(index.get()))); // lijsteraar bij veranderen index => veranderen button text
        this.setOnMouseClicked(event -> nextValue()); // luisteraar bij klikken => model updaten
    }

    public SwitchPeriodButton(List<String> periodNames, List<Integer> periodInDays) {
        this();
        setPeriodNames(periodNames);
        setPeriodInDays(periodInDays);
    }

    public void nextValue() {
        int newIndex = index.get() + 1;
        this.index.set(newIndex < periodNames.size() ? newIndex : 0);
    }

    public void setPeriodNames(List<String> periodNames) {
        this.periodNames = periodNames;
        setText(periodNames.size() > 0 ? periodNames.get(0) : getText());
    }

    public List<String> getPeriodNames() {
        return periodNames;
    }

    public void setPeriodInDays(List<Integer> periodInDays) {
        this.periodInDays = periodInDays;
    }

    public List<Integer> getPeriodInDays() {
        return periodInDays;
    }
}

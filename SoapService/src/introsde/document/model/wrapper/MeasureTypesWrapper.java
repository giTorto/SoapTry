package introsde.document.model.wrapper;

import introsde.document.model.MeasureType;

import java.util.List;
import java.util.Vector;

/**
 * The persistent class for the "Person" database table.
 * This class manages the communication between the application and the DB.
 */
public class MeasureTypesWrapper {
    private List<String> measureTypes;

    MeasureTypesWrapper() {

    }



    public List<String> getMeasureTypes() {
        return measureTypes;
    }

    public void setMeasureTypes(List<String> measureTypes) {
        this.measureTypes = measureTypes;
    }

    public MeasureTypesWrapper (List<MeasureType> measure) {
        measureTypes = new Vector<String>();
        for (MeasureType mt : measure)
            measureTypes.add(mt.getMeasureName());
    }
}

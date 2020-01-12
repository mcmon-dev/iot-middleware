import at.ac.tuwien.infosys.iotmiddleware.model.MeasureType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MeasureTypeTest {

    @Test
    public void returnEnumTest(){
        MeasureType type = MeasureType.mapToEnum("/humidity");
        assertTrue(MeasureType.Humidity == type);
    }
}

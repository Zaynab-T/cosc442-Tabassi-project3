import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VendingMachineExceptionTest {
    
    @Test 
    public void testConstructor_withMessage() {

        String reason = "Test message";

        VendingMachineException exception = new VendingMachineException(reason);

        assertEquals(reason, exception.getMessage());
    }

    @Test 
    public void testConstructor_noArguments() {

        VendingMachineException exception = new VendingMachineException();

        assertNull(exception.getMessage());
    }
}

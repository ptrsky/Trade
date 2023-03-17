package pp.exercise.trade;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTests {
    private final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
    private final PrintStream systemOut = System.out;
    private Application application;

    @BeforeAll
    public void setupOutput() {
        System.setOut(new PrintStream(testOutput));
        application = new Application();
    }

    @AfterEach
    public void clearOutput() {
        testOutput.reset();
    }

    @AfterAll
    public void restoreOutput() {
        System.setOut(systemOut);
    }

    @Test
    void handleSignal_1() {
        application.handleSignal(1);
        assertEquals("setUp\r\n" +
                "setAlgoParam 1,60\r\n" +
                "performCalc\r\n" +
                "submitToMarket\r\n" +
                "doAlgo\r\n", testOutput.toString());
    }

    @Test
    void handleSignal_2() {
        application.handleSignal(2);
        assertEquals("reverse\r\n" +
                "setAlgoParam 1,80\r\n" +
                "submitToMarket\r\n" +
                "doAlgo\r\n", testOutput.toString());
    }

    @Test
    void handleSignal_3() {
        application.handleSignal(3);
        assertEquals("setAlgoParam 1,90\r\n" +
                "setAlgoParam 2,15\r\n" +
                "performCalc\r\n" +
                "submitToMarket\r\n" +
                "doAlgo\r\n", testOutput.toString());
    }

    @Test
    void handleSignal_Other() {
        application.handleSignal(120);
        assertEquals("cancelTrades\r\n" +
                "doAlgo\r\n", testOutput.toString());
    }

}

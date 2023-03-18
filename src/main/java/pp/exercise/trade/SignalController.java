package pp.exercise.trade;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pp.exercise.trade.library.SignalHandler;

@RestController
public class SignalController {
    private final SignalHandler signalHandler = new Application("src/main/resources/procedures.yaml");

    @PostMapping("/trading")
    void processSignal(@RequestBody int signal) {
        signalHandler.handleSignal(signal);
    }
}

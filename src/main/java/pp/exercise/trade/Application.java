package pp.exercise.trade;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pp.exercise.trade.library.Algo;
import pp.exercise.trade.library.SignalHandler;
import pp.exercise.trade.model.Instruction;
import pp.exercise.trade.model.Procedure;
import pp.exercise.trade.model.Procedures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Application implements SignalHandler {
    private Procedures proceduresDefinitions;
    private final Algo algo = new Algo();

    public Application(String path) {
       loadProcedures(path);
    }

    public void handleSignal(int signal) {
        Procedure procedure = proceduresDefinitions.getProcedures().stream()
                .filter(proc -> proc.getSignal().equals(signal))
                .findFirst()
                .orElse(null);

        if (procedure != null) {
            for (Instruction instr : procedure.getInstructions()) {
                execute(instr);
            }
        } else {
            algo.cancelTrades();
        }

        algo.doAlgo();
    }

    private void execute(Instruction instr) {
        switch (instr.getMethodName()) {
            case "doAlgo":
                algo.doAlgo();
                break;
            case "cancelTrades":
                algo.cancelTrades();
                break;
            case "reverse":
                algo.reverse();
                break;
            case "submitToMarket":
                algo.submitToMarket();
                break;
            case "performCalc":
                algo.performCalc();
                break;
            case "setUp":
                algo.setUp();
                break;
            case "setAlgoParam":
                algo.setAlgoParam(Integer.parseInt(instr.getArgument(1)), Integer.parseInt(instr.getArgument(2)));
                break;
            default:
                System.err.println("No method called " + instr.getMethodName() + " found");
                break;
        }
    }

    public void loadProcedures(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            Yaml yaml = new Yaml(new Constructor(Procedures.class));
            proceduresDefinitions = yaml.load(inputStream);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("No configuration file found.");
        }
    }
}

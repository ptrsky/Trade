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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Application implements SignalHandler {
    private Procedures proceduresDefinitions;
    private final Algo algo = new Algo();

    public Application() {
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/procedures.yaml");
            Yaml yaml = new Yaml(new Constructor(Procedures.class));
            proceduresDefinitions = yaml.load(inputStream);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            proceduresDefinitions = null;
        }
    }

    public void handleSignal(int signal) {
        Procedure procedure = proceduresDefinitions.getProcedures().stream()
                .filter(proc -> proc.getSignal().equals(signal))
                .findFirst()
                .orElse(null);

        if (procedure != null)
        {
            for (Instruction instr : procedure.getInstructions()) {
                execute(instr);
            }
        }
        else {
            algo.cancelTrades();
        }

        algo.doAlgo();
    }

    private void execute(Instruction instr) {
        try {
            if (instr.length() == 1) {
                Method method = algo.getClass().getMethod(instr.getMethodName());
                method.invoke(algo);
            }
            else if (instr.length() == 3) {
                Method method = algo.getClass().getMethod(instr.getMethodName(), int.class, int.class);
                method.invoke(algo, Integer.valueOf(instr.getArgument(1)), Integer.valueOf(instr.getArgument(2)));
            }
        }
        catch (NoSuchMethodException e) {
            System.err.println("No method of name " + instr.getMethodName() + " exists in the Algo library");
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            System.err.println("Invocation target exception.");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.err.println("Illegal access.");
            throw new RuntimeException(e);
        }
    }
}

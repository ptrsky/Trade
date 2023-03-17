package pp.exercise.trade.model;

import java.util.List;

public class Instruction {
    private List<String> parts;

    public Instruction(String s1) {
        this.parts = List.of(s1);
    }

    public Instruction(String s1, String s2) {
        this.parts = List.of(s1,s2);
    }

    public Instruction(String s1, String s2, String s3) {
        this.parts = List.of(s1,s2,s3);
    }

    public Instruction(List<String> parts) {
        this.parts = parts;
    }

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }

    public String getMethodName() {
        return parts.get(0);
    }

    public String getArgument(Integer index) {
        return parts.get(index);
    }

    public Integer length() {
        return parts.size();
    }
}

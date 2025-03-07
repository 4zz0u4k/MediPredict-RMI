import java.io.Serializable;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    // TODO: Add relevant health attributes (the ones in the csv)
    private int age;
    private double weight;
    private double height;
    private double bmi;
    private String diet;
    private boolean obese; // Only used for training, not prediction

    // TODO: Constructor, Getters, Setters, and toString()
}

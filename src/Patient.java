import java.io.Serializable;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    // TODO: Add relevant health attributes (age, weight, height, BMI, diet, etc.)
    private int age;
    private double weight;
    private double height;
    private double bmi;
    private String diet;
    private boolean obese; // Only used for training, not prediction

    // TODO: Constructor, Getters, Setters, and toString()
}

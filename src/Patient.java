import java.io.Serializable;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int isMale;
    private double age;
    private double height;
    private double weight;
    private int familialOverweightHistory;
    private int highCaloricFood;
    private double vegetablesInMeals;
    private double dailyMainMeals;
    private int smoking;
    private double waterConsumption;
    private int caloriesMonitoring;
    private double physicalActivity;
    private double screenTime;
    private double bmi;
    private int eatBetweenMealsAlways;
    private int eatBetweenMealsFrequently;
    private int eatBetweenMealsNo;
    private int eatBetweenMealsSometimes;
    private int alcoholConsumptionAlways;
    private int alcoholConsumptionFrequently;
    private int alcoholConsumptionNo;
    private int alcoholConsumptionSometimes;
    private int transportationAutomobile;
    private int transportationBike;
    private int transportationMotorbike;
    private int transportationPublicTransportation;
    private int transportationWalking;
    private int obesityLevel;
    
    public Patient() {
    }

    public Patient(int isMale, double age, double height, double weight, int familialOverweightHistory,
                   int highCaloricFood, double vegetablesInMeals, double dailyMainMeals, int smoking,
                   double waterConsumption, int caloriesMonitoring, double physicalActivity, double screenTime, double bmi,
                   int eatBetweenMealsAlways, int eatBetweenMealsFrequently, int eatBetweenMealsNo, int eatBetweenMealsSometimes,
                   int alcoholConsumptionAlways, int alcoholConsumptionFrequently, int alcoholConsumptionNo, int alcoholConsumptionSometimes,
                   int transportationAutomobile, int transportationBike, int transportationMotorbike, int transportationPublicTransportation,
                   int transportationWalking, int obesityLevel) {
        this.isMale = isMale;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.familialOverweightHistory = familialOverweightHistory;
        this.highCaloricFood = highCaloricFood;
        this.vegetablesInMeals = vegetablesInMeals;
        this.dailyMainMeals = dailyMainMeals;
        this.smoking = smoking;
        this.waterConsumption = waterConsumption;
        this.caloriesMonitoring = caloriesMonitoring;
        this.physicalActivity = physicalActivity;
        this.screenTime = screenTime;
        this.bmi = bmi;
        this.eatBetweenMealsAlways = eatBetweenMealsAlways;
        this.eatBetweenMealsFrequently = eatBetweenMealsFrequently;
        this.eatBetweenMealsNo = eatBetweenMealsNo;
        this.eatBetweenMealsSometimes = eatBetweenMealsSometimes;
        this.alcoholConsumptionAlways = alcoholConsumptionAlways;
        this.alcoholConsumptionFrequently = alcoholConsumptionFrequently;
        this.alcoholConsumptionNo = alcoholConsumptionNo;
        this.alcoholConsumptionSometimes = alcoholConsumptionSometimes;
        this.transportationAutomobile = transportationAutomobile;
        this.transportationBike = transportationBike;
        this.transportationMotorbike = transportationMotorbike;
        this.transportationPublicTransportation = transportationPublicTransportation;
        this.transportationWalking = transportationWalking;
        this.obesityLevel = obesityLevel;
    }

    // Getters and Setters
    public int getIsMale() { return isMale; }
    public void setIsMale(int isMale) { this.isMale = isMale; }

    public double getAge() { return age; }
    public void setAge(double age) { this.age = age; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public int getFamilialOverweightHistory() { return familialOverweightHistory; }
    public void setFamilialOverweightHistory(int familialOverweightHistory) { this.familialOverweightHistory = familialOverweightHistory; }

    public int getHighCaloricFood() { return highCaloricFood; }
    public void setHighCaloricFood(int highCaloricFood) { this.highCaloricFood = highCaloricFood; }

    public double getVegetablesInMeals() { return vegetablesInMeals; }
    public void setVegetablesInMeals(double vegetablesInMeals) { this.vegetablesInMeals = vegetablesInMeals; }

    public double getDailyMainMeals() { return dailyMainMeals; }
    public void setDailyMainMeals(double dailyMainMeals) { this.dailyMainMeals = dailyMainMeals; }

    public int getSmoking() { return smoking; }
    public void setSmoking(int smoking) { this.smoking = smoking; }

    public double getWaterConsumption() { return waterConsumption; }
    public void setWaterConsumption(double waterConsumption) { this.waterConsumption = waterConsumption; }

    public int getCaloriesMonitoring() { return caloriesMonitoring; }
    public void setCaloriesMonitoring(int caloriesMonitoring) { this.caloriesMonitoring = caloriesMonitoring; }

    public double getPhysicalActivity() { return physicalActivity; }
    public void setPhysicalActivity(double physicalActivity) { this.physicalActivity = physicalActivity; }

    public double getScreenTime() { return screenTime; }
    public void setScreenTime(double screenTime) { this.screenTime = screenTime; }

    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }

    public int getEatBetweenMealsAlways() { return eatBetweenMealsAlways; }
    public void setEatBetweenMealsAlways(int eatBetweenMealsAlways) { this.eatBetweenMealsAlways = eatBetweenMealsAlways; }

    public int getEatBetweenMealsFrequently() { return eatBetweenMealsFrequently; }
    public void setEatBetweenMealsFrequently(int eatBetweenMealsFrequently) { this.eatBetweenMealsFrequently = eatBetweenMealsFrequently; }

    public int getEatBetweenMealsNo() { return eatBetweenMealsNo; }
    public void setEatBetweenMealsNo(int eatBetweenMealsNo) { this.eatBetweenMealsNo = eatBetweenMealsNo; }

    public int getEatBetweenMealsSometimes() { return eatBetweenMealsSometimes; }
    public void setEatBetweenMealsSometimes(int eatBetweenMealsSometimes) { this.eatBetweenMealsSometimes = eatBetweenMealsSometimes; }

    public int getAlcoholConsumptionAlways() { return alcoholConsumptionAlways; }
    public void setAlcoholConsumptionAlways(int alcoholConsumptionAlways) { this.alcoholConsumptionAlways = alcoholConsumptionAlways; }

    public int getAlcoholConsumptionFrequently() { return alcoholConsumptionFrequently; }
    public void setAlcoholConsumptionFrequently(int alcoholConsumptionFrequently) { this.alcoholConsumptionFrequently = alcoholConsumptionFrequently; }

    public int getAlcoholConsumptionNo() { return alcoholConsumptionNo; }
    public void setAlcoholConsumptionNo(int alcoholConsumptionNo) { this.alcoholConsumptionNo = alcoholConsumptionNo; }

    public int getAlcoholConsumptionSometimes() { return alcoholConsumptionSometimes; }
    public void setAlcoholConsumptionSometimes(int alcoholConsumptionSometimes) { this.alcoholConsumptionSometimes = alcoholConsumptionSometimes; }

    public int getTransportationAutomobile() { return transportationAutomobile; }
    public void setTransportationAutomobile(int transportationAutomobile) { this.transportationAutomobile = transportationAutomobile; }

    public int getTransportationBike() { return transportationBike; }
    public void setTransportationBike(int transportationBike) { this.transportationBike = transportationBike; }

    public int getTransportationMotorbike() { return transportationMotorbike; }
    public void setTransportationMotorbike(int transportationMotorbike) { this.transportationMotorbike = transportationMotorbike; }

    public int getTransportationPublicTransportation() { return transportationPublicTransportation; }
    public void setTransportationPublicTransportation(int transportationPublicTransportation) { this.transportationPublicTransportation = transportationPublicTransportation; }

    public int getTransportationWalking() { return transportationWalking; }
    public void setTransportationWalking(int transportationWalking) { this.transportationWalking = transportationWalking; }

    public int getObesityLevel() { return obesityLevel; }
    public void setObesityLevel(int obesityLevel) { this.obesityLevel = obesityLevel; }

    @Override
    public String toString() {
        return "Patient(" +
                "Gender=" + (isMale == 1 ? "Male" : "Female") +
                ", Age=" + age +
                ", Height=" + height + " m" +
                ", Weight=" + weight + " kg" +
                ", BMI=" + bmi +
                ", Familial Overweight History=" + (familialOverweightHistory == 1 ? "Yes" : "No") +
                ", High Caloric Food Consumption=" + (highCaloricFood == 1 ? "Yes" : "No") +
                ", Vegetables in Meals=" + vegetablesInMeals +
                ", Daily Main Meals=" + dailyMainMeals +
                ", Smoking=" + (smoking == 1 ? "Yes" : "No") +
                ", Water Consumption=" + waterConsumption + " L/day" +
                ", Calories Monitoring=" + (caloriesMonitoring == 1 ? "Yes" : "No") +
                ", Physical Activity=" + physicalActivity + " hours/week" +
                ", Screen Time=" + screenTime + " hours/day" +
                ", Eating Between Meals=" + getEatBetweenMealsCategory() +
                ", Alcohol Consumption=" + getAlcoholConsumptionCategory() +
                ", Mode of Transportation=" + getTransportationMode() +
                ", Obesity Level=" + getObesityCategory() +
                ')';
    }
    
    // Interpret one-hot encoded eating between meals data
    private String getEatBetweenMealsCategory() {
        if (eatBetweenMealsAlways == 1) return "Always";
        if (eatBetweenMealsFrequently == 1) return "Frequently";
        if (eatBetweenMealsNo == 1) return "Never";
        if (eatBetweenMealsSometimes == 1) return "Sometimes";
        return "Unknown";
    }
    
    // Interpret one-hot encoded alcohol consumption data
    private String getAlcoholConsumptionCategory() {
        if (alcoholConsumptionAlways == 1) return "Always";
        if (alcoholConsumptionFrequently == 1) return "Frequently";
        if (alcoholConsumptionNo == 1) return "Never";
        if (alcoholConsumptionSometimes == 1) return "Sometimes";
        return "Unknown";
    }
    
    // Interpret one-hot encoded transportation data
    private String getTransportationMode() {
        if (transportationAutomobile == 1) return "Automobile";
        if (transportationBike == 1) return "Bicycle";
        if (transportationMotorbike == 1) return "Motorbike";
        if (transportationPublicTransportation == 1) return "Public Transport";
        if (transportationWalking == 1) return "Walking";
        return "Unknown";
    }
    
    // Interpret obesity level
    private String getObesityCategory() {
        switch (obesityLevel) {
            case 0: return "Insufficient Weight";
            case 1: return "Normal Weight";
            case 2: return "Overweight Level I";
            case 3: return "Overweight Level II";
            case 4: return "Obesity Type I";
            case 5: return "Obesity Type II";
            case 6: return "Obesity Type III";
            default: return "Unknown";
        }
    }
}   
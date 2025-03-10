import java.io.Serializable;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String gender;
    private double age;
    private double height;
    private double weight;
    private boolean familyHistoryWithOverweight;
    private boolean favc;  // Frequent consumption of high caloric food
    private double fcvc;   // Frequency of consumption of vegetables
    private double ncp;    // Number of main meals
    private String caec;   // Consumption of food between meals (Sometimes, Frequently, etc.)
    private boolean smoke;
    private double ch2o;   // Consumption of water daily
    private boolean scc;   // Calories consumption monitoring
    private double faf;    // Physical activity frequency
    private double tue;    // Time using technology devices
    private String calc;   // Consumption of alcohol (no, Sometimes, etc.)
    private String mtrans; // Transportation used (Public_Transportation, etc.)
    private String nObeyesdad; // Obesity level (Normal_Weight, Overweight, etc.)

    public Patient() {
    }

    public Patient(String gender, double age, double height, double weight, boolean familyHistoryWithOverweight,
                   boolean favc, double fcvc, double ncp, String caec, boolean smoke, double ch2o, boolean scc,
                   double faf, double tue, String calc, String mtrans, String nObeyesdad) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.familyHistoryWithOverweight = familyHistoryWithOverweight;
        this.favc = favc;
        this.fcvc = fcvc;
        this.ncp = ncp;
        this.caec = caec;
        this.smoke = smoke;
        this.ch2o = ch2o;
        this.scc = scc;
        this.faf = faf;
        this.tue = tue;
        this.calc = calc;
        this.mtrans = mtrans;
        this.nObeyesdad = nObeyesdad;
    }

    public Patient(Patient patient) {
        this.gender = patient.gender;
        this.age = patient.age;
        this.height = patient.height;
        this.weight = patient.weight;
        this.familyHistoryWithOverweight = patient.familyHistoryWithOverweight;
        this.favc = patient.favc;
        this.fcvc = patient.fcvc;
        this.ncp = patient.ncp;
        this.caec = patient.caec;
        this.smoke = patient.smoke;
        this.ch2o = patient.ch2o;
        this.scc = patient.scc;
        this.faf = patient.faf;
        this.tue = patient.tue;
        this.calc = patient.calc;
        this.mtrans = patient.mtrans;
    }

    // Getters et Setters
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isFamilyHistoryWithOverweight() {
        return familyHistoryWithOverweight;
    }

    public void setFamilyHistoryWithOverweight(boolean familyHistoryWithOverweight) {
        this.familyHistoryWithOverweight = familyHistoryWithOverweight;
    }

    public boolean isFavc() {
        return favc;
    }

    public void setFavc(boolean favc) {
        this.favc = favc;
    }

    public double getFcvc() {
        return fcvc;
    }

    public void setFcvc(double fcvc) {
        this.fcvc = fcvc;
    }

    public double getNcp() {
        return ncp;
    }

    public void setNcp(double ncp) {
        this.ncp = ncp;
    }

    public String getCaec() {
        return caec;
    }

    public void setCaec(String caec) {
        this.caec = caec;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        this.smoke = smoke;
    }

    public double getCh2o() {
        return ch2o;
    }

    public void setCh2o(double ch2o) {
        this.ch2o = ch2o;
    }

    public boolean isScc() {
        return scc;
    }

    public void setScc(boolean scc) {
        this.scc = scc;
    }

    public double getFaf() {
        return faf;
    }

    public void setFaf(double faf) {
        this.faf = faf;
    }

    public double getTue() {
        return tue;
    }

    public void setTue(double tue) {
        this.tue = tue;
    }

    public String getCalc() {
        return calc;
    }

    public void setCalc(String calc) {
        this.calc = calc;
    }

    public String getMtrans() {
        return mtrans;
    }

    public void setMtrans(String mtrans) {
        this.mtrans = mtrans;
    }

    public String getNObeyesdad() {
        return nObeyesdad;
    }

    public void setNObeyesdad(String nObeyesdad) {
        this.nObeyesdad = nObeyesdad;
    }

    // Calcul de l'IMC (Indice de Masse Corporelle)
    public double calculateBMI() {
        if (height <= 0) return 0;
        return weight / (height * height);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "gender='" + gender + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", familyHistoryWithOverweight=" + familyHistoryWithOverweight +
                ", favc=" + favc +
                ", fcvc=" + fcvc +
                ", ncp=" + ncp +
                ", caec='" + caec + '\'' +
                ", smoke=" + smoke +
                ", ch2o=" + ch2o +
                ", scc=" + scc +
                ", faf=" + faf +
                ", tue=" + tue +
                ", calc='" + calc + '\'' +
                ", mtrans='" + mtrans + '\'' +
                ", nObeyesdad='" + nObeyesdad + '\'' +
                '}';
    }
}
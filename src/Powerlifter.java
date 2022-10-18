public class Powerlifter {
    public String fullName;
    public String ageClass;
    public String weightClass;

    private float bestSquatKg;
    private float bestBenchKg;
    private float bestDeadliftKg;
    private float bestTotalKg;

    private static float kgToLbs = 2.204623f;

    // Constructor
    Powerlifter(String... userInfo){
        fullName = userInfo[0];
        ageClass = userInfo[1];
        weightClass = userInfo[2];
    }

    // Setters
    public void setBestSquatKg(float bestSquatKg){
        this.bestSquatKg = bestSquatKg;
    }

    public void setBestBenchKg(float bestBenchKg){
        this.bestBenchKg = bestBenchKg;
    }

    public void setBestDeadliftKg(float bestDeadliftKg){
        this.bestDeadliftKg = bestDeadliftKg;
    }

    public void setBestTotalKg(){
        this.bestTotalKg = bestSquatKg + bestBenchKg + bestDeadliftKg;
    }

    // Getters
    public float getBestSquatKg(){
        return bestSquatKg;
    }

    public float getBestBenchKg(){
        return bestBenchKg;
    }

    public float getBestDeadliftKg(){
        return bestDeadliftKg;
    }

    public float getBestTotalKg(){
        return bestTotalKg;
    }

    // Get the best squat/bench/deadlift/total based on the string input.
    // Used in the sort method in Gym.

    public float getBest(String lift) {
        switch (lift.toLowerCase()) {
            case "squat":
                return getBestSquatKg();

            case "bench":
                return getBestBenchKg();

            case "deadlift":
                return getBestDeadliftKg();

            case "total":
                return getBestTotalKg();

            default:
                return -1;
        }
    }

    // Methods to convert best results to lbs
    public void getBestSquatLbs(){
        float result = bestSquatKg * kgToLbs;
        System.out.println("Best Squat in pounds: " + result);
    }

    public void getBestBenchLbs(){
        float result = bestBenchKg * kgToLbs;
        System.out.println("Best Bench in pounds: " + result);
    }

    public void getBestDeadliftLbs(){
        float result = bestDeadliftKg * kgToLbs;
        System.out.println("Best deadlift in pounds: " + result);
    }

    public void getBestTotalLbs(){
        float result = bestTotalKg * kgToLbs;
        System.out.println("Best total in pounds: " + result);
    }


    // Method to print the basic information about an athlete
    public void printShortInfo(){
        System.out.println("Athlete name: " + fullName);
        System.out.println("Athlete age category: " + ageClass);
        System.out.println("Athlete weight class: " + weightClass);
        System.out.println("------------------------------");
    }

    public void printOneLiner(){
        System.out.println(fullName +
                " | Age class: " + ageClass +
                " | Weight Class: " + weightClass);
    }

    // Method to print information about an athlete
    public void printAthleteInfo(){
        System.out.println("Athlete name: " + fullName);
        System.out.println("Athlete age category: " + ageClass);
        System.out.println("Athlete weight class: " + weightClass);
        System.out.println("------------------------------");

        System.out.println("Best Lifts in kg: ");
        System.out.println("1. Best Squat: " + bestSquatKg);
        System.out.println("2. Best Bench: " + bestBenchKg);
        System.out.println("3. Best Deadlift: " + bestDeadliftKg);
        System.out.println("Best Total: " + bestTotalKg);
        System.out.println("------------------------------");

        System.out.println("Best Lifts in lbs: ");
        getBestSquatLbs();
        getBestBenchLbs();
        getBestDeadliftLbs();
        getBestTotalLbs();
        System.out.println("------------------------------\n\n");
    }

}

public class Powerlifter {
    public String fullName;
    public String ageClass;
    public String weightClass;

    private float bestSquatKg;
    private float bestBenchKg;
    private float bestDeadliftKg;
    private float bestTotalKg;

    private static final float kgToLbs = 2.204623f;

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


    public float getBest(String lift) {
        /*
        ----------------------------------------------------
        Gets best specified by the user lift of the powerlifter.
        Used in the sort method in Gym.
        ----------------------------------------------------
        Arguments:
            lift: (String) Type of lift to return (e.g. squat()
        ----------------------------------------------------
        Returns:
            (float) Best lift of the powerlifter
         */

        return switch (lift.toLowerCase()) {
            case "squat" -> getBestSquatKg();
            case "bench" -> getBestBenchKg();
            case "deadlift" -> getBestDeadliftKg();
            case "total" -> getBestTotalKg();
            default -> -1;
        };
    }

    public void getBestSquatLbs(){
        /*
        ----------------------------------------------------
        Prints the converted from kg to lbs best squat.
        ----------------------------------------------------
         */

        float result = bestSquatKg * kgToLbs;
        System.out.println("Best Squat in pounds: " + result);
    }

    public void getBestBenchLbs(){
        /*
        ----------------------------------------------------
        Prints the converted from kg to lbs best bench.
        ----------------------------------------------------
         */

        float result = bestBenchKg * kgToLbs;
        System.out.println("Best Bench in pounds: " + result);
    }

    public void getBestDeadliftLbs(){
        /*
        ----------------------------------------------------
        Prints the converted from kg to lbs best deadlift.
        ----------------------------------------------------
         */

        float result = bestDeadliftKg * kgToLbs;
        System.out.println("Best deadlift in pounds: " + result);
    }

    public void getBestTotalLbs(){
        /*
        ----------------------------------------------------
        Prints the converted from kg to lbs best total.
        ----------------------------------------------------
         */
        float result = bestTotalKg * kgToLbs;
        System.out.println("Best total in pounds: " + result);
    }


    public void printShortInfo(){
        /*
        ----------------------------------------------------
        Prints short information about the lifter.
        ----------------------------------------------------
         */

        System.out.println("Athlete name: " + fullName);
        System.out.println("Athlete age category: " + ageClass);
        System.out.println("Athlete weight class: " + weightClass);
        System.out.println("------------------------------");
    }

    public void printOneLiner(){
        /*
        ----------------------------------------------------
        Prints one-line information about the powerlifter.
        ----------------------------------------------------
         */

        System.out.println(fullName +
                " | Age class: " + ageClass +
                " | Weight Class: " + weightClass);
    }

    public void printAthleteInfo(){
        /*
        ----------------------------------------------------
        Prints full information about the lifter.
        ----------------------------------------------------
         */

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


    @Override
    public String toString(){
        return fullName + ", " + ageClass +  ", " + weightClass + ", " + bestSquatKg + ", " + bestBenchKg +  ", " + bestDeadliftKg;
    }

}

import java.util.Arrays;

public class Gym {
    // Gym is a collection of instances of class Powerlifter.
    private int count = 1;
    private Powerlifter[] powerlifters = new Powerlifter[count];
    private int currentLength = 0;

    public void add(Powerlifter powerlifter){
        /*
        ----------------------------------------------------
        Adds the passed object to the array of powerlifters.
        ----------------------------------------------------
        Arguments:
            powerlifter: (Powerlifter) object of class Powerlifter
        ----------------------------------------------------
         */

        // Expand the array size, if the current length ==
        if (!(currentLength < count)){
            count += 1;
            Powerlifter[] temp = new Powerlifter[count];

            // Copy all the previous values of the array of powerlifters into the new, expanded array
            for (int i = 0; i < powerlifters.length; i++){
                temp[i] = powerlifters[i];
            }
            powerlifters = temp;
        }

        // Otherwise, just add an element to the array, and increment the currentLength
        powerlifters[currentLength] = powerlifter;
        currentLength++;
    }

    public boolean removePowerlifter(String fullName){
        /*
        ----------------------------------------------------
        Removes a powerlifter from the array of powerlifters
        ----------------------------------------------------
        Arguments:
            fullName: (String) Full name of the powerlifter
            to remove.
        ----------------------------------------------------
         */

        int indexToRemove = 0;
        boolean found = false;

        // Get the index of the powerlifter to remove.
        for (int i = 0; i < currentLength; i++){
            if (powerlifters[i].fullName.equals(fullName)) {
                indexToRemove = i;
                found = true;

            }
        }

        if (found){
            // Shrink the array size by 1
            Powerlifter[] temp = new Powerlifter[currentLength-1];

            // Copy all elements from powerlifters to temp, omitting the powerlifter that the user wants to remove.
            System.arraycopy(powerlifters, 0, temp, 0, indexToRemove);
            System.arraycopy(powerlifters, indexToRemove+1, temp, indexToRemove, temp.length-indexToRemove);

            // Reset main variables.
            powerlifters = temp;
            currentLength--;
        }

        return found;
    }

    public void printOne(int index){
        /*
        ----------------------------------------------------
         Prints one powerlifter based on the index.
        ----------------------------------------------------
        Arguments:
            index: (int) index of a powerlifter to print
        ----------------------------------------------------
         */

        // Check if the index >= 0 AND index <= the current length of the array
        if ((index >= 0) && (index <= currentLength)){
            powerlifters[index].printAthleteInfo();

        } else {
            // Otherwise print an error (We don't want to print null values or get the IndexOutOfBounds Error)
            System.out.println("Index is invalid!");
        }
    }

    public void printAll(boolean shortInfo){
        /*
        ----------------------------------------------------
        Prints information about all powerlifters in the array
            of powerlifters.
        ----------------------------------------------------
        Arguments:
            shortInfo: (boolean) whether to print short
            information about the powerlifters or not

            If true, short information will be outputted, and
            full information otherwise.
        ----------------------------------------------------
         */
        for (int i = 0; i < currentLength; i++){
            if (shortInfo) {
                powerlifters[i].printShortInfo();

            } else {
                powerlifters[i].printAthleteInfo();
            }
        }
    }


    public void printList(){
        /*
        ----------------------------------------------------
        Prints list of powerlifters with one-line description
        of each lifter.
        ----------------------------------------------------
         */

        for (int i = 0; i < currentLength; i++){
            System.out.print((i+1) + ". ");
            powerlifters[i].printOneLiner();
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public void find(String searchKey){
        /*
        ----------------------------------------------------
         Finds and prints all powerlifters based on a search key.
         The search is
         ----------------------------------------------------
         Arguments:
            searchKey: (String) A keyword to search the powerlifters by.

            If "Ka" is passed, all powerlifters with "Ka" in their full name
            will be print out.
         ----------------------------------------------------
         */
        for (Powerlifter p : powerlifters){
            if (p.fullName.toLowerCase().contains(
                    searchKey.toLowerCase()
            ))
                p.printShortInfo();
        }

    }

    public void sortPowerlifters(String sortBy, boolean ascending){
        /*
        ----------------------------------------------------
        Sorts the array of powerlifters based on the sortBy argument
        ----------------------------------------------------
        Arguments:
           1. sortBy: (String) lift to sort the array by.
           sortBy takes on 4 different values:
                1. squat
                2. bench
                3. deadlift
                4. total

            2. ascending: (boolean) whether to sort in ascending or descending order

            If ascending = true, the array will be sorted by "sortBy" in ascending order,
                otherwise, it will sort the array in descending order.
        ----------------------------------------------------
         */
        int outIterations = 0;
        boolean isSorted = false;

        while(!isSorted){
            isSorted = true;

            // Sort in ascending order
            if (ascending){
                for (int i = 0; (i < currentLength - outIterations - 1); i++){
                    if (powerlifters[i].getBest(sortBy) > powerlifters[i + 1].getBest(sortBy)){
                        isSorted = false;
                        // Swap the elements
                        Powerlifter temp = powerlifters[i];
                        powerlifters[i] = powerlifters[i + 1];
                        powerlifters[i + 1] = temp;
                    }
                }
            }

            // Sort in descending order
            else {
                for (int i = 0; (i < currentLength - outIterations - 1); i++){
                    if (powerlifters[i].getBest(sortBy) < powerlifters[i + 1].getBest(sortBy)){

                        isSorted = false;
                        // Swap the elements
                        Powerlifter temp = powerlifters[i];
                        powerlifters[i] = powerlifters[i + 1];
                        powerlifters[i + 1] = temp;
                    }
                }
            }

            // Increment outer iterations, for optimization purposes
            outIterations++;
        }

    }

    public void findNthPercentile(float percentile, String sortBy){
        /*
        ----------------------------------------------------
         Finds and prints all powerlifters that fall in the
         specified percentile sorted by the specified argument
        ----------------------------------------------------
        Arguments:
            percentile: (float) Percentile. Takes on values between 0 and 100.
                Percentile of 75 is a data point where 75% of data falls below this point

            sortBy: (float) lift to sort the array by
            takes on 4 different values:
                1. squat
                2. bench
                3. deadlift
                4. total
         ----------------------------------------------------
         If sortBy == squat and percentile == 90,
            the method will print the bottom 90% squatters.
         */

        float[] percentileArray = new float[currentLength];
        float[] sortedArray = sortArray(percentileArray);

        // Fill in the sorted array with lifts based on the sortBy
        for (int i = 0; i < currentLength; i++) {
            sortedArray[i] = powerlifters[i].getBest(sortBy);
        }

        // Find the nth percentile
        int percentileIndex = (int) (Math.floor((percentile * currentLength / 100)) - 1);
        float percentileValue = sortedArray[percentileIndex];

        System.out.println("Powerlifters that fall under the " + percentile + "th percentile: ");
        System.out.println("----------------------------------------------------");

        // Print all powerlifters with the specified lift less than the percentile value.
        for (Powerlifter p : powerlifters){
            if (p.getBest(sortBy) < percentileValue){
                p.printShortInfo();
            }
        }
    }


    // Method to sort an array (used to find the nth percentile of an array)
    private float[] sortArray(float[] array){
        /*
        ----------------------------------------------------
        Sorts a given array.
        ----------------------------------------------------
        Arguments:
            array: (float[]) Array to sort.
        ----------------------------------------------------
        Returns:
            Sorted copy of the given array.
        ----------------------------------------------------
         */

        int outIterations = 0;
        boolean isSorted = false;

        float[] sortedArray = Arrays.copyOf(array, array.length);

        while(!isSorted){
            isSorted = true;

            for (int i = 0; i < (sortedArray.length - outIterations - 1); i++){
                if (sortedArray[i] > sortedArray[i + 1]){
                    isSorted = false;
                    // Swap the elements
                    float temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i + 1];
                    sortedArray[i + 1] = temp;
                }
            }

            outIterations++;
        }
        return sortedArray;
    }
}

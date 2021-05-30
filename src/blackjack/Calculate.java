package blackjack;

import java.util.Arrays;

/**
 * Class containing static functions to take in an array of doubles (end bankrolls, in our use case),
 * and perform statistical calculations on them, including mean, standard deviation and percentile points
 */
public class Calculate {

    /**
     *
     * @param dataSet a double array representing end bankrolls after a simulation
     * @return - the mean of values in dataSet
     */
    public static double mean(double[] dataSet){
        double total = 0;
        for(double each : dataSet){
            total += each;
        }
        return total / dataSet.length;
    }

    /**
     *
     * @param dataSet a double array representing end bankrolls after a simulation
     * @return - the standard deviation of values in dataSet
     */
    public static double standardDeviation(double[] dataSet){
        double standardDeviation = 0;
        double mean = mean(dataSet);
        double sumOfSquaredDifferences = 0;
        for(int i = 0; i < dataSet.length; i++){
            sumOfSquaredDifferences += Math.pow((dataSet[i] - mean), 2.0);
        }
        double meanOfSquaredDifferences = sumOfSquaredDifferences / dataSet.length;
        standardDeviation = Math.sqrt(meanOfSquaredDifferences);

        return standardDeviation;
    }

    /**
     *
     * @param dataSet a double array representing end bankrolls after a simulation
     * @param percentile a decimal percentile, 0.75 = 75th percentile
     * @return - a double value taken from dataSet that is the percentile value of percentile
     */
    public static double percentile(double[] dataSet, double percentile){
        Arrays.sort(dataSet);
        int n = (int) Math.round(percentile * dataSet.length) - 1;
        return dataSet[n];
    }

    /**
     *
     * @param dataSet a double array representing end bankrolls after a simulation
     * @return - the smallest value of the values in dataSet
     */
    public static double min(double[] dataSet){
        Arrays.sort(dataSet);
        return dataSet[0];
    }

    /**
     * @param dataSet a double array representing end bankrolls after a simulation
     * @return - the largest value of the values in dataSet
     */
    public static double max(double[] dataSet){
        Arrays.sort(dataSet);
        return dataSet[dataSet.length - 1];
    }

    public static void main(String[] args) {
        double[] deviationData = {9, 2, 5, 4, 12, 7, 8, 11, 9, 3, 7, 4, 12, 5, 4, 10, 9, 6, 9, 4};
        System.out.println(mean(deviationData));
        System.out.println(standardDeviation(deviationData));

        double[] percentileData = {75, 77, 78, 78, 80, 81, 81, 82, 83, 84, 84, 84, 85, 87, 87, 88, 88, 88, 89, 90};
        System.out.println(percentile(percentileData, 0.95));
        System.out.println(min(percentileData));
        System.out.println(max(percentileData));

    }
}

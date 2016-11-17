package edu.ucd.ml.knn.helper;

/**
 * Created by Zuhaib on 11/17/2016.
 */
public class MathsHelper {
    public static double sqrt(double number) {
        double t;

        double squareRoot = number / 2;

        do {
            t = squareRoot;
            squareRoot = (t + (number / t)) / 2;
        } while ((t - squareRoot) != 0);

        return squareRoot;
    }

}

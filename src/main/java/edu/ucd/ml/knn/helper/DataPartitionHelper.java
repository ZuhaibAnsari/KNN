package edu.ucd.ml.knn.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zuhaib on 11/10/2016.
 * This is the helper class for partitioning the data into training and test data
 */
public class DataPartitionHelper {

    //Setting the training data to be 30% of the actual data
    private final int TRAINING_SET_SPLIT_PERCENTAGE = 30;

    private double[][] testDataArrayMatrix;
    private double[][] trainingArrayDataMatrix;

    /**
     * This method is used to split the data into training and test data
     * @param sparseDataMatrix is the actual complete data set
     */
    public void splitDataIntoTrainingAndTestFromMatrix(double[][] sparseDataMatrix){
        int rowsOfData=sparseDataMatrix.length-1;
        int numRowsInTestSet     = rowsOfData* TRAINING_SET_SPLIT_PERCENTAGE/100;
        int randomSeed;
        int testDataRowCounter=0;
        int trainingDataRowCounter=0;
        int[] randomRowNumberArray = new int[rowsOfData];

        trainingArrayDataMatrix = new double[rowsOfData-numRowsInTestSet][sparseDataMatrix[rowsOfData].length] ;
        testDataArrayMatrix = new double[numRowsInTestSet][(sparseDataMatrix[rowsOfData].length)] ;

        int lastCoulumn=sparseDataMatrix[rowsOfData].length-1;
        for (int i = 0; i < randomRowNumberArray.length; i++) {
            randomRowNumberArray[i] = i;
        }
        Collections.shuffle(Arrays.asList(randomRowNumberArray));

        for (int aRandomRowNumberArray : randomRowNumberArray) {
            randomSeed = aRandomRowNumberArray;
            if (randomSeed % 2 == 0 && testDataRowCounter < numRowsInTestSet) {

                testDataArrayMatrix[testDataRowCounter] = sparseDataMatrix[randomSeed];
                testDataArrayMatrix[testDataRowCounter][lastCoulumn] = randomSeed + 1;
                testDataRowCounter++;
            } else {
                trainingArrayDataMatrix[trainingDataRowCounter] = sparseDataMatrix[randomSeed];
                trainingArrayDataMatrix[trainingDataRowCounter][lastCoulumn] = randomSeed + 1;
                trainingDataRowCounter++;
            }
        }
    }

    /**
     * This method is used to get the class labels for the data
     * @param filename is the name of file containing the call labels
     * @return a map of document number and its class labels
     * @throws IOException
     */
    public Map<Double,String> getClassLabelOfData(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

        String lineOfDataLabel;
        String[] arrayOfClassLabels;
        Map<Double,String> mapOfClassLabel = new HashMap<>();

        //Read the file and update the map with the class labels
        while (true) {
            lineOfDataLabel = bufferedReader.readLine();
            if (lineOfDataLabel == null)
                break;
            arrayOfClassLabels = lineOfDataLabel.split(",");
            double documentNumber = (Double.parseDouble(arrayOfClassLabels[0].trim()));
            String classLabel = (arrayOfClassLabels[1].trim());
            mapOfClassLabel.put(documentNumber,classLabel);

        }
        return mapOfClassLabel;
    }

    public double[][] getTestDataArrayMatrix() {
        return testDataArrayMatrix;
    }

    public void setTestDataArrayMatrix(double[][] testDataArrayMatrix) {
        this.testDataArrayMatrix = testDataArrayMatrix;
    }

    public double[][] getTrainingArrayDataMatrix() {
        return trainingArrayDataMatrix;
    }

    public void setTrainingArrayDataMatrix(double[][] trainingArrayDataMatrix) {
        this.trainingArrayDataMatrix = trainingArrayDataMatrix;
    }
}

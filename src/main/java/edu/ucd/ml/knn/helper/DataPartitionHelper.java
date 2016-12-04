package edu.ucd.ml.knn.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
        int testDataRowCounter=0;
        int trainingDataRowCounter=0;

        //This is the array which will be used to shuffle the test and training data
        List<Integer> randomRowNumberArray = new ArrayList<>(rowsOfData);

        trainingArrayDataMatrix = new double[rowsOfData-numRowsInTestSet][sparseDataMatrix[rowsOfData].length] ;
        testDataArrayMatrix = new double[numRowsInTestSet][(sparseDataMatrix[rowsOfData].length)] ;

        int lastColumn=sparseDataMatrix[rowsOfData].length-1;
        for (int i = 0; i < rowsOfData; i++) {
            randomRowNumberArray.add(i);
        }

        //Shuffling the array of data index
        Collections.shuffle(randomRowNumberArray);

        //Partitioning the data indexes into test and training data
        for (int randomizedDataIndexes : randomRowNumberArray) {
            if (testDataRowCounter < numRowsInTestSet) {
                testDataArrayMatrix[testDataRowCounter] = sparseDataMatrix[randomizedDataIndexes];
                testDataArrayMatrix[testDataRowCounter][lastColumn] = randomizedDataIndexes + 1;
                testDataRowCounter++;
            } else {
                trainingArrayDataMatrix[trainingDataRowCounter] = sparseDataMatrix[randomizedDataIndexes];
                trainingArrayDataMatrix[trainingDataRowCounter][lastColumn] = randomizedDataIndexes + 1;
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));

        String lineOfDataLabel;
        String[] arrayOfClassLabels;
        Map<Double,String> mapOfClassLabel = new HashMap<>();

        //Read the file and update the map with the class labels
        while (true) {
            lineOfDataLabel = bufferedReader.readLine();
            if (lineOfDataLabel == null)
                break;
            //Splitting the line to get the document number and its class label
            arrayOfClassLabels = lineOfDataLabel.split(",");
            double documentNumber = (Double.parseDouble(arrayOfClassLabels[0].trim()));
            String classLabel = (arrayOfClassLabels[1].trim());

            //Updating the hash map with the class label as value and document number as key
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

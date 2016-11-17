package edu.ucd.ml.knn.helper;

import org.la4j.Matrix;
import org.la4j.iterator.VectorIterator;

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
 */
public class DataPartitionHelper {

    private final int TRAINING_SET_SPLIT_PERCENTAGE = 30;

    private double[][] testDataArrayMatrix;
    private double[][] trainingArrayDataMatrix;

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

    public double[] calculateDocumentCountForIDF(Matrix sparseMatrix){
        double[] idfMatrix = new double[sparseMatrix.columns()];


        int numberofDistinctDocumentsContainingTerm=0;
        for(int i=0;i<sparseMatrix.columns();i++){
            VectorIterator vectorIterator =sparseMatrix.iteratorOfColumn(i);
            while(vectorIterator.hasNext()){
                int column_value=vectorIterator.next().intValue();
                if(column_value>0){
                    numberofDistinctDocumentsContainingTerm++;
                }

                idfMatrix[i]=numberofDistinctDocumentsContainingTerm;
            }
            numberofDistinctDocumentsContainingTerm=0;
        }

        return idfMatrix;
    }

    public double[] calculateTFIDF(Matrix sparseMatrix){

            int rowCount=0;
            while(rowCount< sparseMatrix.rows()){


                VectorIterator rowVectorIterator = sparseMatrix.iteratorOfRow(rowCount);

                while(rowVectorIterator.hasNext()){
                    int column_number=rowVectorIterator.index();
                    int column_value=rowVectorIterator.next().intValue();

                }

                rowCount++;

            }



        return null;
    }

    public Map<Double,String> getClassLabelOfData(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

        String lineOfDataLabel;
        String[] arrayOfClassLabels;
        Map<Double,String> mapOfClassLabel = new HashMap<>();

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

package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.DataMatrix;
import org.la4j.Matrix;
import org.la4j.iterator.VectorIterator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Zuhaib on 11/10/2016.
 */
public class DataPartitionHelper {

    final int TRAINING_SET_SPLIT_PERCENTAGE = 30;

    private Map<Integer,DataMatrix> testDataMatrix;
    private Map<Integer,DataMatrix> trainingDataMatrix;

    public void splitDataIntoTrainingAndTest(Matrix sparseDataMatrix){
        int rowsOfData=sparseDataMatrix.rows();
        int numRowsInTestSet     = rowsOfData* TRAINING_SET_SPLIT_PERCENTAGE/100;
        int randomSeed=0;
        int testDataRowCounter=0;
        int trainingDataRowCounter=0;
        int[] randomRowNumberArray = new int[rowsOfData];
        DataMatrix dataMatrix =null;

        trainingDataMatrix = new LinkedHashMap<Integer, DataMatrix>();
        testDataMatrix = new LinkedHashMap<Integer, DataMatrix>();

        for (int i = 0; i < randomRowNumberArray.length; i++) {
            randomRowNumberArray[i] = i;
        }
        Collections.shuffle(Arrays.asList(randomRowNumberArray));

        for(int i=0;i <randomRowNumberArray.length;i++){
            randomSeed =randomRowNumberArray[i];
            if(randomSeed % 2==0 && testDataRowCounter <numRowsInTestSet){
                dataMatrix = getColumnDataMatrix(sparseDataMatrix, randomSeed);
                testDataMatrix.put(randomSeed,dataMatrix);
                testDataRowCounter++;
        }
        else{
                dataMatrix = getColumnDataMatrix(sparseDataMatrix, randomSeed);
                trainingDataMatrix.put(randomSeed,dataMatrix);
                trainingDataRowCounter++;
        }
        }
    }

    private DataMatrix getColumnDataMatrix(Matrix sparseDataMatrix, int randomSeed) {
        VectorIterator vectorIterator =sparseDataMatrix.iteratorOfRow(randomSeed);
        DataMatrix dataMatrix = new DataMatrix(sparseDataMatrix.columns());

        while(vectorIterator.hasNext()){
                int column_number=vectorIterator.index();
                int column_value=vectorIterator.next().intValue();
                dataMatrix.getColumnIndexes()[column_number+1]= column_value;
        }
        return dataMatrix;
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
                System.out.println(i);
                idfMatrix[i]=numberofDistinctDocumentsContainingTerm;
            }
            numberofDistinctDocumentsContainingTerm=0;
        }

        return idfMatrix;
    }

    public double[] calculateTFIDF(Matrix sparseMatrix){

            int rowCount=0;
            while(rowCount< sparseMatrix.rows()){

                System.out.println("Row number is"+rowCount);
                VectorIterator rowVectorIterator = sparseMatrix.iteratorOfRow(rowCount);
                int count=0;
                while(rowVectorIterator.hasNext()){
                    int column_number=rowVectorIterator.index();
                    int column_value=rowVectorIterator.next().intValue();
                    if (column_value > 0) {
                        System.out.println("********"+column_number);
                    }
                }
                count++;
                rowCount++;

            }



        return null;
    }

    public Map<Integer,DataMatrix> getTestDataMatrix() {
        return testDataMatrix;
    }

    public void setTestDataMatrix(Map<Integer,DataMatrix> testDataMatrix) {
        this.testDataMatrix = testDataMatrix;
    }

    public Map<Integer,DataMatrix> getTrainingDataMatrix() {
        return trainingDataMatrix;
    }

    public void setTrainingDataMatrix(Map<Integer,DataMatrix> trainingDataMatrix) {
        this.trainingDataMatrix = trainingDataMatrix;
    }

}

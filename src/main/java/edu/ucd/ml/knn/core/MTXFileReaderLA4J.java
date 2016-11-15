package edu.ucd.ml.knn.core;

import edu.ucd.ml.knn.datastruc.CosineDistance;
import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;
import edu.ucd.ml.knn.datastruc.DataMatrix;
import edu.ucd.ml.knn.helper.DataPartitionHelper;
import org.la4j.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zuhaib on 11/6/2016.
 */
public class MTXFileReaderLA4J {

    private Matrix sparseDataMatrix;
    private int numberOfUniqueDocuments;
    private int numberOfUniqueWords;
    private int numberOfNonZeroValues;


    public Matrix readMatrixMarketData(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String lineOfMTXFile = bufferedReader.readLine();
        boolean isCommentLine = true;

        while (isCommentLine) {
            lineOfMTXFile = bufferedReader.readLine();
            isCommentLine = lineOfMTXFile.startsWith("%");
        }

        String[] mtxFileData = lineOfMTXFile.split(" ");
        numberOfUniqueDocuments = (Integer.parseInt(mtxFileData[0].trim()));
        numberOfUniqueWords = (Integer.parseInt(mtxFileData[1].trim()));
        numberOfNonZeroValues = (Integer.parseInt(mtxFileData[2].trim()));

        setNumberOfUniqueDocuments(numberOfUniqueDocuments);
        setNumberOfUniqueWords(numberOfUniqueWords);
        setNumberOfNonZeroValues(numberOfNonZeroValues);

        sparseDataMatrix = new CRSMatrix(numberOfUniqueDocuments,numberOfUniqueWords);
        while (true) {
            lineOfMTXFile = bufferedReader.readLine();
            if (lineOfMTXFile == null)
                break;
            mtxFileData = lineOfMTXFile.split("( )+");
            int i = (Integer.parseInt(mtxFileData[0].trim()));
            int j = (Integer.parseInt(mtxFileData[1].trim()));
            int x = (Integer.parseInt(mtxFileData[2].trim()));
            System.out.println(i+" "+j+" "+x);
            sparseDataMatrix.set(i-1,j-1,x);
        }
        return sparseDataMatrix;
    }

    public CosineSimilarityResults[] calculateCosineSimilarity(Map<Integer,DataMatrix> testDataMatrix, Map<Integer,DataMatrix> trainingDataMatrix){


       Iterator<Integer> testDataKeyIterator= testDataMatrix.keySet().iterator();

        CosineSimilarityResults[] cosineSimilarityResults = new CosineSimilarityResults[testDataMatrix.size()+1];
int count =0;
        while(testDataKeyIterator.hasNext()){
            int testDataKey=testDataKeyIterator.next();
            count++;
            System.out.println(count);
            int[] testDataColumns=testDataMatrix.get(testDataKey).getColumnIndexes();
            int columnCount=testDataColumns.length;
            Iterator<Integer> trainingDataKeyIterator= trainingDataMatrix.keySet().iterator();
            CosineDistance[] cosineDistances = new CosineDistance[trainingDataMatrix.size()];
            double dotProductOfVectors = 0, magnitudeOfTrainingVector = 0, magnitureOfTestVector = 0;
            int trainingRowsCount=0;
            while(trainingDataKeyIterator.hasNext()){
                int trainingDataKey=trainingDataKeyIterator.next();
                int[] trainingDataColumns=trainingDataMatrix.get(trainingDataKey).getColumnIndexes();
                for(int i=0;i<columnCount;i++){
                    dotProductOfVectors += trainingDataColumns[i] * testDataColumns[i];
                    magnitudeOfTrainingVector += Math.pow(trainingDataColumns[i], 2);
                    magnitureOfTestVector += Math.pow(testDataColumns[i], 2);

                }
                magnitudeOfTrainingVector = Math.sqrt(magnitudeOfTrainingVector);
                magnitureOfTestVector = Math.sqrt(magnitureOfTestVector);
                cosineDistances[trainingRowsCount]= new CosineDistance(trainingDataKey,dotProductOfVectors / (magnitudeOfTrainingVector * magnitureOfTestVector));
                trainingRowsCount++;
            }

            cosineSimilarityResults[count]= new CosineSimilarityResults(testDataKey,cosineDistances);

        }

        return cosineSimilarityResults;
        /*double distanceVector[] = new double[mtxFileReaderLA4J.getNumberOfUniqueDocuments()];
        double euclideanDistance[] = new double[mtxFileReaderLA4J.getNumberOfUniqueDocuments()];
        for(int i=0;i <mtxFileReaderLA4J.getNumberOfUniqueDocuments();i++){
            for(int j=0;j <mtxFileReaderLA4J.getNumberOfUniqueWords();j++){
                distanceVector[i]+=Math.pow(sparseMatrix.get(i,j)-trainingData.get(mtxFileReaderLA4J.getNumberOfUniqueDocuments()-1,j),2);
            }

        }
        for (int i=0;i<mtxFileReaderLA4J.getNumberOfUniqueDocuments();i++){
            euclideanDistance[i]=Math.sqrt(distanceVector[i]);
        }*/
    }

   public CosineSimilarityResults[] findNearestNeighbours(int numberOfNeighbours, CosineSimilarityResults[] cosineSimilarityResult, Map<Integer,String> mapOfClassLabel ){
       for (int i=1;i<cosineSimilarityResult.length;i++) {
           if(null!=cosineSimilarityResult[i]){
               cosineSimilarityResult[i].calculateKnn(numberOfNeighbours);
           }
           int[] knnIndexes = cosineSimilarityResult[i].getKnnIndexes();
           String[] classLabels = new String[knnIndexes.length];
           int speciesCount = 0;
           for (int knnIndex : knnIndexes) {
               classLabels[speciesCount++] = mapOfClassLabel.get(knnIndex);
           }
           cosineSimilarityResult[i].setClassLabels(classLabels);
           cosineSimilarityResult[i].getPredictedClassLabelFromData();
       }
       return cosineSimilarityResult;

   }

    public double calculateAccuracy(Map<Integer,DataMatrix> testData,Map<Integer,String> mapOfClassLabels, CosineSimilarityResults[] knnResults) {
        int testDatapointIndex ;
        int trueCount = 0;
        int testDataRowCounter=1;
       Iterator testDataIterator = testData.keySet().iterator();

        while(testDataIterator.hasNext()) {
            testDatapointIndex = ((int) testDataIterator.next())+1;

            String actualClassLabel = mapOfClassLabels.get(testDatapointIndex);

            if (actualClassLabel.equals(knnResults[testDataRowCounter].getPredictedClassLabel())) {
                trueCount++;
            }

            testDataRowCounter++;
        }
        System.out.println(trueCount+"        "+testData.size());
            double accuracy =(Double.valueOf(trueCount) / Double.valueOf(testData.size()))* 100.0;
        System.out.println("Accuracy is ssss "+accuracy);
        return  accuracy;
    }

    public Map<Integer,String> getClassLabelOfData(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String lineOfDataLabel = "";
        String[] arrayOfClassLabels = null;
        Map<Integer,String> mapOfClassLabel = new HashMap<Integer, String>();

        while (true) {
            lineOfDataLabel = bufferedReader.readLine();
            if (lineOfDataLabel == null)
                break;
            arrayOfClassLabels = lineOfDataLabel.split(",");
            int documentNumber = (Integer.parseInt(arrayOfClassLabels[0].trim()));
            String classLabel = (arrayOfClassLabels[1].trim());
            mapOfClassLabel.put(documentNumber,classLabel);

        }
        return mapOfClassLabel;
    }
    public int getNumberOfUniqueDocuments() {
        return numberOfUniqueDocuments;
    }

    public void setNumberOfUniqueDocuments(int numberOfUniqueDocuments) {
        this.numberOfUniqueDocuments = numberOfUniqueDocuments;
    }

    public int getNumberOfUniqueWords() {
        return numberOfUniqueWords;
    }

    public void setNumberOfUniqueWords(int numberOfUniqueWords) {
        this.numberOfUniqueWords = numberOfUniqueWords;
    }

    public int getNumberOfNonZeroValues() {
        return numberOfNonZeroValues;
    }

    public void setNumberOfNonZeroValues(int numberOfNonZeroValues) {
        this.numberOfNonZeroValues = numberOfNonZeroValues;
    }


    public static void main(String args[]){
        String fileName ="C:\\Users\\Gurjeet\\IdeaProjects\\KNN\\src\\main\\resources\\news_articles.mtx";
        String classLabelFile="C:\\Users\\Gurjeet\\IdeaProjects\\KNN\\src\\main\\resources\\news_articles.labels";

        try {
            MTXFileReaderLA4J mtxFileReader = new MTXFileReaderLA4J();
            Matrix sparseMatrix = mtxFileReader.readMatrixMarketData(fileName);
            DataPartitionHelper dataPartitionHelper = new DataPartitionHelper();
//            dataPartitionHelper.splitDataIntoTrainingAndTest(sparseMatrix);
           // dataPartitionHelper.calculateDocumentCountForIDF(sparseMatrix);

            dataPartitionHelper.splitDataIntoTrainingAndTest(sparseMatrix);

            System.out.println(dataPartitionHelper.getTestDataMatrix().size());

            System.out.println(dataPartitionHelper.getTrainingDataMatrix().size());
            Map<Integer,DataMatrix> matrix = dataPartitionHelper.getTestDataMatrix();

            Map<Integer,String> classLabelMap =mtxFileReader.getClassLabelOfData(classLabelFile);

            CosineSimilarityResults[] cosineSimilarityResultses = mtxFileReader.calculateCosineSimilarity(dataPartitionHelper.getTestDataMatrix(),dataPartitionHelper.getTrainingDataMatrix());

            cosineSimilarityResultses = mtxFileReader.findNearestNeighbours(9,cosineSimilarityResultses,classLabelMap);

            System.out.println("Accuracy is "+mtxFileReader.calculateAccuracy(dataPartitionHelper.getTestDataMatrix(),classLabelMap,cosineSimilarityResultses));




        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

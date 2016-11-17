package edu.ucd.ml.knn.core;

import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;
import edu.ucd.ml.knn.helper.CosineSimilarityHelper;
import edu.ucd.ml.knn.helper.DataPartitionHelper;
import edu.ucd.ml.knn.helper.KNNHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Zuhaib on 11/6/2016.
 */
public class MTXFileReaderLA4J {
    public double[][] readMatrixMarketDataIntoArray(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String lineOfMTXFile = bufferedReader.readLine();

        boolean isCommentLine = true;
        while (isCommentLine) {
            lineOfMTXFile = bufferedReader.readLine();
            isCommentLine = lineOfMTXFile.startsWith("%");
        }

        String[] mtxFileData = lineOfMTXFile.split(" ");
        int numberOfUniqueDocuments = (Integer.parseInt(mtxFileData[0].trim()));
        int numberOfUniqueWords = (Integer.parseInt(mtxFileData[1].trim()));

        double[][] dataMatrix = new double[numberOfUniqueDocuments + 1][numberOfUniqueWords + 1];
        while (true) {
            lineOfMTXFile = bufferedReader.readLine();
            if (lineOfMTXFile == null)
                break;
            mtxFileData = lineOfMTXFile.split("( )+");
            int rowNumber = (Integer.parseInt(mtxFileData[0].trim())) - 1;
            int columnNumber = (Integer.parseInt(mtxFileData[1].trim())) - 1;
            int frequency = (Integer.parseInt(mtxFileData[2].trim()));

            dataMatrix[rowNumber][columnNumber] = frequency;

        }
        return dataMatrix;
    }


    public static void main(String args[]) {
        try {
            System.out.println("Please enter the number of Nearest neighbours for the classifier :");
            Scanner scanner = new Scanner(System.in);

            int kNeighbours = scanner.nextInt();

            System.out.println("Do you want to use Weighted KNN .. Enter Y for Yes:");
            String weightedKNNChoice=scanner.next();
            boolean isWeightedKNN=false;
            if("Y".equals(weightedKNNChoice.toUpperCase())){
                isWeightedKNN = true;
            }

            String fileName = MTXFileReaderLA4J.class.getClassLoader().getResource("news_articles.mtx")
                    .getPath();
            String classLabelFile = MTXFileReaderLA4J.class.getClassLoader().getResource("news_articles.labels")
                    .getPath();

            MTXFileReaderLA4J mtxFileReader = new MTXFileReaderLA4J();
            DataPartitionHelper dataPartitionHelper = new DataPartitionHelper();
            CosineSimilarityHelper cosineSimilarityHelper = new CosineSimilarityHelper();
            KNNHelper knnHelper = new KNNHelper();
            if(null !=fileName && null != classLabelFile){
                double[][] dataMatrix = mtxFileReader.readMatrixMarketDataIntoArray(fileName);

                dataPartitionHelper.splitDataIntoTrainingAndTestFromMatrix(dataMatrix);
                Map<Double, String> classLabelMap = dataPartitionHelper.getClassLabelOfData(classLabelFile);

                double[][] testData = dataPartitionHelper.getTestDataArrayMatrix();
                double[][] trainingData = dataPartitionHelper.getTrainingArrayDataMatrix();

                CosineSimilarityResults[] cosineSimilarityResults = cosineSimilarityHelper.calculateCosineSimilarity(testData, trainingData);
                cosineSimilarityResults = knnHelper.findNearestNeighbours(kNeighbours, cosineSimilarityResults, classLabelMap, isWeightedKNN);

                System.out.println("Accuracy of the classifier is " + knnHelper.calculateAccuracy(dataPartitionHelper.getTestDataArrayMatrix(), classLabelMap, cosineSimilarityResults));
            }
            else {
                System.out.println("Unable to Read Matrix market file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

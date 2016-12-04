package edu.ucd.ml;

import edu.ucd.ml.knn.core.MatrixMarketFileReader;
import edu.ucd.ml.knn.datastruc.CosineSimilarityOutput;
import edu.ucd.ml.knn.helper.CosineSimilarityHelper;
import edu.ucd.ml.knn.helper.DataPartitionHelper;
import edu.ucd.ml.knn.helper.KNNHelper;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


/**
 * This class is used to do the execution of Classifier
 */
public class KNNClassifierRunner
{
    public static void main(String args[]) {
        try {

            System.out.println("Please enter the number of Nearest neighbours for the classifier :");
            Scanner scanner = new Scanner(System.in);

            int kNeighbours = scanner.nextInt();
            while(kNeighbours==0){
                System.out.println("Incorrect input ... Please enter the minimum number as 1 ");
                kNeighbours = scanner.nextInt();
            }

            System.out.println("Do you want to use Weighted KNN .. Enter y for Yes or n for No:");
            String weightedKNNChoice=scanner.next();
            boolean isWeightedKNN=false;
            while (!(weightedKNNChoice.toLowerCase().equals("y") || weightedKNNChoice.toLowerCase().equals("n"))){
                System.out.println("Incorrect input ... Please enter either y or n ");
                weightedKNNChoice=scanner.next();
            }
            if("Y".equals(weightedKNNChoice.toUpperCase())){
                isWeightedKNN = true;
            }
            double time= System.currentTimeMillis();
            String fileName = "/news_articles.mtx";
            String classLabelFile = "/news_articles.labels";

            MatrixMarketFileReader mtxFileReader = new MatrixMarketFileReader();
            DataPartitionHelper dataPartitionHelper = new DataPartitionHelper();
            CosineSimilarityHelper cosineSimilarityHelper = new CosineSimilarityHelper();
            KNNHelper knnHelper = new KNNHelper();
            if(null !=fileName && null != classLabelFile){
                System.out.println("\nReading Matrix file ...");
                double[][] dataMatrix = mtxFileReader.readMatrixMarketDataIntoArray(fileName);

                System.out.println("\nPartitioning Data into Test and Training data ...");
                dataPartitionHelper.splitDataIntoTrainingAndTestFromMatrix(dataMatrix);

                double[][] testData = dataPartitionHelper.getTestDataArrayMatrix();
                double[][] trainingData = dataPartitionHelper.getTrainingArrayDataMatrix();

                System.out.println("\nCalculating Cosine Similarity ...");
                CosineSimilarityOutput[] cosineSimilarityResults = cosineSimilarityHelper.calculateCosineSimilarity(testData, trainingData,isWeightedKNN);

                Map<Double, String> classLabelMap = dataPartitionHelper.getClassLabelOfData(classLabelFile);

                System.out.println("\nFinding "+kNeighbours+" Nearest Neighbours ..");
                cosineSimilarityResults = knnHelper.findNearestNeighbours(kNeighbours, cosineSimilarityResults, classLabelMap, isWeightedKNN);

                System.out.println("\n\nAccuracy of the classifier is " + knnHelper.calculateAccuracy(dataPartitionHelper.getTestDataArrayMatrix(), classLabelMap, cosineSimilarityResults));
                double time2 = System.currentTimeMillis();

                System.out.println("\nTime taken to run the classifier : "+(time2-time)/1000+" seconds");
            }
            else {
                System.out.println("Unable to Read Matrix market file");
            }
        } catch (IOException e) {
            System.out.println(" Exception occurred in program execution with below stack trace :");
            e.printStackTrace();
        }
    }
}

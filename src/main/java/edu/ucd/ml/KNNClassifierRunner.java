package edu.ucd.ml;

import edu.ucd.ml.knn.core.MatrixMarketFileReader;
import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;
import edu.ucd.ml.knn.helper.CosineSimilarityHelper;
import edu.ucd.ml.knn.helper.DataPartitionHelper;
import edu.ucd.ml.knn.helper.KNNHelper;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


public class KNNClassifierRunner
{
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

            String fileName = MatrixMarketFileReader.class.getClassLoader().getResource("news_articles.mtx")
                    .getPath();
            String classLabelFile = MatrixMarketFileReader.class.getClassLoader().getResource("news_articles.labels")
                    .getPath();

            MatrixMarketFileReader mtxFileReader = new MatrixMarketFileReader();
            DataPartitionHelper dataPartitionHelper = new DataPartitionHelper();
            CosineSimilarityHelper cosineSimilarityHelper = new CosineSimilarityHelper();
            KNNHelper knnHelper = new KNNHelper();
            if(null !=fileName && null != classLabelFile){
                System.out.println("Reading Matrix file ...");
                double[][] dataMatrix = mtxFileReader.readMatrixMarketDataIntoArray(fileName);

                System.out.println("Partitioning Data into Test and Training data ...");
                dataPartitionHelper.splitDataIntoTrainingAndTestFromMatrix(dataMatrix);
                Map<Double, String> classLabelMap = dataPartitionHelper.getClassLabelOfData(classLabelFile);

                double[][] testData = dataPartitionHelper.getTestDataArrayMatrix();
                double[][] trainingData = dataPartitionHelper.getTrainingArrayDataMatrix();

                System.out.println("Calculating Cosine Similarity ...");
                CosineSimilarityResults[] cosineSimilarityResults = cosineSimilarityHelper.calculateCosineSimilarity(testData, trainingData);

                System.out.println("Finding Nearest Neighbours ..");
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

package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.CosineSimilarityOutput;

import java.util.Map;

/**
 * Created by Zuhaib on 11/17/2016.
 */
public class KNNHelper {
    /**
     * This is the method which is used to find the nearest neighbours
     * @param numberOfNeighbours is the count of nearest neighbours to be used in the classifier
     * @param cosineSimilarityResult is the matrix of cosine similarity distances
     * @param mapOfClassLabel is the map containing the class labels with document number as keys
     * @param isWeightedKNN is use to check if the knn is weighted
     * @return an array containing the details of K nearest neighbours
     */
    public CosineSimilarityOutput[] findNearestNeighbours(int numberOfNeighbours, CosineSimilarityOutput[] cosineSimilarityResult, Map<Double,String> mapOfClassLabel, boolean isWeightedKNN ){
        for (CosineSimilarityOutput nearestNeighborSimilarityResults : cosineSimilarityResult) {
            //this is used as a sanity check to restrict the max number of neighbours to the size of test data
            if (null != nearestNeighborSimilarityResults) {
                if (numberOfNeighbours >= nearestNeighborSimilarityResults.getCosineSimilarity().length) {
                    numberOfNeighbours = nearestNeighborSimilarityResults.getCosineSimilarity().length;
                }
                nearestNeighborSimilarityResults.findKNearestNeighbours(numberOfNeighbours, isWeightedKNN);
            }

            //List of the nearest neighboring documents
            int[] nearestNeighbouringDocuments = nearestNeighborSimilarityResults.getNearestNeighbouringDocuments();

            //this is used to find the class labels of the data and predicted class labels
            if(null != nearestNeighbouringDocuments){
                String[] classLabelsOfNearestNeighbors = new String[nearestNeighbouringDocuments.length];
                int neighboursCount = 0;

                //This is used to find the actual class labels of all the nearest neighbors
                for (int nearestNeighborIndex : nearestNeighbouringDocuments) {
                    classLabelsOfNearestNeighbors[neighboursCount++] = mapOfClassLabel.get((double) nearestNeighborIndex);
                }

                // This is used to set the actual class labels to be then used for predicting label
                nearestNeighborSimilarityResults.setClassLabels(classLabelsOfNearestNeighbors);

                //This is used to get the predicted class label of the test data
                nearestNeighborSimilarityResults.getPredictedClassLabelOfTestData(isWeightedKNN);
            }

        }
        return cosineSimilarityResult;

    }

    /**
     * This method is used to find the accuracy of the classifier
     * @param testData is the test data against which the accuracy is to be calculated
     * @param mapOfClassLabels is the map of class labels
     * @param knnResults is the array of results containing the details of the k nearest neighbours
     * @return the accuracy of the classifier
     */
    public double calculateAccuracy(double[][] testData,Map<Double,String> mapOfClassLabels, CosineSimilarityOutput[] knnResults) {

        //This is used to keep track of the correctly predicted class labels count
        int correctlyPredictedClassLabelsCount = 0;

        //This is used to calculate the correctly predicted class label count
        for (int testDataIndex=0; testDataIndex < testData.length;testDataIndex++){
            //Fetching the actual class label of the data
            String actualClassLabel = mapOfClassLabels.get(testData[testDataIndex][(testData[testDataIndex].length-1)]);

            //Comparing the predicted and actual class label to find the correctly predicted class label count
            if (actualClassLabel.equals(knnResults[testDataIndex].getPredictedClassLabel())) {
                correctlyPredictedClassLabelsCount++;
            }
        }
        return (double) (correctlyPredictedClassLabelsCount / (double) testData.length) * 100.0;
    }

}

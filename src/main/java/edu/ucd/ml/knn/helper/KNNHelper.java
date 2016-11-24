package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;

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
    public CosineSimilarityResults[] findNearestNeighbours(int numberOfNeighbours, CosineSimilarityResults[] cosineSimilarityResult, Map<Double,String> mapOfClassLabel, boolean isWeightedKNN ){
        for (CosineSimilarityResults aCosineSimilarityResult : cosineSimilarityResult) {
            //this is used as a sanity check to restrict the max number of neighbours to the size of test data
            if (null != aCosineSimilarityResult) {
                if (numberOfNeighbours >= aCosineSimilarityResult.getDistances().length) {
                    numberOfNeighbours = aCosineSimilarityResult.getDistances().length;
                }
                aCosineSimilarityResult.findKNearestNeighbours(numberOfNeighbours, isWeightedKNN);
            }

            int[] knnIndexes = aCosineSimilarityResult.getKnnIndexes();

            //this is used to find the class labels of the data and predicted class labels
            if(null != knnIndexes){
                String[] classLabels = new String[knnIndexes.length];
                int neighboursCount = 0;
                for (int knnIndex : knnIndexes) {
                    classLabels[neighboursCount++] = mapOfClassLabel.get((double) knnIndex);
                }
                aCosineSimilarityResult.setClassLabels(classLabels);
                aCosineSimilarityResult.getPredictedClassLabelFromData(isWeightedKNN);
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
    public double calculateAccuracy(double[][] testData,Map<Double,String> mapOfClassLabels, CosineSimilarityResults[] knnResults) {
        int actualCount = 0;
        for (int i=0; i < testData.length;i++){
            String actualClassLabel = mapOfClassLabels.get(testData[i][(testData[i].length-1)]);
            if (actualClassLabel.equals(knnResults[i].getPredictedClassLabel())) {
                actualCount++;
            }
        }
        return (double) (actualCount / (double) testData.length) * 100.0;
    }

}

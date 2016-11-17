package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;

import java.util.Map;

/**
 * Created by Zuhaib on 11/17/2016.
 */
public class KNNHelper {
    public CosineSimilarityResults[] findNearestNeighbours(int numberOfNeighbours, CosineSimilarityResults[] cosineSimilarityResult, Map<Double,String> mapOfClassLabel, boolean isWeightedKNN ){
        for (CosineSimilarityResults aCosineSimilarityResult : cosineSimilarityResult) {
            if (null != aCosineSimilarityResult) {
                if (numberOfNeighbours >= aCosineSimilarityResult.getDistances().length) {
                    numberOfNeighbours = aCosineSimilarityResult.getDistances().length;
                }
                aCosineSimilarityResult.calculateKnn(numberOfNeighbours, isWeightedKNN);
            }
            int[] knnIndexes = aCosineSimilarityResult.getKnnIndexes();
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

    public double calculateAccuracy(double[][] testData,Map<Double,String> mapOfClassLabels, CosineSimilarityResults[] knnResults) {
        int trueCount = 0;
        for (int i=0; i < testData.length;i++){
            String actualClassLabel = mapOfClassLabels.get(testData[i][(testData[i].length-1)]);
            if (actualClassLabel.equals(knnResults[i].getPredictedClassLabel())) {
                trueCount++;
            }
        }
        return (double) trueCount / (double) testData.length * 100.0;
    }

}

package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.CosineDistance;
import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;

/**
 * Created by Zuhaib on 11/17/2016.
 */
public class CosineSimilarityHelper {

    /**
     * This method is used to calculate the cosine similarity
     * @param testDataMatrix this is the test data
     * @param trainingDataMatrix this is the training data
     * @param isWeightedKNN this is to see if it is weightedKNN
     * @return an array of results  of cosine similarity
     */
    public CosineSimilarityResults[] calculateCosineSimilarity(double[][] testDataMatrix, double[][] trainingDataMatrix, boolean isWeightedKNN){
        CosineSimilarityResults[] cosineSimilarityResults = new CosineSimilarityResults[testDataMatrix.length];
        int rowsCount =0;
        for (double[] aTestDataMatrix : testDataMatrix) {
            int columnCount = aTestDataMatrix.length - 1;
            int testDataKey = (int) (aTestDataMatrix[columnCount]);
            int trainingRowsCount = 0;
            CosineDistance[] cosineDistances = new CosineDistance[trainingDataMatrix.length];

            for (double[] aTrainingDataMatrix : trainingDataMatrix) {
                int trainingDataKey = (int) (aTrainingDataMatrix[columnCount]);
                double dotProductOfVectors = 0;
                double magnitudeOfTrainingVector = 0;
                double magnitudeOfTestVector = 0;

                for (int columnNumber = 0; columnNumber < columnCount; columnNumber++) {
                    dotProductOfVectors += aTrainingDataMatrix[columnNumber] * aTestDataMatrix[columnNumber];

                    magnitudeOfTrainingVector += (aTrainingDataMatrix[columnNumber] * aTrainingDataMatrix[columnNumber]);
                    magnitudeOfTestVector += (aTestDataMatrix[columnNumber] * aTestDataMatrix[columnNumber]);

                }

                magnitudeOfTrainingVector = MathsHelper.squareRoot(magnitudeOfTrainingVector);
                magnitudeOfTestVector = MathsHelper.squareRoot(magnitudeOfTestVector);
                double cosineSimilarity=dotProductOfVectors / (magnitudeOfTrainingVector * magnitudeOfTestVector);

                //If it is weighted KNN use a gaussian function to find the weighted similarity
                if(isWeightedKNN){
                    cosineSimilarity= Math.exp(-(cosineSimilarity*cosineSimilarity) / 2) / MathsHelper.squareRoot(2 * Math.PI);
                }

                cosineDistances[trainingRowsCount] = new CosineDistance(trainingDataKey, cosineSimilarity);
                trainingRowsCount++;
            }

            cosineSimilarityResults[rowsCount] = new CosineSimilarityResults(testDataKey, cosineDistances);
            rowsCount++;

        }

        return cosineSimilarityResults;
    }
}

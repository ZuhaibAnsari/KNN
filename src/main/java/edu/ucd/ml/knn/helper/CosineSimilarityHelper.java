package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.CosineSimilarity;
import edu.ucd.ml.knn.datastruc.CosineSimilarityOutput;

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
    public CosineSimilarityOutput[] calculateCosineSimilarity(double[][] testDataMatrix, double[][] trainingDataMatrix, boolean isWeightedKNN){
        CosineSimilarityOutput[] cosineSimilarityResults = new CosineSimilarityOutput[testDataMatrix.length];
        int rowsCount =0;
        for (double[] inputTestData : testDataMatrix) {
            int columnCount = inputTestData.length - 1;
            int testDataKey = (int) (inputTestData[columnCount]);
            int trainingRowsCount = 0;
            CosineSimilarity[] cosineSimilarities = new CosineSimilarity[trainingDataMatrix.length];

            for (double[] trainingData : trainingDataMatrix) {
                int trainingDataKey = (int) (trainingData[columnCount]);
                double dotProductOfVectors = 0;
                double magnitudeOfTrainingVector = 0;
                double magnitudeOfTestVector = 0;

                //Calculating the dot product of the vectors to find the cosine similarity
                for (int columnNumber = 0; columnNumber < columnCount; columnNumber++) {
                    dotProductOfVectors += trainingData[columnNumber] * inputTestData[columnNumber];

                    magnitudeOfTrainingVector += (trainingData[columnNumber] * trainingData[columnNumber]);
                    magnitudeOfTestVector += (inputTestData[columnNumber] * inputTestData[columnNumber]);

                }

                magnitudeOfTrainingVector = MathsHelper.squareRoot(magnitudeOfTrainingVector);
                magnitudeOfTestVector = MathsHelper.squareRoot(magnitudeOfTestVector);

                //The cosine similarity for the test data document
                double cosineSimilarity=dotProductOfVectors / (magnitudeOfTrainingVector * magnitudeOfTestVector);

                //If it is weighted KNN use a gaussian function to find the weighted similarity
                if(isWeightedKNN){
                    cosineSimilarity= Math.exp(-(cosineSimilarity*cosineSimilarity) / 2) / MathsHelper.squareRoot(2 * Math.PI);
                }

                //updating the list of cosine similarity results for corresponding test data
                cosineSimilarities[trainingRowsCount] = new CosineSimilarity(trainingDataKey, cosineSimilarity);
                trainingRowsCount++;
            }

            cosineSimilarityResults[rowsCount] = new CosineSimilarityOutput(testDataKey, cosineSimilarities);
            rowsCount++;

        }

        return cosineSimilarityResults;
    }
}

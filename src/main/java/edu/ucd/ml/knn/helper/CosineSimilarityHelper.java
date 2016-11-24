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
     * @return an array of results  of cosine similarity
     */
    public CosineSimilarityResults[] calculateCosineSimilarity(double[][] testDataMatrix, double[][] trainingDataMatrix){
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
                cosineDistances[trainingRowsCount] = new CosineDistance(trainingDataKey, dotProductOfVectors / (magnitudeOfTrainingVector * magnitudeOfTestVector));
                trainingRowsCount++;
            }

            cosineSimilarityResults[rowsCount] = new CosineSimilarityResults(testDataKey, cosineDistances);
            rowsCount++;

        }

        return cosineSimilarityResults;
    }
}

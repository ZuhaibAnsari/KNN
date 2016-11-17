package edu.ucd.ml.knn.helper;

import edu.ucd.ml.knn.datastruc.CosineDistance;
import edu.ucd.ml.knn.datastruc.CosineSimilarityResults;

/**
 * Created by Zuhaib on 11/17/2016.
 */
public class CosineSimilarityHelper {

    public CosineSimilarityResults[] calculateCosineSimilarity(double[][] testDataMatrix, double[][] trainingDataMatrix){
        CosineSimilarityResults[] cosineSimilarityResults = new CosineSimilarityResults[testDataMatrix.length];
        int count =0;
        for (double[] aTestDataMatrix : testDataMatrix) {
            int columnCount = aTestDataMatrix.length - 1;
            int testDataKey = (int) (aTestDataMatrix[columnCount]);
            System.out.println(count);
            CosineDistance[] cosineDistances = new CosineDistance[trainingDataMatrix.length];
            int trainingRowsCount = 0;
            for (double[] aTrainingDataMatrix : trainingDataMatrix) {
                int trainingDataKey = (int) (aTrainingDataMatrix[columnCount]);
                double dotProductOfVectors = 0, magnitudeOfTrainingVector = 0, magnitureOfTestVector = 0;
                for (int k = 0; k < columnCount; k++) {
                    dotProductOfVectors += aTrainingDataMatrix[k] * aTestDataMatrix[k];
                    magnitudeOfTrainingVector += (aTrainingDataMatrix[k] * aTrainingDataMatrix[k]);
                    magnitureOfTestVector += (aTestDataMatrix[k] * aTestDataMatrix[k]);

                }
                magnitudeOfTrainingVector = MathsHelper.sqrt(magnitudeOfTrainingVector);
                magnitureOfTestVector = MathsHelper.sqrt(magnitureOfTestVector);
                cosineDistances[trainingRowsCount] = new CosineDistance(trainingDataKey, dotProductOfVectors / (magnitudeOfTrainingVector * magnitureOfTestVector));
                trainingRowsCount++;
            }

            cosineSimilarityResults[count] = new CosineSimilarityResults(testDataKey, cosineDistances);
            count++;

        }

        return cosineSimilarityResults;
    }
}

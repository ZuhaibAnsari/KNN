package edu.ucd.ml.knn.datastruc;

import java.util.Arrays;

/**
 * Created by Zuhaib on 11/13/2016.
 * This class is used for calculation of the nearest neighbours, similarities, and holding the cosine similarity results
 */
public class CosineSimilarityResults {
    private int dataPointIndex;
    private CosineDistance[] distances;
    private int[] knnIndexes;
    private String[] classLabels;
    private String predictedClassLabel;

    public CosineSimilarityResults(int dataPointIndex, CosineDistance[] distance) {
        this.dataPointIndex = dataPointIndex;
        this.distances = distance;
    }

    /**
     * This method is used to find the nearest neighbors
     * @param kNeighbours is the number of nearest neighbours
     * @param isWeightedKNN is used to check if the KNN is to be run as weighted or not
     */
    public void findKNearestNeighbours(int kNeighbours, boolean isWeightedKNN) {

        //If it is weighted KNN sort using the Cosine Distance Comparator
        if (isWeightedKNN) {
               Arrays.sort(this.distances, CosineDistance.CosineDistanceComparator);
        } else {
            Arrays.sort(this.distances);
        }

        //This is used to find the K nearest neigbours based on the cosine similarity
        this.knnIndexes = new int[kNeighbours];
        for (int i = 0; i < kNeighbours; i++) {
            this.knnIndexes[i] = this.distances[(this.distances.length - 1) - i].getDataPointIndex();

        }
    }

    /**
     * This method is used to predict the class label from the data
     * @param isWeightedKNN is used to check if the classifier is run as weighted or not
     */
    public void getPredictedClassLabelFromData(boolean isWeightedKNN) {
        //If the KNN is weighted find the sum of similar class labels to find the most similar class label
        if (isWeightedKNN) {
            double sumOfWeightOfBusinessLabel = 0;
            double sumOfWeightOfPoliticsLabel = 0;
            double sumOfWeightOfSportsLabel = 0;
            double sumOfWeightOfTechnologyLabel = 0;
            for (int i = 0; i < classLabels.length; i++) {
                switch (classLabels[i]) {
                    case "business":
                        sumOfWeightOfBusinessLabel += this.distances[(this.distances.length - 1) - i].getCosineDistance();
                        break;

                    case "politics":
                        sumOfWeightOfPoliticsLabel += this.distances[(this.distances.length - 1) - i].getCosineDistance();
                        break;

                    case "sport":
                        sumOfWeightOfSportsLabel += this.distances[(this.distances.length - 1) - i].getCosineDistance();
                        break;

                    case "technology":
                        sumOfWeightOfTechnologyLabel += this.distances[(this.distances.length - 1) - i].getCosineDistance();
                        break;
                }


                double max = sumOfWeightOfBusinessLabel;
                this.predictedClassLabel = "business";
                if (sumOfWeightOfPoliticsLabel > max)
                    this.predictedClassLabel = "politics";
                if (sumOfWeightOfSportsLabel > max)
                    this.predictedClassLabel = "sport";
                if (sumOfWeightOfTechnologyLabel > max)
                    this.predictedClassLabel = "technology";

            }

        } else {
            //If the KNN is not weighted find the count of similar class labels to find the most similar class label
            int countOfBusinessLabel = 0;
            int countOfPoliticsLabel = 0;
            int countOfSportsLabel = 0;
            int countOfTechnologyLabel = 0;

            for (String classLabels : this.classLabels) {
                switch (classLabels) {
                    case "business":
                        countOfBusinessLabel++;
                        break;

                    case "politics":
                        countOfPoliticsLabel++;
                        break;

                    case "sport":
                        countOfSportsLabel++;
                        break;

                    case "technology":
                        countOfTechnologyLabel++;
                        break;
                }
            }

            int max = countOfBusinessLabel;
            this.predictedClassLabel = "business";
            if (countOfPoliticsLabel > max)
                this.predictedClassLabel = "politics";
            if (countOfSportsLabel > max)
                this.predictedClassLabel = "sport";
            if (countOfTechnologyLabel > max)
                this.predictedClassLabel = "technology";
        }

    }

    public int getDataPointIndex() {
        return dataPointIndex;
    }

    public void setDataPointIndex(int dataPointIndex) {
        this.dataPointIndex = dataPointIndex;
    }

    public CosineDistance[] getDistances() {
        return distances;
    }

    public void setDistances(CosineDistance[] distances) {
        this.distances = distances;
    }

    public int[] getKnnIndexes() {
        return knnIndexes;
    }

    public void setKnnIndexes(int[] knnIndexes) {
        this.knnIndexes = knnIndexes;
    }

    public String[] getClassLabels() {
        return classLabels;
    }

    public void setClassLabels(String[] classLabels) {
        this.classLabels = classLabels;
    }

    public String getPredictedClassLabel() {
        return predictedClassLabel;
    }

    public void setPredictedClassLabel(String predictedClassLabel) {
        this.predictedClassLabel = predictedClassLabel;
    }

}

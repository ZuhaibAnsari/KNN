package edu.ucd.ml.knn.datastruc;

import java.util.Arrays;

/**
 * Created by Zuhaib on 11/13/2016.
 * This class is used for calculation of the nearest neighbours, similarities, and holding the cosine similarity results
 */
public class CosineSimilarityOutput {
    private int documentNumber;
    private CosineSimilarity[] cosineSimilarity;
    private int[] nearestNeighbouringDocuments;
    private String[] classLabels;
    private String predictedClassLabel;

    public CosineSimilarityOutput(int documentNumber, CosineSimilarity[] distance) {
        this.documentNumber = documentNumber;
        this.cosineSimilarity = distance;
    }

    /**
     * This method is used to find the nearest neighbors
     * @param kNeighbours is the number of nearest neighbours
     * @param isWeightedKNN is used to check if the KNN is to be run as weighted or not
     */
    public void findKNearestNeighbours(int kNeighbours, boolean isWeightedKNN) {

        //If it is weighted KNN sort using the Cosine Distance Comparator
        if (isWeightedKNN) {
             Arrays.sort(this.cosineSimilarity, CosineSimilarity.CosineSimilarityComparator);
        } else {
            Arrays.sort(this.cosineSimilarity);
        }

        //This is used to find the K nearest neighbors based on the cosine similarity
        this.nearestNeighbouringDocuments = new int[kNeighbours];
        for (int i = 0; i < kNeighbours; i++) {
            this.nearestNeighbouringDocuments[i] = this.cosineSimilarity[(this.cosineSimilarity.length - 1) - i].getDocumentNumber();

        }
    }

    /**
     * This method is used to predict the class label from the data
     * @param isWeightedKNN is used to check if the classifier is run as weighted or not
     */
    public void getPredictedClassLabelOfTestData(boolean isWeightedKNN) {
        //If the KNN is weighted find the sum of similar class labels to find the most similar class label
        if (isWeightedKNN) {
            double sumOfWeightOfBusinessLabel = 0;
            double sumOfWeightOfPoliticsLabel = 0;
            double sumOfWeightOfSportsLabel = 0;
            double sumOfWeightOfTechnologyLabel = 0;
            for (int i = 0; i < classLabels.length; i++) {
                switch (classLabels[i]) {
                    case "business":
                        sumOfWeightOfBusinessLabel += this.cosineSimilarity[(this.cosineSimilarity.length - 1) - i].getCosineSimilarity();
                        break;

                    case "politics":
                        sumOfWeightOfPoliticsLabel += this.cosineSimilarity[(this.cosineSimilarity.length - 1) - i].getCosineSimilarity();
                        break;

                    case "sport":
                        sumOfWeightOfSportsLabel += this.cosineSimilarity[(this.cosineSimilarity.length - 1) - i].getCosineSimilarity();
                        break;

                    case "technology":
                        sumOfWeightOfTechnologyLabel += this.cosineSimilarity[(this.cosineSimilarity.length - 1) - i].getCosineSimilarity();
                        break;
                }


                double sumOfWeights = sumOfWeightOfBusinessLabel;
                this.predictedClassLabel = "business";
                if (sumOfWeightOfPoliticsLabel > sumOfWeights)
                    this.predictedClassLabel = "politics";
                if (sumOfWeightOfSportsLabel > sumOfWeights)
                    this.predictedClassLabel = "sport";
                if (sumOfWeightOfTechnologyLabel > sumOfWeights)
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

            int maximumCountOfBusinessClassLabel = countOfBusinessLabel;
            this.predictedClassLabel = "business";
            if (countOfPoliticsLabel > maximumCountOfBusinessClassLabel)
                this.predictedClassLabel = "politics";
            if (countOfSportsLabel > maximumCountOfBusinessClassLabel)
                this.predictedClassLabel = "sport";
            if (countOfTechnologyLabel > maximumCountOfBusinessClassLabel)
                this.predictedClassLabel = "technology";
        }

    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public CosineSimilarity[] getCosineSimilarity() {
        return cosineSimilarity;
    }

    public void setCosineSimilarity(CosineSimilarity[] cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }

    public int[] getNearestNeighbouringDocuments() {
        return nearestNeighbouringDocuments;
    }

    public void setNearestNeighbouringDocuments(int[] nearestNeighbouringDocuments) {
        this.nearestNeighbouringDocuments = nearestNeighbouringDocuments;
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

package edu.ucd.ml.knn.datastruc;

import java.util.*;

/**
 * Created by Zuhaib on 11/13/2016.
 */
public class CosineSimilarityResults {
    private int dataPointIndex;
    private CosineDistance[] distances;
    private int[] knnIndexes;
    private String[] classLabels;
    private String predictedClassLabel;

    public CosineSimilarityResults(int dataPointIndex,CosineDistance[] distance){
        this.dataPointIndex=dataPointIndex;
        this.distances=distance;
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

    public void calculateKnn(int kNeighbours) {
        Arrays.sort(this.distances);
        int lastDistanceIndex = this.distances.length - 1;
        this.knnIndexes = new int[kNeighbours];
        for (int i = 0; i < kNeighbours; i++) {
            this.knnIndexes[i] = this.distances[lastDistanceIndex - i].getDataPointIndex();
        }
    }


    public void getPredictedClassLabelFromData() {
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
        this.predictedClassLabel="business";
        if (countOfPoliticsLabel > max)
            this.predictedClassLabel="politics";
        if (countOfSportsLabel > max)
            this.predictedClassLabel="sport";
        if (countOfTechnologyLabel > max)
            this.predictedClassLabel="technology";
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

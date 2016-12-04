package edu.ucd.ml.knn.datastruc;

import java.util.Comparator;

/**
 * Created by Zuhaib on 11/13/2016.
 * This class will be used as the POJO for Cosine Distance , it will contain the index of the document and its cosine similarity distance
 */
public class CosineSimilarity implements Comparable<CosineSimilarity> {
    private int documentNumber;
    private double cosineSimilarity;

    public CosineSimilarity(int documentNumber, double cosineSimilarity) {
        this.documentNumber = documentNumber;
        this.cosineSimilarity = cosineSimilarity;
    }


    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public double getCosineSimilarity() {
        return cosineSimilarity;
    }

    public void setCosineSimilarity(double cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CosineSimilarity))
            return false;

        CosineSimilarity distance1 = (CosineSimilarity) o;

        return Double.compare(distance1.cosineSimilarity, cosineSimilarity) == 0;
    }

    @Override
    /*
    This is to generate unique hash codes to be used in hashmap
     */
    public int hashCode() {
        long hashCode = Double.doubleToLongBits(cosineSimilarity);
        return (int) (hashCode ^ (hashCode >>> 32));
    }


    /**
     *
     * @param cosineSimilarity
     * @return comparision to be used for sorting
     */
    public int compareTo(CosineSimilarity cosineSimilarity) {
        return Double.compare(this.cosineSimilarity, cosineSimilarity.getCosineSimilarity());
    }

    /**
     * This is the Cosine Similarity Comparator to be used for sorting when weighted KNN is used
     */
    public static Comparator<CosineSimilarity> CosineSimilarityComparator
            = new Comparator<CosineSimilarity>() {

        public int compare(CosineSimilarity cosineSimilarity1, CosineSimilarity cosineSimilarity2) {
             return Double.compare(cosineSimilarity2.getCosineSimilarity(),cosineSimilarity1.getCosineSimilarity());


        }

    };
}

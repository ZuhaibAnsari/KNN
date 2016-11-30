package edu.ucd.ml.knn.datastruc;

import java.util.Comparator;

/**
 * Created by Zuhaib on 11/13/2016.
 * This class will be used as the POJO for Cosine Distance , it will contain the index of the document and its cosine similarity distance
 */
public class CosineDistance implements Comparable<CosineDistance> {
    private int dataPointIndex;
    private double cosineDistance;

    public CosineDistance(int dataPointIndex, double cosineDistance) {
        this.dataPointIndex = dataPointIndex;
        this.cosineDistance = cosineDistance;
    }


    public int getDataPointIndex() {
        return dataPointIndex;
    }

    public void setDataPointIndex(int dataPointIndex) {
        this.dataPointIndex = dataPointIndex;
    }

    public double getCosineDistance() {
        return cosineDistance;
    }

    public void setCosineDistance(double cosineDistance) {
        this.cosineDistance = cosineDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CosineDistance))
            return false;

        CosineDistance distance1 = (CosineDistance) o;

        return Double.compare(distance1.cosineDistance, cosineDistance) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(cosineDistance);
        return (int) (temp ^ (temp >>> 32));
    }


    public int compareTo(CosineDistance o) {
        return Double.compare(this.cosineDistance, o.getCosineDistance());
    }

    public static Comparator<CosineDistance> CosineDistanceComparator
            = new Comparator<CosineDistance>() {

        public int compare(CosineDistance distance1, CosineDistance distance2) {
            double value = Double.compare(distance2.getCosineDistance(),distance1.getCosineDistance());

            return Double.compare(distance2.getCosineDistance(),distance1.getCosineDistance());


        }

    };
}

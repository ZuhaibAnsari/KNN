package edu.ucd.ml.knn.datastruc;

/**
 * Created by Zuhaib on 11/13/2016.
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
        if(null== o){
            System.out.println("eeee");
        }
        return Double.compare(this.cosineDistance, o.getCosineDistance());
    }
}
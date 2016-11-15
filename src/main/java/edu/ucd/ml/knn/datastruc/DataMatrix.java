package edu.ucd.ml.knn.datastruc;

/**
 * Created by Zuhaib on 11/12/2016.
 */
public class DataMatrix {

   public DataMatrix(int size){
        this.columnIndexes = new int[size];
            }

    public int[] getColumnIndexes() {
        return columnIndexes;
    }

    public void setColumnIndexes(int[] columnIndexes) {
        this.columnIndexes = columnIndexes;
    }

    int [] columnIndexes;
}

package edu.ucd.ml.knn.core;

import no.uib.cipr.matrix.io.MatrixVectorReader;
import no.uib.cipr.matrix.io.VectorInfo;
import no.uib.cipr.matrix.sparse.CompColMatrix;
import org.la4j.matrix.sparse.CRSMatrix;

import java.io.*;
import java.util.Iterator;

/**
 * Created by Zuhaib on 11/6/2016.
 */
public class MTXFileReader {

    private CRSMatrix matrix;

    public void read(String filename) throws java.io.IOException {
        InputStream s = new FileInputStream("C:\\Users\\Gurjeet\\IdeaProjects\\KNN\\src\\main\\resources\\news_articles.mtx");

        BufferedReader br = new BufferedReader(new InputStreamReader(s));

        // readMatrixMarketData type code initial line
        String line = br.readLine();


        // readMatrixMarketData comment lines if any
        boolean comment = true;
        while (comment) {
            line = br.readLine();
            comment = line.startsWith("%");
        }

        // line now contains the size information which needs to be parsed
        String[] str = line.split("( )+");
        int nRows = (Integer.valueOf(str[0].trim())).intValue();
        int nColumns = (Integer.valueOf(str[1].trim())).intValue();
        int nNonZeros = (Integer.valueOf(str[2].trim())).intValue();

        System.out.println(nRows);

        // now we're into the data section
       /*matrix = new CompRowMatrix(nRows,nColumns);
        while (true) {
            line = br.readLine();
            if (line == null)  break;
            str = line.split("( )+");
            int i = (Integer.valueOf(str[0].trim())).intValue();
            int j = (Integer.valueOf(str[1].trim())).intValue();
            double x = (Double.valueOf(str[2].trim())).doubleValue();
            matrix.set(i, j, x);
        }
        System.out.println(matrix.getRow(1));
       */ br.close();


    }




public static void main(String args[]){

    InputStream s = null;
    try {
        s = new FileInputStream("C:\\Users\\Gurjeet\\IdeaProjects\\KNN\\src\\main\\resources\\news_articles.mtx");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    BufferedReader br = new BufferedReader(new InputStreamReader(s));
    MatrixVectorReader matrixVectorReader = new MatrixVectorReader(br);
    try {
        MTXFileReader mtxFileReader = new MTXFileReader();
        mtxFileReader.read("");
        VectorInfo vectorInfo = matrixVectorReader.readVectorInfo();
        System.out.println(vectorInfo.isCoordinate());
      CompColMatrix matrix = new CompColMatrix(matrixVectorReader);
        Iterator entry = matrix.iterator();
        /*while (entry.hasNext()){
            MatrixEntry matrixEntry = (MatrixEntry) entry.next();
            System.out.println(matrixEntry.column());
        }*/
        System.out.println(matrix.get(1,2));
    } catch (IOException e) {
        e.printStackTrace();
    }


}
}

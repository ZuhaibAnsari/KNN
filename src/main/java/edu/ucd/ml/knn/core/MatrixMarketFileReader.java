package edu.ucd.ml.knn.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Zuhaib on 11/6/2016.
 */
public class MatrixMarketFileReader {

    /**
     * This method is used the matrix market file and transform it into an array of data
     * @param filename
     * @return an array of data read from the matrix market file
     * @throws IOException
     */

    public double[][] readMatrixMarketDataIntoArray(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(MatrixMarketFileReader.class.getResourceAsStream(filename)));
        String lineOfMTXFile = bufferedReader.readLine();

        //To read and skip the comment lines
        boolean isCommentLine = true;
        while (isCommentLine) {
            lineOfMTXFile = bufferedReader.readLine();
            isCommentLine = lineOfMTXFile.startsWith("%");
        }

        //Fetching the number of unique documents and number of unique words
        String[] mtxFileData = lineOfMTXFile.split(" ");
        int numberOfUniqueDocuments = (Integer.parseInt(mtxFileData[0].trim()));
        int numberOfUniqueWords = (Integer.parseInt(mtxFileData[1].trim()));

        // This is the actual data matrix which will be populated after reading the file
        double[][] dataMatrix = new double[numberOfUniqueDocuments + 1][numberOfUniqueWords + 1];
        while (true) {
            lineOfMTXFile = bufferedReader.readLine();
            if (lineOfMTXFile == null)
                break;
            mtxFileData = lineOfMTXFile.split("( )+");

            //Fetching row , column count and the document frequency
            int rowNumber = (Integer.parseInt(mtxFileData[0].trim())) - 1;
            int columnNumber = (Integer.parseInt(mtxFileData[1].trim())) - 1;
            int frequency = (Integer.parseInt(mtxFileData[2].trim()));

            dataMatrix[rowNumber][columnNumber] = frequency;

        }
        return dataMatrix;
    }

}

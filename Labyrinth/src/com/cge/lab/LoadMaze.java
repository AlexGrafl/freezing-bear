package com.cge.lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Phips on 27.06.2014.
 */
public class LoadMaze {

    public enum  FieldType{
        EMPTY,
        CRATE,
        START
    }
    public ArrayList<ArrayList<FieldType>> map = new ArrayList<ArrayList<FieldType>>();
    private int startX, startY;
    private String debugFile = "res/testMaze1.txt";



    public LoadMaze(String fileName) throws IOException {
        this.load(fileName);
        this.findStart();
    }

    private void load(String fileName) throws IOException {
        FileReader inputFile = new FileReader(fileName);
        BufferedReader bufferReader = new BufferedReader(inputFile);
        ArrayList<FieldType> row;

        String line;
        int countX = 0;


        // Read file line by line and print on the console
        while ((line = bufferReader.readLine()) != null)   {
            row  = new ArrayList<FieldType>();
            char[] charLine = line.toCharArray();
            for(char c : charLine){
                if (c == '#') row.add(FieldType.CRATE);
                if (c == ' ') row.add(FieldType.EMPTY);
            }
            if(! row.isEmpty() )map.add(row);
        }
        //Close the buffer reader
        bufferReader.close();
    }
    private void findStart() {
        int count = 0;
        //first row
       for (FieldType tmp : this.map.get(0)) {
            if (tmp == FieldType.EMPTY) {
                this.startX = count;
                this.startY = 0;
                this.map.get(startY).set(startX,FieldType.START);
                return;
            }
            count++;
        }

        //last row
        count = 0;
        for (FieldType tmp : this.map.get(this.map.size() - 1)) {
            if (tmp == FieldType.EMPTY) {
                this.startX = count;
                this.startY = this.map.size() - 1;
                this.map.get(startY).set(startX,FieldType.START);
                return;
            }
            count++;
        }

        //first column
        for (int y = 0; y < this.map.size(); y++) {
            if (this.map.get(y).get(0) == FieldType.EMPTY) {
                this.startX = 0;
                this.startY = y;
                this.map.get(startY).set(startX,FieldType.START);
                return;
            }
        }

        //first column
        for (int y = 0; y < this.map.size(); y++) {

            if (this.map.get(y).get(this.map.get(y).size() - 1) == FieldType.EMPTY) {
                this.startX = this.map.get(y).size() - 1;
                this.startY = y;
                this.map.get(startY).set(startX,FieldType.START);
                return;
            }
        }

    }


}

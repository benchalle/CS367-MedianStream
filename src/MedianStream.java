/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          Program 3/ Weather Median
// FILE:             MedianStream.java
//
// TEAM:    Benjamin Challe
// Authors: Benjamin Challe, bchalle@wisc.edu, bchalle,002 
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Lecture notes
// 
// Online sources: Online lecture modules
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;
/**
 * This class deals with the inputs from the user whether that be from a file
 * or console input and prints the results to a file
 *
 * <p>Bugs: none that I am aware of
 *
 * @author Benjamin Challe
 */
public class MedianStream
{

    private static final String PROMPT_NEXT_VALUE = "Enter next value or q to quit: ";
    private static final String MEDIAN = "Current median: ";
    private static final String EXIT_MESSAGE = "That wasn't a double or 'q'. Goodbye!";
    private static final String FNF_MESSAGE = " not found.";

    /**
     * Use this format to ensure that double values are formatted correctly.
     * Double doubleValue = 1412.1221132
     * System.out.printf(DOUBLE_FORMAT, doubleValue);
     */
    private static final String DOUBLE_FORMAT = "%8.3f\n";

    private Double currentMedian;
    private MaxPQ<Double> maxHeap;
    private MinPQ<Double> minHeap;

    /**
     * Override Default Constructor
     *
     *  Initialize the currentMedian = 0.0
     *  Create a new MaxPQ and MinPQ.
     */
    public MedianStream()
    {
        this.currentMedian = 0.0;
        this.maxHeap = new MaxPQ<Double>();
        this.minHeap = new MinPQ<Double>();
    }

    /**
     * This method is called if the user passes NO command line arguments.
     * The method prompts the user for a double value on each iteration.
     *
     * If the input received is a double, the current median is updated.
     * After each iteration, print the new current median using MEDIAN string
     * as declared and initialized with the data members above.
     *
     * If the input is the character 'q', return from the method.
     *
     * If the input is anything else, then you print an error using EXIT_MESSAGE
     * string as declared and initialized with the data members above and
     * then return from the method.
     *
     * For the purposes of calculating the median, every input received since
     * the beginning of the method execution is part of the same stream.
     */
    private static void runInteractiveMode()
    {
    	MedianStream mS = new MedianStream(); // create a new medianStream
    	Scanner scnr = new Scanner(System.in); // create a scanner
    	boolean quit = false; // create the quit condition to exit the loop
    	while(!quit){ // while q is not entered
    		System.out.println(PROMPT_NEXT_VALUE); // prompt user
    		String line = scnr.nextLine(); // scan user input
    		if(line.equalsIgnoreCase("q")){ //if the input is q
    			quit = true; // quit the loop
    		}else{
    			try{
    				double number = Double.parseDouble(line); // try to parse the double
    				System.out.print(MEDIAN); // print the text
    				System.out.printf(DOUBLE_FORMAT, mS.getMedian(number)); // format and print the median
    				
    			}
    			catch(NumberFormatException e){ // if the input cant be switched to double
    				System.out.println(EXIT_MESSAGE); // give user error message
    				return; // return from the method
    			}
    		}
    	}
    	
    }

    /**
     * This method is called if the user passes command line arguments.
     * The method is called once for every filename passed by the user.
     *
     * The method reads values from the given file and writes the new median
     * after reading each new double value to the output file.
     *
     * The name of the output file follows a format specified in the write-up.
     *
     * If the input file contains a non-double value, the program SHOULD NOT
     * throw an exception, instead it should just read the values up to that
     * point, write medians after each value up to that point and then
     * return from the method.
     *
     * If a FileNotFoundException occurs, just print the string FNF_MESSAGE
     * as declared and initialized with the data members above.
     */
    private static void findMedianForFile(String filename)
    {
    	MedianStream ms = new MedianStream(); //create a new medianStream
    	File file = new File(filename); // create a new file with filename
    	String[] output = filename.split(".txt"); // get the name of the file
    	String outputFile = output[0] + "_out.txt"; //reformat for printing to
    	
    	try{ //try to print to the file
    		PrintWriter pw = new PrintWriter(outputFile); // create a new file to print to
    		Scanner scnr = new Scanner(file); // create a scanner the file 
    		while(scnr.hasNextLine()){ // while the file has another line to read
    			String data = scnr.nextLine(); // save the next line of the file
    			String[] multiData = data.split(" "); // split the line by spaces
    			for(int i =0; i<multiData.length; i++){ //for each item in the array
    				if(!multiData[i].equals("")){ // if there is an extra space, skip it
    					Double numberToAdd = Double.parseDouble(multiData[i]); // try to parse string to double
    					Double median = ms.getMedian(numberToAdd); // get the median
    					pw.printf(DOUBLE_FORMAT, median); // print the formated median to the file
    				}
    			}
    			
    		}
    		pw.close(); // close the printer to actually print
    	}catch(FileNotFoundException e){ // if the file isnt found
    		System.out.println(filename + FNF_MESSAGE); // show error message that no file exists
    		return;// return from the method
    		
    	}catch(NumberFormatException e){ // if there is an incorrect character
    		return; // return from the method
    	}
    }

    /**
     * YOU ARE NOT COMPULSORILY REQUIRED TO IMPLEMENT THIS METHOD.
     *
     * That said, we found it useful to implement.
     *
     * Adds the new temperature reading to the corresponding
     * maxPQ or minPQ depending upon the current state.
     *
     * Then calculates and returns the updated median.
     *
     * @param newReading - the new reading to be added.
     * @return the updated median.
     */
    private Double getMedian(Double newReading)
    {
    	if(newReading < currentMedian){ // if the new reading is less than the current median
    	   	if(minHeap.size() == maxHeap.size()){ // if the arrays have the same size
        		maxHeap.insert(newReading); //insert the new reading to the maxHeap
        		currentMedian =  maxHeap.getMax(); // set the new current median
        	}else if(minHeap.size() == maxHeap.size() - 1){ // if the minHeap is one greater than maxheap
        		maxHeap.insert(newReading); //insert the new reading to the maxHeap
        		currentMedian = (minHeap.getMax() + maxHeap.getMax())/2; // set the new median to (max of min + max of max) /2
        	}else if(minHeap.size() == maxHeap.size()+1){ // if the maxHeap is one greater than minheap
        		double minP = maxHeap.getMax();
        		minHeap.insert(minP);
        		maxHeap.insert(newReading); // insert the new reading into maxheap
        		currentMedian = (minHeap.getMax() + maxHeap.getMax())/2; // set the new median to (max of min + max of max) /2
        	}
    		
    	}else{  // if the new reading is greater or equal to the current median
    	if(minHeap.size() == maxHeap.size()){ // if the arrays have the same size
    		minHeap.insert(newReading); //insert the new reading in maxheap
    		currentMedian =  minHeap.getMax(); //set the new current median
    	}else if(minHeap.size() == maxHeap.size() - 1){ //if the minHeap is one greater than maxheap
    		minHeap.insert(newReading); // insert the new reading into minHeap
    		currentMedian = (minHeap.getMax() + maxHeap.getMax())/2; // set the new median to (max of min + max of max) /2
    	}else if(minHeap.size() == maxHeap.size()+1){  // if the maxHeap is one greater than minheap
    		double maxP = minHeap.removeMax(); //remove max from minHeap
    		maxHeap.insert(maxP); // insert it into maxheap
    		minHeap.insert(newReading); // insert newreading into minheap
    		currentMedian = (minHeap.getMax() + maxHeap.getMax())/2; // set the new median to (max of min + max of max) /2
    	}
		
    }
    	return currentMedian; // return the updated current median
    }

    // DO NOT EDIT THE main METHOD.
    public static void main(String[] args)
    {
        // Check if files have been passed in the command line.
        // If no files are passed, run an infinite interactive loop taking a double
        // input each time until "q" is entered by the user.
        // After each iteration of the loop, update and display the new median.
        if ( args.length == 0 )
        {
            runInteractiveMode();
        }

        // If files are passed in the command line, open each file.
        // For each file, iterate over all the double values in the file.
        // After reading each new double value, write the new median to the
        // corresponding output file whose name will be inputFilename_out.txt
        // Stop reading the file at the moment a non-double value is detected.
        else
        {
            for ( int i=0 ; i < args.length ; i++ )
            {
                findMedianForFile(args[i]);
            }
        }
    }
}

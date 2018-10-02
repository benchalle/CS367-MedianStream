/**
 * GENERAL DIRECTIONS -
 *
 * 1. You may add private data fields and private methods as you see fit.
 * 2. Implement ALL the methods defined in the PriorityQueueADT interface.
 * 3. DO NOT change the name of the methods defined in the PriorityQueueADT interface.
 * 4. DO NOT edit the PriorityQueueADT interface.
 * 5. DO NOT implement a shadow array.
 */
/**
 * This Class creates and implements a min priority queue
 *
 * <p>Bugs: none that I am aware of
 *
 * @author Benjamin Challe
 */
public class MinPQ<E extends Comparable<E>> implements PriorityQueueADT<E>
{
    private E[] items; //initialize the queue
    private static final int INITIAL_SIZE = 10; // create a constant variable for size
    private int numItems; // create a variable to count number of items

    //ADD MORE DATA PRIVATE DATA FIELDS AS YOU NEED.

    public MinPQ()
    {
        this.items = (E[]) new Comparable[INITIAL_SIZE]; // create a queue with size 10
        numItems = 0; // set the number of items to 0
        // TO-DO: Complete the constructor for any private data fields that you add.
    }
    /**
     * This method returns true iff there are items in the queue
	 *
     * @return true iff numItems equals 0
     */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return numItems == 0; // return true iff number of items is 0
	}
	/**
	 * This method takes the item the user wants to add and adds it
	 * to the queue and then calls other methods to rearrange the 
	 * array and keeps its shape.
	 *
	 * PRECONDITIONS: array has been created
	 * 
	 * POSTCONDITIONS: items will be reordered
	 *
	 * @param item <E> the item you want to add
	 * @return nothing
	 */
	@Override
	public void insert(E item) {
		if(numItems == items.length-1){  // if the list is full
			E[] temporaryArray = (E[]) new Comparable[2*items.length]; // double the size of the array
			for(int i = 1; i<=numItems; i++){ // for each item in the array
				temporaryArray[i] = items[i]; // create the new array
			}
			items = temporaryArray;  // set the old refrence to the new array
		}
		items[++numItems] = item; // insert the item to the end of the array
		helper(numItems); // call the helper method to help organize queue
		
	}
	/**
	 * This method gets and returns the highest priority item in the queue
	 *
	 * PRECONDITIONS: array has been created
	 * 
	 * POSTCONDITIONS: none
	 *
	 * @return the max item in the queue
	 */
	@Override
	public E getMax() throws EmptyQueueException {
		// TODO Auto-generated method stub
		return items[1]; // return the max priority of the queue
	}
	/**
	 * This method removes the max priority item in the queue and reorganizes
	 * the array and returns the removed item
	 *
	 * PRECONDITIONS: there is an array created
	 * 
	 * POSTCONDITIONS: less items in the array and a rearranged queue
	 *
	 * @return The max priority value of the queue
	 */
	@Override
	public E removeMax() throws EmptyQueueException {
		E maxP = items[1]; // save the top priority item
		swapper(1,numItems--); // call the swapper method to rearrange queue
		helperTwo(1); // call the second helper method
		items[numItems +1] = null; // set the last item in the array to null
		return maxP; // return the removed item
		// TODO Auto-generated method stub
		
	}
	/**
	 * This method returns the number of items in the queue
	 * 
	 * @return numItems, the number of items in the queue
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return numItems; // return the number of items in the queue
	}

	/**
	 * This method compares the parent and child item to see if
	 * they need to be swapped
	 *
	 * PRECONDITIONS: x is within the index of the queue
	 * 
	 * POSTCONDITIONS: queue may be rearranged
	 *
	 * @param x the position of an item
	 */
	private void helper(int x){
		while(x>1 && items[x/2].compareTo(items[x]) >= 0){ // compare the parent and child
			swapper(x,x/2); // call the swapper method if it needs to be swapped
			x = x/2; // half x to get next parent child
		}
	}
	
	/**
	 * This method compares each item in the queue it see if the 
	 * queue needs to be changed
	 *
	 * PRECONDITIONS: x is within the index of the queue
	 * 
	 * POSTCONDITIONS: queue may be rearranged
	 *
	 * @param x the position of an item
	 */
	private void helperTwo(int x){
		while(2*x <= numItems){ // make sure x is within the array
			int j = 2*x; //save in another variable
			if(j < numItems && items[j].compareTo(items[j+1]) > 0) j++; // compare the item and the next item
			if(!(items[j].compareTo(items[j+1]) > 0)) break; // if they dont need to be rearranged break
			swapper(x,j); // call the swapper method
			x=j; // set x equal to j
				
			
		}
	}
	/**
	 * This method swaps two items to create to correct order in the queue
	 *
	 * PRECONDITIONS: x is within the index of the queue
	 * PRECONDITIONS: j is within the index of the queue
	 * 
	 * POSTCONDITIONS: queue may be rearranged
	 *
	 * @param x the position of an item
	 * @param j the position of an item
	 */
	private void swapper(int k, int i){
		E swap = items[k]; // save item at index k
		items[k] = items[i]; // set item at k to item at i
		items[i] = swap; // switch item at k and item at i
	}
}

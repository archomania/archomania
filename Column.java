/**
 * Create a class that represents a generic array Column.
 * @author Jackie Nguyen
 * @param <T> Declare generic type T
 */
 
public class Column<T> 
{
	/**
	 * Default initial capacity / minimum capacity.
	 */
	private static final int DEFAULT_CAPACITY = 2;
	/**
	 * Create a generic array of object.
	 */
	private T[] data;
	/**
	 * Create a temporary generic array of object.
	 */
	private T[] temp;
	
	/**
	 * Column class constructor with initial capacity as DEFAULT_CAPACITY.
	 */
	@SuppressWarnings("unchecked")
	public Column() 
	{
		data = (T[])new Object[DEFAULT_CAPACITY];
	}

	/**
	 * Initial capacity of the storage should be initialCapacity.
	 * @param initialCapacity Throw IllegalArgumentException if initialCapacity is smaller than 1. 
	 */
	@SuppressWarnings("unchecked")
	public Column(int initialCapacity) 
	{
		try 
		{
			if(initialCapacity < 1)
			{
				throw new IllegalArgumentException();
			}
		}
		catch (IllegalArgumentException e)
		{
			System.err.println("Capacity must be postive.");
		}
		data = (T[])new Object[initialCapacity];
	}

	/**
	 * Report the current number of elements. 
	 * O(1)
	 * @return size
	 */
	public int size() 
	{	
		int size = 0;
		for(int i = 0; i < capacity(); i++)
		{
			if (data[i] != null)
				size++;
		}
		return size;
	}  

	/**
	 * Report max number of elements before expansion.
	 * @return data length.
	 */
	public int capacity() 
	{ 
		return data.length;
	}

	/**
	 * Change the item at the given index to be the given value.
	 * O(1)
	 * @param index takes an integer as the index of the array.
	 * @param value takes a generic type object as value.
	 * @return old value at that index
	 */
	public T set(int index, T value) 
	{
		try
		{
			if(index < 0 || index >= capacity())
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("Index: " + index + " out of bounds!");
		}

		T obj = data[index];
		data[index] = value;
		return obj;
	}

	/**
	 * Return the item at the given index.
	 * O(1)
	 * @param index takes an integer as the index of the array.
	 * @return data at given index.
	 */
	public T get(int index) 
	{
		try
		
		{
			if(index < 0 || index >= capacity())
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("Index: " + index + " out of bounds!");
		}

		return data[index];
	}

	/**
	 * Append an element to the end of the storage.
	 * Double the capacity if no space available.
	 * Amortized O(1)
	 * @param value generic object to be appended
	 */
	@SuppressWarnings("unchecked")
	public void add(T value) 
	{
		if(value == null)
			System.out.println("Please enter a valid value.");
		else
		{
			if(size() + 1 <= capacity())
				data[size()] = value;		
			else
			{
				temp = (T[])new Object[capacity()];
				temp = data;
				data = (T[])new Object[DEFAULT_CAPACITY * 2];

				for(int k = 0; k < temp.length; k++)
				{
					data[k] = temp[k];
				}
				data[size()] = value;
			}
		}
	} 

	/**
	 * Insert the given value at the given index.
	 * Double capacity if no space available.
	 * O(N) where N is the number of elements in the storage.
	 * @param index takes an integer as the index of the array.
	 * @param value takes a generic type object as value.
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) 
	{
		try
		{
			if(index < 0 || index > capacity())
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("Index: " + index + " out of bounds!");
		}

		temp = (T[])new Object[capacity()];
		for(int i = 0; i < size(); i++)
		{
			temp[i] = data[i];
		}

		if(size() + 1 > capacity())
		{
			temp = (T[])new Object[capacity() * 2];
			for(int j = 0; j < size(); j++)
			{
				temp[j] = data[j];
			}
			data = (T[])new Object[capacity() * 2];
			for(int k = 0; k < capacity(); k++)
			{
				data[k] = temp[k];
			}
		}

		if(index > capacity())
		{
			data[size()] = value;
		}

		else if(data[index] == null)
		{
			data[size()] = value;
		}

		else
		{
			int tempSize = 0;
			for (int l = 0; l < temp.length; l++)
			{
				if(temp[l] != null)
					tempSize++;
			}

			if(index == 0)
			{
				data[0] = value;
				for(int m = 1; m <= tempSize; m++)
				{
					data[m] = temp [m - 1];
				}
			}
			else
			{
				data[index] = value;

				for(int n = index; n < tempSize; n++)
				{
					data[n + 1] = temp[n];
				}
			}
		}
	} 

	/**
	 * Remove and return the element at the given index.
	 * Shift elements to remove the gap.
	 * O(N)
	 * @param index take an integer as index to delete
	 * @return the element at the given index
	 */
	@SuppressWarnings("unchecked")
	public T delete(int index) 
	{
		T obj = data[index];
		try
		{
			if(index < 0 || index > size())
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			System.err.println("Index: " + index + " out of bounds!");
		}

		if (data[index] != null )
		{
			if (index == size() - 1) //if given index is the last element, delete that element
			{
				data[index] = null;
			}
			else if(index == 0)// if given index is the first element, shift over
			{
				for(int k = 1; k < size(); k++)
				{
					data[k - 1] = data[k];
				}
				data[size() -1] = null;
			}
			else //index in the middle of the array
			{
				for(int m = index + 1; m < size(); m++)
				{
					data[m - 1] = data[m];
				}
				data[size() -1] = null;
			}
		}

		if(size() * 3 < capacity() && capacity() >= DEFAULT_CAPACITY)
		{
			temp = (T[])new Object[capacity()/2];
			for(int n = 0; n < size(); n++) //make copy of data
			{
				temp[n] = data[n];
			}
			data = (T[])new Object[capacity()/2];
			for(int o = 0; o < temp.length; o++)
			{
				data[o] = temp[o];
			}
		}

		return obj;
	}  

	/**
	 * This method is provided for debugging purposes.
	 * @return print Strings
	 */
	public String toString() 
	{
		StringBuilder s = new StringBuilder("Column with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
	}

	/**
	 * Main method.
	 * @param args take String arguments
	 */
	public static void main(String args[]){
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellent first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!

		//create a column of integers
		Column<Integer> nums = new Column<>();
		if((nums.size() == 0) && (nums.capacity() == 2)){
			System.out.println("Yay 1");
		}

		//append some numbers 
		for(int i = 0; i < 3; i++) {
			nums.add(i*2);
		}
		
		if(nums.size() == 3 && nums.get(2) == 4 && nums.capacity() == 4){
			System.out.println("Yay 2");
		}
		
		//create a column of strings
		Column<String> msg = new Column<>();
		
		//insert some strings
		msg.add(0,"world");
		msg.add(0,"hello");
		msg.add(1,"new");
		msg.add(3,"!");
		
		//checking
		if (msg.get(0).equals("hello") && msg.set(1,"beautiful").equals("new") 
			&& msg.size() == 4 && msg.capacity() == 4){
			System.out.println("Yay 3");
		}
		
		//delete 
		if (msg.delete(1).equals("beautiful") && msg.get(1).equals("world")  
			&& msg.size() == 3 ){
			System.out.println("Yay 4");
		}

		//shrinking
		nums.add(100);
		nums.add(0, -10);
		if (nums.delete(0) == -10 && nums.delete(1) == 2 && nums.delete(2) == 100
			&& nums.size() == 2 && nums.capacity() == 4) {
			System.out.println("Yay 5");
		}
	}
}
import java.util.Iterator;

public class ArrayList<T> implements Iterable<T> {
    private T[] arr;
    private int size;

    public ArrayList() {
        arr = (T[])(new Object[15]);
    }

    public void add(T element) {
        if(size>=arr.length){
            Object[] temp = new Object[(int)(arr.length * 1.5)];
            System.arraycopy(arr, 0, temp, 0, size);
            arr = (T[]) temp;
        }
        arr[size++] = element;
    }
    public T set(int ind, T element){
        if(ind>=0&&ind<size){
            T old = arr[ind];
            arr[ind] = element;
            return old;
        }
        else{
        throw new IndexOutOfBoundsException("IndexOutOfBounds");
    }
    }
    public T remove(int ind){
        if(ind>=0&&ind<size){
            T ret = arr[ind];

            for(int i = ind;i<size-1;i++){
                arr[i] = arr[i+1];
            }
            size--;
            arr[size] = null;
            
            return ret;
        }
        throw new IndexOutOfBoundsException("IndexOutOfBounds");
    }
    public int size(){return size;}
    public T get(int ind){
        if(ind>=0&&ind<size){
            return arr[ind];
        }
        throw new IndexOutOfBoundsException("IndexOutOfBounds");
    }

    @Override
    public Iterator<T> iterator() {
        return new MyCustomIterator();
    }

    // Inner class for the custom iterator
    private class MyCustomIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return arr[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }
}
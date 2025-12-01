public class ArrayList<T>{
    private T[] arr;
    private int size;
    public ArrayList(){
        try {
            arr = (T[])new Object[15];
        } catch (Exception e) {}
    }
    public void add(T thing){
        if(size<arr.length){
            arr[size++] = thing;
        }
        else{
            try {
                Object[] temp  = new Object[(int)(arr.length*1.5)];
                for(int i = 0; i<arr.length;i++){
                    temp[i] = arr[i];
                }
                arr = (T[])temp;
            } catch (Exception e) {
            }
        }
    }

    public T get(int ind){
        if(ind>=0&&ind<size){
            return arr[ind];
        }
        throw new IndexOutOfBoundsException("Index out of Bounds");
    }

    public int size(){
        return size;
    }

    public T remove(int ind){
        T retVar = get(ind);
        for(int i = ind; i<size;i++){
            if(i==size-1){
                arr[i] = null;
                size--;
            }
            else{
                arr[i] = arr[i+1];
            }
        }

        return retVar;
    }
}

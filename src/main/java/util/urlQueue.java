package util;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class urlQueue {
    private int size = 0;
    private int LENGTH = 1500000;
    private Object[] elementData;
    private int header = 0;

    public urlQueue(){
        elementData = new Object[LENGTH];
    }

    public urlQueue(int len){
        elementData = new Object[len];
    }

    public int getLength(){
        return size;
    }

    public void push(Object data){
        elementData[size] = data;
        size ++;
    }

    public Object pop(){
        if(size == 0){
            throw new NullPointerException("队列为空");
        }

        Object temp = elementData[header];
        elementData[header] = null;
        header ++;
        size --;
        return temp;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else {
            return false;
        }
    }

    public Object offer(){
        if(size == 0){
            throw new NullPointerException("队列为空");
        }
        return elementData[0];
    }

}

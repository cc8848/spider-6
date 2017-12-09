package util;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class urlQueue {
    private int size = 0;
    private int LENGTH = 40;
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
        //当数组存满又有数据push时，将数组长度扩大到15倍
        if (size == LENGTH - 1) {
            Object[] temp = new Object[LENGTH];
            System.arraycopy(elementData,0,temp,0,LENGTH);
            LENGTH = LENGTH * 15;
            elementData = new Object[LENGTH];
            System.arraycopy(temp,0,elementData,0,LENGTH/15);
        }
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
        return size == 0;
    }

    public Object offer(){
        if(size == 0){
            throw new NullPointerException("队列为空");
        }
        return elementData[0];
    }

}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonOneMain {
    public static void main(String[] args) {
        Object[] arr1 = {"Fish","Space","Mean", "Cash"};
        System.out.println(Arrays.toString(arr1));
        swapElements(arr1,1,3);
        System.out.println(Arrays.toString(arr1));

        ArrayList<Object> newList = transformArrToList(arr1);
        System.out.println(newList.toString() + " this is ArrayList");

//Задание 3:
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox = new Box<>();
        orangeBox.put(new Orange());
        orangeBox.put(new Orange());
        orangeBox.put(new Orange());
        orangeBox.put(new Orange());
        appleBox.put(new Apple());
        appleBox.put(new Apple());
        appleBox.put(new Apple());
        appleBox.put(new Apple());
        appleBox.put(new Apple());
        appleBox.put(new Apple());

        System.out.println(orangeBox.getBoxWeight());
        System.out.println(appleBox.getBoxWeight());

        System.out.println(orangeBox.compare(appleBox));

        Box<Orange> orangeBox1 = new Box<>();
        orangeBox1.put(new Orange());
        orangeBox1.put(new Orange());
        orangeBox1.put(new Orange());
        System.out.println(orangeBox1.list.toString());

        orangeBox.shift(orangeBox1);
        System.out.println(orangeBox.list.toString());
        System.out.println(orangeBox1.list.toString());

//        orangeBox.shift(appleBox); //тут ошибка компиляции
    }
//Задание 1:
    public static Object[] swapElements(Object[] obj, int position1, int position2){
       Object tmp;
       int index1 = position1 - 1;
       int index2 = position2 - 1;
       if(index1 > obj.length || index2 > obj.length) {
           System.out.println("Один из указанных индексов привышает количество элементов входного массива. " +
                   "Уажите индексы меньшие или равные " + (obj.length) + ".");
           return obj;
       }

       tmp = obj[index1];
       obj[index1] = obj[index2];
       obj[index2] = tmp;

     return obj;
    }
//Задание 2:
    public static ArrayList<Object> transformArrToList(Object[] arr){
        ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(arr));
        return list;
    }

}

import java.util.ArrayList;
import java.util.Arrays;

//Задание 2:
public class ArrToListJeneric<T extends ArrayList> {

    public void TransformArrToList(Object[] obj){
        ArrayList<Object> list = new ArrayList<>(Arrays.asList(obj));
    }
}

import java.util.ArrayList;
import java.util.List;
//test

public class Box<T extends Fruit> {
    T fruit;

    ArrayList<T> list = new ArrayList<>();

    public void put(T fruit) {
        list.add(fruit);
    }

    public double getBoxWeight() {
        double weight;
        weight = list.size() * (double) list.get(0).getWeight();
        return weight;
    }

    public boolean compare(Box<?> box){
        return this.getBoxWeight() == box.getBoxWeight();
    }

    public void shift(Box<? super T> box){ //так и не понял, почему тут не может быть просто Box<T>?
        box.list.clear();
        box.list.trimToSize();
        box.list.addAll(this.list);
        this.list.clear();
        this.list.trimToSize();
    }
}

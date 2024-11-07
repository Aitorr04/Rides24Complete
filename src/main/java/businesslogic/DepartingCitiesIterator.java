package businesslogic;

import java.util.List;

public class DepartingCitiesIterator implements ExtendedIterator<String> {

    public DepartingCitiesIterator(List<String> cities) {
        this.cities = cities;
        index = cities.size()-1;
    }

    private List<String> cities;
    private int index;

    @Override
    public String previous() {
        return cities.get(index--);
    }

    @Override
    public boolean hasPrevious() {
        return index >= 0;
    }

    @Override
    public void goFirst() {
        index = 0;
    }

    @Override
    public void goLast() {
        index = cities.size()-1;
    }

    @Override
    public boolean hasNext() {
        return index < cities.size();
    }

    @Override
    public String next() {
        return cities.get(index++);
    }
}

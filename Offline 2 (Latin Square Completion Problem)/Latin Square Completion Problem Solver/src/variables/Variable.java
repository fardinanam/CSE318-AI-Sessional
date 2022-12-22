package variables;

public interface Variable<T> {
    void addToDomain(T element);
    void removeFromDomain(T element);
    boolean setValue(T value);
    void removeValue();
    T getValue();
    int getDomainLength();
}

// Импорт пакетов
import java.util.concurrent.atomic.*;

// Класс "Счетчик"
public class Counter {

    // Поля
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicBoolean flag = new AtomicBoolean(true);

    // Getters
    public AtomicInteger getCount() {
        return this.count;
    }

    public AtomicBoolean getFlag() {
        return this.flag;
    }

    // Setters
    public void setFlag(boolean flag) {
        this.flag.set(flag);
    }

    // Методы

    // Метод увеличения счетчика на 1
    public void increase() {
        this.count.incrementAndGet();
    }

}

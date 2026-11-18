// Импорт пакетов
import static java.lang.System.out;
import java.util.concurrent.TimeUnit;

// Класс "Поток"
public class Flow {

    // Поля
    private final Thread thread;  // поток
    private final String name;    // имя потока
    private String data;          // данные, которые поток должен выводить в консоль
    private boolean turn;         // очередь работы потока
    private final Object lock;    // ключ к блоку кода выполнения задачи потока

    // Конструкторы
    public Flow(String name, String data, boolean turn, Object lock) {
        this.name = name;
        this.data = data;
        this.turn = turn;
        this.lock = lock;
        this.thread = new Thread(this::print, name);
    }

    // Getters
    public Thread getThread() {
        return this.thread;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public boolean isTurn() {
        return this.turn;
    }

    public Object getLock() {
        return this.lock;
    }

    // Setters
    public void setData(String data) {
        this.data = data;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    // Методы

    // Метод вывода data в консоль
    private void print() {
        synchronized(this.lock) {
            while(true) {
                while(!this.turn) {
                    try {
                        this.lock.wait();
                        this.turn = true;
                    }
                    catch(InterruptedException ex) {
                        out.printf("Ожидание своей очереди потоком \"%s\" было прервано\n", this.name);
                        return;
                    }
                }
                out.print(this.data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch(InterruptedException ex) {
                    out.printf("Сон потока \"%s\" был прерван\n", this.name);
                    return;
                }
                this.turn = false;
                lock.notifyAll();
            }
        }
    }

    // Метод запуска потока
    public void start() {
        this.thread.start();
    }

}

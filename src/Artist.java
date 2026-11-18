// Импорт пакетов
import java.util.concurrent.TimeUnit;
import static java.lang.System.out;

// Класс "Художник"
public final class Artist {

    // Поля
    private final String name;    // имя художника
    private final Thread thread;  // поток, в котором художник работает (по сути, поток и есть художник)
    private final Color color1;   // цвет № 1, который нужен художнику
    private final Color color2;   // цвет № 2, который нужен художнику

    // Конструкторы
    public Artist(String name, Color color1, Color color2) {
        this.name = name;
        this.thread = new Thread(this::paint, this.name);
        this.color1 = color1;
        this.color2 = color2;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public Thread getThread() {
        return this.thread;
    }

    public Color getColor1() {
        return this.color1;
    }

    public Color getColor2() {
        return this.color2;
    }

    // Методы

    // Метод "рисовать"
    public void paint() {
        synchronized(this.color1) {
            out.printf("Художник %s рисует цветом \"%s\"\n", this.name, this.color1.name());
            try {
                TimeUnit.SECONDS.sleep(1);
            }
            catch(InterruptedException ex) {
                out.printf("Сон художника \"%s\" был прерван\n", this.name);
            }
            synchronized(this.color2) {
                out.printf("Художник %s рисует цветом \"%s\"\n", this.name, this.color2.name());
            }
        }
    }

    // Метод "художник начал работу"
    public void start() {
        out.printf("Художник %s начал работу\n", this.name);
        this.thread.start();
    }

}

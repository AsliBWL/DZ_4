// Импорт пакетов
import static java.lang.System.out;
import java.util.concurrent.TimeUnit;

public class Program {
    public static void main(String[] args) {

        // Часть № 1 (DeadLock)

        // Создаем цвета
        Color red = new Color("красный", new short[] {255, 0, 0});
        Color blue = new Color("синий", new short[] {0, 0, 255});

        // Создаем художников
        Artist repin = new Artist("Репин Илья Ефимович", red, blue);
        Artist shishkin = new Artist("Шишкин Иван Иванович", blue, red);

        // Художники начали работу
        repin.start();
        shishkin.start();

        // Часть № 1 (LiveLock)

        /* Не удалось реализовать чистый LiveLock: счетчик с перебоями, но увеличивается. Сделала так,
           что внезависимости от того, чья очередь увеличивать счетчик, поток предлагает уступить очередь
           другому потоку */

        // Создаем счетчик
        Counter counter = new Counter();

        // Создаем задачи для потоков
        Runnable task1 = () -> {
            while(true) {
                if(counter.getFlag().get()) {
                    counter.increase();
                    counter.setFlag(false);
                    out.printf("%s: %d\n", Thread.currentThread().getName(), counter.getCount().get());
                }
                Thread.yield();
                out.printf("Поток \"%s\" предлагает планировщику потоков уступить другому потоку\n",
                        Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch(InterruptedException ex) {
                    out.printf("Сон потока \"%s\" был прерван\n", Thread.currentThread().getName());
                }
            }
        };
        Runnable task2 = () -> {
            while(true) {
                if(!counter.getFlag().get()) {
                    counter.increase();
                    counter.setFlag(true);
                    out.printf("%s: %d\n", Thread.currentThread().getName(), counter.getCount().get());
                }
                Thread.yield();
                System.out.printf("Поток \"%s\" предлагает планировщику потоков уступить другому потоку\n",
                        Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch(InterruptedException ex) {
                    out.printf("Сон потока \"%s\" был прерван\n", Thread.currentThread().getName());
                }
            }
        };

        // Создаем потоки
        Thread thread1 = new Thread(task1, "Поток № 1");
        Thread thread2 = new Thread(task2, "Поток № 2");

        // Запускаем потоки
        thread1.start();
        thread2.start();

        // Часть № 2

        // Создаем общий для всех потоков ключ
        Object lock = new Object();

        // Создаем потоки
        Flow flow1 = new Flow("Поток № 1", "1", true, lock);
        Flow flow2 = new Flow("Поток № 2", "2", true, lock);

        // Запускаем потоки
        flow1.start();
        flow2.start();

    }
}

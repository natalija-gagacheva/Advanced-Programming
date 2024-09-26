package auditoriski.vezba6Kolokviumski.TaskScheduler;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

interface Task {

    int getOrder();
}

class TimedTask implements Task {
    private final int time;

    public TimedTask(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("TT -> %d", getOrder());
    }

    @Override
    public int getOrder() {
        return time;
    }
}

class PriorityTask implements Task {
    private final int priority;

    public PriorityTask(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("PT -> %d", getOrder());
    }

    @Override
    public int getOrder() {
        return priority;
    }
}

//genericki interface za rasporeduvanje na zadaci so eden metod, t.e funkciski
interface TaskScheduler<T extends Task> {

                //prima niza od zadaci
    List<T> schedule(T[] tasksNiza);
}

//dva metodi ima ovaa klasa
class Schedulers {

    /* gi sortira zadacite spored nivniot reden broj.
    Negovata implementacija treba da e dadena so anonimna klasa
     */

    //nasleduva <T extends Task> bidejki rasporeduvame taskovi vo ovaa zadaca
    //metodot vrakja TaskScheduler
    public static <T extends Task> TaskScheduler<T> getOrdered() {
        //vashiod kod ovde (anonymous class)
        return new TaskScheduler<T>() {
            @Override
            public List<T> schedule(T[] tasksNiza) {

                return Arrays
                        .stream(tasksNiza)
                        .sorted((t1, t2) -> Integer.compare(t1.getOrder(), t2.getOrder()))
                        .collect(Collectors.toList());
            }
        };
    }

    /* go zadrzuva redosledot na zadacite, no gi filtrira site zadaci
    so pogolem reden broj od daden prag
    Negovata implementacija treba da e dadena so lambda izraz
     */
    public static <T extends Task> TaskScheduler<T> getFiltered(int prag) {
        //vashiod kod ovde (lambda expression)

        return tasksNiza -> Arrays
                    .stream(tasksNiza)
                    .filter(edenTask -> edenTask.getOrder()<=prag)
                    .collect(Collectors.toList());
    }
}

//T e podtip na klasata Task
class TaskRunner<T extends Task> {

    public void run(TaskScheduler<T> scheduler, T[] tasksNiza) {
        List<T> listOfOrders = scheduler.schedule(tasksNiza);

        listOfOrders.forEach(x -> System.out.println(x));
 }
}

public class TaskSchedulerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Task[] timeTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            timeTasks[i] = new TimedTask(x);
        }
        n = scanner.nextInt();
        Task[] priorityTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            priorityTasks[i] = new PriorityTask(x);
        }
        Arrays.stream(priorityTasks).forEach(System.out::println);
        TaskRunner<Task> runner = new TaskRunner<>();
        System.out.println("=== Ordered tasks ===");
        System.out.println("Timed tasks");
        runner.run(Schedulers.getOrdered(), timeTasks);
        System.out.println("Priority tasks");
        runner.run(Schedulers.getOrdered(), priorityTasks);
        int filter = scanner.nextInt();
        System.out.printf("=== Filtered time tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), timeTasks);
        System.out.printf("=== Filtered priority tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), priorityTasks);
        scanner.close();
    }
}
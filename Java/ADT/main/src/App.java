

// import model.Queue;
// import model.Stack;
// import model.StaticQueue;
// import model.StaticStack;
// import util.QueueUtil;
import model.FlatDequeueMutable;
import util.FlatDequeueUtil;
// import util.StackUtil;

public class App {

    public static void main(String[] args) {
        FlatDequeueMutable queue = new FlatDequeueMutable();

        for(int i = 0; i < 10; i++) {
            queue.add(i);
        }
        FlatDequeueUtil.filterInPlace(queue,5);
        FlatDequeueUtil.print(queue);
    }

}

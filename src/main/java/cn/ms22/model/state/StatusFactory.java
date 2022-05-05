package cn.ms22.model.state;

import java.util.HashMap;

public final class StatusFactory {
    private StatusFactory() {
    }

    private static HashMap<Integer, IState> map = new HashMap<>();

    static {
        map.put(0, new NewState());
        map.put(-1, new CancelState());
        map.put(1, new RequestState());
        map.put(2, new CompleteState());
    }

    public static IState getInstance(int value) {
        return map.get(value);
    }

}

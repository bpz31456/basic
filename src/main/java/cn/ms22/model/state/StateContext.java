package cn.ms22.model.state;

public class StateContext {
    public static void main(String[] args) {
        StateMachine stateMachine = new StateMachine(StatusFactory.getInstance(0));
        try {
            System.out.println(stateMachine.getStateValue());
//            stateMachine.cancelEvent();
//            System.out.println(stateMachine.getStateValue());
            stateMachine.pushEvent();
            System.out.println(stateMachine.getStateValue());
            stateMachine.backEvent();
            System.out.println(stateMachine.getStateValue());
            stateMachine.pushEvent();
            System.out.println(stateMachine.getStateValue());
            stateMachine.completeEvent();
            System.out.println(stateMachine.getStateValue());

        } catch (NotSupportedEventException e) {
            e.printStackTrace();
        }
    }
}

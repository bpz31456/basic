package cn.ms22.model.state;

public class RequestState implements IState {
    @Override
    public int getStateValue() {
        return 1;
    }

    @Override
    public void backEvent(StateMachine stateMachine) throws NotSupportedEventException {
        stateMachine.setCurrentState(StatusFactory.getInstance(0));
    }

    @Override
    public void cancelEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw  new NotSupportedEventException("需求单无法直接作废");
    }

    @Override
    public void pushEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw  new NotSupportedEventException("无法继续推送");
    }

    @Override
    public void completeEvent(StateMachine stateMachine) throws NotSupportedEventException {
        stateMachine.setCurrentState(StatusFactory.getInstance(2));
    }
}

package cn.ms22.model.state;

public class CancelState implements IState {
    @Override
    public int getStateValue() {
        return -1;
    }

    @Override
    public void backEvent(StateMachine stateMachine) throws NotSupportedEventException {

    }

    @Override
    public void cancelEvent(StateMachine stateMachine) throws NotSupportedEventException {

    }

    @Override
    public void pushEvent(StateMachine stateMachine) throws NotSupportedEventException {

    }

    @Override
    public void completeEvent(StateMachine stateMachine) throws NotSupportedEventException {

    }
}

package cn.ms22.model.state;

import javax.naming.spi.StateFactory;

public class NewState implements IState {
    private int stateValue = 0;

    @Override
    public int getStateValue() {
        return stateValue;
    }

    @Override
    public void backEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw new NotSupportedEventException("新建状态无法返回。");

    }

    @Override
    public void cancelEvent(StateMachine stateMachine) throws NotSupportedEventException {
        stateMachine.setCurrentState(StatusFactory.getInstance(-1));
    }

    @Override
    public void pushEvent(StateMachine stateMachine) throws NotSupportedEventException {
        stateMachine.setCurrentState(StatusFactory.getInstance(1));
    }

    @Override
    public void completeEvent(StateMachine stateMachine) throws NotSupportedEventException {

    }
}

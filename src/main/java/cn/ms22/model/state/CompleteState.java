package cn.ms22.model.state;

public class CompleteState implements IState {
    @Override
    public int getStateValue() {
        return 2;
    }

    @Override
    public void backEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw  new NotSupportedEventException("完成工单无法回退");
    }

    @Override
    public void cancelEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw  new NotSupportedEventException("完成工单无法作废");
    }

    @Override
    public void pushEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw  new NotSupportedEventException("完成工单无法继续推送");
    }

    @Override
    public void completeEvent(StateMachine stateMachine) throws NotSupportedEventException {
        throw  new NotSupportedEventException("完成工单无法继续完成");
    }
}

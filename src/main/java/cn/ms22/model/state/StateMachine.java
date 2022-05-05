package cn.ms22.model.state;

/**
 * 状态机，保存当前状态，在当前状态基础上设置状态（调用具体的状态内部事件）
 */
public class StateMachine {
    private int stateValue;
    private IState state;

    public void setCurrentState(IState iState) {
        state = iState;
        stateValue = iState.getStateValue();
    }

    public StateMachine(IState state) {
        this.state = state;
        this.stateValue = state.getStateValue();
    }

    public int getStateValue() {
        return stateValue;
    }

    public void backEvent() throws NotSupportedEventException {
        state.backEvent(this);
    }

    public void cancelEvent() throws NotSupportedEventException {
        state.cancelEvent(this);
    }

    public void pushEvent() throws NotSupportedEventException {
        state.pushEvent(this);
    }

    public void completeEvent() throws NotSupportedEventException {
        state.completeEvent(this);
    }
}

package cn.ms22.model.state;

/**
 * 各种状态
 */
public interface IState {
    //得到当前状态值
    int getStateValue();

    /**
     * 打回，需求单和服务单都可以打回
     */
    void backEvent (StateMachine stateMachine) throws NotSupportedEventException;

    /**
     * 作废事件
     */
    void cancelEvent(StateMachine stateMachine) throws NotSupportedEventException;

    /**
     * 推送工单，需求单和服务端都可以使用
     */
    void pushEvent(StateMachine stateMachine) throws NotSupportedEventException;

    /**
     * 完成事件
     */
    void completeEvent(StateMachine stateMachine) throws NotSupportedEventException;
}

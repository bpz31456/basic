package cn.ms22.cglib;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

import java.util.Calendar;

public class Student {
    private Schedule mathSchedule;
    private Schedule englishSchedule;

    public Schedule getMathSchedule() {
        return mathSchedule;
    }

    public Student() {
        this.mathSchedule = mathScheduleGenerator();
        this.englishSchedule = englishScheduleGenerator();
    }

    private Schedule englishScheduleGenerator() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Schedule.class);
        enhancer.setCallback((Dispatcher) () -> new Schedule("english", Calendar.getInstance().getTimeInMillis()));
        return (Schedule) enhancer.create();
    }

    private Schedule mathScheduleGenerator() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Schedule.class);
        enhancer.setCallback((LazyLoader) () -> new Schedule("match", Calendar.getInstance().getTimeInMillis()));
        return (Schedule) enhancer.create();
    }

    public Schedule getEnglishSchedule() {
        return englishSchedule;
    }

    static class Schedule {
        private Long time;
        private String name;
        public Schedule(){}

        public Schedule(String name, Long time) {
            this.time = time;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Schedule{" +
                    "time='" + time + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

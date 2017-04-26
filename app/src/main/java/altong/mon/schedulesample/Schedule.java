package altong.mon.schedulesample;


public class Schedule {

    private String name;
    private String type;
    private String weekOrWeekend;
    private String startEndTime;
    private int color;

    public Schedule(String name, String type, String weekOrWeekend, String startEndTime, int color) {
        this.name = name;
        this.type = type;
        this.weekOrWeekend = weekOrWeekend;
        this.startEndTime = startEndTime;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getWeekOrWeekend() {
        return weekOrWeekend;
    }

    public String getStartEndTime() {
        return startEndTime;
    }

    public int getColor() {
        return color;
    }
}

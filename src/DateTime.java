import java.sql.Time;
import java.util.Date;

public class DateTime {
    private Date date;
    private Time time;

    public DateTime(Date date, Time time) {
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public boolean equals(Object o) {
        if (o instanceof DateTime dt) {
            return date.equals(dt.date) && time.equals(dt.time);
        }
        return false;
    }
}

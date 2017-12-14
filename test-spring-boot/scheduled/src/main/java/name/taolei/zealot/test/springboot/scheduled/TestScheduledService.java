package name.taolei.zealot.test.springboot.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class TestScheduledService {

    @Scheduled(fixedRate = 5 * 1000)
    public void one() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss");
        System.out.println("fixedRate = 5 *1000  " + sdf.format(Calendar.getInstance().getTime()));
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void two() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss");
        System.out.println("cron = \"0/5 * * * * *\"  " + sdf.format(Calendar.getInstance().getTime()));
    }
}

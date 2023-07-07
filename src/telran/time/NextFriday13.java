package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if(!temporal.isSupported(ChronoUnit.DAYS)) {
			throw new UnsupportedTemporalTypeException("Temporal must supported DAYS");
		}
		Temporal res = temporal; 
		LocalDate nextFriday = LocalDate.now(); 
		while((nextFriday.getDayOfMonth() != 13 || 
				nextFriday.getDayOfWeek() != DayOfWeek.FRIDAY)) {
			res = res.plus(1, ChronoUnit.DAYS);
			nextFriday = nextFriday.with((TemporalAdjuster) res);
		}
		return res;
	}

}

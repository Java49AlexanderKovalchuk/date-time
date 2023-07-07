package telran.time;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.junit.jupiter.api.Test;

class DateTimeTests {
	@Test
	void test() {
		
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, YYYY EEEE");
		
		//System.out.println(birthAS.format(dtf)); 
		LocalDate barMizva = birthAS.plusYears(13);
		assertEquals(barMizva, birthAS.with(new BarMizvaAdjuster()));
		assertThrowsExactly(UnsupportedTemporalTypeException.class, 
				() -> LocalTime.now().with(new BarMizvaAdjuster()));
	}
	@Test
	void nextFriday13Next() {
		TemporalAdjuster fr13 = new NextFriday13();
		
		ZonedDateTime zdt = ZonedDateTime.now(); 
		ZonedDateTime fr13Expected = ZonedDateTime.of(2023, 10, 13, 0, 0, 0, 0, ZoneId.systemDefault());
		//assertEquals(fr13Expected, zdt.with(fr13));
		//System.out.println(zdt.with(fr13));
		LocalDate ldNow = LocalDate.now();
		LocalDate fr13expected = LocalDate.of(2023, 10, 13);
		assertEquals(fr13expected, ldNow.with(fr13));
		LocalDate fr13Expected2 = LocalDate.of(2024, 9, 13);
		LocalDate ld = LocalDate.of(2023, 10, 13);
		assertEquals(fr13Expected2, ld.with(fr13));
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, EEEE, YYYY");
		System.out.println(fr13expected.format(dtf));
		System.out.println(fr13Expected2.format(dtf));
	}
	@Test
	void canadaCurrentTime() {
		ZoneId.getAvailableZoneIds().stream().filter(str -> str.contains("Canada"))
		.forEach(zone -> displayCurrentTime(zone));
	}
	
	void displayCurrentTime(String zoneName) {
		DateTimeFormatter dtf = DateTimeFormatter
				.ofPattern("dd.MM.YY / (HH:mm) / VV" );
		System.out.println(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(zoneName))
				.format(dtf));
	}

}

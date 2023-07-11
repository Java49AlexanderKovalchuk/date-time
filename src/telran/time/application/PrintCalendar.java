package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {

	private static final int TITLE_OFFSET = 8;
	static DayOfWeek[] daysOfWeek = DayOfWeek.values();
	
	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstWeekDay());
		printDays(recordArguments.month(), recordArguments.year(), 
				recordArguments.firstWeekDay());
		
	}

	private static void printDays(int month, int year, DayOfWeek weekFromDay) {
		
		int nDays = getNumberOfDays(month, year);
		int currentWeekDay = getFirstWeekDay(month, year, weekFromDay);
		//int firstWD = weekFromDay.getValue();
		
		printOffset(currentWeekDay);
		for(int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			currentWeekDay ++;
			if(currentWeekDay == 7) {
				currentWeekDay = 0;
				System.out.println();
			}
		}
		
	}

	private static void printOffset(int currentWeekDay) {
		System.out.printf("%s", " ".repeat(4 * currentWeekDay)); 
		
	}

	private static int getFirstWeekDay(int month, int year, DayOfWeek weekFrom) {
		int firstWeekDay = LocalDate.of(year, month, 1).get(ChronoField.DAY_OF_WEEK);
		int firstValue = weekFrom.getValue(); 
		int dif = firstWeekDay - firstValue;
		
		return dif < 0 ? dif + 7 : dif;
		
	}

	private static int getNumberOfDays(int month, int year) {
		YearMonth ym =  YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek firstWeekDay) {
		System.out.print("  ");
		for(int i = 0; i < 7; i++) {
			System.out.printf("%s ", firstWeekDay.plus(i)
					.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
		}
		
		//******
//		Arrays.stream(daysOfWeek).forEach(dw -> 
//		System.out.printf("%s ", 
//				dw.getDisplayName(TextStyle.SHORT, Locale.getDefault())));
		
		System.out.println();
		
	}

	private static void printTitle(int monthNumber, int year) {
		Month month = Month.of(monthNumber);
		String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
		System.out.printf("%s%s, %d\n", " ".repeat(TITLE_OFFSET), monthName, year );
		
	} 

	private static RecordArguments getRecordArguments(String[] args) throws Exception {
		LocalDate ld = LocalDate.now();
		int month = args.length == 0 ? ld.get(ChronoField.MONTH_OF_YEAR) : 
			getMonth(args[0]); 
		int year = args.length > 1 ? getYear(args[1]) : 
			ld.get(ChronoField.YEAR);
		
		DayOfWeek firstWeekDay =  args.length < 3 ? daysOfWeek[0] : 
			 getFirstWeekDay(args[2]);
			
		return new RecordArguments(month, year, firstWeekDay);
	}

	private static DayOfWeek getFirstWeekDay(String firstWeekDayString) throws Exception {
		firstWeekDayString = firstWeekDayString.toUpperCase();
		if(!Arrays.stream(daysOfWeek).map(n -> n.toString())
				.anyMatch(firstWeekDayString::equals)) {
			throw new Exception("first day must be in range [monday - saturday]");
		}
		return DayOfWeek.valueOf(firstWeekDayString);
	}

	private static int getYear(String yearString) throws Exception {
		String message = "";
		int year = 0;
		try {
			year = Integer.parseInt(yearString);
			if(year < 0) {
				message = "year must be a positive number";
			}
		} catch (NumberFormatException e) {
			message = "year must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return year;
	}

	private static int getMonth(String monthString) throws Exception {
		String message = "";
		int month = 0;
		try {
			month = Integer.parseInt(monthString);
			if(month < 1 || month > 12) {
				message = "month must be in the range [1-12]";
			}
		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return month;
	}

}

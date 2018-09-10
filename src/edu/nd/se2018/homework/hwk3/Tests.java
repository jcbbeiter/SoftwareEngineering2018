package edu.nd.se2018.homework.hwk3;
import org.junit.*;

public class Tests {
	
	private static Race testRace;
	
	@BeforeClass
	public static void oneTimeSetup() {
		testRace = new Race();
		System.out.println("BeforeClass");
	}
	
	@Before
	public void setUp() {
		testRace.clearRace();
	}
	
	@Test
	public void test1() {
		testRace.enrollHorse("Hobby Horse", 0, new SlowStartStrategy());
		testRace.enrollHorse("Rocking Horse", 0, new EarlySprintStrategy());
		testRace.enrollHorse("Jeff", 1, new SteadyRunStrategy());
		assert(testRace.getWinner() == "Jeff");
	}
	
	@Test
	public void test2() {
		testRace.enrollHorse("Seabiscuit", 5, new SteadyRunStrategy());
		testRace.enrollHorse("Tim", 2, new EarlySprintStrategy());
		testRace.enrollHorse("Jimbo", 3, new SlowStartStrategy());
		assert(testRace.getWinner() == "Seabiscuit");
	}
	
	@Test
	public void test3() {
		testRace.enrollHorse("Ol' Faithful", 4, new SteadyRunStrategy());
		testRace.enrollHorse("Bolt", 4, new EarlySprintStrategy());
		testRace.enrollHorse("Heater", 4, new SlowStartStrategy());
		assert(testRace.getWinner() == "Heater");
	}
}

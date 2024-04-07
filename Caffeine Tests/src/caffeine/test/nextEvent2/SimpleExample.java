package caffeine.test.nextEvent2;

public class SimpleExample {
	public static void main(final String[] args) {
		final SimpleExample simpleExample = new SimpleExample();
		simpleExample.run();
	}
	private final void run() {
		final HelloWorld helloWorld = new HelloWorld();
		for (int i = 0; i < 10; i++) {
			System.out.println(helloWorld.getGreetings());
		}
	}
}
package glass.example.complexDelegation;

public class Worker2 implements IWorker{

	private int nbQueries;
	
	public Worker2() {
		this.nbQueries = 0;
	}
	
	public void doOperation() {
		nbQueries++;
		System.out.println("Worker 2 has answered " + String.valueOf(nbQueries) + " queries");
	}

	@Override
	public int getHoursWorked() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void receivePay(int amount) {
		// TODO Auto-generated method stub
		
	}
}

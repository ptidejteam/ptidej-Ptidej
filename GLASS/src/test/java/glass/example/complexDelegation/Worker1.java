package glass.example.complexDelegation;

public class Worker1 implements IWorker{

	public void doOperation() {
		System.out.println("Worker 1 is working");
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

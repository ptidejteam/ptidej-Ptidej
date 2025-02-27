package glass.example.complexDelegation;

public class Contractor1 implements IWorker{

	private IWorker worker;
	
	public Contractor1() {
		this.worker = new Worker1();
	}
	
	@Override
	public void doOperation() {
		this.worker.doOperation();
	}

	@Override
	public int getHoursWorked() {
		return this.worker.getHoursWorked();
	}
	
	public void payWorker() {
		
	}

	@Override
	public void receivePay(int amount) {
	}
}

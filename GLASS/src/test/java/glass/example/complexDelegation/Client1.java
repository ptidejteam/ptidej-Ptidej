package glass.example.complexDelegation;

public class Client1 {
	
	private IWorker worker;
	
	public Client1() {
		this.worker = new Worker1();
	}
	
	public void doOperation() {
		this.worker.doOperation();
	}
	
	public void payWorker(int amount) {
		
	}
}

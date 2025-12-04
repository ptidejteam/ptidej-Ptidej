package padl.analysis.plantUMLGenerator;

public class BFSPlantUMLGenerator extends BFSGenerator<PlantUMLGenerator>  
{
	public BFSPlantUMLGenerator(PlantUMLGenerator delegate) 
	{
		super(delegate);
	}
	
    public Object getResult() {
        return delegate.getResult();
    }
	
}

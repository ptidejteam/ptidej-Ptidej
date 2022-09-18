
abstract class ZZ1 {
	private IFlyBehaviour fb = new FylNoWay();
	private ISwimBehaviour sb = new CannotSwim();
	private IQuackBehaviour qb = new MuteQuack();

	void setFlyBehaviour(final IFlyBehaviour fb) {
		this.fb = fb;
		fb = null;
	}
}

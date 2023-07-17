package choco.test.search;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Problem;
import choco.Solver;
import choco.integer.IntVar;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.ZebraTest.java, last modified by flaburthe 16 janv. 2004 14:57:02 */

public class ZebraTest extends TestCase {
	private static Logger logger = Logger.getLogger("choco.test.zebra");

	private Problem pb;
	private IntVar green, blue, yellow, ivory, red;
	private IntVar diplomat, painter, sculptor, doctor, violinist;
	private IntVar norwegian, english, japanese, spaniard, italian;
	private IntVar wine, milk, coffee, water, tea;
	private IntVar fox, snail, horse, dog, zebra;
	private IntVar[] colors, trades, nationalities, drinks, pets;
	private IntVar[][] arrays;
	public ZebraTest(final String name) {
		super(name);
	}

	protected void setUp() {
		ZebraTest.logger.fine("Zebra Testing...");
		this.pb = new Problem();
		this.green = this.pb.makeEnumIntVar("green", 1, 5);
		this.blue = this.pb.makeEnumIntVar("blue", 1, 5);
		this.yellow = this.pb.makeEnumIntVar("yellow", 1, 5);
		this.ivory = this.pb.makeEnumIntVar("ivory", 1, 5);
		this.red = this.pb.makeEnumIntVar("red", 1, 5);
		this.diplomat = this.pb.makeEnumIntVar("diplomat", 1, 5);
		this.painter = this.pb.makeEnumIntVar("painter", 1, 5);
		this.sculptor = this.pb.makeEnumIntVar("sculptor", 1, 5);
		this.doctor = this.pb.makeEnumIntVar("doctor", 1, 5);
		this.violinist = this.pb.makeEnumIntVar("violinist", 1, 5);
		this.norwegian = this.pb.makeEnumIntVar("norwegian", 1, 5);
		this.english = this.pb.makeEnumIntVar("english", 1, 5);
		this.japanese = this.pb.makeEnumIntVar("japanese", 1, 5);
		this.spaniard = this.pb.makeEnumIntVar("spaniard", 1, 5);
		this.italian = this.pb.makeEnumIntVar("italian", 1, 5);
		this.wine = this.pb.makeEnumIntVar("wine", 1, 5);
		this.milk = this.pb.makeEnumIntVar("milk", 1, 5);
		this.coffee = this.pb.makeEnumIntVar("coffee", 1, 5);
		this.water = this.pb.makeEnumIntVar("water", 1, 5);
		this.tea = this.pb.makeEnumIntVar("tea", 1, 5);
		this.fox = this.pb.makeEnumIntVar("fox", 1, 5);
		this.snail = this.pb.makeEnumIntVar("snail", 1, 5);
		this.horse = this.pb.makeEnumIntVar("horse", 1, 5);
		this.dog = this.pb.makeEnumIntVar("dog", 1, 5);
		this.zebra = this.pb.makeEnumIntVar("zebra", 1, 5);
		this.colors =
			new IntVar[] { this.green, this.blue, this.yellow, this.ivory,
					this.red };
		this.trades =
			new IntVar[] { this.diplomat, this.painter, this.sculptor,
					this.doctor, this.violinist };
		this.nationalities =
			new IntVar[] { this.norwegian, this.english, this.japanese,
					this.spaniard, this.italian };
		this.drinks =
			new IntVar[] { this.wine, this.milk, this.coffee, this.water,
					this.tea };
		this.pets =
			new IntVar[] { this.fox, this.snail, this.horse, this.dog,
					this.zebra };
		this.arrays =
			new IntVar[][] { this.colors, this.trades, this.nationalities,
					this.drinks, this.pets };
	}

	protected void tearDown() {
		this.pb = null;
		this.green = null;
		this.blue = null;
		this.yellow = null;
		this.ivory = null;
		this.red = null;
		this.diplomat = null;
		this.painter = null;
		this.sculptor = null;
		this.doctor = null;
		this.violinist = null;
		this.norwegian = null;
		this.english = null;
		this.japanese = null;
		this.spaniard = null;
		this.italian = null;
		this.wine = null;
		this.milk = null;
		this.coffee = null;
		this.water = null;
		this.tea = null;
		this.fox = null;
		this.snail = null;
		this.horse = null;
		this.dog = null;
		this.zebra = null;
		this.colors = null;
		this.trades = null;
		this.nationalities = null;
		this.drinks = null;
		this.pets = null;
		this.arrays = null;
	}

	public void test0() {
		for (int a = 0; a < 5; a++) {
			for (int i = 0; i < 4; i++) {
				for (int j = i + 1; j < 5; j++) {
					this.pb.post(this.pb.neq(
						this.arrays[a][i],
						this.arrays[a][j]));
				}
			}
		}
		this.pb.post(this.pb.eq(this.english, this.red));
		this.pb.post(this.pb.eq(this.spaniard, this.dog));
		this.pb.post(this.pb.eq(this.coffee, this.green));
		this.pb.post(this.pb.eq(this.italian, this.tea));
		this.pb.post(this.pb.eq(this.sculptor, this.snail));
		this.pb.post(this.pb.eq(this.diplomat, this.yellow));
		this.pb.post(this.pb.eq(this.green, this.pb.plus(this.ivory, 1)));
		this.pb.post(this.pb.eq(this.milk, 3));
		this.pb.post(this.pb.eq(this.norwegian, 1));
		// pb.post(((doctor - fox == 1) or (doctor - fox == -1)));
		this.pb.post(this.pb.eq(this.pb.minus(this.doctor, this.fox), 1));
		this.pb.post(this.pb.eq(this.violinist, this.wine));
		this.pb.post(this.pb.eq(this.japanese, this.painter));
		//pb.post(((diplomat - horse == 1) or (diplomat - horse == -1)));
		this.pb.post(this.pb.eq(this.pb.minus(this.diplomat, this.horse), -1));
		//pb.post(((norwegian - blue == 1) or (norwegian - blue == -1)));
		this.pb.post(this.pb.eq(this.pb.minus(this.norwegian, this.blue), -1));

		this.pb.solve(true);
		final Solver s = this.pb.getSolver();
		Assert.assertEquals(1, s.getNbSolutions());

		for (int a = 0; a < 5; a++) {
			for (int i = 0; i < 5; i++) {
				Assert.assertTrue(this.arrays[a][i].isInstantiated());
			}
		}
		Assert.assertEquals(1, this.norwegian.getValue());
		Assert.assertEquals(1, this.diplomat.getValue());
		Assert.assertEquals(1, this.fox.getValue());
		Assert.assertEquals(1, this.water.getValue());
		Assert.assertEquals(1, this.yellow.getValue());
		Assert.assertEquals(2, this.italian.getValue());
		Assert.assertEquals(2, this.doctor.getValue());
		Assert.assertEquals(2, this.horse.getValue());
		Assert.assertEquals(2, this.tea.getValue());
		Assert.assertEquals(2, this.blue.getValue());
		Assert.assertEquals(3, this.english.getValue());
		Assert.assertEquals(3, this.sculptor.getValue());
		Assert.assertEquals(3, this.snail.getValue());
		Assert.assertEquals(3, this.milk.getValue());
		Assert.assertEquals(3, this.red.getValue());
		Assert.assertEquals(4, this.spaniard.getValue());
		Assert.assertEquals(4, this.violinist.getValue());
		Assert.assertEquals(4, this.dog.getValue());
		Assert.assertEquals(4, this.wine.getValue());
		Assert.assertEquals(4, this.ivory.getValue());
		Assert.assertEquals(5, this.japanese.getValue());
		Assert.assertEquals(5, this.painter.getValue());
		Assert.assertEquals(5, this.zebra.getValue());
		Assert.assertEquals(5, this.coffee.getValue());
		Assert.assertEquals(5, this.green.getValue());
	}
}
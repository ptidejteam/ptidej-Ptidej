package choco.real;

import choco.Problem;
import choco.real.exp.RealIntervalConstant;

/**
 * Some tools for float computing.
 * Inspired from IAMath : interval.sourceforge.net
 */
public class RealMath {
	static final double ZERO = 0.0;
	static final double NEG_ZER0 = 0.0 * -1.0;

	public static RealInterval add(final RealInterval x, final RealInterval y) {
		return new RealIntervalConstant(RealMath.prevFloat(x.getInf()
				+ y.getInf()), RealMath.nextFloat(x.getSup() + y.getSup()));
	}

	public static RealInterval firstHalf(final RealInterval i) {
		double inf = i.getInf();
		if (inf == Double.NEGATIVE_INFINITY) {
			inf = -Double.MAX_VALUE;
		}
		double sup = i.getSup();
		if (sup == Double.POSITIVE_INFINITY) {
			sup = Double.MAX_VALUE;
		}
		return new RealIntervalConstant(i.getInf(), inf + sup / 2.0 - inf / 2.0);
	}

	public static boolean isCanonical(
		final RealInterval i,
		final double precision) {
		final double inf = i.getInf();
		final double sup = i.getSup();
		if (sup - inf < precision) {
			return true;
		}
		if (RealMath.nextFloat(inf) >= sup) {
			return true;
		}
		return false;
	}

	public static void main(final String args[]) {
		new Problem();
		final RealInterval r1 = new RealIntervalConstant(1, 4);
		final RealInterval r2 = new RealIntervalConstant(-5, -3);
		final RealInterval r3 = new RealIntervalConstant(-3, 4);
		final RealInterval r4 =
			new RealIntervalConstant(0, Double.POSITIVE_INFINITY);

		System.out.println(r1 + "+" + r2 + " = " + RealMath.add(r1, r2));
		System.out.println(r1 + "-" + r2 + " = " + RealMath.sub(r1, r2));
		System.out.println(r1 + "*" + r2 + " = " + RealMath.mul(r1, r2));
		System.out.println(r1 + "/" + r2 + " = " + RealMath.odiv(r1, r2));
		System.out.println(r1 + "/" + r3 + " (in " + r4 + ") = "
				+ RealMath.odiv_wrt(r1, r3, r4));
	}

	public static RealInterval mul(final RealInterval x, final RealInterval y) {
		double i, s;

		if (x.getInf() == 0.0 && x.getSup() == 0.0 || y.getInf() == 0.0
				&& y.getSup() == 0.0) {
			i = 0.0;
			s = RealMath.NEG_ZER0; // Ca peut etre utile pour rejoindre des intervalles : si on veut aller de -5 a 0,
			// ca sera 0-.
		}
		else {
			if (x.getInf() >= 0.0) {
				if (y.getInf() >= 0.0) {
					i =
						Math.max(
							RealMath.ZERO,
							RealMath.prevFloat(x.getInf() * y.getInf())); // Si x et y positifs, on ne veut pas etre n?gatif !
					s = RealMath.nextFloat(x.getSup() * y.getSup());
				}
				else if (y.getSup() <= 0.0) {
					i = RealMath.prevFloat(x.getSup() * y.getInf());
					s =
						Math.min(
							RealMath.ZERO,
							RealMath.nextFloat(x.getInf() * y.getSup()));
				}
				else {
					i = RealMath.prevFloat(x.getSup() * y.getInf());
					s = RealMath.nextFloat(x.getSup() * y.getSup());
				}
			}
			else if (x.getSup() <= 0.0) {
				if (y.getInf() >= 0.0) {
					i = RealMath.prevFloat(x.getInf() * y.getSup());
					s =
						Math.min(
							RealMath.ZERO,
							RealMath.nextFloat(x.getSup() * y.getInf()));
				}
				else if (y.getSup() <= 0.0) {
					i =
						Math.max(
							RealMath.ZERO,
							RealMath.prevFloat(x.getSup() * y.getSup()));
					s = RealMath.nextFloat(x.getInf() * y.getInf());
				}
				else {
					i = RealMath.prevFloat(x.getInf() * y.getSup());
					s = RealMath.nextFloat(x.getInf() * y.getInf());
				}
			}
			else {
				if (y.getInf() >= 0.0) {
					i = RealMath.prevFloat(x.getInf() * y.getSup());
					s = RealMath.nextFloat(x.getSup() * y.getSup());
				}
				else if (y.getSup() <= 0.0) {
					i = RealMath.prevFloat(x.getSup() * y.getInf());
					s = RealMath.nextFloat(x.getInf() * y.getInf());
				}
				else {
					i =
						Math.min(
							RealMath.prevFloat(x.getInf() * y.getSup()),
							RealMath.prevFloat(x.getSup() * y.getInf()));
					s =
						Math.max(
							RealMath.nextFloat(x.getInf() * y.getInf()),
							RealMath.nextFloat(x.getSup() * y.getSup()));
				}
			}
		}

		return new RealIntervalConstant(i, s);
	}

	public static double nextFloat(final double x) {
		if (x < 0) {
			return Double.longBitsToDouble(Double.doubleToLongBits(x) - 1);
		}
		else if (x == 0) {
			return Double.longBitsToDouble(1);
		}
		else if (x < Double.POSITIVE_INFINITY) {
			return Double.longBitsToDouble(Double.doubleToLongBits(x) + 1);
		}
		else {
			return x; // nextFloat(infty) = infty
		}
	}

	/**
	 * y should not contain 0 !
	 * @param x
	 * @param y
	 * @return
	 */
	public static RealInterval odiv(final RealInterval x, final RealInterval y) {
		if (y.getInf() <= 0.0 && y.getSup() >= 0.0) {
			throw new UnsupportedOperationException();
		}
		else {
			final double yl = y.getInf();
			double yh = y.getSup();
			double i, s;
			if (yh == 0.0) {
				yh = RealMath.NEG_ZER0;
			}

			if (x.getInf() >= 0.0) {
				if (yl >= 0.0) {
					i =
						Math.max(
							RealMath.ZERO,
							RealMath.prevFloat(x.getInf() / yh));
					s = RealMath.nextFloat(x.getSup() / yl);
				}
				else { // yh <= 0
					i = RealMath.prevFloat(x.getSup() / yh);
					s =
						Math.min(
							RealMath.ZERO,
							RealMath.nextFloat(x.getInf() / yl));
				}
			}
			else if (x.getSup() <= 0.0) {
				if (yl >= 0.0) {
					i = RealMath.prevFloat(x.getInf() / yl);
					s =
						Math.min(
							RealMath.ZERO,
							RealMath.nextFloat(x.getSup() / yh));
				}
				else {
					i =
						Math.max(
							RealMath.ZERO,
							RealMath.prevFloat(x.getSup() / yl));
					s = RealMath.nextFloat(x.getInf() / yh);
				}
			}
			else {
				if (yl >= 0.0) {
					i = RealMath.prevFloat(x.getInf() / yl);
					s = RealMath.nextFloat(x.getSup() / yl);
				}
				else {
					i = RealMath.prevFloat(x.getSup() / yh);
					s = RealMath.nextFloat(x.getInf() / yh);
				}
			}
			return new RealIntervalConstant(i, s);
		}
	}

	public static RealInterval odiv_wrt(
		final RealInterval x,
		final RealInterval y,
		final RealInterval res) {
		if (y.getInf() > 0.0 || y.getSup() < 0.0) { // y != 0
			return RealMath.odiv(x, y);
		}
		else {
			double resl = res.getInf();
			double resh = res.getSup();

			if (x.getInf() >= 0.0) {
				final double tmp_neg =
					RealMath.nextFloat(x.getInf() / y.getInf()); // la plus grande valeur negative
				final double tmp_pos =
					RealMath.prevFloat(x.getInf() / y.getSup()); // la plus petite valeur positive

				if ((resl > tmp_neg || resl == 0.0) && resl < tmp_pos) {
					resl = tmp_pos;
				}
				if ((resh < tmp_pos || resh == 0.0) && resh > tmp_neg) {
					resh = tmp_neg;
				}
			}
			else if (x.getSup() <= 0.0) {
				final double tmp_neg =
					RealMath.nextFloat(x.getSup() / y.getSup());
				final double tmp_pos =
					RealMath.nextFloat(x.getSup() / y.getInf());

				if ((resl > tmp_neg || resl == 0.0) && resl < tmp_pos) {
					resl = tmp_pos;
				}
				if ((resh < tmp_pos || resh == 0.0) && resh > tmp_neg) {
					resh = tmp_neg;
				}
			}

			return new RealIntervalConstant(resl, resh);
		}
	}

	public static double prevFloat(final double x) {
		if (x == 0.0) {
			return -RealMath.nextFloat(0.0);
		}
		else {
			return -RealMath.nextFloat(-x);
		}
	}

	public static RealInterval secondHalf(final RealInterval i) {
		double inf = i.getInf();
		if (inf == Double.NEGATIVE_INFINITY) {
			inf = -Double.MAX_VALUE;
		}
		double sup = i.getSup();
		if (sup == Double.POSITIVE_INFINITY) {
			sup = Double.MAX_VALUE;
		}
		return new RealIntervalConstant(RealMath.nextFloat(inf + sup / 2.0
				- inf / 2.0), i.getSup());
	}

	public static RealInterval sub(final RealInterval x, final RealInterval y) {
		return new RealIntervalConstant(RealMath.prevFloat(x.getInf()
				- y.getSup()), RealMath.nextFloat(x.getSup() - y.getInf()));
	}
}
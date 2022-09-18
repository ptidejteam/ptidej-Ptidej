/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package dram.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;
import JIP.engine.JIPTerm;
import caffeine.logic.Engine;
import caffeine.logic.EngineListener;
import caffeine.logic.Event;

/**
 * @author rachedsa
 */
public class ListenerCaffeine implements EngineListener {
	private FileOutputStream fos;

	private Vector vectStack = new Vector();

	private OutputStreamWriter osw;

	private FileOutputStream fosall;

	private OutputStreamWriter oswall;

	public ListenerCaffeine() {
	}

	public void engineInitialized(final Engine engine) {
		try {
			this.fos =
				new FileOutputStream(
					new File(
						"C:\\Documents and Settings\\rachedsa\\Bureau\\RJhotDrawRectCercle.txt"));
			this.osw = new OutputStreamWriter(this.fos);

			this.fosall =
				new FileOutputStream(
					new File(
						"C:\\Documents and Settings\\rachedsa\\Bureau\\RJhotDrawRectCercleAll.txt"));
			this.oswall = new OutputStreamWriter(this.fosall);

		}
		catch (Exception wri) {
			System.out.println("Impossible d'ouvrir le fichier");
			//System.exit(1);
		}
	}

	public void engineStarted(final Engine engine) {

	}

	public void engineTerminated(
		JIPTerm solution,
		Engine engine,
		long time,
		long steps) {
		try {
			this.osw.close();
			this.fos.close();
			this.oswall.close();
			this.fosall.close();
		}
		catch (Exception wri) {
			System.out.println("Impossible de fermer le fichier");
		}
	}

	public void eventEmitted(final Event event) {
		//Vector vectStack = new Vector();

		try {
			writeevent(event);

			Vector vectevent = new Vector();
			String evenement = null;
			String getType = null;
			getType = event.getType();
			String SourceName;
			vectevent.removeAllElements();
			if (getType.equals("methodEntry0")) {
				if (event.getName().equals("isFocusTraversable")) {

				}
				else {
					if (this.vectStack.size() == 0) {
						SourceName = "Start";
					}
					else {
						Vector vv = new Vector();
						vv =
							(Vector) this.vectStack.elementAt(this.vectStack
								.size() - 1);
						SourceName = (String) vv.elementAt(1);
						//					SourceName = (String) vectStack
						//							.elementAt(vectStack.size() - 1);
					}

					vectevent.addElement(event.getType());
					vectevent.addElement(event.getComment());
					vectevent.addElement(event.getName());

					push(this.vectStack, vectevent);
					//push(vectStack, event.getComment());
					System.out.println("Vecteur : ");
					print(this.vectStack);

					evenement =
						event.getType() + "|" + SourceName + "|"
								+ event.getComment() + "|" + event.getName()
								+ "|" + event.getReceiverID() + "|"
								+ event.getArgumentID() + "|" + '\n';
					//				System.out.println("evenement " + evenement);
					//				System.out.println("Vecteur : ");
					//				print(vectStack);
				}
			}
			if (getType.equals("constructorEntry0")) {
				Vector vv = new Vector();
				vv =
					(Vector) this.vectStack
						.elementAt(this.vectStack.size() - 1);
				SourceName = (String) vv.elementAt(1);

				vectevent.addElement(event.getType());
				vectevent.addElement(event.getName());

				push(this.vectStack, vectevent);
				//SourceName = (String) vectStack.elementAt(vectStack.size() -
				// 1);
				//push(vectStack, event.getName());

				evenement =
					event.getType() + "|" + SourceName + "|" + event.getName()
							+ "|" + event.getReceiverID() + "|"
							+ event.getArgumentID() + "|" + '\n';
				//				System.out.println("evenement " + evenement);
				System.out.println("Vecteur : ");
				print(this.vectStack);
			}
			if (getType.equals("methodExit0")) {
				if (event.getName().equals("isFocusTraversable")) {

				}
				else {
					Vector vv = new Vector();
					Vector vv1 = new Vector();
					boolean found = true;
					SourceName = "";

					if (this.vectStack.size() != 0) {
						vv =
							(Vector) this.vectStack.elementAt(this.vectStack
								.size() - 1);
						pop(this.vectStack);
					}

					if (this.vectStack.size() == 0) {
						SourceName = "Fin";
					}
					else {
						while (found) {
							if ((vv.elementAt(1).equals(event.getComment()))
									& (vv.elementAt(2).equals(event.getName()))) {
								found = false;
								if (this.vectStack.size() == 0) {
									SourceName = "Fin";
								}
								else {
									vv1 =
										(Vector) this.vectStack
											.elementAt(this.vectStack.size() - 1);
									SourceName = (String) vv1.elementAt(1);
								}
							}
							else {
								found = true;
								if (this.vectStack.size() == 0) {
									SourceName = "Fin";
									found = false;
								}
								else {
									vv =
										(Vector) this.vectStack
											.elementAt(this.vectStack.size() - 1);
									pop(this.vectStack);
								}
							}
						}
						//					vv1 = (Vector) vectStack.elementAt(vectStack.size() -
						// 1);
						//					SourceName = (String) vv1.elementAt(1);
					}
					evenement =
						event.getType() + "|" + SourceName + "|"
								+ event.getComment() + "|" + event.getName()
								+ "|" + event.getReceiverID() + "|"
								+ event.getArgumentID() + "|" + '\n';
					//+ event.getName() + "|"
					//SourceName = (String)
					// vectStack.elementAt(vectStack.size() -
					// 1);
					//				System.out.println("evenement " + evenement);
					//				System.out.println("evenement " + SourceName);
					//				//pop(vectStack);
					System.out.println("Vecteur : ");
					print(this.vectStack);

				}
			}
			if (getType.equals("constructorExit0")) {

				Vector vv = new Vector();
				Vector vv1 = new Vector();
				boolean found = true;
				SourceName = "";
				if (this.vectStack.size() != 0) {
					vv =
						(Vector) this.vectStack
							.elementAt(this.vectStack.size() - 1);
					pop(this.vectStack);
				}
				if (this.vectStack.size() == 0) {
					SourceName = "Fin";
				}
				else {
					while (found) {
						if ((vv.elementAt(1).equals(event.getName()))) {
							found = false;
							if (this.vectStack.size() == 0) {
								SourceName = "Fin";
							}
							else {
								vv1 =
									(Vector) this.vectStack
										.elementAt(this.vectStack.size() - 1);
								SourceName = (String) vv1.elementAt(1);
							}

						}
						else {
							found = true;
							if (this.vectStack.size() == 0) {
								SourceName = "Fin";
								found = false;
							}
							else {
								vv =
									(Vector) this.vectStack
										.elementAt(this.vectStack.size() - 1);
								pop(this.vectStack);
							}
						}
					}
					//					vv1 = (Vector) vectStack.elementAt(vectStack.size() - 1);
					//					SourceName = (String) vv1.elementAt(1);
				}

				//
				//				pop(vectStack);
				//				if (vectStack.size() == 0) {
				//					SourceName = "Fin";
				//				} else
				//					SourceName = (String) vectStack
				//							.elementAt(vectStack.size() - 1);
				//

				evenement =
					event.getType() + "|" + SourceName + "|" + event.getName()
							+ "|" + event.getReceiverID() + "|"
							+ event.getArgumentID() + "|" + '\n';

				//				System.out.println("evenement " + evenement);
				//				System.out.println("evenement " + SourceName);
				//				//pop(vectStack);
				System.out.println("Vecteur : ");
				print(this.vectStack);

			}
			if (getType.equals("finalizerEntry0")) {
				evenement =
					event.getType() + "|" + event.getName() + "|"
							+ event.getReceiverID() + "|"
							+ event.getArgumentID() + "|" + "\n";
			}
			if (getType.equals("finalizerExit0")) {
				evenement =
					event.getType() + "|" + event.getName() + "|"
							+ event.getReceiverID() + "|"
							+ event.getArgumentID() + "|" + "\n";
			}
			this.osw.write(evenement);

		}
		catch (Exception wri) {
			System.out.println("Impossible d'?crire dans le fichier");
		}
	}

	public void push(Vector vect, String element) {
		vect.addElement(element);

	}

	public void push(Vector vect, Vector vect1) {
		vect.addElement(vect1);

	}

	public void pop(Vector vect) {
		vect.removeElementAt(vect.size() - 1);

	}

	public void print(Vector vect) {
		for (int i = 0; i < vect.size(); i++) {
			System.out.print(vect.elementAt(i) + "    " + i);
		}
		System.out.print("\n");

	}

	public void writeevent(Event event) {
		try {
			String ev = null;
			String type = event.getType();

			if (event.getName().equals("open")) {
				System.out.println("Le vecteur contient ");
				print(this.vectStack);
				System.out.println("Le vecteur finit ");
			}

			if (type.equals("methodEntry0")) {
				ev =
					event.getType() + "|" + event.getSourceName() + "|"
							+ event.getComment() + "|" + event.getName() + "|"
							+ event.getReceiverID() + "|"
							+ event.getArgumentID() + "|" + '\n';
			}

			if (type.equals("constructorEntry0")) {
				ev =
					event.getType() + "|" + event.getSourceName() + "|"
							+ event.getName() + "|" + event.getReceiverID()
							+ "|" + event.getArgumentID() + "|" + '\n';
			}

			if (type.equals("methodExit0")) {
				ev =
					event.getType() + "|" + event.getSourceName() + "|"
							+ event.getComment() + "|" + event.getName() + "|"
							+ event.getReceiverID() + "|"
							+ event.getArgumentID() + "|" + '\n';
			}

			if (type.equals("constructorExit0")) {
				ev =
					event.getType() + "|" + event.getSourceName() + "|"
							+ event.getName() + "|" + event.getReceiverID()
							+ "|" + event.getArgumentID() + "|" + '\n';
			}

			this.oswall.write(ev);
		}
		catch (Exception wri) {
			System.out.println("Impossible d'écrire dans le fichier");
		}
	}
}

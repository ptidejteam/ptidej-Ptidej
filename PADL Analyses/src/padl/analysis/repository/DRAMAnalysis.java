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
package padl.analysis.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.ICreation;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IRelationship;
import padl.kernel.IUseRelationship;
import util.help.IHelpURL;
import util.io.ProxyConsole;

/**
 * @author Samah Rached
 */
public final class DRAMAnalysis implements IAnalysis, IHelpURL {
	public static final String OPERATION = "/* OPERATION */ ";
	public static final String TEMPLATE_METHOD = "/* TEMPLATE METHOD */ ";
	private FileReader rd;
	private BufferedReader buf;
	//HashMap hashmap = new HashMap();
	List arraylist = new ArrayList();
	String chaine = null;
	List listFile;

	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		try {
			this.rd = new FileReader("../DRAM Tests/groups/diff4Mod.txt");
			//"C:\\Documents and Settings\\rachedsa\\Bureau\\JunitAE1MRun.txt");	
			//"C:\\Documents and Settings\\rachedsa\\Bureau\\JunitSanserreur.txt");
			//	"C:\\Documents and Settings\\rachedsa\\Bureau\\junit.txt");
			//"C:\\Documents and Settings\\rachedsa\\Bureau\\testRelation sur bureau\\testRelation.txt"
			this.buf = new BufferedReader(this.rd);

			String nameOfClass;
			String token1;
			String token2;
			String token3;
			String token4;
			StringTokenizer st;
			String nameOfTargetEntity;
			String idOfgetID;
			String nameMethod;
			this.listFile = new ArrayList();

			String weight = "1";
			//anAbstractLevelModel.walk(tt);
			while ((this.chaine = this.buf.readLine()) != null) {
				//System.out.println("chaine " + chaine);				
				st = new StringTokenizer(this.chaine, "|");
				token1 = st.nextToken();
				token2 = st.nextToken();
				token3 = st.nextToken();
				//				if (token2.equals("junit.framework.TestCase")){
				//					System.out.println("chaine " + chaine);
				//				}

				if (token1.equals("constructorEntry0")
						|| token1.equals("constructorExit0")) {
					EntityList entityList =
						new EntityList(token1, token2, token3, null, "1");
					this.listFile.add(entityList);
				}
				else {
					token4 = st.nextToken();
					EntityList entityList =
						new EntityList(token1, token2, token3, token4, "1");
					this.listFile.add(entityList);
				}
				//|| token1.equals("constructorEntry0")
				//if (token1.equals("methodEntry0")) {	

				final Iterator iterator =
					anAbstractModel.getIteratorOnTopLevelEntities();
				while (iterator.hasNext()) {
					final IClass clazz = (IClass) iterator.next();
					nameOfClass = clazz.getDisplayName();
					if (nameOfClass.equals(token2)) {
						final Iterator listOfInheritedEntities =
							clazz.getIteratorOnInheritedEntities();
						while (listOfInheritedEntities.hasNext()) {
							final IFirstClassEntity inheritedEntity =
								(IFirstClassEntity) listOfInheritedEntities.next();
							if (token3.equals(inheritedEntity.getDisplayName())) {
								weight = "2";
								EntityMap entitymap =
									new EntityMap(
										nameOfClass,
										token3,
										"heritage",
										weight);

								EntityMap eentitymap =
									lookForEntityMap(entitymap);
								if (eentitymap == null) {
									this.arraylist.add(entitymap);
								}
								else {
									String ss;
									ss = (String) eentitymap.weight;
									if (Integer.parseInt(weight) > Integer
										.parseInt(ss)) {
										eentitymap.weight = weight;
									}
								}
							}
							//System.out.println(((IEntity)listOfInheritedEntities.get(j)).getName() );
						}
						if (token1.equals("methodEntry0")) {
							final Iterator iteratorOnRelationships =
								clazz
									.getIteratorOnConstituents(IRelationship.class);
							while (iteratorOnRelationships.hasNext()) {
								final IRelationship relation =
									(IRelationship) iteratorOnRelationships
										.next();
								idOfgetID = relation.getDisplayID();
								nameOfTargetEntity =
									relation.getTargetEntity().getDisplayName();
								if (nameOfTargetEntity.equals(token3)) {
									nameMethod =
										idOfgetID.substring(0, idOfgetID
											.lastIndexOf("_"));

									System.out.println(nameOfClass + " ---> "
											+ relation.getClass() + "  avec "
											+ nameOfTargetEntity + " par "
											+ nameMethod);

									if (relation instanceof ICreation) {
										weight = "3";
									}
									if (relation instanceof IUseRelationship) {
										weight = "4";
									}
									if (relation instanceof IAssociation) {
										weight = "5";
									}
									if (relation instanceof IAggregation) {
										weight = "6";
									}
									if (relation instanceof IComposition) {
										weight = "7";
									}
									EntityMap entitymap =
										new EntityMap(
											nameOfClass,
											nameOfTargetEntity,
											nameMethod,
											weight);

									EntityMap eentitymap =
										lookForEntityMap(entitymap);
									if (eentitymap == null) {
										//hashmap.put(entitymap, weight);
										this.arraylist.add(entitymap);
									}
									else {
										String ss;
										//ss = (String) hashmap
										//		.get(eentitymap);
										ss = (String) eentitymap.weight;
										if (Integer.parseInt(weight) > Integer
											.parseInt(ss)) {
											eentitymap.weight = weight;
											//hashmap.remove(eentitymap);
											//	hashmap.put(eentitymap,
											//		weight);
										}

									}
								}
							}
						}
					}
				}
			}
			this.buf.close();
			this.rd.close();

			//Set entrees = hashmap.entrySet();
			//Iterator iterateur = entrees.iterator();
			Iterator iterateur = this.arraylist.iterator();
			System.out.println("la map est :  ");
			while (iterateur.hasNext()) {
				//Map.Entry entree = (Map.Entry) iterateur.next();
				//EntityMap pp = (EntityMap) entree.getKey();
				EntityMap pp = (EntityMap) iterateur.next();

				System.out.println(pp.classSource + " ----> " + pp.classTarget
						+ "  " + pp.nameMethod + " - " + pp.weight);
			}

			//			System.out.println("la arraylist est :  ");
			//			Iterator iterat = listFile.iterator();
			//			while (iterat.hasNext()) {
			//				
			//				EntityList ll = (EntityList) iterat.next();
			//				System.out.println(ll.type + "  " + ll.classSource + " ----> " + ll.classTarget
			//						+ "  " + ll.nameMethod );
			//			}

			modifyTraceDynamic();
			//comparaison();
			finalTrace();

		}
		catch (Exception ex) {
			ProxyConsole.getInstance().errorOutput().println("Syntax error line ");
			ex.printStackTrace(ProxyConsole.getInstance().errorOutput());

		}
		return anAbstractModel;
	}

	public String getName() {
		return "DRAM Analysis";
	}

	public String getHelpURL() {
		return "http://www.ptidej.net/publications/documents/APSEC04.doc.pdf";
	}

	public EntityMap lookForEntityMap(EntityMap entity) {
		Iterator iterateur = this.arraylist.iterator();
		while (iterateur.hasNext()) {
			EntityMap mapEntity = (EntityMap) iterateur.next();
			if ((mapEntity.classSource.equals(entity.classSource))
					&& (mapEntity.classTarget.equals(entity.classTarget))
					&& (mapEntity.nameMethod.equals(entity.nameMethod))) {
				return mapEntity;
			}

		}
		return null;
	}

	public void modifyTraceDynamic() {
		Iterator iterateur = this.arraylist.iterator();
		String value;

		while (iterateur.hasNext()) {
			EntityMap mapEntity = (EntityMap) iterateur.next();
			value = (String) mapEntity.weight;
			Iterator iterat = this.listFile.iterator();
			while (iterat.hasNext()) {
				EntityList listEntity = (EntityList) iterat.next();
				if (listEntity.classSource.equals(mapEntity.classSource)
						&& (listEntity.classTarget
							.equals(mapEntity.classTarget))) {
					if (Integer.parseInt(listEntity.weight) < Integer
						.parseInt(value)) {
						listEntity.weight = value;
					}
				}
			}
		}
	}

	public void comparaison() {
		Iterator iterateur = this.arraylist.iterator();
		String value;
		int nbre = 0;
		String simplename;

		while (iterateur.hasNext()) {
			EntityMap mapEntity = (EntityMap) iterateur.next();
			value = (String) mapEntity.weight;
			simplename = computeSimpleName(mapEntity.classSource);
			if (simplename.equals(mapEntity.nameMethod)) {
				Iterator iterat = this.listFile.iterator();
				while (iterat.hasNext()) {
					EntityList listEntity = (EntityList) iterat.next();
					if (listEntity.type.equals("constructorEntry0")) {
						if (listEntity.classTarget
							.equals(mapEntity.classSource)) {
							listEntity.weight = value;
							nbre = nbre + 1;
							continue;
						}
					}
					if (nbre != 0) {
						listEntity.weight = value;
						if (listEntity.type.equals("constructorExit0")) {
							if (listEntity.classTarget
								.equals(mapEntity.classSource)) {
								nbre = nbre - 1;
							}
						}
					}
				}
			}
			else {
				Iterator iterat = this.listFile.iterator();
				while (iterat.hasNext()) {
					EntityList listEntity = (EntityList) iterat.next();
					if (listEntity.type.equals("methodEntry0")) {
						if (listEntity.classTarget
							.equals(mapEntity.classSource)) {
							if (listEntity.nameMethod
								.equals(mapEntity.nameMethod)) {

								listEntity.weight = value;
								nbre = nbre + 1;
								continue;
							}
						}
					}
					if (nbre != 0) {
						listEntity.weight = value;
						if (listEntity.type.equals("methodExit0")) {
							if (listEntity.classTarget
								.equals(mapEntity.classSource)) {
								if (listEntity.nameMethod
									.equals(mapEntity.nameMethod)) {
									nbre = nbre - 1;
								}
							}
						}
					}
				}
			}
		}

		System.out.println("la arraylist finale est :  ");
		Iterator iterat = this.listFile.iterator();
		while (iterat.hasNext()) {
			EntityList ll = (EntityList) iterat.next();
			System.out.println(ll.type + "  " + ll.classSource + " ----> "
					+ ll.classTarget + "  " + ll.nameMethod + " " + ll.weight);
		}
	}

	public static String computeSimpleName(final String fullyQualifiedName) {
		int index = fullyQualifiedName.lastIndexOf('.');
		if (index == -1) {
			return fullyQualifiedName;
		}
		return fullyQualifiedName.substring(index + 1);
	}

	public void finalTrace() {
		FileOutputStream fos;
		OutputStreamWriter osw;
		File outputFile;
		String record;

		try {
			outputFile = new File("../DRAM Tests/groups/diff4Mod.txt");
			//resJunitAERun.txt
			//restestRelation1.txt
			//resjunit1.txt
			fos = new FileOutputStream(outputFile);
			osw = new OutputStreamWriter(fos);

			Iterator iterateur = this.listFile.iterator();
			while (iterateur.hasNext()) {
				EntityList entitylist = (EntityList) iterateur.next();
				if (entitylist.type.equals("constructorEntry0")
						|| entitylist.type.equals("constructorExit0")) {
					//				record = entitylist.type + " " + entitylist.classSource + " " + entitylist.classTarget + " "
					//				+ entitylist.weight + " 1 " + '\n';
					//				record = entitylist.classSource + " " + entitylist.classTarget + " "
					//				+ entitylist.weight + " 1 " + '\n';
					record =
						entitylist.type + "|" + entitylist.classSource + "|"
								+ entitylist.classTarget + "|"
								+ entitylist.weight + "|" + "1" + "|" + '\n';
				}
				else {
					//				record = entitylist.type + " " + entitylist.classSource + " " + entitylist.classTarget + " "
					//				+ entitylist.nameMethod + " " + entitylist.weight + " 1 " + '\n';
					//				record =  entitylist.classSource + " " + entitylist.classTarget + " "
					//				+ entitylist.weight + " 1 " + '\n';
					record =
						entitylist.type + "|" + entitylist.classSource + "|"
								+ entitylist.classTarget + "|"
								+ entitylist.nameMethod + "|"
								+ entitylist.weight + "|" + "1" + "|" + '\n';
				}
				osw.write(record);
			}
			osw.close();
			fos.close();

		}
		catch (final Exception ex) {
			ProxyConsole.getInstance().errorOutput().println("Syntax error line ");
			ex.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void finalTrace1() {
		FileReader rd1;
		BufferedReader buf1;
		String token1;
		String token2;
		String token3;
		String token4;
		String record;
		StringTokenizer st;
		FileOutputStream fos;
		OutputStreamWriter osw;
		File outputFile;

		try {
			rd1 = new FileReader("../DRAM Tests/groups/diff4Mod.txt");
			buf1 = new BufferedReader(rd1);
			outputFile = new File("../DRAM Tests/groups/rr.txt");
			fos = new FileOutputStream(outputFile);
			osw = new OutputStreamWriter(fos);

			while ((this.chaine = buf1.readLine()) != null) {
				st = new StringTokenizer(this.chaine, "|");
				token1 = st.nextToken();
				token2 = st.nextToken();
				token3 = st.nextToken();
				token4 = st.nextToken();
				if (token1.equals("methodEntry0")
						|| token1.equals("methodExit0")) {
					EntityMap entitymap =
						new EntityMap(token2, token3, token4, "1");
					EntityMap eentitymap = lookForEntityMap(entitymap);
					if (eentitymap == null) {
						System.out.println("probleme ");
						record =
							token1 + " " + token2 + " " + token3 + " " + token4
									+ " " + " 99 " + " 1 " + '\n';
						osw.write(record);
					}
					else {
						record =
							token1 + " " + token2 + " " + token3 + " " + token4
									+ " " + eentitymap.weight + " 1 " + '\n';
						osw.write(record);
					}
					//st.nextToken();
					//st.nextToken();
				}
				else {
					if (token1.equals("constructorEntry0")
							|| token1.equals("constructorExit0")) {
						record =
							token1 + " " + token2 + " " + token3 + " " + " 1 "
									+ " 1 " + '\n';
						osw.write(record);
						//st.nextToken();
					}

				}
			}
			osw.close();
			fos.close();

		}
		catch (Exception ex) {
			ProxyConsole.getInstance().errorOutput().println("Syntax error line ");
			ex.printStackTrace(ProxyConsole.getInstance().errorOutput());

		}
	}
}

class EntityMap {
	//implements Comparable
	String classSource;
	String classTarget;
	String nameMethod;
	String weight;

	public EntityMap(
		String classSource,
		String classTarget,
		String nameMethod,
		String weight) {
		this.classSource = classSource;
		this.classTarget = classTarget;
		this.nameMethod = nameMethod;
		this.weight = weight;
	}

	//	public boolean equals(Object o){
	//	    if(!(o instanceof EntityMap))
	//	      return false;
	//	    EntityMap v = (EntityMap)o;
	//	    if(this.hashCode() == v.hashCode())
	//	      return true;
	//	    return false;
	//	  }
	//	
	//	public int compareTo(Object o){
	//	    if(!(o instanceof EntityMap))
	//	      throw new ClassCastException();
	//	    EntityMap v = (EntityMap)o;
	//	    int comparaison;
	//	    if((comparaison = classSource.compareTo(v.classSource)) != 0)
	//	      return comparaison;
	//	    else if((comparaison = classTarget.compareTo(v.classTarget)) != 0)
	//	      return comparaison;
	//	    else return ((nameMethod.compareTo(v.nameMethod)));
	//	  }

}

class EntityList {
	String classSource;
	String classTarget;
	String nameMethod;
	String type;
	String weight;

	public EntityList(
		String type,
		String classSource,
		String classTarget,
		String nameMethod,
		String weight) {
		this.type = type;
		this.classSource = classSource;
		this.classTarget = classTarget;
		this.nameMethod = nameMethod;
		this.weight = weight;

	}
}

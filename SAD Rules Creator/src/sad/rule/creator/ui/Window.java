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
/**
 * @author Administrateur
 */
package sad.rule.creator.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import sad.rule.creator.RULECreator;
import sad.rule.creator.ui.metaModel.Attribute;
import sad.rule.creator.ui.metaModel.ListAttributes;
import sad.rule.creator.ui.metaModel.ListRelationships;
import sad.rule.creator.ui.metaModel.OperatorAttributeAttribute;
import sad.rule.creator.ui.metaModel.OperatorStringString;
import sad.rule.creator.ui.metaModel.Relationship;
import sad.rule.creator.ui.metaModel.Rule;
import sad.rule.creator.ui.metaModel.RuleCard;

@SuppressWarnings("deprecation")
public class Window extends Frame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(final String argv[]) {
		new Window();
	}
	String file = "";

	//////////////////////////////////////////////////////
	// elements' creation
	//////////////////////////////////////////////////////

	String fileContent = "";
	// load_panel's elments
	TextField tLoad = new TextField();
	Button open = new Button("Open");
	Label rules = new Label("Rules list");

	List rulesList = new List();
	// infos_panel's elments
	Label relationship = new Label("Relations");
	Label rType = new Label("Type");
	TextField rTypeT = new TextField();
	Label rName = new Label("Name");
	TextField rNameT = new TextField();
	Label rFrom = new Label("From");
	TextField rFromT = new TextField();
	Label rFCard = new Label("Cardinality");
	TextField rFCardT = new TextField();
	Label rTo = new Label("To");
	TextField rToT = new TextField();
	Label rTCard = new Label("Cardinality");

	TextField rTCardT = new TextField();
	Label attributes = new Label("Attribute");
	Label aType = new Label("Type");
	TextField aTypeT = new TextField();
	Label aName = new Label("Name");
	TextField aNameT = new TextField();
	Label aValue = new Label("Value");

	TextField aValueT = new TextField();
	// text_panel's elments
	Button MAJ = new Button("Update");

	TextArea text = new TextArea(7, 50);
	RULECreator RCreator;

	RuleCard RCard;
	Label label1 = new Label("");
	Label label2 = new Label("");
	Label label3 = new Label("");

	/////////////////////////////////////////////////////////	
	// elements' positionning
	/////////////////////////////////////////////////////////

	Label label4 = new Label("");

	public Window() {

		// modifications of some components' properties
		this.open.setActionCommand("open");
		this.open.addActionListener(this);
		this.MAJ.setActionCommand("maj");
		this.MAJ.addActionListener(this);
		this.text.setEditable(false);

		// Loding File Panel
		final Panel file_panel = new Panel();
		file_panel.setLayout(new GridLayout(2, 2, 2, 2));
		file_panel.add(this.tLoad);
		file_panel.add(this.open);

		// Upper Panel with loading elements and rules 
		final Panel load_panel = new Panel();
		load_panel.setLayout(new GridLayout(2, 3));
		load_panel.add(file_panel);
		load_panel.add(new Label(""));
		load_panel.add(this.rules);
		load_panel.add(this.rulesList);
		load_panel.add(new Label(""));
		load_panel.add(new Label(""));
		load_panel.add(new Label(""));

		// Rules' relationship properties Panel 
		final Panel relations_panel = new Panel();
		relations_panel.setLayout(new GridLayout(4, 4));
		relations_panel.add(this.relationship);
		relations_panel.add(this.label1);
		relations_panel.add(this.label2);
		relations_panel.add(this.label3);
		relations_panel.add(this.rType);
		relations_panel.add(this.rTypeT);
		relations_panel.add(this.rName);
		relations_panel.add(this.rNameT);
		relations_panel.add(this.rFrom);
		relations_panel.add(this.rFromT);
		relations_panel.add(this.rFCard);
		relations_panel.add(this.rFCardT);
		relations_panel.add(this.rTo);
		relations_panel.add(this.rToT);
		relations_panel.add(this.rTCard);
		relations_panel.add(this.rTCardT);

		// Rules' attributes properties Panel   
		final Panel attributs_panel = new Panel();
		attributs_panel.setLayout(new GridLayout(4, 4));
		attributs_panel.add(this.attributes);
		attributs_panel.add(this.label4);
		attributs_panel.add(new Label(""));
		attributs_panel.add(this.aType);
		attributs_panel.add(this.aTypeT);
		attributs_panel.add(new Label(""));
		attributs_panel.add(this.aName);
		attributs_panel.add(this.aNameT);
		attributs_panel.add(new Label(""));
		attributs_panel.add(this.aValue);
		attributs_panel.add(this.aValueT);

		// Central Panel with relationship and attributes
		final Panel infos_panel = new Panel();
		infos_panel.setLayout(new BorderLayout());
		infos_panel.add("West", relations_panel);
		infos_panel.add("East", attributs_panel);

		// Update Panel  
		final Panel maj_panel = new Panel();
		maj_panel.setLayout(new BorderLayout());
		maj_panel.add("West", this.MAJ);

		// Lower Panel with update button and file showing  
		final Panel texte_panel = new Panel();
		texte_panel.setLayout(new BorderLayout());
		texte_panel.add("North", maj_panel);
		texte_panel.add("South", this.text);

		final Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		panel.add("North", load_panel);
		panel.add("Center", infos_panel);
		panel.add("South", texte_panel);
		this.add(panel);
		this.rules.setForeground(Color.RED);
		this.setSize(500, 400);
		this.setResizable(false);
		this.setVisible(true);
		this.relationship.setForeground(Color.BLUE);
		this.attributes.setForeground(Color.BLUE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent evenement) {
				System.exit(0);
			}

		});
	}

	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals("open")) {
			if (this.tLoad.getText().length() == 0) {
				System.out.println("Recherche du fichier");
				final FileDialog dialog = new FileDialog(this, "Browse");
				dialog.setVisible(true);
				final String file = dialog.getFile();
				this.tLoad.setText(file);
			}
			else {
				System.out.println("Ouverture du fichier");
				this.readFile(this.tLoad.getText());
				this.RCreator =
					new RULECreator(new String[] { this.tLoad.getText() });
				this.RCreator.parse();
				//TODO: Naouel this.updateWindow(this.RCreator.parser.ruleCard);

				//final RULECreator ruleCreator = new RULECreator(new String [] {tLoad.getText()});

			}
		}
		if (event.getActionCommand().equals("maj")) {
			System.out.println("Mise a jour du fichier effectué");

			try {
				final LineNumberReader reader =
					new LineNumberReader(new InputStreamReader(
						new FileInputStream(this.tLoad.getText())));

				final StringBuffer buffer = new StringBuffer();
				String readLine;

				final Rule currentRule =
					(Rule) this.RCard
						.getListRules()
						.getRules()
						.elementAt(this.rulesList.getSelectedIndex());
				final OperatorStringString currentOss =
					currentRule.getContentRule().getOperatorStringString();
				if (currentOss != null) {
					final String line =
						"RULE : " + currentRule.getName() + " {"
								+ currentOss.getName() + " "
								+ currentOss.getFirstString() + " "
								+ currentOss.getSecondString() + " } ; ";
					final String name = this.rTypeT.getText();
					final String first = this.rNameT.getText();
					final String second = this.aTypeT.getText();
					currentOss.setName(name);
					currentOss.setFirstString(first);
					currentOss.setSecondString(second);
					final String lineAdd =
						"RULE : " + currentRule.getName() + " {" + name + " "
								+ first + " " + second + " } ; ";
					while ((readLine = reader.readLine()) != null) {
						System.out.println(readLine);
						if (readLine.equals(line) == true) {
							System.out.println(lineAdd);
							buffer.append(lineAdd);
						}
						else {
							buffer.append(readLine);
						}
						buffer.append('\n');
					}
				}

				final ListAttributes listAtt =
					currentRule.getContentRule().getListAttributes();
				if (listAtt != null) {
					final Attribute att = listAtt.getAttribute();
					if (att != null) {
						final String line =
							"RULE : " + currentRule.getName() + " {("
									+ att.getType() + ": " + att.getName()
									+ ", " + att.getValue() + ") } ; ";
						final String type = this.aTypeT.getText();
						final String name = this.aNameT.getText();
						final String value = this.aValueT.getText();
						att.setName(name);
						att.setValue(value);
						att.setType(type);
						final String lineAdd =
							"RULE : " + currentRule.getName() + " {(" + type
									+ ": " + name + ", " + value + ") } ; ";
						while ((readLine = reader.readLine()) != null) {
							System.out.println(readLine);
							if (readLine.equals(line) == true) {
								System.out.println(lineAdd);
								buffer.append(lineAdd);
							}
							else {
								buffer.append(readLine);
							}
							buffer.append('\n');
						}
					}
					final OperatorAttributeAttribute oAA =
						currentRule
							.getContentRule()
							.getListAttributes()
							.getOperatorAttributeAttribute();
					if (oAA != null) {
						final String line =
							"RULE : " + currentRule.getName() + " {"
									+ oAA.getName() + " ("
									+ oAA.getFirstAttribute().getType() + ": "
									+ oAA.getFirstAttribute().getName() + ", "
									+ oAA.getFirstAttribute().getValue()
									+ ") ("
									+ oAA.getSecondAttribute().getType() + ": "
									+ oAA.getSecondAttribute().getName() + ", "
									+ oAA.getSecondAttribute().getValue()
									+ ") } ; ";
						final String fAType = this.rNameT.getText();
						final String fAName = this.rFCardT.getText();
						final String fAValue = this.rTCardT.getText();
						final String sAName = this.aNameT.getText();
						final String sAType = this.aTypeT.getText();
						final String sAValue = this.aValueT.getText();
						final String name = this.rTypeT.getText();
						oAA.setName(name);
						final Attribute fAtt =
							new Attribute(fAType, fAName, fAValue);
						final Attribute sAtt =
							new Attribute(sAType, sAName, sAValue);
						oAA.setFirstAttribute(fAtt);
						oAA.setSecondAttribute(sAtt);
						final String lineAdd =
							"RULE : " + currentRule.getName() + " {" + name
									+ " (" + fAType + ": " + fAName + ", "
									+ fAValue + ") (" + sAType + ": " + sAName
									+ ", " + sAValue + ") } ; ";
						while ((readLine = reader.readLine()) != null) {
							System.out.println(readLine);
							if (readLine.equals(line) == true) {
								System.out.println(lineAdd);
								buffer.append(lineAdd);
							}
							else {
								buffer.append(readLine);
							}
							buffer.append('\n');
						}
					}
				}
				final ListRelationships lrs =
					currentRule.getContentRule().getListRelationships();
				if (lrs != null) {
					final Relationship rs =
						(Relationship) lrs.getRelationships().elementAt(0);
					final String line =
						"RULE : " + currentRule.getName() + " {" + rs.getType()
								+ ": " + rs.getName() + " FROM: "
								+ rs.getFrom() + " " + rs.getFromCardinality()
								+ " TO: " + rs.getTo() + " "
								+ rs.getToCardinality() + " } ; ";
					final String name = this.rNameT.getText();
					final String type = this.rTypeT.getText();
					final String from = this.rFromT.getText();
					final String to = this.rToT.getText();
					final String fromCard = this.rFCardT.getText();
					final String toCard = this.rTCardT.getText();
					rs.setName(name);
					rs.setType(type);
					rs.setFrom(from);
					rs.setTo(to);
					rs.setFromCardinality(fromCard);
					rs.setToCardinality(toCard);
					final String lineAdd =
						"RULE : " + currentRule.getName() + " {" + type + ": "
								+ name + " FROM: " + from + " " + fromCard
								+ " TO: " + to + " " + toCard + " } ; ";
					while ((readLine = reader.readLine()) != null) {
						System.out.println(readLine);
						if (readLine.equals(line) == true) {
							System.out.println(lineAdd);
							buffer.append(lineAdd);
						}
						else {
							buffer.append(readLine);
						}
						buffer.append('\n');
					}
				}
				reader.close();
				this.text.setText(buffer.toString());
				this.fileContent = "";

				try {

					final File outputFile = new File(this.tLoad.getText());
					final FileWriter out = new FileWriter(outputFile);

					out.write(buffer.toString());
					out.close();
				}
				catch (final Exception e) {
					throw new IOException();
				}

			}
			catch (final IOException error) {
				System.out.println("erreur dans: " + error);
			}

		}
	}

	public boolean handleEvent(final Event e) {
		if (e.target instanceof List) {
			switch (e.id) {
				case Event.LIST_SELECT :
					final int sIndex = ((Integer) e.arg).intValue();
					//System.out.println(sIndex);
					this.aTypeT.setText("");
					this.aNameT.setText("");
					this.aValueT.setText("");
					this.rNameT.setText("");
					this.rTypeT.setText("");
					this.rFromT.setText("");
					this.rToT.setText("");
					this.rFCardT.setText("");
					this.rTCardT.setText("");

					this.loadRule(sIndex);

					break;
				case Event.LIST_DESELECT :
					((Integer) e.arg).intValue();

			}
		}
		return super.handleEvent(e);
	}

	public void loadRule(final int aRuleId) {
		final Rule currentRule =
			(Rule) this.RCard.getListRules().getRules().elementAt(aRuleId);
		final OperatorStringString currentOss =
			currentRule.getContentRule().getOperatorStringString();
		if (currentOss != null) {
			this.relationship.setText("Operator");
			this.relationship.setForeground(Color.BLUE);
			this.label1.setText("String");
			this.label2.setForeground(Color.BLUE);
			this.label2.setText("String");
			this.label1.setForeground(Color.BLUE);
			this.attributes.setText("");
			this.rType.setText("Operator");
			this.rName.setText("String 1");
			this.aType.setText("String 2");

			this.rTypeT.setVisible(true);
			this.rNameT.setVisible(true);
			this.aTypeT.setVisible(true);

			this.rFCard.setText("");
			this.rFrom.setText("");
			this.rTo.setText("");
			this.rTCard.setText("");
			this.aName.setText("");
			this.aValue.setText("");

			this.rToT.setVisible(false);
			this.rTCardT.setVisible(false);
			this.rFromT.setVisible(false);
			this.aNameT.setVisible(false);
			this.aValueT.setVisible(false);
			this.rFCardT.setVisible(false);

			this.rTypeT.setText(currentOss.getName());
			this.rNameT.setText(currentOss.getFirstString());
			this.aTypeT.setText(currentOss.getSecondString());

		}
		final ListAttributes listAtt =
			currentRule.getContentRule().getListAttributes();
		if (listAtt != null) {
			final Attribute att = listAtt.getAttribute();
			if (att != null) {
				this.label1.setText("");
				this.label2.setText("");
				this.attributes.setText("Attribute");
				this.attributes.setForeground(Color.BLUE);
				this.relationship.setText("");

				this.rToT.setVisible(false);
				this.rTCardT.setVisible(false);
				this.rFromT.setVisible(false);
				this.rTypeT.setVisible(false);
				this.rNameT.setVisible(false);
				this.rFCardT.setVisible(false);

				this.rType.setText("");
				this.rName.setText("");
				this.rFCard.setText("");
				this.rFrom.setText("");
				this.rTo.setText("");
				this.rTCard.setText("");

				this.aName.setText("Nom");
				this.aValue.setText("Valeur");
				this.aType.setText("Type");

				this.aNameT.setVisible(true);
				this.aValueT.setVisible(true);

				this.aTypeT.setText(att.getType());
				this.aNameT.setText(att.getName());
				this.aValueT.setText(att.getValue());
			}
			final OperatorAttributeAttribute oAA =
				currentRule
					.getContentRule()
					.getListAttributes()
					.getOperatorAttributeAttribute();
			if (oAA != null) {
				this.relationship.setText("Operator");
				this.attributes.setText("Attribute2");
				this.attributes.setForeground(Color.BLUE);
				this.label1.setText("");
				this.label2.setText("Attribute1");
				this.label2.setForeground(Color.BLUE);
				this.rType.setText("Operator");
				this.rFrom.setText("");
				this.rTo.setText("");
				this.rToT.setVisible(false);
				this.rFromT.setVisible(false);
				this.rName.setText("Type");
				this.rFCard.setText("Name");
				this.rTCard.setText("Value");
				this.aName.setText("Name");
				this.aValue.setText("Value");
				this.aType.setText("Type");
				this.rTypeT.setVisible(true);
				this.rTCardT.setVisible(true);
				this.rNameT.setVisible(true);
				this.rFCardT.setVisible(true);
				this.aTypeT.setVisible(true);
				this.aNameT.setVisible(true);
				this.aValueT.setVisible(true);

				this.rNameT.setText(oAA.getFirstAttribute().getType());
				this.rFCardT.setText(oAA.getFirstAttribute().getName());
				this.rTCardT.setText(oAA.getFirstAttribute().getValue());
				this.aNameT.setText(oAA.getSecondAttribute().getName());
				this.aTypeT.setText(oAA.getSecondAttribute().getType());
				this.aValueT.setText(oAA.getSecondAttribute().getValue());
				this.rTypeT.setText(oAA.getName());
			}
		}
		final ListRelationships lrs =
			currentRule.getContentRule().getListRelationships();
		if (lrs != null) {
			//for(int j=0; j < lrs.getRelationships().size(); j++)
			//{}
			final Relationship rs =
				(Relationship) lrs.getRelationships().elementAt(0);

			this.relationship.setText("Relation");
			this.relationship.setForeground(Color.BLUE);
			this.attributes.setText("");

			this.label1.setText("");
			this.label2.setText("");

			this.rToT.setVisible(true);
			this.rTCardT.setVisible(true);
			this.rFromT.setVisible(true);
			this.rTypeT.setVisible(true);
			this.rNameT.setVisible(true);
			this.rFCardT.setVisible(true);

			this.rType.setText("Type");
			this.rName.setText("Name");
			this.rFCard.setText("Cardinality");
			this.rFrom.setText("From");
			this.rTo.setText("To");
			this.rTCard.setText("Cardinality");

			this.aTypeT.setVisible(false);
			this.aNameT.setVisible(false);
			this.aValueT.setVisible(false);

			this.attributes.setText("");
			this.aType.setText("");
			this.aValue.setText("");
			this.aName.setText("");

			this.rNameT.setText(rs.getName());
			this.rTypeT.setText(rs.getType());
			this.rFromT.setText(rs.getFrom());
			this.rToT.setText(rs.getTo());
			this.rFCardT.setText(rs.getFromCardinality());
			this.rTCardT.setText(rs.getToCardinality());
		}

	}

	public void readFile(final String file) {
		try {
			final LineNumberReader reader =
				new LineNumberReader(new InputStreamReader(new FileInputStream(
					file)));

			final StringBuffer buffer = new StringBuffer();
			String readLine;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
			}
			reader.close();
			this.text.setText(buffer.toString());
			this.fileContent = "";
		}
		catch (final IOException e) {
			System.out.println("erreur dans: " + e);
		}
	}

	//////////////////////////////////////////////////////
	// Window creation
	//////////////////////////////////////////////////////     

	public void updateWindow(final RuleCard aRuleCard) {
		this.RCard = aRuleCard;
		//System.out.println("RuleCard Name: "+RCard.getName());
		/*for(int i=0; i< RCard.getListRules().getRules().size(); i++)
		{
			Rule currentRule = (Rule)(RCard.getListRules().getRules().elementAt(i));
			this.rulesList.addItem(currentRule.getName());
		}*/

		int i = 0;
		while (i < this.RCard.getListRules().getRules().size()) {
			final Rule currentRule =
				(Rule) this.RCard.getListRules().getRules().elementAt(i);
			this.rulesList.add(currentRule.getName());
			i++;
		}

	}

}

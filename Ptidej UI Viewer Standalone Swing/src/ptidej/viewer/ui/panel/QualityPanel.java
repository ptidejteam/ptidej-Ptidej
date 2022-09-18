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
package ptidej.viewer.ui.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JPanel;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import ptidej.viewer.widget.EmbeddedPanel;
import squad.quality.INominalQualityAttribute;
import squad.quality.INumericQualityAttribute;
import squad.quality.IQualityAttribute;
import squad.quality.pqmod.PQMODRepository;
import squad.quality.qmood.QMOODRepository;
import util.help.IHelpURL;
import util.io.ProxyConsole;

public class QualityPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public QualityPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		final EmbeddedPanel contentPQMOD = new EmbeddedPanel();
		final PQMODRepository pqmodRepository = PQMODRepository.getInstance();
		final IQualityAttribute[] pqmodAttributes =
			pqmodRepository.getQualityAttributes();
		for (int i = 0; i < pqmodAttributes.length; i++) {
			final IQualityAttribute pqmodAttribute = pqmodAttributes[i];
			contentPQMOD.addButton(
				pqmodAttribute.getName(),
				new ActionListener() {
					public void actionPerformed(final ActionEvent anActionEvent) {
						final IAbstractModel abstractModel =
							(IAbstractModel) DesktopPane
								.getInstance()
								.getAbstractRepresentationWindow()
								.getSourceModel();
						ProxyConsole
							.getInstance()
							.normalOutput()
							.print("Entities");
						ProxyConsole.getInstance().normalOutput().print("\t\t");
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(pqmodAttribute.getName());
						final Iterator iterator =
							abstractModel.getIteratorOnTopLevelEntities();
						while (iterator.hasNext()) {
							final IFirstClassEntity entity =
								(IFirstClassEntity) iterator.next();
							ProxyConsole
								.getInstance()
								.normalOutput()
								.print(entity.getDisplayName());
							ProxyConsole
								.getInstance()
								.normalOutput()
								.print("\t\t");
							if (pqmodAttribute instanceof INumericQualityAttribute) {
								ProxyConsole
									.getInstance()
									.normalOutput()
									.println(
										((INumericQualityAttribute) pqmodAttribute)
											.computeNumericValue(
												abstractModel,
												entity));
							}
							else if (pqmodAttribute instanceof INominalQualityAttribute) {
								ProxyConsole
									.getInstance()
									.normalOutput()
									.println(
										((INominalQualityAttribute) pqmodAttribute)
											.computeNominalValue(
												abstractModel,
												entity));
							}
							else {
								ProxyConsole
									.getInstance()
									.normalOutput()
									.println(this.getClass().getName());
								ProxyConsole
									.getInstance()
									.normalOutput()
									.print(
										" does not know what to do with attribute ");
								ProxyConsole
									.getInstance()
									.normalOutput()
									.print(pqmodAttribute.getName());
							}
						}
					}
				},
				false,
				false,
				Controls.getInstance().areVisitorsListening());
		}
		panel.addCollapsablePanel("PQMOD", contentPQMOD);

		final EmbeddedPanel contentQMOOD = new EmbeddedPanel();
		final QMOODRepository qmoodRepository = QMOODRepository.getInstance();
		final IQualityAttribute[] qmoodAttributes =
			qmoodRepository.getQualityAttributes();
		for (int i = 0; i < qmoodAttributes.length; i++) {
			final IQualityAttribute qmoodAttribute = qmoodAttributes[i];
			contentQMOOD.addButton(
				qmoodAttribute.getName(),
				new ActionListener() {
					public void actionPerformed(final ActionEvent anActionEvent) {
						final IAbstractModel abstractModel =
							(IAbstractModel) DesktopPane
								.getInstance()
								.getAbstractRepresentationWindow()
								.getSourceModel();

						boolean justDoIt;
						if (abstractModel.getNumberOfConstituents() > Resources.NUMBER_OF_ENTITIES_FOR_WARNING) {
							justDoIt = Utils.warnAboutLongOperation();
						}
						else {
							justDoIt = true;
						}

						if (justDoIt) {
							ProxyConsole
								.getInstance()
								.normalOutput()
								.print("Entities");
							ProxyConsole
								.getInstance()
								.normalOutput()
								.print("\t\t");
							ProxyConsole
								.getInstance()
								.normalOutput()
								.println(qmoodAttribute.getName());
							final Iterator iterator =
								abstractModel.getIteratorOnTopLevelEntities();
							while (iterator.hasNext()) {
								final IFirstClassEntity entity =
									(IFirstClassEntity) iterator.next();
								ProxyConsole
									.getInstance()
									.normalOutput()
									.print(entity.getDisplayName());
								ProxyConsole
									.getInstance()
									.normalOutput()
									.print("\t\t");
								if (qmoodAttribute instanceof INumericQualityAttribute) {
									ProxyConsole
										.getInstance()
										.normalOutput()
										.println(
											((INumericQualityAttribute) qmoodAttribute)
												.computeNumericValue(
													abstractModel,
													entity));
								}
								else if (qmoodAttribute instanceof INominalQualityAttribute) {
									ProxyConsole
										.getInstance()
										.normalOutput()
										.println(
											((INominalQualityAttribute) qmoodAttribute)
												.computeNominalValue(
													abstractModel,
													entity));
								}
								else {
								}
							}
						}
					}
				},
				false,
				false,
				Controls.getInstance().areVisitorsListening(),
				(IHelpURL) qmoodAttribute);
		}
		panel.addCollapsablePanel("QMOOD", contentQMOOD);
	}
}

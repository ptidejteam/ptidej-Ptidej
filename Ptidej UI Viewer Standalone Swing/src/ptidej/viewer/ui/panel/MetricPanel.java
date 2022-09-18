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
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IBinaryMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.EmbeddedPanel;
import util.io.ProxyConsole;

public class MetricPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public MetricPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		final EmbeddedPanel content = new EmbeddedPanel();
		final MetricsRepository metricsRepository =
			MetricsRepository.getInstance();
		final IMetric[] metrics = metricsRepository.getMetrics();
		for (int i = 0; i < metrics.length; i++) {
			final IMetric metric = metrics[i];
			content.addButton(metric.getName(), new ActionListener() {
				public void actionPerformed(final ActionEvent anActionEvent) {
					final IAbstractLevelModel model =
						(IAbstractLevelModel) DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel();
					if (metric instanceof IUnaryMetric) {
						ProxyConsole
							.getInstance()
							.normalOutput()
							.print("Entities");
						ProxyConsole.getInstance().normalOutput().print("\t\t");
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(metric.getName());
						final Iterator iterator =
							model.getIteratorOnTopLevelEntities();
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
							ProxyConsole
								.getInstance()
								.normalOutput()
								.println(
									((IUnaryMetric) metric).compute(
										model,
										entity));
						}
					}
					else if (metric instanceof IBinaryMetric) {
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(this.getClass().getName());
						ProxyConsole
							.getInstance()
							.normalOutput()
							.print(
								" reports that it would take too long to compute metric ");
						ProxyConsole
							.getInstance()
							.normalOutput()
							.print(metric.getName());
					}
					else {
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(this.getClass().getName());
						ProxyConsole
							.getInstance()
							.normalOutput()
							.print(" does not know what to do with metric ");
						ProxyConsole
							.getInstance()
							.normalOutput()
							.print(metric.getName());
					}
				}
			},
				false,
				false,
				Controls.getInstance().areVisitorsListening());
		}
		panel.addCollapsablePanel("Metrics", content);
	}
}

/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.panel.color;

import infovis.Visualization;
import infovis.visualization.ColorVisualization;
import infovis.visualization.color.OrderedColor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


/**
 * Color Control Panel for Ordered Colors
 * 
 * <p>
 * See http://colorbrewer.org
 * </p>
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class OrderedColorControlPanel extends ColorVisualizationControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton          startButton;
    JButton          endButton;
    JComboBox        colorSchemes;
    static Color[][] schemes = {
                                   {
                                       new Color(255, 255, 255),
                                       new Color(0, 0, 0)
                                   },
                                   { Color.WHITE, new Color(4, 90, 141) },
                                   {
                                       new Color(255, 255, 204),
                                       new Color(37, 52, 148)
                                   },
                                   {
                                       new Color(240, 249, 232),
                                       new Color(8, 104, 172)
                                   },
                                   {
                                       new Color(255, 255, 204),
                                       new Color(0, 104, 55)
                                   },
                                   {
                                       new Color(237, 248, 251),
                                       new Color(0, 109, 44)
                                   },
                                   {
                                       new Color(254, 240, 217),
                                       new Color(179, 0, 0)
                                   },
                                   {
                                       new Color(246, 239, 247),
                                       new Color(1, 108, 89)
                                   },
                                   {
                                       new Color(237, 248, 251),
                                       new Color(129, 15, 124)
                                   },
                                   {
                                       new Color(254, 235, 226),
                                       new Color(122, 1, 119)
                                   },
                                   {
                                       new Color(241, 238, 246),
                                       new Color(152, 0, 67)
                                   },
                                   {
                                       new Color(255, 255, 178),
                                       new Color(189, 0, 38)
                                   },
                                   {
                                       new Color(242, 240, 247),
                                       new Color(84, 39, 143)
                                   },
                                   {
                                       new Color(237, 248, 233),
                                       new Color(0, 109, 44)
                                   },
                                   {
                                       new Color(254, 237, 222),
                                       new Color(166, 54, 3)
                                   },
                                   {
                                       new Color(254, 229, 217),
                                       new Color(165, 15, 21)
                                   },
                               };

    static class ColorListCellRenderer extends JComponent
        implements ListCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Color[] current;

        public ColorListCellRenderer() {
            setPreferredSize(new Dimension(50, 20));
        }

        /**
         * @see javax.swing.JComponent#paintComponent(Graphics)
         */
        protected void paintComponent(Graphics g) {
            if (this.current != null) {
                Dimension dim = getSize();

                g.setColor(this.current[0]);
                g.fillRect(0, 0, dim.width / 2, dim.height);
                g.setColor(this.current[1]);
                g.fillRect(dim.width / 2, 0, dim.width / 2, dim.height);
            }
        }

        /**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(JList,
         *      Object, int, boolean, boolean)
         */
        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            this.current = (Color[])value;
            return this;
        }
    }
    ;
    /**
     * Creates a new OrderedColorControlPanel object.
     *
     * @param visualization the Visualization
     */
    public OrderedColorControlPanel(Visualization visualization, String visualColor) {
        super(visualization, visualColor);
        this.startButton = new JButton("Minimum");
        this.startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color c = chooseColor(OrderedColorControlPanel.this.startButton.getBackground());
                    if (!c.equals(OrderedColorControlPanel.this.startButton.getBackground())) {
                        getOrderedColor().setStart(c);
                        update();
                        getVisualization().repaint();
                    }
                }
            });
        add(this.startButton);

        this.endButton = new JButton("Maximum");
        this.endButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color c = chooseColor(OrderedColorControlPanel.this.startButton.getBackground());
                    if (!c.equals(OrderedColorControlPanel.this.endButton.getBackground())) {
                        getOrderedColor().setEnd(c);
                        update();
                        getVisualization().repaint();
                    }
                }
            });
        add(this.endButton);

        this.colorSchemes = new JComboBox(schemes);
        this.colorSchemes.setRenderer(new ColorListCellRenderer());
        this.colorSchemes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color[] scheme = schemes[OrderedColorControlPanel.this.colorSchemes.getSelectedIndex()];
                    getOrderedColor().setStart(scheme[0]);
                    getOrderedColor().setEnd(scheme[1]);
                    update();
                    getVisualization().repaint();
                }
            });
        add(this.colorSchemes);

        update();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public OrderedColor getOrderedColor() {
        return (OrderedColor)getColorVisualization();
    }

    /**
     * DOCUMENT ME!
     */
    public void update() {
        OrderedColor orderedColor = getOrderedColor();
        this.startButton.setBackground(orderedColor.getStart());
        this.startButton.setForeground(ColorVisualization.colorComplement(this.startButton.getBackground()));
        this.endButton.setBackground(orderedColor.getEnd());
        this.endButton.setForeground(ColorVisualization.colorComplement(this.endButton.getBackground()));
    }
}

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
package modec.test.builder.example;

public class UIDirector {
  private UIBuilder builder;

  public UIDirector(UIBuilder bldr) {
    this.builder = bldr;
  }
  public void build() {
    this.builder.addUIControls();
    this.builder.initialize();
  }

}


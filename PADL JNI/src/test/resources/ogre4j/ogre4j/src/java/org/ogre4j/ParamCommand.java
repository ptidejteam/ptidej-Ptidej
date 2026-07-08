package org.ogre4j;

abstract public class ParamCommand {
    abstract public String doGet(Object target);

    abstract public void doSet(Object target, String val);
}

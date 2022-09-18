package gr.uom.java.pattern;

public enum PatternEnum {
    FACTORY_METHOD, PROTOTYPE, SINGLETON, ADAPTER_COMMAND, COMPOSITE, DECORATOR,
    OBSERVER, STATE_STRATEGY, TEMPLATE_METHOD, VISITOR, PROXY, PROXY2/*, REDIRECT_IN_FAMILY*/;

    public String toString() {
        switch(this) {
            case DECORATOR: return "Decorator";
            case COMPOSITE: return "Composite";
            case STATE_STRATEGY: return "State-Strategy";
            case OBSERVER: return "Observer";
            case VISITOR: return "Visitor";
            case PROTOTYPE: return "Prototype";
            case ADAPTER_COMMAND: return "(Object)Adapter-Command";
            case SINGLETON: return "Singleton";
            case TEMPLATE_METHOD: return "Template Method";
            case FACTORY_METHOD: return "Factory Method";
            case PROXY: return "Proxy";
            case PROXY2: return "Proxy2";
            //case REDIRECT_IN_FAMILY: return "RedirectInFamily";
            default: return "";
        }
    }
}

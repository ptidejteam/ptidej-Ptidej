package javainuse;
import java.util.HashMap;
import java.util.Map;
public class Roles {
    private AbstractProducts abstractProducts;
    private Clients clients;
    private Products products;
    private Builders builders;
    private Directors directors;
    private ConcreteBuilders concreteBuilders;
    private String receivers;
    private Invokers invokers;
    private ConcreteCommands concreteCommands;
    private String commands;
    private Composites composites;
    private Components components;
    private Leaves leaves;
    private ConcreteObservers concreteObservers;
    private Subjects subjects;
    private ConcreteSubjects concreteSubjects;
    private Observers observers;
    private Singletons singletons;
    public AbstractProducts getAbstractProducts() {
        return abstractProducts;
    }
    public void setAbstractProducts(AbstractProducts abstractProducts) {
        this.abstractProducts = abstractProducts;
    }
    public Clients getClients() {
        return clients;
    }
    public void setClients(Clients clients) {
        this.clients = clients;
    }
    public Products getProducts() {
        return products;
    }
    public void setProducts(Products products) {
        this.products = products;
    }
    public Builders getBuilders() {
        return builders;
    }
    public void setBuilders(Builders builders) {
        this.builders = builders;
    }
    public Directors getDirectors() {
        return directors;
    }
    public void setDirectors(Directors directors) {
        this.directors = directors;
    }
    public ConcreteBuilders getConcreteBuilders() {
        return concreteBuilders;
    }
    public void setConcreteBuilders(ConcreteBuilders concreteBuilders) {
        this.concreteBuilders = concreteBuilders;
    }
    public String getReceivers() {
        return receivers;
    }
    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }
    public Invokers getInvokers() {
        return invokers;
    }
    public void setInvokers(Invokers invokers) {
        this.invokers = invokers;
    }
    public ConcreteCommands getConcreteCommands() {
        return concreteCommands;
    }
    public void setConcreteCommands(ConcreteCommands concreteCommands) {
        this.concreteCommands = concreteCommands;
    }
    public String getCommands() {
        return commands;
    }
    public void setCommands(String commands) {
        this.commands = commands;
    }
    public Composites getComposites() {
        return composites;
    }
    public void setComposites(Composites composites) {
        this.composites = composites;
    }
    public Components getComponents() {
        return components;
    }
    public void setComponents(Components components) {
        this.components = components;
    }
    public Leaves getLeaves() {
        return leaves;
    }
    public void setLeaves(Leaves leaves) {
        this.leaves = leaves;
    }
    public ConcreteObservers getConcreteObservers() {
        return concreteObservers;
    }
    public void setConcreteObservers(ConcreteObservers concreteObservers) {
        this.concreteObservers = concreteObservers;
    }
    public Subjects getSubjects() {
        return subjects;
    }
    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }
    public ConcreteSubjects getConcreteSubjects() {
        return concreteSubjects;
    }
    public void setConcreteSubjects(ConcreteSubjects concreteSubjects) {
        this.concreteSubjects = concreteSubjects;
    }
    public Observers getObservers() {
        return observers;
    }
    public void setObservers(Observers observers) {
        this.observers = observers;
    }
    public Singletons getSingletons() {
        return singletons;
    }
    public void setSingletons(Singletons singletons) {
        this.singletons = singletons;
    }
}
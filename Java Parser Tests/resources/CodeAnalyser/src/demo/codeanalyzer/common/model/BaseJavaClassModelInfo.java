package demo.codeanalyzer.common.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Stores common attributes of a java class
 * @author Deepa Sobhana, Seema Richard
 */
public class BaseJavaClassModelInfo implements BaseJavaClassModel {

    private int moduleType;
    
    private String name = null;
    
    private Collection<Annotation> annontations = new ArrayList<Annotation>();
    
    private Location locationInfo;
    
    private boolean publicFlag;
    
    private boolean privateFlag;
    
    private boolean protectedFlag;
    
    private boolean finalFlag;
    
    private boolean abstractFlag;
    
    private boolean nativeFlag;
    
    private boolean staticFlag;

    public void setAbstractFlag(boolean abstractFlag) {
        this.abstractFlag = abstractFlag;
    }
    
    public boolean isAbstract() {
        return abstractFlag;
    }

    public void setFinalFlag(boolean finalFlag) {
        this.finalFlag = finalFlag;
    }
    
    public boolean isFinal() {
         return finalFlag;
    }

    public void setNativeFlag(boolean nativeFlag) {
        this.nativeFlag = nativeFlag;
    }
    
    public boolean isNative() {
         return nativeFlag;
    }

    public void setPrivateFlag(boolean privateFlag) {
        this.privateFlag = privateFlag;
    }
    
    public boolean isPrivate() {
        return protectedFlag;
    }

    public void setProtectedFlag(boolean protectedFlag) {
        this.protectedFlag = protectedFlag;
    }
    
    public boolean isProtected() {
        return protectedFlag;
    }

    public void setPublicFlag(boolean publicFlag) {
        this.publicFlag = publicFlag;
    }
    
    public boolean isPublic() {
        return publicFlag;
    }

    public void setStaticFlag(boolean staticFlag) {
        this.staticFlag = staticFlag;
    }
    
    public boolean isStatic() {
        return staticFlag;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Collection<Annotation> getAnnotations() {
        return annontations;
    }
    
    public void addAnnotation(Annotation anno){
        annontations.add(anno);
    }

    public int getModuleType() {
        return moduleType;
    }
    
    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public Location getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(Location locationInfo) {
        this.locationInfo = locationInfo;
    }

}

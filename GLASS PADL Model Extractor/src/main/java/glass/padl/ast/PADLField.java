package glass.padl.ast;

import glass.ast.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IUseRelationship;

public class PADLField implements IField{
	
	private IUseRelationship padlField;
	private String targetEntityName;
	private String location;
	
	public PADLField(IUseRelationship padlField, String fieldLocation) {
		this.padlField = padlField;
		this.location = fieldLocation;
		
		IFirstClassEntity entity = this.padlField.getTargetEntity();
		String[] splitPackages = entity.getDisplayPath().split("\\|");
		targetEntityName = splitPackages[splitPackages.length - 1];
	}

	@Override
	public boolean hasUnresolvedSignature() {
		return false; // PADL should have already resolved the signature
	}

	@Override
	public String getTypeSignature() {
		return targetEntityName;
	}

	@Override
	public String getElementName() {
		return this.padlField.getDisplayName();
	}

	@Override
	public boolean isClassType() {
		return true; // default for now
	}
	
	@Override
	public String getLocation() {
		return this.location;
	}

}

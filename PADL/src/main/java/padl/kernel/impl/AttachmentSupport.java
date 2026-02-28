package padl.kernel.impl;

import padl.kernel.IElement;
import padl.kernel.exception.ModelDeclarationException;
import util.multilingual.MultilingualManager;

/**
 * Helper class to avoid code duplication of attachment logic between Element
 * and MemberClass, which cannot share a common parent implementing IElement.
 * 
 * @author Sikandar Ejaz
 * @since 2026/02/27
 */

public final class AttachmentSupport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private IElement attachedElement;

	public void attachTo(final IElement self, final IElement anElement) {
		if (anElement != null) {
			if (anElement == self) {
				throw new ModelDeclarationException(MultilingualManager.getString("ELEM_ATTACH", IElement.class));
			}
			if (!anElement.getClass().isInstance(self)) {
				throw new ModelDeclarationException(
						MultilingualManager.getString("ATTACH", IElement.class, new Object[] { anElement.getClass() }));
			}
			this.detach();
			this.attachedElement = anElement;
		}
	}

	public void detach() {
		if (this.attachedElement == null) {
			return;
		}
		this.attachedElement = null;
	}

	public IElement getAttachedElement() {
		return this.attachedElement;
	}

	public void setAttachedElement(final IElement anElement) {
		this.attachedElement = anElement;
	}
}

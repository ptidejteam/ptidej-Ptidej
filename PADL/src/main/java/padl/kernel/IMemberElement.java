package padl.kernel;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2026/06/24
 */
public interface IMemberElement extends IElement {
	void attachTo(final IElement anElement);

	void detach();

	IElement getAttachedElement();
}

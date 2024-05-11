/**
 * Returns the set of all super classes or interfaces
 */
public Set<IJCTNonPrimitiveType> getAllSuperClasses()
{
    final Set<IJCTNonPrimitiveType> not_ok_yet = new HashSet<IJCTNonPrimitiveType>();
    final Set<IJCTNonPrimitiveType> result = new HashSet<IJCTNonPrimitiveType>();
    result.add(this);

    not_ok_yet.addAll(this.getDirectSuperClasses());

    while(!not_ok_yet.isEmpty())
    {
        final IJCTNonPrimitiveType superClass = not_ok_yet.iterator().next();
        if(!result.contains(superClass))
        {
            result.add(superClass);
            not_ok_yet.addAll(superClass.getAllSuperClasses());
        }
        not_ok_yet.remove(superClass);
    }

    return Collections.unmodifiableSet(result);
}

private static final long serialVersionUID = 4213112898470303666L;

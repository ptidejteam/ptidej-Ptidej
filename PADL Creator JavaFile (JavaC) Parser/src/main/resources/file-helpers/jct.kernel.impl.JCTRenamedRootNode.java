private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
{
    in.defaultReadObject();
    this.factory = new SoftReference<IJCTFactory>(null);
}

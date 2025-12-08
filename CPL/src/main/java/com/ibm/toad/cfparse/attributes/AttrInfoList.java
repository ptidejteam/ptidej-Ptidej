package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;

//HENRIQUE 4/11/2025
public final class AttrInfoList {
	private ConstantPool d_cp;
	private int d_numAttrs;
	private AttrInfo[] d_attrs;
	private int d_depth;

	public AttrInfoList(ConstantPool cp, int depth) {
		d_cp = cp;
		d_numAttrs = 0;
		d_attrs = null;
		d_depth = depth;
	}

	//	Reorder by priority Henrique 4/22/2025
	private void reorderByPriority(String[] priority) {
		if (d_attrs == null || d_numAttrs == 0)
			return;

		final AttrInfo[] reordered = new AttrInfo[d_attrs.length];
		int insertIndex = 0;

		for (final String target : priority) {
			for (int i = 0; i < d_numAttrs; i++) {
				if (this.d_attrs[i] != null
						&& this.d_attrs[i].getName().equals(target)) {
					reordered[insertIndex++] = this.d_attrs[i];
					this.d_attrs[i] = null;
				}
			}
		}

		for (int i = 0; i < d_numAttrs; i++) {
			if (this.d_attrs[i] != null) {
				reordered[insertIndex++] = this.d_attrs[i];
			}
		}

		this.d_attrs = reordered;
	}

	public AttrInfo add(String name) {
		if (d_attrs == null || d_numAttrs == d_attrs.length) {
			this.resize();
		}

		int idxName = d_cp.find(ConstantPool.CONSTANT_Utf8, name);
		if (idxName == -1) {
			idxName = d_cp.addUtf8(name);
		}

		final AttrInfo attr = switch (name) {
		case "SourceFile" -> new SourceFileAttrInfo(d_cp, idxName, d_depth);
		case "SourceDir" -> new SourceDirAttrInfo(d_cp, idxName, d_depth);
		case "ConstantValue" ->
			new ConstantValueAttrInfo(d_cp, idxName, d_depth);
		case "Code" -> new CodeAttrInfo(d_cp, idxName, d_depth);
		case "LineNumberTable" ->
			new LineNumberAttrInfo(d_cp, idxName, d_depth);
		case "LocalVariableTable" ->
			new LocalVariableAttrInfo(d_cp, idxName, d_depth);
		case "Exceptions" -> new ExceptionsAttrInfo(d_cp, idxName, d_depth);
		case "Signature" -> new SignatureAttrInfo(d_cp, idxName, d_depth);
		case "LocalVariableTypeTable" ->
			new LocalVariableTypeAttrInfo(d_cp, idxName, d_depth);
		case "RuntimeInvisibleAnnotations" ->
			new RuntimeInvisibleAnnotationsAttrInfo(d_cp, idxName, d_depth);
		case "StackMapTable" ->
			new StackMapTableAttrInfo(d_cp, idxName, d_depth);
		case "InnerClasses" -> new InnerClassesAttrInfo(d_cp, idxName, d_depth);
		case "NestMembers" -> new NestMembersAttrInfo(d_cp, idxName, d_depth);
		case "Synthetic" -> new SyntheticAttrInfo(d_cp, idxName, d_depth);
		case "Deprecated" -> new DeprecatedAttrInfo(d_cp, idxName, d_depth);
		case "BootstrapMethods" ->
			new BootstrapMethodsAttrInfo(d_cp, idxName, d_depth);
		default -> new UnknownAttrInfo(d_cp, idxName, d_depth);
		};
		
		d_attrs[d_numAttrs++] = attr;
		return attr;
	}

	public AttrInfo get(int index) {
		if (index < 0 || index >= d_numAttrs)
			return null;
		return d_attrs[index];
	}

	public AttrInfo get(String name) {
		for (int i = 0; i < d_numAttrs; i++) {
			if (d_attrs[i].getName().equals(name))
				return d_attrs[i];
		}
		return null;
	}

	public void remove(String name) {
		for (int i = 0; i < d_numAttrs; i++) {
			if (d_attrs[i].getName().equals(name))
				remove(i);
		}
	}

	public void remove(int index) {
		if (index < 0 || index >= d_numAttrs)
			return;
		for (int i = index; i < d_numAttrs - 1; i++) {
			d_attrs[i] = d_attrs[i + 1];
		}
		d_numAttrs--;
	}

	public void read(DataInputStream in) throws IOException {
		d_numAttrs = in.readUnsignedShort();
		d_attrs = new AttrInfo[d_numAttrs];
		for (int i = 0; i < d_numAttrs; i++) {
			final int idxName = in.readUnsignedShort();
			final String name = d_cp.getAsString(idxName);

			final AttrInfo attr = switch (name) {
			case "SourceFile" -> new SourceFileAttrInfo(d_cp, idxName, d_depth);
			case "SourceDir" -> new SourceDirAttrInfo(d_cp, idxName, d_depth);
			case "ConstantValue" ->
				new ConstantValueAttrInfo(d_cp, idxName, d_depth);
			case "Code" -> new CodeAttrInfo(d_cp, idxName, d_depth);

			case "LineNumberTable" ->
				new LineNumberAttrInfo(d_cp, idxName, d_depth);
			case "LocalVariableTable" ->
				new LocalVariableAttrInfo(d_cp, idxName, d_depth);
			case "Signature" -> new SignatureAttrInfo(d_cp, idxName, d_depth);
			case "LocalVariableTypeTable" ->
				new LocalVariableTypeAttrInfo(d_cp, idxName, d_depth);
			case "Exceptions" -> new ExceptionsAttrInfo(d_cp, idxName, d_depth);
			case "RuntimeInvisibleAnnotations" ->
				new RuntimeInvisibleAnnotationsAttrInfo(d_cp, idxName, d_depth);
			case "StackMapTable" ->
				new StackMapTableAttrInfo(d_cp, idxName, d_depth);
			case "InnerClasses" ->
				new InnerClassesAttrInfo(d_cp, idxName, d_depth);
			case "NestMembers" ->
				new NestMembersAttrInfo(d_cp, idxName, d_depth);
			case "Synthetic" -> new SyntheticAttrInfo(d_cp, idxName, d_depth);
			case "Deprecated" -> new DeprecatedAttrInfo(d_cp, idxName, d_depth);
			case "BootstrapMethods" ->
				new BootstrapMethodsAttrInfo(d_cp, idxName, d_depth);
			default -> new UnknownAttrInfo(d_cp, idxName, d_depth);
			};

			d_attrs[i] = attr;
			attr.read(in);
		}
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeShort(d_numAttrs);
		for (int i = 0; i < d_numAttrs; i++)
			d_attrs[i].write(out);
	}

	public void sort(int[] mapping) {
		for (int i = 0; i < d_numAttrs; i++)
			d_attrs[i].sort(mapping);
	}

	public BitSet uses() {
		BitSet bits = new BitSet(d_cp.length());
		for (int i = 0; i < d_numAttrs; i++)
			bits.or(d_attrs[i].uses());
		return bits;
	}

	public int size() {
		int size = 2; // short count
		for (int i = 0; i < d_numAttrs; i++)
			size += d_attrs[i].size();
		return size;
	}

	public String getName(int index) {
		if (index < 0 || index >= d_numAttrs)
			return null;
		return d_attrs[index].getName();
	}

	public int length() {
		return d_numAttrs;
	}

	int depth() {
		return d_depth;
	}

	private void resize() {
		AttrInfo[] newAttrs = new AttrInfo[d_numAttrs + 10];
		if (d_attrs != null)
			System.arraycopy(d_attrs, 0, newAttrs, 0, d_numAttrs);
		d_attrs = newAttrs;
	}

	@Override
	public String toString() {
		if (this.d_numAttrs > 1) {
			this.d_attrs = Arrays.copyOfRange(this.d_attrs, 0, this.d_numAttrs);
			Arrays.sort(this.d_attrs);
		}

		final StringBuilder sb = new StringBuilder();
		if (d_numAttrs > 0) {
			sb.append("ATTRIBUTES:\n");
			for (int i = 0; i < d_numAttrs; i++) {
				AttrInfo attr = d_attrs[i];

				String attrStr = attr.toString().trim().replaceAll("(?m)^",
						"    ");
				sb.append(attrStr).append("\n");
			}
		}
		return sb.toString().trim();
	}

}

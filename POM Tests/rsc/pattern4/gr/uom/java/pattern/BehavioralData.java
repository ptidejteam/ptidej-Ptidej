package gr.uom.java.pattern;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import gr.uom.java.bytecode.FieldObject;
import gr.uom.java.bytecode.MethodObject;

public class BehavioralData {
	private Map<Position, Set<MethodObject>> methodData;
	private Map<Position, Set<FieldObject>> fieldData;
	
	public BehavioralData() {
		this.methodData = new LinkedHashMap<Position, Set<MethodObject>>();
		this.fieldData = new LinkedHashMap<Position, Set<FieldObject>>();
	}
	
	public void addMethod(int row, int column, MethodObject mo) {
		Position pos = new Position(row, column);
		if(methodData.containsKey(pos)) {
			Set<MethodObject> methods = methodData.get(pos);
			methods.add(mo);
		}
		else {
			Set<MethodObject> methods = new LinkedHashSet<MethodObject>();
			methods.add(mo);
			methodData.put(pos, methods);
		}
	}
	
	public void addMethods(int row, int column, Set<MethodObject> methods) {
		Position pos = new Position(row, column);
		if(methodData.containsKey(pos)) {
			Set<MethodObject> previousMethods = methodData.get(pos);
			previousMethods.addAll(methods);
		}
		else {
			methodData.put(pos, methods);
		}
	}
	
	public Set<MethodObject> getMethods(int row, int column) {
		Position pos = new Position(row, column);
		return methodData.get(pos);
	}
	
	public void addField(int row, int column, FieldObject fo) {
		Position pos = new Position(row, column);
		if(fieldData.containsKey(pos)) {
			Set<FieldObject> fields = fieldData.get(pos);
			fields.add(fo);
		}
		else {
			Set<FieldObject> fields = new LinkedHashSet<FieldObject>();
			fields.add(fo);
			fieldData.put(pos, fields);
		}
	}
	
	public void addFields(int row, int column, Set<FieldObject> fields) {
		Position pos = new Position(row, column);
		if(fieldData.containsKey(pos)) {
			Set<FieldObject> previousFields = fieldData.get(pos);
			previousFields.addAll(fields);
		}
		else {
			fieldData.put(pos, fields);
		}
	}
	
	public Set<FieldObject> getFields(int row, int column) {
		Position pos = new Position(row, column);
		return fieldData.get(pos);
	}
	
	private class Position {
		private volatile int hashCode = 0;
		private int row;
		private int column;
		
		public Position(int x, int y) {
			this.row = x;
			this.column = y;
		}
		
		public boolean equals(Object o) {
			if(this == o) {
                return true;
            }

            if (o instanceof Position) {
                Position pos = (Position)o;
                return row == pos.row && column == pos.column;
            }
            return false;
		}
		
		public int hashCode() {
            if (hashCode == 0) {
                int result = 17;
                result = 37*result + row;
                result = 37*result + column;
                hashCode = result;
            }
            return hashCode;
        }
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(").append(row).append(",").append(column).append(")");
			return sb.toString();
		}
	}
}

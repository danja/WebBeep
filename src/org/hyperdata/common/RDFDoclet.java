/*
 * Based on RDFizerDoclet.java created by dfhuynh 2005
 * http://simile.mit.edu/wiki/Javadoc_RDFizer
 * 
 * changes: primarily URNs to HTTP URLs
 */
package org.hyperdata.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;

/**
 * @author dfhuynh
 */
public class RDFDoclet extends Doclet {
	final static public String s_javaPrefix = "java:";
	final static public String s_objectPrefix = "obj:";
	
	static private PrintStream	s_out;
	static private String		s_codebase = "unknown";
	
	static private Set			s_toProcess = new HashSet();
	static private Set			s_processed = new HashSet();
	static private Set			s_externalToProcess = new HashSet();
	static private Set			s_packages = new HashSet();
	
	static private Map			s_docToURI = new HashMap();
	static private String[]		s_separators = new String[20];
	static private int			s_separatorIndex = 0;
	
	static public int optionLength(String option) {
		return 2;
	}
	
	static public boolean validOptions(String[][] options, DocErrorReporter reporter) {
		for (int i = 0; i < options.length; i++) {
			String[] optionValues = options[i];
			try {
				if ("-out".equals(optionValues[0])) {
					s_out = 
						new PrintStream(
							new FileOutputStream(
								new File(optionValues[1])));
				} else if ("-codebase".equals(optionValues[0])) {
					s_codebase = optionValues[1];
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return s_out != null;
	}
	
	static public boolean start(RootDoc root) {
		try {
			writePreamble();
			
			ClassDoc[] classes = root.classes();
			for (int i = 0; i < classes.length; i++) {
				s_toProcess.add(classes[i]);
			}
			
			while (s_toProcess.size() > 0) {
				Iterator i = s_toProcess.iterator();
				
				Doc d = (Doc) i.next();
				
				i.remove();
				
				s_processed.add(d);
				processDoc(d, root);
			}
			
			s_externalToProcess.removeAll(s_processed);

			Iterator i = s_externalToProcess.iterator();
			while (i.hasNext()) {
				processExternal((Doc) i.next(), root);
			}
			
			i = s_packages.iterator();
			while (i.hasNext()) {
				processExternal((Doc) i.next(), root);
			}
			
			s_out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			s_out.close();
			return false;
		}
	}
	
	static private void writePreamble() {
		s_out.print(
			"@prefix rdf:		<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
			"@prefix rdfs:		<http://www.w3.org/2000/01/rdf-schema#> .\n" +
			"@prefix dc:		<http://purl.org/dc/elements/1.1/> .\n" +
			"@prefix java:		<http://simile.mit.edu/java#> .\n" +
			"@prefix obj:		<urn:java:object#> .\n"
		);
	}
	
	static private void processExternal(Doc doc, RootDoc root) {
		String uri = docToURI(doc);
		push();
		
		s_out.println(uri);
		writeStringProperty("rdfs:label", doc.name());
		writeTypes(doc);
		
		if (doc instanceof ProgramElementDoc) {
			writePackages((ProgramElementDoc) doc, root); 
		}
		
		s_out.println(".");
		pop();
	}
	
	static private void processDoc(Doc doc, RootDoc root) {
		String uri = docToURI(doc);
		
		push();
		
		s_out.println(uri);
		writeStringProperty("rdfs:label", doc.name());
		writeStringProperty(s_javaPrefix + "codebase", s_codebase);
		writeWordsInName(doc.name());
		
		writeTypes(doc);
		
		// TODO: process tags
		
		if (doc instanceof ProgramElementDoc) {
			processProgramElement((ProgramElementDoc) doc, root);
		} else if (doc instanceof PackageDoc) {
			processPackage((PackageDoc) doc, root);
		} else {
			throw new RuntimeException("Doc of unknown type " + doc);
		}
		
		s_out.println(".");
		
		pop();
	}
	
	static private void processPackage(PackageDoc pakkage, RootDoc root) {
		// do nothing
	}
	
	static private void processProgramElement(ProgramElementDoc element, RootDoc root) {
		ClassDoc containingClass = element.containingClass();
		if (containingClass != null) {
			writeProperty(s_javaPrefix + "containing-class", docToURI(containingClass));
		}
		
		writePackages(element, root);
		
		if (element.isFinal()) {
			writeStringProperty(s_javaPrefix + "modifier", "final");
		}
		if (element.isStatic()) {
			writeStringProperty(s_javaPrefix + "modifier", "static");
		}
		if (element.isPublic()) {
			writeStringProperty(s_javaPrefix + "modifier", "public");
		}
		if (element.isProtected()) {
			writeStringProperty(s_javaPrefix + "modifier", "protected");
		}
		if (element.isPrivate()) {
			writeStringProperty(s_javaPrefix + "modifier", "private");
		}
		if (element.isPackagePrivate()) {
			writeStringProperty(s_javaPrefix + "modifier", "(package-private)");
		}
		
		if (element instanceof ClassDoc) {
			processClass((ClassDoc) element);
		} else if (element instanceof MemberDoc) {
			processMember((MemberDoc) element);
		} else {
			throw new RuntimeException("Program element of unknown type " + element);
		}
	}
	
	static private void processClass(ClassDoc klass) {
		writeBooleanProperty(s_javaPrefix + "abstract", klass.isAbstract());
		writeBooleanProperty(s_javaPrefix + "externalizable", klass.isExternalizable());
		writeBooleanProperty(s_javaPrefix + "serializable", klass.isSerializable());
		
		ClassDoc superClass = klass.superclass();
		if (superClass != null) {
			writeProperty(s_javaPrefix + "extends", docToURI(superClass));
			
			traceAncestor(superClass, s_javaPrefix + "extends-indirectly");
			traceAncestor(superClass, s_javaPrefix + "assignable-to");
		}
		
		ClassDoc[] interfaces = klass.interfaces();
		for (int i = 0; i < interfaces.length; i++) {
			ClassDoc itf = interfaces[i];
			
			writeProperty(s_javaPrefix + "implements", docToURI(itf));
			traceAncestor(itf, s_javaPrefix + "implements-indirectly");
			traceAncestor(itf, s_javaPrefix + "assignable-to");
		}
		
		ConstructorDoc[] constructors = klass.constructors();
		for (int i = 0; i < constructors.length; i++) {
			ConstructorDoc constructor = constructors[i];
			
			writeProperty(s_javaPrefix + "constructor", docToURI(constructor));
			
			addDoc(constructor);
		}
		
		MethodDoc[] methods = klass.methods();
		for (int i = 0; i < methods.length; i++) {
			MethodDoc method = methods[i];
			
			writeProperty(s_javaPrefix + "method", docToURI(method));
			
			addDoc(method);
		}
		
		FieldDoc[] fields = klass.fields();
		for (int i = 0; i < fields.length; i++) {
			FieldDoc field = fields[i];
			
			writeProperty(s_javaPrefix + "field", docToURI(field));
			
			addDoc(field);
		}
	}
	
	static private void traceAncestor(ClassDoc klass, String property) {
		writeProperty(property, docToURI(klass));
		addExternalDoc(klass);
		
		ClassDoc superclass = klass.superclass();
		if (superclass != null) {
			traceAncestor(superclass, property);
		}
		
		ClassDoc[] interfaces = klass.interfaces();
		for (int i = 0; i < interfaces.length; i++) {
			traceAncestor(interfaces[i], property);
		}
	}
	
	static private void processMember(MemberDoc member) {
		writeBooleanProperty(s_javaPrefix + "synthetic", member.isSynthetic());
		if (member instanceof ExecutableMemberDoc) {
			processExecutableMember((ExecutableMemberDoc) member);
		} else if (member instanceof FieldDoc) {
			processField((FieldDoc) member);
		} else {
			throw new RuntimeException("Member of unknown type " + member);
		}
	}
	
	static private void processExecutableMember(ExecutableMemberDoc member) {
		writeBooleanProperty(s_javaPrefix + "native", member.isNative());
		writeBooleanProperty(s_javaPrefix + "synchronized", member.isSynchronized());
		
		Parameter[] parameters = member.parameters();
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			
			writeStringProperty(s_javaPrefix + "has-param-name", parameter.name());
			
			ClassDoc paramType = parameter.type().asClassDoc();
			if (paramType != null) {
				writeProperty(s_javaPrefix + "takes", docToURI(paramType));
				addExternalDoc(paramType);
			} else {
				writeStringProperty(s_javaPrefix + "takes", parameter.typeName());
			}
		}
		
		ClassDoc[] exceptions = member.thrownExceptions();
		for (int i = 0; i < exceptions.length; i++) {
			ClassDoc exception = exceptions[i];
			
			writeProperty(s_javaPrefix + "throws", docToURI(exception));
			traceAncestor(exception, s_javaPrefix + "throws-descendent-of");
		}
		
		if (member instanceof MethodDoc) {
			processMethod((MethodDoc) member);
		} else if (member instanceof ConstructorDoc) {
			processConstructor((ConstructorDoc) member);
		} else {
			throw new RuntimeException("Executable member of unknown type " + member);
		}
	}
	
	static private void processMethod(MethodDoc method) {
		writeBooleanProperty(s_javaPrefix + "abstract", method.isAbstract());
		
		ClassDoc overriddenClass = method.overriddenClass();
		if (overriddenClass != null) {
			writeProperty(s_javaPrefix + "overrides-class", docToURI(overriddenClass));
			addExternalDoc(overriddenClass);
		}
		
		Type type = method.returnType();
		if (type != null) {
			ClassDoc classType = type.asClassDoc();
			if (classType != null) {
				writeProperty(s_javaPrefix + "expression-type", docToURI(classType));
				addExternalDoc(classType);
			} else {
				writeStringProperty(s_javaPrefix + "expression-type", type.typeName());
			}
		} else {
			writeStringProperty(s_javaPrefix + "expression-type", "void");
		}
	}
	
	static private void processConstructor(ConstructorDoc method) {
		// do nothing
	}
	
	static private void processField(FieldDoc field) {
		writeBooleanProperty(s_javaPrefix + "transient", field.isTransient());
		writeBooleanProperty(s_javaPrefix + "volatile", field.isVolatile());
		
		Type type = field.type();
		ClassDoc typeDoc = type.asClassDoc();
		if (typeDoc != null) {
			writeProperty(s_javaPrefix + "expression-type", docToURI(typeDoc));
			addExternalDoc(typeDoc);
		} else {
			writeStringProperty(s_javaPrefix + "expression-type", type.typeName());
		}
	}
	
	static private String docToURI(Doc doc) {
		String name = (String) s_docToURI.get(doc);
		if (name == null) {
			name = doc.name();
			if (doc instanceof ProgramElementDoc) {
				name = ((ProgramElementDoc) doc).qualifiedName();
			}
			if (doc instanceof ExecutableMemberDoc) {
				ExecutableMemberDoc member = (ExecutableMemberDoc) doc;
				
				name = name + "(";
				
				Parameter[] parameters = member.parameters();
				for (int i = 0; i < parameters.length; i++) {
					name = name + (i == 0 ? "" : ",") + 
						parameters[i].type().qualifiedTypeName();
				}
				
				name = name + ")";
			}
			name = name.replaceAll("\\(", "_");
			name = name.replaceAll("\\)", "_");
			name = name.replaceAll("\\.", "_");
			name = name.replaceAll("\\,", "_");
			
			s_docToURI.put(doc, name);
		}
		return s_objectPrefix + name;
	}
	
	static private void addDoc(Doc d) {
		if (!s_processed.contains(d)) {
			s_toProcess.add(d);
		}
	}
	static private void addExternalDoc(Doc d) {
		s_externalToProcess.add(d);
	}
	
	static private void writePackages(ProgramElementDoc element, RootDoc root) {
		PackageDoc containingPackage = element.containingPackage();
		if (containingPackage != null) {
			writeProperty(s_javaPrefix + "containing-package", docToURI(containingPackage));
			
			while (containingPackage != null) {
				s_packages.add(containingPackage);
				
				writeProperty(s_javaPrefix + "ancestor-package", docToURI(containingPackage));
				
				String pakkageName = containingPackage.name();
				int i = pakkageName.lastIndexOf('.');
				if (i >= 0) {
					containingPackage = root.packageNamed(pakkageName.substring(0, i));
				} else {
					containingPackage = null;
				}
			}
		}
	}
	static private void writeTypes(Doc doc) {
		if (doc.isClass()) {
			writeProperty("rdf:type", s_javaPrefix + "Class");
			if (doc.isError()) {
				writeProperty("rdf:type", s_javaPrefix + "Error");
			}
			if (doc.isException()) {
				writeProperty("rdf:type", s_javaPrefix + "Exception");
			}
		} else if (doc.isInterface()) {
			writeProperty("rdf:type", s_javaPrefix + "Interface");
		} else if (doc.isConstructor()) {
			writeProperty("rdf:type", s_javaPrefix + "Constructor");
		} else if (doc.isMethod()) {
			writeProperty("rdf:type", s_javaPrefix + "Method");
		} else if (doc.isField()) {
			writeProperty("rdf:type", s_javaPrefix + "Field");
		} else if (doc instanceof PackageDoc) {
			writeProperty("rdf:type", s_javaPrefix + "Package");
		}
	}
	static private void writeWordsInName(String name) {
		String[] segments = name.split("\\.");
		for (int i = 0; i < segments.length; i++) {
			internalWriteNameFragments(segments[i]);
		}
	}
	static private void internalWriteNameFragments(String name) {
		int end = name.length();
		while (end > 0) {
			boolean upperCase = Character.isUpperCase(name.charAt(end - 1));
			
			int start = end - 1;
			while (start > 0) {
				if (upperCase) {
					if (Character.isLowerCase(name.charAt(start))) {
						start++;
						break;
					}
				} else {
					if (Character.isUpperCase(name.charAt(start))) {
						break;
					}
				}
				start--;
			}
			
			writeStringProperty(s_javaPrefix + "word", name.substring(start, end));
			
			end = start;
		}
	}
	static private void writeBooleanProperty(String predicate, boolean b) {
		writeStringProperty(predicate, b ? "true" : "false");
	}
	static private void writeStringProperty(String predicate, String s) {
		writeProperty(predicate, "\"" + s + "\"");
	}
	static private void writeProperty(String predicate, String object) {
		s_out.println(getSeparator() + " " + predicate + " " + object);
		separate();
	}
	static private void push() {
		s_separators[s_separatorIndex++] = " ";
	}
	static private void pop() {
		s_separatorIndex--;
	}
	static private void separate() {
		s_separators[s_separatorIndex - 1] = ";";
	}
	static private String getSeparator() {
		return s_separators[s_separatorIndex - 1];
	}
}

/*
* Copyright (c) 2012 Vienna University of Technology.
* All rights reserved. This program and the accompanying materials are made 
* available under the terms of the Eclipse Public License v1.0 which accompanies 
* this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
* Philip Langer - initial API and generator
*/
package org.modelexecution.fuml.convert.uml2.internal.gen;
    	
import javax.annotation.Generated;
import org.modelexecution.fuml.convert.impl.ConversionResultImpl;
import org.modelexecution.fuml.convert.uml2.internal.IElementPopulator;

@Generated(value="Generated by org.modelexecution.fuml.convert.uml2.gen.ElementPopulatorGenerator.xtend")
public class TypePopulator implements IElementPopulator {

	@Override
	public void populate(fUML.Syntax.Classes.Kernel.Element fumlElement,
		org.eclipse.uml2.uml.Element uml2Element, ConversionResultImpl result) {
			
		if (!(uml2Element instanceof org.eclipse.uml2.uml.Type) ||
			!(fumlElement instanceof fUML.Syntax.Classes.Kernel.Type)) {
			return;
		}
		
		fUML.Syntax.Classes.Kernel.Type fumlNamedElement = (fUML.Syntax.Classes.Kernel.Type) fumlElement;
		org.eclipse.uml2.uml.Type uml2NamedElement = (org.eclipse.uml2.uml.Type) uml2Element;
		
		fumlNamedElement.package_ = (fUML.Syntax.Classes.Kernel.Package) result
							.getFUMLElement(uml2NamedElement.getPackage());
		
		
		
		
	}
	
}

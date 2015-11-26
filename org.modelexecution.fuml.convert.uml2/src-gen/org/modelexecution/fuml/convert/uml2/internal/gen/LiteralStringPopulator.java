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
public class LiteralStringPopulator implements IElementPopulator {

	@Override
	public void populate(fUML.Syntax.Classes.Kernel.Element fumlElement,
		org.eclipse.uml2.uml.Element uml2Element, ConversionResultImpl result) {
			
		if (!(uml2Element instanceof org.eclipse.uml2.uml.LiteralString) ||
			!(fumlElement instanceof fUML.Syntax.Classes.Kernel.LiteralString)) {
			return;
		}
		
		fUML.Syntax.Classes.Kernel.LiteralString fumlNamedElement = (fUML.Syntax.Classes.Kernel.LiteralString) fumlElement;
		org.eclipse.uml2.uml.LiteralString uml2NamedElement = (org.eclipse.uml2.uml.LiteralString) uml2Element;
		
		fumlNamedElement.setValue(uml2NamedElement.getValue());
		
		
		
		
	}
	
}

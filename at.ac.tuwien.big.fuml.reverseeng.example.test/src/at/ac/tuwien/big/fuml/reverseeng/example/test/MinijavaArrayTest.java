package at.ac.tuwien.big.fuml.reverseeng.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.modelexecution.fumldebug.core.ExecutionContext;
import org.modelexecution.fumldebug.core.trace.tracemodel.ActivityExecution;
import org.modelexecution.fumldebug.core.trace.tracemodel.CallActionExecution;

import fUML.Semantics.Classes.Kernel.ExtensionalValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.Object_;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.Loci.LociL1.Locus;

public class MinijavaArrayTest extends at.ac.tuwien.big.fuml.reverseeng.example.test.Test {

	//private static final String UMLMODEL_PATH = "model/ArrayUMLActivityDiagramMini_manual.uml";
	private static final String UMLMODEL_PATH = "model/ArrayUMLActivityDiagramMini.uml";
	
	@BeforeClass
	public static void setup() {
		setup(UMLMODEL_PATH);
	}
	
	@Test
	public void array() {	
		//executeActivity("ArrayClass_main", null); //manual
		executeActivity("ArrayMain_main", null);
		ActivityExecution activityExecution = getActivityExecution();
		CallActionExecution callArrayExecution = (CallActionExecution)activityExecution.getNodeExecutions().get(2);
		Value arrayOutputValue = callArrayExecution.getOutputs().get(0).getOutputValues().get(0).getValueSnapshot().getValue();
		assertEquals(3, ((IntegerValue)arrayOutputValue).value);
		
		//second param is because it is needed a context object
//		executeActivity("ArrayClass_access", null); 
//		ActivityExecution activityExecution = getActivityExecution();
//		Value outputValue = activityExecution.getActivityOutputs().get(0).getParameterValues().get(0).getValueSnapshot().getValue();
//		assertEquals(4, ((IntegerValue)outputValue).value);
	}
	
//	public Object_ getArrayClassObject() {
//		ExecutionContext executionContext = ExecutionContext.getInstance();
//		Locus locus = executionContext.getLocus();
//		for(ExtensionalValue value : locus.extensionalValues) {
//			if (value instanceof Object_) {
//				Object_ object_ = (Object_) value;
//				if (object_.types.get(0).name.equals("ArrayClass"))
//					return object_;
//			}
//		}
//		return null;
//	}
}

package at.ac.tuwien.big.fuml.reverseeng.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.modelexecution.fumldebug.core.trace.tracemodel.ActivityExecution;
import org.modelexecution.fumldebug.core.trace.tracemodel.CallActionExecution;

import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class MinijavaBreakWhileLoopTest extends at.ac.tuwien.big.fuml.reverseeng.example.test.Test {

	//private static final String UMLMODEL_PATH = "model/LoopsUMLActivityDiagramMini_manual.uml";
	//private static final String UMLMODEL_PATH = "model/LoopsUMLActivityDiagramMini.uml";
	private static final String UMLMODEL_PATH = "model/BreakWhileLoop.uml"; //Tanja

	@BeforeClass
	public static void setup() {
		setup(UMLMODEL_PATH);
	}
	
	@Test
	public void loop() {
		executeActivity("BreakWhileLoop_whileMethod", null);
		ActivityExecution activityExecution = getActivityExecution();
		Value outputValue = activityExecution.getActivityOutputs().get(0).getParameterValues().get(0).getValueSnapshot().getValue();
		assertEquals(5, ((IntegerValue)outputValue).value);
	
	}
}

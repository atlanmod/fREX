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

public class MinijavaAdditionTest extends at.ac.tuwien.big.fuml.reverseeng.example.test.Test {

	private static final String UMLMODEL_PATH = "model/AdditionUMLActivityDiagramMini.uml";

	@BeforeClass
	public static void setup() {
		setup(UMLMODEL_PATH);
	}
	
	@Test
	public void addition() {		
		executeActivity("MainClass_sum", null);
		ActivityExecution activityExecution = getActivityExecution();
		Value outputValue = activityExecution.getActivityOutputs().get(0).getParameterValues().get(0).getValueSnapshot().getValue();
		assertEquals(3, ((IntegerValue)outputValue).value);
	}
}

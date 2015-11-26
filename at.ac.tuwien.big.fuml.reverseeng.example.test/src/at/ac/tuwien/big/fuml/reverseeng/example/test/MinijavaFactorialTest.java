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

public class MinijavaFactorialTest extends at.ac.tuwien.big.fuml.reverseeng.example.test.Test {

	private static final String UMLMODEL_PATH = "model/FactorialUMLActivityDiagramMini.uml";

	@BeforeClass
	public static void setup() {
		setup(UMLMODEL_PATH);
	}
	
	@Test
	public void factorial() {
		executeActivity("Factorial_main", null);
		ActivityExecution activityExecution = getActivityExecution();
		CallActionExecution callComputeFacExecution = (CallActionExecution)activityExecution.getNodeExecutions().get(2);
		Value computeFacOutputValue = callComputeFacExecution.getOutputs().get(0).getOutputValues().get(0).getValueSnapshot().getValue();
		assertEquals(3628800, ((IntegerValue)computeFacOutputValue).value);
		
//		ValueList params = new ValueList();
//		Value param = createValue(10);
//		params.add(param);
//		ParameterValueList output = executeActivity("Fac_ComputeFac", null, params);
//		ValueList outputValues = output.get(0).values;
//		assertEquals(3628800, ((IntegerValue)outputValues.get(0)).value);
	}
}

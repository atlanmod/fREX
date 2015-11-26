package at.ac.tuwien.big.fuml.reverseeng.example.test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Before;
import org.modelexecution.fuml.convert.ConverterRegistry;
import org.modelexecution.fuml.convert.IConversionResult;
import org.modelexecution.fuml.convert.IConverter;
import org.modelexecution.fumldebug.core.ExecutionContext;
import org.modelexecution.fumldebug.core.ExecutionEventListener;
import org.modelexecution.fumldebug.core.event.ActivityEntryEvent;
import org.modelexecution.fumldebug.core.event.Event;
import org.modelexecution.fumldebug.libraryregistry.LibraryRegistry;
import org.modelexecution.fumldebug.libraryregistry.OpaqueBehaviorCallReplacer;

import fUML.Semantics.Classes.Kernel.FeatureValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.Object_;
import fUML.Semantics.Classes.Kernel.Reference;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.Loci.LociL1.Locus;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public abstract class Test {
	
	private static final ConverterRegistry converterRegistry = ConverterRegistry
			.getInstance();

	private static ResourceSet resourceSet;
	private static Resource resource;
	private static IConversionResult conversionResult;
	private static ExecutionContext executionContext;

	private int executionID = -1;

	public static void setup(String modelPath) {
		resourceSet = setupResourceSet();
		resource = loadModel(modelPath);
		executionContext = ExecutionContext.getInstance();
		conversionResult = convertResource();
		registerOpaqueBehaviors();
	}
	
	@Before
	public void setupExecutionContext() {
		executionContext.reset();
		executionID = -1;
		registerExecutionEventListener();
	}
	
	private static ResourceSet setupResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI,
				UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		return resourceSet;
	}

	private static Resource loadModel(String modelPath) {
		URI uri = URI.createFileURI(new File(modelPath).getAbsolutePath());
		Resource resource = resourceSet.getResource(uri, true);
		EcoreUtil.resolveAll(resourceSet);
		return resource;
	}

	private static IConversionResult convertResource() {
		IConverter converter = getConverter(resource);
		return converter.convert(resource);
	}

	private static IConverter getConverter(Object input) {
		return converterRegistry.getConverter(input);
	}

	private void registerExecutionEventListener() {
		executionContext.addEventListener(new ExecutionEventListener() {
			@Override
			public void notify(Event event) {
				try {
					System.out.println(event);
				} catch (Exception e) {
				}
				if (event instanceof ActivityEntryEvent && executionID == -1) {
					executionID = ((ActivityEntryEvent) event)
							.getActivityExecutionID();
				}
			}
		});
	}

	protected ParameterValueList executeActivity(String activityName,
			Object_ context, ValueList... inputValues) {
		Activity activity = getActivity(activityName);
		ParameterValueList parameters = createParameterValueList(activity,
				inputValues);
		addContextObjectToLocus(context);
		addParameterObjectsToLocus(inputValues);
		executionContext.execute(activity, context, parameters);
		return executionContext.getActivityOutput(executionID);
	}

	private void addParameterObjectsToLocus(ValueList[] inputValues) {
		Locus locus = executionContext.getLocus();
		for (ValueList inputValue : inputValues) {
			for (Value value : inputValue) {
				if (value instanceof Reference) {
					Reference reference = (Reference) value;
					if (!locus.extensionalValues.contains(reference.referent))
						locus.add(reference.referent);
				}
			}
		}
	}

	private void addContextObjectToLocus(Object_ context) {
		if (context != null)
			executionContext.getLocus().add(context);
	}

	private ParameterValueList createParameterValueList(Activity activity,
			ValueList... inputValues) {
		int nextInputValue = 0;
		ParameterValueList parameterValueList = new ParameterValueList();
		for (Parameter parameter : activity.ownedParameter) {
			if (parameter.direction == ParameterDirectionKind.in) {
				ParameterValue parameterValue = createParameterValue(parameter,
						inputValues[nextInputValue++]);
				parameterValueList.add(parameterValue);
			}
		}
		return parameterValueList;
	}

	private ParameterValue createParameterValue(Parameter parameter,
			ValueList value) {
		ParameterValue parameterValue = new ParameterValue();
		parameterValue.parameter = parameter;
		parameterValue.values = value;
		return parameterValue;
	}

	protected Activity getActivity(String activityName) {
		return conversionResult.getActivity(activityName);
	}

	protected Object_ createObject(String className) {
		Object_ object = new Object_();
		Class_ class_ = getClass(className);
		object.types.add(class_);
		return object;
	}

	protected Class_ getClass(String className) {
		for (TreeIterator<EObject> allContents = resource.getAllContents(); allContents
				.hasNext();) {
			EObject eObject = allContents.next();
			if (eObject instanceof org.eclipse.uml2.uml.Class) {
				org.eclipse.uml2.uml.Class classUML = (org.eclipse.uml2.uml.Class) eObject;
				if (classUML.getName().equals(className))
					return (Class_) conversionResult.getFUMLElement(classUML);
			}
		}
		return null;
	}

	protected void setFeatureValue(Object_ object, String featureName,
			Value... values) {
		Class_ class_ = object.types.get(0);
		Property feature = getOwnedAttribute(class_, featureName);
		if (feature != null) {
			FeatureValue featureValue = new FeatureValue();
			featureValue.feature = feature;
			List<Value> valuesAsList = Arrays.asList(values);
			ValueList valueList = new ValueList();
			valueList.addAll(valuesAsList);
			featureValue.values = valueList;
			object.featureValues.add(featureValue);
		}
	}

	protected FeatureValue getFeatureValue(Object_ object, String featureName) {
		Property feature = getOwnedAttribute(object.types.get(0), featureName);
		if (feature != null)
			return object.getFeatureValue(feature);
		return null;
	}

	private Property getOwnedAttribute(Class_ class_, String featureName) {
		for (Property attribute : class_.ownedAttribute) {
			if (attribute.name.equals(featureName))
				return attribute;
		}
		return null;
	}

	protected Value createValue(String string) {
		StringValue value = new StringValue();
		value.value = string;
		return value;
	}

	protected Value createValue(int int_) {
		IntegerValue value = new IntegerValue();
		value.value = int_;
		return value;
	}

	protected Value createValue(Object_ object) {
		Reference value = new Reference();
		value.referent = object;
		return value;
	}

	protected ValueList createValueList(Value... values) {
		ValueList valueList = new ValueList();
		valueList.addAll(Arrays.asList(values));
		return valueList;
	}
	
	private static void registerOpaqueBehaviors() {
		LibraryRegistry libraryRegistry = new LibraryRegistry(executionContext);
		Map<String, OpaqueBehavior> registeredOpaqueBehaviors = libraryRegistry.loadRegisteredLibraries();
		OpaqueBehaviorCallReplacer.instance.replaceOpaqueBehaviorCalls(conversionResult
				.getAllActivities(), registeredOpaqueBehaviors);				
	}

	protected ActivityNode getActivityNode(String activityName, String nodeName) {
		return getActivityNode(getActivity(activityName), nodeName);
	}

	private ActivityNode getActivityNode(Activity activity, String nodeName) {
		return getActivityNode(activity.node, nodeName);
	}

	private ActivityNode getActivityNode(ActivityNodeList nodes, String nodeName) {
		for (ActivityNode node : nodes) {
			if (node.name != null)
				if (node.name.equals(nodeName))
					return node;
			if (node instanceof StructuredActivityNode) {
				StructuredActivityNode structuredActivityNode = (StructuredActivityNode) node;
				ActivityNode activityNode = getActivityNode(structuredActivityNode.node, nodeName);
				if (activityNode != null)
					return activityNode;
			}
		}
		return null;
	}

	protected org.modelexecution.fumldebug.core.trace.tracemodel.ActivityExecution getActivityExecution() {
		return executionContext.getTrace(executionID).getActivityExecutionByID(
				executionID);
	}
	
}

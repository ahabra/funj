package com.tek271.funj;

public class FunctionDescriptor {
	public final String staticPath;
	public final Object dynamicContext;
	public final String functionName;
	public final boolean isStatic;
	private final String staticFullFunction;

	private FunctionDescriptor(String staticPath, Object dynamicContext,
														 String functionName, boolean isStatic) {
		this.staticPath = staticPath;
		this.dynamicContext = dynamicContext;
		this.functionName = functionName;
		this.isStatic = isStatic;
		this.staticFullFunction = staticPath + "." + functionName;
	}

	public static FunctionDescriptor dynamicFunction(Object dynamicContext, String functionName) {
		if (dynamicContext == null) {
			throw new IllegalArgumentException("Cannot create a dynamic function descriptor with null context");
		}
		return new FunctionDescriptor(null, dynamicContext, functionName, false);
	}

	public static FunctionDescriptor staticFunction(String staticPath, String functionName) {
		if (staticPath == null || staticPath.length() == 0) {
			throw new IllegalArgumentException("Cannot create a static function descriptor with no class name");
		}
		return new FunctionDescriptor(staticPath, null, functionName, true);
	}

	public static FunctionDescriptor staticFunction(Class<?> cls, String functionName) {
		if (cls == null) {
			throw new IllegalArgumentException("Cannot create a static function descriptor with null class");
		}
		return new FunctionDescriptor(cls.getName(), null, functionName, true);
	}

	public static FunctionDescriptor staticFunction(String fullFunctionName) {
		if (fullFunctionName == null || fullFunctionName.length() == 0) {
			throw new IllegalArgumentException("Cannot create a static function descriptor with no function name");
		}
		int i= fullFunctionName.lastIndexOf('.');
		String path= fullFunctionName.substring(0, i);
		String functionName = fullFunctionName.substring(i+1);

		return new FunctionDescriptor(path, null, functionName, true);
	}


	public <OUT> OUT call(Object... args) {
		if (isStatic) {
			return ReflectionTools.callStatic(staticFullFunction, args);
		}
		return ReflectionTools.callMethod(dynamicContext, functionName, args);
	}

}

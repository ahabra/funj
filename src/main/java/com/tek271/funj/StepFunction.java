package com.tek271.funj;

import static com.tek271.funj.ReflectionTools.callMethod;
import static com.tek271.funj.ReflectionTools.callStatic;

public class StepFunction {
	private String staticPath;
	private Object dynamicContext;
	private String functionName;
	private Object[] extraArgs;
	private boolean isIgnoreNulls;
	private FunctionType functionType = FunctionType.MAP;

	public static StepFunction create() {
		return new StepFunction();
	}

	public StepFunction staticClass(String staticPath) {
		this.staticPath = staticPath;
		this.dynamicContext = null;
		return this;
	}

	public StepFunction staticClass(Class<?> cls) {
		return staticClass(cls.getName());
	}

	public StepFunction dynamicContext(Object context) {
		this.dynamicContext = context;
		this.staticPath = null;
		return this;
	}

	public StepFunction functionName(String functionName) {
		this.functionName = functionName;
		return this;
	}

	public StepFunction extraArgs(Object... args) {
		this.extraArgs = args;
		return this;
	}

	public StepFunction ignoreNulls(boolean isIgnoreNulls) {
		this.isIgnoreNulls = isIgnoreNulls;
		return this;
	}

	public StepFunction ignoreNulls() {
		return ignoreNulls(true);
	}

	public boolean isIgnoreNulls() {
		return isIgnoreNulls;
	}

	public StepFunction functionType(FunctionType functionType) {
		this.functionType = functionType;
		return this;
	}

	public FunctionType getFunctionType() {
		return this.functionType;
	}

	private <OUT> OUT call(Object[] args) {
		if (dynamicContext == null) {
			String callback = staticPath + "." + functionName;
			return callStatic(callback, args);
		}
		return callMethod(dynamicContext, functionName, args);
	}

	public <OUT> OUT call(Object arg) {
		Object[] args = ArrayTools.concat(arg, extraArgs);
		return call(args);
	}


}

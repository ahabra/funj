package com.tek271.funj;

import com.google.common.annotations.Beta;
import com.google.common.collect.ObjectArrays;

import java.util.List;

import static com.tek271.funj.ReflectionTools.callMethod;
import static com.tek271.funj.ReflectionTools.callStatic;

@Beta
public class StepFunction {
	private String staticPath;
	private Object dynamicContext;
	private String functionName;
	private Object[] extraArgs;
	private boolean isIgnoreNulls;
	private TransformType transformType = TransformType.MAP;

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
		if (ArrayTools.size(this.extraArgs) == 0) {
			this.extraArgs = args;
		} else {
			this.extraArgs = ObjectArrays.concat(this.extraArgs, args, Object.class);
		}

		return this;
	}

	public StepFunction initialValueForMap(Object arg) {
		this.extraArgs = new Object[] {arg};
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

	public StepFunction transformType(TransformType transformType) {
		this.transformType = transformType;
		return this;
	}

	private <OUT> OUT call(Object[] args) {
		if (dynamicContext == null) {
			String callback = staticPath + "." + functionName;
			return callStatic(callback, args);
		}
		return callMethod(dynamicContext, functionName, args);
	}

	public <OUT> OUT call(Object arg) {
		Object[] args;
		if (ArrayTools.size(this.extraArgs) == 0) {
			args = new Object[] {arg};
		} else {
			args = ObjectArrays.concat(arg, this.extraArgs);
		}
		return call(args);
	}

	public <IN, OUT> List<OUT> apply(Iterable<IN> iterable) {
		return transformType.apply(iterable, this);
	}

}

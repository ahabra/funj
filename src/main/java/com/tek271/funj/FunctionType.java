package com.tek271.funj;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public enum FunctionType {
	MAP {
		@Override
		public <IN, OUT> List<OUT> apply(Iterable<IN> iterable, StepFunction stepFunction) {
			List<OUT> list = newArrayList();
			boolean includeNulls = !stepFunction.isIgnoreNulls();

			for (IN i: iterable) {
				OUT out = stepFunction.call(i);
				if (includeNulls || out != null) {
					list.add(out);
				}
			}
			return list;
		}
	},


	FILTER {
		@SuppressWarnings("unchecked")
		@Override
		public <IN, OUT> List<OUT> apply(Iterable<IN> iterable, StepFunction stepFunction) {
			List<OUT> list = newArrayList();
			for (IN i: iterable) {
				Boolean bool = stepFunction.call(i);
				if (bool) {
					list.add((OUT) i);
				}
			}
			return list;
		}
	}
	;

	// TODO REDUCE, EXPAND ??

	public abstract <IN, OUT> List<OUT> apply(Iterable<IN> iterable, StepFunction stepFunction);

}

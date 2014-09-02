package com.tek271.funj;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Transformer {
	private List<StepFunction> steps = newArrayList();

	public static Transformer create() {
		return new Transformer();
	}

	public void addSteps(StepFunction... steps) {
		this.steps.addAll(newArrayList(steps));
	}

	@SuppressWarnings("unchecked")
	public <IN, OUT> List<OUT> apply(Iterable<IN> iterable) {
		List<?> in = newArrayList(iterable);

		for (StepFunction step: steps) {
			in = applyStep(in, step);
		}

		return (List<OUT>) in;
	}

	@SuppressWarnings("unchecked")
	private <IN, OUT> List<OUT> applyStep(Iterable<IN> iterable, StepFunction stepFunction) {
		if (stepFunction.isFilterFunction()) {
			return (List<OUT>) filter(iterable, stepFunction);
		}

		return map(iterable, stepFunction);
	}

	private <T> List<T> filter(Iterable<T> iterable, StepFunction stepFunction) {
		List<T> list = newArrayList();
		for (T i: iterable) {
			Boolean bool = stepFunction.call(i);
			if (bool) {
				list.add(i);
			}
		}
		return list;
	}

	private <IN, OUT> List<OUT> map(Iterable<IN> iterable, StepFunction stepFunction) {
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


}

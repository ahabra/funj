package com.tek271.funj;

import com.google.common.annotations.Beta;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Beta
public class Transformer {
	private List<StepFunction> steps = newArrayList();

	public static Transformer create() {
		return new Transformer();
	}

	public Transformer addSteps(StepFunction... steps) {
		this.steps.addAll(newArrayList(steps));
		return this;
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
		return stepFunction.getFunctionType().apply(iterable, stepFunction);
	}


}

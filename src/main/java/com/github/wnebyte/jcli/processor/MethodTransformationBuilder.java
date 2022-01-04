package com.github.wnebyte.jcli.processor;

import com.github.wnebyte.jarguments.factory.AbstractArgumentFactoryBuilder;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;

public class MethodTransformationBuilder {

    private IInstanceTracker tracker;

    private AbstractArgumentFactoryBuilder builder = new ArgumentFactoryBuilder();

    public MethodTransformationBuilder setInstanceTracker(IInstanceTracker tracker) {
        this.tracker = tracker;
        return this;
    }

    public MethodTransformationBuilder setArgumentFactoryBuilder(AbstractArgumentFactoryBuilder builder) {
        if (builder != null) {
            this.builder = builder;
        }
        return this;
    }

    public MethodTransformation build() {
        if (tracker == null) {
            throw new NullPointerException(
                    "InstanceTracker may not be null."
            );
        }
        return new MethodTransformation(tracker, builder);
    }
}

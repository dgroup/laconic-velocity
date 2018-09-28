/*
 * MIT License
 *
 * Copyright (c) 2018 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.dgroup.velocity.resource;

import com.github.dgroup.velocity.Resource;
import com.github.dgroup.velocity.ResourceException;
import org.apache.velocity.VelocityContext;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;

/**
 * The envelope for {@link Resource}.
 *
 * @param <T> Type of resource.
 * @since 0.1.0
 */
public class RsEnvelope<T> implements Resource<T> {

    /**
     * The func to evaluate the entity.
     */
    private final Func<VelocityContext, T> fnc;

    /**
     * Ctor.
     * @param fnc The function to map the {@link VelocityContext} to entity.
     */
    public RsEnvelope(final Func<VelocityContext, T> fnc) {
        this.fnc = fnc;
    }

    @Override
    public final T transform(final RsVariable... args)
        throws ResourceException {
        return this.transform(new ListOf<>(args));
    }

    @Override
    public final T transform(final Iterable<RsVariable> args)
        throws ResourceException {
        return this.transform(
            () -> {
                final VelocityContext ctx = new VelocityContext();
                for (final RsVariable arg : args) {
                    ctx.put(arg.getKey(), arg.getValue());
                }
                return ctx;
            }
        );
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public final T transform(final Scalar<VelocityContext> ctx)
        // @checkstyle IllegalCatchCheck (5 lines)
        throws ResourceException {
        try {
            return this.fnc.apply(ctx.value());
        } catch (final Exception cause) {
            throw new ResourceException(cause);
        }
    }

}

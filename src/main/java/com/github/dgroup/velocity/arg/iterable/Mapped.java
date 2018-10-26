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
package com.github.dgroup.velocity.arg.iterable;

import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterator.IteratorOf;

/**
 * The resource variable with mapped value.
 *
 * Useful when the resource value is {@code Iterable<X>} which need to be
 *  transformed into {@code Iterable<Y>}.
 *
 * @param <X> Type of source item
 * @param <Y> Type of target item
 *
 * @since 0.2.0
 */
public final class Mapped<X, Y> extends ArgsEnvelope<Y> {

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param fnc The function to map {@code <X>} into {@code <Y>}.
     * @param val The variable value specified in template file.
     */
    public Mapped(final String key, final Func<X, Y> fnc, final X... val) {
        this(key, fnc, new IteratorOf<>(val));
    }

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param fnc The function to map {@code <X>} into {@code <Y>}.
     * @param val The variable value specified in template file.
     */
    public Mapped(final String key, final Func<X, Y> fnc,
        final Iterator<X> val) {
        this(key, fnc, new IterableOf<>(val));
    }

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param fnc The function to map {@code <X>} into {@code <Y>}.
     * @param val The variable value specified in template file.
     */
    public Mapped(final String key, final Func<X, Y> fnc,
        final Iterable<X> val) {
        super(key, () -> new org.cactoos.iterable.Mapped<>(fnc, val));
    }
}

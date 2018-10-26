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

import java.util.Comparator;
import java.util.function.Function;
import org.cactoos.iterable.IterableOf;

/**
 * The resource variable with sorted value.
 *
 * Useful when the resource value is collection or other iterable entities.
 *
 * @param <V> The type of value.
 * @since 0.2.0
 */
public final class Sorted<V> extends ArgsEnvelope<V> {

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param val The variable value specified in template file.
     *  The value should implement the {@link Comparable}.
     */
    @SuppressWarnings("unchecked")
    public Sorted(final String key, final V... val) {
        this(key, (Comparator<V>) Comparator.naturalOrder(), val);
    }

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param fnc The function to evaluate the comparator for values sorting.
     * @param val The variable value specified in template file.
     * @param <K> the type of the {@code Comparable} sort key.
     */
    public <K extends Comparable<? super K>> Sorted(
        final String key,
        final Function<? super V, ? extends K> fnc,
        final V... val
    ) {
        this(key, fnc, new IterableOf<>(val));
    }

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param fnc The function to evaluate the comparator for values sorting.
     * @param val The variable value specified in template file.
     * @param <K> the type of the {@code Comparable} sort key.
     */
    public <K extends Comparable<? super K>> Sorted(
        final String key,
        final Function<? super V, ? extends K> fnc,
        final Iterable<V> val
    ) {
        this(key, Comparator.comparing(fnc), val);
    }

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param cmp The comparator in order to sort the values.
     * @param val The variable value specified in template file.
     */
    public Sorted(final String key, final Comparator<V> cmp, final V... val) {
        this(key, cmp, new IterableOf<>(val));
    }

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param cmp The comparator in order to sort the values.
     * @param value The variable value specified in template file.
     */
    public Sorted(final String key, final Comparator<V> cmp,
        final Iterable<V> value) {
        super(key, () -> new org.cactoos.iterable.Sorted<>(cmp, value));
    }

}

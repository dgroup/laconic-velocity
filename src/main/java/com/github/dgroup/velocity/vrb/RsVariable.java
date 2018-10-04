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

package com.github.dgroup.velocity.vrb;

import com.github.dgroup.velocity.Variable;
import org.apache.velocity.VelocityContext;

/**
 * The resource variable.
 *
 * The variable, which will be replaced in the velocity template.
 * Immutable single argument for {@link VelocityContext}.
 *
 * @since 0.1.0
 */
public final class RsVariable implements Variable<Object> {

    /**
     * The Apache Velocity variable name, specified in template file.
     */
    private final String key;

    /**
     * The Apache Velocity variable value, specified in template file.
     */
    private final Object val;

    /**
     * Ctor.
     * @param key The variable name specified in template file.
     * @param value The variable value specified in template file.
     */
    public RsVariable(final String key, final Object value) {
        this.key = key;
        this.val = value;
    }

    @Override
    public String name() {
        return this.key;
    }

    @Override
    public Object value() {
        return this.val;
    }

}

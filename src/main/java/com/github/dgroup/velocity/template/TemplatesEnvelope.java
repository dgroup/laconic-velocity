/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yurii Dubinka
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
package com.github.dgroup.velocity.template;

import com.github.dgroup.velocity.Template;
import com.github.dgroup.velocity.Templates;
import org.cactoos.Func;
import org.cactoos.func.StickyFunc;

/**
 * The envelope for {@link Templates}.
 *
 * @param <T> The type of template.
 *
 * @since 0.2.0
 */
public class TemplatesEnvelope<T> implements Templates<T> {

    /**
     * The function to evaluate the velocity template by filename.
     */
    private final Func<String, Template<T>> fnc;

    /**
     * Ctor.
     * @param fnc The function to evaluate the velocity template by filename.
     */
    public TemplatesEnvelope(final Func<String, Template<T>> fnc) {
        this.fnc = new StickyFunc<>(fnc);
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public final Template<T> find(final String fname) throws TemplateException {
        // @checkstyle IllegalCatchCheck (5 lines)
        try {
            return this.fnc.apply(fname);
        } catch (final Exception cause) {
            throw new TemplateException(cause);
        }
    }

}

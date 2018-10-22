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
package com.github.dgroup.velocity.rs;

import com.github.dgroup.velocity.Resource;
import com.github.dgroup.velocity.Resources;
import org.cactoos.Func;
import org.cactoos.func.StickyFunc;

/**
 * The envelope for {@link Resources}.
 *
 * @param <T> The type of resource.
 *
 * @since 0.2.0
 */
public class ResourcesEnvelope<T> implements Resources<T> {

    /**
     * The function to evaluate the velocity resource by filename.
     */
    private final Func<String, Resource<T>> fnc;

    /**
     * Ctor.
     * @param fnc The function to evaluate the velocity resource by filename.
     */
    public ResourcesEnvelope(final Func<String, Resource<T>> fnc) {
        this.fnc = new StickyFunc<>(fnc);
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public final Resource<T> find(final String fname) throws RsException {
        // @checkstyle IllegalCatchCheck (5 lines)
        try {
            return this.fnc.apply(fname);
        } catch (final Exception cause) {
            throw new RsException(cause);
        }
    }

}

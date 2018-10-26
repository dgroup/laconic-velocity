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
import java.util.Map;
import org.cactoos.Func;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.cactoos.map.StickyMap;

/**
 * The storage for textual velocity resources.
 *
 * @since 0.2.0
 */
public final class ResourcesOf extends ResourcesEnvelope<String> {

    /**
     * Detect the velocity resources in the classpath.
     */
    public ResourcesOf() {
        this(RsClasspath::new);
    }

    /**
     * Ctor.
     * @param name The name of velocity resource.
     * @param rsrc The velocity resource.
     */
    public ResourcesOf(final String name, final Resource<String> rsrc) {
        this(new MapEntry<>(name, rsrc));
    }

    /**
     * Ctor.
     * @param rsrcs The velocity resources, where key is filename.
     */
    @SafeVarargs
    public ResourcesOf(final MapEntry<String, Resource<String>>... rsrcs) {
        this(new StickyMap<>(new MapOf<>(rsrcs)));
    }

    /**
     * Ctor.
     * @param rsrcs The velocity resources, where key is filename.
     */
    public ResourcesOf(final Map<String, Resource<String>> rsrcs) {
        this(rsrcs::get);
    }

    /**
     * Ctor.
     * @param fnc The function to evaluate the velocity resource by filename.
     */
    public ResourcesOf(final Func<String, Resource<String>> fnc) {
        super(fnc);
    }

}

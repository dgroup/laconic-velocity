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
package com.github.dgroup.velocity.template;

import com.github.dgroup.velocity.path.RelativePath;
import java.text.MessageFormat;
import org.cactoos.Scalar;
import org.cactoos.io.InputOf;

/**
 * Velocity template from the classpath.
 *
 * @since 0.2.0
 * @todo #/DEV:30min Add constructor RsClasspath(String) with relative path.
 *  Ensure that getResourceAsStream works on WinOs with '/' symbol.
 */
public final class Classpath extends TemplateEnvelope {

    /**
     * Build relative path to the resource from the classpath.
     *  For example, in case if you resource is placed to
     *  {@code src/main/resources/com/xxx/rs.txt} then relative path is
     *  {@code com/xxx/rs.txt}.
     * @param pattern The pattern for {@link MessageFormat#format}.
     * @param args The arguments for {@link MessageFormat#format}.
     */
    public Classpath(final String pattern, final Object... args) {
        this(new RelativePath(pattern, args));
    }

    /**
     * Ctor.
     * @param rscr The relative path to resource from the classpath.
     *  For example, in case if you resource is placed to
     *  {@code src/main/resources/com/xxx/rs.txt} then relative path is
     *  {@code com/xxx/rs.txt}.
     */
    public Classpath(final Scalar<String> rscr) {
        super(
            rscr,
            () -> {
                if (rscr == null || rscr.value() == null) {
                    throw new IllegalArgumentException(
                        "The resource path can't be a null"
                    );
                }
                return new InputOf(
                    Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(rscr.value())
                );
            }
        );
    }
}

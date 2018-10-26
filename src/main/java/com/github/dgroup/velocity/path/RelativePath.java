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
package com.github.dgroup.velocity.path;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Relative path.
 *
 * @see Class#getResourceAsStream(String)
 *
 * @since 0.2
 */
public final class RelativePath implements Scalar<String> {

    /**
     * The relative path.
     */
    private final Scalar<String> src;

    /**
     * Ctor.
     * @param pattern The path pattern for {@link MessageFormat#format}.
     *  The default delimiter is OS depended path separator.
     */
    public RelativePath(final String pattern) {
        this(pattern, File.separator);
    }

    /**
     * Ctor.
     * @param pattern The pattern for {@link MessageFormat#format}.
     * @param args The arguments for {@link MessageFormat#format}.
     */
    public RelativePath(final String pattern, final Object... args) {
        this(() -> MessageFormat.format(pattern, args));
    }

    /**
     * Ctor.
     * @param src The path to resource.
     */
    public RelativePath(final Scalar<String> src) {
        this.src = src;
    }

    @Override
    public String value() throws Exception {
        return this.src.value();
    }

    @Override
    public String toString() {
        return new UncheckedScalar<>(this).value();
    }

    /**
     * Convert relative path to {@link Path}.
     * @return The path.
     */
    public Scalar<Path> toPath() {
        return () -> Paths.get(this.toString());
    }

}

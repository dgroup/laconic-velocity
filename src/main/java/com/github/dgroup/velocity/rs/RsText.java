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

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.io.Directory;
import org.cactoos.io.InputOf;
import org.cactoos.io.InputStreamOf;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.text.TextOf;

/**
 * The Text resource.
 *
 * Allows to avoid HTML/SQL/etc hardcode (or generation) on the java side.
 * Reed more about Apache Velocity at
 *  http://velocity.apache.org/engine/1.7/user-guide.html.
 *
 * @since 0.1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class RsText extends RsEnvelope<String> {

    /**
     * Ctor.
     * @param tname The name of the Velocity template.
     * @param dir The directory where Velocity template is placed/located.
     */
    public RsText(final String tname, final String dir) {
        this(tname, () -> new InputOf(new InputStreamOf(new File(dir, tname))));
    }

    /**
     * Ctor.
     * @param tname The name of the Velocity template from the classpath.
     * @param roots The root classpath nodes for hierarchical search.
     */
    @SafeVarargs
    public RsText(final String tname, final Scalar<Path>... roots) {
        this(
            tname,
            () -> {
                if (tname == null || tname.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                        "Template name is null or empty"
                    );
                }
                if (roots == null || roots.length == 0) {
                    throw new IllegalArgumentException(
                        "Root path(s) weren't defined or null"
                    );
                }
                return new InputOf(
                    new InputStreamOf(
                        new Filtered<Path>(
                            path -> tname.equals(path.toFile().getName()),
                            new Joined<>(
                                new Mapped<>(
                                    Directory::new,
                                    new Mapped<>(Scalar::value, roots)
                                )
                            )
                        ).iterator().next()
                    )
                );
            }
        );
    }

    /**
     * Ctor.
     * @param src The Velocity template as {@link org.cactoos.Input}.
     * @param tname The name of Velocity template.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public RsText(final String tname, final Scalar<Input> src) {
        super(
            ctx -> {
                // @checkstyle IllegalCatchCheck (20 lines)
                try {
                    final RuntimeServices rsrv = RuntimeSingleton
                        .getRuntimeServices();
                    final StringReader srdr = new StringReader(
                        new TextOf(src.value()).asString()
                    );
                    final Template template = new Template();
                    template.setRuntimeServices(rsrv);
                    template.setData(rsrv.parse(srdr, tname));
                    template.initDocument();
                    final StringWriter writer = new StringWriter();
                    template.merge(ctx, writer);
                    return writer.toString();
                } catch (final Exception cause) {
                    throw new RsException(cause);
                }
            }
        );
    }

}

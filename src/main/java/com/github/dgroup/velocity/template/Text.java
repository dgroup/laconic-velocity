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

import com.github.dgroup.velocity.path.RelativePath;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.io.Directory;
import org.cactoos.io.InputOf;
import org.cactoos.io.InputStreamOf;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

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
public final class Text extends TemplateEnvelope {

    /**
     * Ctor.
     * @param tname The name of the Velocity template.
     * @param dir The directory where Velocity template is placed/located.
     */
    public Text(final String tname, final String dir) {
        this(tname, () -> Paths.get(dir));
    }

    /**
     * Ctor.
     * @param tname The name of the Velocity template from the classpath.
     * @param roots The root classpath nodes for hierarchical search.
     *  In case several files exist in the hierarchy with the same name,
     *  the first one will be used.
     */
    @SafeVarargs
    public Text(final String tname, final Scalar<Path>... roots) {
        this(
            () -> tname,
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
     * Find template from the classpath.
     * @param rscr The relative path to resource from the classpath.
     *  For example, in case if you resource is placed to
     *  {@code src/main/resources/com/xxx/rs.txt} then relative path is
     *  {@code com/xxx/rs.txt}.
     */
    public Text(final RelativePath rscr) {
        this(
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

    /**
     * Ctor.
     * @param tname The name of Velocity template.
     * @param src The Velocity template as {@link org.cactoos.Input}.
     *  The input stream will be closed automatically by
     *  {@link TemplateEnvelope}.
     */
    private Text(final Scalar<String> tname, final Scalar<Input> src) {
        super(tname, src);
    }

}

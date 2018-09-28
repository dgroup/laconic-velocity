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

package com.github.dgroup.velocity.resource;

import com.github.dgroup.velocity.ResourceException;
import java.io.File;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.cactoos.Scalar;

/**
 * Text velocity resource.
 *
 * Allows to avoid HTML/SQL hardcode (or sql generation) in current jsp file.
 * Reed more about Apache Velocity at
 *  http://velocity.apache.org/engine/1.7/user-guide.html.
 *
 * @since 0.1.0
 */
public final class RsText extends RsEnvelope<String> {

    /**
     * Ctor.
     * @param tpath The absolute path to Velocity template.
     */
    public RsText(final String tpath) {
        this(() -> new File(tpath));
    }

    /**
     * Ctor.
     * @param tname The name of the Velocity template.
     * @param dir The directory where Velocity template is placed/located.
     */
    public RsText(final String tname, final String dir) {
        this(() -> new File(dir, tname));
    }

    /**
     * Ctor.
     * @param rsrc The original resource for transformation.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public RsText(final Scalar<File> rsrc) {
        super(
            ctx -> {
                // @checkstyle IllegalCatchCheck (20 lines)
                try {
                    final VelocityEngine veng = new VelocityEngine();
                    final Properties prps = new Properties();
                    prps.put(
                        "file.resource.loader.path",
                        rsrc.value().getParent()
                    );
                    prps.put(
                        "runtime.log.logsystem.class",
                        "org.apache.velocity.runtime.log.NullLogSystem"
                    );
                    veng.init(prps);
                    final Template template = veng.getTemplate(
                        rsrc.value().getName()
                    );
                    final StringWriter writer = new StringWriter();
                    template.merge(ctx, writer);
                    return writer.toString();
                } catch (final Exception cause) {
                    throw new ResourceException(cause);
                }
            }
        );
    }

}

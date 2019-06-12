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
package com.github.dgroup.velocity;

import com.github.dgroup.velocity.template.TemplateException;
import org.apache.velocity.VelocityContext;
import org.cactoos.Scalar;

/**
 * Velocity template for text generation (HTML,SQL,XML,etc).
 *
 * Reed more about Apache Velocity at
 *  http://velocity.apache.org/engine/1.7/user-guide.html.
 *
 * @param <T> Type of template.
 * @since 0.1.0
 */
public interface Template<T> {

    /**
     * Transform the velocity template to HTML/SQL/etc using velocity variables.
     * @param args The velocity variables for template.
     * @return Text, XML, JSON, SQL, HTML, etc
     * @throws TemplateException in case template format error.
     */
    T compose(Arg... args) throws TemplateException;

    /**
     * Transform the velocity template to HTML/SQL/etc using velocity variables.
     *
     * @param args The velocity variables for template.
     * @return Text, XML, JSON, SQL, HTML, etc
     * @throws TemplateException in case template format error.
     */
    T compose(Iterable<Arg> args) throws TemplateException;

    /**
     * Transform the velocity template to HTML/SQL/etc using velocity variables.
     *
     * @param ctx The velocity context with variables.
     * @return Text, XML, JSON, SQL, HTML, etc
     * @throws TemplateException in case template format error.
     */
    T compose(Scalar<VelocityContext> ctx) throws TemplateException;

}

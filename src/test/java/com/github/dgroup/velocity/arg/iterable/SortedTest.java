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

package com.github.dgroup.velocity.arg.iterable;

import com.github.dgroup.velocity.arg.ArgOf;
import com.github.dgroup.velocity.path.PathOf;
import com.github.dgroup.velocity.template.TemplateException;
import com.github.dgroup.velocity.template.Text;
import java.util.Comparator;
import org.cactoos.iterable.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link Sorted}.
 *
 * @since 0.2.0
 * @checkstyle EmptyLinesCheck (500 lines)
 * @checkstyle RequireThisCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle EmptyLineSeparatorCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SortedTest {

    @Test
    public void sorted() {
        MatcherAssert.assertThat(
            new Sorted<>(
                "sorted-arg",
                4, 2, 3
            ).value(),
            Matchers.contains(2, 3, 4)
        );
    }

    @Test
    public void reverseSort() throws TemplateException {
        MatcherAssert.assertThat(
            new Text(
                "sorted.md", new PathOf("src{0}test{0}resources{0}velocity")
            ).compose(
                new ArgOf("header", "Reverse ordered OS"),
                new Sorted<>(
                    "systems",
                    Comparator.reverseOrder(),
                    "Windows", "Linux", "Mac OS"
                )
            ),
            Matchers.equalTo(
                "Reverse ordered OS\n"
                    + "| OS |\n"
                    + "|---|\n"
                    + "| Windows |\n"
                    + "| Mac OS |\n"
                    + "| Linux |\n"
            )
        );
    }

    @Test
    public void sortByFunction() {
        class WithSeveralFields {
            private final int number;
            private final String txt;

            WithSeveralFields(final int number, final String txt) {
                this.number = number;
                this.txt = txt;
            }

            public int numeric() {
                return number;
            }

            public String text() {
                return txt;
            }
        }
        MatcherAssert.assertThat(
            new Mapped<>(
                WithSeveralFields::text,
                new Sorted<WithSeveralFields>(
                    "sorted-arg-label",
                    WithSeveralFields::numeric,
                    new WithSeveralFields(9, "nine"),
                    new WithSeveralFields(4, "four"),
                    new WithSeveralFields(5, "five")
                ).value()
            ),
            Matchers.contains("four", "five", "nine")
        );
    }
}

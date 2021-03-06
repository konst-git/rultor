/**
 * Copyright (c) 2009-2013, rultor.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the rultor.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rultor.snapshot;

import java.lang.reflect.Method;
import java.util.logging.Level;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.xembly.Directives;

/**
 * Tag AspectJ aspect.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@Aspect
public final class TagAspect {

    /**
     * Track execution of a method annotated with {@link Tag}.
     * @param point Join point
     * @return Result of the method
     * @throws Throwable If fails
     * @checkstyle IllegalThrow (4 lines)
     */
    @Around("execution(* * (..)) && @annotation(com.rultor.snapshot.Tag)")
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public Object track(final ProceedingJoinPoint point) throws Throwable {
        final Method method =
            MethodSignature.class.cast(point.getSignature()).getMethod();
        final String tag = method.getAnnotation(Tag.class).value();
        new XemblyLine(
            new Directives()
                .xpath("/snapshot").strict(1).addIfAbsent("tags")
                .xpath(String.format("tag[label='%s']", tag))
                .remove().xpath("/snapshot/tags").strict(1)
                .add("tag").add("label").set(tag)
        ).log();
        try {
            final Object result = point.proceed();
            this.mark(tag, Level.INFO);
            return result;
        // @checkstyle IllegalCatch (1 line)
        } catch (Throwable ex) {
            this.mark(tag, Level.SEVERE);
            throw ex;
        }
    }

    /**
     * Set level to the tag.
     * @param label Label of the tag
     * @param level Level to set
     */
    private void mark(final String label, final Level level) {
        new XemblyLine(
            new Directives()
                .xpath(String.format("/snapshot/tags/tag[label='%s']", label))
                .strict(1)
                .add("level").set(level.toString())
        ).log();
    }

}


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
package com.rultor.users;

import com.jcabi.urn.URN;
import javax.validation.ConstraintViolationException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link AwsStand}.
 * @author Krzysztof Krason (Krzysztof.Krason@gmail.com)
 * @version $Id$
 */
public final class AwsStandsTest {

    /**
     * Check that null constrain is enforced for normal contains call.
     */
    @Test
    public void containsNotEmpty() {
        MatcherAssert.assertThat(
            new AwsStands(
                new RegionMocker().mock(),
                new URN()
            ).contains("test"),
            Matchers.is(true)
        );
    }

    /**
     * Check that null constrain is enforced for blank contains call.
     */
    @Test(expected = ConstraintViolationException.class)
    public void containsBlank() {
        new AwsStands(new RegionMocker().mock(), new URN()).contains("");
    }

    /**
     * Check that null constrain is enforced for null contains call.
     */
    @Test(expected = ConstraintViolationException.class)
    public void containsNull() {
        new AwsStands(new RegionMocker().mock(), new URN()).contains(null);
    }

    /**
     * Check that null constrain is enforced for normal get call.
     */
    @Test
    public void getNormal() {
        MatcherAssert.assertThat(
            new AwsStands(new RegionMocker().mock(), new URN()).get("other"),
            Matchers.notNullValue()
        );
    }

    /**
     * Check that null constrain is enforced for null get call.
     */
    @Test(expected = ConstraintViolationException.class)
    public void getNull() {
        new AwsStands(new RegionMocker().mock(), new URN()).get(null);
    }

    /**
     * Check that null constrain is enforced for blank get call.
     */
    @Test(expected = ConstraintViolationException.class)
    public void getBlank() {
        new AwsStands(new RegionMocker().mock(), new URN()).get("");
    }
}

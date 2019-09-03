/*
 * Copyright (C) 2019 Welyab da Silva Paula
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.welyab.anjabachen;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for <code>PieceMovementMeta</code> class.
 *
 * @author Welyab Paula
 */
public class PieceMovementMetaTest {

	@Test
	@SuppressWarnings("javadoc")
	public void captureCountShouldBeZeroWhenNotSet() {
		Assert.assertEquals(
			0,
			MovementMetadata.builder().build().getCaptureCount()
		);
	}

	@Test
	@SuppressWarnings("javadoc")
	public void incrementCaptureCountShouldIncrementByOne() {
		Assert.assertEquals(
			1,
			MovementMetadata.builder().incrementCaptureCount().build().getCaptureCount()
		);
	}

	@Test
	@SuppressWarnings("javadoc")
	public void incrementCaptureCountIntShouldIncrementByPassedValue() {
		Assert.assertEquals(
			752,
			MovementMetadata.builder().incrementCaptureCount(752).build().getCaptureCount()
		);
	}
}

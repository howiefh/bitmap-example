/*
 * @(#)BitSetOp 1.0 2024/1/28
 *
 * Copyright 2024 Feng Hao.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.howiefh.bitmap;

import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.BitSet;

/**
 * @author fenghao
 * @version 1.0
 * @since 2024/1/28
 */
public class BitSetOp {
    public static void main(String[] args) {
        BitSet bitSet1 = new BitSet();
        for (int i = 0; i < 100000; i++) {
            bitSet1.set(i);
        }
        BitSet bitSet2 = new BitSet();
        for (int i = 100000; i < 200000; i++) {
            bitSet2.set(i);
        }
        boolean result = bitSet1.get(90000);
        Assert.state(result, "bitSet.get(90000) 不为true");
        BitSet bitSet = BitSet.valueOf(bitSet1.toByteArray());
        bitSet.and(bitSet2);
        Assert.state(bitSet.cardinality() == 0, "bitSet.cardinality() 不为0");
        bitSet = BitSet.valueOf(bitSet1.toByteArray());
        bitSet.or(bitSet2);
        Assert.state(bitSet.cardinality() == 200000, "bitSet.cardinality() 不为200000");
        bitSet = BitSet.valueOf(bitSet1.toByteArray());
        bitSet.xor(bitSet2);
        Assert.state(bitSet.cardinality() == 200000, "bitSet.cardinality() 不为200000");
        bitSet = BitSet.valueOf(bitSet1.toByteArray());
        bitSet.andNot(bitSet2);
        Assert.state(bitSet.cardinality() == 100000, "bitSet.cardinality() 不为100000");
    }
}

/*
 * @(#)BitSetSize 1.0 2024/1/28
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

import org.openjdk.jol.info.GraphLayout;

import java.util.BitSet;

/**
 * @author fenghao
 * @version 1.0
 * @since 2024/1/28
 */
public class BitSetSize {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(200000000);
        System.out.println(GraphLayout.parseInstance(bitSet).totalSize());
    }
}

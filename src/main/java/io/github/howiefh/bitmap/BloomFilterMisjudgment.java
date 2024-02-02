/*
 * @(#)BloomFilterMisjudgment 1.0 2024/1/28
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

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

/**
 * @author fenghao
 * @version 1.0
 * @since 2024/1/28
 */
public class BloomFilterMisjudgment {
    public static void main(String[] args) {
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 10000, 0.01);
        for (int i = 0; i < 10000; i++) {
            bloomFilter.put(String.valueOf(i));
        }

        int count = 0;
        for (int i = 10000; i < 20000; i++) {
            if (bloomFilter.mightContain(String.valueOf(i))) {
                count++;
            }
        }
        System.out.println(count);
    }
}

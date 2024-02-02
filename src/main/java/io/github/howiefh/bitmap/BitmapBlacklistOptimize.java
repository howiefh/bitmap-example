/*
 * @(#)BitmapBlacklistOptimize 1.0 2024/1/28
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

import lombok.SneakyThrows;
import org.openjdk.jol.info.GraphLayout;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.longlong.Roaring64NavigableMap;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.LongStream;

/**
 * @author fenghao
 * @version 1.0
 * @since 2024/1/28
 */
public class BitmapBlacklistOptimize {
    public static void main(String[] args) {
        // 模拟构造50万客户bitmap
        Roaring64NavigableMap customerBitmap = randomBitmap(500000, 2000000000L);
        // 模拟构造4000万黑名单bitmap
        Roaring64NavigableMap blacklistBitmap = randomBitmap(40000000, 4000000000L);
        System.out.println("before andNot customerBitmap size: " + GraphLayout.parseInstance(customerBitmap).totalSize() / 1024 / 1024 + "MB" + " cardinality: " + customerBitmap.getLongCardinality());
        System.out.println("before andNot blacklistBitmap size: " + GraphLayout.parseInstance(blacklistBitmap).totalSize() / 1024 / 1024 + "MB" + " cardinality: " + blacklistBitmap.getLongCardinality());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        customerBitmap.andNot(blacklistBitmap);
        stopWatch.stop();
        System.out.println("after andNot customerBitmap size: " + GraphLayout.parseInstance(customerBitmap).totalSize() / 1024 / 1024 + "MB" + " cardinality: " + customerBitmap.getLongCardinality());
        System.out.println(stopWatch.getTotalTimeMillis() + " ms");
    }

    @SneakyThrows
    private static Roaring64NavigableMap randomBitmap(int streamSize, long randomBound) {
        Path path = Paths.get(streamSize + "-" + randomBound + ".dat");
        if (Files.exists(path)) {
            // 为了快速加载之前生成的位图文件没有用Roaring64NavigableMap 的 serialize deserialize方法
            try (InputStream fis = Files.newInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (Roaring64NavigableMap) ois.readObject();
            }
        }
        RandomGenerator secureRandom = RandomGeneratorFactory.of("SecureRandom").create();
        LongStream stream = secureRandom.longs(streamSize, 0L, randomBound);
        Roaring64NavigableMap bitmap = new Roaring64NavigableMap();
        stream.forEach(bitmap::addLong);
        try (OutputStream fos = Files.newOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(bitmap);
            return bitmap;
        }
    }
}

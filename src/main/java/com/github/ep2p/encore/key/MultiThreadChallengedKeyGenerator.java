package com.github.ep2p.encore.key;

import com.github.ep2p.encore.Generator;
import lombok.SneakyThrows;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class MultiThreadChallengedKeyGenerator implements Generator<KeyPair> {
    private final int zeros;
    private final Generator<KeyPair> keyPairGenerator;
    private final int threads;
    private CountDownLatch countDownLatch;

    public MultiThreadChallengedKeyGenerator(int zeros, Generator<KeyPair> keyPairGenerator, int threads) {
        this.zeros = zeros;
        this.keyPairGenerator = keyPairGenerator;
        this.threads = threads;
    }

    @SneakyThrows
    @Override
    public synchronized KeyPair generate() {
        countDownLatch = new CountDownLatch(1);
        AtomicReference<KeyPair> atomicReference = new AtomicReference<>();
        List<Task> tasks = new ArrayList<>();
        for(int i=0; i < threads; i++){
            Task task = new Task(atomicReference);
            new Thread(task).start();
            tasks.add(task);
        }

        countDownLatch.await();
        //cancel tasks:
        tasks.forEach(Task::interrupt);
        countDownLatch = null;
        return atomicReference.get();
    }

    private class Task implements Runnable {
        private final ChallengedKeyGenerator challengedKeyGenerator;
        private final AtomicReference<KeyPair> atomicReference;

        private Task(AtomicReference<KeyPair> atomicReference) {
            this.challengedKeyGenerator = new ChallengedKeyGenerator(zeros, keyPairGenerator);
            this.atomicReference = atomicReference;
        }

        @Override
        public void run() {
            KeyPair keyPair = challengedKeyGenerator.generate();
            if(!challengedKeyGenerator.isInterrupt()){
                atomicReference.set(keyPair);
                countDownLatch.countDown();
            }
        }

        public void interrupt(){
            challengedKeyGenerator.interrupt();
        }
    }

}

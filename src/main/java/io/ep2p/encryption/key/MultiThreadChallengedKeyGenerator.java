package io.ep2p.encryption.key;

import lombok.SneakyThrows;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class MultiThreadChallengedKeyGenerator extends KeyGeneratorDecorator {
    private final int threads;
    private CountDownLatch countDownLatch;

    public MultiThreadChallengedKeyGenerator(ChallengedKeyGeneratorDecorator keyPairGenerator, int threads) {
        super(keyPairGenerator);
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
        private final AtomicReference<KeyPair> atomicReference;

        private Task(AtomicReference<KeyPair> atomicReference) {
            this.atomicReference = atomicReference;
        }

        @Override
        public void run() {
            KeyPair keyPair = keyPairGenerator.generate();
            if(!((ChallengedKeyGeneratorDecorator)keyPairGenerator).isInterrupt()){
                atomicReference.set(keyPair);
                countDownLatch.countDown();
            }
        }

        public void interrupt(){
            ((ChallengedKeyGeneratorDecorator)keyPairGenerator).interrupt();
        }
    }

}

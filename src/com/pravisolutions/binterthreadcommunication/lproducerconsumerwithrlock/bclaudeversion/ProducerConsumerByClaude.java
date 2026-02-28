package com.pravisolutions.binterthreadcommunication.lproducerconsumerwithrlock.bclaudeversion;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerByClaude {

    // ─── Shared Resources ───────────────────────────────────────────────

    private final Queue<Integer> shelf = new LinkedList<>();
    private final int CAPACITY = 3;

    // ReentrantLock = the "one person at a time" door to the shelf
    // Think of it as a single key to the storage room
    private final Lock lock = new ReentrantLock();

    // Two separate "waiting rooms" linked to the same lock
    // notEmpty → customers wait here when shelf is empty
    // notFull  → bakers wait here when shelf is full
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull  = lock.newCondition();


    // ─── Producer (Baker) ────────────────────────────────────────────────

    public void produce(int item) throws InterruptedException {

        lock.lock(); // 🔑 Grab the key — enter the storage room
        try {

            // While shelf is full → go wait in the "notFull" waiting room
            // NOTE: We use WHILE (not if) to re-check after waking up
            while (shelf.size() == CAPACITY) {
                System.out.println("Shelf full! Baker is waiting...");
                notFull.await(); // 😴 Baker sleeps AND releases the lock
                // (so consumer can enter and consume)
            }

            // Shelf has space now → add the item
            shelf.add(item);
            System.out.println("Produced: " + item + " | Shelf: " + shelf);

            // Wake up a sleeping customer (if any) — "Hey! There's bread now!"
            notEmpty.signal();

        } finally {
            lock.unlock(); // 🔓 Always release the key, even if error occurs
        }
    }


    // ─── Consumer (Customer) ─────────────────────────────────────────────

    public void consume() throws InterruptedException {

        lock.lock(); // 🔑 Grab the key — enter the storage room
        try {

            // While shelf is empty → go wait in the "notEmpty" waiting room
            while (shelf.isEmpty()) {
                System.out.println("Shelf empty! Customer is waiting...");
                notEmpty.await(); // 😴 Customer sleeps AND releases the lock
            }

            // Shelf has items → take one
            int item = shelf.poll();
            System.out.println("Consumed: " + item + " | Shelf: " + shelf);

            // Wake up a sleeping baker (if any) — "Hey! There's space now!"
            notFull.signal();

        } finally {
            lock.unlock(); // 🔓 Always release the key
        }
    }


    // ─── Main: Start Threads ─────────────────────────────────────────────

    public static void main(String[] args) {
        ProducerConsumerByClaude bakery = new ProducerConsumerByClaude();

        // Baker thread — produces items 1 to 6
        Thread baker = new Thread(() -> {
            for (int i = 1; i <= 6; i++) {
                try {
                    bakery.produce(i);
                    Thread.sleep(500); // baking takes time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Customer thread — consumes 6 items
        Thread customer = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                try {
                    bakery.consume();
                    Thread.sleep(800); // eating takes time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        baker.start();
        customer.start();
    }
}

/*

### The 3 Most Important Concepts to Internalize

## 1. `await()` does TWO things atomically
```
notFull.await()
   ├── Releases the lock  ← (so the other thread can enter!)
   └── Puts thread to sleep

When woken up:
   ├── Re-acquires the lock
   └── Continues from next line

## 2. Always use while, never if
❌ WRONG - Dangerous!
if (shelf.isEmpty()) {
    notEmpty.await();
}

// ✅ CORRECT - Always re-check condition after waking up
while (shelf.isEmpty()) {
    notEmpty.await();
}
Why? Spurious wakeups exist (thread wakes up randomly without being signaled).
Re-checking ensures correctness.

## 3. Always unlock in finally
lock.lock();
try {
    // your logic
} finally {
    lock.unlock(); // runs even if an exception is thrown!
}

---

## Execution Flow Walkthrough
===================================
Time | Baker              | Shelf      | Customer
-----|--------------------| -----------|------------------
 1   | produces 1,2,3     | [1,2,3]    | sleeping
 2   | shelf full! waits  | [1,2,3]    | wakes up, consumes 1
 3   | woken up, adds 4   | [2,3,4]    | consumes 2
 4   | adds 5             | [3,4,5]    | waiting...
 5   | shelf full! waits  | [3,4,5]    | consumes 3, signals baker
 6   | woken up, adds 6   | [4,5,6]    | consumes 4,5,6
*/
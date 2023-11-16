package Lab7.parallelism;

import Lab7.production.IProduction;

public class SerialBlockRunner extends AbstractBlockRunner {

    private final MyLock lock = new MyLock();

    @Override
    void runOne(IProduction _pOne) {
        _pOne.injectRefs(lock);
        _pOne.start();
        lock.unlock();
    }

    @Override
    void wakeAll() {
        //do nothing
    }

}

package se.kth.iv1350.controller;

import se.kth.iv1350.view.SaleObserver;

public interface SaleObservable {
    public void subscribeOnUpdate(SaleObserver observer);

    public void subscribeOnFinish(SaleObserver observer);
}

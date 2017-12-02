package com.journaldev.navigationdrawer;

/**
 * Created by Fillow on 19.09.2017.
 */

interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

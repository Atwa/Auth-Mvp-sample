package com.atwa.taxineum.ui.main;

import com.atwa.taxineum.data.DataManager;
import com.atwa.taxineum.ui.base.BasePresenter;
import com.atwa.taxineum.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ahmed Atwa on 4/5/2018.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

   private static final String TAG = "MainPresenter";

   @Inject
   public MainPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
      super(dataManager, schedulerProvider, compositeDisposable);
   }
}

package com.atwa.taxineum.di.component;



import com.atwa.taxineum.di.PerService;
import com.atwa.taxineum.di.module.ServiceModule;
import com.atwa.taxineum.service.SyncService;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}

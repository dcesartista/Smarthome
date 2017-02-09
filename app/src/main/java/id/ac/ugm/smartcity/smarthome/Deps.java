package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(DashBoardActivity dashBoardActivity);
}

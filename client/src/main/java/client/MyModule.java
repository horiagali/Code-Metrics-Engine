
package client;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import client.scenes.MainCtrl;
import client.scenes.MainPageCtrl;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MainPageCtrl.class).in(Scopes.SINGLETON);
    }
}
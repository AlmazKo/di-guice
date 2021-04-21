package almazko;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.ProvidesIntoSet;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.Set;

public class Main {

    interface Api {
        void say();
    }

    static class Bitcoin implements Api {
        @ProvidesIntoSet
        @Override public void say() {
            System.out.println("Bitcoin Hello");
        }
    }


    static class Ethereum implements Api {

//        @Inject
//        public Ethereum() {
//        }

        @Override public void say() {
            System.out.println("Ethereum Hello");
        }
    }

    static class CryptoModule extends AbstractModule {
        @Named("bitcoin")
        @ProvidesIntoSet
        Api provideBitcoin() {
            return new Bitcoin();
        }

        //        @Named("ethereum")
        @ProvidesIntoSet
        Api provideEthereum() {
            return new Ethereum();
        }
    }

    static class DemoModule extends AbstractModule {
//        @StringMapKey()
//        void priovideApi() {
//
//        }

        @Override protected void configure() {
            super.configure();
            Multibinder.newSetBinder(binder(), Api.class, Names.named("bitcoin"));

            install(new CryptoModule());
//            uriBinder.addBinding().to(Bitcoin.class);
//            bind(Api.class);//.to(Bitcoin.class);
        }
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DemoModule());
        injector.getInstance(Key.get(new TypeLiteral<Set<Api>>() {
        }, Names.named("bitcoin"))).forEach(Api::say);
//        injector.getInstance(Api.class).say();
    }
}

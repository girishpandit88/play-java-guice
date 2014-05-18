import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;
import play.mvc.SimpleResult;
import services.S3Service;
import services.S3ServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {

    private Injector injector;

    @Override
    public void onStart(final Application application) {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(S3Service.class).toInstance(new S3ServiceImpl(application));
            }
        });
    }

    @Override
    public <A> A getControllerInstance(Class<A> aClass) throws Exception {
        return injector.getInstance(aClass);
    }
    
    @Override
    public Promise<SimpleResult> onError(RequestHeader request, Throwable t) {
        return Promise.<SimpleResult>pure(Results.internalServerError(views.html.errorHandler.render(t)));
    }
}

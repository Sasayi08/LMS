package org.example;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import org.example.app.DefaultAccessManager;
import org.example.app.InitializeApp;
import org.example.app.Sessions;
import org.example.app.model.Person;
import org.example.claim.ClaimExpenseController;
import org.example.claim.ClaimsApiController;
import org.example.login.LoginController;
import org.example.nettexpenses.NettExpensesController;
import org.example.settle.SettlementViewController;
import org.jetbrains.annotations.NotNull;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static io.javalin.apibuilder.ApiBuilder.*;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class LibServer {

    private static final int DEFAULT_PORT = 7070;
    private static final String STATIC_DIR = "/html";
    private final Javalin app;
    private String current_user;

    public static void main(String[] args) {
        LibServer server = new LibServer();
        server.start(DEFAULT_PORT);

    }

    public LibServer() {
        configureThymeleafTemplateEngine();
        app = createAndConfigureServer();
        setupRoutes(app);
    }

    public void start(int port) {
        app.start(port);
    }

    private void setupRoutes(Javalin server) {
        server.routes(() -> {
            loginAndLogoutRoutes();
            homePageRoute();
//            expenseRoutes();
            claimRoutes();
            settlementRoutes();
        });
    }

    private void loginAndLogoutRoutes() {
        post(LoginController.LOGIN_PATH, LoginController::handleLogin);
        get(LoginController.LOGOUT_PATH, LoginController::handleLogout);
    }

    private void homePageRoute() {
        path(NettExpensesController.PATH, () -> get(NettExpensesController::renderHomePage));
    }

    private static void claimRoutes() {
        get(ClaimExpenseController.PATH, ClaimExpenseController::renderClaimExpensePage);
        post(ClaimsApiController.PATH, ClaimsApiController::create);
    }

    private static void settlementRoutes() {
        path(SettlementViewController.PATH, () -> {
            get(SettlementViewController::renderSettleClaimForm);
            post(SettlementViewController::submitSettlement);
        });
    }

    private void configureThymeleafTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateEngine.setTemplateResolver(templateResolver);

        templateEngine.addDialect(new LayoutDialect());
        JavalinThymeleaf.configure(templateEngine);
    }


    @NotNull
    private Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            config.addStaticFiles(STATIC_DIR, Location.CLASSPATH);
            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
            config.addStaticFiles("/public", Location.CLASSPATH);
            config.sessionHandler(Sessions::nopersistSessionHandler);
            config.accessManager(new DefaultAccessManager());
        });
    }
}
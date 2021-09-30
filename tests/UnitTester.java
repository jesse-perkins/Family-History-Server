package tests;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.CommunicatorTests.ClientCommTest;
import tests.CommunicatorTests.ServerCommTest;
import tests.CommunicatorTests.ServerProxyTest;
import tests.DAOTests.AuthKeyDAOTest;
import tests.DAOTests.EventDAOTest;
import tests.DAOTests.PersonDAOTest;
import tests.DAOTests.TableBuilderTest;
import tests.Handlers.Requests.LoadRequestTest;
import tests.Handlers.Requests.LoginRequestTest;
import tests.Handlers.Requests.RegisterRequestTest;
import tests.Handlers.Results.*;
import tests.ModelTests.AuthKeyTest;
import tests.ModelTests.EventTest;
import tests.ModelTests.PersonTest;
import tests.ModelTests.UserTest;
import tests.ServiceModelTests.*;

/**
 * Created by Jesse on 3/20/2017.
 */
public class UnitTester {
    public static void main(String[] args) {
        modelTester();
        DAOTester();
        requestTester();
        resultTester();
        serviceTester();
        communicatorTester();
    }

    private static void modelTester(){
        //Run model class tests
        Result result = JUnitCore.runClasses(AuthKeyTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(EventTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(PersonTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(UserTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

    private static void DAOTester(){
        //Run DAO Tests

        Result result;
        result = JUnitCore.runClasses(TableBuilderTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(AuthKeyDAOTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(EventDAOTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(PersonDAOTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(UserTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

    private static void requestTester(){
        //Run request tests
        Result result = JUnitCore.runClasses(LoadRequestTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(LoginRequestTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(RegisterRequestTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

    private static void resultTester(){
        //Run result tests
        Result result = JUnitCore.runClasses(ClearResultTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(EventResultTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(FillResultTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(LoadResultTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(LoginResultTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(PersonResultTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(RegisterRequestTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

    private static void serviceTester(){
        //Run service tests
        Result result = JUnitCore.runClasses(ClearServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(EventServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(FillServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(LoadServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(LoginServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(PersonServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(RegisterServiceTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

    private static void communicatorTester() {
        //Run communicator tests
        Result result;

        result = JUnitCore.runClasses(ClientCommTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(ServerProxyTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(ServerCommTest.class);
        for (Failure failure : result.getFailures()) {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

}

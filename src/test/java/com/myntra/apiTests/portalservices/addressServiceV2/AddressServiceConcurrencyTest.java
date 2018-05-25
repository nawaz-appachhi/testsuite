package com.myntra.apiTests.portalservices.addressServiceV2;

import com.myntra.test.commons.testbase.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AddressServiceConcurrencyTest extends BaseTest implements AddressConstants{

    int THREAD_POOL_SIZE = 30;
    AddressServiceHelperV2 addressServiceHelperV2 = new AddressServiceHelperV2();

    @DataProvider(name = "myntra")
    public Object[][] addNewAddressDP() {
        String[] validDataMyntra = new String[] {"Address line 1", "Bangalore", "IN", "false", "testemail1@myntra.com", "locality1", "Myntra Address", "560034", "KA", "Karanataka", "7000000000"};
        return new Object[][]{validDataMyntra};
    }

    @Test(dataProvider = "myntra")
    public void singleUserAddressCreationTest(String address, String city, String countryCode, String defaultAddress, String email, String locality, String name, String pincode, String stateCode, String stateName, String mobile) {
        int jabongInitialSize = 0, myntraInitialsize = 0;
        String[] tokens = addressServiceHelperV2.loginAndGetMyntraTokens(CONCURRENCY_USER, CONCURRENCY_PWD);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            tasks.add(new CreateAddressUnit(addressServiceHelperV2,address, city, countryCode, defaultAddress, email, locality, name.concat(" Concurrency"), pincode, stateCode, stateName, mobile, tokens[0], tokens[3], MYNTRA_STORE_ID));
            tasks.add(new CreateAddressUnit(addressServiceHelperV2,address + " JABONG", city, countryCode, defaultAddress, email, locality, name.replace("Myntra", "Jabong").concat(" Concurrency"), pincode, stateCode, stateName, mobile, tokens[0], tokens[3], JABONG_STORE_ID));
        }
        try {
            myntraInitialsize = addressServiceHelperV2.getAddressIdForUser(tokens[3], "myntra").size();
            System.out.println("myntraInitialsize = " + myntraInitialsize);
            jabongInitialSize = addressServiceHelperV2.getAddressIdForUser(tokens[3], "jabong").size();
            System.out.println("jabongInitialSize = " + jabongInitialSize);
            List<Future<Boolean>> results = executor.invokeAll(tasks);
            System.out.println("myntraFinalsize = " + addressServiceHelperV2.getAddressIdForUser(tokens[3], "myntra").size());
            System.out.println("jabongFinalsize = " + addressServiceHelperV2.getAddressIdForUser(tokens[3], "jabong").size());
            assert addressServiceHelperV2.getAddressIdForUser(tokens[3], "jabong").size() - jabongInitialSize == THREAD_POOL_SIZE;
            assert addressServiceHelperV2.getAddressIdForUser(tokens[3], "myntra").size() - myntraInitialsize == THREAD_POOL_SIZE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

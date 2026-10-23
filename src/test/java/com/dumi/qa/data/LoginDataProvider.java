
package com.dumi.qa.data;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {

        return new Object[][] {
            {
                "invalid_user",
                "wrong_password",
                "Username and password do not match"
            },
            {
                "",
                "secret_sauce",
                "Username is required"
            },
            {
                "standard_user",
                "",
                "Password is required"
            },
            {
                "locked_out_user",
                "secret_sauce",
                "Sorry, this user has been locked out"
            }
        };
    }
}

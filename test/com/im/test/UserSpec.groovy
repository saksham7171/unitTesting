package com.im.test

import spock.lang.Specification
import spock.lang.Unroll

class UserSpec extends Specification {

    def "First test"() {
        given:
        User user = new User(firstName: fname, lastName: lname)

        expect:
        user.getFullName() == fname + " " + lname

        where:
        fname     | lname
        "Saksham" | "Sharma"
        "Nitin"   | "Singh"
    }

    def "display full name"() {

        given:
        User user = new User(firstName: fname, lastName: lname)

        and:
        user.gender = g

        expect:
        user.displayName() == result

        where:
        fname    | lname    | g        | result
        "nitin"  | "singh"  | 'Male'   | "Mrnitin singh"
        "Surbhi" | "Dhawan" | 'Female' | "MsSurbhi Dhawan"
    }

    def "valid password"() {

        given:
        User user = new User()

        expect:
        user.isValidPassword(pwd) == result

        where:
        pwd           | result
        ""            | false
        "saksham"     | false
        "saksham7171" | true

    }

    def "reset password"() {

        given: "a user"
        User user = new User(password: pwd)
        user.metaClass.encyryptPassword { String string -> "abc" }

        and:
        def mockedEmailService = Mock(EmailService)
        user.emailService = mockedEmailService
        1 * mockedEmailService.sendCancellationEmail(_ as User, _ as String)

        when:
        user.resetPasswordAndSendEmail()

        then:
        user.password == "abc"

        where:
        pwd = "saksham7171"

    }

    def "encrypt supplied password"() {

        given: "a user"
        User user = new User(password: currpwd)

        and:
        def mockedEncrypterService = Mock(PasswordEncrypterService)
        user.passwordEncrypterService = mockedEncrypterService

        and: "stub encrypt method"
        mockedEncrypterService.encrypt(currpwd) >> { result }

        when:
        String encryptedpassword = user.encyryptPassword(currpwd)

        then:
        encryptedpassword == result

        where:
        currpwd       | result
        "mskld"       | null
        "saksham7171" | "abc"


    }

    def "group according to incomes"() {

        given: "a user"
        User user = new User(incomePerMonth: i)

        when:
        String group = user.getIncomeGroup()

        then:
        group == result

        where:
        i     | result
        4000  | "MiddleClass"
        8000  | "Lower MiddleClass"
        11000 | "Higher MiddleClass"

    }

    def "maintaining a list of purchased products"()

    {

        given: "a user"
        User user = new User();

        and: " a list of products"
        user.purchasedProducts = []

        and: "a product to be added"
        Product p = new Product(name: "handbag")

        when:
        user.purchase(p)

        then:
        user.purchasedProducts.contains(p) == true

    }

    def "removing a product from the list of purchased products"()

    {

        given: "a user"
        User user = new User();

        and: " a list of products"
        user.purchasedProducts = []

        and: "a product to be removed"
        Product p = new Product(name: "handbag")

        when:
        user.cancelPurchase(p)

        then:
        user.purchasedProducts.contains(p) == false

    }


}

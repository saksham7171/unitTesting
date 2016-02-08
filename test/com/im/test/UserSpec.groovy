package com.im.test

import spock.lang.Specification

class UserSpec extends Specification {

    def "First test"() {
\
        given:
        User user = new User(firstName: "Surbhi", lastName: "Dhawan")

        expect:
        user.getFullName() == "Surbhi Dhawan"
    }


    def "display full name"() {

        given:
        User user = new User(firstName: fname, lastName: lname)

        and:
        user.gender = g
        expect:
        user.displayName() == "Mrnitin singh"


        where:
        fname   | lname   | g
        "nitin" | "singh" | 'Male'
//     "Surbhi" | "Dhawan" |'Female'
    }

    def "valid password"() {

        given:
        User user = new User()

        and:
        user.password = "saksham7171"

       expect:
       user.isValidPassword(user.password)==true
    }

    def "reset password"() {

        given: "a user"

        User user = new User(password: currpwd)

        String newpassword = "dummy"

        and:

        def mockedEmailService = Mock(EmailService)

        user.emailService = mockedEmailService

        1 * mockedEmailService.sendCancellationEmail(_ as User, _ as String)

        when:

        String newpwd = user.resetPasswordAndSendEmail()

        then:

        newpwd == user.encyryptPassword(newpassword)

        where:

        currpwd = "mypassword"

    }

    def "encrypt supplied password"() {

        given: "a user"

        User user = new User(password: currpwd)

        and:

        def mockedEncrypterService = Mock(PasswordEncrypterService)

        user.passwordEncrypterService = mockedEncrypterService

        and: "stub encrypt method"

        mockedEncrypterService.encrypt(currpwd) >> "abc"

        when:

        String encryptedpassword = user.encyryptPassword(currpwd)

        then:

        encryptedpassword == "abc"

        where:

        currpwd = "mypassword"

    }
    def "group according to incomes"() {

        given: "a user"

        User user = new User(incomePerMonth: i)

        when:

        String group = user.getIncomeGroup()

        then:

        group == result

        where:

        i | result

        4000 | "MiddleClass"

//        8000 | "Lower MiddleClass"

        11000 | "Higher MiddleClass"

    }

    def "maintaining a list of purchased products"()

    {

        given:"a user"

        User user=new User();

        and:" a list of products"

        user.purchasedProducts=[]

        and:"a product to be added"

        Product p=new Product(name:"handbag")

        when:

        user.purchasedProducts.add(p)

        then:

        user.purchasedProducts.contains(p)==true

    }

    def"removing a product from the list of purchased products"()

    {

        given:"a user"

        User user=new User();

        and:" a list of products"

        user.purchasedProducts=[]

        and:"a product to be removed"

        Product p=new Product(name:"handbag")

        when:

        user.purchasedProducts.remove(p)

        then:

        user.purchasedProducts.contains(p)==false

    }


}
